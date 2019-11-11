import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MyDirectedGraph implements A3Graph {
    public static void main(String[] args) {
        MyDirectedGraph g = new MyDirectedGraph(5);
        g.addEdge(0, 1);
        g.addEdge(1, 0);
        g.addEdge(1, 3);
        g.addEdge(2, 1);
        g.addEdge(3, 2);
        g.addEdge(1, 4);
        System.out.println("-------------------------");
        MyDirectedGraph sick = new MyDirectedGraph();
        sick.addVertex(5);
        sick.addVertex(6);
        sick.addVertex(7);
        sick.addEdge(5, 6);
        sick.addEdge(6,7);
        sick.addEdge(7,5);
    }

    private ArrayList<Node> vertexList = new ArrayList<>();
    private List<List<Integer>> connectedComponents = new ArrayList<>();
    private List<Integer> subGraph = new ArrayList<>();

    MyDirectedGraph(int vertex) {
        for (int i = 0; i < vertex; i++)
            addVertex(i);
    }

    @Override
    public void addVertex(int vertex) {
        vertexList.add(new Node(vertex));
    }

    @Override
    public void addEdge(int sourceVertex, int targetVertex) {
        int source = 0;
        for (Node node : vertexList)
            if (node.getVal() == sourceVertex)
                source = vertexList.indexOf(node);
        vertexList.get(source).getNodeLinkedList().add(targetVertex);
    }

    @Override
    public boolean isAcyclic() {
        List<Integer> visited = new ArrayList<>();
        for (int i = 0; i < vertexList.size(); i++)
            if (hasCycle(i, visited))
                return false;
        return true;
    }

    private boolean hasCycle(int node, List<Integer> visited) {
        if (visited.contains(node)) return true;
        visited.add(node);
        Iterable<Integer> adjLst = adj(node);
        for (Integer nextNode : adjLst ){
            for (Node x : vertexList)
                if (x.getVal() == nextNode)
                    nextNode = vertexList.indexOf(x);
            if (hasCycle(nextNode, visited)) return true;
        }
        visited.remove(visited.size() - 1);
        return false;
    }

    Iterable<Integer> adj(int x) {
        return new AdjacencyList(vertexList.get(x).getNodeLinkedList());
    }

    int getVertices() {
        return vertexList.size();
    }

    @Override
    public boolean isConnected() {
        Stack<Integer> stack = new Stack<>();
        ArrayList<Boolean> visited = obscureArray();
        for (int i = 0; i < getVertices(); i++)
            if (!visited.get(i))
                dfsDirected(visited, i, stack);
        MyDirectedGraph graph = getTranspose();
        visited = obscureArray();
        int count = 0;
        while (!stack.isEmpty()) {
            int v = stack.pop();
            if (!visited.get(v)) {
                graph.dfsDirected(visited, v, null);
                System.out.println();
                count++;
            }
        }

        return count == 1;
    }

    private void dfsDirected(ArrayList<Boolean> visited, int startPosition, Stack<Integer> stack) {
        visited.set(startPosition, true);
        if (stack == null) System.out.print(startPosition + " ");
        Iterable<Integer> adjLst = adj(startPosition);
        for (Integer v : adjLst) {
            for (Node node : vertexList)
                if (node.getVal() == v)
                    v = vertexList.indexOf(node);
            if (!visited.get(v)) {
                visited.set(v, true);
                dfsDirected(visited, v, stack);
            }
        }
        if (stack != null) stack.push(startPosition);
    }

    private MyDirectedGraph getTranspose() {
        MyDirectedGraph reverse = new MyDirectedGraph(getVertices());
        for (int i = 0; i < getVertices(); i++)
            for (Integer u : vertexList.get(i).getNodeLinkedList())
                reverse.addEdge(u, i);
        return reverse;
    }

    @Override
    public List<List<Integer>> connectedComponents() {
        Stack<Integer> stack = new Stack<>();
        ArrayList<Boolean> visited = obscureArray();
        for (int i = 0; i < getVertices(); i++)
            if (!visited.get(i))
                dfsDirected(visited, i, stack);
        MyDirectedGraph graph = getTranspose();
        visited = obscureArray();
        while (!stack.isEmpty()) {
            int v = stack.pop();
            if (!visited.get(v)) {
                graph.dfsDirected(visited, v, null);
                subGraph.add(vertexList.get(v).getVal());
                List<Integer> f = new ArrayList<>();
                for (Integer x : subGraph)
                    f.add(x);
                subGraph.clear();
                connectedComponents.add(f);
                System.out.println();
            }
        }
        return connectedComponents;
    }

    private ArrayList<Boolean> obscureArray() {
        ArrayList<Boolean> visited = new ArrayList<>();
        for (int i = 0; i < getVertices(); ++i) visited.add(false);
        return visited;
    }

    public ArrayList<Node> getVertexList() {
        return vertexList;
    }

    @Override
    public boolean hasEulerPath() {
        return false;
    }

    @Override
    public List<Integer> eulerPath() {
        return null;
    }
}
