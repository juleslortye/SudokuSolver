// Solution is based on work by https://stackoverflow.com/users/912935/the111
// Backtracking, print and validity or row, column and square are derived from this solution

public class SudokuSolver
{

    static int countSquaresFilled;
    static int[][] sudokuBoard = new int[9][9];

    public static void main(String[] args) {

        sudokuBoard[5][0] = 4;
        sudokuBoard[7][0] = 5;
        sudokuBoard[8][0] = 2;
        sudokuBoard[0][1] = 4;
        sudokuBoard[2][1] = 9;
        sudokuBoard[4][1] = 3;
        sudokuBoard[6][1] = 8;
        sudokuBoard[7][1] = 7;
        sudokuBoard[0][2] = 6;
        sudokuBoard[1][3] = 9;
        sudokuBoard[5][3] = 5;
        sudokuBoard[7][3] = 3;
        sudokuBoard[5][4] = 2;
        sudokuBoard[0][5] = 5;
        sudokuBoard[2][5] = 1;
        sudokuBoard[0][6] = 7;
        sudokuBoard[3][6] = 9;
        sudokuBoard[8][6] = 3;
        sudokuBoard[6][7] = 7;
        sudokuBoard[7][7] = 8;
        sudokuBoard[2][8] = 6;
        sudokuBoard[5][8] = 8;
        sudokuBoard[7][8] = 2;

        // Get numSquaresFilled
        countSquaresFilled = getCountSquaresFilled();

        // Print start board
        printBoard();

        // Run solver
        if (solve()) {
            printBoard();
        } else {
            System.out.println("Solver could not find valid solution");
        }
    }

    // Backtracking
    static boolean solve() {
        if (81 == countSquaresFilled) {
            return true;
        }

        RowCol rowCol = getOpenRowColumn();
        int row = rowCol.row;
        int col = rowCol.col;
        for (int i = 1; i <= 9; i++) {
            sudokuBoard[row][col] = i;
            countSquaresFilled++;
            if (isBoardValid(row, col)) {
                if (solve()) {
                    return true;
                }
            }
            sudokuBoard[row][col] = 0;
            countSquaresFilled--;
        }
        return false;
    }

    // pass row and column as output using static class
    static class RowCol {
        int row;
        int col;

        RowCol(int r, int c) {
            row = r;
            col = c;
        }
    }

    // Get number of squares filled in board
    static int getCountSquaresFilled(){
        int countSquaresFilled = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (sudokuBoard[i][j] != 0) {
                    countSquaresFilled++;
                }   
            }
        }
        return countSquaresFilled;
    }

    // Print board with vertical/horizontal markers
    static void printBoard() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String boardPlace = (sudokuBoard[i][j] == 0 ? " " : Integer.toString(sudokuBoard[i][j]));
                System.out.print(" " + boardPlace + " ");
                if (j % 3 == 2 && j < 8)
                    System.out.print("|");
            }
            System.out.println();
            if (i % 3 == 2 && i < 8)
                System.out.println("---------|---------|---------");
        }
        System.out.println();
    }

    // Check if board is valid by: row, column, square
    static boolean isBoardValid(int row, int col) {
        return (isRowValid(row) && isColValid(col) && isSquareValid(row, col));
    }

    // Check if row is valid: if number occurs twice row is not valid
    static boolean isRowValid(int row) {
        int[] count = new int[9];
        for (int col = 0; col < 9; col++) {
            int n = sudokuBoard[row][col] - 1;
            if (n == -1)
                continue;
            count[n]++;
            if (count[n] > 1)
                return false;
        }
        return true;
    }

    // Check if column is valid: if number occurs twice column is not valid
    static boolean isColValid(int col) {
        int[] count = new int[9];
        for (int row = 0; row < 9; row++) {
            int n = sudokuBoard[row][col] - 1;
            if (n == -1)
                continue;
            count[n]++;
            if (count[n] > 1)
                return false;
        }
        return true;
    }

    // Check if square is valid: if number occurs twice square is not valid
    static boolean isSquareValid(int row, int col) {
        int r = (row / 3) * 3;
        int c = (col / 3) * 3;
        int[] count = new int[9];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int n = sudokuBoard[r + i][c + j] - 1;
                if (n == -1)
                    continue;
                count[n]++;
                if (count[n] > 1)
                    return false;
            }
        }
        return true;
    }

    // Get row and column which is most constrained
    static RowCol getOpenRowColumn() {
        int r = 0, c = 0, max = 0;
        int[] rowCounts = new int[9];
        int[] colCounts = new int[9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (sudokuBoard[i][j] != 0)
                    rowCounts[i]++;
                if (sudokuBoard[j][i] != 0)
                    colCounts[i]++;
            }
        }

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (sudokuBoard[i][j] == 0) {
                    if (rowCounts[i] > max) {
                        max = rowCounts[i];
                        r = i;
                        c = j;
                    }
                    if (colCounts[j] > max) {
                        max = rowCounts[j];
                        r = i;
                        c = j;
                    }
                }
            }
        }
        return new RowCol(r, c);
    }

}
