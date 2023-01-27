package ftmk.bitp3453.helloClass;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import ftmk.bitp3453.helloClass.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;    // when there is many text view(attribute) STEP 1

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        binding = ActivityMainBinding.inflate(getLayoutInflater()); //STEP 2
        setContentView(binding.getRoot());
        binding.button2.setOnClickListener(this::fnChangeText);

        // drawer layout instance to toggle the menu icon to open
        // drawer and back button to close drawer
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout, R.string.nav_open,
                R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView = findViewById(R.id.nav_menu);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()){
                    case R.id.nav_main_activity:
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        return true;

                    case R.id.nav_first_activity:
                        intent = new Intent(getApplicationContext(), FirstActivity.class);
                        startActivity(intent);
                        return true;

                    case R.id.nav_camera_activity:
                        intent = new Intent(getApplicationContext(), ThreadedActivity.class);
                        startActivity(intent);
                        return true;

                    case R.id.nav_student_registration_activity:
                        intent = new Intent(getApplicationContext(), RegistrationActivity.class);
                        startActivity(intent);
                        return true;

                    case R.id.nav_search_student_activity:
                        intent = new Intent(getApplicationContext(), SearchStudentActivity.class);
                        startActivity(intent);
                        return true;

                    case R.id.nav_student_main_activity:
                        intent = new Intent(getApplicationContext(), StudentMainActivity.class);
                        startActivity(intent);
                        return true;

                    case R.id.nav_settings:
                        Toast.makeText(getApplicationContext(), "You navigated to Setting Screen",
                                Toast.LENGTH_SHORT).show();
                        return true;

                    case  R.id.nav_logout:
                        Toast.makeText(getApplicationContext(), "You are logged out ! See ya!",
                                Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return false;
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void fnChangeText(View view) {

        binding.textView3.setText(R.string.myText);
        binding.button2.setText("Change!");     //STEP 3
        view.setEnabled(false);
    }


}