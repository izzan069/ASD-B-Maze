package maze;

public class Cell {
    public int row, col;
    public boolean wall = true;

    // cost terrain
    public int cost = 1;

    // state for visualization
    public boolean visited = false;
    public boolean inPath = false;

    public Cell parent = null;

    public Cell(int r, int c) {
        row = r;
        col = c;
    }
}
