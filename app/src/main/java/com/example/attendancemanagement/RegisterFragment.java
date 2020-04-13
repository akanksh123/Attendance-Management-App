package com.example.attendancemanagement;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterFragment extends Fragment {
    FirebaseAuth mAuth;
    EditText regemail,regpass;
    Button registerbtn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_register,container,false);
        mAuth = FirebaseAuth.getInstance();

        regemail=(EditText) view.getRootView().findViewById(R.id.register_email);
        regpass=(EditText)view.getRootView().findViewById(R.id.register_pass);
        registerbtn=(Button)view.getRootView().findViewById(R.id.register_button);

        registerbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        return view;
    }

    private void signUp() {
        String email=regemail.getText().toString();
        String password=regemail.getText().toString();

    mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if (task.isSuccessful()) {
                // Sign in success, update UI with the signed-in user's information
                Log.d("TAG", "createUserWithEmail:success");
                FirebaseUser user = mAuth.getCurrentUser();
                Toast.makeText(getActivity(),"Registration successful",Toast.LENGTH_SHORT).show();
            } else {
                // If sign in fails, display a message to the user.
                Log.w("TAG", "createUserWithEmail:failure", task.getException());
                Toast.makeText(getActivity(), "Registration failed.",
                        Toast.LENGTH_SHORT).show();

            }


        }
    });
    }


}
