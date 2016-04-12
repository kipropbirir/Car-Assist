package com.carassist.carassist.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.carassist.carassist.R;
import com.carassist.carassist.data.Spares;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddSparesActivity extends AppCompatActivity {

    //creates an instance of Firebase where the databse resides as indicated by
    //the url passed as a parameter to the Firebase constructor
    Firebase mRef = new Firebase("https://car-assist.firebaseio.com/");

    private Bitmap bitmap;
    private int PICK_IMAGE_REQUEST = 1;
    Spares spares;

    MaterialEditText photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_spares_layout);

        spares = new Spares();

        //initialize the views from the layout as indicated in setContentView above
        final MaterialEditText name = (MaterialEditText)findViewById(R.id.add_spares_name);
        final MaterialEditText description = (MaterialEditText)findViewById(R.id.add_spares_description);
        final MaterialEditText price = (MaterialEditText)findViewById(R.id.add_spares_price);
        final MaterialEditText location = (MaterialEditText)findViewById(R.id.add_spares_location);
        photo = (MaterialEditText)findViewById(R.id.add_spares_photo);

        Button addPhoto = (Button)findViewById(R.id.add_spares_add_photo_button);
        Button saveFab = (Button)findViewById(R.id.add_spares_save_button);

        //listens to the click of the addPhoto button
        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //opens a window that allows the user to pick an image from the gallery
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);

            }
        });

        //listens to the click of the save button
        saveFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //creates a json object "spares" in the database where the values of the spares such as
                //name,description,price,location,pic will be stored as child elements

                Firebase sparesRef = mRef.child("spares").push();

                spares.setName(name.getText().toString());
                spares.setDescription(description.getText().toString());
                spares.setPrice(price.getText().toString());
                spares.setLocation(location.getText().toString());
                spares.setPic(convertBitmap(bitmap));
                spares.setUniqueId(mRef.getAuth().getUid());
                spares.setPushId(sparesRef.getKey());

                sparesRef.setValue(spares, new Firebase.CompletionListener() {

                    @Override
                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {

                        if (firebaseError != null) {
                            //an error occurred
                            Toast.makeText(getApplicationContext(), "an error occurred,please try again", Toast.LENGTH_SHORT).show();

                        } else {
                            //data was saved successfully
                            Toast.makeText(getApplicationContext(), "data saved successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

    //this method is called after the user has picked an image from the gallery

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));
                photo.setText(uri.toString());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //converts the image that has been picke from gallery into a base64 String for storage in the database
    public String convertBitmap(Bitmap bitmap){
        String imageFile="";

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        bitmap.recycle();
        byte[] byteArray = stream.toByteArray();
        imageFile = Base64.encodeToString(byteArray, Base64.DEFAULT);

        return imageFile;
    }

}
