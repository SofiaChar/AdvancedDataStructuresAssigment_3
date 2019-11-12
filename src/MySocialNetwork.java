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

    private void distanceOf(int vertexIndex) {
        visited = marked();
        levelVertices.clear();
        for (int i = 0; i < vertexList.size(); i++) levelVertices.add(1);
        BFSModified(vertexIndex, levelVertices, visited);
        System.out.println("\nDistance of vertex index " + vertexIndex + " = " + levelVertices.toString() + "\n");
    }

    private void BFSModified(int vertex, ArrayList<Integer> levels, ArrayList<Boolean> marked) {
        Queue<Integer> queue = new LinkedList<>();
        for (Node node : vertexList)
            if (node.value == vertex) {
                vertex = vertexList.indexOf(node);
                break;
            }
        queue.add(vertex);
        levels.set(vertex, 0);
        marked.set(vertex, true);
        while (queue.size() > 0) {
            int u = queue.remove();
            Iterable<Integer> iterable = new AdjacencyList(vertexList.get(u).nodeLinkedList);
            for (Integer v : iterable) {
                for (Node node : vertexList)
                    if (node.value == v) {
                        v = vertexList.indexOf(node);
                        break;
                    }
                if (!marked.get(v)) {
                    queue.add(v);
                    marked.set(v, true);
                    levels.set(v, levels.get(u) + 1);
                }
            }

        }
    }

    @Override
    public int numberOfPeopleAtFriendshipDistance(int vertexIndex, int distance) {
        distanceOf(vertexIndex);
        int count = 0;
        for (int i : levelVertices)
            if (i == distance)
                count++;
        return count;
    }

    @Override
    public int furthestDistanceInFriendshipRelationships(int vertexIndex) {
        distanceOf(vertexIndex);
        int count = 0;
        for (int i : levelVertices)
            if (i > count)
                count = i;
        return count;
    }

    @Override
    public List<Integer> possibleFriends(int vertexIndex) {
        distanceOf(vertexIndex);
        for (Node node : vertexList)
            if (node.value == vertexIndex) {
                vertexIndex = vertexList.indexOf(node);
                break;
            }
        List<Integer> vertexEdges = vertexList.get(vertexIndex).nodeLinkedList;
        ArrayList<Integer> consequence = new ArrayList<>();
        for (int i = 0; i < levelVertices.size(); i++) {
            if (levelVertices.get(i) == 2) {
                List<Integer> edges = vertexList.get(i).nodeLinkedList;
                if (edges.size() >= 3) {
                    int third = 0;
                    for (Integer edge : edges)
                        if (vertexEdges.indexOf(edge) != -1)
                            third++;
                    if (third >= 3)
                        consequence.add(vertexList.get(i).value);
                }
            }
        }
        return consequence;
    }
}
