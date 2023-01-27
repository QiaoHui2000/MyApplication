package ftmk.bitp3453.helloClass;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.HashMap;
import java.util.Map;

import ftmk.bitp3453.helloClass.databinding.ActivityStudentMainBinding;
import ftmk.bitp3453.helloClass.sqlite.StudentSQLite;

public class SearchStudentActivity extends AppCompatActivity {

    private ActivityStudentMainBinding binding;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;
    private StudentSQLite studentSQLite;


    Button btnSearch;
    TextView txtVwStudName2, txtVwStudGender, txtVwStudNo, txtVwStudState;
    EditText edtStudID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_student);
        //setContentView(binding.getRoot());
        studentSQLite = new StudentSQLite(this);

        btnSearch = (Button) findViewById(R.id.btnSearch);
        txtVwStudName2 = (TextView) findViewById(R.id.txtVwStudName2);
        txtVwStudGender = (TextView) findViewById(R.id.txtVwStudGender);
        txtVwStudNo = (TextView) findViewById(R.id.txtVwStudNo);
        txtVwStudState = (TextView) findViewById(R.id.txtVwStudState);
        edtStudID = (EditText) findViewById(R.id.edtStudID);

        btnSearch.setOnClickListener(this:: fnSearch);

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

    private void fnSearch(View view){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String strURL = "http://192.168.0.10/RESTAPI/rest_api.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, strURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Toast.makeText(getApplicationContext(), "Getting some respond here",
                                    Toast.LENGTH_SHORT).show();
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                txtVwStudName2.setText(jsonObject.getString("stud_name"));
                                txtVwStudGender.setText(jsonObject.getString("stud_gender"));
                                txtVwStudNo.setText(jsonObject.getString("stud_no"));
                                txtVwStudState.setText(jsonObject.getString("stud_state"));

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Unable to fetch student info",
                        Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String strStudNo = edtStudID.getText().toString();
                Map<String,String> params = new HashMap<String, String>();
                params.put("selectFn", "fnSearchStud");
                params.put("stud_no", strStudNo);

                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}