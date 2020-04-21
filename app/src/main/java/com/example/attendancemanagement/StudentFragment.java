package com.example.attendancemanagement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StudentFragment extends Fragment {
    private FirebaseAuth mAuth;
    EditText emailtxt,pass;
    Button signinbtn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_student,container,false);

        mAuth = FirebaseAuth.getInstance();
        emailtxt=(EditText)view.getRootView().findViewById(R.id.emailstudent);
        pass=(EditText)view.getRootView().findViewById(R.id.passstudent);
        signinbtn=(Button)view.getRootView().findViewById(R.id.signin_btnstudent);


        signinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInStudent();
            }
        });

        return view;
    }
    private void signInStudent(){
        String email=emailtxt.getText().toString();
        String password=pass.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>()  {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            emailtxt.setText("");
                            pass.setText("");
                            updateUI(user);

                        } else {
                            // If sign in fails, display a message to the user.

                            Log.w("TAG", "signInWithEmail:failure"+ task.getException());
                            Toast.makeText(getActivity(), "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            emailtxt.setText("");
                            pass.setText("");
                            updateUI(null);
                        }

                        // ...
                    }
                }
                );
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }
    private void updateUI(FirebaseUser user) {
       if(user!=null){

//            Toast.makeText(getActivity(),user.getEmail(),Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(),HomeActivity.class));

        }
        else{
//            Toast.makeText(getActivity(),"User login failed",Toast.LENGTH_SHORT).show();

        }
  }
}
