package com.yangtp.minecoloniesxaero.client;

import com.mojang.brigadier.CommandDispatcher;
import com.yangtp.minecoloniesxaero.client.map.claim.ClaimElementRenderer;
import com.yangtp.minecoloniesxaero.client.map.claim.ClaimElementRenderer.TooltipMode;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.ChunkPos;

public class ClientProbeCommand {
  public static void register(
      CommandDispatcher<CommandSourceStack> dispatcher
  ){
    dispatcher.register(
        Commands.literal("xaeroprobe")
            .then(
                Commands.literal("claims")
                    .executes(context -> {
                      var colonies =
                          ClientColonyCache
                              .getColonies();
                      var claims =
                          ClientColonyCache
                              .getClaimIndex();
                      Minecraft mc =
                          Minecraft.getInstance();
                      if(mc.player != null){
                        mc.player.sendSystemMessage(
                            Component.literal(
                                "§a[MineColoniesXaero]§r\n"
                                    +
                                    "Colonies="
                                    +
                                    colonies.size()
                                    +
                                    "\nClaims="
                                    +
                                    claims.size()
                            )
                        );
                        for(var entry :
                            colonies.entrySet()){
                          ColonyData data =
                              entry.getValue();
                          mc.player.sendSystemMessage(
                              Component.literal(
                                  "ID="
                                      +
                                      data.getId()
                                      +
                                      " Name="
                                      +
                                      data.getName()
                                      +
                                      " Claims="
                                      +
                                      data.getClaims()
                                          .size()
                              )
                          );
                        }
                      }
                      return 1;
                    })
            )
            .then(
                Commands.literal("tooltip")
                    /*
                     * 无参数：显示当前 tooltip 模式
                     */
                    .executes(context -> {
                      Minecraft mc =
                          Minecraft.getInstance();
                      if(mc.player != null){
                        mc.player.sendSystemMessage(
                            Component.literal(
                                "§a[MineColoniesXaero]§r "
                                    + "当前 Tooltip 模式: §e"
                                    + ClaimElementRenderer
                                        .getTooltipMode()
                            )
                        );
                      }
                      return 1;
                    })
                    .then(
                        Commands.literal("off")
                            .executes(context -> {
                              ClaimElementRenderer
                                  .setTooltipMode(
                                      TooltipMode.OFF
                                  );
                              Minecraft mc =
                                  Minecraft.getInstance();
                              if(mc.player != null){
                                mc.player.sendSystemMessage(
                                    Component.literal(
                                        "§a[MineColoniesXaero]§r "
                                            + "Claim 绘制与 Tooltip 已关闭 (§eOFF§r)"
                                    )
                                );
                              }
                              return 1;
                            })
                    )
                    .then(
                        Commands.literal("simple")
                            .executes(context -> {
                              ClaimElementRenderer
                                  .setTooltipMode(
                                      TooltipMode.SIMPLE
                                  );
                              Minecraft mc =
                                  Minecraft.getInstance();
                              if(mc.player != null){
                                mc.player.sendSystemMessage(
                                    Component.literal(
                                        "§a[MineColoniesXaero]§r "
                                            + "Tooltip 模式已切换为 §eSIMPLE§r（仅名称）"
                                    )
                                );
                              }
                              return 1;
                            })
                    )
                    .then(
                        Commands.literal("detailed")
                            .executes(context -> {
                              ClaimElementRenderer
                                  .setTooltipMode(
                                      TooltipMode.DETAILED
                                  );
                              Minecraft mc =
                                  Minecraft.getInstance();
                              if(mc.player != null){
                                mc.player.sendSystemMessage(
                                    Component.literal(
                                        "§a[MineColoniesXaero]§r "
                                            + "Tooltip 模式已切换为 §eDETAILED§r（完整字段）"
                                    )
                                );
                              }
                              return 1;
                            })
                    )
            )
    );
  }
}