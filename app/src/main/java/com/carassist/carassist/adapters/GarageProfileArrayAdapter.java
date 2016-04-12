package com.carassist.carassist.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.carassist.carassist.R;
import com.carassist.carassist.data.Garage;

import java.util.ArrayList;

/**
 * Created by user on 3/16/2016.
 */
public class GarageProfileArrayAdapter extends ArrayAdapter<Garage> {

    Context context;
    ArrayList<Garage> items;

    public GarageProfileArrayAdapter(Context context,ArrayList<Garage> items){

        super(context, R.layout.garage_profile_list_layout, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.garage_profile_list_layout,parent,false);

        TextView garageName = (TextView)rowView.findViewById(R.id.garage_name);
        TextView garageDescription = (TextView)rowView.findViewById(R.id.garage_description);
        TextView garageLocation = (TextView)rowView.findViewById(R.id.garage_location);

        garageName.setText(items.get(position).getName());
        garageDescription.setText(items.get(position).getDescription());
        garageLocation.setText(items.get(position).getLocation());

        return rowView;
    }
}
