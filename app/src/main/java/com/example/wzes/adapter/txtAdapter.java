package com.example.wzes.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wzes.secretmanagement.R;
import com.example.wzes.secretmanagement.TxtActivity;
import com.example.wzes.secretmanagement.TxtEditActivity;
import com.example.wzes.util.AESHelper;
import com.example.wzes.util.txtData;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by WZES on 2017/2/22.
 */

public class txtAdapter extends RecyclerView.Adapter<txtAdapter.ViewHolder>{
    private Context myContext;
    private List<txtData> myTxtList;
    private String masterPassword = "123456";

    @Override
    public txtAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(myContext == null){
            myContext = parent.getContext();
        }
        View view = LayoutInflater.from(myContext).inflate(R.layout.txt_item,
                parent, false);
        final txtAdapter.ViewHolder holder = new txtAdapter.ViewHolder(view);


        holder.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Intent intent = new Intent(myContext, TxtEditActivity.class);
                intent.putExtra("contentItem", myTxtList.get(position).getContent());
                myContext.startActivity(intent);
            }
        });
        holder.deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final int position = holder.getAdapterPosition();
                txtData myTxtData = myTxtList.get(position);
                final String content = myTxtData.getContent();
                AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
                builder.setTitle("确定删除");
                builder.setPositiveButton("删除",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                DataSupport.deleteAll(txtData.class, "content = ?", AESHelper.encrypt(content, masterPassword));
                                ((TxtActivity)myContext).showUpdate();
                                Toast.makeText(myContext, "删除成功 ", Toast.LENGTH_LONG).show();

                            }
                        });
                builder.setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.create();
                builder.show();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(txtAdapter.ViewHolder holder, int position) {
        txtData myTxtData = myTxtList.get(position);
        holder.contentView.setText(myTxtData.getTitle());
        holder.dateView.setText(myTxtData.getData());
    }

    @Override
    public int getItemCount() {
        return myTxtList.size();
    }
    static class ViewHolder extends RecyclerView.ViewHolder{
        public CardView cardView;
        public TextView contentView;
        public TextView dateView;
        public Button editBtn;
        public Button deleteBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.txtCardView);
            contentView = (TextView) itemView.findViewById(R.id.txtContentTxt);
            dateView = (TextView) itemView.findViewById(R.id.txtDateTxt);
            editBtn = (Button) itemView.findViewById(R.id.txtEditBtn);
            deleteBtn = (Button) itemView.findViewById(R.id.txtDeleteBtn);
        }

    }
    public txtAdapter(List<txtData> txtList){
        myTxtList = txtList;
    }
}
