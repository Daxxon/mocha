package mocha.game.world.map;

import com.google.common.collect.Sets;

import org.springframework.stereotype.Component;

import mocha.game.world.tile.Tile;
import mocha.game.world.tile.TileType;

@Component
public class MapFactory {

  private static int mapId = 0;

  public Map newDefault() {
    return newDefault(18, 12);
  }

  public Map newDefault(int columns, int rows) {
    return Map.builder()
        .tiles(createTiles(columns, rows))
        .entities(Sets.newIdentityHashSet())
        .id(mapId++)
        .build();
  }

  private Tile[][] createTiles(int columns, int rows) {
    Tile[][] tiles = new Tile[rows][columns];

    for (int y = 0; y < rows; y++) {
      for (int x = 0; x < columns; x++) {
        boolean isWater = x + y % (mapId + 1) == 0;
        tiles[y][x] = Tile.builder()
            .tileType(isWater ? TileType.WATER : TileType.DIRT)
            .build();
      }
    }
    return tiles;
  }
}