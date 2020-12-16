package cs431.inventory.utils;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import cs431.inventory.objects.Category;
import cs431.inventory.objects.Item;
import cs431.inventory.objects.DBOP;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VER = 1;
    private static final String DATABASE_NAME = "ims_db";

    private static final String[] ITEM_COLUMNS = {"id", "name", "brand", "description", "quantity",
        "price", "weight", "categories"};

    private static final String[] HISTORY_COLUMNS = {"id", "name", "brand", "description", "quantity",
            "price", "weight", "categories"};

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ITEMS_TABLE = "CREATE TABLE Items (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "name TEXT," +
                "brand TEXT," +
                "description TEXT," +
                "quantity INTEGER," +
                "price REAL," +
                "weight REAL," +
                "categories TEXT" +
                ")";
        db.execSQL(CREATE_ITEMS_TABLE);

        String CREATE_HISTORY_TABLE = "CREATE TABLE History (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "itemID INTEGER," +
                "operation TEXT," +
                "oldValues TEXT," +
                "newValues TEXT" +
                ")";
        db.execSQL(CREATE_HISTORY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Items");
        this.onCreate(db);
    }

    public void deleteItem(Item item){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Items", "id = ?", new String[] {String.valueOf(item.getId())});

        ArrayList<String> oldValues = new ArrayList<>();
        oldValues.add(item.getName());
        oldValues.add(item.getBrand());
        oldValues.add(item.getDescription());
        oldValues.add(String.valueOf(item.getQuantity()));
        oldValues.add(String.valueOf(item.getPrice()));
        oldValues.add(String.valueOf(item.getWeight()));
        oldValues.add(item.getCatString());

        DBOP dbop = new DBOP(item.getId(), "delete", oldValues,null);
        this.addDBOP(dbop);
        
        db.close();
    }

    public long addItem(Item item){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", item.getName());
        values.put("brand", item.getBrand());
        values.put("description", item.getDescription());
        values.put("quantity", item.getQuantity());
        values.put("price", item.getPrice());
        values.put("weight", item.getWeight());
        values.put("categories", item.getCatString());
        long ret = db.insert("items", null, values);

        ArrayList<String> newValues = new ArrayList<>();
        newValues.add(item.getName());
        newValues.add(item.getBrand());
        newValues.add(item.getDescription());
        newValues.add(String.valueOf(item.getQuantity()));
        newValues.add(String.valueOf(item.getPrice()));
        newValues.add(String.valueOf(item.getWeight()));
        newValues.add(item.getCatString());

        DBOP dbop = new DBOP((int) ret, "add", null, newValues);
        this.addDBOP(dbop);

        db.close();
        return ret;
    }

    public int updateItem(Item updatedItem){
        int ret = -1;
        //get the old data from the db using the id
        Item oldItem = this.getItem(updatedItem.getId());
        //continue if you got something
        if(oldItem != null){
            ArrayList<String> oldValues = new ArrayList<>();
            oldValues.add(oldItem.getName());
            oldValues.add(oldItem.getBrand());
            oldValues.add(oldItem.getDescription());
            oldValues.add(String.valueOf(oldItem.getQuantity()));
            oldValues.add(String.valueOf(oldItem.getPrice()));
            oldValues.add(String.valueOf(oldItem.getWeight()));
            oldValues.add(oldItem.getCatString());

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("name", updatedItem.getName());
            values.put("brand", updatedItem.getBrand());
            values.put("description", updatedItem.getDescription());
            values.put("quantity", updatedItem.getQuantity());
            values.put("price", updatedItem.getPrice());
            values.put("weight", updatedItem.getWeight());
            values.put("categories", updatedItem.getCatString());

            ArrayList<String> newValues = new ArrayList<>();
            newValues.add(updatedItem.getName());
            newValues.add(updatedItem.getBrand());
            newValues.add(updatedItem.getDescription());
            newValues.add(String.valueOf(updatedItem.getQuantity()));
            newValues.add(String.valueOf(updatedItem.getPrice()));
            newValues.add(String.valueOf(updatedItem.getWeight()));
            newValues.add(updatedItem.getCatString());

            ret = db.update("Items", values, "id = ?", new String[] {String.valueOf(updatedItem.getId())});

            DBOP dbop = new DBOP(updatedItem.getId(), "edit", oldValues, newValues);
            this.addDBOP(dbop);

            db.close();
        }
        return ret;
    }


    public Item getItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.query("items", ITEM_COLUMNS, "id = ?", new String[] {String.valueOf(id)}, null, null, null, null);

        if (cursor != null){
            cursor.moveToFirst();
        }

        //TODO: check on category translation, might need to involve category constructor explicitly
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Category>>() {}.getType();
        ArrayList<Category> catList = gson.fromJson(cursor.getString(7), type);
        Item item;

        item = new Item(cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getInt(4),
                    Double.parseDouble(cursor.getString(5)),
                    Double.parseDouble(cursor.getString(6)));

        item.setCatergories(catList);
        item.setId(cursor.getInt(0));

        cursor.close();
        db.close();
        return item;
    }

    public ArrayList<Item> getAllItems(){
        ArrayList<Item> items = new ArrayList<>();
        String query = "SELECT * FROM items";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Category>>() {}.getType();

        if (cursor.moveToFirst()) {
            do {
                //TODO: check on category translation, might need to involve category constructor explicitly
                ArrayList<Category> catList = gson.fromJson(cursor.getString(7), type);
                Item item = new Item(cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4),
                        Double.parseDouble(cursor.getString(5)),
                        Double.parseDouble(cursor.getString(6)));
                item.setCatergories(catList);
                item.setId(cursor.getInt(0));

                items.add(item);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return items;
    }
    
    public long addDBOP(DBOP dbop){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("itemid", dbop.getItemId());
        values.put("operation", dbop.getOperation());
        values.put("oldValues", dbop.getOldValuesString());
        values.put("newValues", dbop.getNewValuesString());
        long ret = db.insert("History", null, values);
        db.close();
        return ret;
    }
    
    public ArrayList<DBOP> getHistory() {
        ArrayList<DBOP> history = new ArrayList<>();
        String query = "SELECT * FROM History";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<String>>() {}.getType();

        if (cursor.moveToFirst()) {
            do {
                ArrayList<String> oldValues = gson.fromJson(cursor.getString(3), type);
                ArrayList<String> newValues = gson.fromJson(cursor.getString(4), type);
                DBOP dbop = new DBOP(
                        cursor.getInt(1),
                        cursor.getString(2),
                        oldValues,
                        newValues
                        );
                dbop.setId(cursor.getInt(0));
                history.add(dbop);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return history;
    }
}
