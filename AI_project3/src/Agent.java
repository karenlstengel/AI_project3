/**
 * @author
 *
 * this class is the agent. responsible for keeping track of what the agent has discovered while exploring
 * the cave. responisible for moving and making locial decisions.
 */


public class Agent {

    private boolean foundGold;
    private int score;
    private NodePercept[][] memory;
    private String direction;
    private boolean arrow;
    private boolean isDead;

    public Agent(){
         this.foundGold = false;
         this.arrow = true;
         this.direction = "right";
         this.isDead = false;

    }

    //set up precepts grid
    public void setupPreceptsGrid(int x){
        this.memory = new NodePercept[x][x];

        for(int i = 0; i < memory.length; i++){
            for( int j = 0; j < memory.length; j++){
                memory[i][j] = new NodePercept();
            }
        }
    }

    //get and check boolean methods

    public boolean isArrow() {
        return arrow;
    }

    public boolean isDead() {
        return isDead;
    }

    public boolean isFoundGold() {
        return foundGold;
    }

    public int getScore() {
        return score;
    }

    public String getDirection() {
        return direction;
    }


    //set methods
    public void setArrow(boolean arrow) {
        this.arrow = arrow;
    }

    public void setDead(boolean dead) {
        isDead = dead;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setFoundGold(boolean foundGold) {
        this.foundGold = foundGold;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public NodePercept[][] getMemory() {
        return memory;
    }

    public boolean[] getPrecepts(){
        boolean[] temp = new boolean[1];
        return temp;
    }

    //   agent action methods

    public void solve(Grid g){

         setupPreceptsGrid(g.getGrid()[0].length);
         Node current = g.getNode(0,0);
         while(!isDead || !(foundGold && current == g.getNode(0,0))){
             //do moving stuff

             if(current.isPit()){
                 isDead = true;
                 System.out.println("Game over. You fell in a pit...");
             }
             else if(current.isWumpus()){
                 isDead = true;
                 System.out.println("Game over. You were eaten by the WUMPUS X.X");
             }

             if(current.isGold()){
                 foundGold = true;
                 score += 1000;
             }
         }

         if(foundGold){
             System.out.println("the game ended with finding gold!");
         }

        System.out.println("your score: " + score);
        System.out.println("you entered this many cells: " + getEnteredCells());
    }

    public int getEnteredCells() {
        int entered = 0;
        for (int i = 0; i < memory.length; i++) {
            for (int j = 0; j < memory.length; j++) {
                if(memory[i][j].isVisited()){
                    entered++;
                }
            }
        }
        return entered;
    }

    public void decide(){
        //call ligic base and move or something
    }

    public void move(){
        //move
    }

    public void rotate(String c){
          if(c.equals("clockwise")){
              //rotate direction clockwise
          }
          else if(c.equals("counter")){
              //rotate direction counter clockwise
          }
    }

    public boolean shoot(){
        boolean wLife = true;
        if(arrow){
            // shoot in current direction. if arrow enters square of wumpus then return a scream and set wumpuslife = false

            //else dont change wumpus
        }

        else{
            System.out.println("no arrows");
        }

        return wLife;
    }
}
