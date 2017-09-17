package com.bansalankit.colormemory;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

/**
 * This class holds the view for every cell of card grid.
 * <p>
 * <br><i>Author : <b>Ankit Bansal</b></i>
 * <br><i>Created Date : <b>26 Apr 2017</b></i>
 * <br><i>Modified Date : <b>29 Apr 2017</b></i>
 */
class GridHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final ImageView mCardImage;
    private GridAdapter mAdapter;
    private Card mCard;

    GridHolder(View rootView, GridAdapter adapter) {
        super(rootView);
        mAdapter = adapter;
        mCardImage = (ImageView) rootView.findViewById(R.id.card_image);
        rootView.setOnClickListener(this);
    }

    public void setData(Card card) {
        if (card.isLocked()) mCardImage.setImageResource(card.getImageRes());
        else if (card.isSelected()) mCardImage.setImageResource(card.getImageRes());
        else mCardImage.setImageResource(R.drawable.card_bg);
        mCard = card;
    }

    @Override
    public void onClick(View view) {
        mAdapter.setSelectedCard(mCard);
    }
}