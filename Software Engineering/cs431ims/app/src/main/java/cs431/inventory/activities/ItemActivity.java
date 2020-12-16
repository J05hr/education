package cs431.inventory.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.ListIterator;

import cs431.inventory.R;
import cs431.inventory.dialogs.DeleteDialog;
import cs431.inventory.objects.Category;
import cs431.inventory.objects.Item;
import cs431.inventory.objects.User;

public class ItemActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private TextView itemNameTextView;
    private TextView itemQuantityTextView;
    private TextView itemCategoriesTextView;
    private TextView itemWeightTextView;
    private TextView itemDescTextView;
    private TextView itemPriceTextView;
    private TextView itemBrandTextView;
    private DeleteDialog deleteDialog;
    private Item selectedItem = null;
    private NavigationView navigationView;
    private int userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);

        // Initialize
        itemNameTextView = (TextView) findViewById(R.id.report_view_report);
        itemQuantityTextView = (TextView) findViewById(R.id.item_view_quantity);
        itemCategoriesTextView = (TextView) findViewById(R.id.item_view_categories);
        itemWeightTextView = (TextView) findViewById(R.id.item_view_weight);
        itemDescTextView = (TextView) findViewById(R.id.item_view_desc);
        itemPriceTextView = (TextView) findViewById(R.id.item_view_price);
        itemBrandTextView = (TextView) findViewById(R.id.item_view_brand);

        Intent intent = getIntent();
        userType = intent.getIntExtra("USER_TYPE", User.CUSTOMER);
        // Get passed down/selected item from previous activity
        selectedItem = (Item) intent.getParcelableExtra("ITEM");

        toolbar = (Toolbar) findViewById(R.id.report_view_toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.report_drawerlayout);

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
                        Intent reportIntent = new Intent(getApplicationContext(), ReportActivity.class);
                        reportIntent.putExtra("USER_TYPE", userType);
                        startActivity(reportIntent);
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

        // Add menu icons to toolbar with correct permissions
        if (userType == User.EMPLOYEE || userType == User.ADMIN) {
            toolbar.inflateMenu(R.menu.item_view_action_menu);
        }
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
                if (item.getTitle().equals("Edit Item")) {
                    Intent editIntent = new Intent(getApplicationContext(), ItemFormActivity.class);
                    editIntent.putExtra("ITEM_PASSED", true);
                    editIntent.putExtra("SELECTED_ITEM", selectedItem);
                    editIntent.putExtra("USER_TYPE", userType);
                    startActivity(editIntent);
                } else if (item.getTitle().equals("Delete Item")) {
                    deleteDialog = new DeleteDialog(ItemActivity.this, userType);
                    deleteDialog.show();
                }
                return false;
            }
        });

        // Set textview values according to selected item values
        itemNameTextView.setText(selectedItem.getName());
        itemQuantityTextView.setText(selectedItem.getQuantity() + "");
        itemWeightTextView.setText(String.valueOf(selectedItem.getWeight()));
        itemDescTextView.setText(selectedItem.getDescription());
        itemPriceTextView.setText(String.valueOf(selectedItem.getPrice()));
        itemBrandTextView.setText(selectedItem.getBrand());
        String categoriesString = "";
        ArrayList<Category> categoriesArrayList = selectedItem.getCatergories();
        if (categoriesArrayList != null && categoriesArrayList.size() > 0) {
            ListIterator<Category> iterator = categoriesArrayList.listIterator();
            while(iterator.hasNext()) {
                Category currentCategory = iterator.next();
                categoriesString += currentCategory.getName() + "\n";
            }
        }
        else {
            categoriesString = "No categories";
        }
        itemCategoriesTextView.setText(categoriesString);
    }

    public Item getSelectedItem() {
        return selectedItem;
    }

    public DeleteDialog getDeleteDialog() {
        return deleteDialog;
    }
}
