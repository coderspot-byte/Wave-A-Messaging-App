package com.gpp.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.gpp.R;
import com.gpp.Model.User;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.InputStream;
import java.util.Random;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilePicActivity extends Activity {

    ImageView bk;
    ImageView cam;
    Uri filepath;
    Bitmap bitmap;
    CircularProgressButton resg;
    String name;
    String uid;
    String mobile;
    String status="offline";
    String email , password;
    CircleImageView pic;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M)
        {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
        setContentView(R.layout.activity_profile_pic);

        bk = (ImageView)findViewById(R.id.bk);

        bk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder
                        = new AlertDialog
                        .Builder(ProfilePicActivity.this);

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
        });

        //upload Image function
        cam = (ImageView)findViewById(R.id.camera);
        resg = (CircularProgressButton)findViewById(R.id.resg);
        pic = (CircleImageView)findViewById(R.id.pic);
        name = getIntent().getStringExtra("NAME");
        mobile = getIntent().getStringExtra("MOB");
        email = getIntent().getStringExtra("MAIL");
        password = getIntent().getStringExtra("PASS");
        uid = getIntent().getStringExtra("UID");

        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(ProfilePicActivity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                                Intent intent = new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent , "Select Profile Image " ) , 1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                            }

                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                                permissionToken.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        resg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(filepath == null)
                {
                    AlertDialog.Builder builder
                            = new AlertDialog
                            .Builder(ProfilePicActivity.this);

                    builder.setMessage("Upload Profile Picture");
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
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.register_bk_color));

                }
                else
                {
                    uploadtofirebase();
                }

            }
        });

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {

        if(requestCode == 1 && resultCode==RESULT_OK)
        {
                filepath = data.getData();
                try {
                    InputStream inputStream = getContentResolver().openInputStream(filepath);
                    bitmap  = BitmapFactory.decodeStream(inputStream);
                    pic.setImageBitmap(bitmap);

                }
                catch (Exception ex)
                {

                }

        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void uploadtofirebase()
    {

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference uploader = storage.getReference("Image1" + new Random().nextInt(50));
        ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Please Wait");
        pd.setCancelable(false);
        pd.show();

        uploader.putFile(filepath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                    {
                        pd.dismiss();
                        AlertDialog.Builder builder
                                = new AlertDialog
                                .Builder(ProfilePicActivity.this);

                        builder.setTitle("User Registered");
                        builder.setCancelable(false);
                        builder
                                .setPositiveButton(
                                        "Continue",
                                        new DialogInterface
                                                .OnClickListener() {

                                            @Override
                                            public void onClick(DialogInterface dialog,
                                                                int which)
                                            {
                                                uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                    @Override
                                                    public void onSuccess(Uri uri) {


                                                        FirebaseDatabase db = FirebaseDatabase.getInstance();
                                                        DatabaseReference root = db.getReference("users");
                                                        User user = new User(uid,name, uri.toString(),email,mobile,status);
                                                        root.child(uid).setValue(user);
                                                        Intent intent = new Intent(ProfilePicActivity.this , LoginActivity.class);
                                                        startActivity(intent);
                                                        finish();


                                                    }
                                                });
                                            }
                                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.register_bk_color));


                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot)
                    {
                        float percent = (100 * snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                        pd.setMessage("Uploading " + (int)percent + " %");
                    }
                });

    }

    public void onBackPressed()
    {


        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(ProfilePicActivity.this);

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