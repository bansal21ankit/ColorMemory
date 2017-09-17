package com.bansalankit.colormemory;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Main landing screen of game, shows the card grid and current score, allows user to view high scores.
 * <p>
 * <br><i>Author : <b>Ankit Bansal</b></i>
 * <br><i>Created Date : <b>26 Apr 2017</b></i>
 * <br><i>Modified Date : <b>26 Apr 2017</b></i>
 */
public class MainActivity extends AppCompatActivity implements ScoreListener {
    private DataHandler mDataHandler;
    private GridAdapter mGridAdapter;
    private TextView mTextScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.drawable.logo);
        actionBar.setTitle(null);

        // Init the Score and Data Handler class
        mTextScore = (TextView) findViewById(R.id.main_score);
        mDataHandler = DataHandler.getInstance();
        mDataHandler.setScoreListener(this);
        mDataHandler.resetAndShuffle();

        // Init the cards grid
        mGridAdapter = new GridAdapter(getSupportFragmentManager());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, DataHandler.GRID_SPAN);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.main_card_grid);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mGridAdapter);

        // Add decoration and performance tweaks to card grid
        int spacing = getResources().getDimensionPixelSize(R.dimen.card_spacing);
        recyclerView.addItemDecoration(new GridDecorator(DataHandler.GRID_SPAN, spacing, false));
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onChange(int newScore) {
        mTextScore.setText(String.valueOf(newScore));
        if (mDataHandler.isGameFinished()) onGameFinished(newScore);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.main_high_score) showHighScore(-1);
        return true;
    }

    private void showHighScore(long userId) {
        Intent scoreScreen = new Intent(this, HighScoreActivity.class);
        scoreScreen.putExtra(HighScoreActivity.KEY_PLAYER_ID, userId);
        startActivity(scoreScreen);
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }

    private void resetGame() {
        mDataHandler.resetAndShuffle();
        mGridAdapter.notifyDataSetChanged();
    }

    private void onGameFinished(final int score) {
        // Create the view for dialog to submit the scores
        String title = getString(R.string.score_title) + score;
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_score, null);
        final AlertDialog dialog = new AlertDialog.Builder(this).setTitle(title).setView(dialogView).create();

        final EditText nameView = (EditText) dialogView.findViewById(R.id.score_name);
        View.OnClickListener clickListener = view -> {
            switch (view.getId()) {
                case R.id.score_submit:
                    String playerName = nameView.getText().toString().trim();

                    if (TextUtils.isEmpty(playerName)) {
                        nameView.setError(getString(R.string.score_name_error));
                        return;
                    }

                    long id = new DbHelper(MainActivity.this).insetScore(playerName, score);
                    if (id == -1) { // Notify user that there was error in submitting the scores
                        Toast.makeText(MainActivity.this, "Score not submitted", Toast.LENGTH_SHORT).show();
                        return;
                    } else showHighScore(id);

                case R.id.score_cancel:
                    dialog.dismiss();
                    break;
            }
        };

        // Set the data and listener for dialog views
        dialogView.findViewById(R.id.score_submit).setOnClickListener(clickListener);
        dialogView.findViewById(R.id.score_cancel).setOnClickListener(clickListener);
        dialog.show();

        // Reset the game
        resetGame();
    }
}