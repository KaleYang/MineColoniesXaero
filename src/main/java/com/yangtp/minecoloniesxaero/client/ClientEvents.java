package com.yangtp.minecoloniesxaero.client;

import com.yangtp.minecoloniesxaero.MineColoniesXaero;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;

@EventBusSubscriber(
    modid = "minecoloniesxaero",
    value = Dist.CLIENT
)
public class ClientEvents {
  @SubscribeEvent
  public static void register(
      RegisterClientCommandsEvent event
  ){
    ClientProbeCommand.register(
        event.getDispatcher()
    );
    System.out.println(
        "[MineColoniesXaero] Command registered"
    );
  }

  /*
   * 登出时 MineColonies 会 resetColonyViews() 清空全部
   * ColonyView（日志“Removed all colony views”），但不发任何事件。
   * 此处同步清空缓存，避免残留旧 Claim。
   */
  @SubscribeEvent
  public static void onLogout(
      ClientPlayerNetworkEvent.LoggingOut event
  ){
    if(ClientColonyCache.getColonies().isEmpty())
      return;
    ClientColonyCache.clear();
    MineColoniesXaero.LOGGER.info(
        "[MineColoniesXaero] Colony cache cleared on logout"
    );
  }
}