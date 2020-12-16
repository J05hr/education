package cs431.inventory.filtering;

import cs431.inventory.objects.Item;

/**
 * Class definition for a BST which stores an Item in one of its fields
 */
public class SortedByItemName {
    private Item stored_item;
    public SortedByItemName left = null;
    public SortedByItemName right = null;

    public SortedByItemName(Item citem) throws Exception{
        if(citem == null){
            throw new Exception("Item has value null!");
        }
        this.stored_item = citem;
    }

    /**
     * Adds item to binary search tree using the lexigraphical value of the item name
     * @param item to be added to the BST
     * @throws Exception when duplicate items are found
     */
    public void addItem(Item citem) throws Exception{
        if(this.stored_item.getName().compareTo(citem.getName()) == 0){
            if(this.stored_item.getBrand().compareTo(citem.getBrand())==0){
                throw new Exception("There exists a duplicate item!");
            }
            else if(this.stored_item.getBrand().compareTo(citem.getBrand()) > 0){
                if(this.left == null){
                    this.left = new SortedByItemName(citem);
                }
                else{
                    this.left.addItem(citem);
                }
                return;
            }
            else{
                if(this.right == null){
                    this.right = new SortedByItemName(citem);
                }
                else{
                    this.right.addItem(citem);
                }
                return;
            }
        }
        else if(this.stored_item.getName().compareTo(citem.getName()) > 0){
            if(this.left == null){
                this.left = new SortedByItemName(citem);
            }
            else{
                this.left.addItem(citem);
            }
            return;
        }
        else{
            if(this.right == null){
                this.right = new SortedByItemName(citem);
            }
            else{
                this.right.addItem(citem);
            }
            return;
        }
    }
}
