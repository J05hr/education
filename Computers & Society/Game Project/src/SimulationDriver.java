import java.util.Scanner;

public class SimulationDriver {

    static Scanner sc = new Scanner(System.in);
    static String options = "brpflq";

    static char getOption() {

        System.out.println("\n -- Paradox Timeline Simulation -- ");
        System.out.print("\n Choose an action: ");
        System.out.print("(b)uild the initial timeline, ");
        System.out.print("(r)un a game, ");
        System.out.print("(p)rint the event tree, ");
        System.out.print("(f)ind an event, ");
        System.out.print("(l)ist a person, ");
        System.out.print("(q)uit? => ");

        char response = sc.nextLine().toLowerCase().charAt(0);
        while (!options.contains(response + "")) {
            System.out.print("\tYou must enter one of b, r, p, f, l, or q => ");
            response = sc.nextLine().toLowerCase().charAt(0);
        }
        return response;
    }

    static String turnoptions = "rsidq";
    static char turnOption() {


        System.out.print("\n Choose an action: ");
        System.out.print("(r)emove event, ");
        System.out.print("(s)tart a war, ");
        System.out.print("(i)troduce a plague, ");
        System.out.print("(d)iscover an invention, ");
        System.out.print("(q)uit? => ");

        char response = sc.nextLine().toLowerCase().charAt(0);
        while (!turnoptions.contains(response + "")) {
            System.out.print("\tYou must enter one of r, s, i, d, or q => ");
            response = sc.nextLine().toLowerCase().charAt(0);
        }
        return response;
    }

    public static void main(String[] args) {

        //initialize
        Population pop = new Population();
        Timeline gameTimeline = null;

        char option;
        while ((option = getOption()) != 'q') {
            System.out.println();

            //build the timeline
            if (option == 'b') {

                System.out.print("Enter the starting year => ");
                String startYear = sc.nextLine();
                System.out.print("Enter the number of generations (0-20) => ");
                String gens = sc.nextLine();

                //Create the empty timeline
                gameTimeline = new Timeline(Integer.valueOf(startYear) , Integer.valueOf(gens), pop);

                System.out.print("Building timeline..................... ");
                //build the initial Timeline
                gameTimeline.buildInitialTimeline();
                System.out.print("Timeline complete");
                System.out.println();



            //run a game
            } else if (option == 'r') {

                System.out.print("Enter the number of players => ");
                String numPlayers = sc.nextLine();
                int nplayers = 3;
                String[] players = {"Player 1","Player 2","Player 3"};

                for(String player : players){
                    player = "Player " + nplayers;
                    nplayers--;
                }

                //ten turns
                for(int i = 0; i < 10; i++){
                    System.out.println();
                    System.out.println(" ----Turn " + i + "---- ");

                    for(String player : players){
                        System.out.println();
                        System.out.println(player + " it's your turn");
                        System.out.println();

                        char turnoption;
                        turnoption = turnOption();

                            System.out.print("Pick an event to change (eventID) => ");
                            String EventID = sc.nextLine();
                            Event event = gameTimeline.findEventID(EventID);

                            //remove event
                            if(turnoption == 'r'){
                                System.out.println("Removing the event and rebuilding the timeline");
                                //gameTimeline.alterTimeline(event, 'r');

                            //start a war
                            }else if (turnoption == 's'){
                                System.out.println("Setting war on the timeline at the given event");
                                //gameTimeline.alterTimeline(event, 's');

                            //introduce a plague
                            }else if(turnoption == 'i'){
                                System.out.println("Setting a plague on the timeline at the given event");
                                //gameTimeline.alterTimeline(event, 'i');

                            //discover an invention
                            }else if(turnoption == 'd'){
                                System.out.println("Discover an invention as a child of this event");
                                //gameTimeline.alterTimeline(event, 'd');


                            }
                    }
                    System.out.println("End of turn " + i);

                    System.out.println();
                    System.out.println();
                    System.out.println("  ----Report Cards----  ");
                    System.out.println();
                    System.out.println("Game Stats: ");

                    System.out.println();
                    System.out.println("Current Population: ");
                    System.out.println(gameTimeline.pop.countPeople);

                    System.out.println();
                    System.out.println("Total Events: ");
                    System.out.println(gameTimeline.EventCount);

                    System.out.println();
                    System.out.println("Total Wealth Generated: " + gameTimeline.totalWealth);


                    System.out.println();
                    System.out.println("Player 1 Stats: ");

                    System.out.println();
                    System.out.println("Current Relatives:  ");

                    System.out.println();
                    System.out.println("Current Family Wealth:  ");



                    System.out.println();
                    System.out.println("Player 2 Stats: ");

                    System.out.println();
                    System.out.println("Current Relatives:  ");

                    System.out.println();
                    System.out.println("Current Family Wealth:  ");



                    System.out.println();
                    System.out.println("Player 3 Stats: ");

                    System.out.println();
                    System.out.println("Current Relatives:  ");

                    System.out.println();
                    System.out.println("Current Family Wealth:  ");
                }


                System.out.println();
                System.out.println("Game Stats: ");

                System.out.println();
                System.out.println("Current Population: ");
                System.out.println(gameTimeline.pop.countPeople);

                System.out.println();
                System.out.println("Total Events: ");
                System.out.println(gameTimeline.EventCount);

                System.out.println();
                System.out.println("Total Wealth Generated: " + gameTimeline.totalWealth);



                System.out.println();
                System.out.println("Player 1 Stats: ");

                System.out.println();
                System.out.println("Change in Relatives:  ");

                System.out.println();
                System.out.println("Change in Family Wealth Generated:  ");



                System.out.println();
                System.out.println("Player 2 Stats: ");

                System.out.println();
                System.out.println("Change in Relatives:  ");

                System.out.println();
                System.out.println("Change in Family Wealth Generated:  ");



                System.out.println();
                System.out.println("Player 3 Stats: ");

                System.out.println();
                System.out.println("Change in Relatives:  ");

                System.out.println();
                System.out.println("Change in Family Wealth Generated:  ");




            //print the event tree
            } else if (option == 'p') {

                if (gameTimeline == null){
                    System.out.println("Please build a timeline first");
                    continue;
                }

                //prints out the tree in a level order traversal
                gameTimeline.printLevelOrder(gameTimeline.root);

                System.out.println();
                System.out.println("Current Population: ");
                System.out.println(gameTimeline.pop.countPeople);

                System.out.println();
                System.out.println("Total Events: ");
                System.out.println(gameTimeline.EventCount);

                System.out.println();
                System.out.println("Total Wealth Generated: " + gameTimeline.totalWealth);



               /* System.out.println("Years Passed");
                Event ptr = gameTimeline.root;
                while(ptr.ChildEvents.size()>1){
                    ptr.ChildEvents.trimToSize();
                    ptr = ptr.ChildEvents.get(ptr.ChildEvents.size()-1);
                }
                System.out.println(ptr.EventYear + 20);
                */

                System.out.println();
                System.out.println("Current Population: ");
                System.out.println(gameTimeline.pop.countPeople);

                System.out.println();
                System.out.println("Total Events: ");
                System.out.println(gameTimeline.EventCount);

                System.out.println();
                System.out.println("Total Wealth Generated: " + gameTimeline.totalWealth);





            //find an event
            } else if (option == 'f') {

                System.out.print("Enter the event id => ");
                String eventID = sc.nextLine();

                Event foundEvent = gameTimeline.findEventID(eventID);
                //print all its info



            //list a person
            } else if (option == 'l') {

                System.out.print("Enter the person id => ");
                String personID = sc.nextLine();

                //print the person info

            }
        }
    }
}
