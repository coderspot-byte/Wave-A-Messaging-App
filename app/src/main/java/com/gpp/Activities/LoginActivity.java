package com.gpp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.gpp.R;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

import static com.gpp.Activities.RegisterActivity.isValid;

public class LoginActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    CircularProgressButton bt;
    EditText email;
    EditText password;
    TextView newuser;
    ImageView newuser1;
    FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Action Bar Color change
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M)
        {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
        setContentView(R.layout.activity_login);




        newuser = (TextView)findViewById(R.id.newuser);
        newuser1 = (ImageView)findViewById(R.id.newuser1);

        newuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this , RegisterActivity.class);
                startActivity(intent);
            }
        });

        newuser1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this , RegisterActivity.class);
                startActivity(intent);
            }
        });

        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        bt = (CircularProgressButton)findViewById(R.id.login);
        mAuth = FirebaseAuth.getInstance();

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProgressDialog pd = new ProgressDialog(LoginActivity.this);
                pd.setTitle("Please Wait");
                pd.setMessage("Logging In");
                pd.setCancelable(false);
                pd.show();


                String mail = email.getText().toString();
                String pass = password.getText().toString();

                if(!isValid(mail))
                {
                    email.setError("Enter Valid E-mail");
                    return;
                }

                if(pass.length() == 0)
                {
                    password.setError("Enter Valid E-mail or Password");
                    return;
                }


                mAuth.signInWithEmailAndPassword(mail , pass).addOnCompleteListener(LoginActivity.this,new OnCompleteListener<AuthResult>() {


                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {


                        if(task.isSuccessful())
                        {

//                             add recyclerview intent here
                            pd.dismiss();
                            Intent intent = new Intent(LoginActivity.this , TablayoutActivity.class);
                            startActivity(intent);

                        }
                        else
                        {

                            pd.dismiss();
                            AlertDialog.Builder builder
                                    = new AlertDialog
                                    .Builder(LoginActivity.this);

                            builder.setMessage("Enter Valid Email or Password");
                            builder.setCancelable(false);
                            builder
                                    .setPositiveButton(
                                            "OK",
                                            new DialogInterface
                                                    .OnClickListener() {

                                                @Override
                                                public void onClick(DialogInterface dialog,
                                                                    int which)
                                                {
                                                }
                                            });


                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                        }

                    }
                });



            }
        });

    }


    public void onBackPressed()
    {


        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(LoginActivity.this);

        builder.setMessage("Do you want to exit ?");
        builder.setCancelable(false);
        builder
                .setPositiveButton(
                        "Yes",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {

                                // When the user click yes button
                                // then app will close
                                finishAffinity();
                            }
                        });

        builder
                .setNegativeButton(
                        "No",
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which)
                            {

                                // If user click no
                                // then dialog box is canceled.
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}