package com.example.tinderapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import chat.ChatActivity;

public class MatchesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView mMatchId,mMatchname;
    public ImageView mMatchImage;
    public MatchesViewHolder(@NonNull View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        mMatchId=(TextView)itemView.findViewById(R.id.Matchid);
        mMatchname=(TextView)itemView.findViewById(R.id.MatchName);
        mMatchImage=(ImageView)itemView.findViewById(R.id.MatchImage);
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent(v.getContext(), ChatActivity.class);
        Bundle b=new Bundle();
        b.putString("matchId",mMatchId.getText().toString());
        intent.putExtras(b);
        v.getContext().startActivity(intent);

    }
}
