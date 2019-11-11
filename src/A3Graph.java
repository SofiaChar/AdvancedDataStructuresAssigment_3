import java.util.List;

public interface A3Graph<T> {
	
	public void addVertex(T vertex);
	public void addEdge(T sourceVertex, T targetVertex);
	
	
	public boolean isConnected();
	public boolean isAcyclic();	
	
	public List<List<T>> connectedComponents();
	
	default public boolean hasEulerPath() {
	    System.out.println("Not implemented");
	    return false;
	}
	
	default public List<T> eulerPath(){
	    System.out.println("Not implemented");
	    return null;
	}
	
}
