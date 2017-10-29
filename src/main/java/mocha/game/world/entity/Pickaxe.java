package mocha.game.world.entity;

import mocha.game.world.entity.brain.Brain;
import mocha.game.world.entity.movement.Movement;

public class Pickaxe extends Entity {
  public Pickaxe(Movement movement, Brain brain) {
    super(movement, brain);
  }

  @Override
  public int getSpriteId() {
    return 100;
  }
}