package pathfinding;

import ui.MazePanel;
import util.Result;
import util.TerrainCost;
import java.awt.Point;
import java.util.*;

public class BFS implements Pathfinder {
    private MazePanel panel;

    public BFS(MazePanel panel) { this.panel = panel; }

    @Override
    public Result findPath(char[][] maze) {
        long startTime = System.nanoTime();
        int rows = maze.length;
        int cols = maze[0].length;

        Queue<Point> q = new LinkedList<>();
        Map<Point, Point> parent = new HashMap<>();
        Set<Point> visited = new HashSet<>();

        Point start = findPoint(maze, 'P');
        Point end = findPoint(maze, 'E');

        q.add(start);
        visited.add(start);

        while (!q.isEmpty()) {
            Point cur = q.poll();
            panel.visit(cur);

            if (cur.equals(end)) break;

            int[][] dirs = {{1,0},{-1,0},{0,1},{0,-1}};
            for (int[] d : dirs) {
                Point next = new Point(cur.x + d[1], cur.y + d[0]);
                if (next.y >= 0 && next.y < rows && next.x >= 0 && next.x < cols &&
                        maze[next.y][next.x] != '#' && !visited.contains(next)) {
                    visited.add(next);
                    parent.put(next, cur);
                    q.add(next);
                }
            }
        }

        List<Point> path = new ArrayList<>();
        int totalCost = 0;
        Point p = end;
        while(p != null) {
            path.add(p);
            totalCost += TerrainCost.get(maze[p.y][p.x]);
            p = parent.get(p);
        }
        Collections.reverse(path);

        panel.animateCharacter(path); // Memanggil metode di MazePanel

        return new Result(path, totalCost, System.nanoTime() - startTime, visited.size());
    }

    private Point findPoint(char[][] maze, char target) {
        for(int r=0; r<maze.length; r++)
            for(int c=0; c<maze[0].length; c++)
                if(maze[r][c] == target) return new Point(c, r);
        return null;
    }
}