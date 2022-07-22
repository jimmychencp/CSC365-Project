import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class retrieveMember extends JPanel implements ActionListener {

    JFrame f;
    JLabel title;
    JLabel memberID;
    JTextField tmemberID;
    JButton submit;
    Connection connect;

    public retrieveMember(){
        f = new JFrame();
        f.setLayout(new GridBagLayout());
        f.setSize(400, 200);

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridy = 0;
        c.gridx = 0;

        title = new JLabel("Search a member");
        c.gridy = 0;
        c.gridx = 0;
        c.gridwidth = 2;
        f.add(title, c);

        c.gridwidth = 1;
        memberID = new JLabel("Member id");
        c.gridx = 0;
        c.gridy = 1;
        f.add(memberID, c);

        tmemberID = new JTextField();
        c.gridx = 1;
        c.weightx = 1;
        c.ipadx = 10;
        c.gridy = 1;
        f.add(tmemberID, c);

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
        JFrame querymem = new JFrame();

        DefaultTableModel model = new DefaultTableModel();
        JTable jtable = new JTable(model);
        JScrollPane sp = new JScrollPane(jtable);
        model.addColumn("Name");
        model.addColumn("Address");
        model.addColumn("Phone");
        model.addColumn("Age");

        String print = String.format("SELECT * FROM Members M WHERE M.memberID = %d;", Integer.parseInt(smemID));
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

            String memberName;
            String memberAddress;
            String memberPhone;
            String memberAge;
            ResultSet rs;

            rs = stmn.executeQuery(print);
            while (rs.next()) {
                memberName = rs.getString(2);
                memberAddress = rs.getString(3);
                memberPhone = rs.getString(4);
                memberAge = String.valueOf(rs.getInt(5));
                model.addRow(new Object[]{ memberName, memberAddress, memberPhone, memberAge });
                querymem.add(sp);
            }

            querymem.setSize(500,500);
            querymem.setVisible(true);

            stmn.close();

            connect.commit();
            connect.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

