package dev.hafnerp.components;

import dev.hafnerp.model.SudokuMap;
import dev.hafnerp.runnable.InvalidMove;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameTable extends javax.swing.JPanel {

    private final InvalidMove invalidMove;

    public GameTable(InvalidMove invalidMove) {
        initComponents();

        this.invalidMove = invalidMove;
    }

    private void initComponents() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 9));

        SudokuMap sudokuMap = SudokuMap.getInstance();

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Integer value = sudokuMap.getValue(i, j);
                JTextField field = new JTextField(value == null ? "" : String.valueOf(value));

                field.setPreferredSize(new Dimension(30, 30));

                int finalI = i;
                int finalJ = j;
                field.setAction(new AbstractAction() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        javax.swing.SwingUtilities.invokeLater(() -> {
                            try {
                                int value = Integer.parseInt(field.getText());
                                boolean validMove = sudokuMap.setValue(finalI, finalJ, value);
                                if (validMove) {
                                    invalidMove.setMessage("");
                                    invalidMove.run();

                                    javax.swing.SwingUtilities.invokeLater(() -> {
                                       field.setBackground(Color.WHITE);
                                    });
                                }
                                else {
                                    invalidMove.setMessage("Move was invalid!");
                                    invalidMove.run();

                                    javax.swing.SwingUtilities.invokeLater(() -> {
                                        field.setText("");
                                        field.setBackground(Color.RED);
                                    });
                                }
                            } catch (Exception ex) {
                                invalidMove.setMessage(ex.getMessage());
                                field.setBackground(Color.RED);
                                invalidMove.run();
                            }
                        });
                    }
                });

                panel.add(field);
            }
        }

        add(panel);
    }
}
