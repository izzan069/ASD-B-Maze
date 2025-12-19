package util;

public class TerrainCost {

    public static int get(char c) {
        switch (c) {
            case 'G': return 1;   // grass
            case 'M': return 5;   // mud
            case 'W': return 10;  // water
            case 'P':
            case 'E': return 0;
            default: return Integer.MAX_VALUE;
        }
    }
}
