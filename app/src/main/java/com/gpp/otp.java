package com.gpp;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.gpp.Activities.RegisterActivity;
import com.gpp.Activities.TablayoutActivity;

import java.util.concurrent.TimeUnit;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class otp extends Activity {

    EditText phoneNumber,codeEnter,phoneEt,codeEt;
    TextView change;
    AlertDialog.Builder builder;
    CircularProgressButton phoneContinueBtn;
    ImageView codeSubmitBtn;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog pd;
    TextView resendCodeTv;
    String ans;
    String fi;
    FirebaseUser firebaseUser;
    private PhoneAuthProvider.ForceResendingToken forceResendingToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mverificationId;
    private static final String TAG="TAG";

    @Override
    protected void  onStart() {
        super.onStart();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            Intent intent = new Intent(otp.this, TablayoutActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_otp);
        firebaseAuth = FirebaseAuth.getInstance();
        pd=new ProgressDialog(this);
        builder = new AlertDialog.Builder(otp.this);
        builder.setMessage("Phone Number Verified");
        builder.setCancelable(false);
        pd.setTitle("Please wait");
        pd.setCanceledOnTouchOutside(false);
        phoneEt=(EditText)findViewById(R.id.phoneEt);
        String ans1 = "+91";
        ans = ans1 + phoneEt.getText().toString();
        fi = phoneEt.getText().toString().trim();
        codeEt=(EditText)findViewById(R.id.codeEt);
        mCallbacks=new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

                //This callbacks is invoked in an invalid request for verification is made,
                //for instance if the phone number format is not valid.


            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {

                pd.dismiss();
                Toast.makeText(otp.this,"Try Again , Something Went Wrong ",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                super.onCodeSent(verificationId, forceResendingToken);

                Log.d(TAG,"onCodeSent : "+verificationId);

                mverificationId=verificationId;
                forceResendingToken=token;
                pd.dismiss();
                Toast.makeText(otp.this,"Verification Code Sent",Toast.LENGTH_SHORT).show();
            }
        };
        phoneContinueBtn =(CircularProgressButton) findViewById(R.id.phoneContinueBtn);
        phoneContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone= "+91" + phoneEt.getText().toString().trim();
                if(TextUtils.isEmpty(phone) || phone.length() < 13 || phone.length() > 13)
                {
                    phoneEt.setError("Enter Valid Phone  Number");
                }
                else
                {
                    startPhoneNumberVerification(phone);
                }
            }
        });
        resendCodeTv=(TextView)findViewById(R.id.resendCodeTv);

        resendCodeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone= "+91" + phoneEt.getText().toString().trim();
                if(TextUtils.isEmpty(phone))
                {
                    phoneEt.setError("Enter Valid Phone  Number");
                }
                else
                {
                    resendVerificationCode(phone,forceResendingToken);
                }
            }
        });
        codeSubmitBtn =(ImageView) findViewById(R.id.codeSubmitBtn);
        codeSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code=codeEt.getText().toString().trim();
                if(TextUtils.isEmpty(code))
                {
                    codeEt.setError("Enter Valid OTP");
                }
                else
                {
                    verifyPhoneNumberWithCode(mverificationId,code);
                }
            }
        });
    }

    private void resendVerificationCode(String phone,PhoneAuthProvider.ForceResendingToken token) {

        pd.setMessage("Resending OTP");
        pd.show();

        PhoneAuthOptions options=
                PhoneAuthOptions.newBuilder(firebaseAuth).setPhoneNumber(phone).setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .setForceResendingToken(token)
                        .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private void startPhoneNumberVerification(String phone) {

        pd.setMessage("Verifying Phone Number");
        pd.show();

        PhoneAuthOptions options=
                PhoneAuthOptions.newBuilder(firebaseAuth).setPhoneNumber(phone).setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private void verifyPhoneNumberWithCode(String verificationId, String code) {

        pd.setMessage("Verify OTP");
        pd.show();

        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verificationId,code);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        pd.setMessage("Logging In");

        firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        //successfully signed in
                        pd.dismiss();

                        String phone = firebaseAuth.getCurrentUser().getPhoneNumber();
//                        Toast.makeText(otp.this,phone + " Verified",Toast.LENGTH_SHORT).show();
                        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String phone= "+91 " + phoneEt.getText().toString().trim();
                                Intent intent = new Intent(otp.this, RegisterActivity.class);
                                intent.putExtra("IMPMOB" ,phone);
                                startActivity(intent);

                                finish();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //failed
                        pd.dismiss();
                        codeEt.setError("Enter Valid OTP");


                    }
                });
    }

    public void onBackPressed()
    {


        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(otp.this);

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
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.register_bk_color));
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.register_bk_color));

    }


}