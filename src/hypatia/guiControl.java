package hypatia;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class guiControl {
    public CheckBox left = null;
    public CheckBox right = null;
    public CheckBox top = null;
    public CheckBox bottom = null;

    public TextField resBox = null;
    public TextField cellDox = null;

    public void pressStart(ActionEvent event){
        //Window Size
        ArrayList<Integer> coordinates = new ArrayList<>();

        try{
            coordinates = checkString(resBox.getText(), 2);
            Main.changeWindow(coordinates.get(0), coordinates.get(1));
        }
        catch(Exception c){
            System.out.println("A value wasn't provided.");
        }
        //Custom Cells
        ArrayList<Integer> cellCoords = new ArrayList<>();

        try{
            cellCoords = checkString(cellDox.getText(), 0);

            ArrayList<Cell> injectCells = new ArrayList<>();

            for(int i =0; i<cellCoords.size()-1; i+=2){
                Cell iCell = new Cell(cellCoords.get(i), cellCoords.get(i+1), 15, true);
                injectCells.add(iCell);
            }
            Main.cells.addAll(injectCells);
        }
        catch(Exception e){
            System.out.println("A value wasn't provided for injection." + e);
        }

        //Spawn Siding
        char[] input = new char[4];
        if(left.isSelected())
            input[0]=49;
        else{
            input[0]=48;
        }
        if(right.isSelected())
            input[1]=49;
        else{
            input[1]=48;
        }
        if(top.isSelected())
            input[2]=49;
        else{
            input[2]=48;
        }
        if(bottom.isSelected())
            input[3]=49;
        else{
            input[3]=48;
        }
        Stage stage = (Stage) left.getScene().getWindow();
        stage.close();
        Main.Program(input);
    }

    public ArrayList<Integer> checkString(String fillIn, int expected){
        ArrayList<Integer> retGer = new ArrayList<>();
        String nline = fillIn.replaceAll("\\s", ""); //Removing spaces
        for (String s : nline.split(",")) {
            retGer.add(Integer.parseInt(s));
        }

        if(retGer.size() == expected){
            return retGer;
        }
        else if(even(retGer.size()) && expected == 0){
            return retGer;
        }
        return null;
    }

    public boolean even(int toCheck){
        if(toCheck % 2 == 0)
            return true;
        else
            return false;
    }
}
