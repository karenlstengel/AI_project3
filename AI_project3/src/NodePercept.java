/**
 * this class is what the agent understands.
 */

public class NodePercept {

    // should always be 0<= x <= 1.0
    private boolean wumpus = true; //unless otherwise found to not have a wumpus
    private boolean pit = true; // unless otherwise found to not have a pit
    private boolean gold = false;
    private boolean visited = false;
    private boolean isSolved = false; //if we have either visited and is safe or have inferred whats in the pit
    

    public NodePercept(){}

    //set methods
    
    public void setPit(boolean pit) {
        this.pit = pit;
    }

    public void setGold(boolean gold) {
        this.gold = gold;
    }

    public void setSolved(boolean solved) {
        isSolved = solved;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public void setWumpus(boolean wumpus) {
        this.wumpus = wumpus;
    }

    //boolean check methods

    public boolean isGold() {
        return gold;
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
}

