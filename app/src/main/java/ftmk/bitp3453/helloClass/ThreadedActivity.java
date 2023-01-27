package ftmk.bitp3453.helloClass;

import static ftmk.bitp3453.helloClass.R.id.imgVwProfile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import ftmk.bitp3453.helloClass.sqlite.StudentSQLite;


public class ThreadedActivity extends AppCompatActivity {

    Executor executor;
    Handler handler;
    Bitmap bitmap = null;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private NavigationView navigationView;

    private StudentSQLite studentSQLite;

    ImageView imgVwPic;
    TextView tvGreet;
    Button btnAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threaded_main);

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

        imgVwPic = findViewById(R.id.imgVwProfile);
        Intent intent = getIntent();

        String strMsg = intent.getStringExtra("varStr1");
        tvGreet = findViewById(R.id.tvGreet);
        tvGreet.setText("Welcome to Second Activity " + strMsg);

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


        btnAsyncTask = findViewById(R.id.btnAsyncTask);
        btnAsyncTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                    try {
                        URL ImageURL = new URL("https://ftmk.utem.edu.my/web/wp-content/uploads/2020/02/cropped-Logo-FTMK.png");
                        HttpURLConnection connection = (HttpURLConnection) ImageURL.openConnection();
                        connection.setDoInput(true);
                        connection.connect();
                        InputStream inputStream = connection.getInputStream();
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inPreferredConfig = Bitmap.Config.RGB_565;
                        bitmap = BitmapFactory.decodeStream(inputStream,null,options);

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {  // this is to update main thread -- UI
                        @Override
                        public void run() {
                            imgVwPic.setImageBitmap(bitmap);
                        }
                    });
                }
            });
        }
        else
        {
            Toast.makeText(getApplicationContext(), "No Network!! Please add data plan or " +
                    "connect to wifi network!", Toast.LENGTH_SHORT).show();
        }
            }
        });



        /*executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    URL ImageURL = new URL("https://ftmk.utem.edu.my/web/wp-content/uploads/2020/02/cropped-Logo-FTMK.png");
                    HttpURLConnection connection = (HttpURLConnection) ImageURL.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream inputStream = connection.getInputStream();
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inPreferredConfig = Bitmap.Config.RGB_565;
                    bitmap = BitmapFactory.decodeStream(inputStream,null,options);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                handler.post(new Runnable() {  // this is to update main thread -- UI
                    @Override
                    public void run() {

                        ImageView imgVwProfile = (ImageView) findViewById(R.id.imageView);
                        imgVwProfile.setImageBitmap(bitmap);
                    }
                });
            }
        });

        });*/
    }

    /*private void Url(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }*/


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void fnTakePic(View vw) {
        Runnable run = new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        tvGreet.setText(tvGreet.getText().toString() + ".. This is your Picture heheh..");
                    }
                });
            }
        };
        Thread thr = new Thread(run);
        thr.start();
    }

    //Get Image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        Bitmap bp = (Bitmap) data.getExtras().get("data");
        imgVwPic.setImageBitmap(bp);

        Intent intent = new Intent();
        intent.putExtra("BitmapImage", bp);
        setResult(RESULT_OK, intent);
        //finish();
    }

    /*public void fnThreadBack(View view) {
        Intent intent2 = new Intent(this, FirstActivity.class);

        BitmapDrawable drawable = (BitmapDrawable) imgVwPic.getDrawable();
        Bitmap img = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        img.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();


        intent2.putExtra("img", byteArray);
        startActivity(intent2);
    }*/

}