package sound;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URL;

/**
 * @author : Hung Hoang
 * @version : 23/03/2016;
 *          <p>
 *          This class uses a circular buffer to store all the soundtracks organises
 *          them to be played one after another in a loop
 * @date : 28/01/16
 */
public class SoundManager {

    private final String SEPARATOR = "/";
    private final String AUDIO_RESOURES = SEPARATOR + "resources" + SEPARATOR + "audio" + SEPARATOR;

    private CircularBufferNode<MediaPlayer> soundtracks[];

    // Instance for singleton.
    private static SoundManager instance = null;

    /**
     * Implements Singleton for this class (Only one can exist).
     *
     * @return the sound manager instance
     */
    public static SoundManager Instance() {

        if (instance == null) {
            instance = new SoundManager();
        }

        return instance;
    }

    /**
     * Delete the existing instance of this class
     */
    public static void delete() {

        instance = null;
    }


    /**
     * Constructor that creates a new Circular Buffer that will store the sound data
     * It also loads in the URL paths for the sound file
     */
    private SoundManager() {

        URL soundPaths[] = {
                getClass().getResource(AUDIO_RESOURES + "Spell.mp3"),
                getClass().getResource(AUDIO_RESOURES + "a_ninja_among_culturachippers.mp3")
        };

        soundtracks = new CircularBufferNode[2];

        // Wrap each url around a Circular Buffer Node
        for (int i = 0; i < soundtracks.length; i++) {
            soundtracks[i] = new CircularBufferNode<>(new MediaPlayer(new Media(soundPaths[i].toString())));
        }

        // Sets each node's next node, the last node is set to the first
        for (int i = 0; i < soundtracks.length - 1; i++) {
            soundtracks[i].setNext(soundtracks[i + 1]);
        }

        soundtracks[soundtracks.length - 1].setNext(soundtracks[0]);

        // Sets the action when the media ends, to go to the next node and play
        for (int i = 0; i < soundtracks.length; i++) {

            final int finalI = i;

            soundtracks[i].getValue().setOnEndOfMedia(() -> {

                MediaPlayer m = soundtracks[finalI].getNext().getValue();
                m.seek(Duration.ZERO);
                m.play();
            });
        }

    }

    /**
     * Starts playing the soundtrack
     */
    public void startSoundtrack() {

        this.soundtracks[0].getValue().play();
    }

    /**
     * Pauses the soundtrack
     */
    public void pauseSoundtrack() {

        this.soundtracks[0].getValue().pause();
    }
}
