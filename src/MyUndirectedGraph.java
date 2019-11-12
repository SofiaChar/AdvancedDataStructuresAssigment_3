import java.util.*;

public class MyUndirectedGraph implements A3Graph {

    public ArrayList<Node> vertexList = new ArrayList<>();
    public ArrayList<Node> helperList = new ArrayList<>();
    public List<Integer> eulerList = new ArrayList<>();
    public int[] connectedComponents;

    MyUndirectedGraph(int numberOfVertices) {
        for (int i = 0; i < numberOfVertices; i++)
            addVertex(i);
    }

    MyUndirectedGraph() {
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
                ArrayList<Boolean> marked = runBFSUndirected(i);
                for (int j = 0; j < vertexList.size(); j++)
                    if (marked.get(j))
                        connectedComponents[j] = countConnComp;
                countConnComp++;
            }
        return countConnComp == 2;
    }


    @Override
    public boolean isAcyclic() {
        ArrayList<Boolean> marked = new ArrayList<>();
        for (int i = 0; i < vertexList.size(); ++i)
            marked.add(false);
        for (int u = 0; u < vertexList.size(); u++)
            if (!marked.get(u))
                if (checkIfHasCycle(u, marked, -1))
                    return false;
        return true;
    }

    private boolean checkIfHasCycle(int startVertex, ArrayList<Boolean> marked, int parent) {
        marked.set(startVertex, true);
        Iterable<Integer> adjList = new AdjacencyList(vertexList.get(startVertex).nodeLinkedList);
        for (Integer i : adjList) {
            for (Node node : vertexList)
                if (node.value == i){
                    i = vertexList.indexOf(node);
                    break;
                }
            if (!marked.get(i)) {
                if (checkIfHasCycle(i, marked, startVertex))
                    return true;
            }
            else if (i == parent)
                return false;
        }
        return false;
    }

    @Override
    public List<List<Integer>> connectedComponents() {
        List<List<Integer>> consequence = new ArrayList<>();
        List<Integer> subGraph = new ArrayList<>();
        connectedComponents = new int[vertexList.size()];
        int count = 1;
        for (int i = 0; i < vertexList.size(); i++)
            if (connectedComponents[i] == 0) {
                ArrayList<Boolean> marked = runBFSUndirected(i);
                for (int j = 0; j < vertexList.size(); j++)
                    if (marked.get(j)) {
                        subGraph.add(vertexList.get(j).value);
                        connectedComponents[j] = count;
                    }
                List<Integer> f = new ArrayList<>();
                for (Integer x : subGraph){
                //for(Integer x = subGraph; x++)
                    f.add(x);
                    System.out.print(x + " ");
                }

                consequence.add(f);
                System.out.println();
                subGraph.clear();
                count++;
            }
        return consequence;
    }

    @Override
    public boolean hasEulerPath() {
        int consequence = 1;
        if (isConnected()) {
            int count = 0;
            for (int i = 0; i < vertexList.size(); i++)
                if ((vertexList.get(i).nodeLinkedList.size() % 2) != 0)
                    count++;
            if (count == 2)
                consequence = 1;
            if (count == 0)
                consequence = 2;
            else
                consequence = 0;
        }

        return consequence == 1;
    }

    ArrayList<Boolean> marked() {
        ArrayList<Boolean> marked = new ArrayList<>();
        for (int i = 0; i < vertexList.size(); ++i)
            marked.add(false);
        return marked;
    }

    private ArrayList<Boolean> runBFSUndirected(int startVertex) {
        ArrayList<Boolean> marked = new ArrayList<>();
        for (int i = 0; i < vertexList.size(); ++i)
            marked.add(false);
        BFSUndirected(marked, startVertex);
        return marked;
    }

    private void BFSUndirected(ArrayList<Boolean> marked, int startVertex) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(startVertex);
        marked.set(startVertex, true);
        while (queue.size() > 0) {
            int u = queue.remove();
            Iterable<Integer> iterable = new AdjacencyList(vertexList.get(u).nodeLinkedList);
            for (Integer v : iterable) {
                for (Node node : vertexList)
                    if (node.value == v){
                        v = vertexList.indexOf(node);
                        break;
                    }
                if (!marked.get(v)) {
                    marked.set(v, true);
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

        ArrayList<Boolean> marked = new ArrayList<>();
        ArrayList<Boolean> tempVisited = new ArrayList<>();
        for (int i = 0; i < vertexList.size(); i++) {
            marked.add(false);
            tempVisited.add(false);
        }

        int count1 = Undirected(u, marked);
        deleteEdge(u, v);
        int count2 = Undirected(u, tempVisited);
        addEdge(vertexList.get(u).value, vertexList.get(v).value);
        return count1 == count2;
    }

    private int Undirected(int i, ArrayList<Boolean> marked) {
        marked.set(i, true);
        int count = 1;
        Iterable<Integer> adjLst = new AdjacencyList(helperList.get(i).nodeLinkedList);
        for (Integer next : adjLst) {
            for (Node node : helperList)
                if (node.value == next){
                    next = helperList.indexOf(node);
                    break;
                }
            if (!marked.get(next))
                count += Undirected(next, marked);
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
    public Iterator<Integer> iterator() {
        return linkedList.iterator();
    }
}
