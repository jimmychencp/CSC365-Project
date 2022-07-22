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
import java.text.*;
import java.util.*;
import java.util.concurrent.TimeUnit;


public class borrowedTime extends JPanel implements ActionListener {
    JFrame f;
    JLabel title;
    JLabel itemID;
    JTextField titemID;
    JButton submit;
    Connection connect;

    public borrowedTime() {

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

        JFrame querytime = new JFrame();
        DefaultTableModel model = new DefaultTableModel();
        JTable jtable = new JTable(model);
        JScrollPane sp = new JScrollPane(jtable);
        model.addColumn("Borrow ID");
        model.addColumn("Date Borrowed");
        model.addColumn("Date Returned");
        model.addColumn("Days Borrowed");

        String print = String.format("SELECT R.borrowID, B.borrowDate, R.returnDate FROM BorrowRecord B, " +
                "ReturnRecord R WHERE B.borrowID = R.borrowID AND R.inventoryID = %d;", Integer.parseInt(itemID));

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

            String borrowID;
            String borrowDate;
            String returnDate;
            long daysBorrowed;
            ResultSet rs;

            rs = stmn.executeQuery(print);
            while (rs.next()) {
                borrowID = String.valueOf(rs.getInt(1));
                borrowDate = rs.getString(2);
                returnDate = rs.getString(3);
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

                Date firstDate = sdf.parse(borrowDate);
                Date secondDate = sdf.parse(returnDate);
                long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
                daysBorrowed = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

                model.addRow(new Object[]{ borrowID, borrowDate, returnDate, daysBorrowed });
                querytime.add(sp);
            }

            querytime.setSize(500,500);
            querytime.setVisible(true);


//            connect.setAutoCommit(false);
//
//            Statement stmn = connect.createStatement();
//
//            String printStr;
//            rs = stmn.executeQuery(print);
//            boolean chk = rs.next();
//            rs = stmn.executeQuery(print2);
//            boolean chk2 = rs.next();
//            if(chk == false || chk2 == false){
//                printStr = "Item not returned and/or borrowed";
//                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                f.setSize(500,100);
//                f.setVisible(true);
//                JTextArea textarea = new JTextArea();
//                textarea.setText(printStr);
//                f.add(textarea);
//            }
//            rs = stmn.executeQuery(print);
//            while (rs.next()) {
//                date1 = rs.getString(1);
//
//                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
//                Date firstDate = sdf.parse(date1);
//
//                rs = stmn.executeQuery(print2);
//                rs.next();
//                date2 = rs.getString(1);
//                Date secondDate = sdf.parse(date2);
//
//                long diffInMillies = Math.abs(secondDate.getTime() - firstDate.getTime());
//                long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
//
//                printStr = String.format("Borrowed for %s days", String.valueOf(diff));
//
//                f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                f.setSize(500,100);
//                f.setVisible(true);
//                JTextArea textarea = new JTextArea();
//                textarea.setText(printStr);
//                f.add(textarea);
//            }

            stmn.close();

            connect.commit();
            connect.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
    }
}
