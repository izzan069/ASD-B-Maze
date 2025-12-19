package generator;

import java.awt.Point;
import java.util.*;

public class PrimGenerator {

    private static final char WALL = '#';
    private static final char START = 'P';
    private static final char END = 'E';

    private static final char[] TERRAIN = {'G', 'G', 'G', 'M', 'W'};
    // Grass lebih sering muncul

    public char[][] generate(int rows, int cols) {

        char[][] maze = new char[rows][cols];
        for (int y = 0; y < rows; y++) {
            Arrays.fill(maze[y], WALL);
        }

        Random rand = new Random();
        List<Point> frontier = new ArrayList<>();

        Point start = new Point(1, 1);
        maze[start.y][start.x] = randomTerrain(rand);
        addFrontier(start, maze, frontier);

        while (!frontier.isEmpty()) {
            Point f = frontier.remove(rand.nextInt(frontier.size()));
            List<Point> neighbors = carvedNeighbors(f, maze);

            if (!neighbors.isEmpty()) {
                Point n = neighbors.get(rand.nextInt(neighbors.size()));
                carve(f, n, maze, rand);
                addFrontier(f, maze, frontier);
            }
        }

        maze[1][1] = START;
        maze[rows - 2][cols - 2] = END;

        return maze;
    }

    private char randomTerrain(Random r) {
        return TERRAIN[r.nextInt(TERRAIN.length)];
    }

    private void addFrontier(Point p, char[][] maze, List<Point> f) {
        int[][] d = {{2,0},{-2,0},{0,2},{0,-2}};
        for (int[] dir : d) {
            int nx = p.x + dir[0];
            int ny = p.y + dir[1];
            if (ny > 0 && ny < maze.length &&
                    nx > 0 && nx < maze[0].length &&
                    maze[ny][nx] == WALL) {
                f.add(new Point(nx, ny));
            }
        }
    }

    private List<Point> carvedNeighbors(Point p, char[][] maze) {
        List<Point> list = new ArrayList<>();
        int[][] d = {{2,0},{-2,0},{0,2},{0,-2}};
        for (int[] dir : d) {
            int nx = p.x + dir[0];
            int ny = p.y + dir[1];
            if (ny > 0 && ny < maze.length &&
                    nx > 0 && nx < maze[0].length &&
                    maze[ny][nx] != WALL) {
                list.add(new Point(nx, ny));
            }
        }
        return list;
    }

    private void carve(Point a, Point b, char[][] maze, Random r) {
        maze[a.y][a.x] = randomTerrain(r);
        maze[(a.y + b.y) / 2][(a.x + b.x) / 2] = randomTerrain(r);
    }
}
