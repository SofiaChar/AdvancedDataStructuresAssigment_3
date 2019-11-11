import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MySocialNetwork extends MyUndirectedGraph implements A3SocialNetwork {

    private ArrayList<Boolean> visited;
    private ArrayList<Integer> levelVertices = new ArrayList<>();

    MySocialNetwork() {
        super();
    }

    MySocialNetwork(int v) {
        super(v);
    }
    public static void main(String[] arg){
        MySocialNetwork x = new MySocialNetwork();
        for(int i = 0;i<17;i++) x.addVertex(i);
        x.addEdge(5,1);
        x.addEdge(5,2);
        x.addEdge(5,4);
        x.addEdge(5,6);
        x.addEdge(5,7);
        x.addEdge(5,8);
        x.addEdge(5,9);
        x.addEdge(5,12);
        x.addEdge(5,13);
        x.addEdge(10,7);
        x.addEdge(10,8);
        x.addEdge(10,9);
        x.addEdge(6,14);
        x.addEdge(1,2);
        x.addEdge(1,4);
        x.addEdge(1,3);
        x.addEdge(2,3);
        x.addEdge(2,4);
        x.addEdge(3,6);
        x.addEdge(3,4);
        x.addEdge(3,15);
        System.out.println(x.possibleFriends(5));
    }
    private void setupPlay(int vertexIndex) {
        visited = visitArray();
        levelVertices.clear();
        for (int i = 0; i < vertexList.size(); i++) levelVertices.add(1);
        bfsModified(vertexIndex, levelVertices, visited);
        System.out.println("\nDisplays level of units ==> " + levelVertices.toString() + "\n");
    }

    private void bfsModified(int vertex, ArrayList<Integer> levels, ArrayList<Boolean> visited) {
        Queue<Integer> queue = new LinkedList<>();
        for (Node node : vertexList)
            if (node.value == vertex) {
                vertex = vertexList.indexOf(node);
                break;
            }
        queue.add(vertex);
        levels.set(vertex, 0);
        visited.set(vertex, true);
        while (queue.size() > 0) {
            int u = queue.remove();
            Iterable<Integer> iterable = new AdjacencyList(vertexList.get(u).nodeLinkedList);
            for (Integer v : iterable) {
                for (Node node : vertexList)
                    if (node.value == v) {
                        v = vertexList.indexOf(node);
                        break;
                    }
                if (!visited.get(v)) {
                    queue.add(v);
                    visited.set(v, true);
                    levels.set(v, levels.get(u) + 1);
                }
            }

        }
    }

    @Override
    public int numberOfPeopleAtFriendshipDistance(int vertexIndex, int distance) {
        setupPlay(vertexIndex);
        int count = 0;
        for (int i : levelVertices)
            if (i == distance)
                count++;
        return count;
    }

    @Override
    public int furthestDistanceInFriendshipRelationships(int vertexIndex) {
        setupPlay(vertexIndex);
        int count = 0;
        for (int i : levelVertices)
            if (i > count)
                count = i;
        return count;
    }

    @Override
    public List<Integer> possibleFriends(int vertexIndex) {
        setupPlay(vertexIndex);
        for (Node node : vertexList)
            if (node.value == vertexIndex) {
                vertexIndex = vertexList.indexOf(node);
                break;
            }
        List<Integer> vertexEdges = vertexList.get(vertexIndex).nodeLinkedList;
        ArrayList<Integer> result = new ArrayList<>();
        for (int i = 0; i < levelVertices.size(); i++) {
            if (levelVertices.get(i) == 2) {
                List<Integer> edges = vertexList.get(i).nodeLinkedList;
                if (edges.size() >= 3) {
                    int third = 0;
                    for (Integer edge : edges)
                        if (vertexEdges.indexOf(edge) != -1)
                            third++;
                    if (third >= 3)
                        result.add(vertexList.get(i).value);
                }
            }
        }
        return result;
    }
}
