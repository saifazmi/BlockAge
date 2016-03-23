package sceneElements;

/**
 * @author : Anh Pham; Contributors - Anh Pham
 * @version : 23/03/2016;
 *          <p>
 *          This class tracks the users score.
 *
 * @date : 14/03/16
 */
public class Score {

    private double score;
    private boolean halved;

    public Score() {

        this.score = 0;
        this.halved = false;
    }

    // GETTER methods

    /**
     * getting score of the game
     *
     * @return score the Score of the game
     */
    public double getScore() {

        return this.score;
    }

    // SETTER methods

    /**
     * Updates the score
     *
     * @param rate the amount by which to increase the score
     */
    public void update(double rate) {

        this.score += rate;
    }

    /**
     * Divides the score into two halves.
     */
    public void halveScore() {

        if (!halved) {

            this.score = this.score / 2;
            this.halved = true;
        }
    }
}
