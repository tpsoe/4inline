public class Action {
    private int row;
    private int col;

    /* Action constructor */
    public Action( int r, int c ){
        row = r;
        col = c;
    }

    /**
     * Returns the row.
     */
    public int getRow(){
        return row;
    }

    /**
     * Returns the column.
     */
    public int getCol(){
        return col;
    }

    /**
     * Converts the action to a String.
     */
    public String toString(){
        return "" + (char)(row + 65) + (col + 1 );
    }
}