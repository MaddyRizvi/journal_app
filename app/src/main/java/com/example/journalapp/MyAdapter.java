package com.example.journalapp;


import android.content.Context;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.List;

//4 - Creating adapter for recycler view
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    // Variables
    private Context context;
    private List<Journal> journalList;

    public MyAdapter(Context context, List<Journal> journalList) {
        this.context = context;
        this.journalList = journalList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.journal_row, parent, false);
            return new MyViewHolder(view);
    }

    // Setting the data to the views in the ViewHolder at specific position
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Journal currentJournal = journalList.get(position);

        holder.title.setText(currentJournal.getTitle());
        holder.thoughts.setText(currentJournal.getThoughts());
        holder.userName.setText(currentJournal.getUserName());

        String imageURL = currentJournal.getImageURL();
        String timeAgo = (String)DateUtils.getRelativeTimeSpanString(
                currentJournal.getTimeAdded().getSeconds()*1000
        );

        holder.dateadded.setText(timeAgo);

        // Glide Library to display the image
        Glide.with(context)
                .load(imageURL)
                .fitCenter()
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        // it helps recycler i.e how many items in the dataset to be displayed
        return journalList.size();
    }

    // View Holder Class
    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView title, thoughts, dateadded, userName;
        public ImageView image, shareButton;
        public String userId, username;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.journal_title_list);
            thoughts = itemView.findViewById(R.id.journal_thought_list);
            dateadded = itemView.findViewById(R.id.journal_timestamp_list);

            image = itemView.findViewById(R.id.journal_image_list);
            userName = itemView.findViewById(R.id.journal_row_username);

            shareButton = itemView.findViewById(R.id.journal_row_share_button);

            shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Sharing the post here ...
                }
            });
        }
    }
}
