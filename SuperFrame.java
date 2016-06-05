/**
 * Elev:                Pers nr:        Anvnamn ilearn:
 * Anton Fluch          910630-3358     (anfl4215)
 * Georgios Gultidis    911112-0136     (gegu0774)
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashSet;

public class SuperFrame extends JFrame {
    private MainController mainController;
    private MenuBar menuBar;
    private TopPanel topPanel;
    private EastPanel eastPanel;
    private PicturePanel picturePanel;
    private JScrollPane scrollPane;

    public SuperFrame(MainController mainController) {
        this.mainController = mainController;
        menuBar = new MenuBar(this);
        setJMenuBar(menuBar.getMenuBar());

        topPanel = new TopPanel(this);
        this.add(topPanel.getTopPanel(), BorderLayout.NORTH);

        eastPanel = new EastPanel(this);
        this.add(eastPanel.getEastPanel(), BorderLayout.EAST);

        picturePanel = new PicturePanel(this);
        scrollPane = new JScrollPane(picturePanel);
        scrollPane.setViewportView(picturePanel);
        this.add(scrollPane, BorderLayout.CENTER);

        setTitle("Inlupp2");
        pack();
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new finishListener());
        setVisible(true);
    }

    public void addNewPlace(String typeOfPlace, int xPixel, int yPixel) {
        Place place = null;
        if (typeOfPlace.equals("Named")) {
            NamedPlaceForm nf = new NamedPlaceForm();
            int answer = JOptionPane.showConfirmDialog(this, nf, "New Named Place", JOptionPane.OK_CANCEL_OPTION);
            if (answer != JOptionPane.OK_OPTION) {
                topPanel.setPlaceCreated();
                eastPanel.setActiveListListener(true);
                topPanel.setComponentsActive(true);
                eastPanel.getHideCategoryBtn().setEnabled(true);
                menuBar.getArchive().setEnabled(true);
                return;
            }
            place = new NamedPlace(mainController, typeOfPlace, nf.getName(), xPixel, yPixel, eastPanel.getSelectedCategory());
        } else if (typeOfPlace.equals("Described")) {
            DescribedPlaceForm df = new DescribedPlaceForm();
            int answer = JOptionPane.showConfirmDialog(this, df, "New described place", JOptionPane.OK_CANCEL_OPTION);
            if (answer != JOptionPane.OK_OPTION) {
                topPanel.setPlaceCreated();
                eastPanel.setActiveListListener(true);
                topPanel.setComponentsActive(true);
                eastPanel.getHideCategoryBtn().setEnabled(true);
                menuBar.getArchive().setEnabled(true);
                return;
            }
            place = new DescribedPlace(mainController, typeOfPlace, df.getPlaceName(), xPixel, yPixel, eastPanel.getSelectedCategory(), df.getDescription());
        }
        eastPanel.setActiveListListener(true);
        topPanel.setComponentsActive(true);
        eastPanel.getHideCategoryBtn().setEnabled(true);
        menuBar.getArchive().setEnabled(true);

        picturePanel.add(place.getPlaceTriangle());
        mainController.getDataStorage().storeNewPlace(place);
        eastPanel.getCategoryList().clearSelection();
        mainController.getFileStorage().setUnSaved();
        topPanel.setPlaceCreated();
        repaint();

    }

    public void addNewNamedPlaceFromFile(String typeOfPlace, String name, int xPixel, int yPixel, String categoryString) {
        Place place = null;
        place = new NamedPlace(mainController, typeOfPlace, name, xPixel, yPixel, eastPanel.getCategoryByString(categoryString));
        picturePanel.add(place.getPlaceTriangle());
        mainController.getDataStorage().storeNewPlace(place);
        repaint();
    }

    public void addNewDescribedPlaceFromFile(String typeOfPLace, String name, int xPixel, int yPixel, String categoryString, String description) {
        Place place = null;
        place = new DescribedPlace(mainController, typeOfPLace, name, xPixel, yPixel, eastPanel.getCategoryByString(categoryString), description);
        picturePanel.add(place.getPlaceTriangle());
        mainController.getDataStorage().storeNewPlace(place);
        repaint();
    }

    public void loadNewMapPicture() {
        if (!mainController.getFileStorage().getSaved()) {
            int answer = JOptionPane.showConfirmDialog(SuperFrame.this, "Unsaved changes, proceed anyway?", "Unsaved changes", JOptionPane.OK_CANCEL_OPTION);
            if (answer != JOptionPane.OK_OPTION) {
                return;
            }
        }

        String IMG_PATH = mainController.getFileStorage().loadNewMapPicture(this);
        try {
            if (Integer.parseInt(IMG_PATH) != JFileChooser.APPROVE_OPTION) {
                JOptionPane.showMessageDialog(this, "Ladda karta avbruten");
                return;
            }
        } catch (NumberFormatException e) {
        }

        mainController.getDataStorage().emptyAllPlaces();
        menuBar.getSave().setEnabled(true);
        menuBar.getLoadPlaces().setEnabled(true);
        topPanel.enableComponentsAfterMapLoad(true);
        eastPanel.enableComponentsAfterMapLoad(true);
        mainController.getDataStorage().updateSizeOfStorage();
        picturePanel.setPicture(IMG_PATH);
        scrollPane.setViewportView(picturePanel);
        pack();
    }

    public void finish() {
        if (!mainController.getFileStorage().getSaved()) {
            int answer = JOptionPane.showConfirmDialog(SuperFrame.this, "Unsaved changes, exit anyway?", "Unsaved changes", JOptionPane.OK_CANCEL_OPTION);
            if (answer == JOptionPane.OK_OPTION) {
                System.exit(0);
            }
        } else {
            System.exit(0);
        }
    }

    public void removeMarkedPlaces() {
        HashSet<Place> tempMarkedPlaces = mainController.getDataStorage().removeMarkedPlaces();
        for (Place p : tempMarkedPlaces) {
            PlaceTriangle temp = p.getPlaceTriangle();
            picturePanel.remove(temp);
        }
        mainController.getFileStorage().setUnSaved();
        repaint();
    }

    public MainController getMainController() {
        return mainController;
    }

    public MenuBar getTheMenuBar() {
        return menuBar;
    }

    public TopPanel getTopPanel() {
        return topPanel;
    }

    public EastPanel getEastPanel() {
        return eastPanel;
    }

    public PicturePanel getPicturePanel() {
        return picturePanel;
    }

    private class finishListener extends WindowAdapter {
        @Override
        public void windowClosing(WindowEvent wev) {
            if (!mainController.getFileStorage().getSaved()) {
                int answer = JOptionPane.showConfirmDialog(SuperFrame.this, "Unsaved changes, exit anyway?", "Unsaved changes", JOptionPane.OK_CANCEL_OPTION);
                if (answer == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }
            } else {
                System.exit(0);
            }
        }
    }
}
