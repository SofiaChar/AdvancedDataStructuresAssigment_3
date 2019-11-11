import java.util.List;

public class MyUndirectedGraph<T> implements A3Graph<T> {

    @Override
    public void addVertex(T vertex) {
	// TODO Auto-generated method stub

    }

    @Override
    public void addEdge(T sourceVertex, T targetVertex) {
	// TODO Auto-generated method stub

    }

    @Override
    public boolean isConnected() {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public boolean isAcyclic() {
	// TODO Auto-generated method stub
	return false;
    }

    @Override
    public List<List<T>> connectedComponents() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public boolean hasEulerPath() {
	// TODO Auto-generated method stub
	return A3Graph.super.hasEulerPath();
    }

    @Override
    public List<T> eulerPath() {
	// TODO Auto-generated method stub
	return A3Graph.super.eulerPath();
    }
    
    

}
