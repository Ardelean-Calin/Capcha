import javax.swing.*;
import java.awt.*;

/**
 * Created by calin on 11/17/15.
 */
public class Cell extends JPanel{
    Color cellColor;
    String state;

    public Cell(){
        this("none");
    }

    public Cell(String state){
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        setPreferredSize(new Dimension(50, 50));
        setState(state);
    }

    public String getState() {
        return state;
    }

    public void updateCell(){
        setBackground(cellColor);
    }

    public void setState(String state){
        if (state.equalsIgnoreCase("path"))
            cellColor = Color.RED;
        else if(state.equalsIgnoreCase("completed"))
            cellColor = Color.GREEN;
        else if(state.equalsIgnoreCase("cursor"))
            cellColor = Color.YELLOW;
        else
            cellColor = Color.LIGHT_GRAY;

        this.state = state.toLowerCase();

        updateCell();
    }
}
