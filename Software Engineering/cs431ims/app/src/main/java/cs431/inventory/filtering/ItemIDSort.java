package cs431.inventory.filtering;

import java.util.Comparator;

import cs431.inventory.objects.Item;

/**
 * comparator class for sorting Item class by ID
 */
public class ItemIDSort implements Comparator<Item> {

    @Override
    public int compare(Item o1, Item o2) {
        if(o1.getId() > o2.getId()){
            return 1;
        }
        else if(o1.getId() < o2.getId()){
            return -1;
        }
        return 0;
    }
}
