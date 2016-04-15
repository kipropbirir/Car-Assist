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

import me.gujun.android.taggroup.TagGroup;

/**
 * Created by user on 3/17/2016.
 */
public class GarageArrayAdapter extends ArrayAdapter<Garage> {
    Context context;
    ArrayList<Garage> items;

    public GarageArrayAdapter(Context context,ArrayList<Garage> items){

        super(context, R.layout.garage_list_layout, items);
        this.context = context;
        this.items = items;
    }

    static class ViewHolder{

        public TextView garageName;
        public TextView garageDescription;
        public TextView garageLocation;
        public TagGroup tagGroup;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View rowView = convertView;

        //reuse views
        if(rowView == null){

            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.garage_list_layout,parent,false);

            //configure view holder
            ViewHolder viewHolder = new ViewHolder();

            viewHolder.garageName = (TextView)rowView.findViewById(R.id.garage_name);
            viewHolder.garageDescription = (TextView)rowView.findViewById(R.id.garage_description);
            viewHolder.garageLocation = (TextView)rowView.findViewById(R.id.garage_location);
            viewHolder.tagGroup = (TagGroup)rowView.findViewById(R.id.garage_tag);
            rowView.setTag(viewHolder);

        }

        //fill data
        ViewHolder holder = (ViewHolder) rowView.getTag();

        holder.garageName.setText(items.get(position).getName());
        holder.garageDescription.setText(items.get(position).getDescription());
        holder.garageLocation.setText(items.get(position).getLocation());
        holder.tagGroup.setTags(new String[]{"NEW"});

        return rowView;
    }
}
