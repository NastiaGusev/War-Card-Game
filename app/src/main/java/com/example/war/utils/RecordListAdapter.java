package com.example.war.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.war.R;
import com.example.war.objects.Record;

import java.util.ArrayList;

public class RecordListAdapter extends ArrayAdapter<Record> {

    private static final String TAG = "RecordListAdapter";
    private Context context;
    private int resource;
    private int lastPosition = -1;
    private  Record record;
    private View result;
    private ViewHolder holder;

    private static class ViewHolder{
        TextView listView_TXT_name;
        ImageView listView_TXT_icon;
        TextView listView_TXT_score;
        TextView listView_TXT_date;
    }

    public RecordListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Record> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        String name = getItem(position).getName();
        int id = getItem(position).getPlayerId();
        int score = getItem(position).getScore();
        String date = getItem(position).getDate();
        record = new Record(name, id, score, date);

        if (convertView == null){
            LayoutInflater inflater= LayoutInflater.from(context);
            convertView = inflater.inflate(resource, parent, false);
            holder = new ViewHolder();
            findViews(convertView, holder);
            result = convertView;
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        //Animation for loading one item at a time
        Animation animation = AnimationUtils.loadAnimation(context,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition= position;

        setViews(holder);
        return convertView;
    }

    private void findViews(View convertView, ViewHolder holder){
        holder.listView_TXT_name = convertView.findViewById(R.id.listView_TXT_name);
        holder.listView_TXT_icon = convertView.findViewById(R.id.listView_TXT_icon);
        holder.listView_TXT_score = convertView.findViewById(R.id.listView_TXT_score);
        holder.listView_TXT_date = convertView.findViewById(R.id.listView_TXT_date);
    }

    private void setViews(ViewHolder holder){
        holder.listView_TXT_icon.setImageResource(context.getResources().getIdentifier("drawable/img_player" + record.getPlayerId(), "drawable", context.getPackageName()));
        holder.listView_TXT_name.setText(record.getName());
        holder.listView_TXT_score.setText("" + record.getScore());
        holder.listView_TXT_date.setText(record.getDate());
    }
}
