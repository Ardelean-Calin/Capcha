import java.util.Random;

/**
 * Project: Capcha
 * Created by calin on 11/17/15.
 */
public class Pattern {
    int r, c;
    int[][] patternMatrix; // [r][c]
    Random randomGen;

    public Pattern(){
        this(10, 10);
    }

    public Pattern(int rows, int columns){
        r = rows;
        c = columns;
        randomGen = new Random();
        initializePattern();
        generateMatrix();
    }

    // returns the generated pattern
    public int[][] getPattern(){
        return patternMatrix;
    }

    public void printMatrix(){
        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                System.out.print(patternMatrix[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    // Checks if the given row/column exists
    private boolean isOutOfBounds(int row, int col){
        try {
            int x = patternMatrix[row][col];
        }catch (ArrayIndexOutOfBoundsException e){
            return true; // it means it's out of bounds
        }

        return false;
    }

    private boolean isValid(int row, int col){
        int neighbors = 0; // we assume no neighbors
        int[][] coords = {
                {row, col},
                {row - 1, col}, //N
                {row + 1, col}, //S
                {row, col + 1}, //E
                {row, col - 1}, //W
        };

        // we need to check the current position as well as NSEW
        for(int[] cell: coords) {
            if (isOutOfBounds(cell[0], cell[1])) {
                continue; // row, col is not a position I need to care about
            }else if (patternMatrix[cell[0]][cell[1]] == 1) {
                neighbors++;
            }
        }

        if(neighbors > 1 || isOutOfBounds(row, col))
            return false;

        return true;
    }

    private void generateMatrix() {
        boolean validDirections[] = {true, true, true, true}; // NSEW
        int cX = 0, cY = 0;
        int nextX, nextY;


        // While at least one of my directions is still unchecked
        while(isFalse(validDirections) == false){
            int r = randomGen.nextInt(4);
            nextX = cX;
            nextY = cY;

            switch (r){
                case 0: // N
                    nextX--;
                    break;
                case 1: // S
                    nextX++;
                    break;
                case 2: // E
                    nextY++;
                    break;
                case 3: // W
                    nextY--;
                    break;
            }

            validDirections[r] = false;

            if(isValid(nextX, nextY)){
                cX = nextX;
                cY = nextY;
                patternMatrix[cX][cY] = 1;
                validDirections[0] = true;
                validDirections[1] = true;
                validDirections[2] = true;
                validDirections[3] = true;
            }

        }
    }

    private boolean isFalse(boolean[] list){
        int c = list.length;
        for (boolean value: list)
            if(value == false)
                c--;
        if(c == 0)
            return true;
        return false;
    }

    // Fills my matrix with 0's
    private void initializePattern() {
        patternMatrix = new int[r][c];
        for (int i = 0; i < c; i++) {
            patternMatrix[i] = new int[r];
            for (int j = 0; j < r; j++) {
                patternMatrix[i][j] = 0;
            }
        }
        patternMatrix[0][0] = 1;
    }

}
