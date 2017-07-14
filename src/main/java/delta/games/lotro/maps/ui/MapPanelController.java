package delta.games.lotro.maps.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import delta.common.ui.swing.GuiFactory;
import delta.common.utils.ListenersManager;
import delta.games.lotro.maps.data.Map;
import delta.games.lotro.maps.data.MapsManager;
import delta.games.lotro.maps.ui.location.MapLocationController;
import delta.games.lotro.maps.ui.location.MapLocationPanelController;

/**
 * Controller for a map panel.
 * <p>This includes:
 * <ul>
 * <li>a map canvas,
 * <li>a location display,
 * <li>navigation between maps.
 * </ul>
 * @author DAM
 */
public class MapPanelController implements NavigationListener
{
  // Data
  private MapCanvas _canvas;
  // Controllers
  private MapLocationController _locationController;
  private MapLocationPanelController _locationDisplay;
  private NavigationManager _navigation;
  // Listeners
  private ListenersManager<NavigationListener> _listeners;
  // UI
  private JLayeredPane _layers;
  private JCheckBox _labeled;

  /**
   * Constructor.
   * @param mapsManager Maps manager.
   */
  public MapPanelController(MapsManager mapsManager)
  {
    _canvas=new MapCanvas(mapsManager);
    // Location
    _locationController=new MapLocationController(_canvas);
    _locationDisplay=new MapLocationPanelController();
    _locationController.addListener(_locationDisplay);
    // Assembly components in a layered pane
    _layers=new JLayeredPane();
    // - canvas
    _layers.add(_canvas,Integer.valueOf(0),0);
    _canvas.setLocation(0,0);
    // - location
    JPanel locationPanel=_locationDisplay.getPanel();
    _layers.add(locationPanel,Integer.valueOf(1),0);
    // - labeled checkbox
    _labeled=buildLabeledCheckbox();
    _layers.add(_labeled,Integer.valueOf(1),0);
    // Navigation support
    _navigation=new NavigationManager(_canvas);
    _navigation.setNavigationListener(this);
    _listeners=new ListenersManager<NavigationListener>();
  }

  private JCheckBox buildLabeledCheckbox()
  {
    final JCheckBox labeled=GuiFactory.buildCheckbox("Labeled");
    labeled.setFocusPainted(false);
    labeled.setForeground(Color.WHITE);
    ActionListener al=new ActionListener()
    {
      public void actionPerformed(ActionEvent e)
      {
        _canvas.useLabels(labeled.isSelected());
        _canvas.repaint();
      }
    };
    labeled.addActionListener(al);
    return labeled;
  }

  /**
   * Get the managed canvas.
   * @return the managed canvas.
   */
  public MapCanvas getCanvas()
  {
    return _canvas;
  }

  /**
   * Get the managed layered pane.
   * @return the managed layered pane.
   */
  public JLayeredPane getLayers()
  {
    return _layers;
  }

  /**
   * Get the 'navigation' listeners manager.
   * @return A listeners manager.
   */
  public ListenersManager<NavigationListener> getListenersManager()
  {
    return _listeners;
  }

  public void mapChangeRequest(String key)
  {
    setMap(key);
    for(NavigationListener listener : _listeners)
    {
      listener.mapChangeRequest(key);
    }
  }

  /**
   * Set the map to display.
   * @param key
   */
  public void setMap(String key)
  {
    _canvas.setMap(key);
    Map map=_canvas.getMap();
    _locationController.setMap(map);
    _navigation.setMap(map);
    // Set map size
    Dimension size=_canvas.getPreferredSize();
    _canvas.setSize(size);
    _layers.setPreferredSize(size);
    int height=size.height;
    // Place location display (lower left)
    JPanel locationPanel=_locationDisplay.getPanel();
    locationPanel.setSize(100,40);
    locationPanel.setLocation(0,height-locationPanel.getHeight());
    // Place 'labeled' checkbox
    _labeled.setLocation(55,17);
    _labeled.setSize(_labeled.getPreferredSize());
  }

  /**
   * Release all managed resources.
   */
  public void dispose()
  {
    _listeners=null;
    if (_navigation!=null)
    {
      _navigation.dispose();
      _navigation=null;
    }
    if (_locationDisplay!=null)
    {
      if (_locationController!=null)
      {
        _locationController.removeListener(_locationDisplay);
      }
      _locationDisplay.dispose();
      _locationDisplay=null;
    }
    if (_locationController!=null)
    {
      _locationController.dispose();
      _locationController=null;
    }
    _canvas=null;
    _layers=null;
    _labeled=null;
  }
}
