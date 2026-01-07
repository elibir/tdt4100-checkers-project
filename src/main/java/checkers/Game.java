package checkers;

public class Game {

	private Tile[][] board;
	private int turn = 1;
	private final int width = 8;
	private final int height = 8;
	

    public Game() {
    	
        board = new Tile[height][width];
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                board[y][x] = new Tile(x, y);
            }
        }
        
        for (int y = 0; y < height; y++) {
        	if (y % 2 == 0)
        		for (int x = 0; x < width; x++) {
        			if (x % 2 == 0)
        				board[y][x].setWhite();
        			if (x % 2 != 0)
        				board[y][x].setBlack();
        		}
        	if (y % 2 != 0)
        		for (int x = 0; x < width; x++) {
        			if (x % 2 == 0)
        				board[y][x].setBlack();
        			if (x % 2 != 0)
        				board[y][x].setWhite();
        		}
        }
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < width; x++) {
               if (board[y][x].getType() == 'b')
            	   board[y][x].setPlayerTwo();
            }
        }
        for (int y = 5; y < 8; y++) {
            for (int x = 0; x < width; x++) {
               if (board[y][x].getType() == 'b')
            	   board[y][x].setPlayerOne();
            }
        }
        
        
    }
    
    public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}
	
	public int getTurn() {
		return turn;
	}
	
	public Tile[][] getBoard(){
		return board;
	}
	
	public Tile getTile(int x, int y) {
        if (!isTile(x, y)) {
            throw new IllegalArgumentException("Not a valid tile");
        }

        return board[y][x];
    }
	
	public Tile getSelectedPiece() {
		if (hasSelected())
			for (int y = 0; y < getHeight(); y++) {
	            for (int x = 0; x < getWidth(); x++) {
	            	if (getTile(x,y).isSelected())
	            		return getTile(x,y);
	            }
			}return null;
           
	}
	
	public Tile getTargetTile(int x, int y) {
		if (getTile(x,y).isBlack())
			return getTile(x,y);
		else
			return null;
	}
	
	public void selectPiece(int x, int y) {
		
		if (turn == 1)
			
			if (!hasSelected() && getTile(x,y).isPlayerOne())
				getTile(x,y).setSelected();
			else if (hasSelected() && getTile(x,y).isPlayerOne()) { 
				getSelectedPiece().removeSelected();
				getTile(x,y).setSelected();
			}
			else if (hasSelected() && !getTile(x,y).isPlayerOne())
				getSelectedPiece().removeSelected();
			else if (!hasSelected() && !getTile(x,y).isPlayerOne())
				return;
		
		if (turn == 2)
			
			if (!hasSelected() && getTile(x,y).isPlayerTwo())
				getTile(x,y).setSelected();
			else if (hasSelected() && getTile(x,y).isPlayerTwo()) { 
				getSelectedPiece().removeSelected();
				getTile(x,y).setSelected();
			}
			else if (hasSelected() && !getTile(x,y).isPlayerTwo())
				getSelectedPiece().removeSelected();
			else if (!hasSelected() && !getTile(x,y).isPlayerTwo())
				return;
	}

	public void setTurn(int x) {
		if (!((x == 1 )|| (x == 2)))
			throw new IllegalArgumentException("Turn can only be 1 or 2");
		turn = x;
				
	}
	
	public void movePlayerOne(Tile piece, Tile targettile) {
		if (turn == 1 && isValidMove(piece, targettile)) {
			if (isJump(piece, targettile)) {
				jump(piece, targettile);
			}
			piece.setBlack();
			targettile.setPlayerOne();
			setTurn(2);
		}
	}
	
	public void movePlayerTwo(Tile piece, Tile targettile) {
		if (turn == 2 && isValidMove(piece, targettile)) {
			if (isJump(piece, targettile)) {
				jump(piece, targettile);
			}
			piece.setBlack();
			targettile.setPlayerTwo();
			setTurn(1);
		}
	}
	
	public void move(int x, int y) {
		if (getTurn() == 1)
					
			if (hasSelected() && getTile(x, y).isBlack())
				movePlayerOne(getSelectedPiece(), getTargetTile(x, y));
		
		if (getTurn() == 2)
			
			if (hasSelected() && getTile(x, y).isBlack())
				movePlayerTwo(getSelectedPiece(), getTargetTile(x, y));
	}
	
	private void jump(Tile piece, Tile targettile) {
		
		int x = piece.getX();
		int y = piece.getY();
		int dx = targettile.getX();
		int dy = targettile.getY();
	
		if (turn == 1)
			
			if ((isTile(x-1, y-1)) && (getTile(x-1, y-1).isPlayerTwo()) && (dx == x-2) && (dy == y-2))
				getTile(x-1, y-1).setBlack();
				piece.setBlack();
				targettile.setPlayerOne();
			if ((isTile(x-1, y+1)) && (getTile(x-1, y+1).isPlayerTwo()) && (dx == x-2) && (dy == y+2))
				getTile(x-1, y+1).setBlack();
				piece.setBlack();
				targettile.setPlayerOne();
			if ((isTile(x+1, y-1)) && (getTile(x+1, y-1).isPlayerTwo()) && (dx == x+2) && (dy == y-2))
				getTile(x+1, y-1).setBlack();
				piece.setBlack();
				targettile.setPlayerOne();
			if ((isTile(x+1, y+1)) && (getTile(x+1, y+1).isPlayerTwo()) && (dx == x+2) && (dy == y+2))
				getTile(x+1, y+1).setBlack();
				piece.setBlack();
				targettile.setPlayerOne();
			
		if (turn == 2)
			
			if ((isTile(x-1, y-1)) && (getTile(x-1, y-1).isPlayerOne()) && (dx == x-2) && (dy == y-2))
				getTile(x-1, y-1).setBlack();
				piece.setBlack();
				targettile.setPlayerTwo();
			if ((isTile(x-1, y+1)) && (getTile(x-1, y+1).isPlayerOne()) && (dx == x-2) && (dy == y+2))
				getTile(x-1, y+1).setBlack();
				piece.setBlack();
				targettile.setPlayerTwo();
			if ((isTile(x+1, y-1)) && (getTile(x+1, y-1).isPlayerOne()) && (dx == x+2) && (dy == y-2))
				getTile(x+1, y-1).setBlack();
				piece.setBlack();
				targettile.setPlayerTwo();
			if ((isTile(x+1, y+1)) && (getTile(x+1, y+1).isPlayerOne()) && (dx == x+2) && (dy == y+2))
				getTile(x+1, y+1).setBlack();
				piece.setBlack();
				targettile.setPlayerTwo();
	}
	
	private boolean isValidMove(Tile piece, Tile targettile) {
		
		int x = piece.getX();
		int y = piece.getY();
		int dx = targettile.getX();
		int dy = targettile.getY();
		
		if (!isTile(dx, dy))
			return false;
		if (!targettile.isBlack())
			return false;
		if (isJump(piece, targettile))
			return true;
		else if (targettile.isBlack() && (dx == x-1 && dy == y-1))
			return true;
		else if (targettile.isBlack() && (dx == x-1 && dy == y+1))
			return true;
		else if (targettile.isBlack() && (dx == x+1 && dy == y-1))
			return true;
		else if (targettile.isBlack() && (dx == x+1 && dy == y+1))
			return true;
 		return false;
	}
	
	private boolean isJump(Tile piece, Tile targettile) {
		int x = piece.getX();
		int y = piece.getY();
		int dx = targettile.getX();
		int dy = targettile.getY();
		
		if (turn == 1)
		
			if ((isTile(x-1, y-1)) && (getTile(x-1, y-1).isPlayerTwo()) && (dx == x-2) && (dy == y-2))
				return true;
			if ((isTile(x-1, y+1)) && (getTile(x-1, y+1).isPlayerTwo()) && (dx == x-2) && (dy == y+2))
				return true;
			if ((isTile(x+1, y-1)) && (getTile(x+1, y-1).isPlayerTwo()) && (dx == x+2) && (dy == y-2))
				return true;
			if ((isTile(x+1, y+1)) && (getTile(x+1, y+1).isPlayerTwo()) && (dx == x+2) && (dy == y+2))
				return true;
			
		if (turn == 2)
			
			if ((isTile(x-1, y-1)) && (getTile(x-1, y-1).isPlayerOne()) && (dx == x-2) && (dy == y-2))
				return true;
			if ((isTile(x-1, y+1)) && (getTile(x-1, y+1).isPlayerOne()) && (dx == x-2) && (dy == y+2))
				return true;
			if ((isTile(x+1, y-1)) && (getTile(x+1, y-1).isPlayerOne()) && (dx == x+2) && (dy == y-2))
				return true;
			if ((isTile(x+1, y+1)) && (getTile(x+1, y+1).isPlayerOne()) && (dx == x+2) && (dy == y+2))
				return true;
		
		return false;

	}
	
	private boolean isTile(int x, int y) {
        return 0 <= x && x < getWidth() && 0 <= y && y < getHeight();
    }
	
	public boolean hasSelected() {
		for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
            	if (getTile(x,y).isSelected())
            		return true;
            }
		}return false;
           
	}
	
	public boolean playerOneVictory() {
		for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
            	if (getTile(x,y).isPlayerTwo())
            		return false;
            }
		}return true;
	}
	
	public boolean playerTwoVictory() {
		for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
            	if (getTile(x,y).isPlayerOne())
            		return false;
            }
		}return true;
	}
    
    @Override
    public String toString() {
    	
    	String boardstring = "";
    	
    	for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
            	boardstring = boardstring + getTile(x, y).getType();
            }
            boardstring += "\n";
    	}
    	return boardstring;
    }
    
    public static void main(String args[]) {
    	Game game = new Game();
    	System.out.println(game);
    }
}
