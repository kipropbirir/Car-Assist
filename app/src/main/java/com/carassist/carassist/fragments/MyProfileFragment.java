package com.carassist.carassist.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.carassist.carassist.R;

import net.rehacktive.waspdb.WaspDb;
import net.rehacktive.waspdb.WaspFactory;
import net.rehacktive.waspdb.WaspHash;

/**
 * Created by user on 5/26/2016.
 */
public class MyProfileFragment extends Fragment {

    String path;
    String databaseName;
    String password;
    WaspDb db;
    WaspHash userDetails;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_my_profile,container,false);

        path = getActivity().getFilesDir().getPath();
        databaseName = "Car Assist DB";
        password = "qwertyuiop";
        db = WaspFactory.openOrCreateDatabase(path, databaseName, password);
        userDetails = db.openOrCreateHash("userDetails");

        TextView name = (TextView)rootView.findViewById(R.id.my_profile_name);
        TextView username = (TextView)rootView.findViewById(R.id.my_profile_username);
        TextView email = (TextView)rootView.findViewById(R.id.my_profile_email);
        TextView phoneNumber = (TextView)rootView.findViewById(R.id.my_profile_phone_number);

        name.setText(userDetails.get("name").toString());
        username.setText(userDetails.get("userName").toString());
        email.setText(userDetails.get("email").toString());
        phoneNumber.setText(userDetails.get("phoneNumber").toString());

        return rootView;
    }
}
