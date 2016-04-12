package com.carassist.carassist.activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.carassist.carassist.R;
import com.carassist.carassist.data.AuthCredentials;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.rengwuxian.materialedittext.MaterialEditText;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import io.codetail.widget.RevealFrameLayout;

public class MainActivity extends AppCompatActivity {

    CardView cardView,middleCardView,outerCardView;
    FloatingActionButton signUpFab;
    ImageView cancelButton;
    MaterialEditText signUpName,signUpUsername,signUpEmail,signUpPhoneNumber,signUpPassword,signUpRepeatPassword;
    MaterialEditText loginEmail,loginPassword;
    Button signUpButton,loginButton;

    //creates an instance of AuthCredentials which will contain the user details enterd during
    //the sign up process
    AuthCredentials authCredentials = new AuthCredentials();

    //creates an instance of the database
    Firebase mRef = new Firebase("https://car-assist.firebaseio.com/");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardView = (CardView)findViewById(R.id.card_view);
        middleCardView = (CardView)findViewById(R.id.middle_card_view);
        outerCardView = (CardView)findViewById(R.id.outer_card_view);

        signUpFab = (FloatingActionButton)findViewById(R.id.sign_up_fab);
        cancelButton = (ImageView)findViewById(R.id.cancel_image_view);

        signUpName = (MaterialEditText)findViewById(R.id.sign_up_name);
        signUpUsername = (MaterialEditText)findViewById(R.id.sign_up_username);
        signUpEmail = (MaterialEditText)findViewById(R.id.sign_up_email);
        signUpPhoneNumber = (MaterialEditText)findViewById(R.id.sign_up_phone_number);
        signUpPassword = (MaterialEditText)findViewById(R.id.sign_up_password);
        signUpRepeatPassword = (MaterialEditText)findViewById(R.id.sign_up_repeat_password);
        signUpButton = (Button)findViewById(R.id.sign_up_button);

        loginButton = (Button)findViewById(R.id.login_button);
        loginEmail = (MaterialEditText)findViewById(R.id.login_email);
        loginPassword = (MaterialEditText)findViewById(R.id.login_password);

        //check user authentication state
        AuthData authData = mRef.getAuth();

        if(authData != null){
            //user authenticated
            Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(intent);
            finish();
        }else{
            //user not authenticated

        }

        signUpFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outerCardView.setVisibility(View.VISIBLE);
                setViewMargins();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                outerCardView.setVisibility(View.INVISIBLE);
                setViewMargins();
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logIn();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });

        //circularReveal();

    }

    /*
    converts values from pixels to density pixels so that they can be used to resize the views
     */
    public int pxToDp(int pixels){
        int densityPixels = 0;

        densityPixels = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,pixels,getResources().getDisplayMetrics());

        return densityPixels;
    }

    /*
     resizes the margins of the views
     */
    public void setViewMargins(){

        RevealFrameLayout.LayoutParams cardViewLayoutParams = new RevealFrameLayout.LayoutParams(RevealFrameLayout.LayoutParams.MATCH_PARENT, RevealFrameLayout.LayoutParams.MATCH_PARENT);
        RevealFrameLayout.LayoutParams middleCardViewLayoutParams = new RevealFrameLayout.LayoutParams(RevealFrameLayout.LayoutParams.MATCH_PARENT, RevealFrameLayout.LayoutParams.MATCH_PARENT);


        if(outerCardView.getVisibility() == View.INVISIBLE){

            cardViewLayoutParams.setMargins(pxToDp(5),pxToDp(0),pxToDp(5),pxToDp(0));
            middleCardViewLayoutParams.setMargins(pxToDp(0),pxToDp(5),pxToDp(0),pxToDp(0));

            cardView.setLayoutParams(cardViewLayoutParams);
            middleCardView.setLayoutParams(middleCardViewLayoutParams);

        }else if(outerCardView.getVisibility() == View.VISIBLE){

            cardViewLayoutParams.setMargins(pxToDp(10),pxToDp(0),pxToDp(10),pxToDp(0));
            middleCardViewLayoutParams.setMargins(pxToDp(5),pxToDp(5),pxToDp(5),pxToDp(0));

            cardView.setLayoutParams(cardViewLayoutParams);
            middleCardView.setLayoutParams(middleCardViewLayoutParams);

        }

    }


    public void signUp(){

        //checks whether user has entered all the details without leaving any field blank
        if(signUpEmail.getText().toString().equalsIgnoreCase("") || signUpName.getText().toString().equalsIgnoreCase("")
                || signUpPassword.getText().toString().equalsIgnoreCase("") || signUpRepeatPassword.getText().toString().equalsIgnoreCase("")
                || signUpPhoneNumber.getText().toString().equalsIgnoreCase("") || signUpUsername.getText().toString().equalsIgnoreCase("")){

            //prompt user to enter details
            Toast.makeText(getApplicationContext(),"enter details",Toast.LENGTH_SHORT).show();

        }else{

            //if all details are set, an account for the user is created
            if(signUpPassword.getText().toString().equals(signUpRepeatPassword.getText().toString())){

                authCredentials.setEmail(signUpEmail.getText().toString());
                authCredentials.setName(signUpName.getText().toString());
                authCredentials.setPassword(signUpPassword.getText().toString());
                authCredentials.setPhoneNumber(signUpPhoneNumber.getText().toString());
                authCredentials.setUsername(signUpUsername.getText().toString());

                mRef.createUser(signUpEmail.getText().toString(), signUpPassword.getText().toString(), new Firebase.ResultHandler() {
                    @Override
                    public void onSuccess() {
                        mRef.authWithPassword(signUpEmail.getText().toString(), signUpPassword.getText().toString(), new Firebase.AuthResultHandler() {
                            @Override
                            public void onAuthenticated(AuthData authData) {

                                mRef.child("users").child(authData.getUid()).setValue(authCredentials, new Firebase.CompletionListener() {
                                    @Override
                                    public void onComplete(FirebaseError firebaseError, Firebase firebase) {

                                        if (firebaseError != null) {
                                            //an error occurred

                                        } else {
                                            //success
                                            //Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                            //startActivity(intent);

                                            //finish();

                                        }

                                    }
                                });

                            }

                            @Override
                            public void onAuthenticationError(FirebaseError firebaseError) {

                            }
                        });
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {

                    }
                });


            }else{

                //password do not match

            }

        }

    }

    public void logIn(){

        //checks if the user has entered details in all the fields
        if(loginEmail.getText().toString().equalsIgnoreCase("") || loginPassword.getText().toString().equalsIgnoreCase("")){
            //prompt user to enter details
            Toast.makeText(getApplicationContext(),"enter details",Toast.LENGTH_SHORT).show();

        }else{

            //authenticates the user
            mRef.authWithPassword(loginEmail.getText().toString(), loginPassword.getText().toString(), new Firebase.AuthResultHandler() {
                @Override
                public void onAuthenticated(AuthData authData) {

                    //if the user is authenticated he is directed to the 'HomePage'
                    Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                    startActivity(intent);

                    finish();
                }

                @Override
                public void onAuthenticationError(FirebaseError firebaseError) {
                    //an error occurred
                }
            });

        }

    }

}
