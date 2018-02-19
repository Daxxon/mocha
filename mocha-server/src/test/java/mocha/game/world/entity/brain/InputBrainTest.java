package mocha.game.world.entity.brain;

import com.google.common.eventbus.EventBus;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import mocha.game.InputKey;
import mocha.game.world.entity.Entity;
import mocha.game.world.entity.movement.Movement;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InputBrainTest {

  private InputBrain testObject;

  @Mock
  private Entity entity;

  @Mock
  private Movement movement;

  @Mock
  private EventBus eventBus;

  private long now = 0L;

  @Before
  public void setUp() {
    when(entity.getMovement()).thenReturn(movement);

    testObject = InputBrain.builder()
        .eventBus(eventBus)
        .entity(entity)
        .build();

    resetInput();
  }

  private void resetInput() {
    for (int i = 0; i < 10; i++) {
      Arrays.stream(InputKey.values()).forEach(InputKey::up);
      InputKey.tickAll();
    }
  }

  @Test
  public void onTick_TheBrainIsStill() {
    testObject.tick(now);

    verifyNoMoreInteractions(movement);
  }

  @Test
  public void onTick_TheBrainSteersTheEntityUp_WhenTheUpKeyIsPressed() {
    InputKey.UP.down();
    InputKey.tickAll();

    testObject.tick(now);

    verify(movement).up();
    verifyNoMoreInteractions(movement);
  }

  @Test
  public void onTick_TheBrainSteersTheEntityDown_WhenTheDownKeyIsPressed() {
    InputKey.DOWN.down();
    InputKey.tickAll();

    testObject.tick(now);

    verify(movement).down();
    verifyNoMoreInteractions(movement);
  }

  @Test
  public void onTick_TheBrainSteersTheEntityLeft_WhenTheLeftKeyIsPressed() {
    InputKey.LEFT.down();
    InputKey.tickAll();

    testObject.tick(now);

    verify(movement).left();
    verifyNoMoreInteractions(movement);
  }

  @Test
  public void onTick_TheBrainSteersTheEntityRight_WhenTheRightKeyIsPressed() {
    InputKey.RIGHT.down();
    InputKey.tickAll();

    testObject.tick(now);

    verify(movement).right();
    verifyNoMoreInteractions(movement);
  }

}