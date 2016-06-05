/**
 * Elev:                Pers nr:        Anvnamn ilearn:
 * Anton Fluch          910630-3358     (anfl4215)
 * Georgios Gultidis    911112-0136     (gegu0774)
 */

import javax.swing.*;

abstract class Place extends JComponent {
    protected MainController mainController;
    protected String placeName;
    protected String placeType;
    protected Position position;
    protected Category category;
    protected PlaceTriangle placeTriangle;

    public Place(MainController mainController, String placeType, String PlaceName, int x, int y, Category category) {
        this.mainController = mainController;
        this.placeType = placeType;
        this.placeName = PlaceName;
        position = new Position(x, y);
        this.category = category;
        placeTriangle = new PlaceTriangle(this);
    }

    public Category getCategory() {
        return category;
    }

    public String getCategoryName() {
        String categoryName = null;
        if (category == null) {
            categoryName = "None";
        } else {
            categoryName = category.getName();
        }
        return categoryName;
    }

    public Position getPosition() {
        return position;
    }

    public PlaceTriangle getPlaceTriangle() {
        return placeTriangle;
    }

    public String getPlaceName() {
        return placeName;
    }

    public String getPlaceType() {
        return placeType;
    }

    public MainController getMainController() {
        return mainController;
    }

}
