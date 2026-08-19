package com.yangtp.minecoloniesxaero.client.xaero;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

import xaero.map.element.MapElementRenderer;
import xaero.map.element.render.ElementRenderInfo;
import xaero.map.element.render.ElementRenderLocation;
import xaero.map.graphics.renderer.multitexture.MultiTextureRenderTypeRendererProvider;


public class ColonyElementRenderer
    extends MapElementRenderer<
    ColonyMapElement,
    ColonyElementContext,
    ColonyElementRenderer
    > {


  private final ColonyElementContext context;


  public ColonyElementRenderer(){

    this(
        new ColonyElementContext(),
        new ColonyElementProvider(),
        new ColonyElementReader()
    );

  }



  private ColonyElementRenderer(
      ColonyElementContext context,
      ColonyElementProvider provider,
      ColonyElementReader reader
  ){

    super(
        context,
        provider,
        reader
    );

    this.context=context;
  }



  /*
   * 控制显示位置
   */
  @Override
  public boolean shouldRender(
      ElementRenderLocation location,
      boolean dimScaled
  ){

    return location == ElementRenderLocation.WORLD_MAP;
  }



  @Override
  public boolean shouldRender(
      int index,
      boolean dimScaled
  ){

    return index ==
        ElementRenderLocation.WORLD_MAP.getIndex();
  }



  /*
   * 渲染开始
   */
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



  /*
   * 渲染结束
   */
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
  public void renderElementPre(
      int index,
      ColonyMapElement element,
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
      ColonyMapElement element,
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

    int px = (int) x;
    int py = (int) y;

    /*
     * 中心标记 - 填充 + 边框
     */
    guiGraphics.fill(
        px - 5, py - 5,
        px + 5, py + 5,
        0xCC3366FF
    );
    guiGraphics.fill(
        px - 6, py - 6,
        px + 6, py + 6,
        0xFF000000 | 0x003366FF
    );

    /*
     * Colony 名称
     */
    if(font != null && element.getName() != null){
      int textWidth =
          font.width(element.getName());
      guiGraphics.drawString(
          font,
          element.getName(),
          px - textWidth / 2,
          py + 8,
          0xFFFFFFFF,
          true
      );
    }

    return true;
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
      ColonyMapElement element,
      boolean hovered,
      float scale,
      double x,
      double z,
      ElementRenderInfo info,
      GuiGraphics guiGraphics,
      MultiBufferSource.BufferSource buffer,
      MultiTextureRenderTypeRendererProvider provider
  ){

  }
}