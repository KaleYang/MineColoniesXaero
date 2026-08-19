package com.yangtp.minecoloniesxaero.client;

import com.minecolonies.api.colony.IColonyManager;
import com.minecolonies.api.colony.IColonyView;
import com.minecolonies.api.eventbus.events.colony.ColonyViewUpdatedModEvent;
import com.yangtp.minecoloniesxaero.MineColoniesXaero;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ColonyViewListener {
  public static void onColonyUpdate(
      ColonyViewUpdatedModEvent event
  ){
    Minecraft mc =
        Minecraft.getInstance();
    if(mc.level == null)
      return;
    IColonyManager manager =
        IColonyManager.getInstance();
    List<IColonyView> colonies =
        manager.getColonyViews(
            mc.level
        );
    ResourceKey<Level> currentDim =
        mc.level.dimension();
    /*
     * 收集当前客户端存在的所有 colonyId，
     * 更新/覆盖对应缓存，并删除已不存在的条目。
     */
    Set<Integer> currentIds =
        new HashSet<>();
    for(IColonyView view : colonies){
      int colonyId =
          view.getID();
      currentIds.add(colonyId);
      ColonyData data =
          new ColonyData(
              colonyId
          );
      data.setView(
          view
      );
      data.setDimension(
          view.getDimension()
      );
      data.setName(
          view.getName()
      );
      data.setCenter(
          view.getCenter()
      );
      var claimData =
          manager.getClaimData(
              view.getDimension()
          );
      claimData.forEach(
          (pos, claim)->{
            if(claim.getOwningColony()
                == colonyId){
              data.addClaim(
                  pos
              );
            }
          }
      );
      ClientColonyCache.updateColony(
          data
      );
    }
    /*
     * 差分删除：缓存中属于当前维度但已不在
     * ColonyView 列表中的 Colony 视为已删除。
     */
    for(Integer cachedId :
        Set.copyOf(
            ClientColonyCache.getColonies().keySet()
        )){
      ColonyData cached =
          ClientColonyCache.getColonies().get(cachedId);
      if(cached == null)
        continue;
      if(cached.getDimension() != null
          && !currentDim.equals(cached.getDimension())){
        continue;
      }
      if(!currentIds.contains(cachedId)){
        ClientColonyCache.removeColony(cachedId);
      }
    }
    MineColoniesXaero.LOGGER.info(
        "[MineColoniesXaero] Colony cache synced: views={} cache={}",
        colonies.size(),
        ClientColonyCache.getColonies().size()
    );
  }
}