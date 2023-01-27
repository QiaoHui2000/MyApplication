package ftmk.workshop2.labtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText edtUsername,edtPass;
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPass = (EditText) findViewById(R.id.edtPass);
        btnLogin = (Button) findViewById(R.id.btnLogin);

        if(edtUsername.getText().toString().trim().length() <= 0 | edtPass.getText().toString().trim().length() <= 0){
            Toast.makeText(LoginActivity.this, "Please fill in all fields",
                    Toast.LENGTH_SHORT).show();
        }
        /*if(edtPass.getText().toString().trim().length() <= 0){
            Toast.makeText(LoginActivity.this, "Please enter your password.",
                    Toast.LENGTH_SHORT).show();
        }*/

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                intent.putExtra("name",edtUsername.getText().toString());
                startActivity(intent);

            }
        });


    }

    /*public void fnGreet (View vw){
        String strUsername = edtUsername.getText().toString();
        Toast.makeText(this,"Welcome"+strUsername, Toast.LENGTH_LONG).show();

    }*/

}