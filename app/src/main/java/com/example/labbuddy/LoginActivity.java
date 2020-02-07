package com.example.labbuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
    ConnectionClass connectionClass;
    EditText edtuserid,edtpass;
    Button btnlogin;
    ProgressBar pbbar;
    DatabaseConnSqlite mydb;
    String nam,pas,phone,uid,uidtype,role;
    public static Handler h;
    FirebaseAuth auth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    String verification_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        ActivityCompat.requestPermissions(LoginActivity.this, new String[]{android.Manifest.permission.RECEIVE_SMS}, 1);

        h = new Handler() {
            public void handleMessage(Message message) {
                super.handleMessage(message);
                switch (message.what) {
                    case 0:
                        finish();
                        break;
                }
            }
        };
        mydb = new DatabaseConnSqlite(this);
        Cursor rs = mydb.getData();
        if (rs.moveToFirst()) {
            pbbar =  findViewById(R.id.pbbar);
            pbbar.setVisibility(View.GONE);
            nam = rs.getString(rs.getColumnIndex(rs.getColumnName(1)));
            pas = rs.getString(rs.getColumnIndex(rs.getColumnName(2)));
            Intent change = new Intent(LoginActivity.this, NavigationActivity.class);
            change.putExtra("KEY", nam);
            startActivity(change);
            rs.close();
        }
        else {
            connectionClass = new ConnectionClass();
            edtuserid = findViewById(R.id.uname);
            edtpass = findViewById(R.id.smsverify);
            pbbar = findViewById(R.id.pbbar);
            pbbar.setVisibility(View.GONE);

            edtuserid.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(final CharSequence charSequence, int i, int i1, int i2) {
                    if (charSequence.length() == 9)
                    {
                        //Toast.makeText(LoginActivity.this,""+charSequence.length(),Toast.LENGTH_SHORT).show();
                        new AlertDialog.Builder(LoginActivity.this)
                                .setTitle("Confirm PhNo")
                                .setMessage("Have you entered the correct number?")
                                .setCancelable(false)
                                .setPositiveButton("Yes",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                Toast.makeText(LoginActivity.this,charSequence,Toast.LENGTH_SHORT).show();
                                                //send_sms();
                                                try{
                                                    Connection con = connectionClass.CONN();
                                                    String query="SELECT * FROM LabBuddy_Login_Table WHERE [Phone Number]='"+charSequence+"'";
                                                    Statement statement=con.createStatement();
                                                    ResultSet rs=statement.executeQuery(query);
                                                    while(rs.next()){
                                                        phone=rs.getString("Phone Number");
                                                        uid=rs.getString("UID");
                                                        uidtype=rs.getString("UID Type");
                                                        role=rs.getString("Role");
                                                    }

                                                }
                                                //rec();
                                                catch (SQLException sqlex){
                                                    Toast.makeText(LoginActivity.this,"Phone number not registered",Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(LoginActivity.this,"no",Toast.LENGTH_SHORT).show();
                                    }
                                })
                                .show();

                    }
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });



            auth = FirebaseAuth.getInstance();
            mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                }

                @Override
                public void onVerificationFailed(@NonNull FirebaseException e) {

                }

                @Override
                public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    verification_code = s;
                    Toast.makeText(getApplicationContext(),"Code sent to number",Toast.LENGTH_LONG).show();

                }
            };

        }

    }

    public void signInWithPhone(PhoneAuthCredential credential){
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Sign in Successfull",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void verify(View v){
        String input_code = edtpass.getText().toString();
        verifyPhoneNumber(verification_code,input_code);
    }

    public void verifyPhoneNumber(String verifyCode,String inputCode){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verifyCode, inputCode);
        signInWithPhone(credential);
    }
    public class DoLogin extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;

        String userid = edtuserid.getText().toString();
        String password = edtpass.getText().toString();

        @Override
        protected void onPreExecute() {
            pbbar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r) {
            pbbar.setVisibility(View.GONE);

            if(isSuccess) {
                //Toast.makeText(LoginActivity.this,r,Toast.LENGTH_SHORT).show();
                boolean isInserted = mydb.insertData("1",userid,password,phone,"0","null","null",uid,uidtype,role);
                Intent change = new Intent(LoginActivity.this,NavigationActivity.class);
                change.putExtra("KEY",userid);
                startActivity(change);
            }
            else{
                Toast.makeText(LoginActivity.this,r,Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected String doInBackground(String... params) {
            if(userid.trim().equals("")|| password.trim().equals(""))
                z = "Please enter User Id and Password";
            else
            {
                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        z = "Check your internet connection";
                    } else {
                        String query = "select * from LabBuddy_Login_Table where [Entity Name]='" + userid + "' and Password='" + password + "'";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);

                        if(rs.next())
                        {

                            z = "Login successful";
                            isSuccess=true;
                            phone=rs.getString("Phone Number");
                            uid=rs.getString("UID");
                            uidtype=rs.getString("UID Type");
                            role=rs.getString("Role");

                        }
                        else
                        {
                            z = "Invalid Credentials";
                            isSuccess = false;
                        }

                    }
                }
                catch (Exception ex)
                {
                    isSuccess = false;
                    z = "Exceptions"+ex;
                }
            }
            return z;
        }
    }
}

