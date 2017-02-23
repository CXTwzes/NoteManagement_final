package com.example.wzes.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wzes.secretmanagement.R;
import com.example.wzes.secretmanagement.SecretSeeActivity;
import com.example.wzes.util.secretData;

import java.util.List;

public class secretAdapter extends RecyclerView.Adapter<secretAdapter.ViewHolder> {

    private int[] drawables = {R.drawable.usual, R.drawable.qq, R.drawable.wx , R.drawable.wb ,R.drawable.zfb,
            R.drawable.tc, R.drawable.ws , R.drawable.bdnm ,R.drawable.mm,
            R.drawable.bdnm, R.drawable.wph , R.drawable.aqy ,R.drawable.xc,
            R.drawable.qqyy, R.drawable.mp , R.drawable.aqy ,R.drawable.tb,
            R.drawable.kg, R.drawable.td ,R.drawable.qqyx};

    private Context myContext;
    private List<secretData> mySecretList;
    private boolean SEE = false;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {

        if(myContext == null){
            myContext = parent.getContext();
        }
        View view = LayoutInflater.from(myContext).inflate(R.layout.add_item,
                parent, false);
        final ViewHolder holder = new ViewHolder(view);

        holder.secretEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Intent intent = new Intent(myContext, SecretSeeActivity.class);
                intent.putExtra("Title",mySecretList.get(position).getTitle());
                myContext.startActivity(intent);

            }
        });
        holder.secretSeeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(SEE){
                    SEE = false;
                    int position = holder.getAdapterPosition();
                    holder.secretPassword.setText(mySecretList.get(position).getPassword());
                }else {
                    SEE = true;
                    holder.secretPassword.setText("************");
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        secretData secret = mySecretList.get(position);
        holder.secretImage.setImageResource(drawables[Integer.parseInt(secret.getType())]);
        holder.secretName.setText(secret.getTitle());
        holder.secretUsername.setText(secret.getUsername());
        holder.secretPassword.setText("************");
    }

    @Override
    public int getItemCount()    {
        return mySecretList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView secretImage;
        TextView secretName;
        TextView secretUsername;
        TextView secretPassword;
        ImageButton secretEditBtn;
        ImageButton secretSeeBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            secretImage = (ImageView) itemView.findViewById(R.id.addItemImg);
            secretName = (TextView) itemView.findViewById(R.id.addItemTitleTxt);
            secretUsername = (TextView) itemView.findViewById(R.id.addItemUsernameTxt);
            secretPassword = (TextView) itemView.findViewById(R.id.addItemPasswordTxt);
            secretEditBtn = (ImageButton) itemView.findViewById(R.id.addItemEdit);
            secretSeeBtn = (ImageButton) itemView.findViewById(R.id.seePassword);
        }
    }

    public secretAdapter(List<secretData> secretList){
        mySecretList = secretList;
    }


}