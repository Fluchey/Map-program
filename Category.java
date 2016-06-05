/**
 * Elev:                Pers nr:        Anvnamn ilearn:
 * Anton Fluch          910630-3358     (anfl4215)
 * Georgios Gultidis    911112-0136     (gegu0774)
 */

import java.awt.*;

public enum Category {
    BUSS(Color.RED, "Buss"), TUNNELBANA(Color.BLUE, "Tunnelbana"), TÅG(Color.GREEN, "Tåg");
    private Color c;
    private String name;

    Category(Color c, String name) {
        this.c = c;
        this.name = name;
    }

    public Color getColor() {
        return c;
    }

    public String getName() {
        return name;
    }
}
