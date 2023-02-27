package cs3500.mvc.controller;

import java.io.IOException;
import java.util.Scanner;

import cs3500.mvc.model.Coord;
import cs3500.mvc.model.SetGameModel;
import cs3500.mvc.view.SetGameView;

/**
 * Represents the Controller for a set game.
 */
public class SetGameControllerImpl implements SetGameController {
  private final SetGameModel c;
  private final SetGameView v;
  private final Readable r;

  /**
   * Creates a SetGameController.
   *
   * @param c - the SetGameModel.
   * @param v - the SetGameView.
   * @param r - A Readable for input purposes.
   */
  public SetGameControllerImpl(SetGameModel c, SetGameView v, Readable r) {
    if (c == null || v == null || r == null) {
      throw new IllegalArgumentException("One or more arguments for SetGameControllerImpl is null");
    }
    this.c = c;
    this.v = v;
    this.r = r;
  }

  @Override
  public void playGame() throws IllegalStateException {
    Scanner scanner = new Scanner(r);
    boolean canRun = false;
    while (!canRun) {
      String firstInput = scanner.next();
      if (firstInput.equalsIgnoreCase("q")) {
        this.renderMessage("Game quit!\nScore: 0");
        return;
      }
      String secondInput = scanner.next();
      if (secondInput.equalsIgnoreCase("q")) {
        this.renderMessage("Game quit!\nScore: 0");
        return;
      }
      // is the height and width valid? if so start game.
      try {
        int height = Integer.parseInt(firstInput);
        int width = Integer.parseInt(secondInput);
        this.c.startGameWithDeck(c.getCompleteDeck(), height, width);
        canRun = true;
      } catch (IllegalArgumentException e) {
        this.renderMessage("Invalid height/width. Try again. "
              + "Height and width must be correct values.\n");
      }
    }
    int[] moveSet = new int[6];
    int index = 0;
    this.renderMessage("Enter your move as a 3 different coordinates, separated by spaces. \n");
    this.renderGrid();
    this.renderMessage("\n");
    this.renderMessage("Score: " + c.getScore() + "\n");
    // now start the game
    while (!c.isGameOver()) {
      if (!scanner.hasNext()) {
        throw new IllegalStateException("Readable has no next");
      }
      String move = scanner.next();
      if ("q".equalsIgnoreCase(move)) {
        this.renderMessage("Game quit!\nState of game when quit:\n");
        this.renderGrid();
        this.renderMessage("\nScore: " + c.getScore());
        break;
      }
      try {
        moveSet[index] = Integer.parseInt(move) - 1;
      } catch (NumberFormatException e) {
//        this.renderMessage("Invalid value entered. Try Again.\n");
      }
      index++;
      if (index == 6) {
        try {
          c.claimSet(new Coord(moveSet[0], moveSet[1]), new Coord(moveSet[2], moveSet[3]),
                new Coord(moveSet[4], moveSet[5]));
          this.renderMessage("Correct claim!\n");
          this.renderMessage("Enter your move as a 3 different row x column coordinates, " +
                "separated by spaces. \n");
          this.renderGrid();
          this.renderMessage("\nScore: " + c.getScore() + "\n");
        } catch (IllegalArgumentException e) {
          this.renderMessage("Invalid claim. Try again.\n");
        }
        index = 0;
      }
    }
    if (this.c.isGameOver()) {
      this.renderMessage("Game over!\nScore: " + c.getScore());
    }
  }

  /**
   * Used to call renderMessage in SetGameTextView.
   *
   * @param s - the String to show.
   * @throws IllegalStateException if the string is invalid.
   */
  private void renderMessage(String s) throws IllegalStateException {
    try {
      v.renderMessage(s);
    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  /**
   * Used to call renderGrid() in SetGameTextView.
   *
   * @throws IllegalStateException if there is an issue in transmitting the data.
   */
  private void renderGrid() throws IllegalStateException {
    try {
      v.renderGrid();
    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }
}

