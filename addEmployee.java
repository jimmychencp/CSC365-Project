import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class addEmployee extends JPanel implements ActionListener {

    JFrame f;
    JLabel title;
    JLabel emplID;
    JTextField templID;
    JLabel empName;
    JTextField tempName;
    JLabel empPhNum;
    JTextField tempPhNum;
    JLabel empAddress;
    JTextField tempAddress;
    JLabel libraryID;
    JTextField tlibraryID;
    JButton submit;
    Connection connect;

    public addEmployee(){

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
        emplID = new JLabel("Employee id");
        c.gridx = 0;
        c.gridy = 1;
        f.add(emplID, c);

        templID = new JTextField();
        c.gridx = 1;
        c.weightx = 1;
        c.ipadx = 10;
        c.gridy = 1;
        f.add(templID, c);

        empName = new JLabel("Employee name");
        c.gridx = 0;
        c.gridy = 2;
        f.add(empName, c);

        tempName = new JTextField();
        c.weightx = 1;
        c.gridx = 1;
        c.ipadx = 10;
        c.gridy = 2;
        f.add(tempName, c);

        empPhNum = new JLabel("Employee phone number");
        c.gridx = 0;
        c.gridy = 3;
        f.add(empPhNum, c);

        tempPhNum = new JTextField();
        c.gridx = 1;
        c.weightx = 1;
        c.ipadx = 10;
        c.gridy = 3;
        f.add(tempPhNum, c);

        empAddress = new JLabel("Employee address");
        c.gridx = 0;
        c.gridy = 4;
        f.add(empAddress, c);

        tempAddress = new JTextField();
        c.weightx = 1;
        c.gridx = 1;
        c.ipadx = 10;
        c.gridy = 4;
        f.add(tempAddress, c);

        libraryID = new JLabel("Library ID");
        c.gridx = 0;
        c.gridy = 5;
        f.add(libraryID, c);

        tlibraryID = new JTextField();
        c.gridx = 1;
        c.weightx = 1;
        c.ipadx = 10;
        c.gridy = 5;
        f.add(tlibraryID, c);

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

        String stemplID = templID.getText();
        String ename = tempName.getText();
        String tphnum = tempPhNum.getText();
        String taddress = tempAddress.getText();
        String tlibID = tlibraryID.getText();
        String print = String.format("INSERT INTO Employees (employeeID, ename, libraryID, address, phnum) VALUES (%d,\"%s\",%d,\"%s\",\"%s\");", Integer.parseInt(stemplID),
                ename,Integer.parseInt(tlibID), tphnum, taddress);
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
