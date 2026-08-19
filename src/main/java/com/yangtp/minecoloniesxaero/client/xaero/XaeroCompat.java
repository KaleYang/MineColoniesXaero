package com.yangtp.minecoloniesxaero.client.xaero;

import com.yangtp.minecoloniesxaero.MineColoniesXaero;
import com.yangtp.minecoloniesxaero.client.map.claim.ClaimElementRenderer;
import xaero.map.WorldMap;

public class XaeroCompat {
  private static boolean registered=false;
  public static void register(){
    if(registered)
      return;
    MineColoniesXaero.LOGGER.info(
        "[MineColoniesXaero] Registering Xaero elements"
    );
    if(WorldMap.mapElementRenderHandler==null){
      MineColoniesXaero.LOGGER.warn(
          "[MineColoniesXaero] mapElementRenderHandler is null, skip register"
      );
      return;
    }
    WorldMap.mapElementRenderHandler.add(
        new ColonyElementRenderer()
    );
    MineColoniesXaero.LOGGER.info(
        "[MineColoniesXaero] ColonyElementRenderer registered"
    );
    WorldMap.mapElementRenderHandler.add(
        new ClaimElementRenderer()
    );
    MineColoniesXaero.LOGGER.info(
        "[MineColoniesXaero] ClaimElementRenderer registered"
    );
    registered=true;
  }
}