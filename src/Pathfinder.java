package pathfinding;

import util.Result;

public interface Pathfinder {
    // Diubah agar sinkron dengan semua implementasi (BFS, DFS, Dijkstra, AStar)
    Result findPath(char[][] maze);
}