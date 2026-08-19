package com.yangtp.minecoloniesxaero.client;

import com.yangtp.minecoloniesxaero.MineColoniesXaero;
import com.yangtp.minecoloniesxaero.client.xaero.XaeroCompat;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@EventBusSubscriber(
    modid = "minecoloniesxaero",
    value = Dist.CLIENT
)
public class ClientInit {
  @SubscribeEvent
  public static void clientSetup(
      FMLClientSetupEvent event
  ){
    MineColoniesXaero.LOGGER.info(
        "[MineColoniesXaero] Client setup started"
    );
    ClientMineColoniesEvents.register();
    event.enqueueWork(XaeroCompat::register);
    MineColoniesXaero.LOGGER.info(
        "[MineColoniesXaero] Client setup finished"
    );
  }
}