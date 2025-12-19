/**
 * ES234317-Algorithm and Data Structures
 * Semester Ganjil, 2025/2026
 * Ladder Game
 * Muhammad Izzan Aquilla - 5026241069
 * Muhammad Faqih Maulana - 5026241174
 */

import ui.MazeFrame;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            MazeFrame frame = new MazeFrame();
            frame.setVisible(true);
        });

    }
}
