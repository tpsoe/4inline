import java.util.Scanner;

public class Main {
	static int BOARD_SIZE = 8;
	static int TIME_LIMIT = 5000;
	static int DEPTH =20;
	public static void main( String[] args ){

		Scanner keyboard = new Scanner (System.in);
		Board board = new Board(BOARD_SIZE);
		boolean done = false;
		boolean playerMove;		

		System.out.print("Enter A for AI or P for Player: ");
		String input = keyboard.nextLine();


		while (!input.toLowerCase().equals("a") && !input.toLowerCase().equals("p") ) {
			System.out.print("Invalid input! Enter A for AI or P for Player!\n:");
			input = keyboard.nextLine();
		}

		if(!input.toLowerCase().equals("a")) {
			playerMove = true;
		}else {
			playerMove = false;
		}

		board.printBoard();

		while(!done) {
			if(board.isFull()) {
				break;
			}
			if(playerMove) {
				System.out.print("Enter the move: ");
				input = keyboard.nextLine();
				int row = input.charAt(0) - 49;    	
				int col = input.charAt(1) - 49;
				while(!board.isValid(row, col)) {
					System.out.print( "Invalid movie. \nEnter the move: " );                 
					input = keyboard.nextLine();
					row = input.charAt(0) - 49;    	
					col = input.charAt(1) - 49;
				}
				board.set(row, col, 1);
				playerMove = false;
			}else {
				System.out.println("Ai Thinking!");
				int[] aImove = minMax(board);
				board.set(aImove[0], aImove[1], -1);
				System.out.println("AI move: " + (aImove[0]+1) + " " + (aImove[1]+1));
				playerMove = true;
			}//end if else


			board.printBoard();
			if(board.isDone() != 0) {
				done = true;
			}
		}//end while
		int winner = board.isDone();
		if(winner == 1) {
			System.out.println("Player Wins!");
		}else if(winner == -1) {
			System.out.println("Ai Wins!");
		}else {
			System.out.println("It's a Tie!");
		}
	}//end main

	public static int[] minMax(Board b){
		int score;
		long startTime = System.currentTimeMillis();
		int[] aImove = new int[2];

		int best;
		if(b.isEmpty()) {
			best = Integer.MAX_VALUE;
		}else {
			best = calcScore(b);
		}
		
		for( int i = 0; i < BOARD_SIZE; i++ ){
			for( int j = 0; j < BOARD_SIZE; j++ ){
				if(b.board [i][j] == 0 ){
					b.set( i, j, 1 );
					score = minVal( b, DEPTH-1, startTime );
					b.unSet( i, j );
					if( score > best ){
						aImove[0] = i;
						aImove[1] = j;
						best = score;
					}
				}
			}
		}
		
		
		return aImove;

	}//end min max

	private static int minVal( Board b, int d, long startTime ){
		if( System.currentTimeMillis() - startTime > TIME_LIMIT ){
			return calcScore( b );
		}
	
		if( b.isDone() == -1 ){
			return Integer.MIN_VALUE / 2;
		}else if( b.isDone() == 1 ){
			return Integer.MAX_VALUE / 2;
		}


		int best = Integer.MAX_VALUE;
		for( int i = 0; i < BOARD_SIZE; i++ ){
			for( int j = 0; j < BOARD_SIZE; j++ ){
				if( b.getBoard()[i][j] == 0 ){
					b.set( i, j, 1 );
					best = Integer.min( best, maxVal( b, d-1, startTime ) );
					b.unSet( i, j );
				}
			}
		}
		return best;
	}


	private static int maxVal( Board b, int d, long startTime ){
		if( System.currentTimeMillis() - startTime > TIME_LIMIT ){
			return calcScore( b );
		}

		if( b.isDone() == -1 ){
			return Integer.MIN_VALUE / 2;
		}else if( b.isDone() == 1 ){
			return Integer.MAX_VALUE / 2;
		}

		int best = Integer.MIN_VALUE;
		for( int i = 0; i < BOARD_SIZE; i++ ){
			for( int j = 0; j < BOARD_SIZE; j++ ){
				if( b.getBoard()[i][j] == 0 ){
					b.set( i, j, -1 );
					best = Integer.max( best, minVal( b,d-1, startTime) );
					b.unSet( i, j );
				}
			}
		}
		return best;
	}

	private static int calcScore( Board b ){
		int score = 0;
		for( int i = 0; i < BOARD_SIZE; i++ ){
			for( int j = 0; j < BOARD_SIZE; j++ ){
				score += potential( i, j, b );
			}
		}
		return score;
	}


	private static int potential( int row, int col, Board b ){
		int check = b.getBoard()[row][col];
		int score = 0;
		int temp = 0;


		if( check > 0 ){
			check = -1;
			b.getBoard()[row][col] = -1;
			if( b.isDone() == -1 ){
				score += 10000;
			}
			b.getBoard()[row][col] = 1;
		}



		if( row >= 3 ){
			for( int i = 1; i < 4; i++ ){
				if( b.getBoard()[row-i][col] == check ){
					temp += 5 - i;
				}
				else if( b.getBoard()[row-i][col] != 0 ){
					temp = -1;
					i = 4;
				}
			}
			if( row < BOARD_SIZE - 1 ){
				if( temp == 7 && b.getBoard()[row+1][col] == 0 ){
					temp = 10000;
				}
			}
			if( temp == 9 ){
				temp = 9990;
			}
			score += temp;
			temp = 0;
		}
		else{
			score--;
		}

		if( row < BOARD_SIZE - 3 ){
			for( int i = 1; i < 4; i++ ){
				if( b.getBoard()[row+i][col] == check ){
					temp += 5 - i;
				}
				else if( b.getBoard()[row+i][col] != 0 ){
					temp = -1;
					i = 4;
				}
			}
			if( row > 0 ){
				if( temp == 7 && b.getBoard()[row-1][col] == 0 ){
					temp = 10000;
				}
			}
			if( temp == 9 ){
				temp = 9990;
			}
			score += temp;
			temp = 0;
		}
		else{
			score--;
		}

		if( col >= 3 ){
			for( int i = 1; i < 4; i++ ){
				if( b.getBoard()[row][col-i] == check ){
					temp += 5 - i;
				}
				else if( b.getBoard()[row][col-i] != 0 ){
					temp = -1;
					i = 4;
				}
			}
			if( col < BOARD_SIZE - 1 ){
				if( temp == 7 && b.getBoard()[row][col+1] == 0 ){
					temp = 10000;
				}
			}
			if( temp == 9 ){
				temp = 9990;
			}
			score += temp;
			temp = 0;
		}
		else{
			score--;
		}

		if( col < BOARD_SIZE - 3 ){
			for( int i = 1; i < 4; i++ ){
				if( b.getBoard()[row][col+i] == check ){
					temp += 5 - i;
				}
				else if( b.getBoard()[row][col+i] != 0 ){
					temp = -1;
					i = 4;
				}
			}
			if( col > 0 ){
				if( temp == 7 && b.getBoard()[row][col-1] == 0 ){
					temp = 10000;
				}
			}
			if( temp == 9  ){
				temp = 9990;
			}
			score += temp;
		}
		else{
			score--;
		}


		if( true ){
			if( row >= 1 && row < BOARD_SIZE - 1 && col >= 1 && col < BOARD_SIZE - 1 ){
				if( b.getBoard()[row+1][col+1] == check || b.getBoard()[row+1][col-1] == check || b.getBoard()[row-1][col+1] == check || b.getBoard()[row-1][col-1] == check ){
					score++;
				}
			}
			check = b.getBoard()[row][col];
		}

		return score * check;
	}
}

