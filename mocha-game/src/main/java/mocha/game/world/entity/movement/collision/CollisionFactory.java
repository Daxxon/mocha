package mocha.game.world.entity.movement.collision;

import mocha.game.Game;
import mocha.game.world.entity.Entity;

public class CollisionFactory {

  private Game game;

  public CollisionFactory(Game game) {
    this.game = game;
  }

  public SimpleCollision newSimpleCollision() {
    return new SimpleCollision();
  }

  private TileCollision newTileCollision() {
    return new TileCollision(game.getWorld());
  }

  public HitBoxCollision newTileHitBoxCollision(int width, int height) {
    return new HitBoxCollision(newTileCollision(), width, height);
  }

  public EntityCollision newEntityCollision(Entity entity, int width, int height) {
    return new EntityCollision(game, entity, width, height);
  }

  public HitBoxCollision newEntityHitBoxCollision(Entity entity, int width, int height) {
    return new HitBoxCollision(newEntityTileCollision(entity, width, height), width, height);
  }

  private Collision newEntityTileCollision(Entity entity, int width, int height) {
    return location -> newTileCollision().collides(location) || newEntityCollision(entity, width, height).collides(location);
  }
}
