import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class MyUndirectedGraph implements A3Graph {

    int[] connectedComponents;
    ArrayList<Node> vertexList = new ArrayList<>();
    ArrayList<Node> shallow = new ArrayList<>();
    List<Integer> eulerList = new ArrayList<>();

    MyUndirectedGraph() {
    }

    MyUndirectedGraph(int v) {
        for (int i = 0; i < v; i++) addVertex(i);
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
            if (node.getVal() == sourceVertex){
                source = vertexList.indexOf(node);
                break;
            }
        vertexList.get(source).getNodeLinkedList().add(targetVertex);
        shallow.get(source).getNodeLinkedList().add(targetVertex);

        for (Node node : vertexList)
            if (node.getVal() == targetVertex){
                source = vertexList.indexOf(node);
                break;
            }
        vertexList.get(source).getNodeLinkedList().add(sourceVertex);
        shallow.get(source).getNodeLinkedList().add(sourceVertex);

    }

    Iterable<Integer> adj(int x) {
        return new AdjacencyList(vertexList.get(x).getNodeLinkedList());
    }

    Iterable<Integer> adjacentShallow(int x) {
        return new AdjacencyList(shallow.get(x).getNodeLinkedList());
    }

    public int getVertices() {
        return vertexList.size();
    }

    @Override
    public boolean isConnected() {
        return preprocess() == 2;
    }

    private int preprocess() {
        connectedComponents = new int[getVertices()];
        int k = 1;
        for (int i = 0; i < getVertices(); i++)
            if (connectedComponents[i] == 0) {
                ArrayList<Boolean> visited = bfsInterface(i);
                for (int j = 0; j < getVertices(); j++)
                    if (visited.get(j))
                        connectedComponents[j] = k;
                k++;
            }
        return k;
    }

    @Override
    public boolean isAcyclic() {
        ArrayList<Boolean> visited = visitArray();
        for (int u = 0; u < getVertices(); u++)
            if (!visited.get(u)) // Don't recur for u if already visited
                if (isCyclic(u, visited, -1))
                    return false;
        return true;
    }

    private boolean isCyclic(int startVertex, ArrayList<Boolean> visited, int parent) {
        visited.set(startVertex, true);
        Iterable<Integer> adjLst = adj(startVertex);
        for (Integer i : adjLst) {
            for (Node node : vertexList)
                if (node.getVal() == i){
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
        connectedComponents = new int[getVertices()];
        int k = 1;
        for (int i = 0; i < getVertices(); i++)
            if (connectedComponents[i] == 0) {
                ArrayList<Boolean> visited = bfsInterface(i);
                for (int j = 0; j < getVertices(); j++)
                    if (visited.get(j)) {
                        subGraph.add(vertexList.get(j).getVal());
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
        int res = 0;
        if (isConnected()) {
            int odd = 0;
            for (int i = 0; i < getVertices(); i++)
                if ((vertexList.get(i).getNodeLinkedList().size() % 2) != 0)
                    odd++;
            if (odd == 0) res = 2;
            else if (odd == 2) res = 1;
            else res = 0;
        }
        return res == 1;
    }

    ArrayList<Boolean> visitArray() {
        ArrayList<Boolean> visited = new ArrayList<>();
        for (int i = 0; i < getVertices(); ++i)
            visited.add(false);
        return visited;
    }

    private ArrayList<Boolean> bfsInterface(int startVertex) {
        ArrayList<Boolean> visited = visitArray();
        bfs(visited, startVertex);
        return visited;
    }

    private void bfs(ArrayList<Boolean> visited, int startVertex) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(startVertex);
        visited.set(startVertex, true);
        while (queue.size() > 0) {
            int u = queue.remove();
            Iterable<Integer> iterable = adj(u);
            for (Integer v : iterable) {
                for (Node node : vertexList)
                    if (node.getVal() == v){
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

    public static void main(String[] args) {
        MyUndirectedGraph g = new MyUndirectedGraph(7);
        g.addEdge(0, 1);
        g.addEdge(1, 2);
        g.addEdge(2, 3);
        g.addEdge(2, 4);
        g.addEdge(3, 4);
        g.addEdge(3, 5);
        g.addEdge(5, 6);
        g.addEdge(6, 0);
        System.out.println("GRAPH 1");
        System.out.println(g.eulerPath());

        System.out.println("-----------------");
        System.out.println("GRAPH 2");
        MyUndirectedGraph g1 = new MyUndirectedGraph();
        for (int i = 1; i < 6; i++) g1.addVertex(i);
        g1.addEdge(5, 1);
        g1.addEdge(5, 3);
        g1.addEdge(1, 3);
        g1.addEdge(4, 3);
        g1.addEdge(4, 2);
        g1.addEdge(4, 1);
        g1.addEdge(1, 2);
        g1.addEdge(2, 3);

        System.out.println("-----------------");
        System.out.println("GRAPH 3");
        MyUndirectedGraph dd = new MyUndirectedGraph(2);

        System.out.println("-----------------");
        System.out.println("GRAPH 4");
        MyUndirectedGraph sick = new MyUndirectedGraph();
        sick.addVertex(5);
        sick.addVertex(6);
        sick.addVertex(7);
        sick.addEdge(5, 6);

        System.out.println("-----------------");
        MyUndirectedGraph f = new MyUndirectedGraph(4);
        f.addEdge(0, 1);
        f.addEdge(1, 2);
        f.addEdge(2, 3);
        f.addEdge(3, 0);
        System.out.println("GRAPH 5");
    }

    @Override
    public List<Integer> eulerPath() {
        if (!hasEulerPath()) return null;
        int u = 0;
        for (int i = 0; i < getVertices(); i++)
            if (shallow.get(i).getNodeLinkedList().size() % 2 == 1) {
                u = i;
                eulerList.add(shallow.get(u).getVal());
                break;
            }
        pathWalker(u);
        return eulerList;
    }

    private void pathWalker(int startVertex) {
        Iterable<Integer> adjLst = adjacentShallow(startVertex);
        for (Integer target : adjLst) {
            for (Node node : shallow)
                if (node.getVal() == target){
                    target = shallow.indexOf(node);
                    break;
                }
            if (isValidEdge(startVertex, target)) { // target is the position of the node in the shallow adj list
                System.out.print(startVertex + "->" + target + "  ");
                eulerList.add(shallow.get(target).getVal());
                removeEdge(startVertex, target);
                pathWalker(target);
            }
        }
    }

    private boolean isValidEdge(int u, int v) {
        if (shallow.get(u).getNodeLinkedList().size() == 1)
            return true;
        ArrayList<Boolean> visited = visitArray();
        int count1 = dfsWalk(u, visited);
        removeEdge(u, v);
        visited = visitArray();
        int count2 = dfsWalk(u, visited);
        addEdge(vertexList.get(u).getVal(), vertexList.get(v).getVal());
        return count1 == count2;
    }

    private int dfsWalk(int i, ArrayList<Boolean> visited) {
        visited.set(i, true);
        int count = 1;
        Iterable<Integer> adjLst = adjacentShallow(i);
        for (Integer target : adjLst) {
            for (Node node : shallow)
                if (node.getVal() == target){
                    target = shallow.indexOf(node);
                    break;
                }
            if (!visited.get(target))
                count += dfsWalk(target, visited);
        }
        return count;
    }

    private void removeEdge(int sourceVertex, int targetVertex) {
        shallow.get(sourceVertex).getNodeLinkedList().remove(shallow.get(sourceVertex).getNodeLinkedList().indexOf(shallow.get(targetVertex).getVal()));
        shallow.get(targetVertex).getNodeLinkedList().remove(shallow.get(targetVertex).getNodeLinkedList().indexOf(shallow.get(sourceVertex).getVal()));
    }

}
