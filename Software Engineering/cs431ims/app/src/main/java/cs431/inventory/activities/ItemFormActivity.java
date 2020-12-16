package cs431.inventory.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import cs431.inventory.R;
import cs431.inventory.dialogs.DeleteDialog;
import cs431.inventory.objects.Category;
import cs431.inventory.objects.Item;
import cs431.inventory.objects.User;
import cs431.inventory.utils.DatabaseHandler;

public class ItemFormActivity extends AppCompatActivity {
    private EditText nameEditText;
    private EditText quantityEditText;
    private EditText weightEditText;
    private EditText descEditText;
    private EditText priceEditText;
    private EditText brandEditText;
    private Item selectedItem = null;
    private ConstraintLayout constraintLayout;
    private TextView categoryLabel;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private DeleteDialog deleteDialog;
    private ArrayList<CheckBox> checkBoxList;
    private NavigationView navigationView;
    private int userType;

    private cs431.inventory.utils.DatabaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_form);
        // Initialize
        Intent intent = getIntent();
        dbHandler = new DatabaseHandler(getApplicationContext());
        userType = intent.getIntExtra("USER_TYPE", User.CUSTOMER);
        selectedItem = (Item) intent.getParcelableExtra("SELECTED_ITEM");
        nameEditText = (EditText) findViewById(R.id.itemform_name_edittext);
        quantityEditText = (EditText) findViewById(R.id.itemform_quantity_edittext);
        weightEditText = (EditText) findViewById(R.id.itemform_weight_edittext);
        descEditText = (EditText) findViewById(R.id.itemform_desc_edittext);
        priceEditText = (EditText) findViewById(R.id.itemform_price_edittext);
        brandEditText = (EditText) findViewById(R.id.itemform_brand_edittext);
        constraintLayout = (ConstraintLayout) findViewById(R.id.item_form_cl);
        categoryLabel = (TextView) findViewById(R.id.itemform_category_label);
        toolbar = (Toolbar) findViewById(R.id.itemform_view_toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.itemform_view_drawerlayout);
        navigationView = (NavigationView) findViewById(R.id.itemform_navdrawer);
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
        // If true, the edit button was pressed from the inventory listing view; hence, there is a selected item
        // If false, the add button was pressed from the item view; hence, there is no selected item
        final boolean itemPassed = intent.getBooleanExtra("ITEM_PASSED", false);
        // Get values of selected item and place it in appropriate places
        if (itemPassed) {
            nameEditText.setText(selectedItem.getName());
            quantityEditText.setText(selectedItem.getQuantity() + "");
            weightEditText.setText(String.valueOf(selectedItem.getWeight()));
            descEditText.setText(selectedItem.getDescription());
            priceEditText.setText(String.valueOf(selectedItem.getPrice()));
            brandEditText.setText(selectedItem.getBrand());
        }


        // Add menu icons to toolbar
        if (itemPassed) {
            toolbar.inflateMenu(R.menu.itemform_action_menu);
        }
        else {
            toolbar.inflateMenu(R.menu.add_itemform_action_menu);
        }
        // Open nav drawer on nav menu click
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });

        // TODO: Get all categories from DB and replace hardcoded ones
        final ArrayList<Category> allCategories = new ArrayList<>();
        allCategories.add(new Category("Snacks"));
        allCategories.add(new Category("Meals"));
        allCategories.add(new Category("Sweets"));
        allCategories.add(new Category("Breakfast"));
        allCategories.add(new Category("Food"));
        // --------------------------------------------------------

        // Apply layout attributes to checkbox items
        final ConstraintSet set = new ConstraintSet();
        set.clone(constraintLayout);
        // List of checkboxes
        checkBoxList = new ArrayList<>();
        for (Category category : allCategories) {
            CheckBox checkBox = new CheckBox(getApplicationContext());
            checkBox.setText(category.getName());
            checkBox.setId(View.generateViewId());
            // Check checkboxes that categories selected in item (only if itemPassed is true)
            if (itemPassed) {
                for (Category cat : selectedItem.getCatergories()) {
                    if (cat.getName().toLowerCase().equals(category.getName().toLowerCase())) {
                        checkBox.setChecked(true);
                    }
                }
            }
            // Define layout
            if (checkBoxList.size() == 0) {
                set.connect(checkBox.getId(), ConstraintSet.TOP,
                        categoryLabel.getId(), ConstraintSet.BOTTOM, 0);
                set.connect(checkBox.getId(), ConstraintSet.LEFT,
                        categoryLabel.getId(), ConstraintSet.LEFT, 0);
            }
            else {
                set.connect(checkBox.getId(), ConstraintSet.TOP,
                        checkBoxList.get(checkBoxList.size() - 1).getId(), ConstraintSet.BOTTOM);
                set.connect(checkBox.getId(), ConstraintSet.LEFT,
                        checkBoxList.get(checkBoxList.size() - 1).getId(), ConstraintSet.LEFT);
            }
            set.constrainHeight(checkBox.getId(), ConstraintSet.WRAP_CONTENT);
            set.constrainWidth(checkBox.getId(), ConstraintSet.WRAP_CONTENT);
            // Add checkbox to list and UI
            checkBoxList.add(checkBox);
            constraintLayout.addView(checkBox, ConstraintLayout.LayoutParams.WRAP_CONTENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
        }
        // Apply layouts
        set.applyTo(constraintLayout);

        // Toolbar action
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getTitle().equals("Save Item")) {
                    String name = nameEditText.getText().toString();
                    String quantity = quantityEditText.getText().toString();
                    // TODO: Traverse through checkBoxList to get all checked checkboxes. Not sure how but an arraylist of category objects are needed to update the item, right?

                    String weight = weightEditText.getText().toString();
                    String desc = descEditText.getText().toString();
                    String price = priceEditText.getText().toString();
                    String brand = brandEditText.getText().toString();

                    if (itemPassed) {
                        // TODO: Item was edited. Update item on DB.
                        dbHandler.deleteItem(selectedItem);
                        selectedItem.setName(name);
                        selectedItem.setQuantity(Integer.parseInt(quantity));
                        selectedItem.setBrand(brand);
                        selectedItem.setDescription(desc);
                        selectedItem.setPrice(Float.parseFloat(price));
                        selectedItem.setWeight(Float.parseFloat(weight));
                        for (int i = 0; i < checkBoxList.size(); i++) {
                            if (checkBoxList.get(i).isChecked())
                                selectedItem.addCatergory(allCategories.get(i));
                        }
                        dbHandler.addItem(selectedItem);

                        finish();
                        Intent intent = new Intent(getApplicationContext(), InventoryListingActivity.class);
                        intent.putExtra("USER_TYPE", userType);
                        startActivity(intent);
                    }
                    else {
                        //String name, String brand, String description, int quantity, double price, double weight
                        //TODO: Add other data when fields are added
                        Item newItem = new Item(name, brand,desc,Integer.parseInt(quantity), Double.parseDouble(price),Double.parseDouble(weight));
                        for (int i = 0; i < checkBoxList.size(); i++) {
                            if (checkBoxList.get(i).isChecked())
                                newItem.addCatergory(allCategories.get(i));
                        }
                        dbHandler.addItem(newItem);

                        finish();
                        Intent intent = new Intent(getApplicationContext(), InventoryListingActivity.class);
                        intent.putExtra("USER_TYPE", userType);
                        startActivity(intent);
                    }
                    // TODO: After add or update, go back to item view or inventory listing
                } else if (item.getTitle().equals("Delete Item")) {
                    deleteDialog = new DeleteDialog(ItemFormActivity.this, userType);
                    deleteDialog.show();
                }
                return false;
            }
        });
    }

    public Item getSelectedItem() {
        return selectedItem;
    }

    public DeleteDialog getDeleteDialog() {
        return deleteDialog;
    }
}
