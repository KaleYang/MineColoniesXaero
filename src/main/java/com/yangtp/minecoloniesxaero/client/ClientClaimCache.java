package com.yangtp.minecoloniesxaero.client;

import net.minecraft.world.level.ChunkPos;
import java.util.HashMap;
import java.util.Map;

public class ClientClaimCache {

  private static final Map<ChunkPos, Integer> CLAIMS =
      new HashMap<>();
  /**
   * 更新客户端缓存
   */
  public static void update(
      Map<ChunkPos, Integer> claims
  ) {
    CLAIMS.clear();
    CLAIMS.putAll(claims);
  }

  /**
   * 获取所有Claim
   */
  public static Map<ChunkPos, Integer> getClaims() {
    return CLAIMS;
  }


  /**
   * 清空缓存
   */
  public static void clear(){
    CLAIMS.clear();
  }

}