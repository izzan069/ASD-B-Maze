package maze;

import java.awt.Point;

public class Maze {

    private final char[][] grid;
    private final int rows;
    private final int cols;

    private final Point start;
    private final Point end;

    public Maze(char[][] grid, Point start, Point end) {
        this.grid = grid;
        this.rows = grid.length;
        this.cols = grid[0].length;
        this.start = start;
        this.end = end;
    }

    public char[][] getGrid() {
        return grid;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public Point getStart() {
        return start;
    }

    public Point getEnd() {
        return end;
    }

    public int costAt(int r, int c) {
        return switch (grid[r][c]) {
            case 'G' -> 1;   // grass
            case 'M' -> 5;   // mud
            case 'W' -> 10;  // water
            default -> 1;
        };
    }
}
