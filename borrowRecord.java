import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class borrowRecord extends JPanel implements ActionListener {

    JFrame f;
    JLabel title;
    JLabel borrowID;
    JTextField tborrowID;
    JLabel inventoryID;
    JTextField tinventoryID;
    JLabel memberID;
    JTextField tmemberID;

    JLabel memberName;
    JTextField tmemberName;
    JLabel emplID;
    JTextField templID;
    JLabel emplName;
    JTextField templName;
    JLabel libraryID;
    JTextField tlibraryID;
    JLabel date;
    JTextField tdate;
    JButton submit;

    Connection connect;
    public borrowRecord(){

        f = new JFrame();
        f.setLayout(new GridBagLayout());
        f.setSize(400, 400);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 0;
        c.gridx = 0;

        title = new JLabel("Borrow an inventory");
        c.gridy = 0;
        c.gridx = 0;
        c.gridwidth = 2;
        f.add(title, c);

        c.gridwidth = 1;
        borrowID = new JLabel("Borrow ID");
        c.gridx = 0;
        c.gridy = 1;
        f.add(borrowID, c);

        tborrowID = new JTextField();
        c.gridx = 1;
        c.weightx = 1;
        c.ipadx = 10;
        c.gridy = 1;
        f.add(tborrowID, c);

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

        memberID = new JLabel("Member ID");
        c.gridx = 0;
        c.gridy = 3;
        f.add(memberID, c);

        tmemberID = new JTextField();
        c.gridx = 1;
        c.weightx = 1;
        c.ipadx = 10;
        c.gridy = 3;
        f.add(tmemberID, c);

        emplID = new JLabel("Employee ID");
        c.gridx = 0;
        c.gridy = 5;
        f.add(emplID, c);

        templID = new JTextField();
        c.gridx = 1;
        c.weightx = 1;
        c.ipadx = 10;
        c.gridy = 5;
        f.add(templID, c);


        libraryID = new JLabel("Library ID");
        c.gridx = 0;
        c.gridy = 7;
        f.add(libraryID, c);

        tlibraryID = new JTextField();
        c.gridx = 1;
        c.weightx = 1;
        c.ipadx = 10;
        c.gridy = 7;
        f.add(tlibraryID, c);

        date = new JLabel("Date");
        c.gridx = 0;
        c.gridy = 8;
        f.add(date, c);

        tdate = new JTextField();
        c.gridx = 1;
        c.weightx = 1;
        c.ipadx = 10;
        c.gridy = 8;
        f.add(tdate, c);

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
        String bid = tborrowID.getText();
        String invID = tinventoryID.getText();
        String memID = tmemberID.getText();
        String emplID = templID.getText();
        String lbID = tlibraryID.getText();
        String bdate = tdate.getText();

        String print = String.format("INSERT INTO BorrowRecord (borrowID, inventoryID, memberID,  employeeID,  libraryID, borrowDate) " +
                        "VALUES (%d, %d, %d, %d, %d, \"%s\");", Integer.parseInt(bid),
                Integer.parseInt(invID),Integer.parseInt(memID) , Integer.parseInt(emplID),Integer.parseInt(lbID), bdate);
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

