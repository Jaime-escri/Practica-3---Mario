package tp1.logic;

import tp1.exceptions.PositionParseException;
import tp1.logic.Action;

/**
 * 
 * 
 */
public class Position {

	private int col;
	private int row;

	public Position(int row, int col){
		this.row = row;
		this.col = col;
	}


	public int getRow(){
		return row;
	}

	public int getCol(){
		return col;
	}

	public static Position move(Position pos, Action act){
		switch (act) {
			case LEFT:{
				return new Position(pos.getRow(), pos.getCol() - 1);
			}
			case DOWN:{
				return new Position(pos.getRow() + 1, pos.getCol());
			}
			case UP:{
				return new Position(pos.getRow() - 1, pos.getCol());
			}
			case RIGHT:{
				return new Position(pos.getRow(), pos.getCol() + 1);
			}
			default: 
				break;
		}

		return null;
	}

	@Override
	public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || obj.getClass() != getClass()) return false;
    Position p = (Position) obj;
    return row == p.row && col == p.col;
	}


	

    @Override
    public int hashCode() {
        return 31 * row + col;
    }

	public static Position parse(String s) throws PositionParseException {
		if (s == null) throw new PositionParseException("Invalid argument");

		s = s.trim();

		// Debe empezar por "(" y terminar por ")"
		if (!s.startsWith("(") || !s.endsWith(")"))
			throw new PositionParseException("Malformed argument");

		// Eliminar paréntesis
		String inside = s.substring(1, s.length() - 1).trim();

		// Dividir por coma
		String[] parts = inside.split(",");
		if (parts.length != 2)
			throw new PositionParseException("Expected two values in position");

		try {
			int row = Integer.parseInt(parts[0].trim());
			int col = Integer.parseInt(parts[1].trim());
			return new Position(row, col);
		}
		catch (NumberFormatException e) {
			throw new PositionParseException("Invalid number format in position", e);
		}
	}



}
