package com.carassist.carassist.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
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

import de.hdodenhof.circleimageview.CircleImageView;

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
        Query queryRef = sparesRef.orderByChild("uniqueId").equalTo(mRef.getAuth().getUid());

        queryRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //remove all items in the ArrayList
                items.clear();

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

        listView.setAdapter(sparesProfileArrayAdapter);

        linearLayout.setVisibility(View.GONE);

        //add footer to the listView
        LayoutInflater layoutInflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View footerView = layoutInflater.inflate(R.layout.listview_footer,null);

        listView.addFooterView(footerView);

        //listen to listView clicks/events
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                final Firebase deleteSparesRef = mRef.child("spares").child(items.get(position).getPushId());

                ImageView delete = (ImageView)view.findViewById(R.id.spares_delete);
                ImageView edit = (ImageView)view.findViewById(R.id.spares_edit);
                CircleImageView circleImageView = (CircleImageView)view.findViewById(R.id.spares_image);

                circleImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        displayPictureDialog(items.get(position));
                    }
                });

                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        deleteSparesRef.setValue(null, new Firebase.CompletionListener() {
                            @Override
                            public void onComplete(FirebaseError firebaseError, Firebase firebase) {


                                if(firebaseError == null){
                                    //item deleted successfully
                                    Snackbar.make(v,"item has been deleted",Snackbar.LENGTH_SHORT).show();
                                    sparesProfileArrayAdapter.notifyDataSetChanged();

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

}
