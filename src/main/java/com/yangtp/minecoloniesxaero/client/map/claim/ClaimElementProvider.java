package com.yangtp.minecoloniesxaero.client.map.claim;

import com.yangtp.minecoloniesxaero.MineColoniesXaero;
import com.yangtp.minecoloniesxaero.client.ClientColonyCache;
import com.yangtp.minecoloniesxaero.client.ColonyData;
import com.yangtp.minecoloniesxaero.client.xaero.ColonyElementContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import xaero.map.element.MapElementRenderProvider;
import xaero.map.element.render.ElementRenderLocation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class ClaimElementProvider
    extends MapElementRenderProvider<ClaimMapElement, ColonyElementContext> {

  private Iterator<ClaimMapElement> iterator;

  private boolean hasNextLogged;

  /*
   * 跨帧元素缓存：Xaero hover 用引用相等（==）判定悬停元素，
   * 必须保证同一 Colony 跨渲染帧返回同一个 ClaimMapElement 实例，
   * 否则 previousHovered.is(element) 恒为 false，hover 重绘永不触发。
   * elementCache: colonyId -> 元素实例
   * signatureCache: colonyId -> 数据内容签名（变化时才重建实例）
   */
  private final Map<Integer, ClaimMapElement> elementCache = new HashMap<>();

  private final Map<Integer, Integer> signatureCache = new HashMap<>();

  private void prepare(
      int location,
      ColonyElementContext context
  ){
    hasNextLogged = false;

    List<ClaimMapElement> elements =
        new ArrayList<>();

    if(location != ElementRenderLocation.WORLD_MAP.getIndex()){
      iterator = elements.iterator();
      return;
    }

    /*
     * Overlay 数据仅来自 ClientColonyCache。
     * 缓存为空时直接返回空列表，不生成任何测试区域。
     */
    Map<Integer, ColonyData> colonies =
        ClientColonyCache.getColonies();

    if(colonies.isEmpty()){
      /*
       * 无 Colony 时清空跨帧缓存，避免删除后残留。
       */
      elementCache.clear();
      signatureCache.clear();
      iterator = elements.iterator();
      return;
    }

    ResourceKey<Level> mapDim =
        context != null
            ? context.getMapDimension()
            : null;

    for(ColonyData data : colonies.values()){
      if(data.getClaims().isEmpty())
        continue;
      if(data.getName() == null)
        continue;
      if(mapDim != null
          && data.getDimension() != null
          && !mapDim.equals(data.getDimension())
      ){
        continue;
      }
      /*
       * 跨帧复用 ClaimMapElement 实例：
       * 签名未变 -> 直接返回缓存对象；数据变化 -> 重建并更新缓存。
       */
      int signature = Objects.hash(
          data.getName(),
          data.getDimension(),
          data.getCenter(),
          data.getClaims()
      );
      ClaimMapElement element = elementCache.get(data.getId());
      Integer cachedSignature = signatureCache.get(data.getId());
      if(element == null
          || cachedSignature == null
          || cachedSignature.intValue() != signature
      ){
        element = new ClaimMapElement(
            data.getId(),
            data.getName(),
            data.getDimension(),
            data.getCenter(),
            data.getClaims().size(),
            data.getClaims()
        );
        elementCache.put(data.getId(), element);
        signatureCache.put(data.getId(), signature);
      }

      elements.add(element);
    }

    /*
     * 清理已不存在的 Colony，避免删除后缓存残留。
     */
    Set<Integer> currentIds = new HashSet<>();
    for(ClaimMapElement element : elements){
      currentIds.add(element.getColonyId());
    }
    elementCache.keySet().retainAll(currentIds);
    signatureCache.keySet().retainAll(currentIds);

    iterator = elements.iterator();
  }

  @Override
  public void begin(
      int location,
      ColonyElementContext context
  ){
    prepare(location, context);
    int count = 0;
    List<ClaimMapElement> remaining = new ArrayList<>();
    while(iterator.hasNext()){
      remaining.add(iterator.next());
      count++;
    }
    iterator = remaining.iterator();
    MineColoniesXaero.LOGGER.info(
        "[MineColoniesXaero] ClaimProvider begin, location={}, elements={}",
        location, count
    );
  }

  @Override
  public boolean hasNext(
      int location,
      ColonyElementContext context
  ){
    boolean hasNext = iterator != null
        && iterator.hasNext();
    if(hasNext && !hasNextLogged){
      hasNextLogged = true;
      MineColoniesXaero.LOGGER.info(
          "[MineColoniesXaero] ClaimProvider has next"
      );
    }
    return hasNext;
  }

  @Override
  public ClaimMapElement getNext(
      int location,
      ColonyElementContext context
  ){
    ClaimMapElement element = iterator.next();
    MineColoniesXaero.LOGGER.info(
        "[MineColoniesXaero] Providing claim: id={} name={} size={}",
        element.getColonyId(),
        element.getColonyName(),
        element.getClaims().size()
    );
    return element;
  }

  @Override
  public void end(
      int location,
      ColonyElementContext context
  ){
    iterator = null;
    MineColoniesXaero.LOGGER.info(
        "[MineColoniesXaero] ClaimProvider end"
    );
  }
}
