package com.carassist.carassist.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.carassist.carassist.R;
import com.carassist.carassist.adapters.SparesArrayAdapter;
import com.carassist.carassist.adapters.SparesProfileArrayAdapter;
import com.carassist.carassist.data.Garage;
import com.carassist.carassist.data.Spares;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by user on 2/27/2016.
 */
public class SparesFragment extends Fragment {

    ListView listView;
    ArrayList<Spares> items = new ArrayList<>();

    SparesArrayAdapter sparesArrayAdapter;

    Firebase mRef = new Firebase("https://car-assist.firebaseio.com/");

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_spares,container,false);

        sparesArrayAdapter = new SparesArrayAdapter(getActivity(),items);

        listView = (ListView)rootView.findViewById(R.id.fragment_spares_list_view);

        //items.add(new Spares("Shock Absorber","picUrl","ksh. 12000","Ngong Road,Nairobi","","",""));

        mRef.child("spares").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Spares spares = postSnapshot.getValue(Spares.class);
                    items.add(spares);
                }
                sparesArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                if (firebaseError != null) {
                    Toast.makeText(getActivity(), "An error occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });

        listView.setAdapter(sparesArrayAdapter);

        return rootView;
    }
}
