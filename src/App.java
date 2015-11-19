import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by calin on 11/17/15.
 */
public class App extends JFrame implements KeyListener{
    int prevRow = 0, prevColumn = 0;
    int currentRow = 0, currentColumn = 0;
    int nrOfSteps;
    int currentStep;
    long startTime = 0;
    CellGrid grid;
    Cell currentCell;

    public App(){
        super("Capcha");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        grid = new CellGrid(5, 5); // size of grid
        nrOfSteps = grid.getNrOfSteps();
        currentStep = 1;
        add(grid);

        addKeyListener(this);
        setVisible(true);
        pack();

    }
    public static void main(String[] args){
        App myApp = new App();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        currentCell = grid.getCurrentCell(currentRow, currentColumn);
        if(currentCell.getState() == "cursor"){
            currentCell.setState("completed");
        }

        prevRow = currentRow;
        prevColumn = currentColumn;

        switch (e.getKeyCode()){
            case KeyEvent.VK_UP: // N
                currentRow--;
                break;
            case KeyEvent.VK_DOWN: // S
                currentRow++;
                break;
            case KeyEvent.VK_RIGHT: // E
                currentColumn++;
                break;
            case KeyEvent.VK_LEFT: // W
                currentColumn--;
                break;
        }

        // Handles the "Next position out of screen" errors
        try{
            currentCell = grid.getCurrentCell(currentRow, currentColumn);
        }catch(ArrayIndexOutOfBoundsException ex){
            currentColumn = prevColumn;
            currentRow = prevRow;
            currentStep--;
        }

        if(grid.isValid(currentRow, currentColumn)) {
            grid.getCurrentCell(currentRow, currentColumn).setState("cursor");
            currentStep++;
            System.out.println(currentStep + " / " + nrOfSteps);
        }else{
            resetCapcha();
            currentStep = 1;
        }

        if(currentStep == nrOfSteps) {
            long currentTime = System.currentTimeMillis();
            double dt = (currentTime - startTime)/1000.0;

            // Formats my number to two decimal places + s(econd)
            DecimalFormat df = new DecimalFormat("#.##s");
            df.setRoundingMode(RoundingMode.CEILING);

            JOptionPane.showMessageDialog(this, "Capcha entered correctly! It took you " + df.format(dt));
            this.setEnabled(false); // disables the window.
        }

        if(currentStep == 2){
            startTime = System.currentTimeMillis();
        }
    }

    // Resets the capcha. It means wrong move was taken
    private void resetCapcha() {
        currentRow = 0;
        currentColumn = 0;
        grid.resetGrid();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
