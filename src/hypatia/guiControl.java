package hypatia;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;


public class guiControl {
    public CheckBox left = null;
    public CheckBox right = null;
    public CheckBox top = null;
    public CheckBox bottom = null;

    public void pressStart(ActionEvent event){
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
}
