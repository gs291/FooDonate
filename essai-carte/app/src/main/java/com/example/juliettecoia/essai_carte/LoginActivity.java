package com.example.juliettecoia.essai_carte;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by juliettecoia on 7/28/16.
 */
public class LoginActivity extends Activity {

    Button Login;
    EditText USERNAME, USERPASS;
    String username, userpass;
    Context CTX = this;



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

                Toast.makeText(getBaseContext(), "Please wait...", Toast.LENGTH_LONG).show();

                username = USERNAME.getText().toString();
                userpass = USERPASS.getText().toString();

                DatabaseOperations DOP = new DatabaseOperations(CTX);
                Cursor CR = DOP.getInformation(DOP);

                CR.moveToFirst();
                boolean loginStatus = false;
                String NAME = "";
                do {

                    if(username.equals(CR.getString(4)) && userpass.equals(CR.getString(5)))
                    {
                        loginStatus = true;
                        NAME = CR.getString(0);
                    }

                } while(CR.moveToNext());

                if(loginStatus)
                {
                    Toast.makeText(getBaseContext(), "Login Success------\n Welcome " + NAME, Toast.LENGTH_LONG).show();
                    nextPage(v);
                }
                else
                {
                    Toast.makeText(getBaseContext(), "Login Failed------" + NAME, Toast.LENGTH_LONG).show();
                    finish();
                }

            }
        });
    }

    public void nextPage(View view)
    {
        Intent intent = new Intent(this, MenuConfirmActivity.class);
        startActivity(intent);
    }

}
