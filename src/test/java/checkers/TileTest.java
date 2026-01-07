package checkers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



public class TileTest {
	
	private Tile tile;
	
	@Test
	public void testConstructor() {
		tile = new Tile(1,1);
		assertEquals(1, tile.getX());
		assertEquals(1, tile.getY());
		assertThrows(IllegalArgumentException.class, () -> new Tile(-1, 0), 
				"Setting a tile's value to a negative number should throw IlllegalArgumentException.");
		assertThrows(IllegalArgumentException.class, () -> new Tile(0, -1), 
				"Setting a tile's value to a negative number should throw IlllegalArgumentException.");
	}
	
	@BeforeEach
	public void setup() {
		tile = new Tile(0,0);
	}
	
	@Test
	public void testSetType() {
		tile.setType('b');
		assertTrue(tile.isBlack());
		tile.setType('w');
		assertTrue(tile.isWhite());
		tile.setType('1');
		assertTrue(tile.isPlayerOne());
		tile.setType('2');
		assertTrue(tile.isPlayerTwo());
		assertThrows(IllegalArgumentException.class, () -> tile.setType('?'), 
				"IllegalArgumentException should be thrown when tile is set to a non valid value.");
	}
	
	@Test
	public void testSetters() {
		tile.setBlack();
		assertTrue(tile.isBlack());
		tile.setWhite();
		assertTrue(tile.isWhite());
		tile.setPlayerOne();
		assertTrue(tile.isPlayerOne());
		tile.setPlayerTwo();
		assertTrue(tile.isPlayerTwo());
		tile.setSelected();
		assertTrue(tile.isSelected() && tile.isPlayerTwo());
		tile.removeSelected();
		assertFalse(tile.isSelected());
		assertTrue(tile.isPlayerTwo());
	}
	

}