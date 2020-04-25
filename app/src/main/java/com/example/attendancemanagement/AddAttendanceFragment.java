package com.example.attendancemanagement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

import java.util.ArrayList;

public class AddAttendanceFragment extends Fragment {
    private Button presentbtn,absentbtn;
    private TextView usntxt;
    private DatabaseReference databaseReference;
    private ArrayList<String> usnarray=new ArrayList<>();
    private String previousCount;
    private int j,t,s;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_addattendance,container,false);

        databaseReference=FirebaseDatabase.getInstance().getReference("user");

        usntxt=(TextView)view.getRootView().findViewById(R.id.text_usn);
        presentbtn=(Button)view.getRootView().findViewById(R.id.btn_present);
        absentbtn=(Button)view.getRootView().findViewById(R.id.btn_absent);



        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot3) {
                if(dataSnapshot3.exists()){
                    int i=0;
                    for(DataSnapshot studentdata: dataSnapshot3.getChildren()){

                        String usn=studentdata.getKey().toString();
                        usnarray.add(usn);
//                        usntxt.setText(usn);
                        i++;


                    }
//                    Toast.makeText(getActivity(),""+i,Toast.LENGTH_SHORT).show();
                    j=i-1;
//                    studentAttendance(j);
                }
                else{
                    Toast.makeText(getActivity(),"Error fetching data",Toast.LENGTH_SHORT).show();
                }

                try{studentAttendance(j);}
                catch(Exception e){
//                    Toast.makeText(getActivity(),""+e,Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;
    }
    public void studentAttendance(int k){
//        Toast.makeText(getActivity(),"student"+k, Toast.LENGTH_LONG).show();
        t=k;
        s=0;
        usntxt.setText(usnarray.get(s));
        try{presentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.hasChild(usnarray.get(s)))
                             previousCount=dataSnapshot.child(usnarray.get(s)).child("count").getValue().toString();
                            Count count=new Count();
                            count.setCount(Integer.toString(Integer.parseInt(previousCount)+1));
                            databaseReference.child(usnarray.get(s)).setValue(count);
                            Toast.makeText(getActivity(),"Attendance added",Toast.LENGTH_SHORT).show();
                            if(s<t){
                                s++;

                                usntxt.setText(usnarray.get(s));
                            }else{
                                getFragmentManager().beginTransaction().replace(R.id.fragment_container_teacher,new PostAttendanceFragment()).commit();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                }catch (Exception e){
                    Toast.makeText(getActivity(),""+e,Toast.LENGTH_SHORT).show();
                }
            }
        });
        absentbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(s<t){
                    s++;

                    usntxt.setText(usnarray.get(s));
                }
                else{
                    getFragmentManager().beginTransaction().replace(R.id.fragment_container_teacher,new PostAttendanceFragment()).commit();
                }
            }
        });
        }
        catch (Exception e){
            Toast.makeText(getActivity(),"button"+e,Toast.LENGTH_SHORT).show();
        }

        return ;
    }
}
