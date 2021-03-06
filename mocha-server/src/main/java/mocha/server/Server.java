package mocha.server;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import mocha.net.event.NetworkedMochaEventBus;
import mocha.net.packet.MochaConnection;
import mocha.net.packet.PacketFactory;
import mocha.shared.task.SleepyRunnable;

@Slf4j
@Component
public class Server implements SleepyRunnable {

  private ServerSocket server;

  @Inject
  private NetworkedMochaEventBus eventBus;

  @Inject
  private PacketFactory packetFactory;

  @Inject
  Server(@Value("${mocha.server.port}") int port) throws IOException {
    log.info("Staring server on port {}", port);
    server = new ServerSocket(port);
  }

  @Override
  public void run() {
    awaitConnections();
  }

  private void awaitConnections() {
    while (true) {
      try {
        log.info("Awaiting connections...");
        acceptConnection(server.accept());
        nap();
      } catch (IOException ioexception) {
        log.error("IOException while awaiting connections: ", ioexception);
      }
    }
  }

  private void acceptConnection(Socket socket) {
    log.info("Receiving connection from " + socket.getInetAddress().toString());
    eventBus.postConnectedEvent(new MochaConnection(socket, packetFactory));
  }

}
