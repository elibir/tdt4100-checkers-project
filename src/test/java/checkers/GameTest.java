package checkers;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameTest {
	
	private Game game;
	
	@Test
	public void testConstructor() {
		String board = "w2w2w2w2\n"
					+ "2w2w2w2w\n"
					+ "w2w2w2w2\n"
					+ "bwbwbwbw\n"
					+ "wbwbwbwb\n"
					+ "1w1w1w1w\n"
					+ "w1w1w1w1\n"
					+ "1w1w1w1w\n";
		game = new Game();
		assertEquals(board, game.toString());
	}
	
	@BeforeEach
	public void setup() {
		game = new Game();
	}
	
	@Test
	public void testGetTile() {
		assertEquals(game.getBoard()[1][1],game.getTile(1, 1));
		assertThrows(IllegalArgumentException.class, () -> game.getTile(10, 10), 
				"IllegalArgumentException should be thrown when trying to get an out-of-bounds Tile.");
	}
	
	@Test
	public void testGetSelectedPiece() {
		assertNull(game.getSelectedPiece(), "Should return null when no tile is selected.");
		assertTrue(game.getTile(0, 7).isPlayerOne());
		game.selectPiece(0, 7);
		assertEquals(game.getTile(0,7), game.getSelectedPiece(), 
				"The method should return the selected piece.");
	}
	
	@Test
	public void testGetTargetTile() {
		//One can only move to a black tile
		assertTrue(game.getTile(1, 4).isBlack());
		assertEquals(game.getTile(1, 4), game.getTargetTile(1, 4));
		assertTrue(game.getTile(1, 5).isWhite());
		assertNull(game.getTargetTile(1, 5));
		assertTrue(game.getTile(0, 7).isPlayerOne());
		assertNull(game.getTargetTile(0, 7));
		assertTrue(game.getTile(1, 0).isPlayerTwo());
		assertNull(game.getTargetTile(1, 0));
	}
	
	@Test
	public void testSelectPiece() {
		game.getTile(0, 0).setBlack();
		game.selectPiece(0, 0);
		assertFalse(game.hasSelected(), "black and white tiles cannot be selected as pieces");
		
		game.getTile(0, 0).setWhite();
		game.selectPiece(0, 0);
		assertFalse(game.hasSelected(), "black and white tiles cannot be selected as pieces");
		
		game.setTurn(1);
		game.getTile(0, 0).setPlayerTwo();
		game.selectPiece(0, 0);
		assertFalse(game.hasSelected(),"Player 2 cannot be selected when it is player 1's turn.");
		game.getTile(0,0).setPlayerOne();
		game.selectPiece(0, 0);
		assertTrue(game.hasSelected());
		game.getTile(0,1).setPlayerOne();
		game.selectPiece(0, 1);
		assertTrue(game.getTile(0, 1).isSelected());
		assertFalse(game.getTile(0, 0).isSelected(), 
				"The previously selected piece should be unselected when selecting a new piece.");
		
		game.setTurn(2);
		game.getTile(0, 0).setPlayerOne();
		game.selectPiece(0, 0);
		assertFalse(game.hasSelected(),"Player 1 cannot be selected when it is player 2's turn.");
		game.getTile(0,0).setPlayerTwo();
		game.selectPiece(0, 0);
		assertTrue(game.hasSelected());
		game.getTile(0,1).setPlayerTwo();
		game.selectPiece(0, 1);
		assertTrue(game.getTile(0, 1).isSelected());
		assertFalse(game.getTile(0, 0).isSelected(), 
				"The previously selected piece should be unselected when selecting a new piece.");
	}
	
	@Test
	public void testSetTurn() {
		game.setTurn(1);
		assertEquals(1, game.getTurn());
		game.setTurn(2);
		assertEquals(2, game.getTurn());
		assertThrows(IllegalArgumentException.class, () -> game.setTurn(3), 
				"Turn can only be 1 or 2");
	}
	
	private void createTestBoard() {
		game = new Game();
        for (int y = 2; y < 6; y++) {
            for (int x = 0; x < game.getWidth(); x++) {
            	if (game.getTile(x, y).isPlayerOne() || game.getTile(x, y).isPlayerTwo())
            		game.getTile(x, y).setBlack();
            }
        }
        game.getTile(1, 4).setPlayerOne();
        game.getTile(2, 3).setPlayerTwo();
        game.getTile(4, 3).setPlayerTwo();
        game.getTile(5, 4).setPlayerOne();
	}
	
	@Test
	public void testMove(){
		createTestBoard();
		//start the application and load the file "testbrett" to visualize.
		//Player 1
		game.setTurn(1);
		game.movePlayerTwo(game.getTile(2, 3), game.getTile(1,2));
		assertTrue(game.getTile(2, 3).isPlayerTwo() && game.getTile(1, 2).isBlack(), 
				"Player 2 cannot make a valid move while turn is 1");
		game.selectPiece(1,4);
		assertThrows(IllegalArgumentException.class, () -> game.move(10, 10),
				"Cannot move to a non-existing tile.");
		game.move(1, 3);
		assertTrue(game.getTile(1, 4).isPlayerOne() && game.getTile(1, 3).isWhite(), 
				"Cannot move to a white tile.");
		game.move(2, 3);
		assertTrue(game.getTile(1, 4).isPlayerOne() && game.getTile(2, 3).isPlayerTwo(),
				"Cannot move ontop of player 2's piece.");	
		game.move(1, 2);
		assertTrue(game.getTile(1, 4).isPlayerOne() && game.getTile(1, 2).isBlack(),
				"Cannot move to a non-adjacent black tile.");
		game.move(3, 2);
		assertTrue(game.getTile(1, 4).isBlack(), "Tests jump and capture ok."); 
		assertTrue(game.getTile(3, 2).isPlayerOne(), "Tests jump and capture ok.");
		assertTrue(game.getTile(2, 3).isBlack(), "Tests jump and capture ok.");
		game.setTurn(1);
		game.selectPiece(5, 4);
		game.move(6, 5);
		assertTrue(game.getTile(5, 4).isBlack(), "Tests normal move ok.");
		assertTrue(game.getTile(6, 5).isPlayerOne(), "Tests normal move ok.");
		
		//Player 2
		createTestBoard();
		game.setTurn(2);
		game.movePlayerOne(game.getTile(1, 4), game.getTile(0,3));
		assertTrue(game.getTile(1, 4).isPlayerOne() && game.getTile(0, 3).isBlack(), 
				"Player 1 cannot make a valid move while turn is 2");
		game.selectPiece(2,3);
		assertThrows(IllegalArgumentException.class, () -> game.move(10, 10),
				"Cannot move to a non-existing tile.");
		game.move(1, 3);
		assertTrue(game.getTile(2, 3).isPlayerTwo() && game.getTile(1, 3).isWhite(), 
				"Cannot move to a white tile.");
		game.move(1, 4);
		assertTrue(game.getTile(2, 3).isPlayerTwo() && game.getTile(1, 4).isPlayerOne(),
				"Cannot move ontop of player 1's piece.");	
		game.move(2, 5);
		assertTrue(game.getTile(2, 3).isPlayerTwo() && game.getTile(2, 5).isBlack(),
				"Cannot move to a non-adjacent black tile.");
		game.move(0, 5);
		assertTrue(game.getTile(2, 3).isBlack(), "Tests jump and capture ok."); 
		assertTrue(game.getTile(0, 5).isPlayerTwo(), "Tests jump and capture ok.");
		assertTrue(game.getTile(1, 4).isBlack(), "Tests jump and capture ok.");
		game.setTurn(2);
		game.selectPiece(4, 3);
		game.move(5, 2);
		assertTrue(game.getTile(4, 3).isBlack(), "Tests normal move ok.");
		assertTrue(game.getTile(5, 2).isPlayerTwo(), "Tests normal move ok.");
	}
	
	@Test
	public void testVictory() {
		for (int y = 0; y < game.getHeight(); y++) {
            for (int x = 0; x < game.getWidth(); x++) {
            	if(game.getTile(x, y).isPlayerTwo())
            		game.getTile(x, y).setBlack();
            }
		}
		assertTrue(game.playerOneVictory(), 
				"A game with no player 2 pieces should be a victory for player 1.");
		
		game = new Game();
		for (int y = 0; y < game.getHeight(); y++) {
            for (int x = 0; x < game.getWidth(); x++) {
            	if(game.getTile(x, y).isPlayerOne())
            		game.getTile(x, y).setBlack();
            }
		}
		assertTrue(game.playerTwoVictory(), 
				"A game with no player 1 pieces should be a victory for player 2.");
	}

	
	
	
	
	
	
	
	
	
	
}
