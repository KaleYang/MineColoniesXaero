package com.yangtp.minecoloniesxaero.client;

import com.yangtp.minecoloniesxaero.MineColoniesXaero;
import net.minecraft.world.level.ChunkPos;
import java.util.HashMap;
import java.util.Map;

public class ClientColonyCache {

  private static final Map<Integer, ColonyData> COLONIES =
      new HashMap<>();

  private static final Map<ChunkPos,Integer> CLAIM_INDEX =
      new HashMap<>();

  public static void updateColony(
      ColonyData data
  ){
    COLONIES.put(
        data.getId(),
        data
    );
    for(ChunkPos pos:data.getClaims()){
      CLAIM_INDEX.put(
          pos,
          data.getId()
      );
    }
    MineColoniesXaero.LOGGER.info(
        "[MineColoniesXaero] Colony Cached: id={} name={} dimension={} center={} claims size={}",
        data.getId(),
        data.getName(),
        data.getDimension() != null
            ? data.getDimension().location()
            : "null",
        data.getCenter(),
        data.getClaims().size()
    );
    for(ChunkPos pos:data.getClaims()){
      MineColoniesXaero.LOGGER.info(
          "[MineColoniesXaero] Claim: chunkX={} chunkZ={}",
          pos.x,
          pos.z
      );
    }
  }

  public static void removeColony(
      int colonyId
  ){
    ColonyData removed =
        COLONIES.remove(colonyId);
    if(removed == null)
      return;
    CLAIM_INDEX.values()
        .removeIf(id -> id == colonyId);
    MineColoniesXaero.LOGGER.info(
        "[MineColoniesXaero] Removed colony cache: id={}",
        colonyId
    );
  }

  public static Map<Integer, ColonyData> getColonies(){
    return COLONIES;
  }

  public static Map<ChunkPos,Integer> getClaimIndex(){
    return CLAIM_INDEX;
  }

  public static void clear(){
    COLONIES.clear();
    CLAIM_INDEX.clear();
  }
}