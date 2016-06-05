/**
 * Elev:                Pers nr:        Anvnamn ilearn:
 * Anton Fluch          910630-3358     (anfl4215)
 * Georgios Gultidis    911112-0136     (gegu0774)
 */

import javax.swing.*;
import java.util.*;

public class DataStorage {
    private MainController mainController;

    private HashMap<String, Set<Place>> placesByName = new HashMap();
    private HashMap<Category, Set<Place>> placesByCategory = new HashMap<>();
    private HashSet<PlaceTriangle> markedPlaces = new HashSet<>();
    private HashMap<Position, Place> placesByPosition = new HashMap<>();

    public DataStorage(MainController mainController) {
        this.mainController = mainController;
    }

    public void storeNewPlace(Place place) {
        Set<Place> tempNameSet = placesByName.get(place.getPlaceName());
        if (tempNameSet == null) {
            tempNameSet = new HashSet<Place>();
            placesByName.put(place.getPlaceName(), tempNameSet);
        }
        tempNameSet.add(place);
        /** Store in placesByName */

        if (place.getCategory() != null) {
            Set<Place> tempCategorySet = placesByCategory.get(place.getCategory());
            if (tempCategorySet == null) {
                tempCategorySet = new HashSet<Place>();
                placesByCategory.put(place.getCategory(), tempCategorySet);
            }
            tempCategorySet.add(place);
        }
        /** Store in placesByCategory */


        placesByPosition.put(place.getPosition(), place);
        /** Store in placesByPosition */

        updateSizeOfStorage();
    }

    public void showPlacesByName(String placeName) {
        if (!placesByName.containsKey(placeName)) {
            JOptionPane.showMessageDialog(mainController.getSuperFrame(), placeName + " hittas inte");
            return;
        }
        for (Place p : placesByName.get(placeName)) {
            p.getPlaceTriangle().setVisibility(true);
            p.getPlaceTriangle().setMarked(true);
            updateMarkedPlaces(p.getPlaceTriangle());
        }
        /** Loopa över det SET av platser som ligger på nyckeln String placeName och utför operationer */

        Iterator<PlaceTriangle> iterator = markedPlaces.iterator();
        HashSet<PlaceTriangle> temp = new HashSet<>();
        while (iterator.hasNext()) {
            PlaceTriangle p = iterator.next();
            if (!p.getPlace().getPlaceName().equals(placeName)) {
                temp.add(p);
                iterator.remove();
            }
        }
        for(PlaceTriangle p : temp){
            p.setMarked(false);
            markedPlaces.remove(p);
        }
        /** Loopa över alla platser som är markerade och inte matchar nyckeln placeName och sätt som omarkerade */

    }

    public void showPlacesByCategory(Category category) {
        if (placesByCategory.get(category) == null) {
            return;
        }
        for (Place p : placesByCategory.get(category)) {
            p.getPlaceTriangle().setVisibility(true);
            updateMarkedPlaces(p.getPlaceTriangle());
        }
        /** Loopa över det SET av platser som ligger på nyckeln Category category och utför operationer */
    }

    public void hidePlacesByCategory(Category category) {
        if (placesByCategory.get(category) == null) {
            return;
        }
        for (Place p : placesByCategory.get(category)) {
            p.getPlaceTriangle().setVisibility(false);
            p.getPlaceTriangle().setFolded(true);
            p.getPlaceTriangle().setMarked(false);
            updateMarkedPlaces(p.getPlaceTriangle());
        }

        /** Loopa över det SET av platser som ligger på nyckeln Category category och utför operationer */
    }

    public void showPlacesByPosition(int xPixel, int yPixel) {
        boolean placeFound = false;
        for (int i = xPixel - 10; i < xPixel + 10; i++) {
            for (int j = yPixel - 10; j < yPixel + 10; j++) {
                Position tempPos = new Position(i, j);
                Place tempPlace = placesByPosition.get(tempPos);
                if (tempPlace != null) {
                    tempPlace.getPlaceTriangle().setVisibility(true);
                    tempPlace.getPlaceTriangle().setFolded(true);
                    placeFound =true;
                }
            }
        }
        if(!placeFound){
            JOptionPane.showMessageDialog(mainController.getSuperFrame(), "Här finns ingen plats!");
        }

    }

