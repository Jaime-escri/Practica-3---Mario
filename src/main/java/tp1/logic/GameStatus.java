package tp1.logic;

public interface GameStatus {
    public final int DIM_X = 30;
	public final int DIM_Y = 15;

    public int remainingTime();
    public int points();
    public int numLives();
    public String positionToString(int i, int j);
    public boolean playerWins();
    public boolean playerLoses();
}
