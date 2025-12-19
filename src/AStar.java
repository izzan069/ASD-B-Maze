package pathfinding;

import ui.MazePanel;
import util.Result;
import util.TerrainCost;
import java.awt.Point;
import java.util.*;

public class AStar implements Pathfinder {
    private MazePanel panel;

    public AStar(MazePanel panel) {
        this.panel = panel;
    }

    @Override
    public Result findPath(char[][] maze) {
        long startTime = System.nanoTime();
        Point start = find(maze, 'P');
        Point end = find(maze, 'E');

        PriorityQueue<Node> open = new PriorityQueue<>(Comparator.comparingInt(n -> n.f));
        Map<Point, Integer> gScore = new HashMap<>();
        Map<Point, Point> parent = new HashMap<>();
        Set<Point> closed = new HashSet<>();

        gScore.put(start, 0);
        open.add(new Node(start, 0, heuristic(start, end)));

        while (!open.isEmpty()) {
            Point cur = open.poll().p;
            if (closed.contains(cur)) continue;
            closed.add(cur);

            panel.visit(cur);

            if (cur.equals(end)) break;

            for (Point n : neighbors(cur, maze)) {
                if (closed.contains(n)) continue;

                // Menggunakan TerrainCost agar perhitungan biaya akurat
                int tentativeG = gScore.get(cur) + TerrainCost.get(maze[n.y][n.x]);

                if (tentativeG < gScore.getOrDefault(n, Integer.MAX_VALUE)) {
                    parent.put(n, cur);
                    gScore.put(n, tentativeG);
                    open.add(new Node(n, tentativeG, tentativeG + heuristic(n, end)));
                }
            }
        }

        List<Point> path = new ArrayList<>();
        Point p = end;
        while (p != null) {
            path.add(p);
            p = parent.get(p);
        }
        Collections.reverse(path);

        // FIX: Mengganti setFinalPath menjadi animateCharacter
        panel.animateCharacter(path);

        return new Result(path, gScore.getOrDefault(end, 0), System.nanoTime() - startTime, closed.size());
    }

    private int heuristic(Point a, Point b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

    private Point find(char[][] maze, char target) {
        for (int r = 0; r < maze.length; r++) {
            for (int c = 0; c < maze[0].length; c++) {
                if (maze[r][c] == target) return new Point(c, r);
            }
        }
        return null;
    }

    private List<Point> neighbors(Point p, char[][] maze) {
        List<Point> list = new ArrayList<>();
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        for (int[] d : dirs) {
            int nx = p.x + d[0], ny = p.y + d[1];
            if (ny >= 0 && ny < maze.length && nx >= 0 && nx < maze[0].length && maze[ny][nx] != '#') {
                list.add(new Point(nx, ny));
            }
        }
        return list;
    }

    private static class Node {
        Point p; int g, f;
        Node(Point p, int g, int f) { this.p = p; this.g = g; this.f = f; }
    }
}