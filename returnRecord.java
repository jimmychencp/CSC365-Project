import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class returnRecord extends JPanel implements ActionListener {

    JFrame f;
    JLabel title;
    JLabel returnID;
    JTextField treturnID;
    JLabel inventoryID;
    JTextField tinventoryID;
    JLabel borrowID;
    JTextField tborrowID;
    JLabel libraryID;
    JTextField tlibraryID;
    JLabel date;
    JTextField tdate;
    JButton submit;
    Connection connect;
    public returnRecord(){

        f = new JFrame();
        f.setLayout(new GridBagLayout());
        f.setSize(400, 300);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 0;
        c.gridx = 0;

        title = new JLabel("Return an inventory");
        c.gridy = 0;
        c.gridx = 0;
        c.gridwidth = 2;
        f.add(title, c);

        c.gridwidth = 1;
        returnID = new JLabel("Return ID");
        c.gridx = 0;
        c.gridy = 1;
        f.add(returnID, c);

        treturnID = new JTextField();
        c.gridx = 1;
        c.weightx = 1;
        c.ipadx = 10;
        c.gridy = 1;
        f.add(treturnID, c);

        inventoryID = new JLabel("Inventory ID");
        c.gridx = 0;
        c.gridy = 2;
        f.add(inventoryID, c);

        tinventoryID = new JTextField();
        c.weightx = 1;
        c.gridx = 1;
        c.ipadx = 10;
        c.gridy = 2;
        f.add(tinventoryID, c);

        borrowID = new JLabel("Borrow ID");
        c.gridx = 0;
        c.gridy = 3;
        f.add(borrowID, c);

        tborrowID = new JTextField();
        c.gridx = 1;
        c.weightx = 1;
        c.ipadx = 10;
        c.gridy = 3;
        f.add(tborrowID, c);

        libraryID = new JLabel("Library ID");
        c.gridx = 0;
        c.gridy = 4;
        f.add(libraryID, c);

        tlibraryID = new JTextField();
        c.gridx = 1;
        c.weightx = 1;
        c.ipadx = 10;
        c.gridy = 4;
        f.add(tlibraryID, c);

        date = new JLabel("Date");
        c.gridx = 0;
        c.gridy = 5;
        f.add(date, c);

        tdate = new JTextField();
        c.gridx = 1;
        c.weightx = 1;
        c.ipadx = 10;
        c.gridy = 5;
        f.add(tdate, c);

        submit = new JButton("Submit");
        c.gridx = 0;
        c.gridy = 6;
        c.gridwidth = 2;
        f.add(submit, c);

        submit.addActionListener(this);

        f.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String rid = treturnID.getText();
        String invID = tinventoryID.getText();
        String bid = tborrowID.getText();
        String lid = tlibraryID.getText();
        String rdate = tdate.getText();


        String print = String.format("INSERT INTO ReturnRecord (returnID, inventoryID, borrowID, libraryID, returnDate) " +
                        "VALUES (%d, %d, %d, %d,\"%s\");", Integer.parseInt(rid),
                Integer.parseInt(invID),Integer.parseInt(bid), Integer.parseInt(lid), rdate);
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