package com.example.juliettecoia.essai_carte;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by juliettecoia on 7/28/16.
 */
public class MenuConfirmActivity extends FragmentActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_confirmation);
    }

    public void goToA3 (View view){
        String button_text;
        button_text = ((Button) view).getText().toString();
        if(button_text.equals("Donate"))
        {
            Intent intent = new Intent(this, DonateActivity.class);
            startActivity(intent);
        }
        else if (button_text.equals("map"))
        {
            Intent intent = new Intent(this, MapsActivity.class);
            startActivity(intent);
        }
    }
}
