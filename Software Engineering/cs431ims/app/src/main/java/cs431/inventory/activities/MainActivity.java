package cs431.inventory.activities;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import cs431.inventory.R;
import cs431.inventory.activities.InventoryListingActivity;
import cs431.inventory.objects.User;

public class MainActivity extends AppCompatActivity {
    private Button customerButton;
    private Button employeeButton;
    private Button managerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        customerButton = (Button) findViewById(R.id.customer_button);
        employeeButton = (Button) findViewById(R.id.employee_button);
        managerButton = (Button) findViewById(R.id.manager_button);

        // TODO: Check if user type in database
        // If user type not in database --------
        customerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToInventoryListingActivity(User.CUSTOMER);
            }
        });
        employeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToInventoryListingActivity(User.EMPLOYEE);
            }
        });
        managerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToInventoryListingActivity(User.ADMIN);
            }
        });
        // Else ------
        // -------------------
    }
    private void goToInventoryListingActivity(int userType) {
        Intent intent = new Intent(getApplicationContext(), InventoryListingActivity.class);
        intent.putExtra("USER_TYPE", userType);
        startActivity(intent);
    }
}
