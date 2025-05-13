package dev.hafnerp;

import dev.hafnerp.view.MainView;

public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            System.out.println("Invoking Swing component...");
            new MainView().setVisible(true);
        });
    }
}