import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class addMember extends JPanel implements ActionListener {

    static Connection connect;
    JFrame f;
    JLabel title;
    JLabel memberID;
    JTextField tmemberID;
    JLabel name;
    JTextField tname;
    JLabel phnum;
    JTextField tphnum;
    JLabel age;
    JTextField tage;
    JLabel address;
    JTextField taddress;
    JButton submit;

    public addMember(){

        f = new JFrame();
        f.setLayout(new GridBagLayout());
        f.setSize(400, 300);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 0;
        c.gridx = 0;

        title = new JLabel("Add new Member");
        c.gridy = 0;
        c.gridx = 0;
        c.gridwidth = 2;
        f.add(title, c);

        c.gridwidth = 1;
        memberID = new JLabel("Member ID");
        c.gridx = 0;
        c.gridy = 1;
        f.add(memberID, c);

        tmemberID = new JTextField();
        c.gridx = 1;
        c.weightx = 1;
        c.ipadx = 10;
        c.gridy = 1;
        f.add(tmemberID, c);

        name = new JLabel("Name");
        c.gridx = 0;
        c.gridy = 2;
        f.add(name, c);

        tname = new JTextField();
        c.weightx = 1;
        c.gridx = 1;
        c.ipadx = 10;
        c.gridy = 2;
        f.add(tname, c);

        phnum = new JLabel("Phone number");
        c.gridx = 0;
        c.gridy = 3;
        f.add(phnum, c);

        tphnum = new JTextField();
        c.gridx = 1;
        c.weightx = 1;
        c.ipadx = 10;
        c.gridy = 3;
        f.add(tphnum, c);

        age = new JLabel("Age");
        c.gridx = 0;
        c.gridy = 4;
        f.add(age, c);

        tage = new JTextField();
        c.gridx = 1;
        c.weightx = 1;
        c.ipadx = 10;
        c.gridy = 4;
        f.add(tage, c);

        address = new JLabel("Address");
        c.gridx = 0;
        c.gridy = 5;
        f.add(address, c);

        taddress = new JTextField();
        c.gridx = 1;
        c.weightx = 1;
        c.ipadx = 10;
        c.gridy = 5;
        f.add(taddress, c);

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
        String smemID = tmemberID.getText();
        String mname = tname.getText();
        String mphnum = tphnum.getText();
        String mtaddress = taddress.getText();
        String mage = tage.getText();
        String print = String.format("INSERT INTO Members (memberID, mname, address, phnum, age) VALUES (%d,\"%s\",\"%s\",\"%s\", %d);", Integer.parseInt(smemID),
                mname,mphnum, mtaddress, Integer.parseInt(mage));
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
