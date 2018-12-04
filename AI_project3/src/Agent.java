/**
 * @author
 *
 * this class is the agent. responsible for keeping track of what the agent has discovered while exploring
 * the cave. responisible for moving and making locial decisions.
 */

import java.util.*;


public class Agent {

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

        for(int i = 0; i < memory.length; i++){
            for(int j = 0; j < memory.length; j++){
                memory[i][j] = new NodePercept(i,j);
                if(i == 0 || i == memory.length - 1 || j == 0 || j == memory.length - 1 ){
                    memory[i][j].setSymbol('+');
                }
                else
                    memory[i][j].setSymbol('_');
                
            }

        }

    }



    //   agent action methods

    public void solve(Grid g){
        setupPreceptsGrid(g.getGrid()[0].length - 2);
        frontier = new ArrayList<NodePercept>();
        Stack<NodePercept> node_stack = new Stack();

        node_stack.push(memory[1][1]);  //push the start node, I guess
        this.current = node_stack.peek();

        memory[1][1].setSafe(true); //this square are safe
        memory[1][1].setVisited(true);
        while(!isDead && !(foundGold && current.getY() == 1 && current.getX() == 1) && !node_stack.empty()){
            if(foundGold)
            {
                score+=1000;
                return_home();
                
                return;
            }
            if(g.getGrid()[current.getY()][current.getX()].isWumpus() || g.getGrid()[current.getY()][current.getX()].isPit())
            {
                isDead = true;
                if (g.getGrid()[current.getY()][current.getX()].isPit()){
                    System.out.println("You Died by falling in a pit!");
                }
                else{
                    System.out.println("You Died being eaten by the WUMPUS!");
                }
                System.out.println("You entered this many rooms: " + getEnteredCells());
                System.out.println("Your score is: " + score);
                return;
            }                                                                          
        do  {
            if(node_stack.isEmpty())
            {
                node_stack.push(current);
            }


            this.current = node_stack.peek();
            current.setVisited(true);

            update_memory(current, g, current.getY(), current.getX());
            NodePercept val = get_move(current.getY(), current.getX(), g);
            if(g.getGrid()[current.getY()][current.getX()].isGold() == true) //
            {
                foundGold = true;
                score+=1000;
                return_home();
                System.out.println("Your found the gold!");
                System.out.println("You entered this many rooms: " + getEnteredCells());
                System.out.println("Your score is: " + score);
                return;
            }
            if (val != null) {
                node_stack.push(val);
                score--;

            } else {

                frontier.add(current); //frontier of possible not-safe spaces
                node_stack.pop();

                score--;
            } 
        }while(safe_space() == true);
        int wumpusCount = 0;
        for (int yc = 1; yc < memory.length - 1; yc++)
        {
            for (int xc = 1; xc < memory.length - 1; xc++)
            {
                if(memory[yc][xc].isWumpus())
                {
                    wumpusCount++;
                }
            }
        }
        if(wumpusCount <= 2 && arrow)
        {
            deal_with_wumpus(g, wumpusCount);
        }
        else {
            NodePercept nextNode = findLeastRisky();
            if (nextNode == null){
                break;
            }
            else {
                move_to(nextNode.getY(), nextNode.getX());
                node_stack.push(current);

            }
        }

        }

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

    public NodePercept findLeastRisky()
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
                else if(memory[i][j].isVisited() == false){
                    ArrayList<NodePercept> adjacent = return_adjacent(i, j);
                    int risk = 0;
                    boolean neighbVisited = false;
                    for (int z = 0; z < adjacent.size(); z++)
                    {
                        if(adjacent.get(z).isPit())
                        {
                            risk++;
                        }
                        if(adjacent.get(z).isWumpus()) {
                            risk++;
                        }
                        if(adjacent.get(z).isVisited())
                        {
                            neighbVisited = true;
                        }

                    }
                    if(neighbVisited == true){
                    switch(risk) {
                        case 1: risk1.add(memory[i][j]); break;
                        case 2: risk2.add(memory[i][j]); break;
                        case 3: risk3.add(memory[i][j]); break;
                        default: risk4.add(memory[i][j]); break;
                    }    }
                }
            }
        }
        ArrayList<NodePercept> temp;
        if(!risk1.isEmpty())
        {
            temp = risk1;
        }
        else if(!risk2.isEmpty())
        {
            temp = risk2;
        }
        else if(!risk3.isEmpty())
        {
            temp = risk3;
        }
        else if(!risk4.isEmpty())
        {
            temp = risk4;
        }
        else {
            return null;
        }
        NodePercept min = temp.get(0);
        for(int i = 0; i < temp.size(); i++) // are we calculating disCur twice by strting i @ 0..?
        {
            int distCur = Math.abs(current.getY() - min.getY()) + Math.abs(current.getX() - min.getX());
            int distNex = Math.abs(current.getY() - temp.get(i).getY()) + Math.abs(current.getX() - temp.get(i).getX());
            if(distNex < distCur) {
                min = temp.get(i);
            }
        }
        System.out.println(min.getY() + ", " + min.getX());
        return min;

    }


    public void move_to(int y, int x){
        memory[y][x].setVisited(true);
        int dist = 0;
        boolean done = false;
        ArrayList<NodePercept> visited = new ArrayList<NodePercept>();
        visited.add(this.current);
        while(done == false && dist < 100 && !(current.getY() == y && current.getX() == x))
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
        current.setSymbol('s');
        current = memory[y][x];
        current.setSymbol('v');
    }


    public ArrayList<NodePercept> return_adjacent(int y, int x) //from memory
    {
        ArrayList<NodePercept> adjacent = new ArrayList<NodePercept>();

        adjacent.add(memory[y + 1][x]);
        adjacent.add(memory[y - 1][x]);
        adjacent.add(memory[y][x - 1]);
        adjacent.add(memory[y][x + 1]);


        for (int i = 0; i < adjacent.size(); i++) {


            if (adjacent.get(i).getSymbol() == '+') {
                adjacent.get(i).setWall((true));

            }
        }

            return adjacent;

    }



    public void return_home(){
          move_to(1, 1);
    }


    public void update_memory(NodePercept node, Grid grid, int y, int x)
    {

        // 0 = stench, 1 = breeze, 2 = glitter
        boolean[] senses = grid.getGrid()[y][x].getSense(); //index 0 = stench, 1 = breeze, 2 = glitter

        ArrayList<NodePercept> adjacent = return_adjacent(y, x);

        if (senses[0] == false) {
            for (int j = 0; j < adjacent.size(); j++) {
                adjacent.get(j).setWumpus(false);

            }
        }
        else {
            for (int i = 1; i < memory.length - 1; i++)
            {
                for (int j = 1; j < memory.length - 1; j++)
                {
                    NodePercept temp = memory[i][j];
                    if(!adjacent.contains(temp))
                    {
                        temp.setWumpus(false);
                    }
                }
            }
        }
            if (senses[1] == false) // if there is no breeze
            {
                for (int j = 0; j < adjacent.size(); j++) {
                    adjacent.get(j).setPit(false);
                }
            }

                if (senses[2] == true) //if there is a glitter there be gold
                {

                        memory[y][x].setGold(true);
                        foundGold = true;
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


    public boolean shoot(Grid g){
        boolean wLife = g.isWumpusLife();
        Node[][] grid = g.getGrid();

        if(arrow){
            // shoot in current direction.
            //north (i -1), south (i +1), east(j +1), west (j-1)
            System.out.println("shooting");

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


    public int getScore() {
        return score;
    }


    public void deal_with_wumpus(Grid g, int wumpusCount){
        //go thru frontier to find the square with the wumpus
        NodePercept wumpus = frontier.get(0);
        for(int i = 0; i < frontier.size(); i++){
            if(frontier.get(i).isWumpus()){
                wumpus = frontier.get(i);
                break;
            }
        }

        //call move_to on current square and square selected above

        move_to(wumpus.getY(), wumpus.getX());

        this.current = wumpus;
        //set the direction based off of relationship to new square & wumpus square
        //north (i -1), south (i +1), east(j +1), west (j-1)
        ArrayList<NodePercept> adj = return_adjacent(wumpus.getX(), wumpus.getY());
        ArrayList<NodePercept> danger_zone = new ArrayList<NodePercept>();
        for (int i = 0; i < adj.size(); i++)
        {
           if (!(adj.get(i).is_safe() && adj.get(i).isVisited()))
            {
                danger_zone.add(adj.get(i));
            }
        }

        NodePercept throw_arrow = danger_zone.get(0);

        if(wumpus.getX() < throw_arrow.getX()){
            direction = "north";
        }
        else if(wumpus.getX() > throw_arrow.getX()){
            direction = "south";
        }
        else if(wumpus.getY() < throw_arrow.getY()){
            direction = "west";
        }
        else if(wumpus.getY() > throw_arrow.getY()){
            direction = "east";
        }

        //set wumpus status
        g.setWumpusLife(shoot(g));

        if( !g.isWumpusLife()) {
            System.out.println("Scream!");

            //set the square 
            g.getGrid()[wumpus.getX()][wumpus.getY()].setWumpus(false);

            //set all nodeprecepts wumpus = false
            for(int i = 0; i < memory.length; i++){
                for(int j = 0; j< memory[0].length; j++){
                      memory[i][j].setWumpus(false);
                      g.getGrid()[i][j].setSense(0, false);
                      g.getGrid()[i][j].setWumpus(false); //remove wumpus's existence from original grid
                }
            }
        }

        else{
            //set all nodeprecepts wumpus = false in current direction until wall

            for(int i = 0; i < danger_zone.size(); i++)
            {
                danger_zone.get(i).setWumpus(true);

            }



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
