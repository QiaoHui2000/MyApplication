package ftmk.workshop2.labtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class MainActivity extends AppCompatActivity {


    ImageView imageView1, imageView2;
    EditText edtGlazed, edtStuffed;
    TextView tvItem,tvPrice;
    Button btnCalculate;
    Executor executor;
    Handler handler;
    Bitmap bitmap = null;
    Bitmap bitmap2 = null;

    public MainActivity() throws IOException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView1 = (ImageView)findViewById(R.id.imageView1);
        imageView2 = (ImageView)findViewById(R.id.imageView2);
        btnCalculate = (Button) findViewById(R.id.btnCalculate);
        edtGlazed = (EditText) findViewById(R.id.edtGrazed);
        edtStuffed = (EditText) findViewById(R.id.edtStuffed);
        tvItem = (TextView) findViewById(R.id.tvTotalItem);
        tvPrice = (TextView) findViewById(R.id.tvTotalPrice);

        String strUsername = getIntent().getStringExtra("name");
        Toast.makeText(MainActivity.this,"Welcome " + strUsername, Toast.LENGTH_SHORT).show();

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
                        URL ImageURL = new URL("https://abmauri.com.my/wp-content/uploads/2021/06/ab_mauri_donut_recipe.jpg");
                        URL ImageURL2 = new URL("https://bazaronlinesgbuloh.my/wp-content/uploads/2021/03/bomboloni-red-velvet-rm1.00-scaled.jpg");
                        HttpURLConnection connection = (HttpURLConnection) ImageURL.openConnection();
                        HttpURLConnection connection2 = (HttpURLConnection) ImageURL2.openConnection();
                        connection.setDoInput(true);
                        connection.connect();
                        connection2.connect();
                        InputStream inputStream = connection.getInputStream();
                        InputStream inputStream2 = connection2.getInputStream();
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inPreferredConfig = Bitmap.Config.RGB_565;
                        bitmap = BitmapFactory.decodeStream(inputStream,null,options);
                        bitmap2 = BitmapFactory.decodeStream(inputStream2,null,options);


                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {  // this is to update main thread -- UI
                        @Override
                        public void run() {

                            imageView1.setImageBitmap(bitmap);
                            imageView2.setImageBitmap(bitmap2);
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

        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double result = calculatePrice();
                String output = String.format("Total Price to Pay: RM", result);
                tvPrice.setText(output);

                double result2 = calculateItem();
                String output2 = String.format("Total Item: ", result2);
                tvItem.setText(output2);
            }
        });
    }

    private double calculatePrice(){
        try {
            int glazed = Integer.parseInt(edtGlazed.getText().toString());
            int stuffed = Integer.parseInt(edtStuffed.getText().toString());

            //result
            return (3.5 * glazed) + (2.5 * stuffed);

        }catch (NumberFormatException e){
            e.printStackTrace();
            return 0;
        }
    }

    private double calculateItem(){
        try {
            int glazedItem = Integer.parseInt(edtGlazed.getText().toString());
            int stuffedItem = Integer.parseInt(edtStuffed.getText().toString());

            //result
            return glazedItem + stuffedItem;

        }catch (NumberFormatException e){
            e.printStackTrace();
            return 0;
        }
    }

}