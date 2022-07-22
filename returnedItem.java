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


public class returnedItem extends JPanel implements ActionListener {
    JFrame f;
    JLabel title;
    JLabel itemID;
    JTextField titemID;
    JButton submit;
    Connection connect;


    public returnedItem() {

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

        JFrame queryreturn = new JFrame();
        DefaultTableModel model = new DefaultTableModel();
        JTable jtable = new JTable(model);
        JScrollPane sp = new JScrollPane(jtable);
        model.addColumn("Return ID");
        model.addColumn("Date Returned");
        model.addColumn("Returned To (Library)");

        String print = String.format("SELECT returnID, returnDate, libraryID FROM ReturnRecord WHERE inventoryID = %d;", Integer.parseInt(itemID));

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

            String returnID;
            String returnDate;
            String libraryID;
            ResultSet rs;

            rs = stmn.executeQuery(print);
            while (rs.next()) {
                returnID = String.valueOf(rs.getInt(1));
                returnDate = rs.getString(2);
                libraryID = rs.getString(3);
                model.addRow(new Object[]{ returnID, returnDate, libraryID });
                queryreturn.add(sp);
            }

            queryreturn.setSize(500,500);
            queryreturn.setVisible(true);
//            connect.setAutoCommit(false);
//
//
//            Statement stmn = connect.createStatement();
//
//            String printStr, printStr2;
//            String date;
//            rs = stmn.executeQuery(print);
//            boolean chk = rs.next();
//            if(chk == false){
//                printStr = "Item not returned";
//                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                f.setSize(500,100);
//                f.setVisible(true);
//                JTextArea textarea = new JTextArea();
//                textarea.setText(printStr);
//                f.add(textarea);
//            }
//            rs = stmn.executeQuery(print2);
//            while (rs.next()) {
//                date = rs.getString(1);
//                printStr2 = String.format("Item returned on %s", date);
//                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                f.setSize(500,100);
//                f.setVisible(true);
//                JTextArea textarea = new JTextArea();
//                textarea.setText(printStr2);
//                f.add(textarea);
//            }

            stmn.close();

            connect.commit();
            connect.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

}
