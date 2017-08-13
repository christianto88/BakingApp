package com.example.sony.bakingapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.FragmentManager;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.sony.bakingapp.databinding.ActivityDetailBinding;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;

import java.util.ArrayList;


public class detailActivity extends AppCompatActivity implements ExoPlayer.EventListener, ingredientsAndStepsFragment.onClick{
    private ArrayList<Ingredients> ingredientsData;
    private Steps stepsData;
    private ArrayList<Steps> stepsArrayList;
    ActivityDetailBinding bind;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind= DataBindingUtil.setContentView(this,R.layout.activity_detail);
        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            String recipeName=intentThatStartedThisActivity.getStringExtra("recipeName");
            getSupportActionBar().setTitle(recipeName);
            if (intentThatStartedThisActivity.hasExtra("ingredients")) {
                ingredientsData = intentThatStartedThisActivity.getParcelableArrayListExtra("ingredients");
                if(savedInstanceState==null) {
                    ingredientsAndStepsFragment x = new ingredientsAndStepsFragment();
                    Bundle args = new Bundle();
                    args.putParcelableArrayList("ingreData", ingredientsData);
                    x.setArguments(args);
                    FragmentManager fm = getSupportFragmentManager();
                    fm.beginTransaction().add(R.id.my_container2, x).commit();
                }
            }
            else {

                    stepsData = intentThatStartedThisActivity.getParcelableExtra("steps");
                    stepsArrayList = intentThatStartedThisActivity.getParcelableArrayListExtra("stepsData");
                if(savedInstanceState==null) {
                    ingredientsAndStepsFragment x = new ingredientsAndStepsFragment();
                    Bundle args = new Bundle();
                    args.putParcelableArrayList("stepsArray", stepsArrayList);
                    args.putParcelable("steps", stepsData);
                    x.setArguments(args);
                    FragmentManager fm = getSupportFragmentManager();
                    fm.beginTransaction().add(R.id.my_container2, x).commit();
                }
            }
        }
        else{
            Log.v("tes","ga ada data");
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("ini Detail ","pause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("ini Detail ","destroy");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("ini Detail ","resume");
    }

    @Override
    public void onClicked(int index) {

        stepsData=stepsArrayList.get(index);
        ingredientsAndStepsFragment x=new ingredientsAndStepsFragment();
        Bundle args=new Bundle();
        args.putParcelableArrayList("stepsArray",stepsArrayList);
        args.putParcelable("steps",stepsData);
        x.setArguments(args);
        FragmentManager fm=getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.my_container2,x).commit();
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {

    }

    @Override
    public void onRepeatModeChanged(int repeatMode) {

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    @Override
    public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

    }
}
