package com.example.imchatui.channel.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


import com.example.imchatui.R;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CategoryViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.categoryTextView)
    TextView categoryTextView;

    public CategoryViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(String category) {
        categoryTextView.setText(category);
    }
}
