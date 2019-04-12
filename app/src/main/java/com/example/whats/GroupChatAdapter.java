package com.example.whats;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class GroupChatAdapter extends RecyclerView.Adapter<GroupChatAdapter.ViewHolder> {

    List<String> strings;
    Context context;
    OnItemClickListener onItemClickListener;


    public void setOnitemClickListener(OnItemClickListener onitemClickListener) {
        this.onItemClickListener = onitemClickListener;
    }

    public GroupChatAdapter(List<String>strings,Context context){

        this.strings=strings;
        this.context=context;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_item,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        viewHolder.textView.setText(strings.get(i));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,GroupChatActivity.class);
                intent.putExtra("groupName",strings.get(i));
                context.startActivity(intent);
               //onItemClickListener.onClick(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(strings==null){

            return 0;
        }

        return strings.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
textView=itemView.findViewById(R.id.row_item_for_group_chat);

        }
    }

    public interface OnItemClickListener {
        void onClick(int i);
    }

}
