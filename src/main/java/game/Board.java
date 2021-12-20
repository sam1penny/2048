package game;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Board {
    private Tile[][] tiles;
    private int score = 0;
    private int size;

    Board(int n) {
        size = n;
        tiles = new Tile[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tiles[i][j] = new Tile();
            }
        }
    }

    // for testing purposes
    Board(Tile[][] startGrid) {
        if (startGrid.length != startGrid[0].length) {
            throw new IllegalArgumentException();
        }
        size = startGrid.length;
        tiles = new Tile[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tiles[i][j] = startGrid[i][j];
            }
        }
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public void spawnNew() {
        List<int[]> possibleLocations = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
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

    private boolean leftMoveUp() {
        boolean moved = false;
        for (int i = 0; i < size; i++) {
            int moveAlong = 0;
            for (int j = 0; j < size; j++) {
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

    private boolean leftCombine() {
        boolean combined = false;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size - 1; j++) {
                if (tiles[i][j].getValue() == tiles[i][j+1].getValue() && tiles[i][j].getValue() != 0) {
                    tiles[i][j].update();
                    tiles[i][j+1].reset();
                    combined = true;
                    score += tiles[i][j].getValue();
                }
            }
        }
        return combined;
    }

    private void reverse() {
        Tile temp;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size / 2; j++) {
                temp = tiles[i][j];
                tiles[i][j] = tiles[i][size-1-j];
                tiles[i][size-1-j] = temp;
            }
        }
    }

    private void transpose() {
        Tile temp;
        for (int i = 0; i < size; i++) {
            for (int j = i+1; j < size; j++) {
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

    public boolean gameWon() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[i][j].getValue() == 2048) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean gameOver() {
        return isFull() && noMovesLeft();
    }

    public boolean isFull() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[i][j].getValue() == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    //only valid when full
    private boolean noMovesLeft() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size-1; j++) {
                if (tiles[i][j].getValue() == tiles[i][j+1].getValue()  || tiles[j][i].getValue() == tiles[j+1][i].getValue()) {
                    return false;
                }
            }
        }
        return true;
    }

    public int getScore() {
        return score;
    }

    public int getSize() {
        return size;
    }



}
