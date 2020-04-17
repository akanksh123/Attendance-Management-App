package com.example.attendancemanagement;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MarkAttendanceFagment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_present,
                container, false);

        Toast.makeText(getActivity(),"mark attendance",Toast.LENGTH_SHORT).show();
//        Bundle args=getArguments();
//        final String value=args.getString("dates");
//
//        TableLayout stk = (TableLayout)view.getRootView().findViewById(R.id.table_main);
//        TableRow tbrow0 = new TableRow(getActivity());
//        TextView tv0 = new TextView(getActivity());
//        tv0.setText(" Sl.No ");
//        tv0.setTextColor(Color.WHITE);
//        tbrow0.addView(tv0);
//        TextView tv1 = new TextView(getActivity());
//        tv1.setText(" Subject Name ");
//        tv1.setTextColor(Color.WHITE);
//        tbrow0.addView(tv1);
//        TextView tv2 = new TextView(getActivity());
//        tv2.setText(" Classes Held ");
//        tv2.setTextColor(Color.WHITE);
//        tbrow0.addView(tv2);
//        TextView tv3 = new TextView(getActivity());
//        tv3.setText(" Classes Attended "+value);
//        tv3.setTextColor(Color.WHITE);
//        tbrow0.addView(tv3);
//        TextView tv4 = new TextView(getActivity());
//        tv4.setText(" Percentage ");
//        tv4.setTextColor(Color.WHITE);
//        tbrow0.addView(tv4);
//        stk.addView(tbrow0);
//        for (int i = 0; i < 1; i++) {
//            TableRow tbrow = new TableRow(getActivity());
//            TextView t1v = new TextView(getActivity());
//            t1v.setText("" + i);
//            t1v.setTextColor(Color.WHITE);
//            t1v.setGravity(Gravity.CENTER);
//            tbrow.addView(t1v);
//            TextView t2v = new TextView(getActivity());
//            t2v.setText("Product " + i);
//            t2v.setTextColor(Color.WHITE);
//            t2v.setGravity(Gravity.CENTER);
//            tbrow.addView(t2v);
//            TextView t3v = new TextView(getActivity());
//            t3v.setText("Rs." + i);
//            t3v.setTextColor(Color.WHITE);
//            t3v.setGravity(Gravity.CENTER);
//            tbrow.addView(t3v);
//            TextView t4v = new TextView(getActivity());
//            t4v.setText("" + i * 15 / 32 * 10);
//            t4v.setTextColor(Color.WHITE);
//            t4v.setGravity(Gravity.CENTER);
//            tbrow.addView(t4v);
//            TextView t5v = new TextView(getActivity());
//            t5v.setText("" + i * 15 / 32 * 10);
//            t5v.setTextColor(Color.WHITE);
//            t5v.setGravity(Gravity.CENTER);
//            tbrow.addView(t5v);
//            stk.addView(tbrow);
//        }
        return view;

    }
}
