package ui;

import maze.Maze;
import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MazePanel extends JPanel {
    private char[][] grid;
    private int delay = 100; // Animasi mencari (lebih lambat)
    private List<Point> visitedPoints = new CopyOnWriteArrayList<>();
    private List<Point> finalPath = null;
    private Point characterPos = null;

    public MazePanel() {
        setPreferredSize(new Dimension(600, 600));
        setBackground(Color.WHITE);
    }

    public void setMaze(Maze maze) {
        this.grid = maze.getGrid();
        this.visitedPoints.clear();
        this.finalPath = null;
        this.characterPos = null;
        repaint();
    }

    public void visit(Point p) {
        if (grid == null) return;
        if (grid[p.y][p.x] != 'P' && grid[p.y][p.x] != 'E') {
            visitedPoints.add(p);
            paintImmediately(0, 0, getWidth(), getHeight());
            try {
                Thread.sleep(delay);
            } catch (InterruptedException ignored) {}
        }
    }

    // Metode yang menyebabkan error jika tidak ada
    public void animateCharacter(List<Point> path) {
        this.finalPath = path;
        for (Point p : path) {
            this.characterPos = p;
            paintImmediately(0, 0, getWidth(), getHeight());
            try {
                Thread.sleep(150); // Kecepatan jalan karakter
            } catch (InterruptedException ignored) {}
        }
    }

    public void resetPathOnly() {
        this.visitedPoints.clear();
        this.finalPath = null;
        this.characterPos = null;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (grid == null) return;

        int rows = grid.length;
        int cols = grid[0].length;
        int cellW = getWidth() / cols;
        int cellH = getHeight() / rows;

        // 1. Gambar Terrain
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                drawCell(g, r, c, grid[r][c], cellW, cellH);
            }
        }

        // 2. Gambar Visited (Biru Tua Pekat)
        g.setColor(new Color(0, 0, 139));
        for (Point p : visitedPoints) {
            g.fillRect(p.x * cellW, p.y * cellH, cellW, cellH);
        }

        // 3. Gambar Jejak Kuning
        if (finalPath != null) {
            g.setColor(Color.YELLOW);
            for (Point p : finalPath) {
                if (characterPos != null && finalPath.indexOf(p) <= finalPath.indexOf(characterPos)) {
                    if (grid[p.y][p.x] != 'P' && grid[p.y][p.x] != 'E') {
                        g.fillRect(p.x * cellW, p.y * cellH, cellW, cellH);
                    }
                }
            }
        }

        // 4. Gambar Karakter (Magenta)
        if (characterPos != null) {
            g.setColor(Color.MAGENTA);
            g.fillOval(characterPos.x * cellW + 4, characterPos.y * cellH + 4, cellW - 8, cellH - 8);
        }
    }

    private void drawCell(Graphics g, int r, int c, char type, int w, int h) {
        switch (type) {
            case '#': g.setColor(Color.DARK_GRAY); break;
            case 'P': g.setColor(Color.GREEN); break;
            case 'E': g.setColor(Color.RED); break;
            case 'G': g.setColor(new Color(144, 238, 144)); break;
            case 'M': g.setColor(new Color(139, 69, 19)); break;
            case 'W': g.setColor(new Color(30, 144, 255)); break;
            default:  g.setColor(Color.WHITE);
        }
        g.fillRect(c * w, r * h, w, h);
        g.setColor(new Color(200, 200, 200, 100));
        g.drawRect(c * w, r * h, w, h);
    }
}