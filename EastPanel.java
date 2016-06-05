/**
 * Elev:                Pers nr:        Anvnamn ilearn:
 * Anton Fluch          910630-3358     (anfl4215)
 * Georgios Gultidis    911112-0136     (gegu0774)
 */

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EastPanel extends JPanel {
    private SuperFrame superFrame;
    private JPanel eastPanel;
    private JList categoryList;
    private JButton hideCategoryBtn;
    private JLabel sizeLabel;
    private JLabel allSize;
    private JLabel markedLabel;
    private JLabel markedSize;
    private JLabel categoryLabel;
    private JLabel categorySize;
    private JLabel nameLabel;
    private JLabel nameSize;
    private boolean activeListListener;     /** Till för att blockera att platser visas om man efter att ha valt att skapa en ny plats trycker på listan */

    public EastPanel(SuperFrame superFrame){
        this.superFrame = superFrame;
        eastPanel = new JPanel();
        eastPanel.setLayout(new BoxLayout(eastPanel, BoxLayout.Y_AXIS));
        JLabel categories = new JLabel("Categories");
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement("Buss");
        listModel.addElement("Tunnelbana");
        listModel.addElement("Tåg");
        categoryList = new JList<>(listModel);
        categoryList.setSelectionMode(DefaultListSelectionModel.SINGLE_SELECTION);
        hideCategoryBtn = new JButton("Hide Category");

        sizeLabel = new JLabel("All places:");
        sizeLabel.setFont(new Font("Serif", Font.BOLD, 14));
        allSize = new JLabel("0");

        markedLabel = new JLabel("Marked places:");
        markedLabel.setFont(new Font("Serif", Font.BOLD, 14));
        markedSize = new JLabel("0");

        categoryLabel = new JLabel("Number of different categories:");
        categoryLabel.setFont(new Font("Serif", Font.BOLD, 14));
        categorySize = new JLabel("0");

        nameLabel = new JLabel("Number of different names:");
        nameLabel.setFont(new Font("Serif", Font.BOLD, 14));
        nameSize = new JLabel("0");

        eastPanel.add(Box.createRigidArea(new Dimension(70,70)));

        eastPanel.add(categories);
        eastPanel.add(categoryList);
        eastPanel.add(hideCategoryBtn);

        eastPanel.add(Box.createRigidArea(new Dimension(0,70)));

        eastPanel.add(sizeLabel);
        eastPanel.add(allSize);

        eastPanel.add(markedLabel);
        eastPanel.add(markedSize);

        eastPanel.add(categoryLabel);
        eastPanel.add(categorySize);

        eastPanel.add(nameLabel);
        eastPanel.add(nameSize);

        enableComponentsAfterMapLoad(false);
        /** Detta för att man inte ska kunna välja något innan man laddat en karta */

        categoryList.addListSelectionListener(new categoryListListener());
        hideCategoryBtn.addActionListener(new hideCategoryBtnListener());
    }

    public JList getCategoryList() {
        return categoryList;
    }

    public void enableComponentsAfterMapLoad(boolean b){
        categoryList.setEnabled(b);
        hideCategoryBtn.setEnabled(b);
        activeListListener = true;
    }

    public void setSize(int allSize, int markedSize, int categorySize, int nameSize){
        this.allSize.setText(String.valueOf(allSize));
        this.markedSize.setText((String.valueOf(markedSize)));
        this.categorySize.setText((String.valueOf(categorySize)));
        this.nameSize.setText((String.valueOf(nameSize)));
    }

    public void setActiveListListener(boolean b){
        activeListListener = b;
    }

    public JButton getHideCategoryBtn(){
        return hideCategoryBtn;
    }

    public Category getSelectedCategory(){
        Category category = null;
        if(categoryList.getSelectedValue() == "Buss"){
            category = category.BUSS;
        }else if(categoryList.getSelectedValue() == "Tunnelbana"){
            category = category.TUNNELBANA;
        }else if(categoryList.getSelectedValue() == "Tåg"){
            category = category.TÅG;
        }
        return category;
    }

    public Category getCategoryByString(String categoryString){
        Category category = null;
        if(categoryString.equals("Buss")){
            category = category.BUSS;
        }else if(categoryString.equals("Tunnelbana")){
            category = category.TUNNELBANA;
        }else if(categoryString.equals("Tåg")){
            category = category.TÅG;
        }
        return category;
    }

    public JPanel getEastPanel(){
        return eastPanel;
    }

    private class categoryListListener implements ListSelectionListener{
        @Override
        public void valueChanged(ListSelectionEvent lev){
            Category category = null;
            if(activeListListener) {
                if (categoryList.getSelectedValue() == "Buss") {
                    category = category.BUSS;
                } else if (categoryList.getSelectedValue() == "Tunnelbana") {
                    category = category.TUNNELBANA;
                } else if (categoryList.getSelectedValue() == "Tåg") {
                    category = category.TÅG;
                }
                superFrame.getMainController().getDataStorage().showPlacesByCategory(category);
            }
        }
    }

    private class hideCategoryBtnListener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent ave){
            Category category = null;
            if(categoryList.getSelectedValue() == "Buss"){
                category = category.BUSS;
            }else if(categoryList.getSelectedValue() == "Tunnelbana"){
                category = category.TUNNELBANA;
            }else if(categoryList.getSelectedValue() == "Tåg"){
                category = category.TÅG;
            }else if(categoryList.getSelectedValue() == null){
                return;
            }
            superFrame.getMainController().getDataStorage().hidePlacesByCategory(category);
            categoryList.clearSelection();
        }
    }
}
