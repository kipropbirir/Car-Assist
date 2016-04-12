package com.carassist.carassist.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.carassist.carassist.R;
import com.carassist.carassist.data.Spares;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import me.gujun.android.taggroup.TagGroup;

/**
 * Created by user on 3/17/2016.
 */
public class SparesArrayAdapter extends ArrayAdapter<Spares> {
    Context context;
    ArrayList<Spares> items;

    public SparesArrayAdapter(Context context, ArrayList<Spares> items){

        super(context, R.layout.spares_list_layout,items);
        this.context = context;
        this.items = items;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.spares_list_layout,parent,false);

        CircleImageView sparesImage = (CircleImageView)rowView.findViewById(R.id.spares_image);
        TextView name= (TextView)rowView.findViewById(R.id.spares_product_name);
        TextView description = (TextView)rowView.findViewById(R.id.spares_product_description);
        TextView price = (TextView)rowView.findViewById(R.id.spares_product_price);
        TextView location = (TextView)rowView.findViewById(R.id.spares_product_location);
        TagGroup tagGroup = (TagGroup)rowView.findViewById(R.id.spares_tag);

        name.setText(items.get(position).getName());
        description.setText(items.get(position).getDescription());
        price.setText("ksh."+items.get(position).getPrice());
        location.setText(items.get(position).getLocation());
        sparesImage.setImageBitmap(decodeImage(items.get(position).getPic()));
        tagGroup.setTags(new String[]{"NEW"});
        //tagGroup.setVisibility(View.GONE);

        return rowView;
    }

    public Bitmap decodeImage(String imageFile){
        Bitmap bitmap = null;
        byte[] imageAsBytes = Base64.decode(imageFile,Base64.DEFAULT);
        bitmap = BitmapFactory.decodeByteArray(imageAsBytes,0,imageAsBytes.length);

        return bitmap;
    }

}
