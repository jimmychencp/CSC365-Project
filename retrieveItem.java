import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;


public class retrieveItem extends JPanel implements ActionListener {
    JFrame f;
    JLabel title;
    JLabel itemID;
    JTextField titemID;
    JButton submit;

    Connection connect;
    public retrieveItem() {

        f = new JFrame();
        f.setLayout(new GridBagLayout());
        f.setSize(400, 100);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 0;
        c.gridx = 0;

        title = new JLabel("Item Lookup");
        c.gridy = 0;
        c.gridx = 0;
        c.gridwidth = 2;
        f.add(title, c);

        c.gridwidth = 1;
        itemID = new JLabel("Item ID");
        c.gridx = 0;
        c.gridy = 1;
        f.add(itemID, c);

        titemID = new JTextField();
        c.gridx = 1;
        c.weightx = 1;
        c.ipadx = 10;
        c.gridy = 1;
        f.add(titemID, c);

        submit = new JButton("Submit");
        c.gridx = 0;
        c.gridy = 9;
        c.gridwidth = 2;
        f.add(submit, c);

        submit.addActionListener(this);

        f.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String itemID = titemID.getText();
        JFrame queryitem = new JFrame();
        DefaultTableModel model = new DefaultTableModel();
        JTable jtable = new JTable(model);
        JScrollPane sp = new JScrollPane(jtable);
        model.addColumn("Type");
        model.addColumn("Name");
        model.addColumn("Author");
        model.addColumn("Availability");
        model.addColumn("LibraryID");


        String print = String.format("SELECT * FROM Inventory WHERE inventoryID = %d;", Integer.parseInt(itemID));

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
            String itemType;
            String itemName;
            String itemAuthor;
            String itemAvailability;
            String libraryID;
            ResultSet rs;

            rs = stmn.executeQuery(print);
            while (rs.next()) {
                itemType = rs.getString(2);
                itemName = rs.getString(3);
                itemAuthor = rs.getString(4);
                itemAvailability = rs.getString(5);
                libraryID = String.valueOf(rs.getInt(6));
                model.addRow(new Object[]{ itemType, itemName, itemAuthor, itemAvailability, libraryID });
                queryitem.add(sp);
            }

            queryitem.setSize(500,500);
            queryitem.setVisible(true);

            stmn.close();

            connect.commit();
            connect.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}

