package com.carassist.carassist.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.carassist.carassist.R;
import com.carassist.carassist.data.Cars;

import java.util.ArrayList;

/**
 * Created by user on 3/16/2016.
 */
public class DriverProfileArrayAdapter extends ArrayAdapter<Cars> {

    Context context;
    ArrayList<Cars> items;

    public DriverProfileArrayAdapter(Context context,ArrayList<Cars> items){
        super(context, R.layout.driver_profile_list_layout,items);
        this.context = context;
        this.items = items;

    }

    static class ViewHolder{

        public TextView carName;
        public TextView carBodyName;
        public TextView carMileage;
        public TextView carDescription;
        public ImageView carBodyImage;
        public View carBodyPaint;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        //reuse views
        if(rowView == null){

            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.driver_profile_list_layout,parent,false);

            //configure view holder
            ViewHolder viewHolder = new ViewHolder();

            viewHolder.carName = (TextView)rowView.findViewById(R.id.driver_car_name);
            viewHolder.carBodyName = (TextView)rowView.findViewById(R.id.driver_car_body_name);
            viewHolder.carMileage = (TextView)rowView.findViewById(R.id.driver_car_mileage);
            viewHolder.carDescription = (TextView)rowView.findViewById(R.id.driver_car_description);
            viewHolder.carBodyImage = (ImageView)rowView.findViewById(R.id.driver_car_body_image);
            viewHolder.carBodyPaint = rowView.findViewById(R.id.driver_car_body_paint);
            rowView.setTag(viewHolder);

        }

        //fill data
        ViewHolder holder = (ViewHolder)rowView.getTag();

        holder.carName.setText(items.get(position).getCarModel());
        holder.carBodyName.setText(items.get(position).getBodyType());
        holder.carMileage.setText(items.get(position).getMileage()+" km");
        holder.carDescription.setText(items.get(position).getDescription());
        holder.carBodyPaint.setBackgroundColor(Color.parseColor(items.get(position).getBodyPaint()));

        String bodyType = items.get(position).getBodyType();

        switch(bodyType){
            case "Campvan":
                holder.carBodyImage.setImageResource(R.drawable.ic_campvan);
                break;
            case "Convertible":
                holder.carBodyImage.setImageResource(R.drawable.ic_convertible);
                break;
            case "Sports Utility Vehicle":
                holder.carBodyImage.setImageResource(R.drawable.ic_suv);
                break;
            case "Sedan":
                holder.carBodyImage.setImageResource(R.drawable.ic_sedan);
                break;
            case "Shuttle":
                holder.carBodyImage.setImageResource(R.drawable.ic_shuttle);
                break;
            default:
                break;
        }

        return rowView;
    }
}
