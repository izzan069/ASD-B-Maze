package ui;

import util.Result;

import javax.swing.*;
import java.awt.*;

public class ResultPanel extends JPanel {

    private JTextArea area;

    public ResultPanel() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(250, 0));

        area = new JTextArea();
        area.setEditable(false);
        area.setFont(new Font("Monospaced", Font.PLAIN, 12));

        add(new JScrollPane(area), BorderLayout.CENTER);
    }

    public void addResult(String algo, Result r) {
        area.append("=== " + algo + " ===\n");
        area.append("Time (ms)   : " + r.timeNano / 1_000_000.0 + "\n");
        area.append("Cost        : " + r.totalCost + "\n");
        area.append("Visited     : " + r.visitedNodes + "\n\n");
    }

    public void clear() {
        area.setText("");
    }
}
