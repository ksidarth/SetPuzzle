package MVC.model;

import java.util.List;

/**
 * Represents a game of Set.
 */
public class SetThreeGameModel extends ASetGameModel {

  /**
   * This method takes no parameters and randomly generates the board.
   */
  public SetThreeGameModel() {
    super();
  }

  @Override
  public void startGameWithDeck(List<Card> deck, int height, int width)
        throws IllegalArgumentException {
    if (height != 3 || width != 3) {
      throw new IllegalArgumentException("Height and/or width is invalid");
    }
    super.startGameWithDeck(deck, height, width);
    if (this.deck.size() < this.width) {
      this.gameOver = true;
    }
  }
}
