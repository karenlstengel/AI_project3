import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main {

        public static void main(String args[]){

            System.out.println("Welcome to WUMPUS WORLD YEEEEEHAW");
            Grid g4 = new Grid(4);
            g4.printGrid();
            Agent a = new Agent();
            a.solve(g4);
            ArrayList<NodePercept> val = a.return_frontier();
            for (int i = 0; i < a.return_frontier().size(); i++)
            {
                System.out.println(val.get(i).getY() + " " + val.get(i).getX());

            }

            //System.out.println(a.safe_space());

           // a.explore_safe(g4);
           // a.solve(g4);
        }

}
