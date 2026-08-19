package com.yangtp.minecoloniesxaero.client.xaero;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class ColonyElementContext {

  private ResourceKey<Level> mapDimension;

  public ResourceKey<Level> getMapDimension(){
    return mapDimension;
  }

  public void setMapDimension(
      ResourceKey<Level> dimension
  ){
    this.mapDimension = dimension;
  }
}