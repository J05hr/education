public class Timeline {

    Event root;
    int startYear;
    int curYear;
    int endYear;
    int generations;
    int generationLength;
    int popGrowthRate;
    int lifeLength;
    static int EventCount;
    static int totalWealth;
    Population pop;


    public Timeline(int startYear, int generations, Population pop){

        this.startYear = startYear;
        this.curYear = startYear;
        this.endYear = 0;
        this.generations = generations;
        this.generationLength = 20;
        this.popGrowthRate = 4;
        this.lifeLength = 40;
        this.EventCount = 1;
        this.totalWealth = 0;
        this.pop = pop;
        this.root = new Event(String.valueOf(EventCount),"Start of History", curYear,null, pop );

        //initialize the first generation
        pop.countPeople++;
    }


   //postorder traversal
   public Event findEventID(String EventID) {
        if (root == null) {
            return null;
        }
        else if (root.EventID.equals(EventID)){
            return root;
        }
        if(root.ChildEvents != null){
            for (Event event : root.ChildEvents) {
                Event found = findEventID(event.EventID);
                if (found != null) {
                    return findEventID(event.EventID);
                }
            }
        }

       // Not found.
        return null;
    }


    public boolean insertAfter(String ParentEventID, Event event) {
        Event found = findEventID(ParentEventID);
        if (found == null) {
            return false;
        }
        found.getChildren().add(event);
        return true;
    }


    private int getHeight(Event event){
        int max = 0;
        if (event.getChildren() != null){
            for (Event childNode  : event.getChildren()) {
                int height = getHeight(childNode);
                if (height > max)
                    max = height;
            }
        }
        return max + 1;
    }


    private int getHeight2(Event event){
        int height = 0;
        while(event != root){
            event = event.ParentEvent;
            height ++;
        }
        return height;
    }


    public void printGivenLevel (Event curEvent, int level) {

        if (level == 1) {
            System.out.print("[EventID: " + curEvent.EventID + ", Type: " + curEvent.EventType + ", Year: " + curEvent.EventYear + " ]  ");
        }else if (level > 1) {
            if (curEvent.getChildren() != null) {
                for (Event event : curEvent.ChildEvents) {
                    if (event.getChildren() != null) {
                        printGivenLevel(event, level -1);
                    }
                }
            }
        }
    }


    public void printLevelOrder(Event event)
    {
        int h = getHeight(event);
        int i;
        for (i=1; i<=h; i++)
        {
            System.out.println("Level: " + i);
            printGivenLevel(event, i);
            System.out.println();
        }
    }


    public void EventHelper (Event parentEvent, int generations) {

        if (parentEvent.EventType.equals("Birth")) {
            parentEvent.addChild(new Event(String.valueOf(EventCount),"Birth", parentEvent.EventYear + 20, parentEvent, pop ));
            EventCount++;
            pop.countPeople++;
            totalWealth += 1000;
            parentEvent.addChild(new Event(String.valueOf(EventCount),"Birth", parentEvent.EventYear + 20, parentEvent, pop ));
            EventCount++;
            pop.countPeople++;
            totalWealth += 1000;
            parentEvent.addChild(new Event(String.valueOf(EventCount),"Death", parentEvent.EventYear + 40, parentEvent, pop ));
            EventCount++;
            pop.countPeople--;

        }else if (parentEvent.EventType.equals("Death")) {



        }else if (parentEvent.EventType.equals("Invention")) {


        }


        if (parentEvent.ChildEvents != null){
            for (Event event : parentEvent.ChildEvents) {
                int height = getHeight2(parentEvent);
                if (height == generations - 2){
                    break;
                }
                EventHelper(event, generations);
            }
        }
    }


    public void buildInitialTimeline(){

        int countYears = 0;
        Event Birth = new Event(null,  "Birth", 0, null, pop  );
        Event Death = new Event(null,"Death", 0, null, pop );
        Event Invention = new Event(null,"Invention", 0, null, pop );

        //for each year
        //for (int year = curYear; curYear > endYear; year++ ){
        //countYears++;}

        //for each level
        //for (int curHeight = 0; curHeight < 20; curHeight++ ){}

        //add and adam and eve starting population 4 births 2 deaths
        root.addChild(new Event(String.valueOf(EventCount),"Birth", root.EventYear + 20, root, pop ));
        EventCount++;
        pop.countPeople++;
        root.addChild(new Event(String.valueOf(EventCount),"Birth", root.EventYear + 20, root, pop ));
        EventCount++;
        pop.countPeople++;

        /*
        root.addChild(new Event(String.valueOf(EventCount),"Birth", root.EventYear + 20, root, pop ));
        EventCount++;
        pop.countPeople++;
        root.addChild(new Event(String.valueOf(EventCount),"Birth", root.EventYear + 20, root, pop ));
        EventCount++;
        pop.countPeople++;
        */

        root.addChild(new Event(String.valueOf(EventCount),"Death", root.EventYear + 40, root, pop ));
        EventCount++;
        pop.countPeople--;
        root.addChild(new Event(String.valueOf(EventCount),"Death", root.EventYear + 40, root, pop ));
        EventCount++;
        pop.countPeople--;


        //call to the recursive method on the initial children
        for (Event event : root.ChildEvents) {
            EventHelper(event, generations);
       }

    }



    public void alterTimeline(Event event, char choice) {

        //wipe out the children of this event
        for (Event childEvent : event.ChildEvents) {
            childEvent.remove();
        }

        //remove event
        if (choice == 'r') {
            //wipe out the children of this event
            for (Event childEvent : event.ChildEvents) {
                childEvent.remove();
            }
            event.remove();


        //start a war
        } else if (choice == 's') {




        //introduce a plague
        } else if (choice == 'i') {



        //discover an invention
        } else if (choice == 'd') {

            event.addChild(new Event(String.valueOf(EventCount),"Invention", event.EventYear + 30, event, pop ));
            EventCount++;

            event.ChildEvents.trimToSize();

            //call to the recursive method on the current event to rebuild the tree;
            EventHelper(event.ChildEvents.get(event.ChildEvents.size()-1), generations );

        }
    }


}
