/**
 * Elev:                Pers nr:        Anvnamn ilearn:
 * Anton Fluch          910630-3358     (anfl4215)
 * Georgios Gultidis    911112-0136     (gegu0774)
 */

import javax.swing.*;

public class DescribedPlaceForm extends JPanel {
    private JTextField nameField = new JTextField(10);
    private JTextField descriptionField = new JTextField(10);

    public DescribedPlaceForm() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel row = new JPanel();
        row.add(new JLabel("Name:"));
        row.add(nameField);
        row.add(new JLabel("Description:"));
        row.add(descriptionField);
        add(row);
    }

    public String getPlaceName() {
        return nameField.getText();
    }

    public String getDescription() {
        return descriptionField.getText();
    }
}