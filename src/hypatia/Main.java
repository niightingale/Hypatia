package hypatia;
import nano.*;
import nano.Canvas;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    static int call = 0;

    static int xSize = 1000;
    static int ySize = 500;

    static Canvas frustrum = new Canvas(xSize, ySize,0,0);
    static Pen pen = new Pen(frustrum);

    static ArrayList<Cell> cells = new ArrayList<Cell>();
    static ArrayList<Cell> cellQue = new ArrayList<Cell>();

    static ArrayList<Morphogen> morphs = new ArrayList<Morphogen>();

    public static void main(String[] args) {
        for(int j = 1; j<2; j++){
            for(int i = 1; i<9; i++){
                Cell newCell = new Cell(50*j,50*i, 15, true);
                cells.add(newCell);
            }
        }
        Scanner sc = new Scanner(System.in);
        System.out.println("Press enter to continue.");
        sc.nextLine();
/*
        Cell firstMan = new Cell(50,250,20);
        Cell ndman = new Cell(50,180,20);
        Cell ndman2 = new Cell(50,320,20);
        Cell blockr = new Cell(200,250,20);
        blockr.fertile = true;
        cells.add(firstMan);
        cells.add(ndman);
        cells.add(ndman2);
        cells.add(blockr);*/

        //Cell fistMan = new Cell(500,250, 20);
        //Cell secMan = new Cell(500, 210, 100);
        //cells.add(fistMan);
        //cells.add(secMan);

        //Execution order of Method Calls
	    while(true){
	        call++;
	        System.out.println("Frame: " + call);
            System.out.println("Mpg: " + morphs.size());
	        frustrum.clear();
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
