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
import com.carassist.carassist.adapters.SparesProfileArrayAdapter;
import com.carassist.carassist.data.Spares;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by user on 2/28/2016.
 */
public class SparesProfileFragment extends Fragment {

    Firebase mRef = new Firebase("https://car-assist.firebaseio.com/");

    ArrayList<Spares> items = new ArrayList<>();

    SparesProfileArrayAdapter sparesProfileArrayAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_spares_profile, container, false);

        sparesProfileArrayAdapter = new SparesProfileArrayAdapter(getActivity(),items);

        Firebase sparesRef = mRef.child("spares");
        //Query queryRef = sparesRef.orderByChild("uniqueId").equalTo(mRef.getAuth().getUid());

        sparesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Spares spares = snapshot.getValue(Spares.class);
                    items.add(spares);
                }

                sparesProfileArrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        LinearLayout linearLayout = (LinearLayout)rootView.findViewById(R.id.fragment_spares_profile_empty_state);
        ListView listView = (ListView)rootView.findViewById(R.id.fragment_spares_profile_list_view);

        //items.add(new Spares("name","pic","price","location","","",""));
        listView.setAdapter(sparesProfileArrayAdapter);

        linearLayout.setVisibility(View.GONE);

        return rootView;
    }
}
