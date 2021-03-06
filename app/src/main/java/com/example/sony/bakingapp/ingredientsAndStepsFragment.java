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
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
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
import com.squareup.picasso.Picasso;

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
    private boolean tabletMode;
    onClick mCallback;
    private  long position;
    private boolean mPlayVideoWhenForegrounded;
    Uri videoUri;

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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("position",position);
        outState.putBoolean("readyStatus",mPlayVideoWhenForegrounded);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if(savedInstanceState!=null)
        {position = savedInstanceState.getLong("position");}

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if(savedInstanceState!=null) {
            position = savedInstanceState.getLong("position");
            mPlayVideoWhenForegrounded=savedInstanceState.getBoolean("readyStatus");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Bundle args=getArguments();

        ingredientsData=args.getParcelableArrayList("ingreData");
        stepsData=args.getParcelable("steps");
        stepsArrayList=args.getParcelableArrayList("stepsArray");
        tabletMode=args.getBoolean("tabletMode");
        int idx=0;
        View rootView=inflater.inflate(R.layout.ingredientsandsteps_fragment,container,false);
        TextView tv1=(TextView)rootView.findViewById(R.id.tv_detail);
        ImageView iv=(ImageView)rootView.findViewById(R.id.iv_steps);
        mPlayerView=(SimpleExoPlayerView)rootView.findViewById(R.id.exoplayer);
        ImageButton ib_next=(ImageButton)rootView.findViewById(R.id.next_button);
        ImageButton ib_prev=(ImageButton)rootView.findViewById(R.id.prev_button);
        if(!(ingredientsData==null&&stepsData==null&&stepsArrayList==null)) {

            if (ingredientsData != null) {
                for (int a = 0; a < ingredientsData.size(); a++) {
                    tv1.setVisibility(View.VISIBLE);
                    tv1.append(idx + 1 + ") " + ingredientsData.get(a).getQuantity() + ingredientsData.get(a).getMeasure() + "  " + ingredientsData.get(a).getIngredient() + "\n\n");
                    idx++;
                }

                mPlayerView.setVisibility(View.INVISIBLE);
            } else {
                if(stepsData!=null) {
                    if(!tabletMode) {
                        ib_next.setVisibility(View.VISIBLE);
                        ib_prev.setVisibility(View.VISIBLE);
                    }
                    if (!stepsData.getVideoURL().equals("") || !stepsData.getThumbnailURL().equals("")) {
                        tv1.setVisibility(View.VISIBLE);
                        if(!stepsData.getThumbnailURL().equals("")){
                            //load picasso
                            Picasso.with(getContext()).load(stepsData.getThumbnailURL()).into(iv);

                        }
                        tv1.append(stepsData.getShortDescription() + "\n" + stepsData.getDescription());

                        //EXO PLAYER
                        mPlayerView.requestFocus();

                        TrackSelector ts = new DefaultTrackSelector();
                        LoadControl lc = new DefaultLoadControl();
                        mExoPlayer = ExoPlayerFactory.newSimpleInstance(this.getContext(), ts, lc);
                        mPlayerView.setPlayer(mExoPlayer);

                        String userAgent = Util.getUserAgent(this.getContext(), "Baking App");
                        videoUri=Uri.parse(stepsData.getVideoURL());
                        MediaSource mSource = new ExtractorMediaSource(videoUri, new DefaultDataSourceFactory(this.getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
                        if (position != C.TIME_UNSET) {
                            mExoPlayer.seekTo(position);
//                            mExoPlayer.setPlayWhenReady(mPlayVideoWhenForegrounded);
                        }
                            mExoPlayer.prepare(mSource);
                            mExoPlayer.setPlayWhenReady(true);
                            mPlayerView.setVisibility(View.VISIBLE);


                        initializeMediaSession();

                    } else {
                        mPlayerView.setVisibility(View.INVISIBLE);
                        tv1.setVisibility(View.VISIBLE);
                        tv1.append(stepsData.getShortDescription() + "\n" + stepsData.getDescription());
                    }
                }
                if (!tabletMode) {

                    ib_next.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int id = stepsData.getId();
                            if ((id + 1) < stepsArrayList.size()) {
                                if(!tabletMode){                                mCallback.onClicked(stepsData.getId() + 1);
                                }
                                if (mExoPlayer != null) {
                                    mExoPlayer.stop();
                                }
                            }
                        }
                    });
                    ib_prev.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int id = stepsData.getId();
                            if (id != 0) {
                                if(!tabletMode){mCallback.onClicked(stepsData.getId() - 1);}

                                if (mExoPlayer != null) {
                                    mExoPlayer.stop();
                                }
                            }
                        }
                    });
                }
                else{
                    ib_next.setVisibility(View.INVISIBLE);
                    ib_prev.setVisibility(View.INVISIBLE);
                }

            }

        }
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(!(context instanceof recipeDetail)){
            mCallback = (ingredientsAndStepsFragment.onClick) context;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mExoPlayer!=null){
            position = mExoPlayer.getCurrentPosition();
//            mPlayVideoWhenForegrounded = mExoPlayer.getPlayWhenReady();
        }
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer=null;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (videoUri != null){

            TrackSelector ts = new DefaultTrackSelector();
            LoadControl lc = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(this.getContext(), ts, lc);
            mPlayerView.setPlayer(mExoPlayer);
            String userAgent = Util.getUserAgent(this.getContext(), "Baking App");
            MediaSource mSource = new ExtractorMediaSource(videoUri, new DefaultDataSourceFactory(this.getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            if (position != C.TIME_UNSET) {
                mExoPlayer.seekTo(position);
            }
            mExoPlayer.prepare(mSource);
            mExoPlayer.setPlayWhenReady(true);
            mPlayerView.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
