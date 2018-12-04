/**
 * this class is what the agent understands.
 */

public class NodePercept {

    private boolean wumpus; //unless otherwise found to not have a wumpus
    private boolean pit; // unless otherwise found to not have a pit
    private boolean gold;
    private boolean wall;
    private boolean visited;
    private boolean isSolved; //if we have either visited and is safe or have inferred whats in the pit
    private char symbol;
    private int y_val;
    private int x_val;
    private boolean is_safe;
    public boolean visitedForStack = false;
    public int distanceForSearch = 0;

    public NodePercept(int y, int x){
        y_val = y;
        x_val = x;
        this.wall = false;
        this.gold = false;
        this.wumpus = true;
        this.pit = true;
        this.visited = false;
        this.isSolved = false;
        this.is_safe = false;
    }

    //set methods


    public int getY()
    { return y_val;}

    public int getX()
    {return x_val;}

    public void setSymbol(char S)
    {this.symbol = S;}

    public char getSymbol()
    {return symbol;}

    public void setPit(boolean pit) {
        this.pit = pit;
    }

    public void setGold(boolean gold) {
        this.gold = gold;
    }

    public void setSolved(boolean solved) {
        isSolved = solved;
    }

    public void setSafe(boolean is_safe) {
        this.is_safe = is_safe;
        symbol = 's';
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
        this.setSymbol('v');
    }

    public void setWumpus(boolean wumpus) {
        this.wumpus = wumpus;
    }

    public void setWall(boolean wall) {
        this.wall = wall;
    }
    //boolean check methods

    public boolean isGold() {
        return gold;
    }

    public boolean is_safe() {
        if((!wumpus && !wall && !pit) || is_safe)
        {
            return true;
        }
        else {
            return false;
        }
    }

    public boolean isPit() {
        return pit;
    }

    public boolean isSolved() {
        return isSolved;
    }

    public boolean isVisited() {
        return visited;
    }

    public boolean isWumpus() {
        return wumpus;
    }

    public boolean isWall() {
        return wall;
    }
}

