package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



public class Field {
    private int xSize;
    private int ySize;
    private Plot[][] field;
    private int numberOfMines;
    private boolean firstClick;


    public Field(int xSize, int ySize, int numberOfMines){
        this.xSize = xSize;
        this.ySize = ySize;
        this.field = new Plot[xSize][ySize];
        this.numberOfMines = numberOfMines;
        this.firstClick = true;

        generateFields();
        toMineOrNotToMine();
        calDangerLevel();
    }

    public void generateFields() {
        for (int j = 0; j < ySize; j++) {
            for (int i = 0; i < xSize; i++) {
                Plot plot = new Plot();
                plot.x = i;
                plot.y = j;
                plot.explored = false;
                field[i][j] = plot;
            }
        }
    }

    public int countMines() {
        int mineCount = 0;
        for (int j = 0; j < ySize; j++) {
            for (int i = 0; i < xSize; i++) {
                if (field[i][j].isMine) {
                    mineCount++;
                }
            }
        }
        return mineCount;
    }

    public int countFlags() {
        int flagCount = 0;
        for (int j = 0; j < ySize; j++) {
            for (int i = 0; i < xSize; i++) {
                if (field[i][j].flagged) {
                    flagCount++;
                }
            }
        }
        return flagCount;
    }

    public void calDangerLevel() {
        // Loop across the array
        for (int j = 0; j < ySize; j++) {
            for (int i = 0; i < xSize; i++) {
                int dangerLevel = 0;

                // Loop across + - 1 from a field
                for (int y = -1; y <= 1; y++) {
                    for (int x = -1; x <= 1; x++) {

                        if (isWithinFieldBoundaries(x + i, y + j)) {
                            if (field[x + i][y + j].isMine) {
                                dangerLevel++;
                            }
                        }
                    }
                }
                field[i][j].dangerLevel = dangerLevel;
            }
        }
    }

    // replace mine if the first click is a mine
    public void firstClick(Plot plot) {
        plot.isMine = false;
        toMineOrNotToMine();
        calDangerLevel();
    }

    public void toMineOrNotToMine() {
        Random rand = new Random();

        while (countMines() < numberOfMines) {
            int xRand = rand.nextInt(xSize);
            int yRand = rand.nextInt(ySize);

            Plot plot = field[xRand][yRand];

            // check if the field have a mine
            if (!plot.isMine && isNotExplored(plot)) {
                plot.isMine = true;
            }
        }
    }

    // set a field as explored and do some checks to see if it can be explored or it shall check nearby fields also
    public void explore(int xCoor, int yCoor) {
        Plot plot = field[xCoor][yCoor];

        if (isNotFlagged(plot)) {
            plot.explored = true;

            if (firstClick && plot.isMine) {
                firstClick(plot);
            }

            // this make a recursive behaviour with explore() and exploreGrid() if there are no nearby mines
            if (plot.dangerLevel == 0) {
                exploreGrid(xCoor, yCoor);
            }
            firstClick = false;
        }
    }

    // check and click a 3x3 grid
    public void exploreGrid(int xCoor, int yCoor) {
        for (int j = -1; j <= 1; j++) {
            for (int i = -1; i <= 1; i++) {

                // check to stay inside of array
                if (isWithinFieldBoundaries(xCoor + i, yCoor + j)) {

                    Plot plot = field[xCoor + i][yCoor + j];
                    if (isNotExplored(plot) && isNotFlagged(plot)) {
                        explore(xCoor + i, yCoor + j);
                    }
                }
            }
        }
    }

    public void toggleFlag(Plot plot) {
        if (isNotExplored(plot)) {
            plot.flagged = !plot.flagged;
        }
    }

    public boolean isWithinFieldBoundaries(int xCoor, int yCoor) {
        return 0 <= yCoor && yCoor < ySize && 0 <= xCoor && xCoor < xSize;
    }

    public static boolean isNotFlagged(Plot plot) {
        return !plot.flagged;
    }

    public static boolean isNotExplored(Plot plot) {
        return !plot.explored;
    }

    public boolean isPlayerDead() {
        for (int j = 0; j < ySize; j++) {
            for (int i = 0; i < xSize; i++) {
                if (field[i][j].isMine && field[i][j].explored) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isGameWon() {
        int exploredFields = (xSize * ySize) - numberOfMines;
        for (int j = 0; j < ySize; j++) {
            for (int i = 0; i < xSize; i++) {

                if (!field[i][j].isMine && field[i][j].explored) {
                    exploredFields--;
                }

                if (exploredFields == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public Plot[] getPlots(){
        List<Plot> list = new ArrayList<>();
        for (Plot[] plots:field) {
            for (Plot plot:plots) {
                list.add(plot);
            }
        }

        return list.toArray(new Plot[0]);
    }

}
