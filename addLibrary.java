import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class addLibrary extends JPanel implements ActionListener {

    JFrame f;
    JLabel title;
    JLabel libraryID;
    JTextField tlibraryID;
    JLabel libraryName;
    JTextField tlibraryName;
    JLabel libraryAddress;
    JTextField tlibraryAddress;
    JButton submit;
    Connection connect;
    public addLibrary(){

        f = new JFrame();
        f.setLayout(new GridBagLayout());
        f.setSize(400, 200);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 0;
        c.gridx = 0;

        title = new JLabel("Add new employee");
        c.gridy = 0;
        c.gridx = 0;
        c.gridwidth = 2;
        f.add(title, c);

        c.gridwidth = 1;

        libraryID = new JLabel("Library ID");
        c.gridx = 0;
        c.gridy = 1;
        f.add(libraryID, c);

        tlibraryID = new JTextField();
        c.gridx = 1;
        c.weightx = 1;
        c.ipadx = 10;
        c.gridy = 1;
        f.add(tlibraryID, c);

        libraryName = new JLabel("Library Name");
        c.gridx = 0;
        c.gridy = 2;
        f.add(libraryName, c);

        tlibraryName = new JTextField();
        c.gridx = 1;
        c.weightx = 1;
        c.ipadx = 10;
        c.gridy = 2;
        f.add(tlibraryName, c);

        libraryAddress = new JLabel("Library Address");
        c.gridx = 0;
        c.gridy = 3;
        f.add(libraryAddress, c);

        tlibraryAddress = new JTextField();
        c.gridx = 1;
        c.weightx = 1;
        c.ipadx = 10;
        c.gridy = 3;
        f.add(tlibraryAddress, c);

        submit = new JButton("Submit");
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 2;
        f.add(submit, c);

        submit.addActionListener(this);

        f.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String tlibID = tlibraryID.getText();
        String tlibname = tlibraryName.getText();
        String tlibaddress = tlibraryAddress.getText();
        String print = String.format("INSERT INTO Library (libraryID, libraryName, libraryAddress) VALUES (%d,\"%s\",\"%s\");", Integer.parseInt(tlibID),
                tlibname, tlibaddress);
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

