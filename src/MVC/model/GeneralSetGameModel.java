package MVC.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a General Set Game.
 */
public class GeneralSetGameModel extends ASetGameModel {
  /**
   * Sets up a GeneralSetGameModel.
   */
  public GeneralSetGameModel() {
    super();
  }


  /**
   * Different from main claimset in that this adds rows if there is both no set on the grid and
   * there are enough cards in the deck to add rows.
   *
   * @param coord1 the coordinates of the first card.
   * @param coord2 the coordinates of the second card.
   * @param coord3 the coordinates of the third card.
   */
  @Override
  public void claimSet(Coord coord1, Coord coord2, Coord coord3) {
    super.claimSet(coord1, coord2, coord3);
    while (this.deck.size() >= this.width && !this.anySetsPresent()) {
      Card currCard;
      ArrayList<Card> newRow = new ArrayList<>();
      for (int i = 0; i < this.width; i++) {
        currCard = this.deck.get(0);
        this.deck.remove(0);
        newRow.add(currCard);
      }
      this.board.add(newRow);
      this.height++;
      if (this.deck.size() < this.width) {
        this.gameOver = true;
      }
    }
  }

  /**
   * Same as startDeck but it adds rows if there are enough cards in the deck and no sets on the
   * board.
   *
   * @param deck   the list of cards in the order they will be played
   * @param height the height of the board for this game
   * @param width  the width of the board for this game
   * @throws IllegalArgumentException if width/height is invalid.
   */
  @Override
  public void startGameWithDeck(List<Card> deck, int height, int width)
        throws IllegalArgumentException {
    super.startGameWithDeck(deck, height, width);
    // add row if we can
    while (deck.size() >= this.width && !this.anySetsPresent()) {
      Card currCard;
      ArrayList<Card> newRow = new ArrayList<>();
      for (int i = 0; i < this.width; i++) {
        currCard = deck.get(0);
        deck.remove(0);
        newRow.add(currCard);
      }
      this.board.add(newRow);
      this.height++;
    }
    if (this.deck.size() < this.width && !this.anySetsPresent()) {
      this.gameOver = true;
    }
  }

}
