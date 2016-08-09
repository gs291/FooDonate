package com.example.juliettecoia.essai_carte;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by juliettecoia on 7/28/16.
 */
public class MenuConfirmActivity extends FragmentActivity{
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_confirmation);

        textView = (TextView)findViewById(R.id.textView2);
        String message = getIntent().getStringExtra("message");
        textView.setText(message);
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
