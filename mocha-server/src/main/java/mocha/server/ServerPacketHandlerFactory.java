package mocha.server;

import com.google.common.eventbus.EventBus;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import mocha.game.Game;
import mocha.net.MochaConnection;
import mocha.net.packet.PacketFactory;

@Component
public class ServerPacketHandlerFactory {

  @Inject
  private EventBus eventBus;

  @Inject
  private Game game;

  @Inject
  private PacketFactory packetFactory;

  public void newServerPacketHandler(MochaConnection mochaConnection) {
    ServerPacketHandler serverPacketHandler = new ServerPacketHandler(mochaConnection, packetFactory, game);
    eventBus.register(serverPacketHandler);
  }
}