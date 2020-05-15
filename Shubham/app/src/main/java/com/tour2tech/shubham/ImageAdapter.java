package com.tour2tech.shubham;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageViewHolder>{

    private Context mContext;
    private List<Imagedata> myImageList;

    public ImageAdapter(Context mContext, List<Imagedata> myImageList) {
        this.mContext = mContext;
        this.myImageList = myImageList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View mview = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_row_item_project,parent,false);
        return new ImageViewHolder(mview);

    }

    @Override
    public void onBindViewHolder(@NonNull final ImageViewHolder holder, int i) {

        Glide.with(mContext)
                .load(myImageList.get(i).getImage())
                .into(holder.proj_image);

        holder.proj_name.setText(myImageList.get(i).getIname());


    }

    @Override
    public int getItemCount() {
        return myImageList.size();
    }


}

class ImageViewHolder extends RecyclerView.ViewHolder{

    TextView proj_name;
    CardView mCardView;
    ImageView proj_image;


    public ImageViewHolder(View itemView) {
        super(itemView);
        proj_image = itemView.findViewById(R.id.proj_image);
        proj_name = itemView.findViewById(R.id.proj_title);
        mCardView = itemView.findViewById(R.id.myCardView);

    }
}
