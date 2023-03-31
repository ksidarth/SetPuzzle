import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import MVC.model.Card;
import MVC.model.Coord;
import MVC.model.ASetGameModel;
import MVC.model.GeneralSetGameModel;
import MVC.view.SetGameTextView;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Represents tests for a GeneralSetGameModel.
 */
public class GeneralSetGameModelTest {
  private ASetGameModel model;
  private SetGameTextView view;
  private ArrayList<Card> deck;

  /**
   * Initializes testing variables.
   *
   */
  public void init() {
    this.model = new GeneralSetGameModel();
    Appendable s = new StringBuilder();
    this.view = new SetGameTextView(this.model, s);
    ArrayList<Card> deck = new ArrayList<>();
    for (Integer i : Arrays.asList(1, 2, 3)) {
      for (String j : Arrays.asList("E", "S", "F")) {
        for (String k : Arrays.asList("O", "Q", "D")) {
          deck.add(new Card(i, j, k));
        }
      }
    }
    this.deck = deck;
  }

  @Test
  public void testAddsRows() {
    Card card1 = new Card(3, "S", "O");
    Card card2 = new Card(2, "S", "Q");
    Card card3 = new Card(1, "E", "D");

    Card card4 = new Card(3, "F", "Q");
    Card card5 = new Card(3, "F", "O");
    Card card6 = new Card(3, "S", "Q");

    Card card7 = new Card(1, "F", "O");
    Card card8 = new Card(3, "F", "D");
    Card card9 = new Card(2, "S", "D");
    ArrayList<Card> tempDeck = new ArrayList<>(Arrays.asList(card1, card2, card3, card4, card5,
          card6, card7, card8, card9, card1, card2, card3, card4, card5));

    init();
    // No sets on board, so will add a row.
    model.startGameWithDeck(tempDeck, 2, 2);
    assertEquals("""
          3SO 2SQ
          1ED 3FQ
          3FO 3SQ""", view.toString());
    assertEquals(model.getHeight(), 3);
    assertFalse(model.isGameOver());
    model.claimSet(new Coord(0, 1), new Coord(1, 0), new Coord(2, 0));
    // Since there is no set on the board after claim a new row will be added
    assertEquals("""
          3SO 3FO
          3SQ 3FQ
          1FO 3SQ
          3FD 2SD""", view.toString());
    assertEquals(model.getHeight(), 4);
    assertFalse(model.isGameOver());
    model.claimSet(new Coord(0, 1), new Coord(1, 1), new Coord(3, 0));
    assertEquals("""
          3SO 3SO
          3SQ 2SQ
          1FO 3SQ
          1ED 2SD
          3FQ 3FO""", view.toString());
    assertEquals(model.getHeight(), 5);
    // Now that there are not enough cards to make a set, even though a set exists,
    // game will be over
    assertTrue(model.isGameOver());

    init();
    model.startGameWithDeck(model.getCompleteDeck(), 3, 3);
    // Row will not be added
    assertTrue(model.anySetsPresent());
    assertEquals(model.getHeight(), 3);
    model.claimSet(new Coord(1, 0), new Coord(1, 1), new Coord(1, 2));
    // there is a set on board and enough cards, so it will not add a row
    assertEquals(model.getHeight(), 3);
  }


