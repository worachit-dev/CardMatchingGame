package model;
public class Card {
    private String image;
    private boolean revealed;
    private boolean matched;

    public Card(String image){
        this.image = image ;
        this.revealed = false;
        this.matched = false;
    }

    public String getImage(){
        return image;
    }

    public boolean isRevealed(){
        return revealed;
    }

    public boolean isMatched(){
        return matched;
    }

    public void setRevealed(boolean revealed){
        this.revealed = revealed;
    }

    public void setMatched(boolean matched){
        this.matched = matched;
    }
}
