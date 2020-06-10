package hypatia;

import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSOutput;

import java.awt.*;
import java.util.Random;

import static hypatia.Main.pen;

public class Morphogen {
    //Particle Properties
    int xPos;
    int yPos;
    int radius;
    Color color = Color.green;

    //Decay
    double decayChance = 0.01;

    public Morphogen(Cell cell){
        int xy[] = Physics.polarng(1, cell.radius);
        xPos = xy[0] + cell.xPos;
        yPos = xy[1] + cell.yPos;
        radius = 1;

        Main.morphs.add(this);
    }
    public void lateUpate(){
        Physics.stepp(this, 20);

        Random rng = new Random();
        double roll = rng.nextDouble();
        if(roll <= decayChance){
            Main.morphDelQue.add(this);
        }
    }

    public void frameUpdate(){
        pen.drawCircle(xPos, yPos, radius, color, true);
    }
}
