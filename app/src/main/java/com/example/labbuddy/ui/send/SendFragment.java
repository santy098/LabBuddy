package com.example.labbuddy.ui.send;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.labbuddy.DatabaseConnSqlite;
import com.example.labbuddy.LoginActivity;
import com.example.labbuddy.MainActivity;
import com.example.labbuddy.NavigationActivity;
import com.example.labbuddy.R;
import com.example.labbuddy.ui.home.HomeFragment;

public class SendFragment extends Fragment {

    private SendViewModel sendViewModel;
    DatabaseConnSqlite dbt;

    public View onCreateView(@NonNull final LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        sendViewModel =
                ViewModelProviders.of(this).get(SendViewModel.class);
        View root = inflater.inflate(R.layout.fragment_send, container, false);
        dbt = new DatabaseConnSqlite(getActivity());
                    new AlertDialog.Builder(getActivity())
                            .setTitle("Confirm Logout")
                            .setMessage("Are you sure to logout?")
                            .setCancelable(false)
                            .setPositiveButton("Yes",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            NavigationActivity.h.sendEmptyMessage(0);
                                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NO_HISTORY);
                                            startActivity(intent);
                                            Integer deletedRows = dbt.deleteData();
                                        }
                                    })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(getActivity(), NavigationActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NO_HISTORY);
                                    startActivity(intent);
                                }
                            })
                            .show();
        return root;
    }
}