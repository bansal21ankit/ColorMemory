package com.bansalankit.colormemory;

import android.support.annotation.DrawableRes;

/**
 * This class works as a model to store details about every card and its current state.
 * <p>
 * <br><i>Author : <b>Ankit Bansal</b></i>
 * <br><i>Created Date : <b>26 Apr 2017</b></i>
 * <br><i>Modified Date : <b>29 Apr 2017</b></i>
 */
class Card {
    static final int STATE_NONE = 0;
    static final int STATE_LOCK = 1;
    static final int STATE_SELECT = 2;

    private int mImageRes;
    private int mState;

    Card(@DrawableRes int imageRes) {
        this.mImageRes = imageRes;
        this.mState = STATE_NONE;
    }

    @DrawableRes
    int getImageRes() {
        return mImageRes;
    }

    void setState(int state) {
        this.mState = state;
    }

    boolean isLocked() {
        return mState == STATE_LOCK;
    }

    boolean isSelected() {
        return mState == STATE_SELECT;
    }

    boolean equals(Card card) {
        return card != null && mImageRes == card.getImageRes();
    }
}