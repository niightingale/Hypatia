package test;

import nano.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Cell {
    //Cellular properties
    int xPos;
    int yPos;
    int radius;

    Color color = Color.white;

    //Movement
    int dx;
    int dy;

    //Spawn limits
    int underLimit = 10;
    int stretch = 5;

    boolean fertile = true;
    boolean producer = false;

    Pen pen = new Pen(Main.frustrum);

    public Cell(int xIn, int yIn, int rIn, boolean producer){
        xPos = xIn;
        yPos = yIn;
        radius = rIn;
        this.producer = producer;
    }

    public void update(){
        if(fertile)
            duplicate();
        if(producer)
            produce();
    }

    public void lateUpdate(){
        Physics.step(this, 1);

        int cnt = 0;
        for(Morphogen m:Main.morphs){
            if(Math.sqrt(Math.pow(xPos - m.xPos, 2) + Math.pow(yPos - m.yPos, 2)) <= 25){
                cnt++;
            }
        }
        if(cnt > 5 && color == color.white)
            color = color.blue;
        if(cnt > 10 && color == color.blue)
            color = color.pink;
        if(cnt > 20 && color == color.pink)
            color = color.red;
    }

    public void frameUpdate(){
        pen.drawCircle(xPos, yPos, radius, color, false);
    }

    public void duplicate(){
        Random rng = new Random();
        int xF;
        int yF;
        int radF;
        Cell protoCell;

        boolean isValid = false;
        int validCounter = 0;

        //Per chance
        double secc = rng.nextDouble();
        boolean willDivide = false;
        if(secc > 0.5)
            willDivide = true;

        if(willDivide){
            do {
                validCounter++;

                radF = rng.nextInt(stretch + 1) + underLimit;

                int[] xy = Physics.polarng(25, radius);

                xF = xy[0] + xPos;
                yF = xy[1] + yPos;

                protoCell = new Cell(xF, yF, radF, false);

                ArrayList<Cell> cellsTot = new ArrayList<Cell>();
                cellsTot.addAll(Main.cells);
                cellsTot.addAll(Main.cellQue);

                for (Cell sell : cellsTot) {
                    if (getDistance(protoCell, sell) > protoCell.radius + sell.radius && inFrustrum(protoCell)) {
                        isValid = true;
                    } else {
                        isValid = false;
                        break;
                    }

                }
                if(validCounter > 25){
                    fertile = false;
                    break;
                }
            }
            while(!isValid && fertile);

            if(fertile){
                Main.cellQue.add(protoCell);
            }
            //fertile = false;
        }
    }

    public void produce(){
        Morphogen morphogen = new Morphogen(this);
    }

    public double getDistance(Cell cell1, Cell cell2){
        double dist = Math.sqrt(Math.pow(cell1.xPos - cell2.xPos, 2) + Math.pow(cell1.yPos - cell2.yPos, 2));
        return dist;
    }

    public boolean inFrustrum(Cell cell){
        if(cell.xPos > Main.xSize - cell.radius || cell.xPos < 0 + cell.radius || cell.yPos > Main.ySize - cell.radius || cell.yPos < 0 + cell.radius)
            return false;
        else
            return true;
    }
}
