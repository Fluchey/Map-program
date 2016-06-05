/**
 * Elev:                Pers nr:        Anvnamn ilearn:
 * Anton Fluch          910630-3358     (anfl4215)
 * Georgios Gultidis    911112-0136     (gegu0774)
 */

public class DescribedPlace extends Place {
    private String description;

    public DescribedPlace(MainController mainController, String placeType, String name, int x, int y, Category category, String description) {
        super(mainController, placeType, name, x, y, category);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}