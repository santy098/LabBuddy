package com.example.labbuddy;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.os.Handler;
import android.os.Message;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class NavigationActivity extends AppCompatActivity {
    DatabaseConnSqlite dbt;
    String feedback, ordered_by, phone, uid, uidtype;
    private AppBarConfiguration mAppBarConfiguration;
    public static Handler h;
    ConnectionClass conn = new ConnectionClass();

    public Connection db_connect() {
        Connection con = null;
        try {
            con = conn.CONN();
            if (con == null) {
                Toast.makeText(getApplicationContext(), "Check yout internet connection!!",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
        return con;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(NavigationActivity.this, FeedBackActivity.class);
        startActivity(intent);
        setContentView(R.layout.activity_feed_back);
        finish();
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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        /*View headerview = navigationView.getHeaderView(0);
        TextView navusername=(TextView) headerview.findViewById(R.id.clinic);
        TextView navuid=(TextView) headerview.findViewById(R.id.uniqueid);*/
        dbt = new DatabaseConnSqlite(this);
        //mydb = new DatabaseConnSqlite(this);
        Cursor rs1 = dbt.getData();
        if (rs1.moveToFirst()) {
            feedback = rs1.getString(rs1.getColumnIndex(rs1.getColumnName(4)));
            ordered_by = rs1.getString(rs1.getColumnIndex(rs1.getColumnName(1)));
            phone = rs1.getString(rs1.getColumnIndex(rs1.getColumnName(3)));
            uid = rs1.getString(rs1.getColumnIndex(rs1.getColumnName(7)));
            uidtype = rs1.getString(rs1.getColumnIndex(rs1.getColumnName(8)));
        }
        //navusername.setText(""+ordered_by);
        //navuid.setText(""+uidtype+":"+uid);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_order, R.id.nav_refer, R.id.nav_track,
                R.id.nav_support, R.id.nav_logout)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        LoginActivity.h.sendEmptyMessage(0);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Connection con = db_connect();
        try {
            String query = "SELECT TestsOrdered,Serial FROM LabBuddy_Orders WHERE OrderedBy='" + ordered_by + "' AND Status = '5'";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            if (rs.next()) {
                Intent intent = new Intent(NavigationActivity.this, FeedBackActivity.class);
                startActivity(intent);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}