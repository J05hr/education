package cs431.inventory.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.lang.Exception;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import cs431.inventory.filtering.ItemIDSort;
import cs431.inventory.objects.*;
import cs431.inventory.utils.DatabaseHandler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import cs431.inventory.R;

public class ReportActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private TextView reportTextView;
    private NavigationView navigationView;
    private int userType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        Intent i = getIntent();
        userType = i.getIntExtra("USER_TYPE", User.CUSTOMER);

        // Initialize
        navigationView = (NavigationView) findViewById(R.id.report_navdrawer);
        if (userType != User.ADMIN) {
            navigationView.inflateMenu(R.menu.navdrawer_menu_nonadmin);
        }
        else {
            navigationView.inflateMenu(R.menu.navdrawer_menu);
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navdrawer_inventory_list:
                        Intent invIntent = new Intent(getApplicationContext(), InventoryListingActivity.class);
                        invIntent.putExtra("USER_TYPE", userType);
                        startActivity(invIntent);
                        break;
                    case R.id.navdrawer_reports:
                        Intent navIntent = new Intent(getApplicationContext(), ReportActivity.class);
                        navIntent.putExtra("USER_TYPE", userType);
                        startActivity(navIntent);
                        break;
                    // case R.id.navdrawer_shortcut:
                    //     Intent setIntent = new Intent(getApplicationContext(), ShortcutActivity.class);
                    //     setIntent.putExtra("USER_TYPE", userType);
                    //     startActivity(setIntent);
                    //     break;
                    default:
                        break;
                }
                return false;
            }
        });
        reportTextView = (TextView) findViewById(R.id.report_view_report);
        reportTextView.setMovementMethod(new ScrollingMovementMethod());
        Intent intent = getIntent();
        toolbar = (Toolbar) findViewById(R.id.report_view_toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.report_drawerlayout);


        // Open nav drawer on nav menu click
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

    }

    /**
     * Queries database for item list and events list
     * updates item quantities using the events and prints list of updated item details in sorted order of ids
     */
    public void generateReport(View v){
        if (userType != 2) {
//            throw new Exception("Incorrect permission to access function!");
            reportTextView.setText("Error generating report!");
            return;
        }
        DatabaseHandler dbhelper = new DatabaseHandler(getApplicationContext());
        ArrayList<Item> items = dbhelper.getAllItems();
        Collections.sort(items, new ItemIDSort());
        ArrayList<DBOP> i_events = dbhelper.getHistory();

        HashMap<Integer, Integer> delta_quant = new HashMap<Integer, Integer>();
        //use events to modify
        for (DBOP ie : i_events) {
            try{
                ie.getOldValues().size();
            }
            catch(Exception e){
                continue;
            }
            for (int iter = 0; iter < ie.getOldValues().size(); iter++) {
                switch (ie.getOperation()) {
                    case "delete":
                        delta_quant.remove(ie.getItemId());
                        break;
                    case "edit":
                        delta_quant.remove(ie.getItemId());
                        delta_quant.put(ie.getItemId(), Integer.parseInt(ie.getNewValues().get(4)));
                        break;
                    case "add":
                        delta_quant.put(ie.getItemId(), Integer.parseInt(ie.getNewValues().get(4)));
                        break;
                    default:
                        //throw new Exception("Operation Error!");
                }
            }
        }
        reportTextView.setText("");
        for (Item i2 : items) {
            //id  brand  name  quantity  price  weight
            reportTextView.append("Item ID: " + i2.getId() + "\n");
            reportTextView.append("Brand: " + i2.getBrand() + "\n");
            reportTextView.append("Name: " + i2.getName() + "\n");
            reportTextView.append("Quantity: " + delta_quant.get(i2.getId()) + "\n");
            reportTextView.append("Price: " + i2.getPrice() + "\n");
            reportTextView.append("Weight: " + i2.getWeight() + "\n");
            reportTextView.append("\n");
        }


        // Set textview values according to current report
//        reportTextView.setText(
//                "Item ID: 0\n" +
//                "Item Name: Bananas\n" +
//                "Item Brand: Archer Farms\n" +
//                "Item Price: 3.00\n" +
//                "Item Weight: 0.3\n" +
//                "\n" +
//                "Item ID: 1\n" +
//                "Item Name: Apples\n" +
//                "Item Brand: Archer Farms\n" +
//                "Item Price: 2.00\n" +
//                "Item Weight: 0.3\n" +
//                "\n"
//        );
    }

    public void exportReport(View v) {

    }
}
