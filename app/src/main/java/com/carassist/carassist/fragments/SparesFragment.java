package com.carassist.carassist.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
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

import de.hdodenhof.circleimageview.CircleImageView;

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

        mRef.child("spares").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //remove all items in the ArrayList
                items.clear();

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

        //add footer to the listView
        LayoutInflater layoutInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View footerView = layoutInflater.inflate(R.layout.listview_footer,null);

        listView.addFooterView(footerView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                CircleImageView circleImageView = (CircleImageView)view.findViewById(R.id.spares_image);
                circleImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        displayPictureDialog(items.get(position));
                    }
                });

            }
        });

        return rootView;
    }

    public Bitmap decodeImage(String imageFile){
        Bitmap bitmap = null;
        byte[] imageAsBytes = Base64.decode(imageFile, Base64.DEFAULT);
        bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);

        return bitmap;
    }

    public void displayPictureDialog(Spares spares){

        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(),R.style.MyDialogTheme);

        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_picture_layout,null);
        ImageView imageView = (ImageView)dialogView.findViewById(R.id.dialog_image);

        imageView.setImageBitmap(decodeImage(spares.getPic()));

        dialog.setView(dialogView);
        dialog.setTitle("SPARES");

        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = dialog.create();
        alertDialog.show();

    }

    public void writeToSharedPreference(int value){

        //add value to SharedPreferences local storage
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("NUMBER OF ITEMS",value);
        editor.commit();

    }

    public int readFromSharedPreference(){
        int value = 0;

        //read value stored in SharedPreferences local storage

        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        value = sharedPreferences.getInt("NUMBER OF ITEMS",0);

        return value;
    }

}
