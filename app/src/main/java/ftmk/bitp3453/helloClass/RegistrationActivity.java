package ftmk.bitp3453.helloClass;

import static android.app.DatePickerDialog.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.util.Calendar;

import ftmk.bitp3453.helloClass.databinding.ActivityRegistrationBinding;

public class RegistrationActivity extends AppCompatActivity {

    DatePickerDialog datePicker;
    ActivityRegistrationBinding binding;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegistrationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.edtBirthdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {  // user can use tab to change the text field

                fnInvokeDatePicker();
            }
        });
        binding.edtBirthdate.setOnClickListener(new View.OnClickListener() {  //user use click to change the text field
            @Override
            public void onClick(View view) {
                fnInvokeDatePicker();
            }
        });


        binding.fabAddUser.setEnabled(false);
        binding.fabAddUser.setOnClickListener(this::fnAddUser);

        // Add form validation control which disabled the floating action button when all the view element is empty
        binding.rbFemale.setChecked(true);
        binding.rbMale.setChecked(true);
        binding.edtFullName.addTextChangedListener(textWatcher);
        binding.edtPwd.addTextChangedListener(textWatcher);
        binding.edtBirthdate.addTextChangedListener(textWatcher);
        binding.edtEmail.addTextChangedListener(textWatcher);
        binding.edtAddress.addTextChangedListener(textWatcher);
        binding.rbMale.addTextChangedListener(textWatcher);
        binding.rbFemale.addTextChangedListener(textWatcher);

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
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
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

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            CharSequence cs1 = binding.edtFullName.getText().toString().trim();
            CharSequence cs2 = binding.edtPwd.getText().toString().trim();
            CharSequence cs3 = binding.edtBirthdate.getText().toString().trim();
            CharSequence cs4 = binding.edtEmail.getText().toString().trim();
            CharSequence cs5 = binding.edtAddress.getText().toString().trim();

            if (cs1.length() > 0 && cs2.length() > 0 && cs3.length() > 0 && cs4.length() > 0
                    && cs5.length() > 0 && (binding.rbFemale.isChecked() || binding.rbMale.isChecked()))
                binding.fabAddUser.setEnabled(true);
            else
                binding.fabAddUser.setEnabled(false);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private void fnInvokeDatePicker(){

        final Calendar cldr = Calendar.getInstance();
        int day = cldr.get(Calendar.DAY_OF_MONTH);
        int month = cldr.get(Calendar.MONTH);
        int year = cldr.get(Calendar.YEAR);
        //date picker dialog
        datePicker = new DatePickerDialog(RegistrationActivity.this,
              new DatePickerDialog.OnDateSetListener(){

                  @Override
                  public void onDateSet(DatePicker view, int year, int monthOfYear, int
                          dayOfMonth) {
                      binding.edtBirthdate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" +
                              year);
                  }
              }, year, month,day);
        datePicker.show();
    }

    private void fnAddUser(View view){
         String strFullName = binding.edtFullName.getText().toString();
         String strPwd = binding.edtPwd.getText().toString();
         String strEmail = binding.edtEmail.getText().toString();
         String strBirth = binding.edtBirthdate.getText().toString();
         String strAddress = binding.edtAddress.getText().toString();
         String strGender = "";

         if(binding.rbMale.isChecked())
             strGender = binding.rbFemale.getText().toString();
         else if(binding.rbFemale.isChecked())
             strGender = binding.rbFemale.getText().toString();

         User user = new User(strFullName, strPwd,strAddress,strEmail,strBirth,strGender);

         Intent intent = new Intent(this,SecondActivity.class);
         intent.putExtra("objUser",user);
         startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}