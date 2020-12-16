package cs431.inventory.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import cs431.inventory.R;
import cs431.inventory.activities.InventoryListingActivity;
import cs431.inventory.objects.Category;

public class FilterDialog extends Dialog {
    private Activity activity;
    private Dialog dialog;
    private Spinner categoryFilter;
    private Spinner quantityFilter;
    private Button cancelButton;
    private Button clearButton;
    private Button filterButton;

    public FilterDialog(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_filter);

        cancelButton = (Button) findViewById(R.id.filter_cancel_button);
        clearButton = (Button) findViewById(R.id.filter_clear_button);
        filterButton = (Button) findViewById(R.id.filter_filter_button);

        // TODO: Get list of categories (as an array)
        // Its hardcoded for now but once the database is setup the list should be retrieved
        // from it
        Category[] categoryList = {
                new Category("None"),
                new Category("Food"),
                new Category("Cooking Utensils"),
                new Category("Dining Utensils")
        };
        categoryFilter = (Spinner) findViewById(R.id.category_filter);
        ArrayAdapter<Category> categorySpinnerAdapter = new ArrayAdapter<>(activity.getApplicationContext(), android.R.layout.simple_spinner_item, categoryList);
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryFilter.setAdapter(categorySpinnerAdapter);

        // TODO: Either keep it hard coded like this or create dynamic ranges
        String[] quantityList = {
                "All",
                "1 - 5",
                "6 - 10",
                "11 - 15",
                "16 - 20"
        };
        quantityFilter = (Spinner) findViewById(R.id.quantity_filter);
        ArrayAdapter<String> quantitySpinnerAdapter = new ArrayAdapter<>(activity.getApplicationContext(), android.R.layout.simple_spinner_item, quantityList);
        quantitySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        quantityFilter.setAdapter(quantitySpinnerAdapter);

        // Set spinners to currently selected items
        categoryFilter.setSelection(InventoryListingActivity.selectedCategoryPos);
        quantityFilter.setSelection(InventoryListingActivity.selectedQuantityPos);

        // Click listeners
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((InventoryListingActivity) activity).getFilterDialog().dismiss();
            }
        });
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InventoryListingActivity.selectedCategoryPos = 0;
                InventoryListingActivity.selectedQuantityPos = 0;
                categoryFilter.setSelection(0);
                quantityFilter.setSelection(0);
                filterButton.performClick();
            }
        });
        filterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InventoryListingActivity.selectedCategoryPos = categoryFilter.getSelectedItemPosition();
                InventoryListingActivity.selectedQuantityPos = quantityFilter.getSelectedItemPosition();

                // TODO: Filter inventory listing items
                // ...

                ((InventoryListingActivity) activity).getFilterDialog().dismiss();
            }
        });
    }
}
