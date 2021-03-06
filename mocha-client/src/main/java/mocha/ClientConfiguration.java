package mocha;

import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InjectionPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.List;

import mocha.client.event.ClientEventBus;
import mocha.game.Game;
import mocha.game.GameLoop;
import mocha.game.Player;
import mocha.game.event.MochaEventBus;
import mocha.game.rule.GameRule;
import mocha.game.world.Location;
import mocha.game.world.World;
import mocha.game.world.chunk.Chunk;
import mocha.game.world.chunk.ChunkFactory;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.EntityFactory;
import mocha.game.world.entity.movement.MovementFactory;
import mocha.game.world.entity.movement.collision.CollisionFactory;
import mocha.game.world.entity.movement.rule.MovementRule;
import mocha.game.world.entity.rule.PickUpItemsRule;
import mocha.game.world.tile.TileFactory;
import mocha.net.packet.PacketFactory;
import mocha.net.packet.PacketListenerFactory;
import mocha.net.packet.PacketSenderFactory;
import mocha.shared.IdFactory;
import mocha.shared.Registry;
import mocha.shared.task.TaskService;

@Configuration
public class ClientConfiguration {

  @Bean
  public PacketListenerFactory packetListenerFactory(ClientEventBus eventBus) {
    return new PacketListenerFactory(eventBus);
  }

  @Bean
  public PacketFactory packetFactory() {
    return new PacketFactory();
  }

  @Bean
  public World world(ChunkFactory chunkFactory) {
    World world = new World();
    Chunk chunk = chunkFactory.newRandomDefault();
    world.put(new Location(0, 0), chunk);
    return world;
  }

  @Bean
  public CollisionFactory collisionFactory(Game game) {
    return new CollisionFactory(game);
  }

  @Bean
  public MovementFactory movementFactory(CollisionFactory collisionFactory) {
    return new MovementFactory(collisionFactory);
  }

  @Bean
  public IdFactory<Entity> entityIdFactory(Registry<Entity> registry) {
    return new IdFactory<>(registry);
  }

  @Bean
  public EntityFactory entityFactory(MovementFactory movementFactory, IdFactory<Entity> idFactory) {
    return new EntityFactory(movementFactory, idFactory);
  }

  @Bean
  public Registry<Entity> entityRegistry() {
    return new Registry<>();
  }

  @Bean
  public Registry<Player> playerRegistry() {
    return new Registry<>();
  }

  @Bean
  public IdFactory<Player> playerIdFactory(Registry<Player> playerRegistry) {
    return new IdFactory<>(playerRegistry);
  }

  @Bean
  public Game game(MochaEventBus eventBus, World world, List<GameRule> gameRules, Registry<Entity> entityRegistry, Registry<Player> playerRegistry) {
    return new Game(eventBus, world, gameRules, entityRegistry, playerRegistry);
  }

  @Bean
  public GameLoop getGameLoop() {
    return new GameLoop();
  }

  @Bean
  public List<GameRule> getRules(World world, ClientEventBus clientEventBus) {
    MovementRule movementRule = new MovementRule(clientEventBus);
    clientEventBus.register(movementRule);

    PickUpItemsRule pickUpItemsRule = new PickUpItemsRule(clientEventBus);
    clientEventBus.register(pickUpItemsRule);

    return Lists.newArrayList(movementRule, pickUpItemsRule);
  }

  @Bean
  public PacketSenderFactory packetSenderFactory(EventBus eventBus) {
    return new PacketSenderFactory(eventBus);
  }

  @Bean()
  public TaskService taskService() {
    return new TaskService();
  }

  @Bean
  public TileFactory tileFactory() {
    return new TileFactory();
  }

  @Bean
  public ChunkFactory chunkFactory(TileFactory tileFactory) {
    return new ChunkFactory(tileFactory);
  }

  @Bean
  @Scope("prototype")
  Logger logger(InjectionPoint injectionPoint) {
    return LoggerFactory.getLogger(injectionPoint.getMethodParameter().getContainingClass());
  }

}
