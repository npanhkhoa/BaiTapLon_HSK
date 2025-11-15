package main;

import javax.swing.*;

import view.login.fLogin;

public class Application {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(fLogin::new);
    }
}
