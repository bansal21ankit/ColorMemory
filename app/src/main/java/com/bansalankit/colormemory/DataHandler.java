package com.bansalankit.colormemory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class stores the global list of cards and current score.
 * <p>
 * <br><i>Author : <b>Ankit Bansal</b></i>
 * <br><i>Created Date : <b>27 Apr 2017</b></i>
 * <br><i>Modified Date : <b>29 Apr 2017</b></i>
 */
final class DataHandler {
    private static DataHandler sInstance;

    static final int GRID_SPAN = 4;
    final List<Card> LIST_CARDS;

    private ScoreListener mScoreListener;
    private int mScore;

    static DataHandler getInstance() {
        if (sInstance == null) sInstance = new DataHandler();
        return sInstance;
    }

    private DataHandler() {
        // Create list of cards for a square grid
        LIST_CARDS = new ArrayList<>(GRID_SPAN * GRID_SPAN);
        LIST_CARDS.add(new Card(R.drawable.colour1));
        LIST_CARDS.add(new Card(R.drawable.colour1));
        LIST_CARDS.add(new Card(R.drawable.colour2));
        LIST_CARDS.add(new Card(R.drawable.colour2));
        LIST_CARDS.add(new Card(R.drawable.colour3));
        LIST_CARDS.add(new Card(R.drawable.colour3));
        LIST_CARDS.add(new Card(R.drawable.colour4));
        LIST_CARDS.add(new Card(R.drawable.colour4));
        LIST_CARDS.add(new Card(R.drawable.colour5));
        LIST_CARDS.add(new Card(R.drawable.colour5));
        LIST_CARDS.add(new Card(R.drawable.colour6));
        LIST_CARDS.add(new Card(R.drawable.colour6));
        LIST_CARDS.add(new Card(R.drawable.colour7));
        LIST_CARDS.add(new Card(R.drawable.colour7));
        LIST_CARDS.add(new Card(R.drawable.colour8));
        LIST_CARDS.add(new Card(R.drawable.colour8));
    }

    void resetAndShuffle() {
        for (Card card : LIST_CARDS) card.setState(Card.STATE_NONE);
        Collections.shuffle(LIST_CARDS);

        mScore = 0;
        if (mScoreListener != null) mScoreListener.onChange(mScore);
    }

    int getScore() {
        return mScore;
    }

    void updateScore(boolean isMatched) {
        if (isMatched) mScore += 2;
        else mScore--;

        // Notify the score listener about this change
        if (mScoreListener != null) mScoreListener.onChange(mScore);
    }

    void setScoreListener(ScoreListener listener) {
        mScoreListener = listener;
    }

    boolean isGameFinished() {
        for (Card card : LIST_CARDS) if (!card.isLocked()) return false;
        return true;
    }
}