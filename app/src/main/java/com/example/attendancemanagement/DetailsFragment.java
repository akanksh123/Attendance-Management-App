package com.example.attendancemanagement;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.Date;

public class DetailsFragment extends Fragment {
    private DatabaseReference myReferences;
    private Date date,startDate;
    private Calendar calendar;
    private Integer days,noofsundays,noofdays,percent;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_details,container,false);

        myReferences= FirebaseDatabase.getInstance().getReference("user");

        date=new Date();
        calendar= Calendar.getInstance();
        calendar.set(2020,03,15);
        startDate=calendar.getTime();

        days=(int)daysBetween(date,startDate);
        noofsundays=days/7;
        noofdays=days-noofsundays;
        TableLayout stk = (TableLayout)view.getRootView().findViewById(R.id.table_teacher);
        TableRow tbrow = new TableRow(getActivity());
        TextView tv0 = new TextView(getActivity());
        tv0.setText("USN  ");
        tv0.setTextColor(Color.WHITE);
        tv0.setTextSize(15);
        tv0.setPadding(0,0,0,20);
        tbrow.addView(tv0);
        TextView tv1 = new TextView(getActivity());
        tv1.setText("Classes Held ");
        tv1.setTextColor(Color.WHITE);
        tv1.setTextSize(15);
        tv1.setPadding(0,0,0,20);
        tbrow.addView(tv1);
        TextView tv2 = new TextView(getActivity());
        tv2.setText(" Classes Attended ");
        tv2.setTextColor(Color.WHITE);
        tv2.setTextSize(15);
        tv2.setPadding(0,0,0,20);
        tbrow.addView(tv2);
        TextView tv3 = new TextView(getActivity());
        tv3.setText(" Percentage");
        tv3.setTextColor(Color.WHITE);
        tv3.setTextSize(15);
        tv3.setPadding(0,0,0,20);
        tbrow.addView(tv3);
        stk.addView(tbrow);

        myReferences.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                if(dataSnapshot2.exists()){
                    for(DataSnapshot datas:dataSnapshot2.getChildren()){

                String usn=datas.getKey().toString();

                Count attendanceCount=datas.getValue(Count.class);
                Integer noofAttendance=Integer.parseInt(attendanceCount.getCount());
                percent=Math.round(((float)noofAttendance/noofdays)*100);

            TableRow tbrow = new TableRow(getActivity());

            TextView t1v = new TextView(getActivity());
            t1v.setText(usn);
            t1v.setTextColor(Color.WHITE);
            t1v.setGravity(Gravity.CENTER);

            tbrow.addView(t1v);
            TextView t2v = new TextView(getActivity());
            t2v.setText(""+noofdays);
            t2v.setTextColor(Color.WHITE);
            t2v.setGravity(Gravity.CENTER);
            tbrow.addView(t2v);
            TextView t3v = new TextView(getActivity());
            t3v.setText("" + noofAttendance);
            t3v.setTextColor(Color.WHITE);
            t3v.setGravity(Gravity.CENTER);
            tbrow.addView(t3v);
            TextView t4v = new TextView(getActivity());
            t4v.setText("" + percent);
            t4v.setTextColor(Color.WHITE);
            t4v.setGravity(Gravity.CENTER);
            tbrow.addView(t4v);
            stk.addView(tbrow);


                    }
                }
                else{
                    Toast.makeText(getActivity(),"Error fetching data",Toast.LENGTH_SHORT).show();
                }
            }
//
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
    public long daysBetween(Date today,Date beginningDate){
        long difference=(today.getTime()-beginningDate.getTime())/86400000;
        return Math.abs(difference);
    }
}
