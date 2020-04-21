package com.example.attendancemanagement;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class CalendarFagment extends Fragment {
//    FirebaseAuth mAuthentication;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private Button submitbtn,verifybtn;
    private Button checkAttendance;
    private CalendarView calendarView;

    private EditText verifyText;
    private Random random;
    private String randomno,id,useremail;
    private Count cnt;
    private Integer counts;
    @Nullable

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar,
                container, false);

        mFirebaseDatabase=FirebaseDatabase.getInstance();
        myRef=mFirebaseDatabase.getReference("user");
        useremail=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        id=useremail.split("@")[0];



        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        random=new Random();
        submitbtn =(Button)view.getRootView().findViewById(R.id.submit_present);
        verifyText=(EditText)view.findViewById(R.id.text_verification);
        verifybtn=(Button)view.getRootView().findViewById(R.id.verify_button);
        checkAttendance=(Button)view.getRootView().findViewById(R.id.check_attendance);


        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        verifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyOTP();


            }
        });

        checkAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getFragmentManager().beginTransaction().replace(R.id.fragment_container1,new MarkAttendanceFagment()).commit();
            }
        });




        return view;

    }

    private void sendMessage() {
        String phoneNo="+919845352320";
        randomno= Integer.toString(random.nextInt(999999)) ;


        try {

            String apiKey = "apikey=" + "tLx32oZCOU8-U4lZQ4ftGKLvRfbaVsrUw0hG3XRRVH";
            String message = "&message=" + "Your otp is"+randomno;
            String sender = "&sender=" + "TXTLCL";
            String numbers = "&numbers=" + phoneNo;

            // Send data
            HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
            String data = apiKey + numbers + message + sender;
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
            conn.getOutputStream().write(data.getBytes("UTF-8"));
            final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            final StringBuffer stringBuffer = new StringBuffer();
            String line;
            while ((line = rd.readLine()) != null) {
                stringBuffer.append(line);
            }
            rd.close();

        Toast.makeText(getActivity(),"OTP sent",Toast.LENGTH_SHORT).show();
        } catch (Exception e) {

            Toast.makeText(getActivity(),"Error sending OTP.Please try again",Toast.LENGTH_SHORT).show();
        }
    }



        private void verifyOTP() {
            String code = verifyText.getText().toString().trim();
//            Toast.makeText(getActivity(), code, Toast.LENGTH_SHORT).show();
//            Toast.makeText(getActivity(), "correct"+randomno, Toast.LENGTH_SHORT).show();

            if(randomno.equalsIgnoreCase(code)){
                Toast.makeText(getActivity(),"Otp verified successfully", Toast.LENGTH_SHORT).show();
                verifyText.setText("");
//                Toast.makeText(getActivity(),id, Toast.LENGTH_SHORT).show();

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChild(id)){
                    counts=Integer.parseInt(dataSnapshot.child(id).child("count").getValue().toString());
//                    Toast.makeText(getActivity(),""+counts,Toast.LENGTH_SHORT).show();
                    cnt=new Count();
                    cnt.setCount(Integer.toString(counts+1));
                    myRef.child(id).setValue(cnt);
                    Toast.makeText(getActivity(),"Submitted successfully",Toast.LENGTH_SHORT).show();

                }
                else{
                    cnt=new Count("1");


                    try {
                        myRef.child(id).setValue(cnt);
                        Toast.makeText(getActivity(),"Submitted successfully",Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception e){
                        Toast.makeText(getActivity(),"Error in submission",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

            }
            else{
                Toast.makeText(getActivity(),"Wrong OTP",Toast.LENGTH_SHORT).show();
                verifyText.setText("");
            }
        }

}
