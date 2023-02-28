package MVC.model;

import java.util.Arrays;

/**
 * Represents a card in the game. Each card will have 3 attributes.
 */
public final class Card {
  private final int count;
  private final String filling;
  private final String shape;

  /**
   * A Card has 3 attributes.
   *
   * @param count   - 1, 2, or 3 depending on how many shapes exist on the card
   * @param filling - E for empty, S for striped, F for full
   * @param shape   - O for oval, Q for squiggle, D for diamond
   */
  public Card(int count, String filling, String shape) {
    if (count < 1 || count > 3) {
      throw new IllegalArgumentException("Count field is invalid.");
    }
    if (!Arrays.asList("E", "S", "F").contains(filling) || filling == null) {
      throw new IllegalArgumentException("Filling is not a valid value.");
    }
    if (!Arrays.asList("O", "Q", "D").contains(shape) || shape == null) {
      throw new IllegalArgumentException("Shape is not a valid value.");
    }
    this.count = count;
    this.filling = filling;
    this.shape = shape;
  }

  /**
   * Returns count field of the current card.
   */
  public int getCount() {
    return this.count;
  }

  /**
   * Returns filling field of the current card.
   */
  public String getFilling() {
    return this.filling;
  }

  /**
   * Returns shape field of the current card.
   */
  public String getShape() {
    return this.shape;
  }

  @Override
  public String toString() {
    return count + filling + shape;
  }

  @Override
  public int hashCode() {
    return this.count + this.filling.hashCode() + this.shape.hashCode();
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof Card)) {
      return false;
    }
    if (this == other) {
      return true;
    }
    Card newCard = (Card) other;
    return this.count == newCard.count
          && this.filling.equals(newCard.filling)
          && this.shape.equals(newCard.shape);

  }
}
