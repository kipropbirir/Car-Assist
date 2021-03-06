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
import android.widget.LinearLayout;
import android.widget.ListView;

import com.carassist.carassist.R;
import com.carassist.carassist.adapters.GarageProfileArrayAdapter;
import com.carassist.carassist.data.Garage;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by user on 2/28/2016.
 */
public class GarageOwnerProfileFragment extends Fragment {

    Firebase mRef = new Firebase("https://car-assist.firebaseio.com/");

    ArrayList<Garage> items = new ArrayList<>();

    GarageProfileArrayAdapter garageProfileArrayAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_garage_profile, container, false);

        garageProfileArrayAdapter = new GarageProfileArrayAdapter(getActivity(),items);

        Firebase garageRef = mRef.child("garage");
        Query queryRef = garageRef.orderByChild("uniqueId").equalTo(mRef.getAuth().getUid());

        LinearLayout linearLayout = (LinearLayout)rootView.findViewById(R.id.fragment_garage_profile_empty_state);
        ListView listView = (ListView)rootView.findViewById(R.id.fragment_garage_profile_list_view);

        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //remove all items in the ArrayList
                items.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    Garage garage = snapshot.getValue(Garage.class);
                    items.add(garage);
                }
                garageProfileArrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        //items.add(new Garage("name", "description", "location", "", ""));

        listView.setAdapter(garageProfileArrayAdapter);

        linearLayout.setVisibility(View.GONE);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Firebase deleteSparesRef = mRef.child("garage").child(items.get(position).getPushId());

                ImageView delete = (ImageView)view.findViewById(R.id.garage_delete);
                ImageView edit = (ImageView)view.findViewById(R.id.garage_edit);

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        deleteSparesRef.setValue(null, new Firebase.CompletionListener() {
                            @Override
                            public void onComplete(FirebaseError firebaseError, Firebase firebase) {


                                if(firebaseError == null){
                                    //item deleted successfully
                                    Snackbar.make(v, "item has been deleted", Snackbar.LENGTH_SHORT).show();
                                    garageProfileArrayAdapter.notifyDataSetChanged();

                                }else{
                                    //an error occurred
                                    Snackbar.make(v,"an error occurred, try again",Snackbar.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                });

            }
        });

        return rootView;
    }
}
