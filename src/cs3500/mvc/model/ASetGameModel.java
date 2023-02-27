package cs3500.mvc.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Abstract representation of a SetGame.
 */
public abstract class ASetGameModel implements SetGameModel<Card> {
  protected ArrayList<ArrayList<Card>> board;
  protected boolean gameOver;
  protected int score;
  protected int height;
  protected int width;
  protected ArrayList<Card> deck;

  /**
   * This method takes no parameters and initializes score and isGameOver.
   */
  public ASetGameModel() {
    this.gameOver = false;
    this.score = 0;
  }

  @Override
  public void claimSet(Coord coord1, Coord coord2, Coord coord3) throws IllegalStateException,
        IllegalArgumentException {
    if (this.deck == null) {
      throw new IllegalStateException("Game not started");
    }
    if (!this.isValidSet(coord1, coord2, coord3)) {
      throw new IllegalArgumentException("Coordinates given are not a set");
    }
    score++;
    if (deck.size() >= 3) {
      this.board.get(coord1.row).set(coord1.col, this.deck.get(0));
      this.deck.remove(0);
      this.board.get(coord2.row).set(coord2.col, this.deck.get(0));
      this.deck.remove(0);
      this.board.get(coord3.row).set(coord3.col, this.deck.get(0));
      this.deck.remove(0);
    } else {
      this.gameOver = true;
    }

  }

  @Override
  public void startGameWithDeck(List<Card> deck, int height, int width)
        throws IllegalArgumentException {
    if (deck == null || deck.size() < height * width) {
      throw new IllegalArgumentException("Deck is null or not large enough for the grid");
    }
    if (height * width < 3 || height < 0 || width < 0) {
      throw new IllegalArgumentException("Height and/or width is invalid");
    }
    this.height = height;
    this.width = width;
    this.gameOver = false;
    this.board = new ArrayList<>();
    this.deck = new ArrayList<>();
    // build board
    for (int row = 0; row < height; row++) {
      ArrayList<Card> currRow = new ArrayList<>();
      for (int col = 0; col < width; col++) {
        currRow.add(deck.get(0));
        deck.remove(deck.get(0));
      }
      this.board.add(currRow);
    }
    this.deck = new ArrayList<>(deck);
  }

  @Override
  public int getWidth() throws IllegalStateException {
    if (this.board == null) {
      throw new IllegalStateException("Game has not started");
    }
    return this.width;
  }

  @Override
  public int getHeight() throws IllegalStateException {
    if (this.board == null) {
      throw new IllegalStateException("Game has not started");
    }
    return this.height;
  }

  @Override
  public int getScore() throws IllegalStateException {
    if (this.deck == null) {
      throw new IllegalStateException("Game has not started/is over");
    }
    return this.score;
  }

  @Override
  public boolean anySetsPresent() throws IllegalStateException {
    if (this.deck == null) {
      throw new IllegalStateException("Game has not started/is over");
    }
    boolean ans = false;
    int max = this.width * this.height;
    for (int i = 0; i < max - 2; i++) {
      for (int j = i + 1; j < max - 1; j++) {
        for (int k = j + 1; k < max; k++) {
          Coord coord1 = new Coord(i / this.getWidth(), i % this.getWidth());
          Coord coord2 = new Coord(j / this.getWidth(), j % this.getWidth());
          Coord coord3 = new Coord(k / this.getWidth(), k % this.getWidth());
          ans = ans || isValidSet(coord1, coord2, coord3);
        }
      }
    }
    return ans;
  }

  /**
   * Is a given coord valid.
   *
   * @param coord1 - any instance of the Coord Class
   * @return true if the dimensions are valid, false if not
   */
  public boolean checkCoord(Coord coord1) {
    return coord1.row >= 0 && coord1.col >= 0 &&
          coord1.col < this.getWidth() && coord1.row < this.getHeight();
  }

  @Override
  public boolean isValidSet(Coord coord1, Coord coord2, Coord coord3)
        throws IllegalArgumentException, IllegalStateException {
    if (this.deck == null) {
      throw new IllegalStateException("Game has not started/is over");
    }
    if (!this.checkCoord(coord1) || !this.checkCoord(coord2)
          || !this.checkCoord(coord3)) {
      throw new IllegalArgumentException("Given coords are invalid.");
    }
    if (coord1.equals(coord2) || coord1.equals(coord3) || coord2.equals(coord3)) {
      return false;
    }
    String card1 = this.getCardAtCoord(coord1).toString();
    String card2 = this.getCardAtCoord(coord2).toString();
    String card3 = this.getCardAtCoord(coord3).toString();
    boolean ans = true;
    for (int i = 0; i < 3; i++) {
      ans = ans && this.isValidSetHelp(card1.substring(i, i + 1), card2.substring(i, i + 1),
            card3.substring(i, i + 1));
    }
    return ans;
  }

  /**
   * Helper method for isValidSet. It is given strings broken down into each characteristic, then
   * iteratively check them.
   *
   * @param card1 - The first card as a string
   * @param card2 - Second card as a string
   * @param card3 Third card as a string
   * @return is there any set to be created with the given three attributes.
   */
  public boolean isValidSetHelp(String card1, String card2, String card3) {
    if (card1.equals(card2) && card2.equals(card3)) {
      return true;
    }
    return !card1.equals(card2) && !card2.equals(card3) && !card3.equals(card1);
  }

  @Override
  public Card getCardAtCoord(int row, int col) throws IllegalStateException {
    if (this.deck == null) {
      throw new IllegalStateException("Game has not started");
    }
    return this.board.get(row).get(col);
  }

  @Override
  public Card getCardAtCoord(Coord coord) {
    if (this.deck == null) {
      throw new IllegalStateException("Game has not started");
    }
    return this.board.get(coord.row).get(coord.col);
  }

  @Override
  public boolean isGameOver() {
    if (this.board == null) {
      return true;
    }
    return this.gameOver || !anySetsPresent();
  }

  @Override
  public List<Card> getCompleteDeck() {
    ArrayList<Card> deck = new ArrayList<>();
    for (Integer i : Arrays.asList(1, 2, 3)) {
      for (String j : Arrays.asList("E", "S", "F")) {
        for (String k : Arrays.asList("O", "Q", "D")) {
          deck.add(new Card(i, j, k));
        }
      }
    }
    Collections.shuffle(deck);
    return deck;
  }
}
