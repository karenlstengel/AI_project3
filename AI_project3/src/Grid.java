import java.util.Random;

/**
 * @author
 * this class manages the grid which the user will explore
 * the grid is filled with nodes
 *
 */

public class Grid {

    private Node[][] grid;
    private boolean wumpusLife;      // keeps track of whether the wumpus lives or not

    public Grid(int dim){
        grid = new Node[dim][dim];

        for(int i = 0; i < dim; i++){
            for(int j = 0; j <dim; j++){
                grid[i][j] = new Node();
            }
        }
        placeObstacles();
        setSenses();
    }


    public void placeObstacles() {
        grid[0][0].setSymbol('A');
        Random rand = new Random();
        //nothing can be placed in 0,0 since that is where the user will start.
        int x = rand.nextInt(grid.length);
        int y = rand.nextInt(grid.length);

        //place gold randomly
        grid[x][y].setGold(true);
        grid[x][y].setSymbol('G');

        //place WUMPUS randomly
        x = rand.nextInt(grid.length);
        y = rand.nextInt(grid.length);

        while ((x == 0 && y == 0)) {
            //cant place wumpus on agent
            x = rand.nextInt(grid.length);
            y = rand.nextInt(grid.length);
        }
        grid[x][y].setWumpus(true);
        grid[x][y].setSymbol('W');

        //place pits w/ 20% on any empty square
        Random rand2 = new Random();

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (!(i == 0 && j == 0) && !grid[i][j].isGold()) {
                    //cant place pits on traveller or gold
                    boolean pit = rand2.nextInt(5) == 0;
                    grid[i][j].setPit(pit);

                    if (grid[i][j].isPit()) {
                        grid[i][j].setSymbol('P');
                    }

                }
            }

        }

    }

    public void setSenses() {
        // index 0 = stench; 1 = breeze; 2 = glitter; 3 = bump

        //set boundaries
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                System.out.println(i + " " + j);
                //we need to F with this
                if (i == 0 || j == 0 || i == grid.length - 1 || j == grid.length - 1) {
                    // edge of the graph. set the boundary
                    grid[i][j].setSense(3, true);
                }

                //good
                if (grid[i][j].isWumpus()) {
                    if (i == 0 && j == grid.length - 1) { //right corner
                        grid[i][j - 1].setSense(0, true);
                        grid[i + 1][j].setSense(0, true);
                    } else if (i == grid.length - 1 && j == 0) { //bottom left corner
                        grid[i - 1][j].setSense(0, true);
                        grid[i][j + 1].setSense(0, true);
                    } else if (i == grid.length - 1 && j == grid.length - 1) { //bottom right corner
                        grid[i - 1][j].setSense(0, true);
                        grid[i][j - 1].setSense(0, true);
                    } else if (i == 0) { //top wall
                        grid[i][j - 1].setSense(0, true);
                        grid[i + 1][j].setSense(0, true);
                        grid[i][j + 1].setSense(0, true);

                    } else if (j == grid.length - 1) { //right wall
                        grid[i - 1][j].setSense(0, true);
                        grid[i][j - 1].setSense(0, true);
                        grid[i + 1][j].setSense(0, true);
                    } else if (i == grid.length - 1) { //bottom wall
                        grid[i - 1][j].setSense(0, true);
                        grid[i][j - 1].setSense(0, true);
                        grid[i][j + 1].setSense(0, true);
                    } else if (j == 0) { //left wall
                        grid[i - 1][j].setSense(0, true);
                        grid[i][j + 1].setSense(0, true);
                        grid[i + 1][j].setSense(0, true);
                    } else { //middle square
                        grid[i][j - 1].setSense(0, true);
                        grid[i][j + 1].setSense(0, true);
                        grid[i - 1][j].setSense(0, true);
                        grid[i + 1][j].setSense(0, true);
                    }
                }

                if (grid[i][j].isPit()) {
                    if (i == 0 && j == grid.length - 1) { //right corner
                        grid[i][j - 1].setSense(1, true);
                        grid[i + 1][j].setSense(1, true);
                    } else if (i == grid.length - 1 && j == 0) { //bottom left corner
                        grid[i - 1][j].setSense(1, true);
                        grid[i][j + 1].setSense(1, true);
                    } else if (i == grid.length - 1 && j == grid.length - 1) { //bottom right corner
                        grid[i - 1][j].setSense(1, true);
                        grid[i][j - 1].setSense(1, true);
                    } else if (i == 0) { //top wall
                        grid[i][j - 1].setSense(1, true);
                        grid[i + 1][j].setSense(1, true);
                        grid[i][j + 1].setSense(1, true);

                    } else if (j == grid.length - 1) { //right wall
                        grid[i - 1][j].setSense(1, true);
                        grid[i][j - 1].setSense(1, true);
                        grid[i + 1][j].setSense(1, true);
                    } else if (i == grid.length - 1) { //bottom wall
                        grid[i - 1][j].setSense(1, true);
                        grid[i][j - 1].setSense(1, true);
                        grid[i][j + 1].setSense(1, true);
                    } else if (j == 0) { //left wall
                        grid[i - 1][j].setSense(1, true);
                        grid[i][j + 1].setSense(1, true);
                        grid[i + 1][j].setSense(1, true);
                    } else { //middle square
                        grid[i][j - 1].setSense(1, true);
                        grid[i][j + 1].setSense(1, true);
                        grid[i - 1][j].setSense(1, true);
                        grid[i + 1][j].setSense(1, true);
                    }


                    if (grid[i][j].isGold()) {
                        grid[i][j].setSense(2, true);
                    }
                }
            }
        }
    }

    public void printGrid(){
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[0].length; j++){
                System.out.print(grid[i][j].getSymbol());
            }
            System.out.println();
        }
    }

    public void vals(){
        /**
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[0].length; j++) {
                System.out.println("Gold: " + grid[i][j].isGold() + ", WUMPUS: " + grid[i][j].isWumpus() + ", Pit: " + grid[i][j].isPit());
            }
            System.out.println();
        } */

        // index 0 = stench; 1 = breeze; 2 = glitter; 3 = bump
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[0].length; j++) {
                boolean[] sense = grid[i][j].getSense();
                System.out.println("Stench: " + sense[0] + ", breeze: " + sense[1] + ", glitter: " + sense[2] + ", bump: " + sense[3]);
            }
            System.out.println();
        }
    }

    public boolean isWumpusLife(){
        return wumpusLife;
    }

    public void setWumpusLife(boolean life){
        wumpusLife = life;
    }
}
