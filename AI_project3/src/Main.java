import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main {

        public static void main(String args[]){
            System.out.println("hello world");

            Grid g4 = new Grid(4);
            g4.printGrid();
           // g4.vals();
            Agent a = new Agent();
            a.setupPreceptsGrid(4);
           // System.out.println("hi");
            //a.printMemory();


            ArrayList<NodePercept> val =  a.return_adjacent(1,1);
            System.out.println(val.size());
            for(int i = 0; i < val.size(); i++)
            {
                System.out.println(val.get(i).getSymbol());
            }





           // System.out.println(g4.getGrid().length);
            //System.out.println(g4.getGrid()[1][1].getX() + " " + g4.getGrid()[1][1].getY());


            //System.out.println(a.return_adjacent(1,1));




           a.explore_safe(g4, 1, 1);
           int val2 = a.return_frontier().size();
           ArrayList<NodePercept> frontier = a.return_frontier();
           for(int i = 0; i < val2; i++)
           {
               System.out.print(" " + frontier.get(i).getY() + frontier.get(i).getX());

           }

           //System.out.println(a.safe_space());

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
