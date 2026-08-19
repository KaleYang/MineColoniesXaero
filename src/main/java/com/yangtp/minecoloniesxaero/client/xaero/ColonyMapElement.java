package com.yangtp.minecoloniesxaero.client.xaero;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public class ColonyMapElement {
  private final int id;
  private final String name;
  private final BlockPos center;
  private final ResourceKey<Level> dimension;

  public ColonyMapElement(
      int id,
      String name,
      BlockPos center,
      ResourceKey<Level> dimension
  ){
    this.id = id;
    this.name = name;
    this.center = center;
    this.dimension = dimension;
  }

  public int getId(){
    return id;
  }

  public String getName(){
    return name;
  }

  public BlockPos getCenter(){
    return center;
  }

  public ResourceKey<Level> getDimension(){
    return dimension;
  }
}