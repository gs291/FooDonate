package com.example.juliettecoia.essai_carte;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;


/**
 * Created by Legol on 8/3/2016.
 */
public class BackgroundTask extends AsyncTask<String, Void, String>
{
    public static Account logged_in_user;
    Context ctx;
    Activity activity;
    ProgressDialog progressDialog;
    AlertDialog.Builder builder;

    String register_url = "http://foodonation.co.nf/register.php";
    String login_url = "http://foodonation.co.nf/login.php";

    public BackgroundTask(Context ctx) {
        this.ctx = ctx;
        activity = (Activity)ctx;
    }

    @Override
    protected void onPreExecute() {
        builder = new AlertDialog.Builder(activity);

        progressDialog = new ProgressDialog(ctx);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Connecting to server...");
        progressDialog.setIndeterminate(true);
        progressDialog.setCancelable(false);
        progressDialog.show();


    }


    @Override
    protected String doInBackground(String... params) {

        String method = params[0];

        String name, org, address, phone, email, pass, acceptsDon;


        if(method.equals("register")) {

            try {

                URL url = new URL(register_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST");

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                name =params[1];
                org = params[2];
                address = params[3];
                phone = params[4];
                email = params[5];
                pass = params[6];
                acceptsDon = params[7];



                String  data = URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name, "UTF-8")+"&"+
                        URLEncoder.encode("org","UTF-8")+"="+URLEncoder.encode(org, "UTF-8")+"&"+
                        URLEncoder.encode("address","UTF-8")+"="+URLEncoder.encode(address, "UTF-8")+"&"+
                        URLEncoder.encode("phone","UTF-8")+"="+URLEncoder.encode(phone, "UTF-8")+"&"+
                        URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email, "UTF-8")+"&"+
                        URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(pass, "UTF-8")+"&"+
                        URLEncoder.encode("AcceptsDonations","UTF-8")+"="+URLEncoder.encode(acceptsDon, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream =  httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";

                while((line=bufferedReader.readLine())!= null){

                    stringBuilder.append(line+"\n");
                }

               httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (MalformedURLException e){
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        else if(method.equals("login"))
        {
            try {
                URL url = new URL(login_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();

                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setRequestMethod("POST");

                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

                email = params[1];
                pass = params[2];

                String  data = URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email, "UTF-8")+"&"+
                        URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(pass, "UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream =  httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line = "";

                while((line=bufferedReader.readLine())!= null){

                    stringBuilder.append(line+"\n");
                }

                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {

        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String json) {

        try{
            progressDialog.dismiss();
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("server_response");
            JSONObject JO = jsonArray.getJSONObject(0);
            String code = JO.getString("code");
            String message = JO.getString("message");

            if(code.equals("reg_true")){
                String name = JO.getString("name");
                String org = JO.getString("org");
                String address = JO.getString("address");
                String phone = JO.getString("phone");
                String email = JO.getString("email");
                String accptDon = JO.getString("accptDon");
                String food = JO.getString("food");
                String verified = JO.getString("verified");

                boolean ifVerified;
                if (accptDon.equals("true"))
                {
                    ifVerified = true;
                }
                else
                {
                    ifVerified = false;
                }
                logged_in_user = new Account(name, org, address, phone, email, ifVerified, food, verified);

                showDialog("Registration Success", message, code);

            }
            else if(code.equals("reg_false")){
                showDialog("Registration Failed", message, code);
            }
            else if (code.equals("login_true"))
            {
                String name = JO.getString("name");
                String org = JO.getString("org");
                String address = JO.getString("address");
                String phone = JO.getString("phone");
                String email = JO.getString("email");
                String accptDon = JO.getString("accptDon");
                String food = JO.getString("food");
                String verified = JO.getString("verified");

                boolean ifVerified;
                if (accptDon.equals("true"))
                {
                    ifVerified = true;
                }
                else
                {
                    ifVerified = false;
                }
                logged_in_user = new Account(name, org, address, phone, email, ifVerified, food, verified);


                Intent intent = new Intent(activity, MenuConfirmActivity.class);
                intent.putExtra("message", message);
                activity.startActivity(intent);
            }
            else if (code.equals("login_false"))
            {
                showDialog("Login Error", message, code);
            }
        }
        catch (JSONException e){
            e.printStackTrace();
        }
    }


    public void showDialog(String title, String message, String code){
        builder.setTitle(title);
        if(code.equals("reg_true")|| code.equals("reg_false")){
            builder.setMessage(message);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    activity.finish();
                }
            });
        }
        else if (code.equals("login_false"))
        {
            builder.setMessage(message);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    EditText email, password;
                    email = (EditText)activity.findViewById(R.id.editEmail);
                    password = (EditText)activity.findViewById(R.id.editPass);

                    email.setText("");
                    password.setText("");

                    dialog.dismiss();
                }
            });


        }
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
