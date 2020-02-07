package com.example.labbuddy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class FeedBackActivity extends AppCompatActivity {
    ConnectionClass conn = new ConnectionClass();
    ConnectionClass connectionClass=new ConnectionClass();
    int itemsInCart=0;
    String[] serialID;
    DatabaseConnSqlite mydb;
    public Connection db_connect(){
        Connection con=null;
        try {
            con = conn.CONN();
            if (con == null) {
                Toast.makeText(getApplicationContext(), "Check yout internet connection!!",
                        Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception ex){
            Toast.makeText(getApplicationContext(), ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
        return con;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);

    String ordered_by="";
        CardView cardview;
        TextView tests;
        TextView serial;
        CardView.LayoutParams layoutparams;
        RatingBar rb;

        DatabaseConnSqlite dbt = new DatabaseConnSqlite(this);
        //mydb = new DatabaseConnSqlite(this);
        Cursor rs1 = dbt.getData();
        if(rs1.moveToFirst()) {
            ordered_by = rs1.getString(rs1.getColumnIndex(rs1.getColumnName(1)));
        }

        Connection con=db_connect();
        try {
            //Get all test names and get all test prices present in MenuPrices and store them in test_prices as key value pairs
            ArrayList<String> TO = new ArrayList<>();
            final ArrayList<String> SE = new ArrayList<>();

            String query = "SELECT TestsOrdered,Serial FROM LabBuddy_Orders WHERE OrderedBy='"+ordered_by+"' AND Status = '5'";
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
            TO.add(rs.getString("TestsOrdered"));
            SE.add(rs.getString("Serial"));
            }
            itemsInCart = TO.size();

            //Toast.makeText(FeedBackActivity.this,TO.get(0),Toast.LENGTH_LONG).show();
            LinearLayout relativeLayout = findViewById(R.id.mainLayout);
            for(int i = 0;i<TO.size();i++){
                String s = "";
                //Toast.makeText(FeedBackActivity.this,Integer.toString(TO.size()),Toast.LENGTH_LONG).show();

                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


                CardView.LayoutParams clp = new CardView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                clp.setMargins(0,0,0,30);

                LinearLayout row = new LinearLayout(this);
                LinearLayout.LayoutParams tlp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                row.setGravity(Gravity.CENTER);
                row.setOrientation(LinearLayout.HORIZONTAL);
                row.setLayoutParams(tlp);

                LinearLayout table = new LinearLayout(this);
                LinearLayout.LayoutParams tlp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                table.setOrientation(LinearLayout.VERTICAL);
                table.setLayoutParams(tlp1);
                table.setGravity(Gravity.CENTER);


                cardview = new CardView(this);
                cardview.setLayoutParams(clp);

                serial = new TextView(this);
                serial.setLayoutParams(lp);
                serial.setText(SE.get(i)+" : ");
                serial.setTypeface(null, Typeface.BOLD);
                serial.setTextSize(20);

                row.addView(serial);

                tests = new TextView(this);
                tests.setLayoutParams(lp);
                tests.setText(TO.get(i)+"skdjvlkjsdbflsdfjhslkdjf sdf hs udfhsdh siud ks dv halsda hfl kjsadhg lkjsjadh opad sfg kjads hgk sadhg lksd");
                tests.setTextSize(15);

                row.addView(tests);

                table.addView(row);

                rb = new RatingBar(this);
                s = SE.get(i);
                ActionBar.LayoutParams rlp = new ActionBar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                rb.setLayoutParams(rlp);
                rb.setId(Integer.parseInt(s));
                rb.setStepSize(1);
                rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                    @Override
                    public void onRatingChanged(RatingBar ratingBar, float r, boolean fromUser) {

                        String rating = Float.toString(r);
                        try {
                            Connection con = connectionClass.CONN();
                            if (con == null) {
                                Toast.makeText(getApplicationContext(), "Check your internet connection",
                                        Toast.LENGTH_LONG).show();
                            } else {
                                String query = "UPDATE LabBuddy_Orders SET Rating='" + rating + "' WHERE Serial='"+ratingBar.getId()+"'";
                                Statement stmt = con.createStatement();
                                stmt.executeUpdate(query);
                                itemsInCart--;
                                if(itemsInCart==0){
                                    Intent intent = new Intent(FeedBackActivity.this,NavigationActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                CardView c =(CardView) ratingBar.getParent().getParent();
                                c.removeAllViews();
                                Toast.makeText(FeedBackActivity.this, "" + ratingBar.getId(), Toast.LENGTH_LONG).show();
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                                                });

                table.addView(rb);
                cardview.addView(table);
                relativeLayout.addView(cardview);

            }

            } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    }

