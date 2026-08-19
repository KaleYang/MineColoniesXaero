package com.yangtp.minecoloniesxaero.client.map.claim;

import com.mojang.blaze3d.vertex.PoseStack;
import com.yangtp.minecoloniesxaero.client.xaero.ColonyElementContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import xaero.map.element.MapElementRenderer;
import xaero.map.element.render.ElementRenderInfo;
import xaero.map.element.render.ElementRenderLocation;
import xaero.map.graphics.renderer.multitexture.MultiTextureRenderTypeRendererProvider;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ClaimElementRenderer
    extends MapElementRenderer<
    ClaimMapElement,
    ColonyElementContext,
    ClaimElementRenderer
    > {

  private final ColonyElementContext context;

  /*
   * 边框：不透明蓝色 ARGB (0xAARRGGBB)
   */
  private static final int BORDER_COLOR =
      0xFF0000FF;

  /*
   * 内部填充：半透明蓝色 ARGB (0xAARRGGBB)
   */
  private static final int FILL_COLOR =
      0x330000FF;

  /*
   * Tooltip 配色 ARGB (0xAARRGGBB)
   */
  private static final int TOOLTIP_BG_COLOR =
      0x99000000;
  private static final int TOOLTIP_BORDER_COLOR =
      0xFF5555FF;
  private static final int TOOLTIP_TITLE_COLOR =
      0xFFFFAA00;
  private static final int TOOLTIP_LABEL_COLOR =
      0xFFAAAAAA;
  private static final int TOOLTIP_VALUE_COLOR =
      0xFFFFFFFF;

  /*
   * Tooltip 字体缩放与行距。
   * 缩放必须为整数倍，避免字体纹理插值导致文字模糊；
   * 绘制坐标除以缩放值后也保持整数像素对齐。
   * 行距 = 字体行高 + TOOLTIP_LINE_GAP（绘制时按字体动态计算）。
   */
  private static final float TOOLTIP_TEXT_SCALE = 2.0F;
  private static final int TOOLTIP_LINE_GAP = 6;

  /*
   * Tooltip 显示模式开关：
   * OFF      = 不绘制 Claim 范围，也不显示 tooltip
   * SIMPLE   = 仅显示殖民地名称
   * DETAILED = Colony 标题 + ID/Dimension/Center/Claim Chunks 字段
   * 非 final：运行时可通过 /xaeroprobe tooltip 指令切换，
   * 默认 SIMPLE。
   */
  public enum TooltipMode{ OFF, SIMPLE, DETAILED }
  private static TooltipMode tooltipMode = TooltipMode.SIMPLE;

  public static TooltipMode getTooltipMode(){
    return tooltipMode;
  }

  public static void setTooltipMode(TooltipMode mode){
    if(mode != null)
      tooltipMode = mode;
  }

  /*
   * Tooltip 单行内容：字段名与值分段显示，各自着色。
   * 标题行 value 着同色（TOOLTIP_TITLE_COLOR）。
   */
  private record TooltipLine(
      String label,
      String value,
      int labelColor,
      int valueColor
  ){}


  public ClaimElementRenderer(){
    this(
        new ColonyElementContext(),
        new ClaimElementProvider(),
        new ClaimElementReader()
    );
  }

  private ClaimElementRenderer(
      ColonyElementContext context,
      ClaimElementProvider provider,
      ClaimElementReader reader
  ){
    super(context, provider, reader);
    this.context = context;
  }


  @Override
  public boolean shouldRender(
      ElementRenderLocation location,
      boolean dimScaled
  ){
    /*
     * 调试：临时全部放行，确认是否因过滤被跳过
     */
    return true;
  }

  @Override
  public boolean shouldRender(
      int index,
      boolean dimScaled
  ){
    /*
     * 调试：临时全部放行，确认是否因过滤被跳过
     */
    return true;
  }


  @Override
  public void beforeRender(
      int index,
      Minecraft minecraft,
      GuiGraphics guiGraphics,
      double mapX,
      double mapZ,
      double scale,
      double mouseX,
      float partialTicks,
      double cameraX,
      double cameraZ,
      net.minecraft.client.renderer.texture.TextureManager textureManager,
      net.minecraft.client.gui.Font font,
      MultiBufferSource.BufferSource buffer,
      MultiTextureRenderTypeRendererProvider provider,
      boolean dimScaled
  ){
  }

  @Override
  public void afterRender(
      int index,
      Minecraft minecraft,
      GuiGraphics guiGraphics,
      double mapX,
      double mapZ,
      double scale,
      double mouseX,
      float partialTicks,
      double cameraX,
      double cameraZ,
      net.minecraft.client.renderer.texture.TextureManager textureManager,
      net.minecraft.client.gui.Font font,
      MultiBufferSource.BufferSource buffer,
      MultiTextureRenderTypeRendererProvider provider,
      boolean dimScaled
  ){
  }


  @Override
  public void preRender(
      ElementRenderInfo info,
      MultiBufferSource.BufferSource buffer,
      MultiTextureRenderTypeRendererProvider provider,
      boolean b
  ){
    try{
      context.setMapDimension(info.mapDimension);
    }catch(Exception ignored){}
  }

  @Override
  public void postRender(
      ElementRenderInfo info,
      MultiBufferSource.BufferSource buffer,
      MultiTextureRenderTypeRendererProvider provider,
      boolean b
  ){
  }

  @Override
  public void renderElementShadow(
      ClaimMapElement element,
      boolean hovered,
      float scale,
      double x,
      double z,
      ElementRenderInfo info,
      GuiGraphics guiGraphics,
      MultiBufferSource.BufferSource buffer,
      MultiTextureRenderTypeRendererProvider provider
  ){
    /*
     * 阴影通道与主通道使用同一套坐标计算。
     * x/z = 锚点亚像素偏移，info.scale = 缩放比例。
     * OFF 模式：连 Claim 范围也不绘制。
     */
    if(tooltipMode == TooltipMode.OFF)
      return;
    drawClaims(element, guiGraphics, x, z, info.scale);
  }

  @Override
  public void renderElementPre(
      int index,
      ClaimMapElement element,
      boolean hovered,
      Minecraft minecraft,
      GuiGraphics guiGraphics,
      double x,
      double y,
      double z,
      double scale,
      float partialTicks,
      double cameraX,
      double cameraZ,
      net.minecraft.client.renderer.texture.TextureManager textureManager,
      net.minecraft.client.gui.Font font,
      MultiBufferSource.BufferSource buffer,
      MultiTextureRenderTypeRendererProvider provider,
      float alpha,
      double mouseX,
      double mouseY,
      boolean dimScaled,
      float rotation
  ){
  }

  @Override
  public boolean renderElement(
      int index,
      ClaimMapElement element,
      boolean hovered,
      Minecraft minecraft,
      GuiGraphics guiGraphics,
      double x,
      double y,
      double z,
      double scale,
      float partialTicks,
      double cameraX,
      double cameraZ,
      net.minecraft.client.renderer.texture.TextureManager textureManager,
      net.minecraft.client.gui.Font font,
      MultiBufferSource.BufferSource buffer,
      MultiTextureRenderTypeRendererProvider provider,
      int color,
      double mouseX,
      float alpha,
      double mouseY,
      double rotation,
      boolean dimScaled,
      float zoom
  ){
    /*
     * 坐标体系（反编译 Xaero 1.44.2 确认）：
     * PoseStack 已被平移到元素锚点（Reader.getRenderX/Z）的屏幕位置，
     * 参数 x/y 是地图中心世界坐标（renderPos），不是屏幕坐标，不能直接用来绘制。
     * 真正的绘制输入：
     *   mouseY/rotation = 锚点屏幕位置的亚像素偏移（xOffset/zOffset）
     *   cameraX         = info.scale 缩放比例
     *
     * OFF 模式：Claim 范围与 tooltip 都不绘制，直接返回。
     */
    if(tooltipMode == TooltipMode.OFF)
      return true;

    drawClaims(
        element,
        guiGraphics,
        mouseY,
        rotation,
        cameraX
    );

    /*
     * Xaero hover 通道：鼠标悬停在 Claim 区域时，
     * hovered=true 由 MapElementRenderHandler 重新渲染传入。
     * 仅在此通道绘制 Colony 信息 tooltip，
     * 数据全部来自 ClaimMapElement（源头 ClientColonyCache）。
     */
    if(hovered){
      drawColonyTooltip(
          element,
          guiGraphics,
          font,
          mouseY,
          rotation
      );
    }

    return true;
  }

  /*
   * Colony 信息 tooltip（仅 GuiGraphics 绘制，无 GUI/点击）。
   * PoseStack 已平移到锚点屏幕位置，
   * subX/subZ 为锚点亚像素偏移，tooltip 绘制在锚点旁。
   * 内容由 tooltipMode 控制：OFF 不绘制，
   * SIMPLE 仅名称，DETAILED 标题 + 全部字段。
   * 半透明黑色背景 + 黄色标题 + 灰字段名/白字段值；
   * 行距 = 字体行高 + TOOLTIP_LINE_GAP。
   * 文字通过 PoseStack 缩放 TOOLTIP_TEXT_SCALE 倍放大，
   * 绘制坐标同步除以缩放值换算回缩放前坐标系。
   */
  private void drawColonyTooltip(
      ClaimMapElement element,
      GuiGraphics guiGraphics,
      net.minecraft.client.gui.Font font,
      double subX,
      double subZ
  ){
    if(guiGraphics == null || element == null || font == null)
      return;
    if(tooltipMode == TooltipMode.OFF)
      return;

    List<TooltipLine> lines = new ArrayList<>();
    switch(tooltipMode){
      /*
       * 简单模式：仅显示殖民地名称（黄色标题色）
       */
      case SIMPLE -> lines.add(new TooltipLine(
          element.getColonyName(),
          "",
          TOOLTIP_TITLE_COLOR,
          TOOLTIP_TITLE_COLOR
      ));
      /*
       * 详细模式：首行标题（Colony: 名称），其余为详情字段
       */
      case DETAILED -> {
        lines.add(new TooltipLine(
            "",
            element.getColonyName(),
            TOOLTIP_TITLE_COLOR,
            TOOLTIP_TITLE_COLOR
        ));
        lines.add(new TooltipLine(
            "ID: ",
            String.valueOf(element.getColonyId()),
            TOOLTIP_LABEL_COLOR,
            TOOLTIP_VALUE_COLOR
        ));
        lines.add(new TooltipLine(
            "维度: ",
            prettyDimension(
                element.getDimension() != null
                    ? element.getDimension().location()
                    : null
            ),
            TOOLTIP_LABEL_COLOR,
            TOOLTIP_VALUE_COLOR
        ));
        lines.add(new TooltipLine(
            "中心点: ",
            element.getCenter() != null
                ? element.getCenter().getX()
                    + ", " + element.getCenter().getY()
                    + ", " + element.getCenter().getZ()
                : "unknown",
            TOOLTIP_LABEL_COLOR,
            TOOLTIP_VALUE_COLOR
        ));
        lines.add(new TooltipLine(
            "大小/块: ",
            String.valueOf(element.getClaimCount()),
            TOOLTIP_LABEL_COLOR,
            TOOLTIP_VALUE_COLOR
        ));
      }
      default -> { return; }
    }

    int lineHeight = font.lineHeight + TOOLTIP_LINE_GAP;
    int padding = 6;
    int boxWidth = 0;
    for(TooltipLine line : lines){
      int w =
          (int) Math.ceil(
              (font.width(line.label()) + font.width(line.value()))
                  * TOOLTIP_TEXT_SCALE
          );
      if(w > boxWidth) boxWidth = w;
    }
    boxWidth += padding * 2;
    int contentHeight = lines.size() * lineHeight;
    int boxHeight = contentHeight + padding * 2;

    int x0 = (int) subX + 12;
    int y0 = (int) subZ + 12;

    /*
     * 对齐到缩放倍数的整数像素，避免缩放后出现半像素偏移导致模糊。
     */
    int align = (int) TOOLTIP_TEXT_SCALE;
    if((x0 + padding) % align != 0)
      x0 += align - (x0 + padding) % align;
    if((y0 + padding) % align != 0)
      y0 += align - (y0 + padding) % align;

    guiGraphics.fill(
        x0, y0,
        x0 + boxWidth, y0 + boxHeight,
        TOOLTIP_BG_COLOR
    );
    /*
     * 四边描边
     */
    guiGraphics.fill(x0, y0, x0 + boxWidth, y0 + 1, TOOLTIP_BORDER_COLOR);
    guiGraphics.fill(x0, y0 + boxHeight - 1, x0 + boxWidth, y0 + boxHeight, TOOLTIP_BORDER_COLOR);
    guiGraphics.fill(x0, y0, x0 + 1, y0 + boxHeight, TOOLTIP_BORDER_COLOR);
    guiGraphics.fill(x0 + boxWidth - 1, y0, x0 + boxWidth, y0 + boxHeight, TOOLTIP_BORDER_COLOR);

    /*
     * 文字绘制前压入缩放矩阵，绘制坐标除以缩放值，
     * 保证最终屏幕位置与未缩放时一致。
     */
    PoseStack pose = guiGraphics.pose();
    pose.pushPose();
    pose.scale(TOOLTIP_TEXT_SCALE, TOOLTIP_TEXT_SCALE, 1.0F);
    try{
      int textX = (int) ((x0 + padding) / TOOLTIP_TEXT_SCALE);
      for(int i = 0; i < lines.size(); i++){
        TooltipLine line = lines.get(i);
        int textY =
            (int) ((y0 + padding + i * lineHeight) / TOOLTIP_TEXT_SCALE);
        /*
         * 字段名与值分两段绘制，值紧跟在字段名之后。
         */
        guiGraphics.drawString(
            font,
            line.label(),
            textX,
            textY,
            line.labelColor(),
            false
        );
        guiGraphics.drawString(
            font,
            line.value(),
            textX + font.width(line.label()),
            textY,
            line.valueColor(),
            false
        );
      }
    }finally{
      pose.popPose();
    }
  }

  /*
   * Dimension 显示名美化：仅转换三个原版维度，
   * 其它维度保持原始 ResourceLocation 字符串。
   */
  private static String prettyDimension(ResourceLocation location){
    if(location == null)
      return "unknown";
    return switch(location.toString()){
      case "minecraft:overworld" -> "Overworld";
      case "minecraft:the_nether" -> "Nether";
      case "minecraft:the_end" -> "The End";
      default -> location.toString();
    };
  }

  /*
   * 统一绘制入口（主通道与阴影通道共用）。
   * 第一遍：内部填充半透明蓝色。
   * 第二遍：只绘制 Claim 区域最外层边框（压在填充之上），
   * 某条边仅在相邻方向不存在 Claim 时才绘制。
   * 绘制局部坐标 = 亚像素偏移 + (Chunk 世界坐标 - 锚点世界坐标) * 缩放。
   * ChunkPos -> 世界坐标：chunkX * 16 / chunkZ * 16。
   * 锚点必须与 ClaimElementReader.getRenderX/Z 一致（Claims 中心）。
   */
  private void drawClaims(
      ClaimMapElement element,
      GuiGraphics guiGraphics,
      double subX,
      double subZ,
      double zoom
  ){
    if(guiGraphics == null || element == null)
      return;
    if(element.getClaims().isEmpty())
      return;

    double anchorX = element.getCenterBlockX();
    double anchorZ = element.getCenterBlockZ();

    Set<ChunkPos> claims = element.getClaims();

    /*
     * 第一遍：半透明蓝色填充
     */
    for(ChunkPos chunk : claims){
      double wx = chunk.x * 16.0;
      double wz = chunk.z * 16.0;

      double px = subX + (wx - anchorX) * zoom;
      double py = subZ + (wz - anchorZ) * zoom;
      double size = 16.0 * zoom;
      if(size < 1) size = 1;

      int x0 = (int) px;
      int y0 = (int) py;
      int x1 = (int) (px + size);
      int y1 = (int) (py + size);

      guiGraphics.fill(
          x0, y0,
          x1, y1,
          FILL_COLOR
      );
    }

    /*
     * 第二遍：最外层边框
     */
    for(ChunkPos chunk : claims){
      double wx = chunk.x * 16.0;
      double wz = chunk.z * 16.0;

      double px = subX + (wx - anchorX) * zoom;
      double py = subZ + (wz - anchorZ) * zoom;
      double size = 16.0 * zoom;
      if(size < 1) size = 1;

      boolean drawTop =
          !claims.contains(new ChunkPos(chunk.x, chunk.z - 1));
      boolean drawBottom =
          !claims.contains(new ChunkPos(chunk.x, chunk.z + 1));
      boolean drawLeft =
          !claims.contains(new ChunkPos(chunk.x - 1, chunk.z));
      boolean drawRight =
          !claims.contains(new ChunkPos(chunk.x + 1, chunk.z));

      if(!drawTop && !drawBottom
          && !drawLeft && !drawRight){
        continue;
      }

      int x0 = (int) px;
      int y0 = (int) py;
      int x1 = (int) (px + size);
      int y1 = (int) (py + size);

      /*
       * top
       */
      if(drawTop){
        guiGraphics.fill(
            x0, y0,
            x1, y0 + 1,
            BORDER_COLOR
        );
      }
      /*
       * bottom
       */
      if(drawBottom){
        guiGraphics.fill(
            x0, y1 - 1,
            x1, y1,
            BORDER_COLOR
        );
      }
      /*
       * left
       */
      if(drawLeft){
        guiGraphics.fill(
            x0, y0,
            x0 + 1, y1,
            BORDER_COLOR
        );
      }
      /*
       * right
       */
      if(drawRight){
        guiGraphics.fill(
            x1 - 1, y0,
            x1, y1,
            BORDER_COLOR
        );
      }
    }
  }
}
