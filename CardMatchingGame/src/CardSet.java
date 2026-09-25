import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class CardSet {
    private final CardSetType type;
    private final List<String> images;

    public CardSet(CardSetType type, List<String> images) {
        this.type = type;
        this.images = new ArrayList<>(images);
    }

    public CardSetType getType() {
        return type;
    }

    public List<String> getImages() {
        return Collections.unmodifiableList(images);
    }
}
