import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class GUI  extends JFrame implements ActionListener {
    // menubar
    static JMenuBar mb;

    // JMenu
    static JMenu menu, transmenu, querymenu;

    // Menu items
    static JMenuItem addEmployee, addItem, borrowItem, returnItem, addMember, addLibrary;
    static JMenuItem retrieveItem, retrieveMember, borrowedItem, returnedItem, borrowedTime;

    // create a frame
    static JFrame f;

    // a label
    static JLabel l;

    // main class
    public GUI() throws IOException {
//        while (!DatabaseLogin.isLoggedIn());
        // create a frame
        f = new JFrame("Library Database");
        f.setDefaultCloseOperation(EXIT_ON_CLOSE);
        // create a label
        BufferedImage bufferedImage = ImageIO.read(new File("./libraryimage.jpg"));
        Image image = bufferedImage.getScaledInstance(1920, 1080, Image.SCALE_DEFAULT);
        ImageIcon icon = new ImageIcon(image);
        l = new JLabel(icon, 0);

        // create a menubar
        mb = new JMenuBar();

        // create a menu
        menu = new JMenu("Creating a new library entries");
        transmenu = new JMenu("Complete a transaction");
        querymenu = new JMenu("Search database");

        // create menuitems
        addEmployee = new JMenuItem("Add new employee");
        addItem= new JMenuItem("Add new item");
        addMember= new JMenuItem("Add a new member");
        addLibrary = new JMenuItem("Add a new library");

        // create transaction menuitems
        borrowItem = new JMenuItem("Borrow an item");
        returnItem = new JMenuItem("Return an item");

        // create query menuitems
        retrieveItem = new JMenuItem("Search an item");
        retrieveMember = new JMenuItem("Search a member");
        borrowedItem = new JMenuItem("Check an item's borrow record");
        returnedItem = new JMenuItem("Check an item's return record");
        borrowedTime = new JMenuItem("Check time borrowed");

        // add ActionListener to menuItems
        addEmployee.addActionListener(this);
        addItem.addActionListener(this);
        addMember.addActionListener(this);
        addLibrary.addActionListener(this);
        borrowItem.addActionListener(this);
        returnItem.addActionListener(this);

        retrieveItem.addActionListener(this);
        retrieveMember.addActionListener(this);
        borrowedItem.addActionListener(this);
        returnedItem.addActionListener(this);
        borrowedTime.addActionListener(this);

        // add menu items to menu
        menu.add(addMember);
        menu.add(addEmployee);
        menu.add(addItem);
        menu.add(addLibrary);

        // add menu item to transmenu
        transmenu.add(borrowItem);
        transmenu.add(returnItem);

        // add menu item to querymenu
        querymenu.add(retrieveItem);
        querymenu.add(retrieveMember);
        querymenu.add(borrowedItem);
        querymenu.add(returnedItem);
        querymenu.add(borrowedTime);

        // add menu to menu bar
        mb.add(menu);
        mb.add(transmenu);
        mb.add(querymenu);

        // add menubar to frame
        f.setJMenuBar(mb);

        // add label
        f.add(l);

        // set the size of the frame
        f.setSize(500, 500);
        f.setVisible(true);
    }
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == addEmployee){
            addEmployee emp = new addEmployee();
            f.add(emp);
        }
        if (e.getSource() == addItem){
            addRecord item = new addRecord();
            f.add(item);
        }
        if (e.getSource() == borrowItem){
            borrowRecord bItem = new borrowRecord();
            f.add(bItem);
        }
        if (e.getSource() == returnItem){
            returnRecord rItem = new returnRecord();
            f.add(rItem);
        }
        if (e.getSource() == addMember){
            addMember memb = new addMember();
            f.add(memb);
        }
        if (e.getSource() == addLibrary){
            addLibrary lib = new addLibrary();
            f.add(lib);
        }
        if (e.getSource() == retrieveItem){
            retrieveItem ret = new retrieveItem();
            f.add(ret);
        }
        if (e.getSource() == retrieveMember){
            retrieveMember retm = new retrieveMember();
            f.add(retm);
        }
        if (e.getSource() == borrowedItem){
            borrowedItem tem = new borrowedItem();
            f.add(tem);
        }
        if (e.getSource() == returnedItem){
            returnedItem retI = new returnedItem();
            f.add(retI);
        }
        if (e.getSource() == borrowedTime){
            borrowedTime timeB = new borrowedTime();
            f.add(timeB);
        }
    }
}
