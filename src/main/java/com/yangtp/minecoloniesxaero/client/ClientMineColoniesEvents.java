package com.yangtp.minecoloniesxaero.client;

import com.minecolonies.api.IMinecoloniesAPI;
import com.minecolonies.api.eventbus.events.colony.ColonyViewUpdatedModEvent;

public class ClientMineColoniesEvents {
  private static boolean registered = false;
  public static void register(){
    if(registered)
      return;
    IMinecoloniesAPI.getInstance()
        .getEventBus()
        .subscribe(
            ColonyViewUpdatedModEvent.class,
            ColonyViewListener::onColonyUpdate
        );
    registered = true;
    System.out.println(
        "[MineColoniesXaero] MineColonies EventBus registered"
    );
  }
}