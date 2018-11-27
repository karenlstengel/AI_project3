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
    private char[] sense; // index 0 = stench; 1 = breeze; 2 = glitter; 3 = bump
    

    public Node(){

        this.symbol = '_';
        this.wumpus = false;
        this.pit = false;
        this.gold = false;
        sense = new char[4];
    }

    //get the symbol
    public char getSymbol() {
        return symbol;
    }

    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    public void setSense(int index, char s)  {
        sense[index] = s;
    }

    public char[] getSense() {
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

}
