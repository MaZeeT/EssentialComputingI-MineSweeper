package core;

public class Plot {
    public int x;
    public int y;
    public boolean explored = false;
    public boolean isMine = false;
    public int dangerLevel;
    public boolean flagged = false;

    public Plot(int x, int y){
        this.x = x;
        this.y = y;
    }
}
