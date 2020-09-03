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

import java.util.List;

public class LearningboardRecyclerAdapter extends RecyclerView.Adapter<LearningboardRecyclerAdapter.LearningboardViewHolder> {

    private Context mContext;
    private List<PlaceholderFragment.LeaderboardItem> mList;
    private LayoutInflater mInflater;

    public LearningboardRecyclerAdapter(Context context, List<PlaceholderFragment.LeaderboardItem> list) {
        mContext = context;
        mList = list;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public LearningboardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.learning_item_layout,parent,false);
        return new LearningboardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LearningboardViewHolder holder, int position) {
        holder.mImageView.setImageResource(mList.get(position).image);
        holder.mLearnerName.setText(mList.get(position).name);
        holder.mLearnerDetails.setText(mList.get(position).details);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class LearningboardViewHolder extends RecyclerView.ViewHolder{

        private ImageView mImageView;
        private TextView mLearnerName;
        private TextView mLearnerDetails;

        public LearningboardViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.learning_image);
            mLearnerName = itemView.findViewById(R.id.learning_name);
            mLearnerDetails = itemView.findViewById(R.id.learning_details);
        }
    }

}
