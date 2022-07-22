import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseLogin extends JPanel implements ActionListener {
    private static String username = "";
    private static String password = "";
    private static String database = "";
    private static boolean loggedIn = false;

    private GridBagConstraints constraints;
    private JFrame frame;
    private JLabel titleLabel;
    private JLabel usernameLabel;
    private JLabel passwordLabel;
    private JTextField usernameField;
    private JTextField passwordField;
    private JButton loginButton;

    public DatabaseLogin() {

        frame = new JFrame();
        frame.setLayout(new GridBagLayout());
        frame.setSize(400, 400);

        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.ipadx = 10;
        constraints.ipady = 10;

        titleLabel = new JLabel("Login Form");
        titleLabel.setHorizontalAlignment(0);
        setGridPosition(0, 0, 2, 1);
        frame.add(titleLabel, constraints);

        usernameLabel = new JLabel("Username");
        setGridPosition(0, 1, 1, 1);
        frame.add(usernameLabel, constraints);

        usernameField = new JTextField();
        setGridPosition(1, 1, 1, 1);
        constraints.weightx = 1;
        frame.add(usernameField, constraints);

        passwordLabel = new JLabel("Password");
        setGridPosition(0, 2, 1, 1);
        frame.add(passwordLabel, constraints);

        passwordField = new JTextField();
        setGridPosition(1, 2, 1, 1);
        frame.add(passwordField, constraints);

        loginButton = new JButton("Login");
        setGridPosition(0, 3, 2, 1);
        frame.add(loginButton, constraints);

        loginButton.addActionListener(this);

        frame.setVisible(true);
    }

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        DatabaseLogin.username = username;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        DatabaseLogin.password = password;
    }

    public static String getDatabase() {
        return database;
    }

    public static void setDatabase(String database) {
        DatabaseLogin.database = database;
    }

    public static boolean isLoggedIn() {
        return loggedIn;
    }

    private void verifyCredentials() {
        try {
            Connection conn = DriverManager.getConnection(
                    String.format("jdbc:mysql://ambari-node5.csc.calpoly.edu:3306/%s", username),
                    username,
                    password);
            loggedIn = true;
        } catch (SQLException e) {
            System.out.println("Login failed");
        }
    }

    private void setGridPosition(int x, int y, int width, int height) {
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = width;
        constraints.gridheight = height;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        username = usernameField.getText();
        password = passwordField.getText();
        System.out.println("Username: " + username + "\nPassword: " + password);
        verifyCredentials();
        System.out.println("Logged In: " + String.valueOf(loggedIn));
        if (loggedIn)
        {
            frame.dispose();
        }
    }



}
