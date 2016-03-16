package sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URL;

/**
 * Created by hung on 01/03/16.
 */
public class SoundManager {

    private final String SEPARATOR = "/";
    private final String AUDIO_RESOURES = SEPARATOR + "resources" + SEPARATOR + "audio" + SEPARATOR;

    private CircularBufferNode<MediaPlayer> soundtracks[];
    private CircularBufferNode<MediaPlayer> currentMPointer;
    private boolean paused = true;

    private static SoundManager instance = null;

    public static SoundManager Instance() {
        if (instance == null)
            instance = new SoundManager();
        return instance;
    }

    public static boolean delete() {
        instance = null;
        return true;
    }

    private SoundManager() {

        URL soundPaths[] = {
                getClass().getResource(AUDIO_RESOURES + "Spell.mp3"),
                getClass().getResource(AUDIO_RESOURES + "a_ninja_among_culturachippers.mp3")
        };

        soundtracks = new CircularBufferNode[2];

        for (int i = 0; i < soundtracks.length; i++) {
            soundtracks[i] = new CircularBufferNode<>(new MediaPlayer(new Media(soundPaths[i].toString())));
        }

        for (int i = 0; i < soundtracks.length - 1; i++) {
            soundtracks[i].setNext(soundtracks[i + 1]);
        }

        soundtracks[soundtracks.length - 1].setNext(soundtracks[0]);
        //System.out.println(soundtracks[soundtracks.length - 1].getValue().getMedia().toString() + " next now: " + soundtracks[0].getValue().getMedia().toString());

        for (int i = 0; i < soundtracks.length; i++) {
            final int finalI = i;
            soundtracks[i].getValue().setOnEndOfMedia(() -> {
                MediaPlayer m = soundtracks[finalI].getNext().getValue();
                m.seek(Duration.ZERO);
                m.play();
            });
        }

    }

    public void startSoundtrack() {
        paused = false;
        soundtracks[0].getValue().play();
    }

    public void resumeSoudtrack() {
        paused = false;
        currentMPointer.getValue().play();
    }

    public void pauseSoundtrack() {
        paused = true;
        soundtracks[0].getValue().pause();
        //currentMPointer.getValue().pause();
    }

    public boolean isPaused() {
        return paused;
    }
}
