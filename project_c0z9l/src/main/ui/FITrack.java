package ui;

import model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

// The class for initiliazing and running the application
public class FITrack {
    private Scanner scanner = new Scanner(System.in);
    private User user;
    private FunctionsCenter functionsCenter;
    private JLabel label;
    private JTextField field;

    // EFFECTS: calls askUserInfo() to initialize user information
    public FITrack() {
        askUserInfo();
    }

    //MODIFIES: user
    // EFFECTS: initializes the program
    private void askUserInfo() {
        user = new User();
        functionsCenter = new FunctionsCenter(user);
    }
}
