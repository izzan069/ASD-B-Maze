package pathfinding;

import ui.MazePanel;
import util.Result;
import util.TerrainCost;
import java.awt.Point;
import java.util.*;

public class Dijkstra implements Pathfinder {
    private MazePanel panel;

    public Dijkstra(MazePanel panel) {
        this.panel = panel;
    }

    @Override
    public Result findPath(char[][] maze) {
        long startTime = System.nanoTime();
        int rows = maze.length;
        int cols = maze[0].length;

        // KUNCI UTAMA: PriorityQueue harus selalu mengambil cost terkecil
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.cost));

        // Menyimpan jarak terpendek yang ditemukan ke setiap titik
        Map<Point, Integer> dist = new HashMap<>();
        Map<Point, Point> parent = new HashMap<>();
        Set<Point> settled = new HashSet<>();

        Point start = find(maze, 'P');
        Point end = find(maze, 'E');

        pq.add(new Node(start, 0));
        dist.put(start, 0);

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            Point cp = current.p;

            // Jika titik ini sudah diproses dengan biaya lebih murah, abaikan
            if (settled.contains(cp)) continue;
            settled.add(cp);

            panel.visit(cp); // Animasi biru tua

            if (cp.equals(end)) break;

            int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};
            for (int[] d : dirs) {
                Point next = new Point(cp.x + d[0], cp.y + d[1]);

                if (next.y >= 0 && next.y < rows && next.x >= 0 && next.x < cols && maze[next.y][next.x] != '#') {
                    // Ambil bobot asli dari TerrainCost
                    int weight = TerrainCost.get(maze[next.y][next.x]);
                    int newDist = dist.get(cp) + weight;

                    // Update jika ditemukan jalur yang lebih murah
                    if (newDist < dist.getOrDefault(next, Integer.MAX_VALUE)) {
                        dist.put(next, newDist);
                        parent.put(next, cp);
                        pq.add(new Node(next, newDist));
                    }
                }
            }
        }

        List<Point> path = new ArrayList<>();
        Point t = end;
        while(t != null) { path.add(t); t = parent.get(t); }
        Collections.reverse(path);

        // Menjalankan animasi karakter
        panel.animateCharacter(path);

        return new Result(path, dist.getOrDefault(end, 0), System.nanoTime() - startTime, settled.size());
    }

    private Point find(char[][] m, char c) {
        for(int y=0; y<m.length; y++)
            for(int x=0; x<m[0].length; x++)
                if(m[y][x] == c) return new Point(x, y);
        return null;
    }

    private static class Node {
        Point p; int cost;
        Node(Point p, int cost) { this.p = p; this.cost = cost; }
    }
}