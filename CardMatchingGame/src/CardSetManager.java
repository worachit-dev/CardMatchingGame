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

        cardSets.add(createAnimalsSet());
        cardSets.add(createFruitsSet());
        cardSets.add(createNumbersSet());
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

    public static void main(String[] args) {

    CardSetManager manager = new CardSetManager();

    CardSet set = manager.selectRandomSet();

    System.out.println("Selected set: " + set.getType());
    System.out.println("Images: " + set.getImages());
}
}

