/**
 * Elev:                Pers nr:        Anvnamn ilearn:
 * Anton Fluch          910630-3358     (anfl4215)
 * Georgios Gultidis    911112-0136     (gegu0774)
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TopPanel extends JPanel {
    private SuperFrame superFrame;
    private JPanel topPanel;
    private String[] placeBoxObjects;
    private JComboBox<String> typeOfPlaceBox;
    private JTextField searchField;
    private JButton searchBtn;
    private JButton hideBtn;
    private JButton removeBtn;
    private JButton whatIsHereBtn;

    public TopPanel(SuperFrame superFrame) {
        this.superFrame = superFrame;
        topPanel = new JPanel();
        JLabel newLabel = new JLabel("New");
        placeBoxObjects = new String[]{"Named", "Described"};
        typeOfPlaceBox = new JComboBox(placeBoxObjects);
        searchField = new JTextField(10);
        searchField.setText("Search");
        searchBtn = new JButton("Search");
        hideBtn = new JButton("Hide");
        removeBtn = new JButton("Remove");
        whatIsHereBtn = new JButton("What is here?");

        topPanel.add(newLabel);
        topPanel.add(typeOfPlaceBox);
        topPanel.add(searchField);
        topPanel.add(searchBtn);
        topPanel.add(hideBtn);
        topPanel.add(removeBtn);
        topPanel.add(whatIsHereBtn);

        enableComponentsAfterMapLoad(false);
        /** Detta för att man inte ska kunna välja något innan man laddat en karta */

        searchField.addMouseListener(new searchFieldListener());
        searchBtn.addActionListener(new searchBtnListener());
        typeOfPlaceBox.addActionListener(new typeOfPlaceBoxListener());

        hideBtn.addActionListener(
                ave -> superFrame.getMainController().getDataStorage().hideMarkedPlaces()
        );
        removeBtn.addActionListener(
                ave -> superFrame.removeMarkedPlaces()
        );
        whatIsHereBtn.addActionListener(
                ave -> superFrame.getPicturePanel().initShowPlacesByPosition()
        );
    }

    public JPanel getTopPanel() {
        return topPanel;
    }

    public void enableComponentsAfterMapLoad(boolean b) {

        searchField.setEnabled(b);
        searchBtn.setEnabled(b);
        hideBtn.setEnabled(b);
        removeBtn.setEnabled(b);
        whatIsHereBtn.setEnabled(b);
    }

    public void setPlaceCreated() {
        typeOfPlaceBox.setEnabled(true);
    }

    public void setComponentsActive(boolean b) {
        typeOfPlaceBox.setEnabled(b);
        searchField.setEnabled(b);
        searchBtn.setEnabled(b);
        hideBtn.setEnabled(b);
        removeBtn.setEnabled(b);
        whatIsHereBtn.setEnabled(b);
    }

    public JComboBox<String> getTypeOfPlaceBox() {
        return typeOfPlaceBox;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public JButton getSearchBtn() {
        return searchBtn;
    }

    public JButton getHideBtn() {
        return hideBtn;
    }

    public JButton getRemoveBtn() {
        return removeBtn;
    }

    public JButton getWhatIsHereBtn() {
        return whatIsHereBtn;
    }

    private class typeOfPlaceBoxListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ave) {
            if (superFrame.getPicturePanel().getPicture() == null || superFrame.getPicturePanel().getPicture().getDescription() == null) {
                JOptionPane.showMessageDialog(superFrame, "Du måste ladda karta först", "Fel", JOptionPane.OK_OPTION);
                return;
            }
            superFrame.getEastPanel().getCategoryList().clearSelection();
            typeOfPlaceBox.setEnabled(false);
            setComponentsActive(false);
            superFrame.getEastPanel().getHideCategoryBtn().setEnabled(false);
            superFrame.getEastPanel().setActiveListListener(false);
            superFrame.getTheMenuBar().getArchive().setEnabled(false);
            /** Avaktivera alla andra komponenter så att endast kartan och kategori går att trycka på */

            superFrame.getPicturePanel().initNewPlace(((String) typeOfPlaceBox.getSelectedItem()));
        }
    }

    private class searchFieldListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent mev) {
            searchField.setText("");
        }
    }

    private class searchBtnListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent ave) {
            String placeName = searchField.getText();
            if (placeName.equals("Search")) {
                JOptionPane.showMessageDialog(superFrame, "Du måste ange text i sökrutan!");
                return;
            }
            superFrame.getMainController().getDataStorage().showPlacesByName(placeName);
            searchField.setText("Search");
        }
    }

}
