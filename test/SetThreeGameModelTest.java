import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cs3500.set.model.hw02.Card;
import cs3500.set.model.hw02.Coord;
import cs3500.set.model.hw02.SetThreeGameModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


/**
 * Tests SetThreeGameModel class.
 */
public class SetThreeGameModelTest {
  SetThreeGameModel game1;
  SetThreeGameModel game2;
  ArrayList<Card> deck;

  /**
   * Initializes the deck of non duplicated cards.
   * THIS REPRESENTS EVERY POSSIBLE CARD CREATED WITHOUT DUPLICATES
   */
  public SetThreeGameModelTest() {
    // Every Permutation
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
  public void testClaimSet() {
    new SetThreeGameModelTest();
    game1 = new SetThreeGameModel();
    // Test exceptions
    try {
      game1.claimSet(new Coord(0, 1), new Coord(1, 1), new Coord(2, 1));
      fail();
    } catch (IllegalStateException e) {
      // Exception was caught
    }
    game1.startGameWithDeck(this.deck, 3, 3);
    try {
      game1.claimSet(new Coord(1, 1), new Coord(1, 1), new Coord(2, 1));
      fail();
    } catch (IllegalArgumentException e) {
      // Exception was caught
    }
    // Deck is decreasing, board is changing, and score is incrementing upon claim of a valid set
    int scoreBefore = game1.getScore();
    Card card1Before = game1.getCardAtCoord(new Coord(0, 1));
    Card card2Before = game1.getCardAtCoord(new Coord(1, 1));
    Card card3Before = game1.getCardAtCoord(new Coord(2, 1));
    game1.claimSet(new Coord(0, 1), new Coord(1, 1), new Coord(2, 1));
    assertNotEquals(game1.getScore(), scoreBefore);
    assertNotEquals(game1.getCardAtCoord(new Coord(0, 1)), card1Before);
    assertNotEquals(game1.getCardAtCoord(new Coord(1, 1)), card2Before);
    assertNotEquals(game1.getCardAtCoord(new Coord(2, 1)), card3Before);

    // Test for if there are <3 cards in the deck and claimSet is called
    game2 = new SetThreeGameModel();
    SetThreeGameModelTest test = new SetThreeGameModelTest();
    test.deck.subList(0, 17).clear();
    // The deck has 10 cards
    assertEquals(test.deck.size(), 10);
    game1.startGameWithDeck(test.deck, 3, 3);
    // Game is not over yet
    assertTrue(game1.isGameOver());
    // Set has been claimed
    scoreBefore = game1.getScore();
    game1.claimSet(new Coord(0, 1), new Coord(0, 2), new Coord(1, 0));
    // Score has increased
    assertEquals(game1.getScore(), scoreBefore + 1);
    // No Cards have been removed
    assertNotNull(game1.getCardAtCoord(0, 1));
    assertNotNull(game1.getCardAtCoord(1, 1));
    assertNotNull(game1.getCardAtCoord(2, 1));
    // Game is now over
    assertTrue(game1.isGameOver());
  }

  @Test
  public void testStartGameWithDeck() {
    game1 = new SetThreeGameModel();
    List<Card> deck = game1.getCompleteDeck();
    try {
      game1.startGameWithDeck(null, 3, 3);
      fail();
    } catch (IllegalArgumentException e) {
      // Do nothing, argument was thrown correctly
    }
    try {
      game1.startGameWithDeck(deck, -3, 3);
      fail();
    } catch (IllegalArgumentException e) {
      // Do nothing, argument was thrown correctly
    }
    try {
      game1.startGameWithDeck(deck, -3, -3);
      fail();
    } catch (IllegalArgumentException e) {
      // Do nothing, argument was thrown correctly
    }
    try {
      game1.startGameWithDeck(deck, 20, 4);
      fail();
    } catch (IllegalArgumentException e) {
      // Do nothing, argument was thrown correctly
    }
    try {
      game1.startGameWithDeck(deck, 2, -3);
      fail();
    } catch (IllegalArgumentException e) {
      // Do nothing, argument was thrown correctly
    }
    // Before mutation
    assertTrue(game1.isGameOver());
    this.game1.startGameWithDeck(deck, 3, 3);
    assertFalse(game1.isGameOver());
  }

  @Test
  public void getWidth() {
    game1 = new SetThreeGameModel();
    try {
      game1.getWidth();
      fail();
    } catch (IllegalStateException e) {
      // Function called before game started
    }
    game1.startGameWithDeck(game1.getCompleteDeck(), 3, 3);
    assertEquals(game1.getWidth(), 3);

  }

  @Test
  public void getHeight() {
    game1 = new SetThreeGameModel();
    try {
      game1.getHeight();
      fail();
    } catch (IllegalStateException e) {
      // Function called before game started
    }
    game1.startGameWithDeck(game1.getCompleteDeck(), 3, 3);
    assertEquals(game1.getHeight(), 3);
  }

  @Test
  public void getScore() {
    new SetThreeGameModelTest();
    game1 = new SetThreeGameModel();
    try {
      game1.getScore();
      fail();
    } catch (IllegalStateException e) {
      // Function called before game started
    }
    game1.startGameWithDeck(this.deck, 3, 3);
    assertEquals(game1.getScore(), 0);
    game1.claimSet(new Coord(0, 0), new Coord(1, 1), new Coord(2, 2));
    assertEquals(game1.getScore(), 1);
    game1.claimSet(new Coord(0, 0), new Coord(1, 1), new Coord(2, 2));
    assertEquals(game1.getScore(), 2);
  }

  @Test
  public void anySetsPresent() {
    game1 = new SetThreeGameModel();
    try {
      game1.anySetsPresent();
      fail();
    } catch (IllegalStateException e) {
      // Function called before game started
    }
    game1.startGameWithDeck(game1.getCompleteDeck(), 3, 3);
    assertTrue(game1.anySetsPresent());
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
    this.game1.startGameWithDeck(noSets, 3, 3);
    assertFalse(game1.anySetsPresent());
  }

  @Test
  public void testIsValidSet() {
    new SetThreeGameModelTest();
    game1 = new SetThreeGameModel();
    game1.startGameWithDeck(this.deck, 3, 3);
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
      game1.startGameWithDeck(tempRow, 3, 3);
      assertTrue(game1.isValidSet(new Coord(0, 0), new Coord(0, 1),
            new Coord(0, 2)));
    }
    for (Integer i : Arrays.asList(1, 2, 3)) {
      card1 = new Card(i, "E", "D");
      card2 = new Card(i, "S", "Q");
      card3 = new Card(i, "F", "O");
      tempRow = new ArrayList<>(Arrays.asList(card1, card3, card2, card1, card2, card3, card1,
            card2, card3));
      game1.startGameWithDeck(tempRow, 3, 3);
      assertTrue(game1.isValidSet(new Coord(0, 0), new Coord(0, 1),
            new Coord(0, 2)));
    }
    for (String i : Arrays.asList("O", "Q", "D")) {
      card1 = new Card(1, "E", i);
      card2 = new Card(2, "S", i);
      card3 = new Card(3, "F", i);
      tempRow = new ArrayList<>(Arrays.asList(card1, card3, card2, card1, card2, card3, card1,
            card2, card3));
      game1.startGameWithDeck(tempRow, 3, 3);
      assertTrue(game1.isValidSet(new Coord(0, 0), new Coord(0, 1),
            new Coord(0, 2)));
    }
    // All three are different
    card1 = new Card(1, "E", "O");
    card2 = new Card(2, "S", "Q");
    card3 = new Card(3, "F", "D");
    tempRow = new ArrayList<>(Arrays.asList(card1, card3, card2, card1, card2, card3, card1,
          card2, card3));
    game1.startGameWithDeck(tempRow, 3, 3);
    assertTrue(game1.isValidSet(new Coord(0, 0), new Coord(0, 1),
          new Coord(0, 2)));
  }

