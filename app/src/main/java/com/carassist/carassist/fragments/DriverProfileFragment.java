package com.carassist.carassist.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.carassist.carassist.R;
import com.carassist.carassist.adapters.DriverProfileArrayAdapter;
import com.carassist.carassist.data.Cars;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by user on 2/28/2016.
 */
public class DriverProfileFragment extends Fragment {

    Firebase mRef = new Firebase("https://car-assist.firebaseio.com/");

    ArrayList<Cars> items = new ArrayList<>();

    DriverProfileArrayAdapter driverProfileArrayAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_driver_profile, container, false);

        driverProfileArrayAdapter = new DriverProfileArrayAdapter(getActivity(),items);

        Firebase driverRef = mRef.child("cars");
        //Query queryRef = driverRef.orderByChild("uniqueId").equalTo(mRef.getAuth().getUid());

        LinearLayout linearLayout = (LinearLayout)rootView.findViewById(R.id.fragment_driver_profile_empty_state);
        ListView listView = (ListView)rootView.findViewById(R.id.fragment_driver_profile_list_view);

        driverRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){

                    Cars cars = snapshot.getValue(Cars.class);
                    items.add(cars);

                }

                driverProfileArrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        //items.add(new Cars("name", "color", "mileage", "description", "pic", "", ""));

        listView.setAdapter(driverProfileArrayAdapter);

        linearLayout.setVisibility(View.GONE);

        return rootView;
    }
}
