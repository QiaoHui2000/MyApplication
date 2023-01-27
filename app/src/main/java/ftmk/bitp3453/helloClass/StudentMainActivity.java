package ftmk.bitp3453.helloClass;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import ftmk.bitp3453.helloClass.databinding.ActivityStudentMainBinding;
import ftmk.bitp3453.helloClass.sqlite.StudentSQLite;

public class StudentMainActivity extends AppCompatActivity {

    private ActivityStudentMainBinding binding;
    private Student student;

    private Vector<Student> students;
    private ArrayList<Student> studentList;
    private StudentAdapter adapter;

    private DatePickerDialog datePicker;
    private TimePickerDialog timePicker;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    private StudentSQLite studentSQLite;

    Executor executor;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityStudentMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        studentSQLite = new StudentSQLite(this);

        binding.edtBirthdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                final Calendar cldr = Calendar.getInstance();

                //Change date format
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String date = simpleDateFormat.format(cldr.getTime());

                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);

                int mHour = cldr.get(Calendar.HOUR_OF_DAY);
                int mMinute = cldr.get(Calendar.MINUTE);
                String strDay ="";
                // date picker dialog
                datePicker = new DatePickerDialog(StudentMainActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                                  int dayOfMonth) {
                                binding.edtBirthdate.setText(year  + "-" + (monthOfYear + 1)
                                        + "-" + dayOfMonth);
                                //timePicker.show();
                            }
                        }, year,month, day);
                datePicker.show();

                binding.edtBirthdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final Calendar cldr = Calendar.getInstance();
                        int day = cldr.get(Calendar.DAY_OF_MONTH);
                        int month = cldr.get(Calendar.MONTH);
                        int year = cldr.get(Calendar.YEAR);

                        int mHour = cldr.get(Calendar.HOUR_OF_DAY);
                        int mMinute = cldr.get(Calendar.MINUTE);
                        String strDay ="";
                        // date picker dialog
                        datePicker = new DatePickerDialog(StudentMainActivity.this,
                                new DatePickerDialog.OnDateSetListener() {
                                    @Override
                                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                                          int dayOfMonth) {
                                        binding.edtBirthdate.setText(dayOfMonth + "/" + (monthOfYear + 1)
                                                + "/" + year);
                                        //timePicker.show();
                                    }
                                }, year,month, day);
                        datePicker.show();
                    }



                });

                /*timePicker = new TimePickerDialog(StudentMainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        String strTempDate = binding.edtBirthdate.getText().toString();
                        binding.edtBirthdate.setText(strTempDate + " " + mHour + ":" + mMinute);
                    }
                }, mHour, mMinute, false);*/

                if(hasFocus){
                    datePicker.show();
                }else{
                    finish();
                }
            }
        });

        students = new Vector<>();
        adapter = new StudentAdapter(getLayoutInflater(),students);

        //final step populate the RecyclerView in layout
        binding.rcvStud.setAdapter(adapter);
        binding.rcvStud.setLayoutManager(new LinearLayoutManager(this));

        binding.fabAdd.setOnClickListener(this:: fnAddToREST);
        binding.fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullname = binding.edtFullName.getText().toString();
                String studNo = binding.edtStudNum.getText().toString();
                String email = binding.edtEmail.getText().toString();
                String birth = binding.edtBirthdate.getText().toString();
                String gender = "";
                String state = binding.spnState.getSelectedItem().toString();

                if(binding.rbMale.isChecked())
                    gender = binding.rbFemale.getText().toString();
                else if(binding.rbFemale.isChecked())
                    gender = binding.rbFemale.getText().toString();

                student = new Student(fullname,studNo,email,gender,birth, state);

                /*students.add(student);
                adapter.notifyItemInserted(students.size());*/

                studentSQLite.fnInsertStudent(student);

                fnAddToREST(view);
            }
        });
        //swipeToDelete();


        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView,
                                  @NonNull RecyclerView.ViewHolder viewHolder,
                                  @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                int position = viewHolder.getAdapterPosition();
                students.remove(position);
                //adapter.notifyItemRemoved(position);
                adapter.notifyDataSetChanged();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleItemTouchCallback);
        itemTouchHelper.attachToRecyclerView(binding.rcvStud);
        /*).attachToRecyclerView(binding.rcvStud);*/

        binding.fabAdd.setEnabled(false);
        //binding.fabAdd.setOnClickListener(this::fnAdd);

        // Add form validation control which disabled the floating action button when all the view element is empty
        binding.rbFemale.setChecked(true);
        binding.rbMale.setChecked(true);
        binding.edtFullName.addTextChangedListener(textWatcher);
        binding.edtStudNum.addTextChangedListener(textWatcher);
        binding.edtBirthdate.addTextChangedListener(textWatcher);
        binding.edtEmail.addTextChangedListener(textWatcher);
        binding.edtState.addTextChangedListener(textWatcher);
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

        studentSQLite = new StudentSQLite(StudentMainActivity.this);

        executor= Executors.newSingleThreadExecutor();
        handler = new Handler(Looper.getMainLooper());

        ConnectivityManager manager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        if(networkInfo != null  && networkInfo.isConnected())
        {
            // the background task executor and handler is done within this checking
            executor.execute(new Runnable() {
                @Override
                public void run() {

                    studentList = new ArrayList<>();

                    String strURL = "http://192.168.0.10/RESTAPI/get_data.php";
                    RequestQueue requestQueue = Volley.newRequestQueue(StudentMainActivity.this);
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, strURL, new
                            Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    //JSONObject jsonObject = null;
                                    //Log.e("TAG", "RESPONSE IS " + response);
                                    try {

                                        JSONArray jsonArray = new JSONArray(response);
                                        for(int i=0; i<jsonArray.length(); i++){
                                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                                            studentList.add(new Student(jsonObject.getString("stud_name"), jsonObject.getString("stud_no"),
                                                    jsonObject.getString("stud_email"), jsonObject.getString("stud_gender"),
                                                    jsonObject.getString("stud_dob"), jsonObject.getString("stud_state")));
                                        }
                                        if(studentList.isEmpty()) {
                                            students = new Vector<>();
                                        }
                                        else {
                                            students = new Vector<Student>(studentList);
                                        }
                                        adapter = new StudentAdapter(getLayoutInflater(),students);
                                        binding.rcvStud.setAdapter(adapter);
                                        binding.rcvStud.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                        //JSONObject jsonObject = new JSONObject(response);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // method to handle errors.
                            Toast.makeText(StudentMainActivity.this, "Unable to fetch student info",
                                    Toast.LENGTH_SHORT).show();

                        }
                    });
                    requestQueue.add(stringRequest);

                    handler.post(new Runnable() {  // this is to update main thread -- UI
                        @Override
                        public void run() {

                        }
                    });
                }
            });
        }
        else
        {
            Toast.makeText(getApplicationContext(), "No Network!! Please add data plan or " +
                    "connect to wifi network!", Toast.LENGTH_SHORT).show();

            studentList = (ArrayList<Student>) studentSQLite.fnGetAllStudents();

            if(studentList.isEmpty())
                students = new Vector<>();
            else
                students = new Vector<Student>(studentList);

            adapter = new StudentAdapter(getLayoutInflater(),students);
            binding.rcvStud.setAdapter(adapter);
            binding.rcvStud.setLayoutManager(new LinearLayoutManager(this));

        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            CharSequence cs1 = binding.edtFullName.getText().toString().trim();
            CharSequence cs2 = binding.edtStudNum.getText().toString().trim();
            CharSequence cs3 = binding.edtBirthdate.getText().toString().trim();
            CharSequence cs4 = binding.edtEmail.getText().toString().trim();
            CharSequence cs5 = binding.edtState.getText().toString().trim();

            if (cs1.length() > 0 && cs2.length() > 0 && cs3.length() > 0 && cs4.length() > 0
                    && cs5.length() > 0 && (binding.rbFemale.isChecked() || binding.rbMale.isChecked()))
                binding.fabAdd.setEnabled(true);
            else
                binding.fabAdd.setEnabled(false);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };




    /*private void fnAdd(View view){
        String fullname = binding.edtFullName.getText().toString();
        String studNo = binding.edtStudNum.getText().toString();
        String email = binding.edtEmail.getText().toString();
        String birth = binding.edtBirthdate.getText().toString();
        String gender = "";
        String state = binding.spnState.getSelectedItem().toString();

        if(binding.rbMale.isChecked())
            gender = binding.rbFemale.getText().toString();
        else if(binding.rbFemale.isChecked())
            gender = binding.rbFemale.getText().toString();

        student = new Student(fullname,studNo,email,gender,birth, state);

        students.add(student);
        adapter.notifyItemInserted(students.size());

        studentSQLite.fnInsertStudent(student);

    }*/

    private void fnAddToREST(View view){

        String strURL = "http://192.168.1.14/RESTAPI/rest_api.php";
        RequestQueue requestQueue = Volley.newRequestQueue(StudentMainActivity.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, strURL, new
                Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //JSONObject jsonObject = null;
                        Log.e("TAG", "RESPONSE IS " + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(getApplicationContext(), "Respond from server: " +
                                    jsonObject.getString("respond"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(StudentMainActivity.this, "Fail to get response = " + error,
                        Toast.LENGTH_SHORT).show();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String fullname = binding.edtFullName.getText().toString();
                String studNo = binding.edtStudNum.getText().toString();
                String email = binding.edtEmail.getText().toString();
                String birth = binding.edtBirthdate.getText().toString();
                String gender = "";
                String state = binding.spnState.getSelectedItem().toString();

                if(binding.rbMale.isChecked())
                    gender = binding.rbMale.getText().toString();
                else if(binding.rbFemale.isChecked())
                    gender = binding.rbFemale.getText().toString();

                Map<String,String> params = new HashMap<String,String>();
                params.put("selectFn", "fnSaveData");
                params.put("stud_name", fullname);
                params.put("stud_no", studNo);
                params.put("stud_email", email);
                params.put("stud_dob", birth);
                params.put("stud_gender", gender);
                params.put("stud_state", state);
                return params;


            }
        };
        requestQueue.add(stringRequest);
        //fnAdd(view);
    }

}