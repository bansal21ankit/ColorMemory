package com.bansalankit.colormemory;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * This is adapter to inflate data in main Card Grid and handles the selection of any card.
 * <p>
 * <br><i>Author : <b>Ankit Bansal</b></i>
 * <br><i>Created Date : <b>26 Apr 2017</b></i>
 * <br><i>Modified Date : <b>29 Apr 2017</b></i>
 */
class GridAdapter extends RecyclerView.Adapter<GridHolder> {
    private final FragmentManager mFragmentManager;
    private final DataHandler mDataHandler;

    GridAdapter(FragmentManager fragmentManager) {
        mDataHandler = DataHandler.getInstance();
        mFragmentManager = fragmentManager;
        new Student();
    }

    @Override
    public GridHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent, false);
        return new GridHolder(rootView, this);
    }

    @Override
    public void onBindViewHolder(GridHolder holder, int position) {
        holder.setData(mDataHandler.LIST_CARDS.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataHandler.LIST_CARDS == null ? 0 : mDataHandler.LIST_CARDS.size();
    }

    private Card getSelectedCard() {
        for (Card card : mDataHandler.LIST_CARDS) if (card.isSelected()) return card;
        return null;
    }

    void setSelectedCard(final Card card) {
        // Do nothing if selected card is already selected or locked.
        if (card == null || card.isLocked() || card.isSelected()) return;

        final Card previousCard = getSelectedCard();
        card.setState(Card.STATE_SELECT);
        notifyDataSetChanged();

        if (previousCard != null) {
            // Lock the cards and increment the score if previous and current card matches.
            if (card.equals(previousCard)) {
                new RoundDialog().setMatched(true).setOnDismissListener(dialog -> {
                    previousCard.setState(Card.STATE_LOCK);
                    card.setState(Card.STATE_LOCK);
                    mDataHandler.updateScore(true);
                    notifyDataSetChanged();
                }).show(mFragmentManager, RoundDialog.TAG);
            }
            // Deselect previous card and select current one if no match, also decrement the score.
            else {
                new RoundDialog().setMatched(false).setOnDismissListener(dialog -> {
                    previousCard.setState(Card.STATE_NONE);
                    card.setState(Card.STATE_NONE);
                    mDataHandler.updateScore(false);
                    notifyDataSetChanged();
                }).show(mFragmentManager, RoundDialog.TAG);
            }
        }
    }
}