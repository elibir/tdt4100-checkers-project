package checkers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class CheckersFileHandlerTest {
	
	private Game game;
	private static CheckersFileHandler fileHandler = new CheckersFileHandler();
	
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
        game.setTurn(1);
	}
	
	@BeforeEach
	public void setup() {
		createTestBoard();
	}
	
	@Test
	public void testLoad() {
		Game savedGame;
		try {
			savedGame = fileHandler.load("testbrett");
		} catch (FileNotFoundException e) {
			fail("Could not load saved file.");
			return;
		}
		assertEquals(game.toString(), savedGame.toString());
		assertEquals(game.getTurn(), savedGame.getTurn());
		
	}
	
	@Test
	public void testLoadNonExistingFile() {
		assertThrows(FileNotFoundException.class, () -> fileHandler.load("oihfhwoihrf"));
	}
	
	@Test
	public void testLoadInvalidFile() {
		assertTrue(fileHandler.doesFileExist("invalid-file"));
		assertThrows(Exception.class, () -> fileHandler.load("invalid-file"));
	}
	
	@Test
	public void testSave() {
		
		assertThrows(IllegalArgumentException.class, () -> fileHandler.save("", game),
				"Empty string should not be a legal filename.");
		assertThrows(IllegalArgumentException.class, () -> fileHandler.save
				("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", game),
				"Filename with over 30 characters should not be allowed.");
		assertThrows(IllegalArgumentException.class, () -> fileHandler.save
				("\\", game),
				"Filename with \\ should not be allowed.");
		
//Saves a new file called 'testbrett1' with the same data as 'testbrett.txt'
//and then asserts that they have the same content
		try {
			fileHandler.save("testbrett1", game);
		} catch (IOException e) {
			fail("File could not be saved.");
		}
		
		byte[] testFile = null, newTestFile = null;
		
		try {
			testFile = Files.readAllBytes(Path.of(fileHandler.getFilePath("testbrett")));
		} catch (IOException e) {
			fail("Could not load 'testbrett.txt'");
		}

		try {
			newTestFile = Files.readAllBytes(Path.of(fileHandler.getFilePath("testbrett1")));
		} catch (IOException e) {
			fail("Could not load 'testbrett1.txt' ");
		}
		assertNotNull(testFile);
		assertNotNull(newTestFile);
		assertTrue(Arrays.equals(testFile, newTestFile));
		
		
//Saves a new file called 'testbrett1' with a piece moved from the board in
// 'testbrett.txt', and then asserts that the files have different content
		teardown();
		game.setTurn(1);
		game.selectPiece(5, 4);
		game.move(6, 5);
		try {
			fileHandler.save("testbrett1", game);
		} catch (IOException e) {
			fail("File could not be saved.");
		}
	
		try {
			testFile = Files.readAllBytes(Path.of(fileHandler.getFilePath("testbrett")));
		} catch (IOException e) {
			fail("Could not load 'testbrett.txt'");
		}

		try {
			newTestFile = Files.readAllBytes(Path.of(fileHandler.getFilePath("testbrett1")));
		} catch (IOException e) {
			fail("Could not load 'testbrett1.txt' ");
		}
		assertNotNull(testFile);
		assertNotNull(newTestFile);
		assertFalse(Arrays.equals(testFile, newTestFile), 
				"The two boards should be different after changes are made to 'testbrett1'.");
		
	}
	
	@AfterAll
	private static void teardown() {
		File newTestSaveFile = new File(fileHandler.getFilePath("testbrett1"));
		newTestSaveFile.delete();
	}
	
	

}
