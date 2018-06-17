package delta.games.lotro.maps.ui;

import java.io.File;

import delta.games.lotro.maps.data.MapsManager;

/**
 * Simple test to show the maps explorer window.
 * @author DAM
 */
public class MainTestMapWindowController
{
  /**
   * Main method for this test.
   * @param args Not used.
   */
  public static void main(String[] args)
  {
    File rootDir=new File("../lotro-maps-db");
    MapsManager mapsManager=new MapsManager(rootDir);
    mapsManager.load();

    MapWindowController mapWindow=new MapWindowController(mapsManager);
    mapWindow.show();
  }
}
