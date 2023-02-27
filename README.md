# SetGame
My implementation of the daily set puzzle, designed in java.
To play, run SetGame.java and feed 'general' or 'three' to the main method. This will run the game. 

Notes:
When the board is displayed, each card has three attributes:
Count - 1, 2, or 3.
Filling - (E)mpty, (S)triped, or (F)ull.
Shape - O(val), s(Q)uiggle, or (D)iamond.
A set is three cards that must have one/two attributes in common ONLY or no attributes in common. 
The deck of possible cards is 27 in size.
If there is no set on the board, the board will automatically add rows until a set exists.
You play until there is no set on the board or if the 27 card deck runs out. 
