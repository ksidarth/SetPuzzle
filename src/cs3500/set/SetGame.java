package cs3500.set;

import java.io.InputStreamReader;

import cs3500.set.controller.SetGameController;
import cs3500.set.controller.SetGameControllerImpl;
import cs3500.set.model.hw02.Card;
import cs3500.set.model.hw02.SetGameModel;
import cs3500.set.model.hw02.SetThreeGameModel;
import cs3500.set.model.hw03.GeneralSetGameModel;
import cs3500.set.view.SetGameTextView;
import cs3500.set.view.SetGameView;

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
