import javax.swing.*;
import java.awt.*;

/**
 * Project: Capcha
 * Created by calin on 11/17/15.
 */
public class CellGrid extends JPanel {
    int[][] pattern;
    Cell[][] cells;
    int rows, cols;
    int currentR, currentC;
    int nrOfSteps = 0;

    // Implicit constructor
    public CellGrid(){
        this(10, 10);
    }

    // Explicit constructor
    public CellGrid(int rows, int columns){
        this.rows = rows;
        this.cols = columns;

        // At the beginning I'm at the origin.
        currentR = 0;
        currentC = 0;

        setLayout(new GridLayout(rows, columns));
        Pattern p = new Pattern(rows, columns);
        pattern = p.getPattern();
        generateCells();
        populateGrid();

        setVisible(true);
    }

    // Sets my current position in the grid.
    public void setPos(int row, int col){
        currentR = row;
        currentC = col;
    }

    // Resets the path
    public void resetGrid(){
        populateGrid();
    }

    // Returns the number of path blocks
    public int getNrOfSteps() {
        return nrOfSteps;
    }

    // Tells me if the given row, col is a valid path block
    public boolean isValid(int row, int col){
        if (pattern[row][col] == 1)
            return true;
        else
            return false;
    }

    // Returns the cell at my current position
    public Cell getCurrentCell(int row, int col){
        return cells[row][col];
    }

    // Generates a <rows> by <cols> cell matrix to correspond
    //  with my pattern.
    private void generateCells() {
        cells = new Cell[rows][cols];
        for(int i = 0; i < rows; i++){
            for (int j = 0; j < cols; j++){
                cells[i][j] = new Cell();
                // eventual pot da set dimension aici
            }
        }
    }

    // Populates the grid with the randomly generated pattern
    // such that RED cells are path blocks and gray cells are
    // inactive/non-path blocks
    private void populateGrid() {
        for (int r = 0; r < rows; r++){
            for (int c = 0; c < cols; c++){
                add(cells[r][c]);
                if(pattern[r][c] == 1) {
                    cells[r][c].setState("path");
                    nrOfSteps++;
                }
            }
        }

        cells[0][0].setState("cursor");
    }

}
