package com.yangtp.minecoloniesxaero.client.xaero;


import net.minecraft.client.Minecraft;
import xaero.map.element.MapElementReader;


public class ColonyElementReader extends MapElementReader<ColonyMapElement, ColonyElementContext, ColonyElementRenderer> {
  @Override
  public boolean isHidden(
      ColonyMapElement e,
      ColonyElementContext c
  ){
    return false;
  }

  @Override
  public double getRenderX(
      ColonyMapElement e,
      ColonyElementContext c,
      float scale
  ){
    return e.getCenter().getX();
  }

  @Override
  public double getRenderZ(
      ColonyMapElement e,
      ColonyElementContext c,
      float scale
  ){
    return e.getCenter().getZ();
  }

  @Override
  public int getInteractionBoxLeft(
      ColonyMapElement e,
      ColonyElementContext c,
      float scale
  ){
    return -8;
  }

  @Override
  public int getInteractionBoxRight(
      ColonyMapElement e,
      ColonyElementContext c,
      float scale
  ){
    return 8;
  }

  @Override
  public int getInteractionBoxTop(
      ColonyMapElement e,
      ColonyElementContext c,
      float scale
  ){
    return -8;
  }

  @Override
  public int getInteractionBoxBottom(
      ColonyMapElement e,
      ColonyElementContext c,
      float scale
  ){
    return 8;
  }

  @Override
  public int getRenderBoxLeft(
      ColonyMapElement e,
      ColonyElementContext c,
      float scale
  ){
    return -8;
  }

  @Override
  public int getRenderBoxRight(
      ColonyMapElement e,
      ColonyElementContext c,
      float scale
  ){
    return 8;
  }

  @Override
  public int getRenderBoxTop(
      ColonyMapElement e,
      ColonyElementContext c,
      float scale
  ){
    return -8;
  }

  @Override
  public int getRenderBoxBottom(
      ColonyMapElement e,
      ColonyElementContext c,
      float scale
  ){
    return 8;
  }

  @Override
  public int getLeftSideLength(
      ColonyMapElement e,
      Minecraft minecraft
  ){
    return 20;
  }

  @Override
  public String getMenuName(
      ColonyMapElement e
  ){
    return e.getName();
  }

  @Override
  public String getFilterName(
      ColonyMapElement e
  ){
    return e.getName();
  }

  @Override
  public int getMenuTextFillLeftPadding(
      ColonyMapElement e
  ){
    return 0;
  }

  @Override
  public int getRightClickTitleBackgroundColor(
      ColonyMapElement e
  ){
    return 0;
  }

  @Override
  public boolean shouldScaleBoxWithOptionalScale(){
    return false;
  }
}
