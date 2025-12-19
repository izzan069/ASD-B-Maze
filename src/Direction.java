package util;

public enum Direction {
    UP(-1,0), DOWN(1,0), LEFT(0,-1), RIGHT(0,1);
    public int dr, dc;
    Direction(int r, int c) {
        dr = r; dc = c;
    }
}
