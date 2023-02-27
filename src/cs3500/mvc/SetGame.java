package cs3500.mvc;

import java.io.InputStreamReader;

import cs3500.mvc.controller.SetGameController;
import cs3500.mvc.controller.SetGameControllerImpl;
import cs3500.mvc.model.Card;
import cs3500.mvc.model.SetGameModel;
import cs3500.mvc.model.SetThreeGameModel;
import cs3500.mvc.model.GeneralSetGameModel;
import cs3500.mvc.view.SetGameTextView;
import cs3500.mvc.view.SetGameView;

/**
 * Runs the SetGame.
 */
public class SetGame {
  /**
   * Main method to run game.
   *
   * @param args - String input.
   */
  public static void main(String[] args) {
    if (args[0].equalsIgnoreCase("general")) {
      System.out.println("Welcome to the Set Puzzle. Enter the height and width of your board.");
      SetGameModel<Card> model = new GeneralSetGameModel();
      Appendable s = System.out;
      SetGameView view = new SetGameTextView(model, s);
      Readable r = new InputStreamReader(System.in);
      SetGameController controller = new SetGameControllerImpl(model, view, r);
      controller.playGame();
    }
    if (args[0].equalsIgnoreCase("three")) {
      System.out.println("SetThreeGame. Solely for testing purposes.");
      SetGameModel<Card> model = new SetThreeGameModel();
      Appendable s = System.out;
      SetGameView view = new SetGameTextView(model, s);
      Readable r = new InputStreamReader(System.in);
      SetGameController controller = new SetGameControllerImpl(model, view, r);
      controller.playGame();
    }
  }
}
