package util;

import java.awt.Point;
import java.util.List;

public class Result {
    public List<Point> path;
    public int totalCost;
    public long timeNano;
    public int visitedNodes; // Menggunakan nama visitedNodes agar konsisten

    // Konstruktor Kosong untuk AStar dan DFS
    public Result() {}

    public Result(List<Point> path, int totalCost, long timeNano, int visitedNodes) {
        this.path = path;
        this.totalCost = totalCost;
        this.timeNano = timeNano;
        this.visitedNodes = visitedNodes;
    }
}