package mocha.client.gfx;

import org.springframework.stereotype.Component;

import javax.inject.Inject;

import javafx.animation.AnimationTimer;
import lombok.extern.slf4j.Slf4j;
import mocha.client.gfx.view.GameView;

@Component
@Slf4j
public class RenderLoop extends AnimationTimer {

  private static final double NANOSECONDS_PER_SECOND = 1000000000.0;
  private static final int FRAMES_PER_SECOND = 60;
  private static final double FRAME_LIFESPAN = NANOSECONDS_PER_SECOND / FRAMES_PER_SECOND;

  @Inject
  private GameView gameView;

  private long last = 0L;
  private int renders;
  private long lastSecond;

  @Override
  public void handle(long now) {
//    printFramesPerSecond(now);
    render(now);
  }

  private void render(long now) {
    if (skipRendering(now)) {
      return;
    }

    gameView.render();
    renders++;

    last = now;
  }

  private void printFramesPerSecond(long now) {
    if (now - lastSecond >= NANOSECONDS_PER_SECOND) {
      log.info("frames per second: {}", renders);
      renders = 0;
      lastSecond = now;
    }
  }

  private boolean skipRendering(long now) {
    return now - last < FRAME_LIFESPAN;
  }
}
