# Checkers (TDT4100 Project)

A two-player checkers game built with Java and JavaFX for the TDT4100 course at NTNU.

## Features

- 8x8 checkers board with standard piece placement
- Two-player local gameplay (Player 1: red, Player 2: blue)
- Click to select a piece, click again to move it
- Jump/capture opponent pieces by jumping over them
- Win detection when all opponent pieces are captured
- Save and load game state to/from named files

## Project Structure

```
src/main/java/checkers/
  Game.java               # Core game logic (board, moves, jumps, win detection)
  Tile.java               # Represents a single board tile
  Controller.java         # JavaFX controller (UI event handling)
  CheckersFileHandler.java# Save/load game state to .txt files
  FileHandler.java        # File handler interface
  CheckersApp.java        # Application entry point
  CheckersGame.fxml       # UI layout

src/main/resources/saves/ # Saved game files
src/test/java/checkers/   # JUnit 5 tests
```

## Requirements

- Java 17
- Maven

## Running the Application

```bash
mvn javafx:run
```

## Running Tests

```bash
mvn test
```

## How to Play

1. Launch the application.
2. Player 1 (red) moves first.
3. Click a piece to select it (highlighted with a gold border), then click a valid dark tile to move.
4. Jump over an opponent's piece to capture it — the opponent's piece is removed.
5. The first player to eliminate all of the opponent's pieces wins.

## Save / Load

Enter a filename (1–30 characters, no special characters) in the text field and click **Save** or **Load**. Save files are stored as `.txt` files in `src/main/resources/saves/`. If a file with the same name already exists, you will be prompted to confirm before overwriting.
