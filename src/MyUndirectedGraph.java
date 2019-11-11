import java.util.*;

public class MyUndirectedGraph implements A3Graph {

    public void print(){
        System.out.println("\nNODES:");
        for (Node node : vertexList)
            System.out.println(node.val);
        System.out.println("\nList of edges:");
        for (int i = 0; i < vertexList.size(); i++)
            System.out.println(vertexList.get(i).nodeLinkedList);
    }

    public int[] connectedComponents;
    public ArrayList<Node> vertexList = new ArrayList<>();
    public ArrayList<Node> shallow = new ArrayList<>();
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
        shallow.add(new Node(vertex));
    }

    @Override
    public void addEdge(int sourceVertex, int targetVertex) {
        int source = 0;
        for (Node node : vertexList)
            if (node.val == sourceVertex){
                source = vertexList.indexOf(node);
                break;
            }
        vertexList.get(source).nodeLinkedList.add(targetVertex);
        shallow.get(source).nodeLinkedList.add(targetVertex);

        for (Node node : vertexList)
            if (node.val == targetVertex){
                source = vertexList.indexOf(node);
                break;
            }
        vertexList.get(source).nodeLinkedList.add(sourceVertex);
        shallow.get(source).nodeLinkedList.add(sourceVertex);

    }

    Iterable<Integer> adjacentShallow(int x) {
        return new AdjacencyList(shallow.get(x).nodeLinkedList);
    }

    @Override
    public boolean isConnected() {
        connectedComponents = new int[vertexList.size()];
        int countConnComp = 1;
        for (int i = 0; i < vertexList.size(); i++)
            if (connectedComponents[i] == 0) {
                ArrayList<Boolean> visited = runBFS(i);
                for (int j = 0; j < vertexList.size(); j++)
                    if (visited.get(j))
                        connectedComponents[j] = countConnComp;
                countConnComp++;
            }
        return countConnComp == 2;
    }


    @Override
    public boolean isAcyclic() {
        ArrayList<Boolean> visited = visitArray();
        for (int u = 0; u < vertexList.size(); u++)
            if (!visited.get(u)) // Don't recur for u if already visited
                if (isCyclic(u, visited, -1))
                    return false;
        return true;
    }

    private boolean isCyclic(int startVertex, ArrayList<Boolean> visited, int parent) {
        visited.set(startVertex, true);
        Iterable<Integer> adjLst = new AdjacencyList(vertexList.get(startVertex).nodeLinkedList);
        for (Integer i : adjLst) {
            for (Node node : vertexList)
                if (node.val == i){
                    i = vertexList.indexOf(node);
                    break;
                }
            if (!visited.get(i)) {
                if (isCyclic(i, visited, startVertex)) return true;
            } else if (i != parent)
                return true;
        }
        return false;
    }

    @Override
    public List<List<Integer>> connectedComponents() {
        List<List<Integer>> res = new ArrayList<>();
        List<Integer> subGraph = new ArrayList<>();
        connectedComponents = new int[vertexList.size()];
        int k = 1;
        for (int i = 0; i < vertexList.size(); i++)
            if (connectedComponents[i] == 0) {
                ArrayList<Boolean> visited = runBFS(i);
                for (int j = 0; j < vertexList.size(); j++)
                    if (visited.get(j)) {
                        subGraph.add(vertexList.get(j).val);
                        connectedComponents[j] = k;
                    }
                List<Integer> f = new ArrayList<>();
                for (Integer x : subGraph)
                    f.add(x);
                res.add(f);
                subGraph.clear();
                k++;
            }
        //System.out.println("list size : ? = " + res.size());
        return res;
    }

    @Override
    public boolean hasEulerPath() {
        int res = 1;
        if (isConnected()) {
            int odd = 0;
            for (int i = 0; i < vertexList.size(); i++)
                if ((vertexList.get(i).nodeLinkedList.size() % 2) != 0)
                    odd++;
            if (odd == 0) res = 2;
            else if (odd == 2) res = 1;
            else res = 0;
        }
        return res == 1;
    }

    ArrayList<Boolean> visitArray() {
        ArrayList<Boolean> visited = new ArrayList<>();
        for (int i = 0; i < vertexList.size(); ++i)
            visited.add(false);
        return visited;
    }

    private ArrayList<Boolean> runBFS(int startVertex) {
        ArrayList<Boolean> visited = new ArrayList<>();
        for (int i = 0; i < vertexList.size(); ++i)
            visited.add(false);
        bfs(visited, startVertex);
        return visited;
    }

    private void bfs(ArrayList<Boolean> visited, int startVertex) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(startVertex);
        visited.set(startVertex, true);
        while (queue.size() > 0) {
            int u = queue.remove();
            Iterable<Integer> iterable = new AdjacencyList(vertexList.get(u).nodeLinkedList);
            for (Integer v : iterable) {
                for (Node node : vertexList)
                    if (node.val == v){
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



    @Override
    public List<Integer> eulerPath() {
        if (!hasEulerPath()) return null;
        int u = 0;
        for (int i = 0; i < vertexList.size(); i++)
            if (shallow.get(i).nodeLinkedList.size() % 2 == 1) {
                u = i;
                eulerList.add(shallow.get(u).val);
                break;
            }
        pathWalker(u);
        return eulerList;
    }

    private void pathWalker(int startVertex) {
        Iterable<Integer> adjLst = adjacentShallow(startVertex);
        for (Integer target : adjLst) {
            for (Node node : shallow)
                if (node.val == target){
                    target = shallow.indexOf(node);
                    break;
                }
            if (isValidEdge(startVertex, target)) { // target is the position of the node in the shallow adj list
                System.out.print(startVertex + "->" + target + "  ");
                eulerList.add(shallow.get(target).val);
                removeEdge(startVertex, target);
                pathWalker(target);
            }
        }
    }

    private boolean isValidEdge(int u, int v) {
        if (shallow.get(u).nodeLinkedList.size() == 1)
            return true;
        ArrayList<Boolean> visited = visitArray();
        int count1 = dfsWalk(u, visited);
        removeEdge(u, v);
        visited = visitArray();
        int count2 = dfsWalk(u, visited);
        addEdge(vertexList.get(u).val, vertexList.get(v).val);
        return count1 == count2;
    }

    private int dfsWalk(int i, ArrayList<Boolean> visited) {
        visited.set(i, true);
        int count = 1;
        Iterable<Integer> adjLst = adjacentShallow(i);
        for (Integer target : adjLst) {
            for (Node node : shallow)
                if (node.val == target){
                    target = shallow.indexOf(node);
                    break;
                }
            if (!visited.get(target))
                count += dfsWalk(target, visited);
        }
        return count;
    }

    private void removeEdge(int sourceVertex, int targetVertex) {
        shallow.get(sourceVertex).nodeLinkedList.remove(shallow.get(sourceVertex).nodeLinkedList.indexOf(shallow.get(targetVertex).val));
        shallow.get(targetVertex).nodeLinkedList.remove(shallow.get(targetVertex).nodeLinkedList.indexOf(shallow.get(sourceVertex).val));
    }

}

class Node {
    public LinkedList<Integer> nodeLinkedList = new LinkedList<>();
    public int val;
    public Node(int val) {
        this.val = val;
    }
    public int getVal() { return val; }
    public LinkedList<Integer> getNodeLinkedList() { return nodeLinkedList; }
}

class AdjacencyList implements Iterable<Integer> {
    private LinkedList<Integer> linkedList;
    public AdjacencyList(LinkedList<Integer> linkedList) {
        this.linkedList = linkedList;
    }
    @Override public Iterator<Integer> iterator() { return linkedList.iterator(); }
}
