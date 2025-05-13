package dev.hafnerp.view;

import dev.hafnerp.components.GameTable;
import dev.hafnerp.model.SudokuMap;
import dev.hafnerp.runnable.InvalidMove;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class MainView extends javax.swing.JFrame {

    //TOP
    private JLabel sudokuText;
    private JTextField sudokuCount;
    private JButton sudokuButton;
    //MID
    private GameTable gameTable;
    //BOTTOM
    private JLabel errorMessage;

    public MainView() {
        setTitle("HaCoGames/jSudoku");
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        sudokuText = new JLabel("Sudoku by Hafny");
        sudokuButton = new JButton();
        sudokuCount = new JTextField();
        errorMessage = new JLabel("Welcome!");

        sudokuCount.setText("20");
        sudokuCount.setAction(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                javax.swing.SwingUtilities.invokeLater(() -> {
                    try {
                        String text = sudokuCount.getText();
                        if (!text.isEmpty() && !text.isBlank()) SudokuMap.filled = Integer.parseInt(text);
                        else SudokuMap.filled = 0;
                    } catch (NumberFormatException ignore) {
                        javax.swing.SwingUtilities.invokeLater(() -> {
                            errorMessage.setText("Invalid number!");
                            sudokuCount.setText("20");
                        });
                    }
                });
            }
        });

        JPanel topPanel = new JPanel();
        topPanel.add(sudokuText);
        topPanel.add(sudokuCount);
        topPanel.add(sudokuButton);

        gameTable = getNewGameTable();

        JPanel jPanel = new JPanel();

        sudokuButton.setAction(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                javax.swing.SwingUtilities.invokeLater(() -> {
                    SudokuMap.getInstance(true);
                    jPanel.remove(gameTable);
                    gameTable = getNewGameTable();
                    jPanel.add(gameTable, BorderLayout.CENTER);

                    jPanel.revalidate();
                    jPanel.repaint();
                });
            }
        });
        sudokuButton.setText("New Sudoku");

        jPanel.setLayout(new BorderLayout());
        jPanel.add(topPanel, BorderLayout.NORTH);
        jPanel.add(gameTable, BorderLayout.CENTER);
        jPanel.add(errorMessage, BorderLayout.SOUTH);
        add(jPanel);
    }


    private GameTable getNewGameTable() {
        return new GameTable(new InvalidMove() {
            @Override
            public void run() {
                javax.swing.SwingUtilities.invokeLater(() -> {
                    errorMessage.setText(getMessage());
                });
            }
        });
    }
}
