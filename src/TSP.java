import java.applet.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class TSP extends Applet {


    private int screenWidth = 640, screenHeight = 640; // default screen size
    final private boolean USE_DEFAULT_SCREEN_SIZE = true;

    String fileRef = "TSP_result.txt";
    String mapRef = "TSP_map.txt";

    private final Color gridColor = Color.GREEN;
    private final Color emptyCellColor = Color.WHITE;
    private final Color occupiedCellColor = Color.BLACK;
    private final Color penaltyCellColor = Color.RED;
    private final Color lineColor = Color.BLUE;

    public void paint(Graphics g1) {

        Graphics2D g = (Graphics2D) g1;

        // gets actual screen size
        if (!USE_DEFAULT_SCREEN_SIZE) {
            GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
            screenWidth = gd.getDisplayMode().getWidth();
            screenHeight = gd.getDisplayMode().getHeight();
        }

        this.setSize(screenWidth, screenWidth);

        try {
            draw(g);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private boolean printedAnswer = false;

    private void draw(Graphics2D g) throws Exception {
        boolean[][] map = readMap();
        int n = map.length; // height
        int m = (n > 0) ? map[0].length : 0; // width

        int tileWidth = screenWidth / m;
        int tileHeight = screenHeight / n;

        // draws empty cells
        g.setColor(emptyCellColor);

        g.fillRect(0, 0, tileWidth * m, tileHeight * n);

        //draws occupied cells
        g.setColor(occupiedCellColor);

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                if (!map[i][j]) continue;
                g.fillRect(j * tileWidth, i * tileHeight, tileWidth, tileHeight);
            }
        }

        // draws grid
        g.setColor(gridColor);

        for (int i = 0; i < n; ++i) {
            g.drawLine(0, (i + 1) * tileHeight, m * tileWidth, (i + 1) * tileHeight);
        }

        for (int i = 0; i < m; ++i) {
            g.drawLine((i + 1) * tileWidth, 0, (i + 1) * tileWidth, n * tileHeight);
        }
    }

    private int mapWidth = 0, mapHeight = 0;

    private boolean[][] readMap() throws IOException {

        File fileToRead = new File(mapRef);
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(fileToRead));
        } catch (IOException e) {
            System.err.println("No file in " + fileToRead.getAbsolutePath());
            return new boolean[0][0];
        }

        ArrayList<String> mapLayers = new ArrayList<>();

        while (true) {
            String curLine = br.readLine();
            if (curLine == null) break;

            StringTokenizer st = new StringTokenizer(curLine);
            while (st.hasMoreTokens()) {
                String token = st.nextToken();
                boolean isMapLayer = true;

                for (int i = 0; i < token.length(); ++i) {
                    if (token.charAt(i) != '.' && token.charAt(i) != '#') {
                        isMapLayer = false;
                        break;
                    }
                }

                if (isMapLayer) {
                    mapLayers.add(token);
                }
            }
        }

        mapHeight = mapLayers.size();

        // making the layers be the same length (if the differ)
        for (int i = 0; i < mapHeight; ++i) {
            mapWidth = Math.max(mapWidth, mapLayers.get(i).length());
        }
        for (int i = 0; i < mapWidth; ++i) {
            if (mapLayers.get(i).length() == mapWidth) {
                continue;
            }

            StringBuilder stb = new StringBuilder();
            stb.append(mapLayers.get(i));
            while (stb.length() < mapWidth) {
                stb.append('.');
            }
            mapLayers.set(i, stb.toString());
        }

        boolean[][] result = new boolean[mapHeight][mapWidth];
        for (int i = 0; i < mapHeight; ++i) {
            for (int j = 0; j < mapWidth; ++j) {
                result[i][j] = (mapLayers.get(i).charAt(j) == '#');
            }
        }

        return result;
    }



    private class Position {
        int x, y;

        public Position (int x, int y)  {
            this.x = x;
            this.y = y;
        }
    }
}
