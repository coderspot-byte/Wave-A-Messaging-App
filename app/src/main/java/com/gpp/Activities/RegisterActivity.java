package com.gpp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
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

import java.util.regex.Pattern;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    EditText name , mobile , password , email;
    CircularProgressButton next;
    TextView alreadyuser;
    String ans1;
    FirebaseUser firebaseUser;
    ImageView alreadyuser1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Action Bar Color Change
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        name = (EditText)findViewById(R.id.editTextName);
        email = (EditText)findViewById(R.id.email);
        mobile = (EditText)findViewById(R.id.editTextMobile);
        password = (EditText)findViewById(R.id.password);
        alreadyuser = (TextView)findViewById(R.id.alreadyuser);
        alreadyuser1 = (ImageView)findViewById(R.id.alreadyuser1);
        ans1 = getIntent().getStringExtra("IMPMOB");
        mobile.setText(ans1);
        mobile.setEnabled(false);
        alreadyuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this , LoginActivity.class);
                startActivity(intent);
            }
        });

        alreadyuser1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this , LoginActivity.class);
                startActivity(intent);
            }
        });



        //redirect to Profilepic activity
        next = (CircularProgressButton)findViewById(R.id.rg);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String naming = name.getText().toString();
                String mail = email.getText().toString();
                String pass = password.getText().toString();
                int i = 0;
                if(name.length() == 0)
                {
                    name.setError("Name is Required");
                    i = 1;
                    return;
                }

                if(!isValid(mail))
                {
                    email.setError("Enter Valid E-mail");
                    i = 1;
                    return;
                }



                if(pass.length() < 8)
                {
                    password.setError("Password Must be 8 Character or more");
                    i = 1;
                    return;
                }


                int finalI = i;
                mAuth.createUserWithEmailAndPassword(mail , pass).addOnCompleteListener(RegisterActivity.this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful())
                        {
                            // code to upload image from profilepic activity and then finally register
                            if(finalI == 0)
                            {
                                Intent intent = new Intent(RegisterActivity.this , ProfilePicActivity.class);
                                String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                intent.putExtra("NAME" , naming);
                                intent.putExtra("MOB" , ans1);
                                intent.putExtra("MAIL" , mail);
                                intent.putExtra("PASS",pass);
                                intent.putExtra("UID",uid);
                                startActivity(intent);
                            }
                        }
                        else
                        {
                            AlertDialog.Builder builder
                                    = new AlertDialog
                                    .Builder(RegisterActivity.this);

                            builder.setMessage("E-mail Already Registered");
                            builder.setCancelable(false);
                            builder
                                    .setPositiveButton(
                                            "Ok",
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
                .Builder(RegisterActivity.this);

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
                                finish();
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
    public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }
}