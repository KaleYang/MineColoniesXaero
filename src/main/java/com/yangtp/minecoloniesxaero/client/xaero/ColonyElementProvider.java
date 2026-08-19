package com.yangtp.minecoloniesxaero.client.xaero;

import com.yangtp.minecoloniesxaero.client.ClientColonyCache;
import com.yangtp.minecoloniesxaero.client.ColonyData;
import xaero.map.element.MapElementRenderProvider;
import xaero.map.element.render.ElementRenderLocation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ColonyElementProvider
    extends MapElementRenderProvider<ColonyMapElement, ColonyElementContext> {

  private Iterator<ColonyMapElement> iterator;

  private void prepare(
      int location
  ){
    if(location != ElementRenderLocation.WORLD_MAP.getIndex()){
      iterator = List.<ColonyMapElement>of().iterator();
      return;
    }

    Map<Integer, ColonyData> colonies =
        ClientColonyCache.getColonies();

    List<ColonyMapElement> elements =
        new ArrayList<>();

    for(ColonyData data : colonies.values()){
      if(data.getCenter() == null)
        continue;
      if(data.getName() == null)
        continue;
      elements.add(
          new ColonyMapElement(
              data.getId(),
              data.getName(),
              data.getCenter(),
              data.getDimension()
          )
      );
    }

    iterator = elements.iterator();
  }

  @Override
  public void begin(
      int location,
      ColonyElementContext context
  ){
    prepare(location);
  }

  @Override
  public boolean hasNext(
      int location,
      ColonyElementContext context
  ){
    return iterator != null
        && iterator.hasNext();
  }

  @Override
  public ColonyMapElement getNext(
      int location,
      ColonyElementContext context
  ){
    return iterator.next();
  }

  @Override
  public void end(
      int location,
      ColonyElementContext context
  ){
    iterator = null;
  }
}
