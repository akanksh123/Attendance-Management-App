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
    private Calendar calendar;
    private SimpleDateFormat simpleDateFormat;
    private Button submitbtn,verifybtn;
    private Button checkAttendance;
    private String date,value;
    private CalendarView calendarView;
    private String selectedDate;
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
//        mAuth=FirebaseAuth.getInstance();
//
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        myRef=mFirebaseDatabase.getReference("user");
        useremail=FirebaseAuth.getInstance().getCurrentUser().getEmail();
        id=useremail.split("@")[0];

//
//        calendarView =(CalendarView)view.getRootView().findViewById(R.id.calendarView);


        StrictMode.ThreadPolicy policy=new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

//
//        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
//                selectedDate=Integer.toString(year)+Integer.toString(month)+Integer.toString(dayOfMonth);
//
//
//                Toast.makeText(getActivity(),selectedDate+"  ",Toast.LENGTH_SHORT).show();
//            }
//        });
        random=new Random();
        submitbtn =(Button)view.getRootView().findViewById(R.id.submit_present);
        verifyText=(EditText)view.findViewById(R.id.text_verification);
        verifybtn=(Button)view.getRootView().findViewById(R.id.verify_button);
        checkAttendance=(Button)view.getRootView().findViewById(R.id.check_attendance);
        calendar=Calendar.getInstance();
        simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        date=simpleDateFormat.format(calendar.getTime());


        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(),date,Toast.LENGTH_SHORT).show();
                sendMessage();
            }
        });

        verifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.hasChild(id)){
                        counts=Integer.parseInt(dataSnapshot.child(id).child("count").getValue().toString());
                        Toast.makeText(getActivity(),""+counts,Toast.LENGTH_SHORT).show();
                        cnt=new Count();
                        cnt.setCount(Integer.toString(counts+1));
                        myRef.child(id).setValue(cnt);
                        Toast.makeText(getActivity(),"Updated successfully",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        cnt=new Count("0");


                        try {
                            myRef.child(id).setValue(cnt);
                            Toast.makeText(getActivity(),"Added successfully",Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception e){
                            Toast.makeText(getActivity(),""+e,Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


//                verifyOTP();
            }
        });
//
//        checkAttendance.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                myRef.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        value=dataSnapshot.getValue(String.class);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                        Log.w("TAG", "Failed to read value.", databaseError.toException());
//
//                    }
//                });
//
//                Fragment  markAttendanceFagment= new MarkAttendanceFagment();
//                Bundle args =new Bundle();
//                args.putString("dates",value);
//                Toast.makeText(getActivity(),value,Toast.LENGTH_SHORT).show();
//                markAttendanceFagment.setArguments(args);
//                getFragmentManager().beginTransaction().replace(R.id.fragment_container1,markAttendanceFagment).commit();
//            }
//        });
//



        return view;

    }

    private void sendMessage() {
        String phoneNumber="+919845352320";
        randomno= Integer.toString(random.nextInt(999999)) ;


        try {

            String apiKey = "apikey=" + "tLx32oZCOU8-U4lZQ4ftGKLvRfbaVsrUw0hG3XRRVH";
            String message = "&message=" + "Your otp is"+randomno;
            String sender = "&sender=" + "TXTLCL";
            String numbers = "&numbers=" + phoneNumber;

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

            Toast.makeText(getActivity(),"Error "+e,Toast.LENGTH_SHORT).show();
        }
    }



//        private void verifyOTP() {
//            String code = verifyText.getText().toString().trim();
//            Toast.makeText(getActivity(), code, Toast.LENGTH_SHORT).show();
//            Toast.makeText(getActivity(), "correct"+randomno, Toast.LENGTH_SHORT).show();

//

//            if(randomno.equalsIgnoreCase(code)){
//                Toast.makeText(getActivity(),"Otp verified successfully", Toast.LENGTH_SHORT).show();

//            Toast.makeText(getActivity(),id, Toast.LENGTH_SHORT).show();
//            Integer counting=10;


//            }
//            else{
//                Toast.makeText(getActivity(),"Wrong OTP",Toast.LENGTH_SHORT).show();
//            }
//        }
//    private void verifyCredential(PhoneAuthCredential credential) {
//
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            cnt.setCount(Integer.parseInt("10"));
//                            myRef.child("User1").setValue(cnt);
//
//                            Toast.makeText(getActivity(),"Works perfectly",Toast.LENGTH_SHORT).show();
//                        } else {
//                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
//                                Toast.makeText(getActivity(),"Unsuccessfull",Toast.LENGTH_SHORT).show();
//                            }
//                            Toast.makeText(getActivity(),"Unsuccessfull again",Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }



}
