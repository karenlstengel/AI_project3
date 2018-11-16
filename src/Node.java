/**
 *
 * @author
 * this class represents the locations on the search grid
 */


public class Node {

    private char symbol; //tells what the square contains

    public Node(){
           this.symbol = ' ';
    }

    //set the symbol
    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }


    //get the symbol
    public char getSymbol() {
        return symbol;
    }
}
