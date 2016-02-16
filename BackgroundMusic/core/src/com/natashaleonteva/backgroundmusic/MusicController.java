package com.natashaleonteva.backgroundmusic;

import com.badlogic.gdx.audio.Music;

import java.util.ArrayList;

/**
 * Created by natashaleonteva on 16.02.16.
 */
public interface MusicController {
    void pause();
    void play();
    boolean isPlaying();
}
