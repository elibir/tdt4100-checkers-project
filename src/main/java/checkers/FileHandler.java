package checkers;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface FileHandler {
	
	void save(String filename, Game game) throws IOException;
	
	Game load(String filename) throws FileNotFoundException;
	
	String getFilePath(String filename);
	
	boolean doesFileExist(String filename);

}
