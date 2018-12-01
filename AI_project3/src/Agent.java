/**
 * @author
 *
 * this class is the agent. responsible for keeping track of what the agent has discovered while exploring
 * the cave. responisible for moving and making locial decisions.
 */

import java.io.*;
import java.util.*;


public class Agent {

    private int top_wall; // top_wall == grid.length -1 bot_wall == 0 left_wall == grid.length -1 ....
    private int bot_wall = 1;
    private int left_wall;
    private int right_wall = 1;
    private boolean foundGold;
    private int score;
    private NodePercept[][] memory;
    private String direction;    // can be  north (i -1), south (i +1), east(j +1), west (j-1)
    private boolean arrow;
    private boolean isDead;

    public Agent(){
         this.foundGold = false;
         this.arrow = true;
         this.direction = "east";      //right
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
        //call logic base and move or something

        //grid.setWumpuslife = shoot()
        //if iswumpuslife
        //print scream -> for all node precepts set wumpus = false
        //else all np current - edge wumpus = false
    }

    public void move(){
        //move
    }

    public void update_memory(int x, int y, Grid grid)
    {

        // 0 = stench, 1 = breeze, 2 = glitter
        boolean[] senses = grid.getGrid()[y][x].getSense();
        if (senses[0] == true)
        {

            int up = y+1;
            int down = y -1;
            int left = x -1; //need to remember to update to a wall
            int right = x+1;




            this.memory[y][x]

        }




    }

    public void explore_safe(Grid grid)
    {
        Stack<Node> node_stack = new Stack();
                node_stack.push(grid.getGrid()[0][0]);
                System.out.println(node_stack.pop().getSymbol());



    }

    public void rotate(String c){
          if(c.equals("clockwise")){
              //rotate direction clockwise
              if(direction.equals("north")){
                  direction = "east";
              }
              else if(direction.equals("south")){
                   direction = "west";
              }
              else if(direction.equals("east")){
                  direction = "south";
              }
              else if(direction.equals("west")){
                  direction = "north";
              }
              else{
                  System.out.println("direction not valid.");
              }
          }
          else if(c.equals("counter")){
              //rotate direction counter clockwise
              if(direction.equals("north")){
                  direction = "west";
              }
              else if(direction.equals("south")){
                  direction = "east";
              }
              else if(direction.equals("east")){
                  direction = "north";
              }
              else if(direction.equals("west")){
                  direction = "south";
              }
              else{
                  System.out.println("direction not valid.");
              }
          }
    }

    public boolean shoot(Grid g, Node current){
        boolean wLife = g.isWumpusLife();
        Node[][] grid = g.getGrid();

        if(arrow){
            // shoot in current direction.
            //north (i -1), south (i +1), east(j +1), west (j-1)


            // if arrow enters square of wumpus then wumpuslife = false
            if(direction.equals("north")){
               for(int i = current.getX(); i >= 0; i--){
                   if(grid[i][current.getY()].isWumpus()){
                       wLife = false;
                   }
               }
            }
            else if(direction.equals("south")){
                for(int i = current.getX(); i < grid.length; i++){
                    if(grid[i][current.getY()].isWumpus()){
                        wLife = false;
                    }
                }
            }
            else if(direction.equals("east")){
                for(int j = current.getY(); j < grid.length; j ++){
                    if(grid[current.getX()][j].isWumpus()){
                        wLife = false;
                    }
                }
            }
            else{
                //direction = west
                for(int j = current.getY(); j >= 0; j--){
                    if(grid[current.getX()][j].isWumpus()){
                        wLife = false;
                    }
                }
            }
            arrow = false;

            //else dont change wumpusLife
        }

        else{
            System.out.println("no arrows");
        }

        return wLife;
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
}
