package mocha.game.world.map;


import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


public class MapIOTest {

  private MapIO testObject;

  private File dataDirectory;
  private File mapsDirectory;

  @Before
  public void setUp() throws IOException {
    testObject = new MapIO();

    dataDirectory = new File("data");
    FileUtils.deleteDirectory(dataDirectory);
    mapsDirectory = new File(dataDirectory, "maps");
  }

  @Test
  public void saveMap_CreatesAFileOnDiskRepresentingTheSavedMap() {
    Map map = new Map(12, 10, 10);

    testObject.saveMap(map);

    File mapFile = new File(mapsDirectory, "12.map");
    assertTrue(mapFile.exists());
  }

  @Test
  public void saveMap_CreatesAMapDirectoryIfItDoesNotExist() throws IOException {
    Map map = new Map(12, 10, 10);
    assertFalse(mapsDirectory.exists());

    testObject.saveMap(map);

    assertTrue(mapsDirectory.exists());
  }

}