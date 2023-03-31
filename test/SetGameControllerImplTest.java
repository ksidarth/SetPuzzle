import org.junit.Test;

import java.io.Reader;
import java.io.StringReader;


import MVC.controller.SetGameController;
import MVC.controller.SetGameControllerImpl;
import MVC.model.GeneralSetGameModel;
import MVC.view.SetGameTextView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests the SetGameControllerImpl class.
 */
public class SetGameControllerImplTest {
  private GeneralSetGameModel model;
  private SetGameTextView view;
  private StringBuilder s;
  private SetGameController controller;
  private Reader r;

  /**
   * Initializes testing variables.
   *
   * @param string - input for the game.
   */
  public void init(String string) {
    this.model = new GeneralSetGameModel();
    this.s = new StringBuilder();
    this.view = new SetGameTextView(this.model, this.s);
    r = new StringReader(string);
    controller = new SetGameControllerImpl(model, view, r);
  }

  @Test
  public void testValidConstruction() {
    this.model = new GeneralSetGameModel();
    this.s = new StringBuilder();
    this.view = new SetGameTextView(this.model, this.s);
    r = new StringReader("test");
    SetGameControllerImpl testController = new SetGameControllerImpl(model, view, r);
    assertTrue(testController instanceof SetGameControllerImpl);
  }

  @Test
  public void testInvalidConstruction() {
    init("");
    // Misc tests for fields being null
    try {
      new SetGameControllerImpl(null, view, r);
      new SetGameControllerImpl(model, null, null);
      new SetGameControllerImpl(model, view, null);
      new SetGameControllerImpl(model, null, null);
      new SetGameControllerImpl(null, view, null);
    } catch (IllegalArgumentException e) {
      // The exception was caught
      assertNotEquals(model, s);
    }
  }

  @Test
  public void testIllegalStateException() {
    // No input after height/width
    init("3 3 ");
    try {
      controller.playGame();
    } catch (IllegalStateException e) {
      assertEquals(s.toString(), """
            Enter your move as a 3 different coordinates, separated by spaces.\s
            1EO 1EQ 1ED
            1SO 1SQ 1SD
            1FO 1FQ 1FD
            Score: 0
            Game quit!
            State of game when quit:
            Enter your move as a 3 different coordinates, separated by spaces.\s
            1EO 1EQ 1ED
            1SO 1SQ 1SD
            1FO 1FQ 1FD
            Score: 0""");
    }
    // Valid move ends halfway
    init("3 3 1 1 1 3 ");
    try {
      controller.playGame();
    } catch (IllegalStateException e) {
      assertEquals(s.toString(), """
            1EO 1EQ 1ED
            1SO 1SQ 1SD
            1FO 1FQ 1FD
            Score: 0
            """);
    }
    // Invalid move ends halfway
    init("3 3 1 e 1 3 ");
    try {
      controller.playGame();
    } catch (IllegalStateException e) {
      assertEquals(s.toString(), """
            1EO 1EQ 1ED
            1SO 1SQ 1SD
            1FO 1FQ 1FD
            Score: 0
            Invalid value entered. Try Again.
            """);
    }
    // Valid move ends with nothing
    init("3 3 1 1 1 2 1 3 ");
    try {
      controller.playGame();
    } catch (IllegalStateException e) {
      // Exception was caught
    }
  }

  @Test
  public void testInvalidWidthHeight() {
    // Width invalid
    init("1 2 q");
    controller.playGame();
    assertEquals(s.toString(), """
          Invalid height/width. Try again. Height and width must be correct values.
          Game quit!
          Score: 0""");
    // Height invalid
    init("-1 2 q");
    controller.playGame();
    assertEquals(s.toString(), """
          Invalid height/width. Try again. Height and width must be correct values.
          Game quit!
          Score: 0""");
    // Both invalid
    init("2 e q");
    controller.playGame();
    assertEquals(s.toString(), """
          Invalid height/width. Try again. Height and width must be correct values.
          Game quit!
          Score: 0""");
  }

  @Test
  public void testQuitBefore() {
    init("q");
    controller.playGame();
    assertEquals(s.toString(), "Game quit!\nScore: 0");
    // Test quitting after declaring only height
    init("3 q 3");
    controller.playGame();
    assertEquals(s.toString(), "Game quit!\nScore: 0");
  }

