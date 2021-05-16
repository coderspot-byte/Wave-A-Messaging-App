package com.gpp.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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

public class ProfileActivity extends AppCompatActivity {

    TextView n, e, p;
    ImageView img, cam;
    DatabaseReference reference;
    FirebaseUser fuser;
    Uri filepath;
    Bitmap bitmap;
    FirebaseStorage storage;
    StorageReference storageReference;
    FirebaseAuth mAuth;
    String sname,smail,sphone,sstatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        n = (TextView) findViewById(R.id.name);
        e = (TextView) findViewById(R.id.mail);
        p = (TextView) findViewById(R.id.phone);
        img = findViewById(R.id.pic);
        cam = findViewById(R.id.camera);

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("users").child(fuser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                User user = snapshot.getValue(User.class);
                n.setText(user.getName());
                e.setText(user.getMail());
                p.setText(user.getMob());
                sname=user.getName();
                smail=user.getMail();
                sphone=user.getMob();
                sstatus=user.getStatus();
                if (user.getPimage().equals("default")) {
                    img.setImageResource(R.drawable.pppp);
                } else {
                    Glide.with(getApplicationContext()).load(user.getPimage()).into(img);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(ProfileActivity.this)
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
                img.setImageBitmap(bitmap);
                uploadtofirebase();

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
                                .Builder(ProfileActivity.this);

                        builder.setTitle("Profile Picture Updated");
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
                                                        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                                        User user = new User(uid,sname, uri.toString(),smail,sphone,sstatus);
                                                        root.child(uid).setValue(user);
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
                .Builder(ProfileActivity.this);

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