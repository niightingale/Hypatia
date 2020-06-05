package hypatia;

import java.awt.*;

import static hypatia.Main.pen;

public class Morphogen {
    //Particle Properties
    int xPos;
    int yPos;
    int radius;
    Color color = Color.green;

    //Timer
    int ctr = 0;

    public Morphogen(Cell cell){
        int xy[] = Physics.polarng(1, cell.radius);
        xPos = xy[0] + cell.xPos;
        yPos = xy[1] + cell.yPos;
        radius = 1;

        Main.morphs.add(this);
    }
    public void lateUpate(){
        Physics.stepp(this, 20);

        ctr++;
        if(ctr>100){
            Main.morphQue.add(this);
        }
    }

    public void frameUpdate(){
        pen.drawCircle(xPos, yPos, radius, color, true);
    }
}
