public class ScoreManager {
    private int score;
    private int highScore;
    
    public ScoreManager(){
        score = 0;
        highScore = 0;
    }

    public void resetScore(){
        score = 0;
    }

    public void correctMatch(){
        score += 1;
    }

    public int getScore(){
        return  score;
    }

    public int getHighScore(){
        return highScore;
    }

    public void checkHighScore(){
        if(score > highScore){
            highScore = score;
        }
    }
}
