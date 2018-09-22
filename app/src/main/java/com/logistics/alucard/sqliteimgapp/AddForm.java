package com.logistics.alucard.sqliteimgapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddForm extends AppCompatActivity {

    final int REQUEST_CODE_GALLERY = 1234;
    DatabaseHelper mDatabaseHelper;
    Button btnadd, btnchoose, btNext;
    ImageView myImg;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_form);
        btnadd = (Button) findViewById(R.id.btnadd);
        btnchoose = (Button) findViewById(R.id.btn_choose);
        btNext = findViewById(R.id.btNext);
        myImg = findViewById(R.id.imageView);
        editText = (EditText) findViewById(R.id.addText);
        mDatabaseHelper= new DatabaseHelper(this);

        btnchoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(AddForm.this,
                        new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_GALLERY);
            }
        });

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newEntry = editText.getText().toString();
                byte[] newEntryImg = imageViewToByte(myImg);

                if(editText.length() != 0)
                {
                    AddData(newEntry, newEntryImg);
                    Intent intent =  new Intent(AddForm.this,MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast("Field mandatory");
                }
            }


        });

        btNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent next = new Intent(getApplicationContext(), TestActivity.class);
                startActivity(next);
                //other new cool stuff
            }
        });
    }

    private byte[] imageViewToByte(ImageView myImg) {
        Bitmap bitmap =  ((BitmapDrawable) myImg.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    private void AddData(String newEntry, byte[] newEntryImg)
    {
        boolean insertData = mDatabaseHelper.addData(newEntry, newEntryImg);
        if(insertData)
        {
            Toast("Data inserted");

        }
        else
        {
            Toast("Data not inserted");
        }
    }
    private  void Toast(String s)
    {
        Toast.makeText(this,s,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE_GALLERY) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i, REQUEST_CODE_GALLERY);
            } else {
                Toast.makeText(getApplicationContext(), "You dont have permission to access the file", Toast.LENGTH_SHORT).show();
            }
            return;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Uri uri = data.getData();
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            myImg.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
