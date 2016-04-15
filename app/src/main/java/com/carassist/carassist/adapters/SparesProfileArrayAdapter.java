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

        super(context, R.layout.spares_profile_list_layout, items);
        this.context = context;
        this.items = items;

    }

    static class ViewHolder{

        public TextView sparesName;
        public TextView sparesDescription;
        public TextView sparesPrice;
        public TextView sparesLocation;
        public CircleImageView sparesImage;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        //reuse view
        if(rowView == null){

            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.spares_profile_list_layout,parent,false);

            //configure view holder
            ViewHolder viewHolder = new ViewHolder();

            viewHolder.sparesName = (TextView)rowView.findViewById(R.id.spares_product_name);
            viewHolder.sparesDescription = (TextView)rowView.findViewById(R.id.spares_product_description);
            viewHolder.sparesPrice = (TextView)rowView.findViewById(R.id.spares_product_price);
            viewHolder.sparesLocation = (TextView)rowView.findViewById(R.id.spares_product_location);
            viewHolder.sparesImage = (CircleImageView)rowView.findViewById(R.id.spares_image);
            rowView.setTag(viewHolder);

        }

        ViewHolder holder = (ViewHolder)rowView.getTag();

        holder.sparesName.setText(items.get(position).getName());
        holder.sparesDescription.setText(items.get(position).getDescription());
        holder.sparesPrice.setText("ksh." + items.get(position).getPrice());
        holder.sparesLocation.setText(items.get(position).getLocation());
        //checks if image string is empty
        if(!(items.get(position).getPic().equalsIgnoreCase(""))) {
            holder.sparesImage.setImageBitmap(decodeImage(items.get(position).getPic()));
        }

        return rowView;
    }

    public Bitmap decodeImage(String imageFile){
        Bitmap bitmap = null;
        byte[] imageAsBytes = Base64.decode(imageFile, Base64.DEFAULT);
        bitmap = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);

        return bitmap;
    }

}
