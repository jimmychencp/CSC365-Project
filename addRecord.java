import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class addRecord extends JPanel implements ActionListener {

    JFrame f;
    JLabel title;
    JLabel inventoryID;
    JTextField tinventoryID;
    JLabel inventoryName;
    JTextField tinventoryName;
    JLabel inventoryAuthor;
    JTextField tinventoryAuthor;
    JLabel inventoryType;
    JTextField tinventoryType;
    JLabel availability;
    JTextField tavailability;
    JLabel libID;
    JTextField tlibID;
    JButton submit;
    Connection connect;
    public addRecord(){

        f = new JFrame();
        f.setLayout(new GridBagLayout());
        f.setSize(400, 400);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 0;
        c.gridx = 0;

        title = new JLabel("Add new inventory");
        c.gridy = 0;
        c.gridx = 0;
        c.gridwidth = 2;
        f.add(title, c);

        c.gridwidth = 1;
        inventoryID = new JLabel("Inventory id");
        c.gridx = 0;
        c.gridy = 1;
        f.add(inventoryID, c);

        tinventoryID = new JTextField();
        c.gridx = 1;
        c.weightx = 1;
        c.ipadx = 10;
        c.gridy = 1;
        f.add(tinventoryID, c);

        inventoryName = new JLabel("Item name");
        c.gridx = 0;
        c.gridy = 2;
        f.add(inventoryName, c);

        tinventoryName = new JTextField();
        c.gridx = 1;
        c.weightx = 1;
        c.ipadx = 10;
        c.gridy = 2;
        f.add(tinventoryName, c);

        inventoryAuthor = new JLabel("Item's author or artist");
        c.gridx = 0;
        c.gridy = 3;
        f.add(inventoryAuthor, c);

        tinventoryAuthor = new JTextField();
        c.gridx = 1;
        c.weightx = 1;
        c.ipadx = 10;
        c.gridy = 3;
        f.add(tinventoryAuthor, c);

        inventoryType = new JLabel("Type of item");
        c.gridx = 0;
        c.gridy = 4;
        f.add(inventoryType, c);

        tinventoryType = new JTextField();
        c.gridx = 1;
        c.weightx = 1;
        c.ipadx = 10;
        c.gridy = 4;
        f.add(tinventoryType, c);

        availability = new JLabel("Availability");
        c.gridx = 0;
        c.gridy = 5;
        f.add(availability, c);

        tavailability = new JTextField();
        c.weightx = 1;
        c.gridx = 1;
        c.ipadx = 10;
        c.gridy = 5;
        f.add(tavailability, c);

        libID = new JLabel("Library ID");
        c.gridx = 0;
        c.gridy = 6;
        f.add(libID, c);

        tlibID = new JTextField();
        c.weightx = 1;
        c.gridx = 1;
        c.ipadx = 10;
        c.gridy = 6;
        f.add(tlibID, c);

        submit = new JButton("Submit");
        c.gridx = 0;
        c.gridy = 7;
        c.gridwidth = 2;
        f.add(submit, c);

        submit.addActionListener(this);

        f.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String iid = tinventoryID.getText();
        String invType = tinventoryType.getText();
        String invname = tinventoryName.getText();
        String author = tinventoryAuthor.getText();
        String avail = tavailability.getText();
        String library = tlibID.getText();
        String print = String.format("INSERT INTO Inventory (inventoryID, inventoryType, iname, author, availability, libraryID) VALUES (%d,\"%s\",\"%s\",\"%s\",\"%s\", %d);", Integer.parseInt(iid),
                invType, invname ,author, avail, Integer.parseInt(library));
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connect = DriverManager.getConnection(
                    String.format("jdbc:mysql://ambari-node5.csc.calpoly.edu:3306/%s", DatabaseLogin.getUsername()),
                    DatabaseLogin.getUsername(),
                    DatabaseLogin.getPassword());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try
        {
            connect.setAutoCommit(false);


            Statement stmn = connect.createStatement();

            stmn.executeUpdate(print);

            stmn.close();

            connect.commit();
            connect.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
