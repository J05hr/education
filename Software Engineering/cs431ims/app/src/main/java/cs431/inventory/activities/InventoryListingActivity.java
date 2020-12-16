package cs431.inventory.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import cs431.inventory.R;
import cs431.inventory.controllers.InventoryListingAdapter;
import cs431.inventory.dialogs.FilterDialog;
import cs431.inventory.objects.Category;
import cs431.inventory.objects.Item;
import cs431.inventory.objects.User;
import cs431.inventory.utils.DatabaseHandler;

public class InventoryListingActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    private FilterDialog filterDialog;
    private NavigationView navigationView;
    private int userType;

    // Keeps track of which items are selected in the filters
    public static int selectedCategoryPos = 0;
    public static int selectedQuantityPos = 0;

    private int BARCODE_READER_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory_listing);
        Intent i = getIntent();
        userType = i.getIntExtra("USER_TYPE", User.CUSTOMER);
        DatabaseHandler dbHelper = new DatabaseHandler(getApplicationContext());
        ArrayList<Item> items = dbHelper.getAllItems();
        if (items.size()<1){
            //if the db is empty add in the sample data
            ArrayList<Item> inputItems = new ArrayList<>();
            ArrayList<Category> categoriesTempList = new ArrayList<>();
            inputItems.add(new Item("Bananas", "chiquita","desc", 5, 10.00, 10.00));
            inputItems.add(new Item("Oranges","the orange ones","desc", 10, 1.00, 5.00));
            inputItems.add(new Item("Pineapples", "the piney ones","desc", 273, 5.00, 1.00));
            Item tempItem = new Item("Peaches", "the boring ones", "desc", 37, 2.00, 2.00);
            categoriesTempList.add(new Category("Food"));
            categoriesTempList.add(new Category("Fruit"));
            tempItem.setCatergories(categoriesTempList);
            inputItems.add(tempItem);

            for(Item item : inputItems){
                long newId = dbHelper.addItem(item);
                // print a line on error, for testing
                if (newId == -1){
                    System.out.println("Item "+item.toString()+" failed to add to DB");
                }
            }
            items = dbHelper.getAllItems();


        }
        /*
        Item anItem = dbHelper.getAllItems().get(0);
        System.out.println(anItem.getId());
        anItem.setName("Test");
        dbHelper.updateItem(anItem);

         */
        // ----------------------

        // Initialize
        navigationView = (NavigationView) findViewById(R.id.inventory_listing_navdrawer);
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
        toolbar = (Toolbar) findViewById(R.id.inventory_listing_toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.inventory_listing_drawerlayout);

        // Add menu icons to toolbar
        toolbar.inflateMenu(R.menu.inventory_listing_action_menu);
        // Open nav drawer on nav menu click
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getTitle().equals("Scan Item")) {
                    goToScanBarcodeActivity();
                } else if (item.getTitle().equals("Filter Item")) {
                    filterDialog = new FilterDialog(InventoryListingActivity.this);
                    filterDialog.show();
                } else if (item.getTitle().equals("Add Item")) {
                    Intent intent = new Intent(getApplicationContext(), ItemFormActivity.class);
                    intent.putExtra("ITEM_PASSED", false);
                    intent.putExtra("USER_TYPE", userType);
                    startActivity(intent);
                }
                return false;
            }
        });

        // Recycler view
        recyclerView = (RecyclerView) findViewById(R.id.inventory_listing_recyclerview);
        layoutManager = new LinearLayoutManager(this);
        adapter = new InventoryListingAdapter(items, this, userType);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void goToScanBarcodeActivity() {
        Intent intent = new Intent(getApplicationContext(), ScanBarcodeActivity.class);
        startActivityForResult(intent, BARCODE_READER_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Check which request we're responding to
        if (requestCode == BARCODE_READER_REQUEST_CODE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Barcode barcode = data.getParcelableExtra("BARCODE_RESULT");
                    String s = barcode.displayValue;
                }
            }
        }
    }

    public FilterDialog getFilterDialog() {
        return filterDialog;
    }
}
