
package model; //1. ต้องบอกว่าไฟล์นี้อยู่แพ็กเกจไหน

public class Card {
    private String imageId;
    private boolean revealed;
    private boolean matched;

    public Card(String imageId){
        this.imageId = imageId ;
        this.revealed = false;
        this.matched = false;
    }

    public String getImage(){
        return imageId;
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

    public void hide() {
        if (!matched) {
            revealed = false;
        }
    }

    public void setMatched() {
        matched = true;
        revealed = true;
    }
}
