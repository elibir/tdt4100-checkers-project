package checkers;

public class Tile {
	
	private int x;
	private int y;
	private char type;
	private boolean selected;
	
	public Tile(int x, int y) {
		if (x < 0 || y < 0)
			throw new IllegalArgumentException("A tile must have a positive value.");
		this.x = x;
		this.y = y;
	}
	
    public void setType(char symbol) {
    	if ("12bw".indexOf(symbol) == -1) {
    		throw new IllegalArgumentException("Not a valid state");
    	}
    	
    	type = symbol;
    } 
    
    public void setBlack() {
    	type = 'b';
    }
    
    public void setWhite() {
    	type = 'w';
    }
    
    public void setPlayerOne() {
    	type = '1';
    }
    
    public void setPlayerTwo() {
    	type = '2';
    }
    
	public void setSelected() {
		this.selected = true;
	}
	public void removeSelected() {
		this.selected = false;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public char getType() {
		return type;
	}

	
	public boolean isSelected() {
		return selected;
	}

	public boolean isBlack() {
		return type == 'b';
	}
	
	public boolean isWhite() {
		return type == 'w';
	}
	
	public boolean isPlayerOne() {
		return type == '1';
	}
	
	public boolean isPlayerTwo() {
		return type == '2';
	}


}
