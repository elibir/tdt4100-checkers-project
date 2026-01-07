package checkers;

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;



public class Controller {

	private Game game;
	private CheckersFileHandler filehandler = new CheckersFileHandler();
	private String overwriteFilename = "   ";
	
	@FXML
	private GridPane board;
	
	@FXML
	private Node clickedNode;
	
	@FXML
	private Text turn;
	
	@FXML
	private TextField filename;
	
	@FXML
	private Text gameSaved;
	
	@FXML
	private Text errorMessage;
	
	
	

	
	
	
	@FXML
	public void initialize() {
		game = new Game();
		game.setTurn(1);
		createBoard();
		drawBoard();
		
	}
	
	@FXML
	private void handleGame(javafx.scene.input.MouseEvent event) {
	    clickedNode = event.getPickResult().getIntersectedNode();
	    if (clickedNode != board) {
	        Integer x = GridPane.getColumnIndex(clickedNode);
	        Integer y = GridPane.getRowIndex(clickedNode);
	        
			if (game.hasSelected() && game.getTile(x, y).isBlack())
				handleMove(x,y);
			 	drawBoard();
	        
	        game.selectPiece(x, y);
	        drawBoard();
	    }
	}
	
	@FXML
	private void setTurnText() {
		if (game.getTurn() == 2 && game.playerOneVictory())
			turn.setText("Player 1 wins!");
		else if (game.getTurn() == 1 && game.playerTwoVictory())
			turn.setText("Player 2 wins!");
		else if (game.getTurn() == 1)
			turn.setText("Player 1");
		else if (game.getTurn() == 2)
			turn.setText("Player 2");
			
	}
	
	@FXML
	private void handleSave() {
		try {
			
			if (filehandler.doesFileExist(getFileName()) && overwriteFilename.equals(getFileName())) {
				filehandler.save(getFileName(), game);
				errorMessage.setVisible(false);
				gameSaved.setVisible(true);
				overwriteFilename = "   ";
			}
			
			else if (filehandler.doesFileExist(getFileName()) && overwriteFilename.equals("   ")) {
				overwriteFilename = getFileName();
				gameSaved.setVisible(false);
				errorMessage.setText("A file with this name already exists. "
						+ "Press save again to overwrite.");
				errorMessage.setVisible(true);
			}
			
			else {
				filehandler.save(getFileName(), game);
				errorMessage.setVisible(false);
				gameSaved.setVisible(true);
			}
				
		} catch (IOException e) {
			errorMessage.setText("An unknown error occured while saving. "
					+ "Try removing any special characters from filename.");
			gameSaved.setVisible(false);
			errorMessage.setVisible(true);
		}catch (IllegalArgumentException e) {
			errorMessage.setText("Filename must be between 1 and 30 characters,"
					+ " and not contain any special characters.");
			gameSaved.setVisible(false);
			errorMessage.setVisible(true);
		}
	}
	
	@FXML
	private void handleLoad() {
		try {
			game = filehandler.load(getFileName());
			drawBoard();
		} catch (FileNotFoundException e) {
			gameSaved.setVisible(false);
			errorMessage.setText("File not found");
			errorMessage.setVisible(true);
		}catch (Exception e) {
			gameSaved.setVisible(false);
			errorMessage.setText("File could not be loaded.");
			errorMessage.setVisible(true);
		}
	}
	
	@FXML
	private String getFileName() {
    	String filename = this.filename.getText();
    	return filename;
    }
	
	private void handleMove(int x, int y) {
		game.move(x, y);
	}
	
	private void createBoard() {
		board.getChildren().clear();
        for (int y = 0; y < game.getHeight(); y++) {
            for (int x = 0; x < game.getWidth(); x++) {
                Pane tile = new Pane();
                GridPane.setRowIndex(tile, y);
                GridPane.setColumnIndex(tile, x);
                board.getChildren().add(tile);
            }
        }
	}
	
	private void drawBoard() {
		for (int y = 0; y < game.getHeight(); y++) {
			for (int x = 0; x < game.getWidth(); x++) {
				
				if (game.getTile(x, y).isSelected())
					board.getChildren().get(y*game.getWidth() + x)
					.setStyle("-fx-border-color: gold; -fx-border-width: 3px;-fx-background-color: "
					+ getTileColor(game.getTile(x, y)) + ";");
				
				else
					board.getChildren().get(y*game.getWidth() + x)
					.setStyle("-fx-background-color: " + getTileColor(game.getTile(x, y)) + ";");
			}
		}
		setTurnText();
		gameSaved.setVisible(false);
		errorMessage.setVisible(false);
		overwriteFilename = "   ";
	}
	
	private String getTileColor(Tile tile) {
        if (tile.isBlack()) {
            return "#8B4513";
        } else if (tile.isWhite()) {
            return "#FFFF99";
        } else if (tile.isPlayerOne()) {
            return "red";
        } else if (tile.isPlayerTwo()) {
            return "blue";
        }
        return null;
	}
	
	

}
