package com.bansalankit.colormemory;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

/**
 * Activity to display the High Score table.
 * <p>
 * <br><i>Author : <b>Ankit Bansal</b></i>
 * <br><i>Created Date : <b>27 Apr 2017</b></i>
 * <br><i>Modified Date : <b>29 Apr 2017</b></i>
 */
public class HighScoreActivity extends AppCompatActivity {
    static final String KEY_PLAYER_ID = "playerId";
    private List<Score> mListScores;
    private long mSelectedId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) actionBar.setDisplayHomeAsUpEnabled(true);

        mListScores = new DbHelper(this).getScores();
        mSelectedId = getIntent().getLongExtra(KEY_PLAYER_ID, -1);
        ListView scoreTable = (ListView) findViewById(R.id.high_score_list);
        scoreTable.setAdapter(new MyAdapter());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }

    private class MyAdapter extends BaseAdapter {
        private class MyHolder {
            private TextView mTextRank;
            private TextView mTextName;
            private TextView mTextScore;

            MyHolder(View rootView) {
                mTextRank = (TextView) rootView.findViewById(R.id.high_score_rank);
                mTextName = (TextView) rootView.findViewById(R.id.high_score_name);
                mTextScore = (TextView) rootView.findViewById(R.id.high_score_score);
                mTextScore.setBackgroundResource(android.R.color.transparent);
                mTextName.setBackgroundResource(android.R.color.transparent);
                mTextRank.setBackgroundResource(android.R.color.transparent);
            }

            void setData(Score score, int position) {
                mTextRank.setText(String.valueOf(position + 1));
                mTextScore.setText(String.valueOf(score.getScore()));
                mTextName.setText(score.getPlayerName());

                int textStyle = (mSelectedId == score.getId()) ? R.style.HighScore_Selected : R.style.HighScore_Entry;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mTextRank.setTextAppearance(textStyle);
                    mTextName.setTextAppearance(textStyle);
                    mTextScore.setTextAppearance(textStyle);
                } else {
                    mTextRank.setTextAppearance(mTextRank.getContext(), textStyle);
                    mTextName.setTextAppearance(mTextName.getContext(), textStyle);
                    mTextScore.setTextAppearance(mTextScore.getContext(), textStyle);
                }
            }
        }

        @Override
        public boolean isEnabled(int position) {
            return false;
        }

        @Override
        public Score getItem(int position) {
            return mListScores.get(position);
        }

        @Override
        public long getItemId(int position) {
            return getItem(position).getId();
        }

        @Override
        public int getCount() {
            return mListScores == null ? 0 : mListScores.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MyHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_high_score, parent, false);
                holder = new MyHolder(convertView);
                convertView.setTag(holder);
            } else holder = (MyHolder) convertView.getTag();
            holder.setData(getItem(position), position);
            return convertView;
        }
    }
}
