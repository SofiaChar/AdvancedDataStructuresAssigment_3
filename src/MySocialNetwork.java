import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MySocialNetwork extends MyUndirectedGraph implements A3SocialNetwork {

    private ArrayList<Boolean> marked;
    private ArrayList<Integer> distanceBetweenVertices = new ArrayList<>();

    MySocialNetwork(int v) {

        super(v);
    }

    MySocialNetwork() {

        super();
    }


    private void distanceOf(int vertexIndex) {
        marked = marked();
        distanceBetweenVertices.clear();
        for (int i = 0; i < vertexList.size(); i++)
            distanceBetweenVertices.add(1);
        BFSSocialN(vertexIndex, distanceBetweenVertices, marked);

        System.out.println("\nDistance of vertex index " + vertexIndex + " = " + distanceBetweenVertices.toString() + "\n");
    }

    private void BFSSocialN(int vertex, ArrayList<Integer> distances, ArrayList<Boolean> marked) {
        Queue<Integer> queue = new LinkedList<>();
        for (Node node : vertexList)
            if (node.value == vertex) {
                vertex = vertexList.indexOf(node);
                break;
            }

        queue.add(vertex);
        distances.set(vertex, 0);
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
                    distances.set(v, distances.get(u) + 1);
                }
            }

        }
    }

    @Override
    public int numberOfPeopleAtFriendshipDistance(int vertexIndex, int distance) {
        distanceOf(vertexIndex);
        int number = 0;
        for (int i : distanceBetweenVertices)
            if (i == distance)
                number++;
        return number;
    }

    @Override
    public int furthestDistanceInFriendshipRelationships(int vertexIndex) {

        int number = 0;
        for (int i : distanceBetweenVertices)
            if (i > number)
                number = i;
        return number;
    }

    @Override
    public List<Integer> possibleFriends(int vertexIndex) {

        for (Node node : vertexList)
            if (node.value == vertexIndex) {
                vertexIndex = vertexList.indexOf(node);
                break;
            }

        List<Integer> vertexEdges = vertexList.get(vertexIndex).nodeLinkedList;
        ArrayList<Integer> consequence = new ArrayList<>();
        for (int i = 0; i < distanceBetweenVertices.size(); i++) {
            if (distanceBetweenVertices.get(i) == 2) {
                List<Integer> edges = vertexList.get(i).nodeLinkedList;
                if (edges.size() >= 3) {
                    int commonFriends = 0;
                    for (Integer edge : edges)
                        if (vertexEdges.indexOf(edge) != -1)
                            commonFriends++;
                    if (commonFriends >= 3)
                        consequence.add(vertexList.get(i).value);
                }
            }
        }
        return consequence;
    }
}
