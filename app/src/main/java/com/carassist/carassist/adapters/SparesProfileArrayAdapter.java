package com.carassist.carassist.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.carassist.carassist.R;
import com.carassist.carassist.data.Spares;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by user on 3/16/2016.
 */
public class SparesProfileArrayAdapter extends ArrayAdapter<Spares> {

    Context context;
    ArrayList<Spares> items;

    public SparesProfileArrayAdapter(Context context, ArrayList<Spares> items){

        super(context,R.layout.spares_profile_list_layout,items);
        this.context = context;
        this.items = items;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.spares_profile_list_layout,parent,false);

        TextView sparesName = (TextView)rowView.findViewById(R.id.spares_product_name);
        TextView sparesDescription = (TextView)rowView.findViewById(R.id.spares_product_description);
        TextView sparesPrice = (TextView)rowView.findViewById(R.id.spares_product_price);
        TextView sparesLocation = (TextView)rowView.findViewById(R.id.spares_product_location);
        CircleImageView sparesImage = (CircleImageView)rowView.findViewById(R.id.spares_image);

        sparesName.setText(items.get(position).getName());
        sparesDescription.setText(items.get(position).getDescription());
        sparesPrice.setText(items.get(position).getPrice());
        sparesLocation.setText(items.get(position).getLocation());
        sparesImage.setImageBitmap(decodeImage(items.get(position).getPic()));

        return rowView;
    }

    public Bitmap decodeImage(String imageFile){
        Bitmap bitmap = null;
        byte[] imageAsBytes = Base64.decode(imageFile, Base64.DEFAULT);
        bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);

        return bitmap;
    }

}
