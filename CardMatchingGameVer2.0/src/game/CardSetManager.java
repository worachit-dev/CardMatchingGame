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
           "bear","cat","chick","cow","dog","frog","hamster","koala","lion","octopus","panda"
           ,"rabbit","seal","sheep","whale"
        );

        return new CardSet(
            CardSetType.ANIMALS,
            images
        );
    }

    private CardSet createFruitsSet() {

        List<String> images = List.of(
            "apple","avocado","banana","blueberry","carrot","cherry","corn","grape","lemon"
            ,"mango","orange","pineapple","strawberry","tomato","watermelon"
        );

        return new CardSet(
            CardSetType.FRUITS,
            images
        );
    }

 }



