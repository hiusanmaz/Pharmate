package fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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

import com.example.pharmate.ForgetPassword;
import com.example.pharmate.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import homepage.HomePageOrg;

public class Frag2 extends Fragment {
    private FirebaseAuth firebaseAuth;
    EditText mailSign, passwordSign;
    Button login;
    TextView forget;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.frag2_layout,container,false);

        mailSign=view.findViewById(R.id.mailSign);
        passwordSign=view.findViewById(R.id.passwordSign);
        login=view.findViewById(R.id.login);
        forget=view.findViewById(R.id.forgotpasswordd);
        firebaseAuth = FirebaseAuth.getInstance();
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String email = mailSign.getText().toString();
                String password =passwordSign.getText().toString();
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                        alert.setTitle("Information");
                        alert.setMessage("Are you sure you want to login?");
                        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                Toast.makeText(getActivity(), "Login Canceled", Toast.LENGTH_SHORT).show();
                            }
                        });
                        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(firebaseAuth.getCurrentUser().isEmailVerified()){
                                    Toast.makeText(getActivity(), "Login Successful", Toast.LENGTH_SHORT).show();

                                    Intent _intent = new Intent(getActivity(), HomePageOrg.class);
                                    startActivity(_intent);
                                    getActivity().finish();
                                }else{
                                    Toast.makeText(getActivity(), "please verify your email address", Toast.LENGTH_SHORT).show();
                                }
                            }

                        });
                        alert.create().show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        forget.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent _intent = new Intent(getActivity(), ForgetPassword.class);
                startActivity(_intent);
                getActivity().finish();
            }
        });
        return view;
}}