    public void hideMarkedPlaces() {
        if (markedPlaces.isEmpty()) {
            JOptionPane.showMessageDialog(mainController.getSuperFrame(), "Inga platser markerade!");
        }
        Set<PlaceTriangle> tempPlaceTriangle = new HashSet<>(markedPlaces);
        tempPlaceTriangle.forEach(pt -> pt.setMarked(false));
        tempPlaceTriangle.forEach(pt -> pt.setVisibility(false));
        tempPlaceTriangle.forEach(pt -> updateMarkedPlaces(pt));

        /** Loopa över alla platser som är markerade och sätt som omarkerade */
    }

    public void updateMarkedPlaces(PlaceTriangle p) {
        if (markedPlaces.contains(p) && p.getMarked() == false) {
            markedPlaces.remove(p);
        } else if (!markedPlaces.contains(p) && p.getMarked() == true) {
            markedPlaces.add(p);
        }
        updateSizeOfStorage();

        /** Uppdaterar det SET som håller koll på vilka platser som just nu är markerade */
    }

    public void updateSizeOfStorage() {
        mainController.getSuperFrame().getEastPanel().setSize(placesByPosition.size(), markedPlaces.size(), placesByCategory.size(), placesByName.size());
        /** Uppdaterar aktuell storlek för de olika datasamlingarna så att de hålls uppdaterade för användaren */
    }

    public HashSet<Place> removeMarkedPlaces() {
        if (markedPlaces.isEmpty()) {
            JOptionPane.showMessageDialog(mainController.getSuperFrame(), "Inga platser markerade!");
        }
        HashSet<Place> tempMarkedPlaceSet = new HashSet<>();
        markedPlaces.forEach(pt -> tempMarkedPlaceSet.add(pt.getPlace()));
        /** Extrahera ut de Plats objekt ur de PlaceTriangle objekt som är markerade och lägg i en egen SET */

        for (Place p : tempMarkedPlaceSet) {
            Set<Place> tempNameSet = placesByName.get(p.getPlaceName());
            tempNameSet.remove(p);
            placesByName.remove(p.getPlaceName());
            placesByName.put(p.getPlaceName(), tempNameSet);
            if (placesByName.get(p.getPlaceName()).isEmpty()) {
                placesByName.remove(p.getPlaceName());
            }
            /** placesByName */

            if (p.getCategory() != null) {
                Set<Place> tempCategorySet = new HashSet<>(placesByCategory.get(p.getCategory()));
                tempCategorySet.remove(p);
                placesByCategory.remove(p.getCategory());
                placesByCategory.put(p.getCategory(), tempCategorySet);
                if (placesByCategory.get(p.getCategory()).isEmpty()) {
                    placesByCategory.remove(p.getCategory());
                }
            }
            /** placesByCategory */

            placesByPosition.remove(p.getPosition());
            /** placesByPosition */
        }
        markedPlaces.clear();
        updateSizeOfStorage();
        return tempMarkedPlaceSet;
        /** Den returnerar ett SET för att metoden som anropar ska kunna ta bort objekt från picturePanel*/
    }

    public HashSet<Place> getAllPlaces() {
        HashSet<Place> allPlaces = new HashSet(placesByPosition.values());
        return allPlaces;
        /** Det här fungerar eftersom vi vet att alla values är unika? */
    }

    public void emptyAllPlaces() {
        for (Place p : placesByPosition.values()) {
            mainController.getSuperFrame().getPicturePanel().remove(p.getPlaceTriangle());
        }
        placesByName.clear();
        placesByCategory.clear();
        markedPlaces.clear();
        placesByPosition.clear();

    }
}
