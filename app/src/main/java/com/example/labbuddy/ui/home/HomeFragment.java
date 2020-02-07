package com.example.labbuddy.ui.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.labbuddy.BuildConfig;
import com.example.labbuddy.ConnectionClass;
import com.example.labbuddy.DatabaseConnSqlite;
import com.example.labbuddy.MainActivity;
import com.example.labbuddy.NavigationActivity;
import com.example.labbuddy.R;
import com.example.labbuddy.ordersplash;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class HomeFragment extends Fragment {
    String[] tests = {
            "1st Trimester Down Syndrome Screening Double Marker",
            "Anti Cyclic Citrullinated Peptide (Anti Ccp)",
            "Anti Thyro Peroxidase Antibody (Tpo)",
            "C Reactive Protein (Crp)   Quantitative",
            "Ca 125",
            "Creatinine",
            "Electrolytes (Na  K  Cl)",
            "Estradiol   Oestrogen   E2",
            "Ferritin",
            "Folic Acid   Folate   Serum",
            "Follicle Stimulating Hormone (Fsh)",
            "Free T3  Free T4 & Tsh   Free Tft",
            "Fsh  Lh & Prolactin",
            "Insulin Fasting",
            "Leutinising Hormone (Lh)",
            "Lipid Profile",
            "Liver Function Test",
            "Para Thyroid Hormone (Pth)",
            "Prolactin",
            "Quadruple Marker",
            "Testosterone   Total",
            "Thyroid Profile  Tft( T3  T4  Tsh)",
            "Thyroid Stimulating Hormone  Tsh",
            "Total Prostate Specific Antigen   Psa",
            "Triple Test Markers  Second Trimester",
            "Vitamin B12 (Cyanocobalamin)",
            "Vitamin D (25   Hydroxy Cholecalciferol)",
            "Absolute Eosinophil Count (Aec)",
            "Absolute Neutrophil Count",
            "Acid Phosphatase   Total",
            "Acid Phosphatase   Total & Prostatic Fraction",
            "Adenosine Deaminase (Ada)   Serum",
            "Afb 24 Hrs Sputum",
            "Afb   24 Hrs Urine",
            "Afb   Urine",
            "Afb Culture   Conventional Method",
            "Alanine Aminotransferase (Alt Sgpt)",
            "Albumin",
            "Albumin   Ascitic Fluid",
            "Albumin   Protein   Urine",
            "Alcohol   Blood (Qualitative)",
            "Alcohol   Urine (Qualitative)",
            "Alfa Feto Protein Afp",
            "Alkaline Phosphatase (Alp   Sap)",
            "Ammonia",
            "Amphetamine (Doa)   Urine",
            "Amylase   Serum",
            "Amylase Urine Spot Sample",
            "Ana Profile",
            "Anaerobic Culture",
            "Anca Mpo   P Anca",
            "Anca Pr3   C Anca",
            "Angiotensin Converting Enzyme (Ace)",
            "Anti Cardiolipin Antibody (Acl)   Igg",
            "Anti Cardiolipin Antibody (Acl)   Igm",
            "Anti Dsdna   (Elisa)",
            "Anti Microsomal (Ama)   Anti Thyro Peroxidase (Tpo)",
            "Anti Mullerian Hormone (Amh)",
            "Anti Neutrophil Cytoplasmic Antibody (Anca)",
            "Anti Nuclear Antibodies (Ana)  Elisa",
            "Anti Phospholipid Antibody (Apa)   Igg",
            "Anti Phospholipid Antibody (Apa)   Igm",
            "Anti Streptolysin O (Aso) Titre   Quantitative",
            "Anti Thyroglobulin Antibody (Anti Tg Ab)",
            "Anti Thyroid Antibodies (Ama  Atg)",
            "Antibody To H.Pylori   Total",
            "Antibody To Hepatitis B Surface Antigen   (Anti Hbsag)",
            "Ascitic   Peritoneal Fluid Analysis",
            "Ascitic   Peritoneal Fluid For Culture & Sensitivity   Aerobic",
            "Aspartate Aminotransferase (Ast Sgot)",
            "Aspirated Fluid For Culture & Sensitivity   Aerobic",
            "Barbiturate (Doa)   Urine",
            "Bence Jones Protein   Urine",
            "Benzodiazepine (Doa)   Urine",
            "Beta Human Chorionic Gonadotrophin   Beta Hcg",
            "Bicarbonate Hco3",
            "Bilirubin (Total  Direct & Id)",
            "Biopsy (Extra Large Specimen)",
            "Biopsy (Large Sized Specimen)",
            "Biopsy (Medium Sized Specimen)",
            "Biopsy (Oncology Specimen)",
            "Biopsy (Small Specimen)",
            "Blood Culture & Sensitivity(Enteric & Nonenteric)",
            "Blood Group & Rh Type",
            "Blood Urea Nitrogen (Bun)",
            "Body Fluid For Culture & Sensitivity   Aerobic",
            "Bone Marrow Reporting",
            "Bone Specimen   Medium",
            "Bone Specimen   Small",
            "Bronchial Washing For Culture & Sensitivity   Aerobic",
            "Brucella Agglutinins Test",
            "B Type Natriuretic Peptide   Bnp",
            "C Peptide",
            "Ca 15 3",
            "Ca 19 9",
            "Calcium",
            "Calcium   24 Hrs Urine",
            "Calcium  Urine   Spot Sample",
            "Calcium Creatinine Ratio   24 Hrs Urine",
            "Calcium Creatinine Ratio   Spot Sample   Urine",
            "Carbamazepine   Tegretol (Tdm)",
            "Cardiac Enzymes (Sgot Ck Ckmb Trop I (Qualitative) Ldh)",
            "Catheter Tip For Culture & Sensitivity   Aerobic",
            "Cea (Carcino Embryonic Antigen)",
            "Cerebrospinal Fluid (Csf) Analysis",
            "Cerebrospinal Fluid (Csf) For Culture & Sensitivity   Aerobic",
            "Chikungunya   Igm Antibody (Qualitative)",
            "Chloride",
            "Chloride   Cerebrospinal Fluid (Csf)",
            "Chloride  Urine   Spot Sample",
            "Cholesterol   Body Fluid",
            "Cholesterol Total",
            "Cholinesterase (Pseudocholinesterase)",
            "Cocaine (Doa)   Urine",
            "Complete Blood Count With Esr",
            "Complete Haemogram",
            "Conjunctival Swab For Culture & Sensitivity   Aerobic",
            "Cortisol   4 Pm",
            "Cortisol   8 Am",
            "Cortisol   Random",
            "Creatinine   24 Hrs Urine",
            "Creatinine   Body Fluid",
            "Creatinine Clearance   24 Hrs Urine",
            "Creatinine Clearance   6 Hrs Urine",
            "Creatinine Kinase   Mb   Creatinine Phosphokinase Mb (Ck Mb)",
            "Creatinine Kinase   Creatinine Phosphokinase (Ck Cpk)",
            "Creatinine Urine   Spot Sample",
            "Cytology",
            "Cytomegalo Virus (Cmv) Antibody   Igg",
            "Cytomegalo Virus (Cmv) Antibody   Igm",
            "D Dimer",
            "Dengue Profile (Igg  Igm & Ns1)   Rapid",
            "Dengue Test Camp",
            "Dhea Sulphate",
            "Direct Coombs Test (Dct)",
            "Drain Tip For Culture & Sensitivity   Aerobic",
            "Ear Swab For Culture & Sensitivity   Aerobic",
            "Electrolytes   24 Hrs Urine",
            "Electrolytes   Urine (Spot Sample)",
            "Electrophoresis   Protein",
            "Erythrocyte Sedimentation Rate   Esr",
            "Estriol Unconjugated   E3",
            "Et Tip For Culture & Sensitivity   Aerobic",
            "Eye Swab For Culture & Sensitivity   Aerobic",
            "Fibrin Degradation Product (Fdp) D Dimer",
            "Fluid For Culture & Sensitivity   Aerobic",
            "Fnac   Multiple Sites",
            "Fnac   Single Site",
            "Free Beta Human Chorionic Gonadotrophin (Free B Hcg)",
            "Free Prostatic Specific Antigen (Free & Total)",
            "Free T3 (Ft3)",
            "Free T4 (Ft4)",
            "Fungal Culture",
            "G6pd   Glucose 6 Phosphate Dehydrogenase",
            "Gamma Glutamyl Transferase (G Gt)",
            "Glucose   Fasting",
            "Glucose   Fasting   Blood & Urine",
            "Glucose   Post Dinner",
            "Glucose   Post Lunch",
            "Glucose   Post Prandial (Pp)   Blood & Urine",
            "Glucose   Postprandial",
            "Glucose   Postprandial With Oral Glucose",
            "Glucose   Pre Dinner",
            "Glucose   Pre Lunch",
            "Glucose   Random",
            "Glucose   Random Blood & Urine",
            "Glucose   Synovial Fluid",
            "Glucose Challenge Test (Gct)",
            "Glycosylated Haemoglobin (Hba1c)",
            "Gram S Stain",
            "Haemoglobin Electrophoresis   Hb Electrophoresis",
            "Haemoglobin Hb",
            "Hb  Tc  Dc & Esr",
            "Hb Tc & Dc",
            "Hdl Cholesterol",
            "Hepatitis A Virus Antibody   Igm  Anti Hav Igm",
            "Hepatitis B Core Antibody   Igm (Hbcab   Igm)",
            "Hepatitis B E Antibody   (Anti Hbeag)",
            "Hepatitis B E Antigen   (Hbeag)",
            "Hepatitis B Surface Antigen (Hbsag)   Elisa",
            "Hepatitis B Surface Antigen (Hbsag)   Chemiluminiscence",
            "Hepatitis B Surface Antigen (Hbsag)   Spot   Rapid Test",
            "Hepatitis C Virus Antibody   Igg Total (Anti Hcv)   Elisa",
            "Hepatitis C Virus Antibody (Anti Hcv)   Rapid",
            "Hepatitis C Virus Antibody (Anti Hcv)   Chemiluminiscence",
            "Hepatitis E Virus Antibody   Igm (Anti Hev Igm)",
            "Herpes Simplex Virus (Hsv) 1 & 2 Antibodies Igg",
            "Herpes Simplex Virus (Hsv) 1 & 2 Antibodies   Igm",
            "Herpes Simplex Virus (Hsv) 1 Antibodies   Igg & Igm",
            "Herpes Simplex Virus (Hsv) 2 Antibodies   Igg & Igm",
            "High Sensitive C   Reactive Protein (Hscrp) Quantitative",
            "Hiv 1 & 2 Elisa",
            "Hiv 1 & 2   Chemiluminiscence",
            "Hiv  Spot Rapid Test",
            "Homocysteine Level",
            "Human Growth Hormone  Gh",
            "Immunoglobulin E   Total",
            "India Ink Preparation   Stain For Cryptococcus   Csf",
            "Indirect Coombs Test (Ict)",
            "Inhibin A",
            "Insulin   Postprandial",
            "Insulin Random",
            "Insulin (Oral Glucose   120 Min)",
            "Insulin (Oral Glucose   30 Min)",
            "Insulin (Oral Glucose   60 Min)",
            "Insulin (Oral Glucose   90 Min)",
            "Ionised Calcium",
            "Iron",
            "Iron & Tibc",
            "Iron Profile",
            "Lactate",
            "Lactate Dehydrogenase (Ldh)",
            "Ldl   Cholesterol",
            "Le (Lupus Erythematosus) Cells",
            "Leptospira Antibodies   Igg & Igm   Rapid",
            "Lipase",
            "Lipase",
            "Lipoprotein (A)",
            "Lupus Anticoagulant",
            "Magnesium",
            "Magnesium   24 Hrs Urine",
            "Magnesium  Urine   Spot Sample",
            "Malaria Test Camp",
            "Mean Corpuscular Haemoglobin   Mch",
            "Mean Corpuscular Haemoglobin Concentration   Mchc",
            "Mean Corpuscular Volume Mcv",
            "Microalbumin   Creatinine Ratio  Urine   Spot Sample",
            "Microalbumin  Urine   Spot Sample",
            "Microalbuminuria   24 Hrs Urine",
            "Nail Clipping For Fungus",
            "Nasal Swab For Culture Sensitivity   Aerobic",
            "Nicotine",
            "Nipple Discharge From Both Breasts (R & L)   Cytology",
            "Nipple Discharge From One Breast   Cytology",
            "Opiates (Morphine) (Doa)",
            "Osmolality   Blood",
            "Osmolality   Urine",
            "Packed Cell Volume  Pcv   Hct",
            "Pap Smear By Conventional Method",
            "Partial Thromboplastin Time (Ptt)   Aptt",
            "Peripheral Smear Study   Ps   Pbs",
            "Phenobarbitone",
            "Phenytoin (Eptoin)",
            "Phosphorous   24 Hrs Urine",
            "Phosphorus",
            "Phosphorus  Urine   Spot Sample",
            "Platelet Count  Plt Count",
            "Pleural Fluid Analysis",
            "Pleural Fluid For Culture & Sensitivity   Aerobic",
            "Potassium   24 Hrs Urine",
            "Potassium (K&)",
            "Potassium  Urine   Spot Sample",
            "Progesterone",
            "Protein (Albumin)   24 Hrs Urine",
            "Protein   Creatinine Ratio   24 Hrs Urine",
            "Protein   Creatinine Ratio   Urine   Spot Sample",
            "Protein Estimation  Urine   Spot Sample",
            "Protein Total (Total Protein)",
            "Prothrombin Time   Pt",
            "Pus For Culture & Sensitivity   Aerobic",
            "Quantiferon Tb Gold   Gamma Interferon For Tb",
            "Rapid Malaria Test   Malarial Antigen Test",
            "Rbc Count",
            "Red Cell Indices (Mch  Mchc & Mcv)",
            "Reticulocyte Count",
            "Rheumatoid Arthritis   Ra Factor   Quantitative",
            "Rubella Igg Antibody",
            "Rubella Igm Antibody",
            "Semen For Culture & Sensitivity   Aerobic",
            "Serum Proteins",
            "Serum Transferrin",
            "Sickle Cell Preparation",
            "Skin Scrapings For Fungus (Multiple Sites)",
            "Skin Scrapings For Fungus (Single Site)",
            "Slide Review (For Second Opinion)",
            "Smear For C.Diphtheriae   Klb ( Diptheria)",
            "Smear For Malarial Parasite (Mp)",
            "Smear For Microfilaria (Mf)",
            "Sodium   24 Hours Urine",
            "Sodium (Na&)",
            "Sodium  Urine   Spot Sample",
            "Sputum For Afb",
            "Sputum For Culture And Sensitivity   Aerobic",
            "Stool Analysis   Complete",
            "Stool For Culture & Sensitivity  Aerobic",
            "Stool For Microscopy",
            "Stool For Occult Blood",
            "Stool For Reducing Substances",
            "Suction Tip For Culture & Sensitivity Aerobic",
            "Surveillance Test   Ot Swabs   Anaerobic Culture",
            "Surveillance Test   Ot Swabs   Aerobic Culture",
            "Swab For Culture & Sensitivity   Aerobic",
            "Synovial Fluid For Culture & Sensitivity   Aerobic",
            "Tc & Dc",
            "Testosterone Panel   Androgen Panel",
            "Thc   Tetrahydrocannabinol (Cannabis Marijuana) (Doa)",
            "Throat Swab For Culture & Senstivity   Aerobic",
            "Thyro 5 (T3  T4 Tsh  Free T3 & Free T4)",
            "Thyroglobulin (Tg) Level",
            "Thyroxine   T4",
            "Tissue For Culture & Sensitivity  Aerobic",
            "Tissue Transglutaminase (Ttg) Iga",
            "Tissue Transglutaminase (Ttg)   Igg",
            "Torch Profile   Igg",
            "Torch Profile   Igg & Igm",
            "Torch Profile   Igm",
            "Total Iron Binding Capacity (Tibc)",
            "Total Wbc Count Tc",
            "Toxoplasma   Igg Antibody",
            "Toxoplasma   Igm Antibody",
            "Tracheal Tip For Culture & Sensitivity   Aerobic",
            "Tracheostomy Aspirate For Culture & Sensitivity   Aerobic",
            "Transferrin Saturation",
            "Triglycerides (Tgl)",
            "Triiodothyronine   T3",
            "Troponin I (Qualitative)",
            "Troponin T (Qualitative)",
            "Typhidot   Igg (Qualitative)",
            "Typhidot   Igm (Qualitative)",
            "Urea",
            "Urea   24 Hrs Urine",
            "Urea  Urine   Spot Sample",
            "Urethral Swab For Culture & Sensitivity Aerobic",
            "Uric Acid",
            "Uric Acid   24 Hrs Urine",
            "Urine Complete Analysis",
            "Urine For Bile Salt & Bile Pigment",
            "Urine For Culture & Sensitivity  Aerobic",
            "Urine For Drugs Level (Upto 6 Drugs)",
            "Urine For Drugs Level (Upto 9 Drugs)",
            "Urine For Fungus",
            "Urine For Microscopy Deposit",
            "Urine For Pregnancy Test   Ug",
            "Urine For Reducing Substances",
            "Urine Glucose   Fasting",
            "Urine Glucose   Post Dinner",
            "Urine Glucose   Post Lunch",
    };
    /*Toast.makeText(getView().getContext(), "Hi",
                        Toast.LENGTH_LONG).show();*/
    Button btnLogout;
    TextView tv,t;
    DatabaseConnSqlite dbt;
    AutoCompleteTextView acTextView; //the search bar
    ArrayList<String> CartArray = new ArrayList<String>(); //Stores all test names added to cart
    TableLayout ordersTable; //table which shows cart
    int flag_table_created=0,flag_ele=0; //flag_table->checks is table exists flag_ele->Checks is selected test from search is already added
    HashMap<String,String> test_prices=new HashMap<>(); //Maintains test_name and test_price as key value pairs
    ConnectionClass conn=new ConnectionClass(); //for database connection
    int rows_so_far=0; //No. of rows in table
    int total_test_price=0; //stores sum of test price
    Button btn_total_test_price;
    String total_test_price_txt,phone;
    public static Handler h;
    DatabaseConnSqlite mydb;
    String feedback,ordered_by;
    String version,vers,currentversion,newversion;
    View root;


    //function for creating an obejct for connecting to server
    public Connection db_connect(){
        Connection con=null;
        try {
            con = conn.CONN();
            if (con == null) {
                Toast.makeText(getView().getContext(), "Check yout internet connection!!",
                        Toast.LENGTH_LONG).show();
            }
        }
        catch (Exception ex){
            Toast.makeText(getView().getContext(), ex.getMessage(),
                    Toast.LENGTH_LONG).show();
        }
        return con;
    }
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        root = inflater.inflate(R.layout.fragment_home, container, false);
        //final TextView textView = root.findViewById(R.id.clinic);
        dbt = new DatabaseConnSqlite(getActivity());
        //mydb = new DatabaseConnSqlite(this);
        Cursor rs1 = dbt.getData();
        if (rs1.moveToFirst()) {
            feedback = rs1.getString(rs1.getColumnIndex(rs1.getColumnName(4)));
            ordered_by = rs1.getString(rs1.getColumnIndex(rs1.getColumnName(1)));
            phone = rs1.getString(rs1.getColumnIndex(rs1.getColumnName(3)));
        }
        //feedback="0";
        /*if (feedback.equals("1")) {
            Intent change1 = new Intent(LBCActivity.this, orderplaced.class);
            //change1.putExtra("KEY", nam);
            startActivity(change1);
        } else */
        {

            // Create a calendar instance
            Calendar calendar = Calendar.getInstance();

            // Set time of execution. Here, we have to run every day 4:20 PM; so,
            // setting all parameters.
            calendar.set(Calendar.HOUR, 10);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.AM_PM, Calendar.PM);

            Long currentTime = new Date().getTime();
            Calendar calendar1 = Calendar.getInstance();

            // Set time of execution. Here, we have to run every day 4:20 PM; so,
            // setting all parameters.
            calendar1.set(Calendar.HOUR, 9);
            calendar1.set(Calendar.MINUTE, 0);
            calendar1.set(Calendar.SECOND, 0);
            calendar1.set(Calendar.AM_PM, Calendar.AM);

            // Check if current time is greater than our calendar's time. If So,
            // then change date to one day plus. As the time already pass for
            // execution.
            if (currentTime > calendar1.getTime().getTime()) {
                if (calendar.getTime().getTime() > currentTime) {
                    //Toast.makeText(getView().getContext(), "inside if", Toast.LENGTH_LONG).show();
                    //calendar.add(Calendar.DATE, 1);
                    Connection con = db_connect();
                    try {
                        String query = "SELECT * FROM LabBuddy_version";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        while (rs.next()) {
                            //Toast.makeText(getView().getContext(), "inside while", Toast.LENGTH_LONG).show();
                            newversion = rs.getString("version");
                        }
                        currentversion = Integer.toString(BuildConfig.VERSION_CODE);
                        //String versinname = BuildConfig.VERSION_NAME;
                        //mydb = new DatabaseConnSqlite(this);
                        dbt.updatedata1(currentversion);
                        dbt.updatedata2(newversion);
                    } catch (Exception ex) {
                    }
                }
            }
            //Toast.makeText(LBCActivity.this,""+versincode+"",Toast.LENGTH_SHORT).show();
            //Toast.makeText(LBCActivity.this,versinname,Toast.LENGTH_SHORT).show();
            Cursor rs2 = dbt.getData();
            if (rs2.moveToFirst()) {
                vers = rs2.getString(rs2.getColumnIndex(rs2.getColumnName(5)));
                version = rs2.getString(rs2.getColumnIndex(rs2.getColumnName(6)));
                //Toast.makeText(LBCActivity.this,vers,Toast.LENGTH_SHORT).show();
                //Toast.makeText(LBCActivity.this,version,Toast.LENGTH_SHORT).show();
            }
            t = (TextView) root.findViewById(R.id.version);
            if (version.equals(vers)) {
                t.setText("");
            } else {
                t.setText("Please update the application!");
                t.setTextSize(25);
                t.setBackgroundColor(Color.RED);
                t.setTextColor(Color.WHITE);
                //t.setTextColor(getResources().getColor(R.color.version));
            }
            // Calendar is scheduled for future; so, it's time is higher than
            // current time.
    /*long startScheduler = calendar.getTime().getTime() - currentTime;

    // Setting stop scheduler at 4:21 PM. Over here, we are using current
    // calendar's object; so, date and AM_PM is not needed to set
    calendar.set(Calendar.HOUR, 5);
    calendar.set(Calendar.MINUTE, 0);
    calendar.set(Calendar.AM_PM, Calendar.PM);

    // Calculation stop scheduler
    long stopScheduler = calendar.getTime().getTime() - currentTime;

    // Executor is Runnable. The code which you want to run periodically.
    Runnable task = new Runnable() {

        @Override
        public void run() {

            Toast.makeText(getView().getContext(), "inside run", Toast.LENGTH_LONG).show();
        }
    };

    // Get an instance of scheduler
    final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    // execute scheduler at fixed time.
    scheduler.scheduleAtFixedRate(task, startScheduler, stopScheduler, MILLISECONDS);/*
   /* ZonedDateTime now = ZonedDateTime.now(ZoneId.of(ZoneId.systemDefault().getId()));
    ZonedDateTime nextRun = now.withHour(14).withMinute(55).withSecond(0);
    if(now.compareTo(nextRun) > 0)
        Toast.makeText(getView().getContext(), "inside if", Toast.LENGTH_LONG).show();
        nextRun = nextRun.plusDays(1);

    Duration duration = Duration.between(now, nextRun);
    long initalDelay = duration.getSeconds();

    ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    scheduler.scheduleAtFixedRate( new Runnable() {
                                       public void run() {
                                           Toast.makeText(getView().getContext(), "inside run", Toast.LENGTH_LONG).show();
                                           try {
                                               Toast.makeText(getView().getContext(), "inside try", Toast.LENGTH_LONG).show();
                                       //        getDataFromDatabase1();
                                           }catch(Exception ex) {
                                               ex.printStackTrace(); //or loggger would be better
                                           }
                                       }
    }, 0, 15, TimeUnit.SECONDS);*/
                                       /*private void getDataFromDatabase1() {
                                           /*Connection con=db_connect();
                                           try {
                                               String query = "SELECT * FROM Version";
                                               Statement stmt = con.createStatement();
                                               ResultSet rs = stmt.executeQuery(query);
                                               while (rs.next()) {
                                                   ver=rs.getString("Version");
                                               }
                                           }
                                           catch(Exception ex){}*/
                                          /* Toast.makeText(getView().getContext(), "inside get data from database", Toast.LENGTH_LONG).show();
                                           t = (TextView) findViewById(R.id.version);
                                           t.setText("Hello");
                                           t.setTextSize(20);
                                           t.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                                       }*/

           /* initalDelay,
            TimeUnit.DAYS.toSeconds(3000),
            TimeUnit.SECONDS);*/

            //onClickButtonLogout();
           /* h = new Handler() {
                public void handleMessage(Message message) {
                    super.handleMessage(message);
                    switch (message.what) {
                        case 0:
                            finish();
                            break;
                    }
                }
            };*/

            Connection con = db_connect();
            try {
                //Get all test names and get all test prices present in MenuPrices and store them in test_prices as key value pairs
                ArrayList<String> NT = new ArrayList<>();
                ArrayList<String> GP = new ArrayList<>();
                String query = "SELECT NameOfTest,GeneralPrice FROM MenuPrices";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    NT.add(rs.getString("NameOfTest"));
                    GP.add(rs.getString("GeneralPrice"));
                }
                for (int i = 0; i < NT.size(); i++) {
                    test_prices.put(NT.get(i), GP.get(i));
                }
            } catch (Exception ex) {
            }
            /*Autocomplete*/
            acTextView = (AutoCompleteTextView) root.findViewById(R.id.searchview);
            ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, tests);
            acTextView.setThreshold(1);
            acTextView.setAdapter(adapter);

            //to select element from dropdown on clicking
            acTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    String search_txt = acTextView.getText().toString();
                    //Check is element exists in table and set flag_ele to avoid multiple rows
                    for (int j = 0; j < CartArray.size(); j++) {
                        if (acTextView.getText().toString().equals(CartArray.get(j))) {
                            flag_ele = 1;
                        }
                    }
                    if (flag_ele == 1) {
                        Toast.makeText(getView().getContext(), "Test already present in cart",
                                Toast.LENGTH_LONG).show();
                /*for(int k=0;k<=rows_so_far;k++){
                    TableRow tr=(TableRow) ordersTable.getChildAt(k);
                    TextView test_name_txt=(TextView) tr.getChildAt(0);

                    //View test_name=tr.getChildAt(0);
                    if(test_name_txt.getText().toString().equals(search_txt)){
                        ViewGroup vg=(ViewGroup) ((TableRow) ordersTable.getChildAt(k)).getChildAt(1);
                        TextView txt_qty=(TextView) vg.getChildAt(1);
                        int add_quantity_str=Integer.parseInt(txt_qty.getText().toString());
                        add_quantity_str+=1;
                        txt_qty.setText(Integer.toString(add_quantity_str));
                        total_test_price+=Integer.parseInt(test_prices.get(acTextView.getText().toString()));
                    }
                }*/
                        flag_ele = 0;
                    } else {
                        CartArray.add(search_txt);
                        modalSubmit();
                    }
                    acTextView.getText().clear(); //Clear search bar so that next search can be done
                }
            });
            btn_total_test_price = (Button) root.findViewById(R.id.btn_order);
            //To place order in database
            btn_total_test_price.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String btn_order_txt = btn_total_test_price.getText().toString();
                    if (btn_order_txt.equals("Order : Rs. 0(0)")) {
                        Toast.makeText(getView().getContext(), "Cart is empty",
                                Toast.LENGTH_LONG).show();
                    } else {
                        mydb = new DatabaseConnSqlite(getView().getContext());
                        feedback = "1";
                        mydb.updatedata(feedback);
                        int amt_payable = total_test_price;
                        Date util_date = new Date();
                        Timestamp date_time_db = new Timestamp(util_date.getTime());//for getting in yyyy-mm-dd hh:mm:ss.SSS format
                        String test_names_db = "";
                        String test_price_db = "";
                        String EntityA = "Medall";
                        String StatusA = "1";

                        //store all tests as comma separated values
                        for (int i = 0; i < CartArray.size() - 1; i++) {
                            test_names_db += CartArray.get(i) + ",";
                            test_price_db += test_prices.get(CartArray.get(i)) + ",";

                        }
                        test_names_db += CartArray.get(CartArray.size() - 1);
                        test_price_db += test_prices.get(CartArray.get(CartArray.size() - 1));
                        Connection con = db_connect();
                        try {
                            if (con == null) {
                                String z = "Check your internet connection";
                                Toast.makeText(getView().getContext(), z,
                                        Toast.LENGTH_LONG).show();
                            } else {
                                String query = "INSERT INTO LabBuddy_Orders (DateTime,TestsOrdered,TestPrices,AmountPayable,Entity,Status,OrderedBy) VALUES ('" + date_time_db + "','" + test_names_db + "','" + test_price_db + "','" + amt_payable + "','" + EntityA + "','" + StatusA + "','" + ordered_by + "')";
                                Statement stmt = con.createStatement();
                                stmt.executeUpdate(query);
                                // Toast.makeText(getView().getContext(), "Order placed!!",
                                //        Toast.LENGTH_LONG).show();
                                //send_sms();
                                CartArray.clear();
                                flag_ele = 0;
                                flag_table_created = 0;
                                rows_so_far = 0;
                                total_test_price = 0;
                                //total_test_price_txt = "Rs. " + total_test_price + "(" + CartArray.size() + ")";
                                total_test_price_txt = "Order : Rs. 0(0)";
                                Intent change = new Intent(getActivity(), ordersplash.class);
                                startActivity(change);
                                btn_total_test_price = root.findViewById(R.id.btn_order);
                                btn_total_test_price.setText(total_test_price_txt);
                                ordersTable.removeAllViews();
                            }
                        } catch (Exception ex) {
                            Toast.makeText(getView().getContext(), ex.getMessage(),
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
            return root;
        }
    }
        /*homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/



    //Create table
    public void modalSubmit(){
        ordersTable=(TableLayout)root.findViewById(R.id.orderTable);
        TableRow.LayoutParams lp=new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT);

        //for first time
        if(flag_table_created==0){
            TableRow th=new TableRow(getView().getContext());
            th.setLayoutParams(lp);

            TextView name_header = new TextView(getView().getContext());
            TextView quantity_header = new TextView(getView().getContext());
            TextView price_header = new TextView(getView().getContext());
            TextView line = new TextView(getView().getContext());

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,4);
            params.setMargins(0,20,0,20);
            line.setLayoutParams(params);
            line.setBackgroundColor(Color.BLACK);


            name_header.setText("Test Name");
            name_header.setTypeface(null, Typeface.BOLD);
            quantity_header.setText(" "+"Quantity");
            quantity_header.setTypeface(null, Typeface.BOLD);
            price_header.setText(" "+"Price");
            price_header.setTypeface(null, Typeface.BOLD);

            name_header.setGravity(Gravity.CENTER);
            quantity_header.setGravity(Gravity.CENTER);
            price_header.setGravity(Gravity.CENTER);

            th.addView(name_header);
            th.addView(quantity_header);
            th.addView(price_header);
            ordersTable.addView(th);
            ordersTable.addView(line);
            total_test_price=0;
            for(int i=rows_so_far;i<CartArray.size();i++){
                TableRow tr=new TableRow(getView().getContext());
                tr.setLayoutParams(lp);
                TextView test_name=new TextView(getView().getContext());
                test_name.setText(CartArray.get(i));
                test_name.setGravity(Gravity.CENTER);
                test_name.setHeight(100);
                TextView test_price=new TextView(getView().getContext());
                Button plus=new Button(getView().getContext());
                TextView quantity=new TextView(getView().getContext());
                Button minus=new Button(getView().getContext());
                plus.setText("+");
                plus.setBackgroundColor(Color.TRANSPARENT);
                plus.setId(i);
                plus.setLayoutParams(new LinearLayout.LayoutParams(200,100));
                plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ViewParent row=view.getParent().getParent();
                        add_item(row);
                    }
                });
                minus.setId(i*100);
                minus.setText("-");
                minus.setLayoutParams(new LinearLayout.LayoutParams(200,100));
                minus.setBackgroundColor(Color.TRANSPARENT);
                minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ViewParent row=view.getParent().getParent();
                        delete_item(row);
                    }
                });
                quantity.setText("1");
                quantity.setId(i*200);
                LinearLayout tr_qty=new LinearLayout(getView().getContext());

                tr_qty.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
                tr_qty.setOrientation(LinearLayout.HORIZONTAL);
                tr_qty.setLayoutParams(lp);

                tr_qty.addView(minus);
                tr_qty.addView(quantity);
                tr_qty.addView(plus);
                tr_qty.setGravity(Gravity.CENTER);
                test_price.setText(test_prices.get(CartArray.get(i)));
                test_price.setGravity(Gravity.CENTER);
                tr.addView(test_name);
                tr.addView(tr_qty);
                tr.addView(test_price);
                ordersTable.addView(tr);
                rows_so_far++;
                total_test_price+=Integer.parseInt(test_price.getText().toString());
                total_test_price_txt="Order : Rs. "+total_test_price+"("+CartArray.size()+")";
                btn_total_test_price=root.findViewById(R.id.btn_order);
                btn_total_test_price.setText(total_test_price_txt);

            }
            if(rows_so_far==0)flag_table_created=0;
            else flag_table_created=1;
        }
        //if table exists continue from existing no. of rows
        else{
            for(int i=rows_so_far;i<CartArray.size();i++){
                TableRow tr=new TableRow(getView().getContext());
                tr.setLayoutParams(lp);
                TextView test_name=new TextView(getView().getContext());
                test_name.setText(CartArray.get(i));
                test_name.setGravity(Gravity.CENTER);
                test_name.setHeight(100);
                TextView test_price=new TextView(getView().getContext());
                Button plus=new Button(getView().getContext());
                TextView quantity=new TextView(getView().getContext());
                Button minus=new Button(getView().getContext());
                plus.setText("+");
                plus.setBackgroundColor(Color.TRANSPARENT);
                plus.setId(i);
                plus.setLayoutParams(new LinearLayout.LayoutParams(200,100));
                plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ViewParent row=view.getParent().getParent();
                        add_item(row);
                    }
                });
                minus.setId(i*100);
                minus.setText("-");
                minus.setBackgroundColor(Color.TRANSPARENT);
                minus.setLayoutParams(new LinearLayout.LayoutParams(200,100));
                minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ViewParent row=view.getParent().getParent();
                        delete_item(row);
                    }
                });
                quantity.setText("1");
                quantity.setId(i*200);
                LinearLayout tr_qty=new LinearLayout(getView().getContext());
                tr_qty.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                tr_qty.setOrientation(LinearLayout.HORIZONTAL);
                tr_qty.setLayoutParams(lp);
                tr_qty.addView(minus);
                tr_qty.addView(quantity);
                tr_qty.addView(plus);
                tr_qty.setGravity(Gravity.CENTER);
                test_price.setText(test_prices.get(CartArray.get(i)));
                test_price.setGravity(Gravity.CENTER);
                tr.addView(test_name);
                tr.addView(tr_qty);
                tr.addView(test_price);
                ordersTable.addView(tr);
                rows_so_far++;
                total_test_price+=Integer.parseInt(test_price.getText().toString());
                total_test_price_txt="Order : Rs. "+total_test_price+"("+CartArray.size()+")";
                btn_total_test_price=root.findViewById(R.id.btn_order);
                btn_total_test_price.setText(total_test_price_txt);
            }
            if (rows_so_far == 0) flag_table_created = 0;
        }
    }
    //Increase quantity when user presses +
    public void add_item(ViewParent row_vp){
        ViewGroup row=(ViewGroup)row_vp;
        TextView add_test=(TextView)row.getChildAt(0);
        String add_test_str=add_test.getText().toString();
        CartArray.add(add_test_str);
        rows_so_far++;
        LinearLayout add_btn_grp=(LinearLayout) row.getChildAt(1);
        TextView add_quantity=(TextView)add_btn_grp.getChildAt(1);
        int add_quantity_str=Integer.parseInt(add_quantity.getText().toString());
        add_quantity_str+=1;
        add_quantity.setText(Integer.toString(add_quantity_str));
        TextView add_price=(TextView) row.getChildAt(2);
        total_test_price+=Integer.parseInt(add_price.getText().toString());
        total_test_price_txt="Order : Rs. "+total_test_price+"("+CartArray.size()+")";
        btn_total_test_price=root.findViewById(R.id.btn_order);
        btn_total_test_price.setText(total_test_price_txt);
    }
    //Decrease quantity when user clicks -
    public void delete_item(ViewParent row_vp) {
        ViewGroup row = (ViewGroup) row_vp;
        TextView minus_price = (TextView) row.getChildAt(2);
        LinearLayout qty_grp_del = (LinearLayout) row.getChildAt(1);
        TextView minus_qty = (TextView) qty_grp_del.getChildAt(1);
        int minus_qty_int = Integer.parseInt(minus_qty.getText().toString());
        if (minus_qty_int > 1) {
            TextView minus_test = (TextView) row.getChildAt(0);
            String minus_test_str = minus_test.getText().toString();
            LinearLayout minus_btn_grp = (LinearLayout) row.getChildAt(1);
            TextView minus_quantity = (TextView) minus_btn_grp.getChildAt(1);
            CartArray.remove(minus_test_str);
            rows_so_far--;
            int minus_quantity_str = Integer.parseInt(minus_quantity.getText().toString());
            minus_quantity_str -= 1;
            minus_quantity.setText(Integer.toString(minus_quantity_str));
            total_test_price -= Integer.parseInt(minus_price.getText().toString());
            total_test_price_txt = "Order : Rs. " + total_test_price + "(" + CartArray.size() + ")";
            btn_total_test_price = root.findViewById(R.id.btn_order);
            btn_total_test_price.setText(total_test_price_txt);
        }
        //If quantity is zero then delete that row
        else {
            TextView minus_test = (TextView) row.getChildAt(0);
            String minus_test_str = minus_test.getText().toString();
            CartArray.remove(minus_test_str);
            rows_so_far--;
            TableRow del_row = (TableRow) row;
            del_row.removeAllViews();
            total_test_price -= Integer.parseInt(minus_price.getText().toString());
            total_test_price_txt = "Order : Rs. " + total_test_price + "(" + CartArray.size() + ")";
            btn_total_test_price = root.findViewById(R.id.btn_order);
            btn_total_test_price.setText(total_test_price_txt);
        }
    }

    //To logout the user

    public void send_sms(){
        Connection con = db_connect();
        ArrayList<Integer> OD = new ArrayList<>();
        String sms = "";
        try {
            if (con == null) {
                String z = "Check your internet connection";
                Toast.makeText(getView().getContext(), z,
                        Toast.LENGTH_LONG).show();
            } else {
                String query = "SELECT TOP 1 Serial FROM LabBuddy_Orders ORDER BY Serial DESC";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                String query1 = "SELECT sms FROM LabBuddy_version";
                Statement stmt1 = con.createStatement();
                ResultSet rs1 = stmt1.executeQuery(query1);
                while(rs1.next()){
                    sms = rs1.getString("sms");
                }


                //Toast.makeText(LBCActivity.this,"hello  111",Toast.LENGTH_LONG).show();
                while (rs.next()) {

                    OD.add(rs.getInt("Serial"));
                    //  Toast.makeText(LBCActivity.this,"hello",Toast.LENGTH_LONG).show();

                }

            }
        }
        catch(Exception ex){}


        sms=sms.replace("#",Integer.toString(OD.get(0)));
        sms=sms.replace("@",Integer.toString(CartArray.size()));
        sms=sms.replace("$",ordered_by);
        sms=sms.replace("*",Integer.toString(total_test_price));
        //Toast.makeText(LBCActivity.this,sms,Toast.LENGTH_LONG).show();
        //OLD SMS CODE

        //Your authentication key
        String authkey = "304588ASufGEs80eD5dd39c76";
        //Multiple mobiles numbers separated by comma
        String mobiles = ""+phone;
        //Toast.makeText(LBCActivity.this,mobiles,Toast.LENGTH_LONG).show();
        //Sender ID,While using route4 sender id should be 6 characters long.
        String senderId = "FNDEED";
        //Your message to send, Add URL encoding here.
        String message =sms;
        //define route
        String route="4";

        //Prepare Url
        URLConnection myURLConnection=null;
        URL myURL=null;
        BufferedReader reader=null;

        //encoding message
        String encoded_message= null;

        encoded_message = URLEncoder.encode(message);


        //Send SMS APIhttps://api.msg91.com/api/sendhttp.php?route=4&sender=FNDEED
        String mainUrl="http://api.msg91.com/api/sendhttp.php?";

        //Prepare parameter string
        StringBuilder sbPostData= new StringBuilder(mainUrl);
        sbPostData.append("authkey="+authkey);
        sbPostData.append("&mobiles="+mobiles);
        sbPostData.append("&message="+encoded_message);
        sbPostData.append("&route="+route);
        sbPostData.append("&sender="+senderId);

        //final string
        mainUrl = sbPostData.toString();

        try
        {

            myURL = new URL(mainUrl);
            myURLConnection = myURL.openConnection();
            myURLConnection.connect();
            reader= new BufferedReader(new InputStreamReader(myURLConnection.getInputStream()));

            //reading response
            String response;
            while ((response = reader.readLine()) != null)
                //print response
                Log.d("RESPONSE", ""+response);

            //finally close connection
            reader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        //OLD SMS CODE END
    }
    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        MainActivity.h.sendEmptyMessage(0);
        finish();
    }*/
}