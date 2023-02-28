package MVC.view;

import java.io.IOException;

import MVC.model.SetGameModelState;

/**
 * View class for SetGameView.
 */
public class SetGameTextView implements SetGameView {
  SetGameModelState c;
  Appendable a;

  /**
   * This represents the viewer of a SetGame.
   *
   * @param c - a SetGameModelState.
   * @throws IllegalArgumentException if param is null.
   */
  public SetGameTextView(SetGameModelState c) throws IllegalArgumentException {
    if (c == null) {
      throw new IllegalArgumentException("Given SetGameModelState is null");
    }
    this.c = c;
    a = System.out;
  }

  /**
   * Constructor for a SetGameTextView.
   *
   * @param c - a SetGameModelState(the game).
   * @param a - an appendable for purposes of transmitting data.
   * @throws IllegalArgumentException if any params are null.
   */
  public SetGameTextView(SetGameModelState c, Appendable a) throws IllegalArgumentException {
    if (c == null || a == null) {
      throw new IllegalArgumentException("SetGameModelState or Appendable is null");
    }
    this.c = c;
    this.a = a;
  }

  /**
   * String representation of SetGame.
   *
   * @return a string representing the board.
   */
  public String toString() {
    StringBuilder ans = new StringBuilder();
    for (int i = 0; i < c.getHeight(); i++) {
      for (int j = 0; j < c.getWidth(); j++) {
        if (j != c.getWidth() - 1) {
          ans.append(c.getCardAtCoord(i, j).toString()).append(" ");
        } else {
          ans.append(c.getCardAtCoord(i, j).toString());
        }
      }
      if (i != c.getHeight() - 1) {
        ans.append("\n");
      }
    }
    return ans.toString();
  }

  @Override
  public void renderGrid() throws IOException {
    a.append(this.toString());
  }

  @Override
  public void renderMessage(String message) throws IOException {
    a.append(message);
  }
}
