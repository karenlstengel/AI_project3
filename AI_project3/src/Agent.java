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
    private NodePercept current;
    private ArrayList<NodePercept> frontier;

    public Agent()
    {
         this.foundGold = false;
         this.arrow = true;
         this.direction = "north";      //right
         this.isDead = false;

    }

    //set up precepts grid
    public void setupPreceptsGrid(int dim) {
        this.memory = new NodePercept[dim + 2][dim + 2];
        System.out.println(memory[0].length);
        for(int i = 0; i < memory.length; i++){
            for(int j = 0; j < memory.length; j++){
                memory[i][j] = new NodePercept(i,j);
                if(i == 0 || i == memory.length - 1 || j == 0 || j == memory.length - 1 ){
                    memory[i][j].setSymbol('+');
                }
                else
                    memory[i][j].setSymbol('_');
                System.out.print(memory[i][j].getSymbol() + " ");
            }
            System.out.println();
        }

    }

    public void printMemory() {
        for(int i = 0; i < memory.length; i++){
            System.out.println();
            for(int j = 0; j < memory.length; j++) {
                System.out.print(memory[i][j].getSymbol() + "  ");
            }
        }
        System.out.println();

    }


    //   agent action methods

    public void solve(Grid g){

         setupPreceptsGrid(g.getGrid()[0].length);
         current = memory[1][1];
         while(!isDead || !(foundGold && current == memory[1][1])){
             //do moving stuff

             if(current.isPit()){
                 isDead = true;
                 System.out.println("Game over. You fell in a pit...");
             }
             else if(current.isWumpus()){   //this condition is checking the "isWumpus" condition from the nodePercept class, which is not necessarily accurate to whether there's a wumpus in the square. You could die from simply having the possibility of a wumpus in the square based on this. Also, this doesn't account for if the wumpus has been killed
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

    public NodePercept moveToLeastRisky()
    {
        ArrayList<NodePercept> risk1 = new ArrayList<NodePercept>(); //for squares where there's only one breeze adjacent
        ArrayList<NodePercept> risk2 = new ArrayList<NodePercept>(); //for squares where there's two breezes
        ArrayList<NodePercept> risk3 = new ArrayList<NodePercept>(); //for squares where there's three breezes
        ArrayList<NodePercept> risk4 = new ArrayList<NodePercept>(); //for squares where there's both breeze and wumpus adjacent
        for(int i = 1; i < memory.length - 1; i++)
        {
            for (int j = 1; j < memory.length - 1; j++)
            {
                if(memory[i][j].isVisited() == false && memory[i][j].is_safe())
                {
                    return memory[i][j];
                }
                else {

                }
            }
        }
        //deal with wumpus maybe
    }

    public void decide(){
        //if no safe squares available

        //call logic base and move or something
        //grid.setWumpuslife = shoot()
        //if iswumpuslife
        //print scream -> for all node precepts set wumpus = false
        //else all np current - edge wumpus = false
    }

    public void move_to(int y, int x){
        int dist = 0;
        boolean done = false;
        ArrayList<NodePercept> visited = new ArrayList<NodePercept>();
        visited.add(this.current);
        while(done == false && dist < 100 && current.getY() != y && current.getX() != x)
        {
            dist++;
            int loop = visited.size();
            for (int i = 0; i < loop; i++)
            {
                NodePercept temp = visited.get(i);
                ArrayList<NodePercept> adjacent = return_adjacent(temp.getY(), temp.getX());
                for (int j = 0; j < adjacent.size(); j++)
                {
                    NodePercept temp2 = adjacent.get(j);
                    if(!temp2.visitedForStack && temp2.isVisited())
                    {
                        if(temp2.getY() == y && temp2.getX() == x)
                        {
                            done = true;
                            break;
                        }
                        temp2.visitedForStack = true;
                        visited.add(temp2);
                    }
                }
            }
        }
        score = score - dist;
        System.out.println("Subtracting " + dist + " from score");
        System.out.println("Moving to " + y + ", " + x);
        current.setSymbol('s');
        current = memory[y][x];
        current.setSymbol('A');
    }






    public ArrayList<NodePercept> return_adjacent(int y, int x) //from memory
    {
        ArrayList<NodePercept> adjacent = new ArrayList<NodePercept>();

        adjacent.add(memory[y + 1][x]);
        adjacent.add(memory[y - 1][x]);
        adjacent.add(memory[y][x - 1]);
        adjacent.add(memory[y][x + 1]);
        int count = 0;
        //System.out.println(adjacent.size());

        for (int i = 0; i < adjacent.size(); i++) {

            //System.out.println("neighbors: " + adjacent.get(i).getY() + " " + adjacent.get(i).getX() + " " + adjacent.get(i).getSymbol());

            if (adjacent.get(i).getSymbol() == '+') {
                adjacent.get(i).setWall((true));
                //cool_guy[i] = adjacent.get(i);
            }
        }
//
//               if (adjacent.get(i).getY() == memory.length - 1 || adjacent.get(i).getX() == memory.length - 1) { //if either of these are true then it must be the wall we don't know about. Now we know
//
//                   this.top_wall = memory.length - 1; //technically this assumes agent knows its a square. Can change, may need method to check existence.
//                   this.left_wall = memory.length - 1;
//
//               }
//               adjacent.get(i).setWall(true);
//               System.out.println("wall removed:" + adjacent.get(i).getY() + " " + adjacent.get(i).getX());
//               adjacent.remove(i);
//           //} //remove any walls
          //  }
        //}

            return adjacent;

    }



    public void return_home(){
<<<<<<< Updated upstream
          move_to(1, 1);
          //Sam: If we keep rationing, we should have enough left.
        //Frodo: Enough? For what?
        //SAM looks at Frodo with concern
        //Sam: For the journey home.
=======
        move_to(1,1);

>>>>>>> Stashed changes
    }


    public void update_memory(NodePercept node, Grid grid, int y, int x)
    {
        //int y = Node.getY();
        //int x = Node.getX();
        // 0 = stench, 1 = breeze, 2 = glitter
        boolean[] senses = grid.getGrid()[y][x].getSense(); //index 0 = stench, 1 = breeze, 2 = glitter

        ArrayList<NodePercept> adjacent = return_adjacent(y, x);

        if (senses[0] == false) {
            for (int j = 0; j < adjacent.size(); j++) {
                adjacent.get(j).setWumpus(false);
                //adjacent.get(j).setSafe(true);}
                //System.out.print(" sense is 0");
            }
        }
            if (senses[1] == false) // if there is no breeze
            {
                for (int j = 0; j < adjacent.size(); j++) {
                    adjacent.get(j).setPit(false);
                    //adjacent.get(j).setSafe(true);}
                    //System.out.print(" no breeze");
                }
            }

                if (senses[2] == true) //if there is a glitter there be gold
                {
//                    for (int j = 0; j < adjacent.size(); j++) ?
//                        adjacent.get(j).setGold(true);
                        //adjacent.get(j).setSafe(true);}
                        memory[y][x].setGold(true);
                        System.out.print(" glitter");

                }

         is_safe(node);
    }

    public boolean safe_space()
    {
        for(int i = 0; i < memory.length; i++) {
            for (int j = 0; j < memory.length; j++)
            {
                if (memory[i][j].isVisited() == false && memory[i][j].is_safe() == true)
                {return true;}
            }
        }
            return false;
    }

     public void is_safe(NodePercept current)
            {
                ArrayList<NodePercept> adjacent = return_adjacent(current.getY(), current.getX());
                for(int i = 0; i < adjacent.size(); i++)
                {
                    if(adjacent.get(i).isWall() == false && adjacent.get(i).isPit() == false && adjacent.get(i).isWumpus() == false)
                    {
                        adjacent.get(i).setSafe(true);
                    }
                }
            }



    public void explore_safe(Grid grid, int y, int x)
    {
        frontier = new ArrayList<NodePercept>();
        Stack<NodePercept> node_stack = new Stack();
        node_stack.push(memory[y][x]);
        memory[y][x].setSafe(true);
        memory[y][x].setVisited(true);
        do  {
            printMemory();
            NodePercept peek = node_stack.peek();
            this.current = peek;
            peek.setVisited(true);
            //System.out.println("current vals:" + " " + peek.getY() + " "  + peek.getX());
            update_memory(peek, grid, peek.getY(), peek.getX());
            NodePercept val = get_move(peek.getY(), peek.getX(), grid);
            if(grid.getGrid()[current.getY()][current.getX()].isGold() == true)
            {
                foundGold = true;
                score+=1000;
                return_home();
                System.out.println("WE EATING " + score);
                break;
            }
            if (val != null) {
                node_stack.push(val);
                score--;
                System.out.println("If:" +  val.getX() + val.getY());
            } else {
                frontier.add(current); //frontier of possible not-safe spaces
                node_stack.pop();
                System.out.println("else");
                score--;
            }
        }while(safe_space() == true);
        printMemory();
        System.out.println(current.getY() + " " + current.getX());
        System.out.println(score);
    }

    public void move(Grid grid, String direction)
    {

    }

    public NodePercept get_move(int y, int x, Grid grid)
    {
        if((memory[y+1][x].is_safe() == true) && memory[y+1][x].isVisited() == false && memory[y+1][x].isWall() == false) {
            memory[y][x].setVisited(true);
            direction = "north";
            return memory[y+1][x];
        }
        else if((memory[y][x +1].is_safe() == true) && memory[y][x +1].isVisited()==false && memory[y+1][x].isWall() == false){
            memory[y][x].setVisited(true);
            direction = "east";
            return memory[y][x+1];
        }
        else if((memory[y][x -1].is_safe() == true) && memory[y][x-1].isVisited()==false && memory[y+1][x].isWall() == false){
            memory[y][x].setVisited(true);
            direction = "west";
            return memory[y][x-1];
        }
        else if((memory[y-1][x].is_safe() == true) && memory[y-1][x].isVisited()==false && memory[y+1][x].isWall() == false){
            memory[y][x].setVisited(true);
            direction = "south";
            return memory[y-1][x];
        }
        else{
            return null;
        }
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

    public boolean shoot(Grid g){
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
            score -= 10;

        }
        //else dont change wumpusLife

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
    public boolean check_dead(Grid g, int y, int x)
    {
        if(g.getGrid()[y][x].getSymbol() == 'P' || g.getGrid()[y][x].getSymbol() == 'W')
        {
            isDead = true;
        }
        else
            isDead = false;
        return isDead;
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


    public void set_current(NodePercept current)
    {
        this.current = current;
    }


    public ArrayList<NodePercept> return_frontier()
    {
        return frontier;
    }

    public void deal_with_wumpus(Grid g){
        //go thru frontier to find the square with the wumpus
        NodePercept wumpus = frontier.get(0);
        for(int i = 0; i < frontier.size(); i++){
            if(frontier.get(i).isWumpus()){
                wumpus = frontier.get(i);
                break;
            }
        }
        ArrayList<NodePercept> adj = return_adjacent(wumpus.getY(), wumpus.getX());
        NodePercept moveTo = adj.get(0);

        //check adjacent squares for safety; take first safe option
        for( int i = 0; i < adj.size(); i ++){
            if(adj.get(i).is_safe()){
                moveTo = adj.get(i);
                break;
            }
        }
        //call move_to on current square and square selected above
        move_to(moveTo.getY(), moveTo.getX());

        //set the direction based off of relationship to new square & wumpus square
        //north (i -1), south (i +1), east(j +1), west (j-1)
        if(wumpus.getX() < current.getX()){
            direction = "north";
        }
        else if(wumpus.getX() > current.getX()){
            direction = "south";
        }
        else if(wumpus.getY() < current.getY()){
            direction = "west";
        }
        else if(wumpus.getY() > current.getY()){
            direction = "east";
        }

        //set wumpus status
        g.setWumpusLife(shoot(g));

        if( !g.isWumpusLife()) {

            //set the square 
            g.getGrid()[wumpus.getX()][wumpus.getY()].setWumpus(false);

            //set all nodeprecepts wumpus = false
            for(int i = 0; i < memory.length; i++){
                for(int j = 0; j< memory[0].length; j++){
                      memory[i][j].setWumpus(false);
                }
            }
        }

        else{
            //set all nodeprecepts wumpus = false in current direction until wall
            if(direction.equals("north")){
                for(int i = current.getX(); i >= 0; i--){
                      memory[i][current.getY()].setWumpus(false);
                }
            }
            else if(direction.equals("south")){
                for(int i = current.getX(); i < g.getGrid().length; i++){
                    memory[i][current.getY()].setWumpus(false);
                }
            }
            else if(direction.equals("east")){
                for(int j = current.getY(); j < g.getGrid().length; j ++){
                    memory[current.getY()][j].setWumpus(false);
                }
            }
            else{
                //direction = west
                for(int j = current.getY(); j >= 0; j--){
                    memory[current.getY()][j].setWumpus(false);
                }
            }

        }

    }
}

//if gold take and bounce
//move_to()
//deal_with_wumpus()
//choose_pit()
