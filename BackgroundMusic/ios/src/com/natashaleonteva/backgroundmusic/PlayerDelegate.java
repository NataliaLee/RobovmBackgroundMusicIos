package com.natashaleonteva.backgroundmusic;

import org.robovm.apple.avfoundation.AVAudioPlayer;
import org.robovm.apple.avfoundation.AVAudioPlayerDelegateAdapter;
import org.robovm.apple.foundation.NSError;

/**
 * Created by natashaleonteva on 16.02.16.
 */
public class PlayerDelegate extends AVAudioPlayerDelegateAdapter {
    @Override
    public void didFinishPlaying(AVAudioPlayer avAudioPlayer, boolean b) {
        System.out.println("didFinishPlaying");
    }

    @Override
    public void decodeErrorDidOccur(AVAudioPlayer avAudioPlayer, NSError nsError) {
        System.out.println("decodeErrorDidOccur "+nsError.toString());
    }
}