  @Test
  public void testClaimSet() {
    init();
    // Test exceptions
    try {
      model.claimSet(new Coord(0, 1), new Coord(1, 1), new Coord(2, 1));
      fail();
    } catch (IllegalStateException e) {
      // Exception was caught
    }
    model.startGameWithDeck(this.deck, 3, 3);
    try {
      model.claimSet(new Coord(1, 1), new Coord(1, 1), new Coord(2, 1));
      fail();
    } catch (IllegalArgumentException e) {
      // Exception was caught
    }
    // Deck is decreasing, board is changing, and score is incrementing upon claim of a valid set
    int scoreBefore = model.getScore();
    Card card1Before = model.getCardAtCoord(new Coord(0, 1));
    Card card2Before = model.getCardAtCoord(new Coord(1, 1));
    Card card3Before = model.getCardAtCoord(new Coord(2, 1));
    model.claimSet(new Coord(0, 1), new Coord(1, 1), new Coord(2, 1));
    assertNotEquals(model.getScore(), scoreBefore);
    assertNotEquals(model.getCardAtCoord(new Coord(0, 1)), card1Before);
    assertNotEquals(model.getCardAtCoord(new Coord(1, 1)), card2Before);
    assertNotEquals(model.getCardAtCoord(new Coord(2, 1)), card3Before);

    // Test for if there are <3 cards in the deck and claimSet is called
    init();
    this.deck.subList(0, 17).clear();
    // The deck has 10 cards
    assertEquals(this.deck.size(), 10);
    model.startGameWithDeck(this.deck, 3, 3);
    // Game is not over yet
    assertFalse(model.isGameOver());
    // Set has been claimed
    scoreBefore = model.getScore();
    model.claimSet(new Coord(0, 1), new Coord(0, 2), new Coord(1, 0));
    // Score has increased
    assertEquals(model.getScore(), scoreBefore + 1);
    // No Cards have been removed
    assertNotNull(model.getCardAtCoord(0, 1));
    assertNotNull(model.getCardAtCoord(1, 1));
    assertNotNull(model.getCardAtCoord(2, 1));
    // Game is now over
    assertTrue(model.isGameOver());
  }

  @Test
  public void testStartGameWithDeck() {
    init();
    List<Card> deck = model.getCompleteDeck();
    try {
      model.startGameWithDeck(null, 3, 3);
      fail();
    } catch (IllegalArgumentException e) {
      // Do nothing, argument was thrown correctly
    }
    try {
      model.startGameWithDeck(deck, -3, 3);
      fail();
    } catch (IllegalArgumentException e) {
      // Do nothing, argument was thrown correctly
    }
    try {
      model.startGameWithDeck(deck, -3, -3);
      fail();
    } catch (IllegalArgumentException e) {
      // Do nothing, argument was thrown correctly
    }
    try {
      model.startGameWithDeck(deck, 20, 4);
      fail();
    } catch (IllegalArgumentException e) {
      // Do nothing, argument was thrown correctly
    }
    try {
      model.startGameWithDeck(deck, 2, -3);
      fail();
    } catch (IllegalArgumentException e) {
      // Do nothing, argument was thrown correctly
    }
    try {
      model.startGameWithDeck(deck, 2, 1);
      fail();
    } catch (IllegalArgumentException e) {
      // Do nothing, argument was thrown correctly
    }
    try {
      model.startGameWithDeck(new ArrayList<>(), 2, 1);
      fail();
    } catch (IllegalArgumentException e) {
      // Do nothing, argument was thrown correctly
    }
    // Before mutation
    assertTrue(model.isGameOver());
    this.model.startGameWithDeck(deck, 3, 3);
    assertFalse(model.isGameOver());
  }

  @Test
  public void getWidth() {
    init();
    try {
      model.getWidth();
      fail();
    } catch (IllegalStateException e) {
      // Function called before game started
    }
    model.startGameWithDeck(model.getCompleteDeck(), 3, 3);
    assertEquals(model.getWidth(), 3);

  }

  @Test
  public void getHeight() {
    init();
    try {
      model.getHeight();
      fail();
    } catch (IllegalStateException e) {
      // Function called before game started
    }
    model.startGameWithDeck(model.getCompleteDeck(), 3, 3);
    assertEquals(model.getHeight(), 3);
  }

  @Test
  public void getScore() {
    new SetThreeGameModelTest();
    init();
    try {
      model.getScore();
      fail();
    } catch (IllegalStateException e) {
      // Function called before game started
    }
    model.startGameWithDeck(this.deck, 3, 3);
    assertEquals(model.getScore(), 0);
    model.claimSet(new Coord(0, 0), new Coord(1, 1), new Coord(2, 2));
    assertEquals(model.getScore(), 1);
    model.claimSet(new Coord(0, 0), new Coord(1, 1), new Coord(2, 2));
    assertEquals(model.getScore(), 2);
  }

