package MVC;

import java.io.InputStreamReader;

import MVC.view.SetGameTextView;
import MVC.controller.SetGameController;
import MVC.controller.SetGameControllerImpl;
import MVC.model.Card;
import MVC.model.SetGameModel;
import MVC.model.SetThreeGameModel;
import MVC.model.GeneralSetGameModel;
import MVC.view.SetGameView;

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
