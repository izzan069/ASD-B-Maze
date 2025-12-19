package ui;

import generator.PrimGenerator;
import maze.Maze;
import pathfinding.*;
import util.Result;
import javax.swing.*;
import java.awt.*;

public class MazeFrame extends JFrame {
    private MazePanel mazePanel;
    private ResultPanel resultPanel;
    private char[][] currentGrid;

    public MazeFrame() {
        setTitle("Maze Pathfinding Comparison");
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        mazePanel = new MazePanel();
        resultPanel = new ResultPanel();

        add(mazePanel, BorderLayout.CENTER);
        add(resultPanel, BorderLayout.EAST);
        add(createTopPanel(), BorderLayout.NORTH);

        generateMaze();
        setVisible(true);
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel();
        JButton btnGenerate = new JButton("Generate Maze");
        JButton btnBFS = new JButton("BFS");
        JButton btnDFS = new JButton("DFS");
        JButton btnDijkstra = new JButton("Dijkstra");
        JButton btnAStar = new JButton("A*");

        btnGenerate.addActionListener(e -> generateMaze());
        btnBFS.addActionListener(e -> runAlgorithm(new BFS(mazePanel), "BFS"));
        btnDFS.addActionListener(e -> runAlgorithm(new DFS(mazePanel), "DFS"));
        btnDijkstra.addActionListener(e -> runAlgorithm(new Dijkstra(mazePanel), "Dijkstra"));
        btnAStar.addActionListener(e -> runAlgorithm(new AStar(mazePanel), "A*"));

        panel.add(btnGenerate);
        panel.add(btnBFS);
        panel.add(btnDFS);
        panel.add(btnDijkstra);
        panel.add(btnAStar);
        return panel;
    }

    private void generateMaze() {
        PrimGenerator generator = new PrimGenerator();
        // Ukuran harus ganjil untuk algoritma Prim
        currentGrid = generator.generate(25, 25);
        mazePanel.setMaze(new Maze(currentGrid, new Point(1,1), new Point(23,23)));
        resultPanel.clear();
    }

    private void runAlgorithm(Pathfinder algorithm, String name) {
        mazePanel.resetPathOnly();
        new Thread(() -> {
            // SEKARANG SESUAI: hanya mengirim satu parameter currentGrid
            Result result = algorithm.findPath(currentGrid);

            SwingUtilities.invokeLater(() -> {
                resultPanel.addResult(name, result);
            });
        }).start();
    }
}