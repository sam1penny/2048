package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;
import static java.util.Map.entry;


public class Game extends JPanel implements KeyListener {
    private JFrame frame = new JFrame("2^11");
    private int size;
    private Board board;
    private int highScore = 0;

    private final int WIDTH = 800;
    private final int HEIGHT = 800;
    private final int X_DISTTOBOARD = 100;
    private final int Y_DISTTOBOARD = 100;
    private final int BOARD_SIZE = 600;
    private int CELL_SIZE;

    private Map<String, Font> fonts;

    Game(int n) {
        size = n;
        setBackground(Color.black);
        frame.addKeyListener(this);
        frame.getContentPane().add(this);
        frame.setSize(WIDTH, HEIGHT+200);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        restart();

    }

    public void restart() {
        board = new Board(size);
        board.spawnNew();
        board.spawnNew();
        frame.repaint();
        CELL_SIZE = (BOARD_SIZE / board.getSize());
        fonts = Map.ofEntries(
                entry("info", new Font(Font.SANS_SERIF, Font.BOLD, 20)),
                entry("tile", new Font(Font.DIALOG, Font.BOLD, CELL_SIZE / 3)),
                entry("gameover", new Font(Font.SANS_SERIF, Font.BOLD, 80))
        );
    }

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setPaint(Color.white);
        g2d.setFont(fonts.get("info"));
        g2d.drawString("Score: " + board.getScore(), X_DISTTOBOARD,Y_DISTTOBOARD - 50);
        g2d.drawString("High Score: " + highScore, X_DISTTOBOARD + BOARD_SIZE - 150,Y_DISTTOBOARD - 50);

        //outer rectangle
        //g2d.setPaint(new Color(120,120,120));
        //g2d.fillRect(X_DISTTOBOARD - 20, X_DISTTOBOARD - 20, BOARD_SIZE + 40, BOARD_SIZE + 40);

        //draw tiles
        Tile[][] tiles = board.getTiles();
        for (int y = 0; y < board.getSize(); y++) {
            for (int x = 0; x < board.getSize(); x++) {
                g2d.setPaint(tiles[y][x].getColour());
                g2d.fillRect(X_DISTTOBOARD + x * CELL_SIZE, Y_DISTTOBOARD + y*CELL_SIZE, CELL_SIZE, CELL_SIZE);
                if (!tiles[y][x].isEmpty()) {
                    g2d.setPaint(Color.white);
                    g2d.setFont(fonts.get("tile"));
                    drawCenteredString(g2d, String.valueOf(tiles[y][x].getValue()), new Rectangle(X_DISTTOBOARD + x * CELL_SIZE, Y_DISTTOBOARD + y*CELL_SIZE, CELL_SIZE, CELL_SIZE), g2d.getFont());
                }

            }
        }
        //draw grid
        g2d.setPaint(Color.black);
        int error = BOARD_SIZE % board.getSize();
        for (int i = 0; i <= size; i++) {
            g2d.drawLine(X_DISTTOBOARD+CELL_SIZE*i, Y_DISTTOBOARD, X_DISTTOBOARD+CELL_SIZE*i,Y_DISTTOBOARD + BOARD_SIZE - error);
            g2d.drawLine(X_DISTTOBOARD, Y_DISTTOBOARD+CELL_SIZE*i, X_DISTTOBOARD + BOARD_SIZE - error,Y_DISTTOBOARD+CELL_SIZE*i);
        }

        if (board.gameWon()) {
            g2d.setFont(fonts.get("gameover"));
            drawCenteredString(g2d, "YOU WIN!", new Rectangle(X_DISTTOBOARD + BOARD_SIZE / 3 , Y_DISTTOBOARD + BOARD_SIZE / 3, BOARD_SIZE / 3, BOARD_SIZE / 3), g2d.getFont());
        }

        if (board.gameOver()) {
            g2d.setFont(fonts.get("gameover"));
            drawCenteredString(g2d, "GAME OVER", new Rectangle(X_DISTTOBOARD + BOARD_SIZE / 3 , Y_DISTTOBOARD + BOARD_SIZE / 3, BOARD_SIZE / 3, BOARD_SIZE / 3), g2d.getFont());
        }

        //draw space to reset
        g2d.setFont(fonts.get("info"));
        g2d.setPaint(Color.white);
        drawCenteredString(g2d, "press space to restart | press number keys to change grid size", new Rectangle(X_DISTTOBOARD, Y_DISTTOBOARD + BOARD_SIZE, BOARD_SIZE, 60),g2d.getFont());


    }

    public void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        // Get the FontMetrics
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        g.setFont(font);
        // Draw the String
        g.drawString(text, x, y);
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            restart();
        }
        else if (board.gameOver() || board.gameWon()) {
            return;
        }
        else if (Character.toLowerCase(e.getKeyChar()) == 'w' || e.getKeyCode() == KeyEvent.VK_UP) {
            boolean moved = board.up();
            if (moved) {
                board.spawnNew();
                frame.repaint();
            }

        }
        else if (Character.toLowerCase(e.getKeyChar()) == 's'|| e.getKeyCode() == KeyEvent.VK_DOWN) {
            boolean moved = board.down();
            if (moved) {
                board.spawnNew();
                frame.repaint();
            }
        }
        else if (Character.toLowerCase(e.getKeyChar()) == 'a'|| e.getKeyCode() == KeyEvent.VK_LEFT) {
            boolean moved = board.left();
            if (moved) {
                board.spawnNew();
                frame.repaint();
            }
        }
        else if (Character.toLowerCase(e.getKeyChar()) == 'd'|| e.getKeyCode() == KeyEvent.VK_RIGHT) {
            boolean moved = board.right();
            if (moved) {
                board.spawnNew();
                frame.repaint();
            }
        }
        else {
            switch (e.getKeyChar()) {
                case '2' -> size = 2;
                case '3' -> size = 3;
                case '4' -> size = 4;
                case '5' -> size = 5;
                case '6' -> size = 6;
                case '7' -> size = 7;
                case '8' -> size = 8;
                case '9' -> size = 9;
            }
            //System.out.println(Integer.parseInt(String.valueOf(e.getKeyChar())));
            restart();
        }
        highScore = Math.max(highScore, board.getScore());
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
