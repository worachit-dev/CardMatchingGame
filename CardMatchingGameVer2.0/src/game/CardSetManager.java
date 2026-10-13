package game;
import model.CardSet; //เรียกใช้งาน CardSet จากโฟลเดอร์ model
import model.CardSetType;

import java.util.ArrayList;
import java.util.List;


public class CardSetManager {

    private final List<CardSet> cardSets;

    private CardSet currentSet;

    public CardSetManager() {
 
        cardSets = new ArrayList<>();
 
        cardSets.add(createAnimalsSet());
        cardSets.add(createFruitsSet());
        //cardSets.add(createNumbersSet());
    }

    public CardSet getSet(CardSetType type) {

    for (CardSet set : cardSets) {
        if (set.getType() == type) {
            currentSet = set;
            return set;
        }
    }

    return null;
    }

    public CardSet getCurrentSet() {
        return currentSet;
    }

    private CardSet createAnimalsSet() {

        List<String> images = List.of(
           
        );

        return new CardSet(
            CardSetType.ANIMALS,
            images
        );
    }

    private CardSet createFruitsSet() {

        List<String> images = List.of(
            "apple","b_berry","carrot","lemon","orange","s_berry","tomato","corn","greenapple"
            ,"grape","mango","banana","roseapple","durain","image"
        );

        return new CardSet(
            CardSetType.FRUITS,
            images
        );
    }

    private CardSet createNumbersSet() {

        List<String> images = List.of(
           
        );

        return new CardSet(
            CardSetType.NUMBERS,
            images
        );
    }
}


