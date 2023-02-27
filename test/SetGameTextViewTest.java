import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;

import cs3500.set.controller.SetGameController;
import cs3500.set.controller.SetGameControllerImpl;
import cs3500.set.model.hw02.SetThreeGameModel;
import cs3500.set.view.SetGameTextView;
import cs3500.set.view.SetGameView;

import static org.junit.Assert.assertEquals;

/**
 * Tests the SetGameTextView class for SetGame.
 */
public class SetGameTextViewTest {
  private SetGameTextView view;
  private StringBuilder s;
  private SetGameController controller;

  /**
   * Initializes testing variables.
   *
   * @param string - Input for playing the game.
   */
  public void init(String string) {
    SetThreeGameModel model = new SetThreeGameModel();
    this.s = new StringBuilder();
    this.view = new SetGameTextView(model, this.s);
    Readable r = new StringReader(string);
    controller = new SetGameControllerImpl(model, view, r);
  }

  @Test
  public void testConstruction() {
    // Valid Constructions
    SetThreeGameModel state = new SetThreeGameModel();
    SetGameView tester = new SetGameTextView(state);
    state.startGameWithDeck(state.getCompleteDeck(), 3, 3);
    assertEquals(tester.toString(), """
          1EO 1EQ 1ED
          1SO 1SQ 1SD
          1FO 1FQ 1FD""");
    // Valid construction with appendable
    state = new SetThreeGameModel();
    tester = new SetGameTextView(state, new StringBuilder());
    state.startGameWithDeck(state.getCompleteDeck(), 3, 3);
    assertEquals(tester.toString(), """
          1EO 1EQ 1ED
          1SO 1SQ 1SD
          1FO 1FQ 1FD""");
  }

  @Test
  public void testRenderMessage() throws IOException {
    init("3 3 q");
    controller.playGame();
    view.renderMessage("testing"); // this should be visible
    assertEquals(s.toString(), """
          1EO 1EQ 1ED
          1SO 1SQ 1SD
          1FO 1FQ 1FD
          Score: 0
          Game quit!
          State of game when quit:
          1EO 1EQ 1ED
          1SO 1SQ 1SD
          1FO 1FQ 1FD
          Score: 0testing"""); // as you can see the testing is visible
  }

  @Test
  public void testRenderGrid() throws IOException {
    init("3 3 q");
    controller.playGame();
    view.renderGrid(); // there should be an extra grid printed
    assertEquals(s.toString(), """
          1EO 1EQ 1ED
          1SO 1SQ 1SD
          1FO 1FQ 1FD
          Score: 0
          Game quit!
          State of game when quit:
          1EO 1EQ 1ED
          1SO 1SQ 1SD
          1FO 1FQ 1FD
          Score: 01EO 1EQ 1ED
          1SO 1SQ 1SD
          1FO 1FQ 1FD""");
  }

  @Test
  public void testToString() {
    SetThreeGameModel game = new SetThreeGameModel();
    game.startGameWithDeck(game.getCompleteDeck(), 3, 3);
    SetGameTextView tester = new SetGameTextView(game);
    assertEquals(tester.toString(), "1ED 2SD 3FD\n1ED 2SD 3FD\n1ED 2SD 3FD");
    game = new SetThreeGameModel();
    game.startGameWithDeck(game.getCompleteDeck(), 3, 3);
  }

}