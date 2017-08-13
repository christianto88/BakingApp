package com.example.sony.bakingapp;

import android.content.Context;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;

/**
 * Created by SONY on 8/8/2017.
 */

public class ingredientsAndStepsFragment extends Fragment implements ExoPlayer.EventListener{
    private static final java.lang.String TAG ="Baking App" ;
    private ArrayList<Ingredients> ingredientsData;
    private Steps stepsData;
    private ArrayList<Steps> stepsArrayList;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;
    onClick mCallback;

    private void initializeMediaSession() {

        // Create a MediaSessionCompat.
        mMediaSession = new MediaSessionCompat(this.getContext(), TAG);

        // Enable callbacks from MediaButtons and TransportControls.
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Do not let MediaButtons restart the player when the app is not visible.
        mMediaSession.setMediaButtonReceiver(null);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player.
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());


        // MySessionCallback has methods that handle callbacks from a media controller.
        mMediaSession.setCallback(new MySessionCallback());

        // Start the Media Session since the activity is active.
        mMediaSession.setActive(true);

    }
    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
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
        if((playbackState == ExoPlayer.STATE_READY) && playWhenReady){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mExoPlayer.getCurrentPosition(), 1f);
        } else if((playbackState == ExoPlayer.STATE_READY)){
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mExoPlayer.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mStateBuilder.build());
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

    public interface onClick{
        void onClicked(int index);
    }
    public ingredientsAndStepsFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Bundle args=getArguments();
        ingredientsData=args.getParcelableArrayList("ingreData");
        stepsData=args.getParcelable("steps");
        stepsArrayList=args.getParcelableArrayList("stepsArray");
        int idx=0;
        View rootView=inflater.inflate(R.layout.ingredientsandsteps_fragment,container,false);
        TextView tv1=(TextView)rootView.findViewById(R.id.tv_detail);
        mPlayerView=(SimpleExoPlayerView)rootView.findViewById(R.id.exoplayer);
        ImageButton ib_next=(ImageButton)rootView.findViewById(R.id.next_button);
        ImageButton ib_prev=(ImageButton)rootView.findViewById(R.id.prev_button);

        if(ingredientsData!=null){
            for(int a=0;a<ingredientsData.size();a++){
                tv1.setVisibility(View.VISIBLE);
                tv1.append(idx+1 +") "+ingredientsData.get(a).getQuantity()+ingredientsData.get(a).getMeasure()+"  "+ingredientsData.get(a).getIngredient()+"\n\n");
                idx++;
            }
            ib_next.setVisibility(View.INVISIBLE);
            ib_prev.setVisibility(View.INVISIBLE);
            mPlayerView.setVisibility(View.INVISIBLE);
        }
        else
        {
            if(!stepsData.getVideoURL().equals("") || !stepsData.getThumbnailURL().equals("")){
                tv1.setVisibility(View.VISIBLE);

                tv1.append(stepsData.getShortDescription()+"\n"+stepsData.getDescription());

                //EXO PLAYER
                mPlayerView.requestFocus();

                TrackSelector ts=new DefaultTrackSelector();
                LoadControl lc=new DefaultLoadControl();
                mExoPlayer= ExoPlayerFactory.newSimpleInstance(this.getContext(),ts,lc);
                mPlayerView.setPlayer(mExoPlayer);

                String userAgent= Util.getUserAgent(this.getContext(),"Baking App");
                MediaSource mSource=new ExtractorMediaSource(Uri.parse(stepsData.getVideoURL()),new DefaultDataSourceFactory(this.getContext(),userAgent),new DefaultExtractorsFactory(),null,null);
                mExoPlayer.prepare(mSource);
                mExoPlayer.setPlayWhenReady(true);
                mPlayerView.setVisibility(View.VISIBLE);
                initializeMediaSession();

            }else
            {
                mPlayerView.setVisibility(View.INVISIBLE);
                tv1.setVisibility(View.VISIBLE);
                tv1.append(stepsData.getShortDescription()+"\n"+stepsData.getDescription());
            }
            ib_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id=stepsData.getId();
                    if((id+1)<stepsArrayList.size()){
                        mCallback.onClicked(stepsData.getId()+1);
                        if(mExoPlayer!=null) {
                            mExoPlayer.stop();
                        }
                    }
                }
            });
            ib_prev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int id=stepsData.getId();
                    if(id!=0){
                        mCallback.onClicked(stepsData.getId()-1);
                        if(mExoPlayer!=null) {
                            mExoPlayer.stop();
                        }
                    }
                }
            });
        }


        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback=(ingredientsAndStepsFragment.onClick)context;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        mExoPlayer.stop();
//        mExoPlayer.release();
//        mExoPlayer = null;
//        mMediaSession.setActive(false);
    }
}