  @Test
  public void anySetsPresent() {
    init();
    try {
      model.anySetsPresent();
      fail();
    } catch (IllegalStateException e) {
      // Function called before game started
    }
    model.startGameWithDeck(model.getCompleteDeck(), 3, 3);
    assertTrue(model.anySetsPresent());
    ArrayList<Card> row1 = new ArrayList<>(Arrays.asList(new Card(1, "E", "O"),
          new Card(1, "E", "O"), new Card(1, "E", "Q")));
    ArrayList<Card> row2 = new ArrayList<>(Arrays.asList(new Card(1, "S", "Q"),
          new Card(1, "S", "D"), new Card(1, "S", "Q")));
    ArrayList<Card> row3 = new ArrayList<>(Arrays.asList(new Card(2, "E", "D"),
          new Card(2, "E", "D"), new Card(1, "E", "Q")));
    // This is a board with no possible sets
    ArrayList<Card> noSets = new ArrayList<>(row1);
    noSets.addAll(row2);
    noSets.addAll(row3);
    this.model.startGameWithDeck(noSets, 3, 3);
    assertFalse(model.anySetsPresent());
  }

  @Test
  public void testIsValidSet() {
    new SetThreeGameModelTest();
    init();
    model.startGameWithDeck(this.deck, 3, 3);
    Card card1;
    Card card2;
    Card card3;
    ArrayList<Card> tempRow;

    // Test is every valid set combination is a set
    for (String i : Arrays.asList("E", "S", "F")) {
      card1 = new Card(1, i, "D");
      card2 = new Card(2, i, "Q");
      card3 = new Card(3, i, "O");
      tempRow = new ArrayList<>(Arrays.asList(card1, card3, card2, card1, card2, card3, card1,
            card2, card3));
      model.startGameWithDeck(tempRow, 3, 3);
      assertTrue(model.isValidSet(new Coord(0, 0), new Coord(0, 1),
            new Coord(0, 2)));
    }
    for (Integer i : Arrays.asList(1, 2, 3)) {
      card1 = new Card(i, "E", "D");
      card2 = new Card(i, "S", "Q");
      card3 = new Card(i, "F", "O");
      tempRow = new ArrayList<>(Arrays.asList(card1, card3, card2, card1, card2, card3, card1,
            card2, card3));
      model.startGameWithDeck(tempRow, 3, 3);
      assertTrue(model.isValidSet(new Coord(0, 0), new Coord(0, 1),
            new Coord(0, 2)));
    }
    for (String i : Arrays.asList("O", "Q", "D")) {
      card1 = new Card(1, "E", i);
      card2 = new Card(2, "S", i);
      card3 = new Card(3, "F", i);
      tempRow = new ArrayList<>(Arrays.asList(card1, card3, card2, card1, card2, card3, card1,
            card2, card3));
      model.startGameWithDeck(tempRow, 3, 3);
      assertTrue(model.isValidSet(new Coord(0, 0), new Coord(0, 1),
            new Coord(0, 2)));
    }
    // All three are different
    card1 = new Card(1, "E", "O");
    card2 = new Card(2, "S", "Q");
    card3 = new Card(3, "F", "D");
    tempRow = new ArrayList<>(Arrays.asList(card1, card3, card2, card1, card2, card3, card1,
          card2, card3));
    model.startGameWithDeck(tempRow, 3, 3);
    assertTrue(model.isValidSet(new Coord(0, 0), new Coord(0, 1),
          new Coord(0, 2)));
  }

