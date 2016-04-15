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

    static class ViewHolder{

        public CircleImageView sparesImage;
        public TextView name;
        public TextView description;
        public TextView price;
        public TextView location;
        public TagGroup tagGroup;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        //reuse views
        if(rowView == null){

            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.spares_list_layout,parent,false);

            //configure view holder
            ViewHolder viewHolder = new ViewHolder();

            viewHolder.sparesImage = (CircleImageView)rowView.findViewById(R.id.spares_image);
            viewHolder.name= (TextView)rowView.findViewById(R.id.spares_product_name);
            viewHolder.description = (TextView)rowView.findViewById(R.id.spares_product_description);
            viewHolder.price = (TextView)rowView.findViewById(R.id.spares_product_price);
            viewHolder.location = (TextView)rowView.findViewById(R.id.spares_product_location);
            viewHolder.tagGroup = (TagGroup)rowView.findViewById(R.id.spares_tag);
            rowView.setTag(viewHolder);

        }

        //fill data
        ViewHolder holder = (ViewHolder)rowView.getTag();

        holder.name.setText(items.get(position).getName());
        holder.description.setText(items.get(position).getDescription());
        holder.price.setText("ksh."+items.get(position).getPrice());
        holder.location.setText(items.get(position).getLocation());
        //checks if image string is empty
        if(!(items.get(position).getPic().equalsIgnoreCase(""))) {
            holder.sparesImage.setImageBitmap(decodeImage(items.get(position).getPic()));
        }
        holder.tagGroup.setTags(new String[]{"NEW"});
        holder.tagGroup.setVisibility(View.GONE);

        return rowView;
    }

    public Bitmap decodeImage(String imageFile){
        Bitmap bitmap = null;
        byte[] imageAsBytes = Base64.decode(imageFile,Base64.DEFAULT);
        bitmap = BitmapFactory.decodeByteArray(imageAsBytes,0,imageAsBytes.length);

        return bitmap;
    }

}
