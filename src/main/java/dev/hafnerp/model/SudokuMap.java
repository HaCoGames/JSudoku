package dev.hafnerp.model;

public class SudokuMap {
    private static SudokuMap sudokuMapInstance;
    public static int filled = 20;

    public static SudokuMap getInstance() {
        if (sudokuMapInstance == null) {
            sudokuMapInstance = new SudokuMap();
        }
        return sudokuMapInstance;
    }

    public static SudokuMap getInstance(boolean newInstance) {
        if (newInstance) {
            sudokuMapInstance = new SudokuMap();
        }
        return sudokuMapInstance;
    }

    private final Integer[][] sudokuMap;

    public SudokuMap() {
        this.sudokuMap = new Integer[9][9];

        generateRandomBoard(filled);
    }

    /**
     * Checks if a value exists in the specified row or column.
     *
     * @param index    The index of the row or column.
     * @param value    The value to look for.
     * @param isRow    True to search in a row, false to search in a column.
     * @return true if the value is found, false otherwise.
     */
    private boolean validateLine(int index, int value, boolean isRow) {
        if (index < 0 || index >= 9) {
            throw new IllegalStateException("index out of bounds");
        }

        for (int i = 0; i < 9; i++) {
            Integer current = isRow ? sudokuMap[index][i] : sudokuMap[i][index];
            if (current != null && current == value) {
                return true;
            }
        }

        return false;
    }

    /**
     * Validates the given value for a row.
     * @param row The row index.
     * @param value The value to validate.
     * @return true if the value is valid, false otherwise.
     */
    private boolean validateRow(int row, int value) {
        return !validateLine(row, value, true);
    }

    /**
     * Validates the given value for a column.
     * @param col The column index.
     * @param value The value to validate.
     * @return true if the value is valid, false otherwise.
     */
    private boolean validateCol(int col, int value) {
        return !validateLine(col, value, false);
    }

    /**
     * Validate the 3x3 box for the value.
     * @param row Row index.
     * @param col Col index.
     * @param value Value
     * @return true - if the value was found. false - otherwise
     */
    private boolean validateBox(int row, int col, int value) {
        int startRowIndex = 3 * (row / 3);
        int startColIndex = 3 * (col / 3);

        for (int i = startRowIndex; i < startRowIndex + 3; i++) {
            for (int j = startColIndex; j < startColIndex + 3; j++) {
                if (sudokuMap[i][j] != null && value == sudokuMap[i][j]) {
                    return true;
                }
            }
        }

        return false;
    }


    /**
     * This function trys to add a value to the map and checks if the value even can be placed.
     * @param row The row in which the value should be placed
     * @param col The column in which the value should be placed
     * @param value The value (1-9) that should be placed and displayed!
     * @return true - if the value could be placed on the coordinates. false - if not.
     */
    public boolean setValue(int row, int col, int value) {
        if (value <= 0 || value > 9) throw new IllegalStateException("Value must be in range of [1-9]");

        boolean validRow = validateRow(row, value);
        boolean validCol = validateCol(col, value);
        boolean inBox = validateBox(row, col, value);

        if (!validRow || !validCol || inBox) return false;
        else sudokuMap[row][col] = value;

        return true;
    }

    /**
     * Get the value on a given position.
     * @param row Row of the table
     * @param col Col of the table
     * @return int - the value that is filled in. null - if nothing is filled in yet.
     */
    public Integer getValue(int row, int col) {
        return sudokuMap[row][col];
    }

    /**
     * Generates a new partially filled Sudoku board by placing valid numbers
     * randomly across the grid. This ensures each placed number follows Sudoku rules:
     * - Unique in its row
     * - Unique in its column
     * - Unique in its 3x3 box
     * This generator **does not guarantee a unique solution**, but it generates a
     * solvable-looking puzzle board.
     * Note: This will overwrite any existing values in the map!
     *
     * @param filledCells Number of cells to pre-fill (recommended: 20–40).
     */
    public void generateRandomBoard(int filledCells) {
        if (filledCells <= 0) return;
        if (filledCells > 81) return;

        // Clear existing board
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                sudokuMap[row][col] = null;
            }
        }

        java.util.Random rand = new java.util.Random();

        int filled = 0;
        while (filled < filledCells) {
            int row = rand.nextInt(9);
            int col = rand.nextInt(9);

            if (sudokuMap[row][col] != null) continue; // Already filled

            int value = 1 + rand.nextInt(9);

            if (validateRow(row, value) && validateCol(col, value) && !validateBox(row, col, value)) {
                sudokuMap[row][col] = value;
                filled++;
            }
        }
    }

}
