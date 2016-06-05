/**
 * Elev:                Pers nr:        Anvnamn ilearn:
 * Anton Fluch          910630-3358     (anfl4215)
 * Georgios Gultidis    911112-0136     (gegu0774)
 */

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.util.HashSet;

public class FileStorage {
    private MainController mainController;
    private boolean saved = true;

    public FileStorage(MainController mainController) {
        this.mainController = mainController;
    }

    public void loadNewPlaces() {
        if (!mainController.getFileStorage().getSaved()) {
            int answer = JOptionPane.showConfirmDialog(mainController.getSuperFrame(), "Unsaved changes, proceed anyway?", "Unsaved changes", JOptionPane.OK_CANCEL_OPTION);
            if (answer != JOptionPane.OK_OPTION) {
                return;
            }
        }

        try {
            JFileChooser fileChooser = new JFileChooser();
            FileFilter pictureFilter = new FileNameExtensionFilter("Platser", "places", "placesUTF8");
            fileChooser.setFileFilter(pictureFilter);

            int result = fileChooser.showOpenDialog(mainController.getSuperFrame());
            if (result != JFileChooser.APPROVE_OPTION) {
                JOptionPane.showMessageDialog(mainController.getSuperFrame(), "Ladda platser avbruten!");
                return;
            }
            mainController.getDataStorage().emptyAllPlaces();
            File file = fileChooser.getSelectedFile();
            String FILE_PATH = file.getAbsolutePath();
            FileReader inFile = new FileReader(FILE_PATH);
            BufferedReader in = new BufferedReader(inFile);
            String line;

            while ((line = in.readLine()) != null) {
                String[] tokens = line.split(",");
                String typeOfPlace = tokens[0];
                String categoryString = tokens[1];
                int xPixel = Integer.valueOf(tokens[2]);
                int yPixel = Integer.valueOf(tokens[3]);
                String name = tokens[4];
                if (typeOfPlace.equals("Named")) {
                    mainController.getSuperFrame().addNewNamedPlaceFromFile(typeOfPlace, name, xPixel, yPixel, categoryString);
                } else if (typeOfPlace.equals("Described")) {
                    String description = tokens[5];
                    mainController.getSuperFrame().addNewDescribedPlaceFromFile(typeOfPlace, name, xPixel, yPixel, categoryString, description);
                }
            }
            saved = true;
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(mainController.getSuperFrame(), "Can not open");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(mainController.getSuperFrame(), "Fel");
        }
    }

    public void savePlaces(HashSet<Place> allPlaces) {
        try {
            JFileChooser fileChooser = new JFileChooser();

            int result = fileChooser.showOpenDialog(mainController.getSuperFrame());
            if (result != JFileChooser.APPROVE_OPTION) {
                return;
            }

            File file = fileChooser.getSelectedFile();
            String FILE_PATH = file.getAbsolutePath();

            FileWriter outFile = new FileWriter(FILE_PATH);
            PrintWriter out = new PrintWriter(outFile);
            for (Place p : allPlaces) {
                if (p instanceof NamedPlace) {
                    NamedPlace np = (NamedPlace) p;
                    out.println(np.getPlaceType() + "," + np.getCategoryName() + "," + np.getPosition().getxPixel() + "," + np.getPosition().getyPixel() + "," + np.getPlaceName());
                } else if (p instanceof DescribedPlace) {
                    DescribedPlace dp = (DescribedPlace) p;
                    out.println(dp.getPlaceType() + "," + dp.getCategoryName() + "," + dp.getPosition().getxPixel() + "," + dp.getPosition().getyPixel() + "," + dp.getPlaceName() + "," + dp.getDescription());
                }
            }
            out.close();
            outFile.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Can not open jarvafaltet.places");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
        saved = true;
    }

    public void setUnSaved() {
        saved = false;
    }

    public boolean getSaved() {
        return saved;
    }

    public String loadNewMapPicture(SuperFrame superFrame) {
        JFileChooser fileChooser = new JFileChooser();
        FileFilter pictureFilter = new FileNameExtensionFilter("Bilder", "jpg", "gif", "png");
        fileChooser.setFileFilter(pictureFilter);

        int result = fileChooser.showOpenDialog(superFrame);
        if (result != JFileChooser.APPROVE_OPTION) {
            return String.valueOf(result);
        }

        File file = fileChooser.getSelectedFile();
        String IMG_PATH = file.getAbsolutePath();
        saved = true;

        return IMG_PATH;
    }
}
