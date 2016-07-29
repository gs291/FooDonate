package com.example.juliettecoia.essai_carte;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by juliettecoia on 7/28/16.
 */
public class MainActivity extends FragmentActivity{
    Button REG;
    Context ctx = this;
    boolean user_accptdon;
    CheckBox USER_ACCPTDON;
    EditText USER_NAME, USER_ORG, USER_PHONE, USER_ADDRESS, USER_EMAIL, USER_PASS;
    String user_name, user_org, user_phone, user_address, user_email, user_pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        USER_NAME = (EditText)findViewById(R.id.edtName);
        USER_ORG = (EditText)findViewById(R.id.editEmail);
        USER_PHONE = (EditText)findViewById(R.id.edtPhone);
        USER_ADDRESS = (EditText)findViewById(R.id.edtAddress);
        USER_EMAIL = (EditText)findViewById(R.id.editPass);
        USER_PASS = (EditText)findViewById(R.id.edtPass);
        USER_ACCPTDON = (CheckBox)findViewById(R.id.chkAcceptDon);

        REG = (Button)findViewById(R.id.btnLogin);
        REG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_name = USER_NAME.getText().toString();
                user_org = USER_ORG.getText().toString();
                user_address = USER_ADDRESS.getText().toString();
                user_phone = USER_PHONE.getText().toString();
                user_email = USER_EMAIL.getText().toString();
                user_pass = USER_PASS.getText().toString();
                user_accptdon = USER_ACCPTDON.isChecked();


                boolean entries = validEntries(user_name, user_org, user_address, user_phone, user_email, user_pass);

                if(entries)
                {
                    DatabaseOperations DB = new DatabaseOperations(ctx);
                    DB.putInformation(DB,user_name, user_org, user_address, user_phone, user_email, user_pass, user_accptdon);

                    Toast.makeText(getBaseContext(), "Registration Success", Toast.LENGTH_LONG).show();

                    nextPage(v);
                }

            }
        });
    }

    private boolean validEntries(String name, String org,  String address, String phone, String email, String pass)
    {
        if(TextUtils.isEmpty(name)) {
            Toast.makeText(getBaseContext(), "------Missing Name------", Toast.LENGTH_LONG).show();
            return false;
        }
        if(TextUtils.isEmpty(org)) {
            Toast.makeText(getBaseContext(), "------Missing Organization------", Toast.LENGTH_LONG).show();
            return false;
        }
        if(TextUtils.isEmpty(address)) {
            Toast.makeText(getBaseContext(), "------Missing Address------", Toast.LENGTH_LONG).show();
            return false;
        }
        if(TextUtils.isEmpty(phone) || !isPhoneValid(phone)) {
            Toast.makeText(getBaseContext(), "------Invalid Phone Number------", Toast.LENGTH_LONG).show();
            USER_PHONE.setText("");
            return false;
        }
        if ( TextUtils.isEmpty(email) || !isEmailValid(email))
        {
            Toast.makeText(getBaseContext(), "------Invalid Email------", Toast.LENGTH_LONG).show();
            USER_EMAIL.setText("");
            return false;
        }
        if (TextUtils.isEmpty(pass) || !isPasswordValid(pass))
        {
            Toast.makeText(getBaseContext(), "------Password Too Short------" , Toast.LENGTH_LONG).show();
            USER_PASS.setText("");
            return false;
        }
        return true;
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
    private boolean isPhoneValid(String phone)
    {
        return phone.length() == 10;
    }

    public void nextPage(View view)
    {
        Intent intent = new Intent(this, MenuConfirmActivity.class);
        startActivity(intent);
    }

}
