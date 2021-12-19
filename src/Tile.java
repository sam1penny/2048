import java.awt.*;
import java.util.Map;
import static java.util.Map.entry;

public class Tile {
    private int value;
    private static final Map<Integer, Color> colours = Map.ofEntries(entry(0, new Color(150, 150, 150)), // empty tile
                                                entry(2, new Color(238, 228, 218)),
                                                entry(4, new Color(237, 224, 200)),
                                                entry(8, new Color(242, 177, 121)),
                                                entry(16, new Color(245, 149, 99)),
                                                entry(32, new Color(246, 124, 95)),

                                                entry(64, new Color(246, 94, 59)),

                                                entry(128, new Color(237, 207, 114)),

                                                entry(256, new Color(237, 204, 97)),
                                                entry(512, new Color(237, 200, 80)),
                                                entry(1024, new Color(237, 197, 63)),
                                                entry(2048, new Color(237,194,46)));


    Tile(int initialValue) {
        this.value = initialValue;
    }

    Tile() {
        this.value = 0;
    }

    public Color getColour() {
        return colours.get(value);
    }

    public int getValue() {
        return value;
    }

    public void update() {
        value *= 2;
    }

    public void reset() {
        value = 0;
    }

    public boolean isEmpty() {
        return value == 0;
    }

    @Override
    public String toString() {
        return "" + value;
    }
}