  @Test
  public void getCardAtCoord() {
    new SetThreeGameModelTest();
    init();
    try {
      model.getCardAtCoord(new Coord(0, 1));
      model.getCardAtCoord(new Coord(0, 0));
      model.getCardAtCoord(new Coord(-2, -3));
      model.getCardAtCoord(new Coord(-2, -3));
      model.getCardAtCoord(new Coord(-2, -3));
    } catch (IllegalStateException e) {
      // Exception was thrown correctly
    }
    model.startGameWithDeck(this.deck, 3, 3);
    assertEquals(this.model.getCardAtCoord(new Coord(0, 0)),
          new Card(1, "E", "O"));
    assertEquals(this.model.getCardAtCoord(new Coord(2, 1)),
          new Card(1, "F", "Q"));
    assertEquals(this.model.getCardAtCoord(new Coord(2, 2)),
          new Card(1, "F", "D"));
    assertEquals(this.model.getCardAtCoord(new Coord(0, 0)),
          this.model.getCardAtCoord(0, 0));
    assertEquals(this.model.getCardAtCoord(new Coord(2, 1)),
          this.model.getCardAtCoord(2, 1));
    assertEquals(this.model.getCardAtCoord(new Coord(2, 2)),
          this.model.getCardAtCoord(2, 2));
    // Check if getCardAtCoord works after ClaimSet
    Card oldCard = this.model.getCardAtCoord(0, 0);
    this.model.claimSet(new Coord(0, 0), new Coord(1, 0), new Coord(2, 0));
    assertNotEquals(oldCard, this.model.getCardAtCoord(new Coord(0, 0)));
    oldCard = this.model.getCardAtCoord(0, 0);
    this.model.claimSet(new Coord(0, 0), new Coord(1, 0), new Coord(2, 0));
    assertNotEquals(oldCard, this.model.getCardAtCoord(0, 0));
  }

  @Test
  public void isGameOver() {
    new SetThreeGameModelTest();
    this.init();
    assertTrue(this.model.isGameOver());
    this.model.startGameWithDeck(this.deck, 3, 3);
    assertFalse(this.model.isGameOver());
    this.model.claimSet(new Coord(0, 0), new Coord(1, 0), new Coord(2, 0));
    assertFalse(this.model.isGameOver());
    // Claim set one final time
    this.model.claimSet(new Coord(0, 0), new Coord(1, 0), new Coord(2, 0));
    assertFalse(this.model.isGameOver());
    this.model.claimSet(new Coord(0, 0), new Coord(1, 0), new Coord(2, 0));
    assertFalse(this.model.isGameOver());
  }

  @Test
  public void getCompleteDeck() {
    new SetThreeGameModelTest();
    this.init();
    List<Card> deck = this.model.getCompleteDeck();
    Set<Card> set = new HashSet<>(deck);
    assertEquals(deck.size(), 27);
    assertEquals(set.size(), deck.size()); // Check for duplicates
    assertEquals(this.model.getCompleteDeck(), deck); // Newly generated deck is the same
    ArrayList<Card> everyPermutation = this.deck; // This represents every possible card
    for (Card card : deck) {
      assertTrue(everyPermutation.contains(card)); // Every card created is a valid card
    }
  }

  @Test
  public void testCheckCoord() {
    new SetThreeGameModelTest();
    init();
    Coord coord1 = new Coord(1, 2);
    Coord coord2 = new Coord(3, 4);
    Coord coord3 = new Coord(0, 0);
    Coord coord4 = new Coord(-1, 2);
    Coord coord5 = new Coord(-1, -2);
    Coord coord6 = new Coord(1, -2);
    model.startGameWithDeck(model.getCompleteDeck(), 3, 3);
    assertTrue(model.checkCoord(coord1));
    assertFalse(model.checkCoord(coord2));
    assertTrue(model.checkCoord(coord3));
    assertFalse(model.checkCoord(coord4));
    assertFalse(model.checkCoord(coord5));
    assertFalse(model.checkCoord(coord6));
  }

  @Test
  public void testIsValidSetHelp() {
    new SetThreeGameModelTest();
    this.init();
    assertTrue(this.model.isValidSetHelp("E", "E", "E"));
    assertTrue(this.model.isValidSetHelp("F", "G", "B"));
    assertFalse(this.model.isValidSetHelp("E", "F", "E"));
    assertFalse(this.model.isValidSetHelp("C", "C", "E"));
    assertFalse(this.model.isValidSetHelp("E", "C", "C"));
  }
}