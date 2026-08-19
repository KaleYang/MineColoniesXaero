package com.yangtp.minecoloniesxaero.client.map.claim;

import com.yangtp.minecoloniesxaero.MineColoniesXaero;
import com.yangtp.minecoloniesxaero.client.xaero.ColonyElementContext;
import net.minecraft.client.Minecraft;
import net.minecraft.world.level.ChunkPos;
import xaero.map.element.MapElementReader;
import xaero.map.element.render.ElementRenderLocation;

public class ClaimElementReader
    extends MapElementReader<ClaimMapElement, ColonyElementContext, ClaimElementRenderer> {

  /*
   * 调试：每个方法只记录一次，避免每帧刷屏
   */
  private static boolean isHiddenLogged;
  private static boolean renderXLogged;
  private static boolean renderZLogged;
  private static boolean renderBoxLogged;
  private static boolean interactionBoxLogged;
  private static boolean boxScaleLogged;
  private static boolean isOnScreenLogged;

  /*
   * Xaero 默认 isInteractable 返回 false，
   * hover 检测（isHoveredOnMap）只对可交互元素生效，
   * 必须覆写返回 true 才能触发悬停判定。
   */
  @Override
  public boolean isInteractable(
      int location,
      ClaimMapElement e
  ){
    return e != null;
  }

  @Override
  public boolean isInteractable(
      ElementRenderLocation location,
      ClaimMapElement e
  ){
    return e != null;
  }

  @Override
  public boolean isHidden(
      ClaimMapElement e,
      ColonyElementContext c
  ){
    if(!isHiddenLogged){
      isHiddenLogged = true;
      MineColoniesXaero.LOGGER.info(
          "[MineColoniesXaero] ClaimReader isHidden -> false"
      );
    }
    return false;
  }

  /*
   * 调试：返回 Claims 中心的方块坐标。
   * 之前返回 0，玩家远离原点时元素被 Xaero 视口剔除，
   * 导致 renderElement 永远不被调用。
   */
  @Override
  public double getRenderX(
      ClaimMapElement e,
      ColonyElementContext c,
      float scale
  ){
    double x = e.getCenterBlockX();
    if(!renderXLogged){
      renderXLogged = true;
      MineColoniesXaero.LOGGER.info(
          "[MineColoniesXaero] ClaimReader getRenderX -> {}", x
      );
    }
    return x;
  }

  @Override
  public double getRenderZ(
      ClaimMapElement e,
      ColonyElementContext c,
      float scale
  ){
    double z = e.getCenterBlockZ();
    if(!renderZLogged){
      renderZLogged = true;
      MineColoniesXaero.LOGGER.info(
          "[MineColoniesXaero] ClaimReader getRenderZ -> {}", z
      );
    }
    return z;
  }

  /*
   * 调试：包围盒覆盖整个 Claims 范围（相对 renderX/Z）。
   * 之前全部返回 0，0x0 包围盒被 Xaero 视为不可见而跳过 renderElement。
   */
  @Override
  public int getInteractionBoxLeft(
      ClaimMapElement e,
      ColonyElementContext c,
      float scale
  ){
    if(!interactionBoxLogged){
      interactionBoxLogged = true;
      MineColoniesXaero.LOGGER.info(
          "[MineColoniesXaero] ClaimReader interactionBox L/R/T/B -> {}/{}/{}/{}",
          -getClaimsHalfExtentX(e), getClaimsHalfExtentX(e),
          -getClaimsHalfExtentZ(e), getClaimsHalfExtentZ(e)
      );
    }
    return -getClaimsHalfExtentX(e);
  }

  @Override
  public int getInteractionBoxRight(
      ClaimMapElement e,
      ColonyElementContext c,
      float scale
  ){
    return getClaimsHalfExtentX(e);
  }

  @Override
  public int getInteractionBoxTop(
      ClaimMapElement e,
      ColonyElementContext c,
      float scale
  ){
    return -getClaimsHalfExtentZ(e);
  }

  @Override
  public int getInteractionBoxBottom(
      ClaimMapElement e,
      ColonyElementContext c,
      float scale
  ){
    return getClaimsHalfExtentZ(e);
  }

  @Override
  public int getRenderBoxLeft(
      ClaimMapElement e,
      ColonyElementContext c,
      float scale
  ){
    if(!renderBoxLogged){
      renderBoxLogged = true;
      MineColoniesXaero.LOGGER.info(
          "[MineColoniesXaero] ClaimReader renderBox L/R/T/B -> {}/{}/{}/{}",
          -getClaimsHalfExtentX(e), getClaimsHalfExtentX(e),
          -getClaimsHalfExtentZ(e), getClaimsHalfExtentZ(e)
      );
    }
    return -getClaimsHalfExtentX(e);
  }

  @Override
  public int getRenderBoxRight(
      ClaimMapElement e,
      ColonyElementContext c,
      float scale
  ){
    return getClaimsHalfExtentX(e);
  }

  @Override
  public int getRenderBoxTop(
      ClaimMapElement e,
      ColonyElementContext c,
      float scale
  ){
    return -getClaimsHalfExtentZ(e);
  }

  @Override
  public int getRenderBoxBottom(
      ClaimMapElement e,
      ColonyElementContext c,
      float scale
  ){
    return getClaimsHalfExtentZ(e);
  }

  /*
   * 调试：Xaero 主渲染循环中 getNext 之后的关键闸门。
   * isOnScreen 返回 false 时元素被跳过，
   * renderElement 永远不被调用。
   */
  @Override
  public boolean isOnScreen(
      ClaimMapElement e,
      double renderX,
      double renderZ,
      int width,
      int height,
      double scale,
      double screenSizeBasedScale,
      double dimScale,
      ColonyElementContext c,
      float partialTicks
  ){
    boolean result = super.isOnScreen(
        e, renderX, renderZ,
        width, height,
        scale, screenSizeBasedScale, dimScale,
        c, partialTicks
    );
    if(!isOnScreenLogged){
      isOnScreenLogged = true;
      MineColoniesXaero.LOGGER.info(
          "[MineColoniesXaero] ClaimReader isOnScreen -> {} renderPos=({}, {}) screen={}x{} scale={} dimScale={}",
          result, renderX, renderZ, width, height, scale, dimScale
      );
    }
    return result;
  }

  @Override
  public int getLeftSideLength(
      ClaimMapElement e,
      Minecraft minecraft
  ){
    return 0;
  }

  @Override
  public String getMenuName(
      ClaimMapElement e
  ){
    return e.getColonyName();
  }

  @Override
  public String getFilterName(
      ClaimMapElement e
  ){
    return e.getColonyName();
  }

  @Override
  public int getMenuTextFillLeftPadding(
      ClaimMapElement e
  ){
    return 0;
  }

  @Override
  public int getRightClickTitleBackgroundColor(
      ClaimMapElement e
  ){
    return 0;
  }

  @Override
  public boolean shouldScaleBoxWithOptionalScale(){
    return false;
  }

  /*
   * 调试：默认实现可能返回 0，
   * 导致 Xaero 认为元素大小为 0 而跳过 renderElement。
   */
  @Override
  public float getBoxScale(
      int location,
      ClaimMapElement e,
      ColonyElementContext c
  ){
    return 1.0F;
  }

  @Override
  public float getBoxScale(
      ElementRenderLocation location,
      ClaimMapElement e,
      ColonyElementContext c
  ){
    if(!boxScaleLogged){
      boxScaleLogged = true;
      MineColoniesXaero.LOGGER.info(
          "[MineColoniesXaero] ClaimReader getBoxScale -> 1.0 location={}",
          location
      );
    }
    return 1.0F;
  }

  /*
   * Claims 中心方块坐标已移至 ClaimMapElement.getCenterBlockX/Z，
   * 保证 Reader 锚点与 Renderer 绘制锚点完全一致。
   */

  /*
   * Claims 半宽（方块数），最小保底 8，
   * 避免空 Claims 时包围盒为 0。
   */
  private static int getClaimsHalfExtentX(
      ClaimMapElement e
  ){
    int min = Integer.MAX_VALUE;
    int max = Integer.MIN_VALUE;
    for(ChunkPos chunk : e.getClaims()){
      if(chunk.x < min) min = chunk.x;
      if(chunk.x > max) max = chunk.x;
    }
    if(max < min)
      return 8;
    return (max - min + 1) * 8 + 1;
  }

  private static int getClaimsHalfExtentZ(
      ClaimMapElement e
  ){
    int min = Integer.MAX_VALUE;
    int max = Integer.MIN_VALUE;
    for(ChunkPos chunk : e.getClaims()){
      if(chunk.z < min) min = chunk.z;
      if(chunk.z > max) max = chunk.z;
    }
    if(max < min)
      return 8;
    return (max - min + 1) * 8 + 1;
  }
}
