package com.yangtp.minecoloniesxaero.client.map.claim;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import java.util.Set;

public class ClaimMapElement {

  private final int colonyId;
  private final String colonyName;
  private final ResourceKey<Level> dimension;
  private final BlockPos center;
  private final int claimCount;
  private final Set<ChunkPos> claims;

  public ClaimMapElement(
      int colonyId,
      String colonyName,
      ResourceKey<Level> dimension,
      BlockPos center,
      int claimCount,
      Set<ChunkPos> claims
  ){
    this.colonyId = colonyId;
    this.colonyName = colonyName;
    this.dimension = dimension;
    this.center = center;
    this.claimCount = claimCount;
    this.claims = claims;
  }

  public int getColonyId(){
    return colonyId;
  }

  public String getColonyName(){
    return colonyName;
  }

  public ResourceKey<Level> getDimension(){
    return dimension;
  }

  public BlockPos getCenter(){
    return center;
  }

  public int getClaimCount(){
    return claimCount;
  }

  public Set<ChunkPos> getClaims(){
    return claims;
  }

  /*
   * Claims 中心方块坐标（世界坐标）。
   * Reader.getRenderX/Z 和 Renderer 绘制必须使用同一锚点，
   * 否则绘制位置与元素屏幕位置不匹配。
   * center = (minChunk + maxChunk + 1) * 8
   */
  public double getCenterBlockX(){
    int min = Integer.MAX_VALUE;
    int max = Integer.MIN_VALUE;
    for(ChunkPos chunk : claims){
      if(chunk.x < min) min = chunk.x;
      if(chunk.x > max) max = chunk.x;
    }
    if(max < min)
      return 0;
    return (min + max + 1) * 8.0;
  }

  public double getCenterBlockZ(){
    int min = Integer.MAX_VALUE;
    int max = Integer.MIN_VALUE;
    for(ChunkPos chunk : claims){
      if(chunk.z < min) min = chunk.z;
      if(chunk.z > max) max = chunk.z;
    }
    if(max < min)
      return 0;
    return (min + max + 1) * 8.0;
  }
}
