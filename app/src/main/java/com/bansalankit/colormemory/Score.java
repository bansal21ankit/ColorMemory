package com.bansalankit.colormemory;

/**
 * Model class to hold the data about score and the player who scored.
 * <p>
 * <br><i>Author : <b>Ankit Bansal</b></i>
 * <br><i>Created Date : <b>28 Apr 2017</b></i>
 * <br><i>Modified Date : <b>28 Apr 2017</b></i>
 */
class Score {
    private String mPlayerName;
    private int mScore;
    private long mId;

    Score(long id, String playerName, int score) {
        mPlayerName = playerName;
        mScore = score;
        mId = id;
    }

    String getPlayerName() {
        return mPlayerName;
    }

    int getScore() {
        return mScore;
    }

    long getId() {
        return mId;
    }
}