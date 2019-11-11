import java.util.*;

public class MyUndirectedGraph implements A3Graph {

    public int[] connectedComponents;
    public ArrayList<Node> vertexList = new ArrayList<>();
    public ArrayList<Node> helperList = new ArrayList<>();
    public List<Integer> eulerList = new ArrayList<>();

    MyUndirectedGraph() {
    }

    MyUndirectedGraph(int numberOfVertices) {
        for (int i = 0; i < numberOfVertices; i++)
            addVertex(i);
    }

    @Override
    public void addVertex(int vertex) {
        vertexList.add(new Node(vertex));
        helperList.add(new Node(vertex));
    }

    @Override
    public void addEdge(int sourceVertex, int targetVertex) {
        int sourceIndex = 0;
        for (Node node : vertexList)
            if (node.value == sourceVertex){
                sourceIndex = vertexList.indexOf(node);
                break;
            }
        vertexList.get(sourceIndex).nodeLinkedList.add(targetVertex);
        helperList.get(sourceIndex).nodeLinkedList.add(targetVertex);

        for (Node node : vertexList)
            if (node.value == targetVertex){
                sourceIndex = vertexList.indexOf(node);
                break;
            }
        vertexList.get(sourceIndex).nodeLinkedList.add(sourceVertex);
        helperList.get(sourceIndex).nodeLinkedList.add(sourceVertex);

    }

    @Override
    public boolean isConnected() {
        connectedComponents = new int[vertexList.size()];
        int countConnComp = 1;
        for (int i = 0; i < vertexList.size(); i++)
            if (connectedComponents[i] == 0) {
                ArrayList<Boolean> visited = runBFSUndirected(i);
                for (int j = 0; j < vertexList.size(); j++)
                    if (visited.get(j))
                        connectedComponents[j] = countConnComp;
                countConnComp++;
            }
        return countConnComp == 2;
    }


    @Override
    public boolean isAcyclic() {
        ArrayList<Boolean> visited = new ArrayList<>();
        for (int i = 0; i < vertexList.size(); ++i)
            visited.add(false);
        for (int u = 0; u < vertexList.size(); u++)
            if (!visited.get(u))
                if (isCyclic(u, visited, -1))
                    return false;
        return true;
    }

    private boolean isCyclic(int startVertex, ArrayList<Boolean> visited, int parent) {
        visited.set(startVertex, true);
        Iterable<Integer> adjLst = new AdjacencyList(vertexList.get(startVertex).nodeLinkedList);
        for (Integer i : adjLst) {
            for (Node node : vertexList)
                if (node.value == i){
                    i = vertexList.indexOf(node);
                    break;
                }
            if (!visited.get(i)) {
                if (isCyclic(i, visited, startVertex)) return true;
            }
            else if (i != parent)
                return true;
        }
        return false;
    }

    @Override
    public List<List<Integer>> connectedComponents() {
        List<List<Integer>> result = new ArrayList<>();
        List<Integer> subGraph = new ArrayList<>();
        connectedComponents = new int[vertexList.size()];
        int count = 1;
        for (int i = 0; i < vertexList.size(); i++)
            if (connectedComponents[i] == 0) {
                ArrayList<Boolean> visited = runBFSUndirected(i);
                for (int j = 0; j < vertexList.size(); j++)
                    if (visited.get(j)) {
                        subGraph.add(vertexList.get(j).value);
                        connectedComponents[j] = count;
                    }
                List<Integer> f = new ArrayList<>();
                for (Integer x : subGraph){
                    f.add(x);
                    System.out.print(x + " ");
                }

//                f.addAll(subGraph);
                result.add(f);
                System.out.println();
                subGraph.clear();
                count++;
            }
        return result;
    }

    @Override
    public boolean hasEulerPath() {
        int result = 1;
        if (isConnected()) {
            int count = 0;
            for (int i = 0; i < vertexList.size(); i++)
                if ((vertexList.get(i).nodeLinkedList.size() % 2) != 0)
                    count++;
            if (count == 0) result = 2;
            if (count == 2) result = 1;
            else result = 0;
        }
        return result == 1;
    }

