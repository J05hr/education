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
import android.widget.Button;

import com.google.android.material.navigation.NavigationView;

import cs431.inventory.R;
import cs431.inventory.objects.User;

public class ShortcutActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Button usersButton;
    private Button categoriesButton;
    private int userType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shortcut);

        // Initialize
        Intent intent = getIntent();
        userType = intent.getIntExtra("USER_TYPE", User.CUSTOMER);
        usersButton = (Button) findViewById(R.id.shortcut_user_button);
        categoriesButton = (Button) findViewById(R.id.shortcut_categories_button);
        usersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Switch to User list activity
            }
        });
        categoriesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Switch to category listing activity
            }
        });
        navigationView = (NavigationView) findViewById(R.id.shortcut_navdrawer);
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
        toolbar = (Toolbar) findViewById(R.id.shortcut_toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.shortcut_drawerlayout);

        // Open nav drawer on nav menu click
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }
}
