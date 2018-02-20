import java.applet.*;
import java.awt.*;
import java.io.*;
import java.util.*;

public class Queens extends Applet {


    private int screenWidth = 640, screenHeight = 640; // default screen size
    final private boolean USE_DEFAULT_SCREEN_SIZE = true;

    String fileRef = "Queens.txt";

    private final Color gridColor = Color.GREEN;
    private final Color emptyCellColor = Color.WHITE;
    private final Color queenCellColor = Color.BLACK;
    private final Color penaltyLineColor = Color.RED;

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
        int[] p = read();
        int n = p.length;

        int tileWidth = screenWidth / n, tileHeight = screenHeight / n;

        //drawing the queens

        g.setColor(emptyCellColor);
        g.fillRect(0, 0, n * tileWidth, n * tileHeight);

        g.setColor(queenCellColor);
        for (int i = 0; i < n; ++i) {
            g.fillRect(i * tileWidth, (n - p[i] - 1) * tileHeight, tileWidth, tileHeight);
        }

        //drawing the grid

        g.setColor(gridColor);

        for (int i = 0; i < n; ++i) {
            g.drawLine((i + 1) * tileWidth, 0, (i + 1) * tileWidth, n * tileHeight);
        }
        for (int i = 0; i < n; ++i) {
            g.drawLine(0, (i + 1) * tileHeight, n * tileWidth, (i + 1) * tileHeight);
        }

        // evaluating and drawing lines between "bad" queens, O(n)

        g.setColor(penaltyLineColor);

        int energy = 0;

        ArrayList<Integer>[] risingDiag = new ArrayList[2 * n - 1];
        ArrayList<Integer>[] decreasingDiag = new ArrayList[2 * n - 1];

        for (int i = 0; i < 2 * n - 1; ++i) {
            risingDiag[i] = new ArrayList<Integer>();
            decreasingDiag[i] = new ArrayList<Integer>();
        }

        for (int i = 0; i < n; ++i) {
            int curRisingDiag = i + (n - 1 - p[i]);
            int curDecreasingDiag = i + p[i];

            risingDiag[curRisingDiag].add(i);
            decreasingDiag[curDecreasingDiag].add(i);
        }

        for (int i = 0; i < 2 * n - 1; ++i) {

            for (int j = 0; j < risingDiag[i].size() - 1; ++j) {
                int cur = risingDiag[i].get(j), next = risingDiag[i].get(j + 1);

                g.drawLine(cur * tileWidth + (tileWidth / 2), (n - 1 - p[cur]) * tileHeight + (tileHeight / 2),
                        next * tileWidth + (tileWidth / 2), (n - 1 - p[next]) * tileHeight + (tileHeight / 2));
            }


            for (int j = 0; j < decreasingDiag[i].size() - 1; ++j) {
                int cur = decreasingDiag[i].get(j), next = decreasingDiag[i].get(j + 1);

                g.drawLine(cur * tileWidth + (tileWidth / 2), (n - 1 - p[cur]) * tileHeight + (tileHeight / 2),
                        next * tileWidth + (tileWidth / 2), (n - 1 - p[next]) * tileHeight + (tileHeight / 2));
            }

            energy += (risingDiag[i].size() * (risingDiag[i].size() - 1) / 2)
                    + (decreasingDiag[i].size() * (decreasingDiag[i].size() - 1) / 2);
        }

        if (!printedAnswer) {
            printedAnswer = true;
            System.out.println("Energy is " + energy);
        }
    }


    private int[] read() throws Exception {

        ArrayList<Integer> permutation = new ArrayList<>();

        File fileToRead = new File(fileRef);
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(fileToRead));
        } catch (IOException e) {
            System.err.println("No file in " + fileToRead.getAbsolutePath());
            return new int[0];
        }

        while (true) {
            String line = br.readLine();
            if (line == null) break;

            StringTokenizer st = new StringTokenizer(line);

            while (st.hasMoreTokens()) {
                String nextToken = st.nextToken();
                try {
                    permutation.add(Integer.parseInt(nextToken));
                } catch (NumberFormatException e) {
                    System.err.println(nextToken + " is not a number!");
                    permutation.add(0);
                }
            }
        }

        //changes to array
        int n = permutation.size();
        int[] res = new int[n];
        for (int i = 0; i < n; ++i) {
            res[i] = permutation.get(i);
        }

        //checks if it's an permutation
        boolean[] used = new boolean[n];
        for (int i = 0; i < n; ++i) {
            if (res[i] < 0 || res[i] >= n || used[res[i]]) {
                throw new Exception("It's not a permutation!");
            }
            used[res[i]] = true;
        }

        return res;

    }

}
