package com.example.juliettecoia.essai_carte;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by juliettecoia on 7/28/16.
 */
public class LoginActivity extends Activity {

    Button Login;
    EditText USERNAME, USERPASS;
    String username, userpass;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        Login = (Button) findViewById(R.id.btnLogin);

        USERNAME = (EditText) findViewById(R.id.editEmail);
        USERPASS = (EditText) findViewById(R.id.editPass);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                username = USERNAME.getText().toString();
                userpass = USERPASS.getText().toString();

                BackgroundTask backgroundTask = new BackgroundTask(LoginActivity.this);
                backgroundTask.execute("login", username, userpass);
                nextPage(v);
            }
        });
    }


    public void nextPage(View view)
    {
        Intent intent = new Intent(this, MenuConfirmActivity.class);
        startActivity(intent);
    }

}
