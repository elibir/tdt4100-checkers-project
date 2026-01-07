package checkers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class CheckersFileHandler implements FileHandler {

	private final static String SAVE_FOLDER = "students/project-v21-elias/src/main/resources/saves/";
	
	@Override
	public void save(String filename, Game game) throws IOException {
		
		if (filename.contains("/") || filename.contains("\\"))
			throw new IllegalArgumentException("Filename must be between 1 and 30 characters,"
					+ " and not contain any special characters.");
		
		else if (filename.isEmpty() ||filename.isBlank() || filename.length() > 30)
			throw new IllegalArgumentException("Filename must be between 1 and 30 characters,"
					+ " and not contain any special characters.");
		
		try (PrintWriter writer = new PrintWriter(getFilePath(filename))) {
			for (int y = 0; y < game.getHeight(); y++) {
				for (int x = 0; x < game.getWidth(); x++) {
					writer.print(game.getTile(x, y).getType());
				}
			}

			writer.println();
			writer.print(game.getTurn());
		}
	}

	@Override
	public Game load(String filename) throws FileNotFoundException {
		
		try (Scanner scanner = new Scanner(new File(getFilePath(filename)))) {
			Game game = new Game();
			String board = scanner.next();
			for (int y = 0; y < game.getHeight(); y++) {
				for (int x = 0; x < game.getWidth(); x++) {
					char symbol = board.charAt(y * game.getWidth() + x);
					game.getTile(x, y).setType(symbol);
				}
			}
			game.setTurn(scanner.nextInt());
			return game;
		}
	}

	@Override
	public String getFilePath(String filename) {
		return SAVE_FOLDER + filename + ".txt";
	}

	@Override
	public boolean doesFileExist(String filename) {
		File f = new File(getFilePath(filename));
		return f.exists();
	}

}
