package sceneElements;

/**
 * Created by Anh Pham on 14/03/2016.
 */
public class Score {
    private double score;
    private boolean halved;

    public Score() {
        this.score = 0;
        this.halved = false;
    }

    // update the score
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

    /**
     * getting score of the game
     *
     * @return score the Score of the game
     */
    public double getScore() {
        return score;
    }
}
