package com.hyphenate.easeui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.constants.EaseCommom;

import java.util.List;

/**
 * author ： xiaogf
 * time    ： 2023/4/7
 * describe    ：
 */
public class MyEaseMessageAdapter extends EaseMessageAdapter {
    private  boolean isVis = false;

    public void setVis(boolean isVis){
        this.isVis=isVis;
    }
    public boolean getVis(){
        return isVis;
    }
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
           return super.onCreateViewHolder(parent, viewType);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        ConstraintLayout header_text_view =holder.itemView.findViewById(R.id.header_text_view);
        if (header_text_view!=null){
            if (position==0&&isVis){
                header_text_view.setVisibility(View.VISIBLE);
            }else {
                header_text_view.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position);
        ConstraintLayout header_text_view =holder.itemView.findViewById(R.id.header_text_view);
        RelativeLayout rlProjectTitle =holder.itemView.findViewById(R.id.rlProjectTitle);
        RelativeLayout rlInvestorTitle =holder.itemView.findViewById(R.id.rlInvestorTitle);
        if (header_text_view!=null){
            if (position==0&&isVis){
                header_text_view.setVisibility(View.VISIBLE);
                if (EaseCommom.getInstance().isProject()){
                    rlProjectTitle.setVisibility(View.GONE);
                    rlInvestorTitle.setVisibility(View.VISIBLE);
                }else {
                    rlProjectTitle.setVisibility(View.VISIBLE);
                    rlInvestorTitle.setVisibility(View.GONE);
                }
            }else {
                header_text_view.setVisibility(View.GONE);
            }
        }

    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }
}

