package com.yangtp.minecoloniesxaero.client;

import com.minecolonies.api.colony.IColonyView;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ChunkPos;
import java.util.HashSet;
import java.util.Set;

public class ColonyData {
  private final int id;
  private String name;
  private ResourceKey<Level> dimension;
  private BlockPos center;
  private IColonyView view;

  private final Set<ChunkPos> claims = new HashSet<>();
  public ColonyData(
      int id
  ){
    this.id = id;
  }
  public int getId(){
    return id;
  }
  public String getName(){
    return name;
  }
  public void setName(
      String name
  ){
    this.name = name;
  }
  public ResourceKey<Level> getDimension(){
    return dimension;
  }
  public void setDimension(
      ResourceKey<Level> dimension
  ){
    this.dimension = dimension;
  }
  public BlockPos getCenter(){
    return center;
  }
  public void setCenter(
      BlockPos center
  ){
    this.center = center;
  }
  public IColonyView getView(){
    return view;
  }
  public void setView(
      IColonyView view
  ){
    this.view = view;
  }
  public Set<ChunkPos> getClaims(){
    return claims;
  }
  public void clearClaims(){
    claims.clear();
  }
  public void addClaim(
      ChunkPos pos
  ){
    claims.add(pos);
  }
}