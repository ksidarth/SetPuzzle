package cs3500.mvc.controller;

/**
 * Interface for Controller Implementation of SetGame.
 */
public interface SetGameController {
  /**
   * Plays the game of set.
   * throws @IllegalStateException if controller is unable to read input or output.
   */
  void playGame() throws IllegalStateException;
}