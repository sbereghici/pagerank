import java.util.*;

public class Graph {
    private int n;
    private Map<Integer, List<Integer>> sourceAdjMap;
    private Map<Integer, List<Integer>> destAdjMap;

    Graph(int n) {
        this.n = n;
        sourceAdjMap = new HashMap<>();
        destAdjMap = new HashMap<>();
    }

    public void addEdge(Integer from, Integer to) {
        sourceAdjMap.computeIfAbsent(from, k -> new ArrayList<>());
        destAdjMap.computeIfAbsent(to, k -> new ArrayList<>());
        sourceAdjMap.get(from).add(to);
        destAdjMap.get(to).add(from);
    }

    public Map<Integer, Double> getPageRanks() {
        Map<Integer, Double> PROld = new HashMap<>();
        Map<Integer, Double> PRNew = new HashMap<>();
        for (int id = 0; id < n; ++id) {
            PROld.put(id, 1.00 / n);
        }

        int it = 0;
        int ITERATIONS_LIMIT = 100;
        double d = 0.85;
        double delta = 1.00;
        double EPS = 1e-10;
        while (it <= ITERATIONS_LIMIT && delta > EPS) {
            delta = 0.00;
            for (int id = 0; id < n; ++id) {
                double temp = (1 - d) + d * sum(id, PROld);
                PRNew.put(id, temp);
                delta = Math.max(delta, Math.abs(PROld.get(id) - temp));
            }
            PROld = new HashMap<>(PRNew);
            PRNew = new HashMap<>();
            ++it;
        }

        Map<Integer, Double> pageRanks = new HashMap<>();
        for (int id = 0; id < n; ++id) {
            pageRanks.put(id, PROld.get(id));
        }
        System.out.println("PageRank Iterations : " + it);
        return pageRanks;
    }

    private double sum(Integer nodeId, Map<Integer, Double> PROld) {
        double sum = 0.0;
        List<Integer> destNodesToCurrentNode = destAdjMap.get(nodeId);
        if (destNodesToCurrentNode != null && destNodesToCurrentNode.size() > 0) {
            for (Integer sourceNodeId : destNodesToCurrentNode) {
                sum += PROld.get(sourceNodeId) / sourceAdjMap.get(sourceNodeId).size();
            }
        }
        return sum;
    }
}
