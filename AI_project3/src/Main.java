import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

        public static void main(String args[]){

            System.out.println("Welcome to WUMPUS WORLD");

            int choice = 4;
            Scanner in = new Scanner(System.in);

            while(choice != 0){
                System.out.println();
                System.out.println("what size world would you like to solve? Must be an integer > 0: ");
                System.out.println("Or press 0 to exit.");
                choice = in.nextInt();

                if(choice == 0){
                    System.out.println("thanks for playing");
                    return;
                }

                Grid g = new Grid(choice);
                g.printGrid();
                Agent a = new Agent();
                a.solve(g);


            }
        }

}
