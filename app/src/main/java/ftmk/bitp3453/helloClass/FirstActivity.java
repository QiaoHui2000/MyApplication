package ftmk.bitp3453.helloClass;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class FirstActivity extends AppCompatActivity {

    TextView txtvwAge;
    TextView txtNameAge;
    EditText edtName, edtYear;
    Button btnMe;
    ImageView imageView;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        btnMe = (Button) findViewById(R.id.btnMe);
        txtvwAge = (TextView) findViewById(R.id.txtvwAge);
        txtNameAge = (TextView) findViewById(R.id.txtNameAge);
        edtName = (EditText) findViewById(R.id.edtTxtName);
        edtYear = (EditText) findViewById(R.id.edtYear);
        imageView = (ImageView) findViewById(R.id.imageView);


        edtYear.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                final Calendar cldr = Calendar.getInstance();
                int year = cldr.get(Calendar.YEAR);
                int month = cldr.get(Calendar.MONTH);
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                //date picker dialog
                DatePickerDialog dateDialog = new DatePickerDialog(view.getContext(),
                        datePickerListener, year, month, day);
                dateDialog.getDatePicker().setMaxDate(new Date().getTime());
                dateDialog.show();

                if(hasFocus){
                    dateDialog.show();
                }else{
                    finish();
                }
            }
        });

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

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this,"This happen when app is about to pause",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this,"This happen when app is resuming back",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this,"This happen when app is about to stop",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"This happen when app is about to destroy",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this,"This happen when app is started",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this,"This happen when app is restarting",
                Toast.LENGTH_SHORT).show();
    }

    public void fnGreet (View vw)
    {
        String strName = edtName.getText().toString();
        String age = txtNameAge.getText().toString();
        txtvwAge.setText("Hellooo and welcome " + strName + "." + "You are " + age + " years old." );
    }

    public void fnThreadActivity(View vw)
    {
        Intent intent = new Intent(this, ThreadedActivity.class);
        String strMsg = ((EditText) findViewById(R.id.edtTxtName)).getText().toString();
        intent.putExtra("varStr1", strMsg);
        startActivityForResult(intent,1);
    }

    // Send the image to FirstActivity
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                Bitmap bp = (Bitmap) data.getExtras().get("BitmapImage");
                imageView.setImageBitmap(bp);
            }
        }
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            Calendar c = Calendar.getInstance();
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, day);
            String format = new SimpleDateFormat("dd MMM YYYY").format(c.getTime());
            edtYear.setText(format);

            txtNameAge.setText(Integer.toString(calculateAge(c.getTimeInMillis())));

        }

        int calculateAge(long date) {
            Calendar dob = Calendar.getInstance();
            dob.setTimeInMillis(date);
            Calendar today = Calendar.getInstance();
            int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
            if (today.get(Calendar.DAY_OF_MONTH) < dob.get(Calendar.DAY_OF_MONTH)) {
                age--;
            }
            return age;
        }
    };

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}