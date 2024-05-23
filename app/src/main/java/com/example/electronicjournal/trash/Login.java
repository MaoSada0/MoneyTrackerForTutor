package com.example.electronicjournal.trash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.example.electronicjournal.Main.MainActivity;
import com.example.electronicjournal.R;

public class Login extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginEnterButton;


    static final String LOGINSTRING = "login";
    static final String PASSWORDSTRING = "password";
    private String LOGIN = "qq";
    private String PASSWORD = "123";


    static SharedPreferences pref;
    static SharedPreferences.Editor edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        pref = ActivityLauncher.pref;
        edit = pref.edit();


        usernameEditText = findViewById(R.id.login);
        passwordEditText = findViewById(R.id.passwrod);
        loginEnterButton = findViewById(R.id.loginEnter);



        loginEnterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(usernameEditText.getText().toString().isEmpty() || passwordEditText.getText().toString().isEmpty())){
                    String lg = usernameEditText.getText().toString();
                    String pswrd = passwordEditText.getText().toString();

                    boolean flag = true;

                    if("123".equals(lg) && "123".equals(pswrd)){
                        edit.putString(LOGINSTRING, lg);
                        edit.putString(PASSWORDSTRING, pswrd);
                        edit.commit();
                        flag = false;
                        Intent intent = new Intent(Login.this, MainActivity.class);startActivity(intent);

                    }

                    if(flag){
                        WrongPasswordToast();
                    }

                }
            }
        });

    }

    private void WrongPasswordToast(){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout, (ViewGroup) findViewById(R.id.toast_wrong));

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);

        toast.setView(layout);
        toast.show();
    }

}