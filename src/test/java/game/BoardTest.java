package game;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;

@RunWith(JUnit4.class)
public class BoardTest {
    private static class TestingBoard extends Board {

        TestingBoard(int n) {
            super(n);
        }

        TestingBoard(Tile[][] startGrid) {
            super(startGrid);
        }

        @Override
        public String toString() {
            String s = "";
            for (int i = 0; i < getSize(); i++) {
                s += Arrays.toString(getTiles()[i]) + "\n";
            }
            return s;
        }

    }
    @Test
    public void spawnNew_generates2or4InRandomPosition() {
        TestingBoard b = new TestingBoard(4);

        b.spawnNew();


        int count = 0;
        for (int i = 0; i < b.getSize(); i++) {
            for (int j = 0; j < b.getSize(); j++) {
                if (b.getTiles()[i][j].getValue() == 2 || b.getTiles()[i][j].getValue() == 4) {
                    count += 1;
                }
            }
        }
        assertThat(count).isEqualTo(1);
    }
    @Test
    public void gameOver_isTrue_whenGameOver() {

        Tile[][] tiles = new Tile[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tiles[i][j] = new Tile((int) Math.pow(2, i+j+1));
            }
        }
        TestingBoard b = new TestingBoard(tiles);


        assertThat(b.gameOver()).isTrue();
    }

    @Test
    public void gameOver_isFalse_whenGameNotOver() {
        Tile[][] startPos = new Tile[4][4];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                startPos[i][j] = new Tile((int) Math.pow(2, (i+j+1)));
            }
        }
        startPos[0][1].update();

        TestingBoard b = new TestingBoard(startPos);

        assertThat(b.gameOver()).isFalse();
    }

    @Test
    public void left_isCorrect() {
        Tile[][] startPos = new Tile[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                startPos[i][j] = new Tile();
            }
        }
        startPos[0][0] = new Tile(4);
        startPos[0][2] = new Tile(8);

        startPos[1][0] = new Tile(4);
        startPos[1][1] = new Tile(4);

        startPos[2][0] = new Tile(4);
        startPos[2][2] = new Tile(4);
        startPos[2][3] = new Tile(8);

        TestingBoard b = new TestingBoard(startPos);

        b.left();
        //test for moving, not combining
        assertThat(b.getTiles()[0][0].getValue()).isEqualTo(4);
        assertThat(b.getTiles()[0][1].getValue()).isEqualTo(8);
        assertThat(b.getTiles()[0][2].getValue()).isEqualTo(0);
        assertThat(b.getTiles()[0][3].getValue()).isEqualTo(0);
        //test for combining
        assertThat(b.getTiles()[1][0].getValue()).isEqualTo(8);
        assertThat(b.getTiles()[1][1].getValue()).isEqualTo(0);
        assertThat(b.getTiles()[1][2].getValue()).isEqualTo(0);
        assertThat(b.getTiles()[1][3].getValue()).isEqualTo(0);
        //test for moving and combing
        assertThat(b.getTiles()[2][0].getValue()).isEqualTo(8);
        assertThat(b.getTiles()[2][1].getValue()).isEqualTo(8);
        assertThat(b.getTiles()[2][2].getValue()).isEqualTo(0);
        assertThat(b.getTiles()[2][3].getValue()).isEqualTo(0);
    }

    @Test
    public void right_isCorrect() {
        Tile[][] tiles = emptyTiles(4);
        tiles[0][1] = new Tile(4);
        tiles[0][3] = new Tile(8);

        tiles[1][2] = new Tile(4);
        tiles[1][3] = new Tile(4);

        tiles[2][3] = new Tile(4);
        tiles[2][1] = new Tile(4);
        tiles[2][0] = new Tile(8);

        TestingBoard b = new TestingBoard(tiles);

        b.right();
        //test for moving, not combining
        assertThat(b.getTiles()[0][0].getValue()).isEqualTo(0);
        assertThat(b.getTiles()[0][1].getValue()).isEqualTo(0);
        assertThat(b.getTiles()[0][2].getValue()).isEqualTo(4);
        assertThat(b.getTiles()[0][3].getValue()).isEqualTo(8);
        //test for combining
        assertThat(b.getTiles()[1][0].getValue()).isEqualTo(0);
        assertThat(b.getTiles()[1][1].getValue()).isEqualTo(0);
        assertThat(b.getTiles()[1][2].getValue()).isEqualTo(0);
        assertThat(b.getTiles()[1][3].getValue()).isEqualTo(8);
        //test for moving and combing
        assertThat(b.getTiles()[2][0].getValue()).isEqualTo(0);
        assertThat(b.getTiles()[2][1].getValue()).isEqualTo(0);
        assertThat(b.getTiles()[2][2].getValue()).isEqualTo(8);
        assertThat(b.getTiles()[2][3].getValue()).isEqualTo(8);
    }

    @Test
    public void up_isCorrect() {
        Tile[][] tiles = emptyTiles(4);
        tiles[1][0] = new Tile(4);
        tiles[3][0] = new Tile(8);

        tiles[2][1] = new Tile(4);
        tiles[3][1] = new Tile(4);

        tiles[0][2] = new Tile(8);
        tiles[1][2] = new Tile(4);
        tiles[3][2] = new Tile(4);



        TestingBoard b = new TestingBoard(tiles);

        b.up();
        //test for moving, not combining
        assertThat(b.getTiles()[0][0].getValue()).isEqualTo(4);
        assertThat(b.getTiles()[1][0].getValue()).isEqualTo(8);
        assertThat(b.getTiles()[2][0].getValue()).isEqualTo(0);
        assertThat(b.getTiles()[3][0].getValue()).isEqualTo(0);
        //test for combining
        assertThat(b.getTiles()[0][1].getValue()).isEqualTo(8);
        assertThat(b.getTiles()[1][1].getValue()).isEqualTo(0);
        assertThat(b.getTiles()[2][1].getValue()).isEqualTo(0);
        assertThat(b.getTiles()[3][1].getValue()).isEqualTo(0);
        //test for moving and combing
        assertThat(b.getTiles()[0][2].getValue()).isEqualTo(8);
        assertThat(b.getTiles()[1][2].getValue()).isEqualTo(8);
        assertThat(b.getTiles()[2][2].getValue()).isEqualTo(0);
        assertThat(b.getTiles()[3][2].getValue()).isEqualTo(0);
    }

    @Test
    public void down_isCorrect() {
        Tile[][] tiles = emptyTiles(4);
        tiles[1][0] = new Tile(4);
        tiles[3][0] = new Tile(8);

        tiles[2][1] = new Tile(4);
        tiles[3][1] = new Tile(4);

        tiles[0][2] = new Tile(4);
        tiles[1][2] = new Tile(4);
        tiles[3][2] = new Tile(8);

        TestingBoard b = new TestingBoard(tiles);

        b.down();
        //test for moving, not combining
        assertThat(b.getTiles()[0][0].getValue()).isEqualTo(0);
        assertThat(b.getTiles()[1][0].getValue()).isEqualTo(0);
        assertThat(b.getTiles()[2][0].getValue()).isEqualTo(4);
        assertThat(b.getTiles()[3][0].getValue()).isEqualTo(8);
        //test for combining
        assertThat(b.getTiles()[0][1].getValue()).isEqualTo(0);
        assertThat(b.getTiles()[1][1].getValue()).isEqualTo(0);
        assertThat(b.getTiles()[2][1].getValue()).isEqualTo(0);
        assertThat(b.getTiles()[3][1].getValue()).isEqualTo(8);
        //test for moving and combing
        assertThat(b.getTiles()[0][2].getValue()).isEqualTo(0);
        assertThat(b.getTiles()[1][2].getValue()).isEqualTo(0);
        assertThat(b.getTiles()[2][2].getValue()).isEqualTo(8);
        assertThat(b.getTiles()[3][2].getValue()).isEqualTo(8);
    }

    public static Tile[][] emptyTiles (int n) {
        Tile[][] ts = new Tile[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                ts[i][j] = new Tile();
            }
        }
        return ts;
    }

}
