package wall_avoider.Game;

public class Map {

	private int[][] map;
	private int[] zeroRow, bottomRow;
	private Character character;
	private int row, column;

	public Map() {
		row = 10;
		column = 7;
		map = new int[10][7];
		zeroRow = new int[7];
		bottomRow = new int[7];
		for (int i = 0; i < 7; i++)
			zeroRow[i] = 0;
	}

	public void insertWall(int[] wall) {
		for (int i = 0; i < column; i++)
			map[0][i] = wall[i];
	}

	public void initializeMap(Character character) {
		this.character = character;
		character.setLocation((int) (Math.random() * 7));
		map[9][character.getLocation()] = character.getMapValue();
	}

	public int[] createWall(int numberOfVoids) {
		int[] wall = new int[7];
		int placeOfTheFirstVoid = (int) (Math.random() * (wall.length - numberOfVoids + 1));
		for (int i = 0; i < wall.length; i++)
			wall[i] = 1;
		for (int i = 0; i < numberOfVoids; i++) {
			wall[placeOfTheFirstVoid] = 0;
			placeOfTheFirstVoid++;
		}
		return wall;
	}

	public boolean isGameOver() {
		boolean over = false;
		if (character.isDead())
			over = true;
		return over;
	}

	public void slide() {
		for (int i = row - 1; i > 0; i--)
			for (int j = 0; j < column; j++)
				map[i][j] = map[i - 1][j];
		for (int i = 0; i < column; i++)
			map[0][i] = 0;
	}

	public void summation() {
		map[9][character.getLocation()] = map[9][character.getLocation()] + character.getMapValue();
		character.setMapValue(map[row - 1][character.getLocation()]);
	}

	public void update() {
		slide();
		summation();
	}

	public int[][] getMap() {
		return map;
	}

	public int[] getBottomRow() {
		return bottomRow;
	}

	public int[] getInput() {
		int[] input = new int[5];
		outerloop: for (int i = 9; i >= 0; i--) {
			for (int j = 0; j < 7; j++) {
				if (map[i][j] == 1) {
					if (character.getLocation() == 0) {
						for (int k = 0; k < 3; k++)
							if (map[i][k] == 2)
								input[k + 1] = 0;
							else if (map[i][k] == 3)
								input[k + 1] = 1;
							else {
								if (k == 0)
									input[k + 1] = 1;
								else
									input[k + 1] = map[i][k];
							}
						input[0] = 1;
						input[4] = 0;
					} else if (character.getLocation() == 6) {
						for (int k = 4, l = 0; k < 7 && l < 3; k++, l++)
							if (map[i][k] == 2)
								input[l + 1] = 0;
							else if (map[i][k] == 3)
								input[l + 1] = 1;
							else {
								if (k == 6)
									input[l + 1] = 1;
								else
									input[l + 1] = map[i][k];
							}
						input[0] = 0;
						input[4] = 1;
					} else {
						for (int k = character.getLocation() - 1, l = 0; k < character.getLocation() + 2
								&& l < 3; k++, l++)
							if (map[i][k] == 2)
								input[l + 1] = 0;
							else if (map[i][k] == 3)
								input[l + 1] = 1;
							else
								input[l + 1] = map[i][k];
						input[0] = 0;
						input[4] = 0;

					}
					break outerloop;
				}
			}
		}
		return input;
	}

	public Character getCharacter() {
		return character;
	}

	public String toString() {
		String mapping = "";
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 7; j++)
				mapping = mapping + map[i][j] + " ";
			mapping = mapping + "\n";
		}
		return mapping;
	}
}
