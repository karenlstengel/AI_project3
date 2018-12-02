public class Main {

        public static void main(String args[]){
            System.out.println("hello world");

            Grid g4 = new Grid(4);
            g4.printGrid();
            g4.vals();
            Agent a = new Agent();
            a.setupPreceptsGrid(4);
           // a.explore_safe(g4);
           // a.solve(g4);
/**
            Grid g5 = new Grid(5);
            g5.printGrid();
            g5.vals();

            Grid g8 = new Grid(8);
            g8.printGrid();
            g8.vals();

            Grid g10 = new Grid(10);
            g10.printGrid();
            g10.vals();
 */
        }

}
