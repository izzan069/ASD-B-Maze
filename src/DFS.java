package pathfinding;

import ui.MazePanel;
import util.Result;
import util.TerrainCost;
import java.awt.Point;
import java.util.*;

public class DFS implements Pathfinder {
    private MazePanel panel;

    public DFS(MazePanel panel) {
        this.panel = panel;
    }

    @Override
    public Result findPath(char[][] maze) {
        long startTime = System.nanoTime();
        Stack<Point> stack = new Stack<>();
        Map<Point, Point> parent = new HashMap<>();
        Set<Point> visited = new HashSet<>();

        Point start = find(maze, 'P');
        Point end = find(maze, 'E');

        stack.push(start);
        visited.add(start);

        while (!stack.isEmpty()) {
            Point cur = stack.pop();
            panel.visit(cur);

            if (cur.equals(end)) break;

            int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
            for (int[] d : dirs) {
                Point n = new Point(cur.x + d[1], cur.y + d[0]);
                if (n.y >= 0 && n.y < maze.length && n.x >= 0 && n.x < maze[0].length &&
                        maze[n.y][n.x] != '#' && !visited.contains(n)) {
                    visited.add(n);
                    parent.put(n, cur);
                    stack.push(n);
                }
            }
        }

        List<Point> path = new ArrayList<>();
        int totalCost = 0;
        Point temp = end;
        while (temp != null) {
            path.add(temp);
            totalCost += TerrainCost.get(maze[temp.y][temp.x]);
            temp = parent.get(temp);
        }
        Collections.reverse(path);

        panel.animateCharacter(path);

        return new Result(path, totalCost, System.nanoTime() - startTime, visited.size());
    }

    private Point find(char[][] maze, char target) {
        for (int r = 0; r < maze.length; r++)
            for (int c = 0; c < maze[0].length; c++)
                if (maze[r][c] == target) return new Point(c, r);
        return null;
    }
}