package com.example.attendancemanagement;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
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
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class CalendarFagment extends Fragment {
    FirebaseAuth mAuth;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    private Button submitbtn,verifybtn;
    private Button checkAttendance;
    String date;
    private CalendarView calendarView;
    private String selectedDate;
    EditText verifyText;
    String codeSent;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar,
                container, false);
        mAuth=FirebaseAuth.getInstance();

        calendarView =(CalendarView)view.getRootView().findViewById(R.id.calendarView);



        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                selectedDate=Integer.toString(year)+Integer.toString(month)+Integer.toString(dayOfMonth);


                Toast.makeText(getActivity(),selectedDate+"  ",Toast.LENGTH_SHORT).show();
            }
        });

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
                sendVerificationCode();
            }
        });

        verifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyOTPCode();
            }
        });

        checkAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment  markAttendanceFagment= new MarkAttendanceFagment();
                Bundle args =new Bundle();
                args.putString("dates","your stirng");
                markAttendanceFagment.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container,markAttendanceFagment).commit();
            }
        });




        return view;

    }

    private void verifyOTPCode(){
        String code=verifyText.getText().toString().trim();
        Toast.makeText(getActivity(),code,Toast.LENGTH_SHORT).show();
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(codeSent,code);
        verifyCredential(credential);
    }
    private void verifyCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast.makeText(getActivity(),"Works perfectly",Toast.LENGTH_SHORT).show();
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getActivity(),"Unsuccessfull",Toast.LENGTH_SHORT).show();
                            }
                            Toast.makeText(getActivity(),"Unsuccessfull again",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void sendVerificationCode() {
        String phoneNumber = "+919845352320";


        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                getActivity(),               // Activity (for callback binding)
                mCallbacks);

    }
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {


        }

        @Override
        public void onVerificationFailed(FirebaseException e) {

        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            codeSent = s;
        }
    };
}
