package apps;

import structures.Graph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;


public class MSTDriver {

    static Scanner sc = new Scanner(System.in);
    static String options = "nieq";

    static char getOption() {
        System.out.print("\n Choose action: ");
        System.out.print("(n)ew graph, ");
        System.out.print("(i)nitialize, ");
        System.out.print("(e)xecute, ");
        System.out.print("(q)uit? => ");
        char response = sc.nextLine().toLowerCase().charAt(0);
        while (!options.contains(response+"")) {
            System.out.print("\tYou must enter one of n, i, e, or q => ");
            response = sc.nextLine().toLowerCase().charAt(0);
        }
        return response;
    }

    public static void main(String[] args){

        System.out.println();
        System.out.print("Enter graph file name => ");
        String graphFile = sc.nextLine();

        //try to get the file
        Graph graph = null;
        try {
            graph = new Graph(graphFile);
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        //initialize the tree list
        PartialTreeList partialTreeList = null;

        //pick an option
        char option;
        while ((option = getOption()) != 'q') {
            System.out.println();

            //initialize
            if (option == 'i') {
                //Initialize with the given graph
                partialTreeList = MST.initialize(graph);
                System.out.println();
                System.out.println();
                System.out.println("Initialize Trees: ");
                for (PartialTree pt : partialTreeList) {
                    System.out.println(pt.toString());
                }
                System.out.println();
                System.out.println();


            //Execute on the tree list
            } else if (option == 'e') {
                if (partialTreeList == null) {
                    partialTreeList = MST.initialize(graph);
                }
                //Execute on the initialized tree list
                System.out.println("Executing on the trees list: ");
                ArrayList<PartialTree.Arc> arcArrayList = MST.execute(partialTreeList);
                //use print statements for each pass inside the loop in MST.java

                //Print out the final list
                System.out.println();
                System.out.println();
                System.out.println("List of Arcs for MST: ");
                for (int i = 0; i < arcArrayList.size(); i++) {
                    PartialTree.Arc anArcArrayList = arcArrayList.get(i);
                    System.out.print(" " + anArcArrayList + " ");
                }
                System.out.println();
                System.out.println();

            //New Graph
            }else if (option == 'n') {
                System.out.println();
                System.out.print("Enter graph file name => ");
                graphFile = sc.nextLine();

                //try to get the file
                graph = null;
                try {
                    graph = new Graph(graphFile);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }

                //initialize the tree list
                partialTreeList = null;

            }
        }
    }
}
