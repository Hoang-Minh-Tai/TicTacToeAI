import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;

public class Board extends JPanel implements MouseListener, KeyListener {
    char[][] board;
    boolean computerMoveFirst = false;
    AI comp = new AI();
    boolean endGame = false;
    static ArrayList<int[]> winningComb = new ArrayList<>();


    public Board() {
        winningComb.add(new int[]{0, 1, 2});
        winningComb.add(new int[]{3, 4, 5});
        winningComb.add(new int[]{6, 7, 8});
        winningComb.add(new int[]{0, 3, 6});
        winningComb.add(new int[]{1, 4, 7});
        winningComb.add(new int[]{2, 5, 8});
        winningComb.add(new int[]{0, 4, 8});
        winningComb.add(new int[]{2, 4, 6});
        newGame();
    }

    private void newGame() {
        board = new char[3][3];
        if (computerMoveFirst) {
            int[] compAction = comp.getComputerAction(board);
            board[compAction[0]][compAction[1]] = 'O';
        }
    }

    public static boolean checkWin(char turn, char[][] state) {
        return winningComb.stream().anyMatch(combo -> Arrays.stream(combo).allMatch(pos -> state[pos % 3][pos / 3] == turn));
    }

    public static boolean checkDraw(char[][] state) {
        for (int x = 0; x < state.length; x++) {
            for (int y = 0; y < state.length; y++) {
                if (state[x][y] == '\u0000') return false;
            }
        }
        return true;
    }


    @Override
    public void paint(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, 600, 600);
        g.setColor(Color.white);
        g.setFont(new Font("MV Boli", Font.BOLD, 200));
        for (int i = 1; i <= 2; i++) {
            g.fillRect(i * 200 - 5, 0, 10, getHeight());
            g.fillRect(0, i * 200, getWidth(), 10);
        }

        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                char cell = board[x][y];
                if (cell == '\u0000') continue;
                g.setColor(cell == 'X' ? Color.red : Color.blue);
                g.drawString(String.valueOf(cell), x * getWidth() / 3 + 10, y * getHeight() / 3 + 190);
            }
        }
        if (endGame) {
            g.setColor(new Color(100,100,100,2));
            g.fillRect(0,0,getWidth(),getHeight());
            g.setColor(Color.green);
            g.setFont(new Font("MV Boli", Font.BOLD, 100));
            String result;
            if (checkDraw(board)) result = "Draw!";
            else if (checkWin('X',board)) result = "X wins!";
            else result = "O wins!";
            g.drawString(result, 150,250);
            g.setFont(new Font("MV Boli", Font.CENTER_BASELINE,40));
            g.drawString("Press enter to try again",50,350);
        }


    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (endGame) return;
        int x = e.getX() * 3 / getWidth();
        int y = e.getY() * 3 / getHeight();

        if (x >= 3 || y >= 3) return;
        if (x < 0 || y < 0) return;

        if (board[x][y] != '\u0000') return;
        board[x][y] = 'X';
        if (AI.isTerminalState(board)) {
            endGame = true;
            repaint();
            return;
        }
        int[] compAction = comp.getComputerAction(board);
        board[compAction[0]][compAction[1]] = 'O';
        if (AI.isTerminalState(board)) endGame = true;
        repaint();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public static char[][] copy(char[][] board) {
        char[][] clone = new char[board.length][board.length];
        for (int x = 0; x < board.length; x++) {
            for (int y = 0; y < board.length; y++) {
                clone[x][y] = board[x][y];
            }
        }
        return clone;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (endGame) {
                endGame = false;
                computerMoveFirst = !computerMoveFirst;
                newGame();
                repaint();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
