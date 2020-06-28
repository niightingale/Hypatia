package hypatia;

import java.util.Random;

public class Physics {

    static Random rng = new Random();

    static int dxT = 1;
    static int dyT = 1;

    //Returns randomised step. First X, then Y
    public static void step(Cell cell, int stepSpeed){
        boolean atEdgeOverride = true;

        boolean rightStep;
        boolean upStep;

        int dX;
        int dY;

        //Edge detection
        if (cell.xPos + cell.radius >= Main.xSize)
            rightStep = false;
        else if (cell.xPos - cell.radius <= 0)
            rightStep = true;
        else{
            rightStep = rng.nextBoolean();
            atEdgeOverride = false;
        }

        if (cell.yPos + cell.radius >= Main.ySize)
            upStep = false;
        else if (cell.yPos - cell.radius <= 0)
            upStep = true;
        else{
            upStep = rng.nextBoolean();
            atEdgeOverride = false;
        }

        //Generation of random step values
        if(rightStep)
            dX = rng.nextInt(dxT)*stepSpeed + 1;
        else
            dX = -rng.nextInt(dxT)*stepSpeed - 1;
        if(upStep)
            dY = rng.nextInt(dyT)*stepSpeed + 1;
        else
            dY = -rng.nextInt(dyT)*stepSpeed - 1;

        //Check particle collision - CONTACT INHIBITION
        boolean hasMade = false;

        if(!atEdgeOverride){
            for(Cell oCell:Main.cells){
                double distNow = Math.sqrt(Math.pow((cell.xPos - oCell.xPos), 2) + Math.pow((cell.yPos - oCell.yPos), 2));
                double distLate = Math.sqrt(Math.pow((cell.xPos + dX - oCell.xPos), 2) + Math.pow((cell.yPos + dY - oCell.yPos), 2));

                //First condition might be redundant
                if(distNow > distLate && distLate < (double)(cell.radius + oCell.radius) && oCell != cell){
                    dX = 0;
                    dY = 0;
                    makeStep(cell,dX,dY);
                    hasMade = true;
                    break;
                }
            }
        }
        else{
            makeStep(cell,dX,dY);
        }

        //If the step hasn't been made because nothing is close
        if(!hasMade){
            makeStep(cell,dX,dY);
        }
    }

    //Makes the actual xPos and yPos change
    public static void makeStep(Cell cell, int dX, int dY){
        cell.xPos += dX;
        cell.yPos += dY;
    }

    //Returns a random X and Y within a circle. Outerbound is the outer 'r' limit while selfRadius provides us an inner limit.
    public static int[] polarng(int outerBound, int selfRadius){
        int[] xy = new int[2];

        double r = rng.nextInt(outerBound) + 2 * selfRadius;
        double t = rng.nextDouble() * 2* Math.PI;

        xy[0] = (int)(r * Math.cos(t));
        xy[1] = (int)(r * Math.sin(t));

        return xy;
    }

    public static void morphStep(Morphogen m, int stepSpeed){
        boolean rightStep;
        boolean upStep;

        int dX;
        int dY;

        //Edge detection
        if (m.xPos + m.radius >= Main.xSize)
            rightStep = false;
        else if (m.xPos - m.radius <= 0)
            rightStep = true;
        else{
            rightStep = rng.nextBoolean();
        }

        if (m.yPos + m.radius >= Main.ySize)
            upStep = false;
        else if (m.yPos - m.radius <= 0)
            upStep = true;
        else{
            upStep = rng.nextBoolean();
        }

        //Generation of random step values
        if(rightStep)
            dX = rng.nextInt(dxT) + (1 + stepSpeed);
        else
            dX = -rng.nextInt(dxT) - (1 + stepSpeed);
        if(upStep)
            dY = rng.nextInt(dyT) + (1 + stepSpeed);
        else
            dY = -rng.nextInt(dyT) - (1 + stepSpeed);

        m.xPos += dX;
        m.yPos += dY;
    }



}
