package com.example.wzes.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wzes.secretmanagement.MemoActivity;
import com.example.wzes.secretmanagement.R;
import com.example.wzes.secretmanagement.SecretSeeActivity;
import com.example.wzes.secretmanagement.TxtActivity;
import com.example.wzes.util.memoData;
import com.example.wzes.util.secretData;
import com.example.wzes.util.txtData;

import org.litepal.crud.DataSupport;

import java.util.List;
import java.util.StringTokenizer;

import static java.lang.Math.round;

public class memoAdapter extends RecyclerView.Adapter<memoAdapter.ViewHolder> {


    private Context myContext;
    private List<memoData> myMemoList;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {

        if(myContext == null){
            myContext = parent.getContext();
        }
        View view = LayoutInflater.from(myContext).inflate(R.layout.memo_item,
                parent, false);
        final ViewHolder holder = new ViewHolder(view);

        holder.memoDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
                builder.setTitle("确定删除");
                builder.setPositiveButton("删除",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int i) {
                                int position = holder.getAdapterPosition();
                                DataSupport.deleteAll(memoData.class, "context = ?",
                                        String.valueOf(myMemoList.get(position).getContext()));
                                ((MemoActivity)myContext).initData();

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
        holder.secretfinishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                myMemoList.get(position).setFinish("true");
                myMemoList.get(position).save();     //更新

                ((MemoActivity)myContext).initUnData();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        memoData memo = myMemoList.get(position);

        holder.ratingbar.setRating(round(memo.getPriority()));
        holder.memoDate.setText("截止时间："+memo.getDate());
        holder.memoContext.setText(memo.getContext());
    }

    @Override
    public int getItemCount()    {
        return myMemoList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        RatingBar ratingbar;
        TextView memoDate;
        TextView memoContext;
        Button memoDeleteBtn;
        Button secretfinishBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            ratingbar = (RatingBar) itemView.findViewById(R.id.memoStarNumber);

            memoDate = (TextView) itemView.findViewById(R.id.memoDate);
            memoContext = (TextView) itemView.findViewById(R.id.memoContext);

            memoDeleteBtn = (Button) itemView.findViewById(R.id.memoDelete);
            secretfinishBtn = (Button) itemView.findViewById(R.id.memoFinish);

        }
    }

    public memoAdapter(List<memoData> memoList){
        myMemoList = memoList;
    }


}