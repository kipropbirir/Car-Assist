package com.carassist.carassist.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import com.carassist.carassist.R;
import com.carassist.carassist.data.Cars;
import com.carassist.carassist.data.Garage;
import com.carassist.carassist.data.Spares;
import com.carassist.carassist.fragments.DriverProfileFragment;
import com.carassist.carassist.fragments.GarageOwnerProfileFragment;
import com.carassist.carassist.fragments.SparesProfileFragment;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {

    //create an instance of the firebase database
    Firebase mRef = new Firebase("https://car-assist.firebaseio.com/");

    Spares spares = new Spares();
    String category = "";
    private int PICK_IMAGE_REQUEST = 1;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            category = getIntent().getStringExtra("PROFILECATEGORY");

        switch(category){
            case "spares":
                getSupportActionBar().setTitle("Spares");
                setFragment(new SparesProfileFragment());
                break;
            case "garage":
                getSupportActionBar().setTitle("Garage");
                setFragment(new GarageOwnerProfileFragment());
                break;
            case "driver":
                getSupportActionBar().setTitle("Driver");
                setFragment(new DriverProfileFragment());
                break;
            default:
                setFragment(null);
                break;
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //listen to the click of the FloatingActionButton
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //checks the selected category and navigates to the appropriate activity (screen)
                if (category.equalsIgnoreCase("spares")) {
                    //sparesDialog();
                    Intent intent = new Intent(getApplicationContext(),AddSparesActivity.class);
                    startActivity(intent);

                } else if (category.equalsIgnoreCase("garage")) {
                    garageDialog();
                } else {
                    carDialog();
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri uri = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                // Log.d(TAG, String.valueOf(bitmap));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setFragment(Fragment fragment){

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.profile_fragment,fragment);
        fragmentTransaction.commit();

    }

    public void carDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this,R.style.MyDialogTheme);
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.add_car_layout,null);
        dialog.setView(dialogView);
        dialog.setTitle("ADD CAR");

        final View colorBox = dialogView.findViewById(R.id.add_car_body_paint_color_box);
        final SeekBar redSeekBar = (SeekBar)dialogView.findViewById(R.id.add_car_body_paint_red);
        final SeekBar greenSeekBar = (SeekBar)dialogView.findViewById(R.id.add_car_body_paint_green);
        final SeekBar blueSeekBar = (SeekBar)dialogView.findViewById(R.id.add_car_body_paint_blue);

        blueSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                colorBox.setBackgroundColor(Color.rgb(redSeekBar.getProgress(),greenSeekBar.getProgress(),progress));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        greenSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                colorBox.setBackgroundColor(Color.rgb(redSeekBar.getProgress(),progress,blueSeekBar.getProgress()));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        redSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                colorBox.setBackgroundColor(Color.rgb(progress,greenSeekBar.getProgress(),blueSeekBar.getProgress()));

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        dialog.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //convert rgb color to hexadecimal
                String colorHex = String.format("#%02x%02x%02x",redSeekBar.getProgress(),greenSeekBar.getProgress(),blueSeekBar.getProgress());

                //initialize dialog views
                MaterialEditText carModel = (MaterialEditText) ((AlertDialog) dialog).findViewById(R.id.add_car_car_model);
                MaterialEditText bodyType = (MaterialEditText) ((AlertDialog) dialog).findViewById(R.id.add_car_body_type);
                MaterialEditText bodyPaint = (MaterialEditText) ((AlertDialog) dialog).findViewById(R.id.add_car_body_paint);
                MaterialEditText mileage = (MaterialEditText) ((AlertDialog) dialog).findViewById(R.id.add_car_mileage);
                MaterialEditText description = (MaterialEditText) ((AlertDialog) dialog).findViewById(R.id.add_car_other_description);

                RadioGroup radioGroup = (RadioGroup) ((AlertDialog)dialog).findViewById(R.id.add_car_body_type_radio_button);
                RadioButton radioButton = (RadioButton) ((AlertDialog)dialog).findViewById(radioGroup.getCheckedRadioButtonId());
                bodyType.setText(radioButton.getText());
                bodyPaint.setText(colorHex);

                Firebase carRef = mRef.child("cars").push();

                Cars cars = new Cars();
                cars.setCarModel(carModel.getText().toString());
                cars.setBodyType(bodyType.getText().toString());
                cars.setBodyPaint(bodyPaint.getText().toString());
                cars.setDescription(description.getText().toString());
                cars.setMileage(mileage.getText().toString());
                cars.setUniqueId(mRef.getAuth().getUid());
                cars.setPushId(carRef.getKey());

                carRef.setValue(cars, new Firebase.CompletionListener() {

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
        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

    public void garageDialog(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this,R.style.MyDialogTheme);
        dialog.setView(R.layout.add_garage_layout);
        dialog.setTitle("ADD GARAGE");
        dialog.setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //initialize dialog views
                MaterialEditText name = (MaterialEditText) ((AlertDialog) dialog).findViewById(R.id.add_garage_name);
                MaterialEditText description = (MaterialEditText) ((AlertDialog) dialog).findViewById(R.id.add_garage_description);
                MaterialEditText location = (MaterialEditText) ((AlertDialog) dialog).findViewById(R.id.add_garage_location);

                Firebase garageRef = mRef.child("garage").push();

                Garage garage = new Garage();
                garage.setName(name.getText().toString());
                garage.setLocation(location.getText().toString());
                garage.setDescription(description.getText().toString());
                garage.setUniqueId(mRef.getAuth().getUid());
                garage.setPushId(garageRef.getKey());

                garageRef.setValue(garage, new Firebase.CompletionListener() {

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
        dialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog alertDialog = dialog.create();
        alertDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case android.R.id.home:
                super.onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
