package com.hyphenate.easeui.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hyphenate.chat.EMMessage;
import com.hyphenate.easeui.R;
import com.hyphenate.easeui.constants.EaseCommom;
import com.hyphenate.easeui.constants.UserMessage;
import com.hyphenate.easeui.modules.chat.model.EaseChatItemStyleHelper;
import com.hyphenate.easeui.viewholder.EaseChatRowViewHolder;
import com.hyphenate.easeui.widget.EaseImageView;

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

        EaseImageView iv_userhead =holder.itemView.findViewById(R.id.iv_userhead);//对话框头像


        UserMessage userMessage = EaseCommom.getInstance().getUserMessage();
        if (iv_userhead!=null){
            Glide.with(mContext).load(userMessage.getLogo()).placeholder(R.drawable.icon_base)
                    .dontAnimate()
                    .error(R.drawable.icon_base)
                    .into(iv_userhead);
        }

        if (header_text_view!=null){
            if (position==0&&isVis){
                header_text_view.setVisibility(View.VISIBLE);
                TextView tvInvestorName =holder.itemView.findViewById(R.id.tvInvestorName);
                TextView tvInvestorCompany =holder.itemView.findViewById(R.id.tvInvestorCompany);
                EaseImageView iv_userHeadInvestor =holder.itemView.findViewById(R.id.iv_userHeadInvestor);

                TextView tvName =holder.itemView.findViewById(R.id.tvName);
                TextView tvCompany =holder.itemView.findViewById(R.id.tvCompany);
                TextView tvAddress =holder.itemView.findViewById(R.id.tvAddress);
                TextView tvCase =holder.itemView.findViewById(R.id.tvCase);
                EaseImageView iv_userHeadProject =holder.itemView.findViewById(R.id.iv_userHeadProject);

                RelativeLayout rlProjectTitle =holder.itemView.findViewById(R.id.rlProjectTitle);
                RelativeLayout rlInvestorTitle =holder.itemView.findViewById(R.id.rlInvestorTitle);
                if (EaseCommom.getInstance().isProject()){
                    rlProjectTitle.setVisibility(View.GONE);
                    rlInvestorTitle.setVisibility(View.VISIBLE);
                }else {
                    rlProjectTitle.setVisibility(View.VISIBLE);
                    rlInvestorTitle.setVisibility(View.GONE);
                }
                if (userMessage==null) return;
                if (EaseCommom.getInstance().isProject()){
                    tvInvestorName.setText(userMessage.getName());
                    tvInvestorCompany.setText(userMessage.getPosition()+"  |  "+userMessage.getFinancing());
                    Glide.with(mContext).load(userMessage.getLogo()).placeholder(R.drawable.icon_base)
                            .dontAnimate()
                            .error(R.drawable.icon_base)
                            .into(iv_userHeadInvestor);

                }else {
                    tvName.setText(userMessage.getName());
                    tvCompany.setText(userMessage.getTrade()+"  |  "+userMessage.getFinancing());
                    tvAddress.setText(userMessage.getAddress());
                    tvCase.setText(userMessage.getDescribe());
                    Glide.with(mContext).load(userMessage.getLogo()).placeholder(R.drawable.icon_base)
                            .dontAnimate()
                            .error(R.drawable.icon_base)
                            .into(iv_userHeadProject);
                }
            }else {
                header_text_view.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position);

       /* if (EaseChatItemStyleHelper.getInstance().getStyle().getItemShowType()==2){

        }*/
        ConstraintLayout header_text_view =holder.itemView.findViewById(R.id.header_text_view);

        EaseImageView iv_userhead =holder.itemView.findViewById(R.id.iv_userhead);//对话框头像

        UserMessage userMessage = EaseCommom.getInstance().getUserMessage();
        if (iv_userhead!=null){
            iv_userhead.setShapeType(1);
            if (holder instanceof EaseChatRowViewHolder){
              if (((EaseChatRowViewHolder)holder).getChatRow().isSender()){
/*
                  Glide.with(mContext).load(userMessage.getLogo()).placeholder(R.drawable.icon_base)
                          .dontAnimate()
                          .error(R.drawable.icon_base)
                          .into(iv_userhead);
*/
              }else {

                  Glide.with(mContext).load(userMessage.getLogo()).placeholder(R.drawable.icon_base)
                          .dontAnimate()
                          .error(R.drawable.icon_base)
                          .into(iv_userhead);

              }
            }
        }


        if (header_text_view!=null){
            if (position==0&&isVis){
                header_text_view.setVisibility(View.VISIBLE);
                TextView tvInvestorName =holder.itemView.findViewById(R.id.tvInvestorName);
                TextView tvInvestorCompany =holder.itemView.findViewById(R.id.tvInvestorCompany);
                EaseImageView iv_userHeadInvestor =holder.itemView.findViewById(R.id.iv_userHeadInvestor);

                TextView tvName =holder.itemView.findViewById(R.id.tvName);
                TextView tvCompany =holder.itemView.findViewById(R.id.tvCompany);
                TextView tvAddress =holder.itemView.findViewById(R.id.tvAddress);
                TextView tvCase =holder.itemView.findViewById(R.id.tvCase);
                EaseImageView iv_userHeadProject =holder.itemView.findViewById(R.id.iv_userHeadProject);

                RelativeLayout rlProjectTitle =holder.itemView.findViewById(R.id.rlProjectTitle);
                RelativeLayout rlInvestorTitle =holder.itemView.findViewById(R.id.rlInvestorTitle);
                if (EaseCommom.getInstance().isProject()){
                    rlProjectTitle.setVisibility(View.GONE);
                    rlInvestorTitle.setVisibility(View.VISIBLE);
                }else {
                    rlProjectTitle.setVisibility(View.VISIBLE);
                    rlInvestorTitle.setVisibility(View.GONE);
                }
                if (userMessage==null) return;
                if (EaseCommom.getInstance().isProject()){
                    tvInvestorName.setText(userMessage.getName());
                    tvInvestorCompany.setText(userMessage.getPosition()+"  |  "+userMessage.getFinancing());
                    Glide.with(mContext).load(userMessage.getLogo()).placeholder(R.drawable.icon_base)
                            .dontAnimate()
                            .error(R.drawable.icon_base)
                            .into(iv_userHeadInvestor);

                }else {
                    tvName.setText(userMessage.getName());
                    tvCompany.setText(userMessage.getTrade()+"  |  "+userMessage.getFinancing());
                    tvAddress.setText(userMessage.getAddress());
                    tvCase.setText(userMessage.getDescribe());
                    Glide.with(mContext).load(userMessage.getLogo()).placeholder(R.drawable.icon_base)
                            .dontAnimate()
                            .error(R.drawable.icon_base)
                            .into(iv_userHeadProject);
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