  @Test
  public void getCardAtCoord() {
    new SetThreeGameModelTest();
    game1 = new SetThreeGameModel();
    try {
      game1.getCardAtCoord(new Coord(0, 1));
      game1.getCardAtCoord(new Coord(0, 0));
      game1.getCardAtCoord(new Coord(-2, -3));
      game1.getCardAtCoord(new Coord(-2, -3));
      game1.getCardAtCoord(new Coord(-2, -3));
    } catch (IllegalStateException e) {
      // Exception was thrown correctly
    }
    game1.startGameWithDeck(this.deck, 3, 3);
    assertEquals(this.game1.getCardAtCoord(new Coord(0, 0)),
          new Card(1, "E", "O"));
    assertEquals(this.game1.getCardAtCoord(new Coord(2, 1)),
          new Card(1, "F", "Q"));
    assertEquals(this.game1.getCardAtCoord(new Coord(2, 2)),
          new Card(1, "F", "D"));
    assertEquals(this.game1.getCardAtCoord(new Coord(0, 0)),
          this.game1.getCardAtCoord(0, 0));
    assertEquals(this.game1.getCardAtCoord(new Coord(2, 1)),
          this.game1.getCardAtCoord(2, 1));
    assertEquals(this.game1.getCardAtCoord(new Coord(2, 2)),
          this.game1.getCardAtCoord(2, 2));
    // Check if getCardAtCoord works after ClaimSet
    Card oldCard = this.game1.getCardAtCoord(0, 0);
    this.game1.claimSet(new Coord(0, 0), new Coord(1, 0), new Coord(2, 0));
    assertNotEquals(oldCard, this.game1.getCardAtCoord(new Coord(0, 0)));
    oldCard = this.game1.getCardAtCoord(0, 0);
    this.game1.claimSet(new Coord(0, 0), new Coord(1, 0), new Coord(2, 0));
    assertNotEquals(oldCard, this.game1.getCardAtCoord(0, 0));
  }

