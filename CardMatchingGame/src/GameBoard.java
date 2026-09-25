import java.util.*;

public class GameBoard {

    private final List<Card> cards;

    public GameBoard() {
        cards = new ArrayList<>();
    }

    public void createCards(CardSet cardSet) {

        cards.clear();

        for (String image : cardSet.getImages()) {

            cards.add(new Card(image));
            cards.add(new Card(image));
        }

        shuffleCards();
    }

    public void shuffleCards() {
        Collections.shuffle(cards);
    }

    public Card getCard(int index) {
        return cards.get(index);
    }

    public int getCardCount() {
        return cards.size();
    }

    public List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }
}
