package ftmk.bitp3453.helloClass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    TextView received_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        received_info = findViewById(R.id.received_info);
        Intent intent = getIntent();
        User user = (User) intent.getSerializableExtra("objUser");
        received_info.setText("\nFull Name: " + user.getStrName() + "\nPassword: "
                + user.getStrPwd() + "\nAddress: " + user.getStrAddress() + "\nEmail: " + user.getStrEmail()
                + "\nBirth Date: " + user.getStrBirth()  + "\nGender: " + user.getStrGender());

    }
    public void fnRegistrationActivity(View view){
        Intent newIntent = new Intent(this, RegistrationActivity.class);
        startActivity(newIntent);
    }


}
