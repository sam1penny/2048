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
    public static final Map<String, Font> fonts = Map.ofEntries(
            entry("score", new Font(Font.SANS_SERIF, Font.BOLD, 20)),
            entry("tile", new Font(Font.DIALOG, Font.BOLD, 60)),
            entry("gameover", new Font(Font.SANS_SERIF, Font.BOLD, 80))
            );

    public static void main(String[] args) {
        newGame.setBackground(Color.black);
        frame.addKeyListener(newGame);
        frame.getContentPane().add(newGame);
        frame.setSize(800, 800);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        board.spawnNew();
        board.spawnNew();
    }



    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setPaint(new Color(255,255,255));
        g2d.setFont(fonts.get("score"));
        g2d.drawString("Score: " + board.getScore(), 100,50);

        g2d.setPaint(new Color(120,120,120));
        g2d.fillRect(80, 80, 640, 640);

        //draw tiles
        Tile[][] tiles = board.getTiles();
        for (int y = 0; y < tiles.length; y++) {
            for (int x = 0; x < tiles[0].length; x++) {
                g2d.setPaint(tiles[y][x].getColour());
                g2d.fillRect(100 + x*150, 100 + y*150, 150, 150);
                if (!tiles[y][x].isEmpty()) {
                    g2d.setPaint(Color.white);
                    g2d.setFont(fonts.get("tile"));
                    drawCenteredString(g2d, String.valueOf(tiles[y][x].getValue()) , new Rectangle(100 + x*150, 100 + y*150, 150, 150), g2d.getFont());
                }

            }
        }
        //draw grid
        g2d.setPaint(Color.black);
        for (int i = 0; i < 5; i++) {
            g2d.drawLine(100+150*i, 100, 100+150*i,700);
            g2d.drawLine(100, 100+150*i, 700,100+150*i);
        }

        if (board.gameOver()) {
            g2d.setFont(fonts.get("gameover"));
            drawCenteredString(g2d, "GAME OVER", new Rectangle(300,300, 200, 200), g2d.getFont());

        }

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

    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
