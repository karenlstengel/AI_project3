/**
 *
 * @author
 * this class represents the locations on the search grid
 */


public class Node {

    private char symbol; //tells what the square contains
    private boolean solved;
    private boolean wumpus;
    private boolean pit;
    private boolean gold;
    private boolean[] sense; // index 0 = stench; 1 = breeze; 2 = glitter
    private int x;
    private int y;
    

    public Node(int y, int x){

        this.symbol = '_';
        this.wumpus = false;
        this.pit = false;
        this.gold = false;
        sense = new boolean[3];
        this.x = x;
        this.y = y;
    }

    //get the symbol
    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public void setSense(int index, boolean s)  {
        sense[index] = s;
    }

    public boolean[] getSense() {
        return sense;
    }
    public void setGold(boolean gold) {
        this.gold = gold;
    }

    public boolean isGold() {
        return gold;
    }

    public boolean isPit() {
        return pit;
    }

    public void setPit(boolean pit) {
        this.pit = pit;
    }

    public boolean isWumpus() {
        return wumpus;
    }
    public void setWumpus(boolean wumpus){
        this.wumpus = wumpus;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

}
