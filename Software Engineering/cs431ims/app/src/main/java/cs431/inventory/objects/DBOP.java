package cs431.inventory.objects;

import com.google.gson.Gson;

import java.util.ArrayList;

public class DBOP {
   private int pkid;
   private int itemid;
   private String operation;
   private ArrayList<String> oldValues;
   private ArrayList<String> newValues;


    public DBOP(int itemid, String operation, ArrayList<String> oldValues, ArrayList<String> newValues) {
        this.pkid = -1;
        this.itemid = itemid;
        this.operation = operation;
        this.oldValues = oldValues;
        this.newValues = newValues;
    }


    public int getId() {
        return this.pkid;
    }
    public int getItemId() {
        return this.itemid;
    }
    public String getOperation() {
        return this.operation;
    }
    public ArrayList<String> getOldValues() { return this.oldValues; }
    public ArrayList<String> getNewValues() {
        return this.newValues;
    }

    public void setId(int pkid) {
        this.pkid = pkid;
    }
    public void setItemId(int itemid) {
        this.itemid = itemid;
    }
    public void setOperation(String operation) {
        this.operation = operation;
    }
    public void setOldValues(ArrayList<String> oldValues) {
        this.oldValues = oldValues;
    }
    public void setNewValues(ArrayList<String> newValues) {
        this.newValues = newValues;
    }
    public String getOldValuesString() {
        Gson gson = new Gson();
        return gson.toJson(this.oldValues);
    }
    public String getNewValuesString() {
        Gson gson = new Gson();
        return gson.toJson(this.newValues);
    }
}
