package com.example.projectapp.ui.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectapp.R;

import java.util.ArrayList;
import java.util.List;

public class SkilIQRecyclerAdapter extends RecyclerView.Adapter<SkilIQRecyclerAdapter.SkillIQViewHolder> {
    private Context mContext;
    private List<PlaceholderFragment.LeaderboardItem> mList;
    private LayoutInflater mInflater;

    public SkilIQRecyclerAdapter(Context context, List<PlaceholderFragment.LeaderboardItem> list) {
        mContext = context;
        mList = list;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public SkillIQViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.skill_iq_leaders_item_layout,parent,false);
        return new SkillIQViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SkillIQViewHolder holder, int position) {
        holder.mImageView.setImageResource(mList.get(position).image);
        holder.mSkillName.setText(mList.get(position).name);
        holder.mSkillDetails.setText(mList.get(position).details);
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class SkillIQViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageView;
        private TextView mSkillName;
        private TextView mSkillDetails;

        public SkillIQViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.skill_image);
            mSkillName = itemView.findViewById(R.id.skill_name);
            mSkillDetails = itemView.findViewById(R.id.skill_details);


        }
    }

}
