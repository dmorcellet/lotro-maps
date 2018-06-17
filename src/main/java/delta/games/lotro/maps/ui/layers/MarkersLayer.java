package delta.games.lotro.maps.ui.layers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.List;

import delta.common.ui.swing.draw.HaloPainter;
import delta.common.utils.collections.filters.Filter;
import delta.games.lotro.maps.data.GeoReference;
import delta.games.lotro.maps.data.MapBundle;
import delta.games.lotro.maps.data.MapsManager;
import delta.games.lotro.maps.data.Marker;
import delta.games.lotro.maps.data.MarkersManager;
import delta.games.lotro.maps.ui.DefaultMarkerIconsProvider;
import delta.games.lotro.maps.ui.MapView;
import delta.games.lotro.maps.ui.MarkerIconProvider;

/**
 * Layer for markers.
 * @author DAM
 */
public class MarkersLayer implements Layer
{
  private MapView _view;
  private Filter<Marker> _filter;
  private boolean _useLabels;
  private MarkerIconProvider _iconProvider;

  /**
   * Constructor.
   * @param mapsManager Maps manager.
   * @param view Map view.
   */
  public MarkersLayer(MapsManager mapsManager, MapView view)
  {
    _view=view;
    _filter=null;
    _useLabels=false;
    _iconProvider=new DefaultMarkerIconsProvider(mapsManager);
  }

  @Override
  public int getPriority()
  {
    return 50;
  }

  @Override
  public List<Marker> getVisibleMarkers()
  {
    MapBundle map=_view.getCurrentMap();
    if (map!=null)
    {
      MarkersManager markersManager=map.getData();
      return markersManager.getMarkers(_filter);
    }
    return null;
  }

  /**
   * Set a filter.
   * @param filter Filter to set or <code>null</code> to remove it.
   */
  public void setFilter(Filter<Marker> filter)
  {
    _filter=filter;
  }

  /**
   * Set the flag to use labels or not.
   * @param useLabels <code>true</code> to display labels, <code>false</code> to hide them.
   */
  public void useLabels(boolean useLabels)
  {
    _useLabels=useLabels;
  }

  /**
   * Paint the markers.
   * @param g Graphics.
   */
  @Override
  public void paintLayer(Graphics g)
  {
    MapBundle map=_view.getCurrentMap();
    if (map!=null)
    {
      MarkersManager markersManager=map.getData();
      List<Marker> markers=markersManager.getAllMarkers();
      for(Marker marker : markers)
      {
        boolean ok=((_filter==null)||(_filter.accept(marker)));
        if (ok)
        {
          paintMarker(marker,g);
        }
      }
    }
  }

  private void paintMarker(Marker marker, Graphics g)
  {
    GeoReference viewReference=_view.getViewReference();
    Dimension pixelPosition=viewReference.geo2pixel(marker.getPosition());

    // Grab icon
    BufferedImage image=null;
    if (_iconProvider!=null)
    {
      image=_iconProvider.getImage(marker);
    }
    int x=pixelPosition.width;
    int y=pixelPosition.height;
    if (image!=null)
    {
      int width=image.getWidth();
      int height=image.getHeight();
      g.drawImage(image,x-width/2,y-height/2,null);
    }
    // Label
    if (_useLabels)
    {
      String label=marker.getLabel();
      if ((label!=null) && (label.length()>0))
      {
        HaloPainter.drawStringWithHalo(g,x+10,y,label,Color.WHITE,Color.BLACK);
      }
    }
  }
}
