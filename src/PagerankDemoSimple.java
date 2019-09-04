import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class PagerankDemoSimple {

    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(new FileReader("wikipedia.txt"));
        int n = sc.nextInt();
        int m = sc.nextInt();
        Graph g = new Graph(n);
        while (m-- > 0) {
            int from = sc.nextInt();
            int to = sc.nextInt();
            g.addEdge(from, to);
        }
        long timeStart = new Date().getTime();
        Map<Integer, Double> pageRanks = g.getPageRanks();
        System.out.println("Running Time :" + (new Date().getTime() - timeStart) + " ms ");
        List<Pair> pairs = new ArrayList<>();
        PrintWriter pw = new PrintWriter("pageranks.txt");
        for (Integer nodeId : pageRanks.keySet()) {
            pairs.add(new Pair<>(nodeId, pageRanks.get(nodeId)));
        }
        pairs.sort((p0, p1) -> Double.compare(p1.value, p0.value));
        pairs.forEach(pair -> pw.println(pair.id + " " + pair.value));
        pw.flush();

    }
}