  @Test
  public void isGameOver() {
    new SetThreeGameModelTest();
    this.game1 = new SetThreeGameModel();
    assertTrue(this.game1.isGameOver());
    this.game1.startGameWithDeck(this.deck, 3, 3);
    assertFalse(this.game1.isGameOver());
    this.game1.claimSet(new Coord(0, 0), new Coord(1, 0), new Coord(2, 0));
    assertFalse(this.game1.isGameOver());
    // Claim set one final time
    this.game1.claimSet(new Coord(0, 0), new Coord(1, 0), new Coord(2, 0));
    assertFalse(this.game1.isGameOver());
    this.game1.claimSet(new Coord(0, 0), new Coord(1, 0), new Coord(2, 0));
    assertFalse(this.game1.isGameOver());
  }

  @Test
  public void getCompleteDeck() {
    new SetThreeGameModelTest();
    this.game1 = new SetThreeGameModel();
    List<Card> deck = this.game1.getCompleteDeck();
    Set set = new HashSet(deck);
    assertEquals(deck.size(), 27);
    assertEquals(set.size(), deck.size()); // Check for duplicates
    assertEquals(this.game1.getCompleteDeck(), deck); // Newly generated deck is the same
    ArrayList<Card> everyPermutation = this.deck; // This represents every possible card
    for (Card card : deck) {
      assertTrue(everyPermutation.contains(card)); // Every card created is a valid card
    }
  }

  @Test
  public void checkCoord() {
    new SetThreeGameModelTest();
    game1 = new SetThreeGameModel();
    Coord coord1 = new Coord(1, 2);
    Coord coord2 = new Coord(3, 4);
    Coord coord3 = new Coord(0, 0);
    Coord coord4 = new Coord(-1, 2);
    Coord coord5 = new Coord(-1, -2);
    Coord coord6 = new Coord(1, -2);
    assertTrue(game1.checkCoord(coord1));
    assertFalse(game1.checkCoord(coord2));
    assertTrue(game1.checkCoord(coord3));
    assertFalse(game1.checkCoord(coord4));
    assertFalse(game1.checkCoord(coord5));
    assertFalse(game1.checkCoord(coord6));
  }

  @Test
  public void testIsValidSetHelp() {
    new SetThreeGameModelTest();
    this.game1 = new SetThreeGameModel();
    assertTrue(this.game1.isValidSetHelp("E", "E", "E"));
    assertTrue(this.game1.isValidSetHelp("F", "G", "B"));
    assertFalse(this.game1.isValidSetHelp("E", "F", "E"));
    assertFalse(this.game1.isValidSetHelp("C", "C", "E"));
    assertFalse(this.game1.isValidSetHelp("E", "C", "C"));
  }
}