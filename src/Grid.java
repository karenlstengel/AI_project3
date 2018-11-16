import java.util.Random;

/**
 * @author
 * this class manages the grid which the user will explore
 * the grid is filled with nodes
 *
 */

public class Grid {

    private Node[][] grid;

    public Grid(int dim){
        grid = new Node[dim][dim];

        for(int i = 0; i < dim; i++){
            for(int j = 0; j <dim; j++){
                grid[i][j] = new Node();
            }
        }
        placeObstacles();
    }


    public void placeObstacles(){

        Random rand = new Random();
        //nothing can be placed in 0,0 since that is where the user will start.
        int x = rand.nextInt(grid.length) + 1;
        int y = rand.nextInt(grid.length) + 1;

        //place gold randomly

        //place WUMPUS randomly

        //place pits w/ 20% on any empty square
    }

    public void printGrid(){
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[0].length; j++){
                System.out.println(grid[i][j]);
            }
        }
    }
}
