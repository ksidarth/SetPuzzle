import org.junit.Test;

import cs3500.mvc.model.Card;
import cs3500.mvc.model.SetThreeGameModel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * Tests the Card class.
 */
public class CardTest {
  Card card1;
  Card card2;
  Card card3;
  Card card4;
  Card card5;
  Card card6;

  @org.junit.Test
  public void testConstruction() {
    try {
      card1 = new Card(-1, "E", "Q");
      card2 = new Card(-2, "Z", "O");
      card3 = new Card(-3, "Z", "X");
      card4 = new Card(1, "Z", "D");
      card5 = new Card(2, "S", "P");
      card6 = new Card(3, "F", "U");
    } catch (IllegalArgumentException e) {
      // Do nothing
    }
    try {
      card1 = new Card(1, null, "Q");
      card2 = new Card(-2, "Z", null);
      card3 = new Card(-3, null, null);
    } catch (IllegalArgumentException e) {
      // Do nothing
    }
    assertNull(card1);
    assertNull(card2);
    assertNull(card3);
  }

  @org.junit.Test
  public void getCount() {
    card1 = new Card(3, "E", "Q");
    card2 = new Card(2, "S", "O");
    card3 = new Card(1, "F", "D");
    assertEquals(card1.getCount(), 3);
    assertEquals(card2.getCount(), 2);
    assertEquals(card3.getCount(), 1);
  }

  @org.junit.Test
  public void getFilling() {
    card1 = new Card(3, "E", "Q");
    card2 = new Card(2, "S", "O");
    card3 = new Card(1, "F", "D");
    assertEquals(card1.getFilling(), "E");
    assertEquals(card2.getFilling(), "S");
    assertEquals(card3.getFilling(), "F");
  }

  @org.junit.Test
  public void getShape() {
    card1 = new Card(3, "E", "Q");
    card2 = new Card(2, "S", "O");
    card3 = new Card(1, "F", "D");
    assertEquals(card1.getShape(), "Q");
    assertEquals(card2.getShape(), "O");
    assertEquals(card3.getShape(), "D");
  }

  @org.junit.Test
  public void testToString() {
    card1 = new Card(3, "E", "Q");
    card2 = new Card(2, "S", "O");
    card3 = new Card(1, "F", "D");
    assertEquals(card1.toString(), "3EQ");
    assertEquals(card2.toString(), "2SO");
    assertEquals(card3.toString(), "1FD");
  }

  @Test
  public void testHashCode() {
    Card card1 = new Card(1, "E", "D");
    Card card2 = new Card(3, "F", "Q");
    Card card3 = new Card(2, "S", "O");
    assertEquals(card1.hashCode(), 1 + "E".hashCode() + "D".hashCode());
    assertEquals(card2.hashCode(), 3 + "F".hashCode() + "Q".hashCode());
    assertEquals(card3.hashCode(), 2 + "S".hashCode() + "O".hashCode());
  }

  @Test
  public void testEquals() {
    Card card1 = new Card(1, "E", "D");
    Card card2 = new Card(3, "F", "Q");
    Card card3 = new Card(1, "E", "D");
    assertNotEquals(card1, card2);
    assertEquals(card1, card3);
    assertEquals(card1, card1);
    assertNotEquals(card1, new SetThreeGameModel());
  }
}