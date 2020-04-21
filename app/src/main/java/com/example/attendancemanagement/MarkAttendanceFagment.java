package com.example.attendancemanagement;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MarkAttendanceFagment extends Fragment {
    private TextView classheld,classattended,percentage;
    private DatabaseReference myReference;
    private Integer attendedCount,percent,days,noofsundays,noofdays;
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;
    private Date date,startDate;
    private String useremail1,sid;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_present,
                container, false);


        Toast.makeText(getActivity(),"mark attendance",Toast.LENGTH_SHORT).show();

        classheld=(TextView)view.getRootView().findViewById(R.id.classes_held);
        classattended=(TextView)view.getRootView().findViewById(R.id.classes_attended);
        percentage=(TextView)view.getRootView().findViewById(R.id.percentage);
        date=new Date();
        calendar=Calendar.getInstance();
        calendar.set(2020,03,15);
        startDate=calendar.getTime();

        days=(int)daysBetween(date,startDate);
//        Toast.makeText(getActivity(), ""+date, Toast.LENGTH_SHORT).show();
        noofsundays=days/7;
         noofdays=days-noofsundays;


        myReference=FirebaseDatabase.getInstance().getReference("user");
       useremail1= FirebaseAuth.getInstance().getCurrentUser().getEmail();
       sid=useremail1.split("@")[0];
//        Toast.makeText(getActivity(),sid,Toast.LENGTH_SHORT).show();
        myReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                if(dataSnapshot1.hasChild(sid)){

                    attendedCount=Integer.parseInt(dataSnapshot1.child(sid).child("count").getValue().toString());
//                    Toast.makeText(getActivity(),""+attendedCount,Toast.LENGTH_SHORT).show();
                    percent=(int)Math.round(((float)attendedCount/noofdays)*100);
                    classattended.setText(Integer.toString(attendedCount));
                    classheld.setText(Integer.toString(noofdays));
                    percentage.setText(Integer.toString(percent));
                }
                else{
                    attendedCount=0;
                    percent=Math.round(((float)attendedCount/noofdays)*100);
                    classattended.setText(Integer.toString(attendedCount));
                    classheld.setText(Integer.toString(noofdays));
                    percentage.setText(Integer.toString(percent));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

//        Toast.makeText(getActivity(),""+noofdays,Toast.LENGTH_SHORT).show();






        return view;

    }

    public long daysBetween(Date today,Date beginningDate){
        long difference=(today.getTime()-beginningDate.getTime())/86400000;
        return Math.abs(difference);
    }
}
