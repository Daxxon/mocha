package mocha.game;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;

import mocha.game.rule.GameRule;
import mocha.game.world.Location;
import mocha.game.world.World;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.entity.Entity;

public class Game implements Tickable {

  private World world;
  private List<GameRule> gameRules;
  protected Registry<Entity> registry;

  public Game(World world, List<GameRule> gameRules, Registry<Entity> registry) {
    this.world = world;
    this.gameRules = gameRules;
    this.registry = registry;
  }

  public void tick(long now) {
    gameRules.forEach(gameRule -> gameRule.apply(this));
  }

  public World getWorld() {
    return world;
  }

  public void add(Entity entity) {
    registry.add(entity);
    world.add(entity);
  }

  private List<Chunk> getActiveChunks() {
    Location playerLocation = Location.at(0,0);
    List<Chunk> activeChunks = Lists.newLinkedList();
    for (int y = -1; y <= 1; y++) {
      for (int x = -1; x <= 1; x++) {
        Location chunkLocation = playerLocation.addNew(x * Chunk.getWidth(), y * Chunk.getHeight());
        world.getChunkAt(chunkLocation).ifPresent(activeChunks::add);
      }
    }
    return activeChunks;
  }

  public List<Entity> getActiveEntities() {
    return world.getChunks().stream()
        .map(Chunk::getEntities)
        .flatMap(List::stream)
        .collect(Collectors.toList());
  }

  public Registry<Entity> getEntityRegistry() {
    return registry;
  }
}
