import java.util.ArrayList;

public class Event {

    String EventID;
    String EventType;
    int EventYear;
    Event ParentEvent;
    ArrayList<Event> ChildEvents;
    Population pop;
    boolean war;
    boolean peaceTreaty;
    boolean plague;
    boolean cure;



    public Event(String EventID, String EventType, int EventYear, Event ParentEvent, Population pop){

        this.EventID = EventID;
        this.EventType = EventType;
        this.EventYear = EventYear;
        this.ParentEvent = ParentEvent;
        this.ChildEvents = new ArrayList<Event>();
        this.pop = pop;
        this.war = false;
        this.peaceTreaty = false;
        this.plague = false;
        this.cure = false;

    }


    public void addChild(Event child) {
        child.setParent(this);
        this.ChildEvents.add(child);
    }



    public void addChildren(ArrayList<Event> children) {
        children.forEach(each -> each.setParent(this));
        this.ChildEvents.addAll(children);
    }


    public void remove() {
        if (ParentEvent != null) {
            ParentEvent.removeChild(this);
        }
    }


    public void removeChild(Event child) {
        if (ChildEvents.contains(child))
            ChildEvents.remove(child);

    }


    public ArrayList<Event> getChildren() {
        return ChildEvents;
    }


    public void setParent(Event parent) {
        this.ParentEvent = parent;
    }


    public Event getParent() {
        return ParentEvent;
    }


    public int getLevel() {
        int level = 0;
        Event p = ParentEvent;
        while (p != null) {
            ++level;
            p = p.ParentEvent;
        }
        return level;
    }






}