    ArrayList<Boolean> visitArray() {
        ArrayList<Boolean> visited = new ArrayList<>();
        for (int i = 0; i < vertexList.size(); ++i)
            visited.add(false);
        return visited;
    }

    private ArrayList<Boolean> runBFSUndirected(int startVertex) {
        ArrayList<Boolean> visited = new ArrayList<>();
        for (int i = 0; i < vertexList.size(); ++i)
            visited.add(false);
        BFSUndirected(visited, startVertex);
        return visited;
    }

    private void BFSUndirected(ArrayList<Boolean> visited, int startVertex) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(startVertex);
        visited.set(startVertex, true);
        while (queue.size() > 0) {
            int u = queue.remove();
            Iterable<Integer> iterable = new AdjacencyList(vertexList.get(u).nodeLinkedList);
            for (Integer v : iterable) {
                for (Node node : vertexList)
                    if (node.value == v){
                        v = vertexList.indexOf(node);
                        break;
                    }
                if (!visited.get(v)) {
                    visited.set(v, true);
                    queue.add(v);
                }
            }
        }
    }

    public void print(){
        System.out.println("\nNODES:");
        for (Node node : vertexList)
            System.out.println(node.value);
        System.out.println("\nList of edges:");
        for (int i = 0; i < vertexList.size(); i++)
            System.out.println(vertexList.get(i).nodeLinkedList);
    }

    @Override
    public List<Integer> eulerPath() {
        if (!hasEulerPath()) return null;
        int u = 0;
        for (int i = 0; i < vertexList.size(); i++)
            if (helperList.get(i).nodeLinkedList.size() % 2 == 1) {
                u = i;
                eulerList.add(helperList.get(u).value);
                break;
            }
        doEulerPath(u);
        return eulerList;
    }

    private void doEulerPath(int startVertex) {
        Iterable<Integer> adjLst = new AdjacencyList(helperList.get(startVertex).nodeLinkedList);
        for (Integer next : adjLst) {
            for (Node node : helperList)
                if (node.value == next){
                    next = helperList.indexOf(node);
                    break;
                }
            if (checkEdge(startVertex, next)) {
                System.out.print(startVertex + "--" + next + "  ");
                eulerList.add(helperList.get(next).value);
                deleteEdge(startVertex, next);
                doEulerPath(next);
            }
        }
    }

    private boolean checkEdge(int u, int v) {

        if (helperList.get(u).nodeLinkedList.size() == 1)
            return true;

        ArrayList<Boolean> visited = new ArrayList<>();
        ArrayList<Boolean> tempVisited = new ArrayList<>();
        for (int i = 0; i < vertexList.size(); i++) {
            visited.add(false);
            tempVisited.add(false);
        }

        int count1 = Undirected(u, visited);
        deleteEdge(u, v);
        int count2 = Undirected(u, tempVisited);
        addEdge(vertexList.get(u).value, vertexList.get(v).value);
        return count1 == count2;
    }

    private int Undirected(int i, ArrayList<Boolean> visited) {
        visited.set(i, true);
        int count = 1;
        Iterable<Integer> adjLst = new AdjacencyList(helperList.get(i).nodeLinkedList);
        for (Integer next : adjLst) {
            for (Node node : helperList)
                if (node.value == next){
                    next = helperList.indexOf(node);
                    break;
                }
            if (!visited.get(next))
                count += Undirected(next, visited);
        }
        return count;
    }

    private void deleteEdge(int sourceVertex, int targetVertex) {
        helperList.get(sourceVertex).nodeLinkedList.remove(helperList.get(sourceVertex).nodeLinkedList.indexOf(helperList.get(targetVertex).value));
        helperList.get(targetVertex).nodeLinkedList.remove(helperList.get(targetVertex).nodeLinkedList.indexOf(helperList.get(sourceVertex).value));
    }

}

class Node {
    public LinkedList<Integer> nodeLinkedList = new LinkedList<>();
    public int value;
    public Node(int val) {
        value = val;
    }
}

class AdjacencyList implements Iterable<Integer> {
    private LinkedList<Integer> linkedList;
    public AdjacencyList(LinkedList<Integer> linkedList) {
        this.linkedList = linkedList;
    }
    @Override
    public Iterator<Integer> iterator() { return linkedList.iterator(); }
}
