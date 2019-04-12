package com.example.whats;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class GroupChatActivityAdapter extends RecyclerView.Adapter<GroupChatActivityAdapter.ViewHolder> {
    List<messageModel> messageModels;
    Context context;

    public GroupChatActivityAdapter(List<messageModel> messageModels, Context context) {
        this.messageModels = messageModels;
        this.context = context;
    }

    @NonNull
    @Override
    public GroupChatActivityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row1_item,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupChatActivityAdapter.ViewHolder viewHolder, int i) {
        viewHolder.message.setText(messageModels.get(i).getMessage());
        viewHolder.name.setText(messageModels.get(i).getName());

    }

    @Override
    public int getItemCount() {
        if (messageModels==null){
            return 0;
        }
        return messageModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView message,name;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            message=itemView.findViewById(R.id.message_Group_message);
            name=itemView.findViewById(R.id.message_Group_name);
        }
    }
}
