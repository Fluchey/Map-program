/**
 * Elev:                Pers nr:        Anvnamn ilearn:
 * Anton Fluch          910630-3358     (anfl4215)
 * Georgios Gultidis    911112-0136     (gegu0774)
 */

import javax.swing.*;

public class NamedPlaceForm extends JPanel {
    private JTextField nameField = new JTextField(10);

    public NamedPlaceForm() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        JPanel row = new JPanel();
        row.add(new JLabel("Name:"));
        row.add(nameField);
        add(row);
    }

    public String getName() {
        return nameField.getText();
    }
}