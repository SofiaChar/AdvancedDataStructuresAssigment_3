import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class MyDirectedGraph implements A3Graph {


    private ArrayList<Node> vertexList = new ArrayList<>();
    private List<List<Integer>> connectedComponents = new ArrayList<>();
    private List<Integer> subGraph = new ArrayList<>();

    MyDirectedGraph(int vertex) {
        for (int i = 0; i < vertex; i++)
            addVertex(i);
    }

    MyDirectedGraph(){

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


    private boolean hasCycle(int node, List<Integer> visited) {
        if (visited.contains(node)) return true;
        visited.add(node);
        Iterable<Integer> adjLst = adjacency(node);
        for (Integer nextNode : adjLst ){
            for (Node x : vertexList)
                if (x.getVal() == nextNode)
                    nextNode = vertexList.indexOf(x);
            if (hasCycle(nextNode, visited)) return true;
        }
        visited.remove(visited.size() - 1);
        return false;
    }

    @Override
    public boolean isAcyclic() {
        List<Integer> visited = new ArrayList<>();
        for (int i = 0; i < vertexList.size(); i++)
            if (hasCycle(i, visited))
                return false;
        return true;
    }


    Iterable<Integer> adjacency(int v) {
        return new AdjacencyList(vertexList.get(v).getNodeLinkedList());
    }

    int getVertices() {
        return vertexList.size();
    }

    @Override
    public boolean isConnected() {
        Stack<Integer> stack = new Stack<>();
        ArrayList<Boolean> visited = setUnvisited();
        for (int i = 0; i < getVertices(); i++)
            if (!visited.get(i))
                DFSDirected(visited, i, stack);
        MyDirectedGraph graph = transpose();
        visited = setUnvisited();
        int count = 0;
        while (!stack.isEmpty()) {
            int v = stack.pop();
            if (!visited.get(v)) {
                graph.DFSDirected(visited, v, null);
                System.out.println();
                count++;
            }
        }

        return count == 1;
    }

    private void DFSDirected(ArrayList<Boolean> visited, int startPos, Stack<Integer> stack) {
        visited.set(startPos, true);
        if (stack == null) System.out.print(startPos + " ");
        Iterable<Integer> adjLst = adjacency(startPos);
        for (Integer v : adjLst) {
            for (Node node : vertexList)
                if (node.getVal() == v)
                    v = vertexList.indexOf(node);
            if (!visited.get(v)) {
                visited.set(v, true);
                DFSDirected(visited, v, stack);
            }
        }
        if (stack != null) stack.push(startPos);
    }

    private MyDirectedGraph transpose() {
        MyDirectedGraph reverse = new MyDirectedGraph(getVertices());
        for (int i = 0; i < getVertices(); i++)
            for (Integer u : vertexList.get(i).getNodeLinkedList())
                reverse.addEdge(u, i);
        return reverse;
    }

    @Override
    public List<List<Integer>> connectedComponents() {
        Stack<Integer> stack = new Stack<>();
        ArrayList<Boolean> visited = setUnvisited();
        for (int i = 0; i < getVertices(); i++)
            if (!visited.get(i))
                DFSDirected(visited, i, stack);
        MyDirectedGraph graph = transpose();
        visited = setUnvisited();
        while (!stack.isEmpty()) {
            int v = stack.pop();
            if (!visited.get(v)) {
                graph.DFSDirected(visited, v, null);
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

    private ArrayList<Boolean> setUnvisited() {
        ArrayList<Boolean> visited = new ArrayList<>();
        for (int i = 0; i < getVertices(); ++i)
            visited.add(false);
        return visited;
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
