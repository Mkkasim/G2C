package com.mk.g2c;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RecycChecksAdapter extends RecyclerView.Adapter<RecycChecksAdapter.CheckViewHolder>{

    private static final String TAG = RecycChecksAdapter.class.getSimpleName();

    Context context;
    ArrayList<ChecksModel> checksModels;
    OnItemClickListener onItemClickListener;


    public RecycChecksAdapter(Context context, ArrayList<ChecksModel> checksModels, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.checksModels = checksModels;
        this.onItemClickListener = onItemClickListener;
    }


    @NonNull
    @Override
    public CheckViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.recyc_list_item,parent,false);
        return new CheckViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckViewHolder holder, int position) {

        ChecksModel list = checksModels.get(position);
        holder.checksName.setText(list.getChecksName());



        holder.itemView.setOnLongClickListener(view -> {
            onItemClickListener.onItemClick(checksModels.get(position));
            return true;
        });

    }

    @Override
    public int getItemCount() {
        return checksModels.size();
    }




    public interface OnItemClickListener{
        void onItemClick(ChecksModel model);
    }


    public class CheckViewHolder extends RecyclerView.ViewHolder {

        TextView checksName;

        public CheckViewHolder(@NonNull View itemView) {
            super(itemView);

            checksName = itemView.findViewById(R.id.checksText);


        }
    }
}
