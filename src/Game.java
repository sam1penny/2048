import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;
import static java.util.Map.entry;

public class Game extends JPanel implements KeyListener {
    public static JFrame frame = new JFrame("2^11");
    public static Game newGame = new Game();
    public static Board board = new Board();

    private int highScore = 0;

    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;
    public static final int X_DISTTOBOARD = 100;
    public static final int Y_DISTTOBOARD = 100;
    public static final int BOARD_SIZE = 600;
    public static final int CELL_SIZE = (BOARD_SIZE / board.gridSize());;

    public static final Map<String, Font> fonts = Map.ofEntries(
            entry("info", new Font(Font.SANS_SERIF, Font.BOLD, 20)),
            entry("tile", new Font(Font.DIALOG, Font.BOLD, CELL_SIZE / 3)),
            entry("gameover", new Font(Font.SANS_SERIF, Font.BOLD, 80))
    );



    public static void main(String[] args) {
        //Game newGame = new Game();
        newGame.setBackground(Color.black);
        frame.addKeyListener(newGame);
        frame.getContentPane().add(newGame);
        frame.setSize(newGame.WIDTH, newGame.HEIGHT);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        board.spawnNew();
        board.spawnNew();
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
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[0].length; x++) {
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
        for (int i = 0; i < 5; i++) {
            g2d.drawLine(X_DISTTOBOARD+CELL_SIZE*i, Y_DISTTOBOARD, X_DISTTOBOARD+CELL_SIZE*i,Y_DISTTOBOARD + BOARD_SIZE);
            g2d.drawLine(X_DISTTOBOARD, Y_DISTTOBOARD+CELL_SIZE*i, X_DISTTOBOARD + BOARD_SIZE,Y_DISTTOBOARD+CELL_SIZE*i);
        }

        if (board.gameOver()) {
            g2d.setFont(fonts.get("gameover"));
            drawCenteredString(g2d, "GAME OVER", new Rectangle(X_DISTTOBOARD + BOARD_SIZE / 3 , Y_DISTTOBOARD + BOARD_SIZE / 3, BOARD_SIZE / 3, BOARD_SIZE / 3), g2d.getFont());
        }

        //draw space to reset
        g2d.setFont(fonts.get("info"));
        g2d.setPaint(Color.white);
        drawCenteredString(g2d, "press space to restart", new Rectangle(X_DISTTOBOARD, Y_DISTTOBOARD + BOARD_SIZE, BOARD_SIZE, 100),g2d.getFont());


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
        if (board.gameOver()) {
            return;
        }
        if (Character.toLowerCase(e.getKeyChar()) == 'w' || e.getKeyCode() == KeyEvent.VK_UP) {
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
        else if (e.getKeyCode() == 32) {
            board = new Board();
            board.spawnNew();
            board.spawnNew();
            frame.repaint();
        }
        highScore = Math.max(highScore, board.getScore());
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
