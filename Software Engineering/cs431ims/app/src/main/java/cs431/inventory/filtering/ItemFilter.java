package cs431.inventory.filtering;

import java.util.ArrayList;

import cs431.inventory.objects.Item;
import cs431.inventory.utils.DatabaseHandler;

public class ItemFilter {
//    /**
//     * @param {String} which is being sorted in filterByName() function
//     * @return int representation of the first char of the string name
//     */

//    private static int orderIndex(String name){
//         return Character.toUpperCase(name.charAt(0)) - 65;
//    }

//    /**
//     * Sorts the item list obtained by querying the database
//     * @return Sorted array of SortedByItemName objects in alphabetical order using the stored items name
//     */
//    public static SortedByItemName[] filterByName() throws Exception{
//        DatabaseHandler dbhelper = new DatabaseHandler(getApplicationContext());
//        ArrayList<Item> items = dbhelper.getAllItems();
//        SortedByItemName[] alph_order = new SortedByItemName[26];
//        int index = -1;
//        for(Item i: items){
//            index = orderIndex(i.getName());
//            try{
//                if(alph_order[index]==null){
//                    alph_order[index]=new SortedByItemName(i);
//                    continue;
//                }
//                alph_order[index].addItem(i);
//            }
//            catch(Exception e){
//                throw e;
//            }
//        }
//        return alph_order;
//    }
}
