public class Program {

    public static void main(String[] args) {
        System.out.println("---Exercise 1---\n");

        //testing
        MyDirectedGraph graphD2 = new MyDirectedGraph(5);
        graphD2.addEdge(0, 3);
        graphD2.addEdge(0, 2);
        graphD2.addEdge(2, 1);
        graphD2.addEdge(1, 0);
        graphD2.addEdge(3, 4);

        MyUndirectedGraph graphUD2 = new MyUndirectedGraph();
        graphUD2.addVertex(0);
        graphUD2.addVertex(1);
        graphUD2.addVertex(2);
        graphUD2.addVertex(3);
//        graphD2.addVertex(4);
//        graphD2.addVertex(5);
//        graphD2.addVertex(6);
        graphUD2.addEdge(0, 1);
        graphUD2.addEdge(0, 2);
        graphUD2.addEdge(1, 2);
//        graphD2.addEdge(2, 4);
//        graphD2.addEdge(3, 4);
//        graphD2.addEdge(3, 5);
//        graphD2.addEdge(5, 6);
//        graphD2.addEdge(6, 0);


        if (graphUD2.isAcyclic()) {
            System.out.println("Undirected graph. Acyclic!");
        } else {
            System.out.println("Undirected graph. Not Acyclic");
        }

        if (graphD2.isAcyclic()) {
            System.out.println("Directed graph. Acyclic!");
        } else {
            System.out.println("Directed graph. Not Acyclic");
        }

        if (graphUD2.isConnected()) {
            System.out.println("Undirected graph. Is connected.");
        } else {
            System.out.println("Undirected graph. Is not connected.");
        }

        if (graphD2.isConnected()) {
            System.out.println("Directed graph. Is connected.");
        } else {
            System.out.println("Directed graph. Is not connected.");
        }

        System.out.println("\nConnected components for undirected graph:");
        graphUD2.connectedComponents();

        System.out.println("\nConnected components for directed graph:");
        graphD2.connectedComponents();
        System.out.println("\n-------------------------\n");


        System.out.println("---Exercise2---");
        System.out.println("---Exercise3---");


        MySocialNetwork x = new MySocialNetwork();
        for(int i = 0;i<17;i++) x.addVertex(i);
        x.addEdge(5,1);
        x.addEdge(5,2);
        x.addEdge(5,4);
        x.addEdge(5,6);
        x.addEdge(5,7);
        x.addEdge(5,8);
        x.addEdge(5,9);
        x.addEdge(5,12);
        x.addEdge(5,13);
        x.addEdge(10,7);
        x.addEdge(10,8);
        x.addEdge(10,9);
        x.addEdge(6,14);
        x.addEdge(1,2);
        x.addEdge(1,4);
        x.addEdge(1,3);
        x.addEdge(2,3);
        x.addEdge(2,4);
        x.addEdge(3,6);
        x.addEdge(3,4);
        x.addEdge(3,15);
        System.out.println(x.possibleFriends(5));
    }


    }
