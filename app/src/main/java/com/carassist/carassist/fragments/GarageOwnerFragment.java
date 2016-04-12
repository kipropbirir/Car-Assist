package com.carassist.carassist.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.carassist.carassist.R;
import com.carassist.carassist.adapters.GarageArrayAdapter;
import com.carassist.carassist.adapters.GarageProfileArrayAdapter;
import com.carassist.carassist.data.Garage;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by user on 2/27/2016.
 */
public class GarageOwnerFragment extends Fragment {

    ListView listView;
    ArrayList<Garage> items = new ArrayList<>();

    Firebase mRef = new Firebase("https://car-assist.firebaseio.com/");

    GarageArrayAdapter garageArrayAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_garage,container,false);

        garageArrayAdapter = new GarageArrayAdapter(getActivity(),items);

        listView = (ListView)rootView.findViewById(R.id.fragment_garage_list_view);

        mRef.child("garage").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    Garage garage = postSnapshot.getValue(Garage.class);
                    items.add(garage);
                }
                garageArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                if(firebaseError != null){
                    Toast.makeText(getActivity(),"An error occurred",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //items.add(new Garage("AutoPorts Garage", "We offer services for BMW and VW", "Juja,Kiambu", "", ""));

        listView.setAdapter(garageArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ImageView call = (ImageView)view.findViewById(R.id.garage_call);
                ImageView locate = (ImageView)view.findViewById(R.id.garage_locate);

                call.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                locate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });

        return rootView;
    }
}
