package com.app.mypresence.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.mypresence.R;
import com.app.mypresence.model.database.user.User;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private List<UserCard> userCardList;
    private ClickListener<UserCard> clickListener;

    public RecyclerViewAdapter(List<UserCard> userCardList){
        this.userCardList = userCardList;
    }
    @Override
    public RecyclerViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_adapter_layout,parent,false);
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(RecyclerViewAdapter.MyViewHolder holder, final int position) {
        final UserCard userCard = userCardList.get(position);
        holder.nameandsurname.setText(userCard.getName() + " " + userCard.getSurname());
        //holder.image.setImageURI();
        holder.image.setImageDrawable(userCard.getImage());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(userCard);
            }
        });


    }
    @Override
    public int getItemCount() {
        return userCardList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView nameandsurname;
        private ImageView image;
        private CardView cardView;

        public MyViewHolder(View itemView) {
            super(itemView);
            nameandsurname = itemView.findViewById(R.id.nameandsurname);
            image = itemView.findViewById(R.id.image);
            cardView = itemView.findViewById(R.id.carView);
        }
    }

    public void setOnItemClickListener(ClickListener<UserCard> movieClickListener) {
        this.clickListener = movieClickListener;
    }
}