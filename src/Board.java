import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Board {
    private Tile[][] tiles = new Tile[4][4];

    Board() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tiles[i][j] = new Tile();
            }
        }
    }

    Board(Tile[][] start) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                tiles[i][j] = start[i][j];
            }
        }
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public void spawnNew() {
        List<int[]> possibleLocations = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (tiles[i][j].isEmpty()) {
                    possibleLocations.add(new int[] {i, j});
                }
            }
        }
        int[] randomCoordinates = possibleLocations.get(new Random().nextInt(possibleLocations.size()));
        int v = 2;
        if (Math.random() >= 0.9f) {
            v = 4;
        }
        tiles[randomCoordinates[0]][randomCoordinates[1]] = new Tile(v);

    }

    public boolean leftMoveUp() {
        boolean moved = false;
        for (int i = 0; i < 4; i++) {
            int moveAlong = 0;
            for (int j = 0; j < 4; j++) {
                if (tiles[i][j].getValue() == 0) {
                    moveAlong += 1;
                }
                else {
                    if (moveAlong != 0) {
                        tiles[i][j - moveAlong] = tiles[i][j];
                        tiles[i][j] = new Tile();
                        moved = true;
                    }
                }
            }
        }
        return moved;
    }

    public boolean leftCombine() {
        boolean combined = false;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 3; j++) {
                if (tiles[i][j].getValue() == tiles[i][j+1].getValue() && tiles[i][j].getValue() != 0) {
                    tiles[i][j].update();
                    tiles[i][j+1] = new Tile();
                    combined = true;
                }
            }
        }
        return combined;
    }

    public void reverse() {
        Tile temp;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                temp = tiles[i][j];
                tiles[i][j] = tiles[i][3-j];
                tiles[i][3-j] = temp;
            }
        }
    }

    public void transpose() {
        Tile temp;
        for (int i = 0; i < 4; i++) {
            for (int j = i+1; j < 4; j++) {
                temp = tiles[i][j];
                tiles[i][j] = tiles[j][i];
                tiles[j][i] = temp;
            }
        }
    }

    public boolean left() {
        boolean movedFirst = leftMoveUp();
        boolean combined = leftCombine();
        boolean movedSecond = leftMoveUp();
        return movedFirst || movedSecond || combined;
    }

    public boolean right() {
        reverse();
        boolean moved = left();
        reverse();
        return moved;
    }

    public boolean up() {
        transpose();
        boolean moved = left();
        transpose();
        return moved;
    }

    public boolean down() {
        transpose();
        boolean moved = right();
        transpose();
        return moved;
    }

    public boolean gameOver() {
        return isFull() && noMovesLeft();
    }

    public boolean isFull() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (tiles[i][j].getValue() == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    //only valid when full
    public boolean noMovesLeft() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4-1; j++) {
                if (tiles[i][j].getValue() == tiles[i][j+1].getValue()  || tiles[j][i].getValue() == tiles[j+1][i].getValue()) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {

        Board b = Board.losingPosition();
        System.out.println(b);
        System.out.println(b.gameOver());

    }

    public static Board losingPosition() {
        Tile[][] ts = new Tile[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                ts[i][j] = new Tile((int) Math.pow(2, (i+j+1)));
            }
        }
        //ts[0][0].update();
        return new Board(ts);
    }

    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < 4; i++) {
            s += Arrays.toString(tiles[i]) + "\n";
        }
        return s;
    }

}
