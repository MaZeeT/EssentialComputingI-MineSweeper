package core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Field {
    private final int xSize;
    private final int ySize;
    private final Plot[][] field;
    private final List<Plot> plots;
    private final int numberOfMines;
    private boolean firstClick;


    public Field(int xSize, int ySize, int numberOfMines) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.field = new Plot[xSize][ySize];
        this.plots = new ArrayList<>();
        this.numberOfMines = numberOfMines;
        this.firstClick = true;

        generateFields();
        toMineOrNotToMine();
        calDangerLevel();
    }

    private void generateFields() {
        for (int j = 0; j < ySize; j++) {
            for (int i = 0; i < xSize; i++) {
                Plot plot = new Plot(i, j);
                field[i][j] = plot;
                plots.add(plot);
            }
        }
    }

    public long countMines() {
        return plots.stream()
                .filter(p -> p.isMine)
                .count();
    }

    public long countFlags() {
        return plots.stream()
                .filter(p -> p.flagged)
                .count();
    }

    private void calDangerLevel() {
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
    private void firstClick(Plot plot) {
        plot.isMine = false;
        toMineOrNotToMine();
        calDangerLevel();
    }

    private void toMineOrNotToMine() {
        Random rand = new Random();
        while (countMines() < numberOfMines){
            List<Plot> miningOptions = plots.stream()
                    .filter(p -> isNotAMine(p) && isNotExplored(p))
                    .toList();
            
            int index = rand.nextInt(miningOptions.size());
            miningOptions.get(index).isMine = true;
        }
    }

    private static boolean isNotAMine(Plot plot) {
        return !plot.isMine;
    }

    // set a field as explored and do some checks to see if it can be explored or it shall check nearby fields also
    public void explore(Plot plot) {
        if (isNotFlagged(plot)) {
            plot.explored = true;

            if (firstClick && plot.isMine) {
                firstClick(plot);
            }

            // this make a recursive behaviour with explore() and exploreGrid() if there are no nearby mines
            if (plot.dangerLevel == 0) {
                exploreGrid(plot);
            }
            firstClick = false;
        }
    }

    // check and click a 3x3 grid
    public void exploreGrid(Plot plot) {
        for (int j = -1; j <= 1; j++) {
            for (int i = -1; i <= 1; i++) {

                // check to stay inside of array
                if (isWithinFieldBoundaries(plot.x + i, plot.y + j)) {

                    Plot neirborPlot = field[plot.x + i][plot.y + j];
                    if (isNotExplored(neirborPlot) && isNotFlagged(neirborPlot)) {
                        explore(neirborPlot);
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

    private boolean isWithinFieldBoundaries(int xCoor, int yCoor) {
        return 0 <= yCoor && yCoor < ySize && 0 <= xCoor && xCoor < xSize;
    }

    public static boolean isNotFlagged(Plot plot) {
        return !plot.flagged;
    }

    private static boolean isNotExplored(Plot plot) {
        return !plot.explored;
    }

    public boolean isPlayerDead() {
        return plots.stream()
                .anyMatch(p -> p.isMine && p.explored);
    }

    public boolean isGameWon() {
        int plotsToBeExplored = (xSize * ySize) - numberOfMines;

        long exploredPlots =
                plots.stream()
                .filter(p -> isNotAMine(p) && p.explored)
                .count();

        return plotsToBeExplored == exploredPlots;
    }

    public List<Plot> getPlots() {
        return plots;
    }

}