  @Test
  public void testQuitAfterGameStart() {
    // Test quitting after declaring height and width
    init("3 3 q");
    controller.playGame();
    assertEquals(s.toString(), """
          Enter your move as a 3 different coordinates, separated by spaces.\s
          1EO 1EQ 1ED
          1SO 1SQ 1SD
          1FO 1FQ 1FD
          Score: 0
          Game quit!
          State of game when quit:
          Enter your move as a 3 different coordinates, separated by spaces.\s
          1EO 1EQ 1ED
          1SO 1SQ 1SD
          1FO 1FQ 1FD
          Score: 0""");
    // Test quitting after one valid move
    init("3 3 1 1 1 2 1 3 q");
    controller.playGame();
    assertEquals(s.toString(), """
          1EO 1EQ 1ED
          1SO 1SQ 1SD
          1FO 1FQ 1FD
          Score: 0
          2EO 2EQ 2ED
          1SO 1SQ 1SD
          1FO 1FQ 1FD
          Score: 1
          Game quit!
          State of game when quit:
          2EO 2EQ 2ED
          1SO 1SQ 1SD
          1FO 1FQ 1FD
          Score: 1""");
    // Test quitting midway through valid move
    init("3 3 1 2 1 3 q 3");
    controller.playGame();
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
          Score: 0""");
    // Test quitting after 2 moves
    init("3 3 1 1 1 2 1 3 " + "3 1 3 2 3 3 Q");
    controller.playGame();
    assertEquals(s.toString(), """
          1EO 1EQ 1ED
          1SO 1SQ 1SD
          1FO 1FQ 1FD
          Score: 0
          2EO 2EQ 2ED
          1SO 1SQ 1SD
          1FO 1FQ 1FD
          Score: 1
          2EO 2EQ 2ED
          1SO 1SQ 1SD
          2SO 2SQ 2SD
          Score: 2
          Game quit!
          State of game when quit:
          2EO 2EQ 2ED
          1SO 1SQ 1SD
          2SO 2SQ 2SD
          Score: 2""");
  }

  @Test
  public void testMiscQuits() {
    // Test quitting after an invalid value
    init("3 3 1 1 1 2 1 3 " + "3 1 e q 3 3");
    controller.playGame();
    assertEquals(s.toString(), """
          Enter your move as a 3 different coordinates, separated by spaces.\s
          1EO 1EQ 1ED
          1SO 1SQ 1SD
          1FO 1FQ 1FD
          Score: 0
          Correct claim!
          Enter your move as a 3 different coordinates, separated by spaces.\s
          2EO 2EQ 2ED
          1SO 1SQ 1SD
          1FO 1FQ 1FD
          Score: 1
          Invalid value entered. Try Again.
          Game quit!
          State of game when quit:
          Enter your move as a 3 different coordinates, separated by spaces.\s
          2EO 2EQ 2ED
          1SO 1SQ 1SD
          1FO 1FQ 1FD
          Score: 1""");
    // test quitting after game is finished
    init("3 3 1 1 1 2 1 3 " + "1 1 1 2 1 3 " + "1 1 1 2 1 3 " + "1 1 1 2 1 3 " + "1 1 1 2 1 3 " +
          "1 1 1 2 1 3 " + "1 1 1 2 1 3 q");
    controller.playGame();
    assertEquals(s.toString(), """
          Enter your move as a 3 different coordinates, separated by spaces.\s
          1EO 1EQ 1ED
          1SO 1SQ 1SD
          1FO 1FQ 1FD
          Score: 0
          Correct claim!
          Enter your move as a 3 different coordinates, separated by spaces.\s
          2EO 2EQ 2ED
          1SO 1SQ 1SD
          1FO 1FQ 1FD
          Score: 1
          Correct claim!
          Enter your move as a 3 different coordinates, separated by spaces.\s
          2SO 2SQ 2SD
          1SO 1SQ 1SD
          1FO 1FQ 1FD
          Score: 2
          Correct claim!
          Enter your move as a 3 different coordinates, separated by spaces.\s
          2FO 2FQ 2FD
          1SO 1SQ 1SD
          1FO 1FQ 1FD
          Score: 3
          Correct claim!
          Enter your move as a 3 different coordinates, separated by spaces.\s
          3EO 3EQ 3ED
          1SO 1SQ 1SD
          1FO 1FQ 1FD
          Score: 4
          Correct claim!
          Enter your move as a 3 different coordinates, separated by spaces.\s
          3SO 3SQ 3SD
          1SO 1SQ 1SD
          1FO 1FQ 1FD
          Score: 5
          Correct claim!
          Enter your move as a 3 different coordinates, separated by spaces.\s
          3FO 3FQ 3FD
          1SO 1SQ 1SD
          1FO 1FQ 1FD
          Score: 6
          Correct claim!
          Enter your move as a 3 different coordinates, separated by spaces.\s
          3FO 3FQ 3FD
          1SO 1SQ 1SD
          1FO 1FQ 1FD
          Score: 7
          Game over!
          Score: 7""");
  }

  @Test
  public void testGameOver() {
    // Test 1 valid move
    init("3 3 1 1 1 2 1 3 q");
    controller.playGame();
    assertEquals(s.toString(), """
          Enter your move as a 3 different coordinates, separated by spaces.\s
          1EO 1EQ 1ED
          1SO 1SQ 1SD
          1FO 1FQ 1FD
          Score: 0
          Correct claim!
          Enter your move as a 3 different coordinates, separated by spaces.\s
          2EO 2EQ 2ED
          1SO 1SQ 1SD
          1FO 1FQ 1FD
          Score: 1
          Game quit!
          State of game when quit:
          Enter your move as a 3 different coordinates, separated by spaces.\s
          2EO 2EQ 2ED
          1SO 1SQ 1SD
          1FO 1FQ 1FD
          Score: 1""");
    // Test playing game to completion
    init("3 3 1 1 1 2 1 3 " + "1 1 1 2 1 3 " + "1 1 1 2 1 3 " + "1 1 1 2 1 3 " + "1 1 1 2 1 3 " +
          "1 1 1 2 1 3 " + "1 1 1 2 1 3 ");
    controller.playGame();
    assertEquals(s.toString(), """
          Enter your move as a 3 different coordinates, separated by spaces.\s
          1EO 1EQ 1ED
          1SO 1SQ 1SD
          1FO 1FQ 1FD
          Score: 0
          Correct claim!
          Enter your move as a 3 different coordinates, separated by spaces.\s
          2EO 2EQ 2ED
          1SO 1SQ 1SD
          1FO 1FQ 1FD
          Score: 1
          Correct claim!
          Enter your move as a 3 different coordinates, separated by spaces.\s
          2SO 2SQ 2SD
          1SO 1SQ 1SD
          1FO 1FQ 1FD
          Score: 2
          Correct claim!
          Enter your move as a 3 different coordinates, separated by spaces.\s
          2FO 2FQ 2FD
          1SO 1SQ 1SD
          1FO 1FQ 1FD
          Score: 3
          Correct claim!
          Enter your move as a 3 different coordinates, separated by spaces.\s
          3EO 3EQ 3ED
          1SO 1SQ 1SD
          1FO 1FQ 1FD
          Score: 4
          Correct claim!
          Enter your move as a 3 different coordinates, separated by spaces.\s
          3SO 3SQ 3SD
          1SO 1SQ 1SD
          1FO 1FQ 1FD
          Score: 5
          Correct claim!
          Enter your move as a 3 different coordinates, separated by spaces.\s
          3FO 3FQ 3FD
          1SO 1SQ 1SD
          1FO 1FQ 1FD
          Score: 6
          Correct claim!
          Enter your move as a 3 different coordinates, separated by spaces.\s
          3FO 3FQ 3FD
          1SO 1SQ 1SD
          1FO 1FQ 1FD
          Score: 7
          Game over!
          Score: 7""");
    // Test winning game while making invalid moves
    init("3 3 1 1 1 string 1 3 " + "1 fs 1 2 1 3 " + "1 1 3 2 3 3 " + "1 1 1 e 1 3 " +
          "1 1 1 2 1 3 " + "1 1 1 2 1 3 " + "1 1 1 2 1 3 " + "1 1 1 2 1 3 " + "1 p 1 2 1 3 " +
          "2 1 2 2 2 3 " + "3 1 3 2 3 3 " + "3 1 3 2 3 3 " + "3 1 3 2 3 3 " + "3 1 3 2 3 3 " +
          "3 1 3 2 3 3 " + "3 1 3 2 3 3 " + "3 1 3 2 3 3 ");
    controller.playGame();
    assertEquals(s.toString(), """
          Enter your move as a 3 different coordinates, separated by spaces.\s
          1EO 1EQ 1ED
          1SO 1SQ 1SD
          1FO 1FQ 1FD
          Score: 0
          Invalid value entered. Try Again.
          Invalid claim. Try again.
          Invalid value entered. Try Again.
          Correct claim!
          Enter your move as a 3 different coordinates, separated by spaces.\s
          2ED 2EO 2EQ
          1SO 1SQ 1SD
          1FO 1FQ 1FD
          Score: 1
          Invalid claim. Try again.
          Invalid value entered. Try Again.
          Invalid claim. Try again.
          Invalid claim. Try again.
          Invalid claim. Try again.
          Invalid claim. Try again.
          Invalid value entered. Try Again.
          Invalid claim. Try again.
          Invalid claim. Try again.
          Invalid claim. Try again.
          Correct claim!
          Enter your move as a 3 different coordinates, separated by spaces.\s
          2ED 2EO 2EQ
          1SO 1SQ 1SD
          2SQ 2SD 2SO
          Score: 2
          Correct claim!
          Enter your move as a 3 different coordinates, separated by spaces.\s
          2ED 2EO 2EQ
          1SO 1SQ 1SD
          2FQ 2FD 2FO
          Score: 3
          Correct claim!
          Enter your move as a 3 different coordinates, separated by spaces.\s
          2ED 2EO 2EQ
          1SO 1SQ 1SD
          3EQ 3ED 3EO
          Score: 4
          Correct claim!
          Enter your move as a 3 different coordinates, separated by spaces.\s
          2ED 2EO 2EQ
          1SO 1SQ 1SD
          3SQ 3SD 3SO
          Score: 5
          Correct claim!
          Enter your move as a 3 different coordinates, separated by spaces.\s
          2ED 2EO 2EQ
          1SO 1SQ 1SD
          3FQ 3FD 3FO
          Score: 6
          Correct claim!
          Enter your move as a 3 different coordinates, separated by spaces.\s
          2ED 2EO 2EQ
          1SO 1SQ 1SD
          3FQ 3FD 3FO
          Score: 7
          Game over!
          Score: 7""");
  }
}