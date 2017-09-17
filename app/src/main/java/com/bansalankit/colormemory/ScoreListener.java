package com.bansalankit.colormemory;

/**
 * Listener for score change events.
 * <p>
 * <br><i>Author : <b>Ankit Bansal</b></i>
 * <br><i>Created Date : <b>27 Apr 2017</b></i>
 * <br><i>Modified Date : <b>27 Apr 2017</b></i>
 */
interface ScoreListener {
    /**
     * Callback invoked when player's score is changed.
     */
    void onChange(int newScore);
}
