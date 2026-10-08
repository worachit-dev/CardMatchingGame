package game;
import model.CardSet; //เรียกใช้งาน CardSet จากโฟลเดอร์ model
import model.CardSetType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class CardSetManager {

    private final List<CardSet> cardSets;
    private final Random random;
    private CardSet currentSet;

    public CardSetManager() {

        random = new Random();

        cardSets = new ArrayList<>();

        //cardSets.add(createAnimalsSet());
        cardSets.add(createFruitsSet());
        //cardSets.add(createNumbersSet());
    }

    public CardSet selectRandomSet() {

        int index = random.nextInt(cardSets.size());

        currentSet = cardSets.get(index);

        return currentSet;
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
            "apple","b_berry","carrot","lemon","orange","s_berry","tomato","corn"
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


