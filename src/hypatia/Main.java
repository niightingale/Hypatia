package hypatia;
import nano.*;
import nano.Canvas;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static int call = 0;

    static int xSize = 1000;
    static int ySize = 600;

    static Canvas frustrum = new Canvas(xSize, ySize,0,0);
    static Pen pen = new Pen(frustrum);

    static ArrayList<Cell> cells = new ArrayList<Cell>();
    static ArrayList<Cell> cellQue = new ArrayList<Cell>();

    static ArrayList<Morphogen> morphs = new ArrayList<Morphogen>();
    static ArrayList<Morphogen> morphDelQue = new ArrayList<>();

    public static void main(String[] args) {
        Program();
    }
    public static void Program(){

        char[] input = query();
        preWarm(input);

        Scanner sc = new Scanner(System.in);
        System.out.println("Press any button to start the simulation.");
        sc.nextLine();

        //Execution order of Method Calls
        while(true){
            //COUNTERS
            call++;
            System.out.println("Frame: " + call);
            System.out.println("Mpg: " + morphs.size());
            frustrum.clear();

            //EXECUTION
            for(Cell cell:cells){
                cell.update();
            }
            cells.addAll(cellQue);
            for(Cell cell:cells){
                cell.lateUpdate();
            }
            for(Morphogen m:morphs){
                m.lateUpate();
            }
            morphs.removeAll(morphDelQue);
            for(Cell cell:cells){
                cell.frameUpdate();
            }
            for(Morphogen m:morphs){
                m.frameUpdate();
            }

            frustrum.update();
            frustrum.pause(100);
            cellQue.clear();
        }
    }

    //Queries the user
    public static char[] query(){
        char[] orient= new char[4];
        System.out.println("\nPlease denote the sides at which you wish the cells to start using " +
                "following structure in binary operators:\n LRTB (left, right top bottom) \n" +
                "This means that 1010 means 'left' and 'top'.");
        Scanner sc = new Scanner(System.in);
        String fill = sc.nextLine();
        //Test for too many digits or digits that are not 0 or 1.
        try{
            if(fill.length() != 4)
                throw new Exception();
            orient = fill.toCharArray();
            for(char ch:orient){
                if(ch < 48 || ch > 49)
                    throw new Exception();
            }
        }
        catch(Exception e){
            System.out.println("The digits were invalid, the program will restart.");
            Program();
        }
        System.out.println("You entered: " + fill);
        return orient;
    }

    //Spawns the cells
    public static void preWarm(char[] setting){
        int offSet = 40;

        int xSpawnInit = 25;
        int ySpawnInit = 25;

        int xSpawn = xSpawnInit;
        int ySpawn = ySpawnInit;

        //LEFT SPAWN
        if(setting[0] == 49){
            while(ySpawn <= ySize - offSet){
                Cell newCell = new Cell(xSpawn, ySpawn, 15, true);
                cells.add(newCell);
                ySpawn += offSet;
            }
        }
        //RIGHT SPAWN
        if(setting[1] ==49){
            xSpawn = xSize - xSpawnInit;
            ySpawn = ySpawnInit;
            while(ySpawn <= ySize - offSet){
                Cell newCell = new Cell(xSpawn, ySpawn, 15, true);
                cells.add(newCell);
                ySpawn += offSet;
            }
        }
        //TOP SPAWN
        if(setting[2] == 49){
            xSpawn = xSpawnInit + offSet;
            ySpawn = ySize - ySpawnInit;
            while(xSpawn <= xSize - offSet){
                Cell newCell = new Cell(xSpawn, ySpawn, 15, true);
                cells.add(newCell);
                xSpawn += offSet;
            }
        }
        //BOTTOM SPAWN
        if(setting[3] == 49){
            xSpawn = xSpawnInit + offSet;
            ySpawn = ySpawnInit;
            while(xSpawn <= xSize - offSet){
                Cell newCell = new Cell(xSpawn, ySpawn, 15, true);
                cells.add(newCell);
                xSpawn += offSet;
            }
        }
    }
}
/*NOTES GENERAL
The order of method calls is update>lateUpdate>frameUpdate. This is because first new cells are spawned, as this
is required for 'a priori' collision detection - or in other words; we don't want the cells that will spawn the next
frame to already be overlapping. Then 'lateUpdate' makes them move
 */

/*NOTES PROBLEMS

1 - /PROBLEM - FIXED\
Clearing the frustrum seems to cause pulsating of the image. Why it is this way is not fully clear,
probably because of the amount of checks which have to be done.

1 - /FIX\
    To solve this, frustrum.clear() must be called BEFORE the for loop that calls all the update methods and
    frustrum.update() must be called AFTER all particles have updated.

2 - /PROBLEM - FIXED\
The collision behaviour didn't function as expected. This was due to a logic error in the if/else statement
at its end.

2 - /FIX\
This was fixed by revisiting this logic and correcting the mistake.

 */
