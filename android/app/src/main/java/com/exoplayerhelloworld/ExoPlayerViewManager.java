package com.exoplayerhelloworld;

import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;


public class ExoPlayerViewManager extends SimpleViewManager<ExoPlayerView> {

  public static final String REACT_CLASS = "ExoPlayerView";

    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @Override
    public ExoPlayerView createViewInstance(ThemedReactContext context) {
        return new ExoPlayerView(context); //exoplayer constructor
    }
}
