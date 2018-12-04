import java.lang.reflect.Array;
import java.util.ArrayList;

public class Main {

        public static void main(String args[]){

            System.out.println("Welcome to WUMPUS WORLD YEEEEEHAW");
            Grid g4 = new Grid(4);
            g4.printGrid();
            Agent a = new Agent();
            a.solve(g4);

            //System.out.println(a.safe_space());

           // a.explore_safe(g4);
           // a.solve(g4);
        }

}
