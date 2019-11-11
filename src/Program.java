public class Program {

    public static void main(String[] args) {
        System.out.println("---Exercise 1---\n");

        //testing
        MyDirectedGraph graphCheck = new MyDirectedGraph(5);
        graphCheck.addEdge(0, 1);
        graphCheck.addEdge(1, 2);
        graphCheck.addEdge(2, 3);
        graphCheck.addEdge(3, 0);
        graphCheck.addEdge(2, 4);
        graphCheck.addEdge(4, 2);

        if(graphCheck.isAcyclic()){
            System.out.println("Acyclic!");
        }
        else{
            System.out.println("Not Acyclic");
        }

        if(graphCheck.isConnected()){
            System.out.println("Is connected.");
        }
        else{
            System.out.println("Is not connected.");
        }
        System.out.println("\nConnected components:");
        graphCheck.connectedComponents();
        System.out.println("-------------------------");

        MyDirectedGraph graph1 = new MyDirectedGraph(5);
        graph1.addEdge(0, 3);
        graph1.addEdge(0, 2);
        graph1.addEdge(2, 1);
        graph1.addEdge(1, 0);
        graph1.addEdge(3, 4);
        // graph1.addEdge(4, 2);
        if(graph1.isAcyclic()){
            System.out.println("Acyclic!");
        }
        else{
            System.out.println("Not Acyclic");
        }

        if(graph1.isConnected()){
            System.out.println("Is connected.");
        }
        else{
            System.out.println("Is not connected.");
        }
        System.out.println("\nConnected components:");
        graph1.connectedComponents();
        System.out.println("-------------------------");

        MyDirectedGraph graph2 = new MyDirectedGraph();
        graph2.addVertex(0);
        graph2.addVertex(2);
        graph2.addVertex(1);
        graph2.addVertex(3);
        graph2.addEdge(0, 1);
        graph2.addEdge(1,2);
        graph2.addEdge(2,3);
        if(graph2.isAcyclic()){
            System.out.println("Acyclic!");
        }
        else{
            System.out.println("Not Acyclic");
        }

        if(graph2.isConnected()){
            System.out.println("Is connected.");
        }
        else{
            System.out.println("Is not connected.");
        }
        System.out.println("\nConnected components:");
        graph2.connectedComponents();


        System.out.println("---Exercise2---");
        System.out.println("---Exercise3---");






    }

}
