import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

public class Window extends JFrame {
    public static final int WIDTH = 600;
    public static final int HEIGHT = 600;

    JPanel game;

    Window() {
        game = new Board();
        init();
    }

    private void init() {
        setVisible(true);
        setTitle("TicTacToe");
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.setPreferredSize(new Dimension(WIDTH,HEIGHT));
        addMouseListener((MouseListener) game);
        addKeyListener((KeyListener) game);
        add(game);
        pack();
        setLocationRelativeTo(null);
    }


}
