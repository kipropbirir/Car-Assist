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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.driver_profile_list_layout,parent,false);

        TextView carName = (TextView)rowView.findViewById(R.id.driver_car_name);
        TextView carBodyName = (TextView)rowView.findViewById(R.id.driver_car_body_name);
        TextView carMileage = (TextView)rowView.findViewById(R.id.driver_car_mileage);
        TextView carDescription = (TextView)rowView.findViewById(R.id.driver_car_description);
        ImageView carBodyImage = (ImageView)rowView.findViewById(R.id.driver_car_body_image);
        View carBodyPaint = rowView.findViewById(R.id.driver_car_body_paint);

        carName.setText(items.get(position).getCarModel());
        carBodyName.setText(items.get(position).getBodyType());
        carMileage.setText(items.get(position).getMileage()+" km");
        carDescription.setText(items.get(position).getDescription());
        //carBodyPaint.setBackgroundColor(Color.parseColor(items.get(position).getBodyPaint()));

        String bodyType = items.get(position).getBodyType();

        switch(bodyType){
            case "Campvan":
                carBodyImage.setImageResource(R.drawable.ic_campvan);
                break;
            case "Convertible":
                carBodyImage.setImageResource(R.drawable.ic_convertible);
                break;
            case "Sports Utility Vehicle":
                carBodyImage.setImageResource(R.drawable.ic_suv);
                break;
            case "Sedan":
                carBodyImage.setImageResource(R.drawable.ic_sedan);
                break;
            case "Shuttle":
                carBodyImage.setImageResource(R.drawable.ic_shuttle);
                break;
            default:
                break;
        }

        return rowView;
    }
}
