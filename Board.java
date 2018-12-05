/**
 * This class represents one board state of the Four In A Line game. A board is
 * an 8x8 2D array. The number of empty spaces on the board is also kept track
 * of.
 */
/**
 * @author Oakar Kyaw
 *
 */
public class Board {
	int[][] board;
	int availableSpot;

	/* Board constructor */
	public Board(int size) {
		board = new int[size][size];
		availableSpot = size*size;
	}

	public int[][] getBoard() {
		return board;
	}

	public boolean isFull() {
		return (availableSpot==0)? true: false;
	}

//	public int isDone() {
//		for (int i = 0; i < board.length; i++) {
//			for (int j = 0; j < board.length; j++) {
//				if (board[i][j] != 0 ) {
//					if (checkFour(i, j, board[i][j])) {
//						return board[i][j];
//					}
//				}
//			}
//		}
//		return 0;
//	}
	public int isDone() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				if (board[i][j] != 0 ) {
					int count = 0;
					if(i+4 < board.length) {
						for(int x=i; x<i+4; x++) {
							if(board[x][j] != board[i][j]) {
								continue;
							}
							count++;
						}
						if(count == 4) {
							return board[i][j];
						}
					}
					count = 0;
					if(j+4 < board.length) {
						for(int x=j; x<j+4; x++) {
							if(board[i][x] != board[i][j]) {
								continue;
							}
							count++;
						}
						if(count == 4) {
							return board[i][j];
						}
					}
				}
			}
		}
		return 0;
	}

	private boolean checkFour(int row, int col, int player) {
		if (row + 4 <= board.length && checkCol(row, col, player)) {
			return true;
		}
		if (col + 4 <= board.length && checkRow(row, col, player)) {
			return true;
		}
		return false;
	}


	private boolean checkRow(int row, int col, int player) {
		for (int i = col; i < col + 4; i++) {
			if (board[row][i] != player) {
				return false;
			}
		}
		return true;
	}


	private boolean checkCol(int row, int col, int player) {
		for (int i = row; i < row + 4; i++) {
			if (board[i][col] != player) {
				return false;
			}
		}
		return true;
	}
	public boolean isValid(int row, int col) {
		if (row < 0 || col < 0 || row >= board.length || col >= board.length || board[row][col] != 0) {
			return false;
		}
		return true;
	}

	public void set(int row, int col, int value) {
		board[row][col] = value;
		availableSpot--;
	}


	public void undoMove(int row, int col) {
		board[row][col] = 0;
		availableSpot++;
	}


	public void printBoard() {
		for (int i = 0; i < board.length+1; i++) {
			for (int j = 0; j < board.length+1; j++) {
				if(i==0) {
					System.out.print(j + " ");
				}else {
					if (j==0) {
						System.out.print(i + " ");
					}else {
						if (board[i-1][j-1] == 0) {
							System.out.print("- ");
						} else if (board[i-1][j-1] == 1) {
							System.out.print("X ");
						} else {
							System.out.print("O ");
						}
					}
				}
			}
			System.out.println();
		}

	}


}