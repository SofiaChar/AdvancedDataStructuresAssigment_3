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

        System.out.println("\nEuler Path:");
        System.out.println(graphUD2.eulerPath());

        System.out.println("\n\n---Exercise3---");


        MySocialNetwork socialNetwork = new MySocialNetwork();
        for(int i = 1;i<16;i++) socialNetwork.addVertex(i);
        socialNetwork.addEdge(5,1);
        socialNetwork.addEdge(5,2);
        socialNetwork.addEdge(5,4);
        socialNetwork.addEdge(5,6);
        socialNetwork.addEdge(5,7);
        socialNetwork.addEdge(5,8);
        socialNetwork.addEdge(5,9);
        socialNetwork.addEdge(5,12);
        socialNetwork.addEdge(5,15);
        socialNetwork.addEdge(10,7);
        socialNetwork.addEdge(7,11);
        socialNetwork.addEdge(10,8);
        socialNetwork.addEdge(10,9);
        socialNetwork.addEdge(6,14);
        socialNetwork.addEdge(1,2);
        socialNetwork.addEdge(1,4);
        socialNetwork.addEdge(1,3);
        socialNetwork.addEdge(2,3);
        socialNetwork.addEdge(2,4);
        socialNetwork.addEdge(3,6);
        socialNetwork.addEdge(3,4);
        socialNetwork.addEdge(3,13);

        System.out.println("NumberOfPeopleAtFriendshipDistance:");
        System.out.println(socialNetwork.numberOfPeopleAtFriendshipDistance(5,2));

        System.out.println("\nFurthestDistanceInFriendshipRelationships:");
        System.out.println( socialNetwork.furthestDistanceInFriendshipRelationships(5));

        System.out.println("\nPossibleFriends:");
        System.out.println(socialNetwork.possibleFriends(5));

        //testing another vertex index
        System.out.println("-------------------");
        System.out.println("NumberOfPeopleAtFriendshipDistance:");
        System.out.println(socialNetwork.numberOfPeopleAtFriendshipDistance(13,2));

        System.out.println("\nFurthestDistanceInFriendshipRelationships:");
        System.out.println( socialNetwork.furthestDistanceInFriendshipRelationships(13));

        System.out.println("\nPossibleFriends:");
        System.out.println(socialNetwork.possibleFriends(13));


    }


    }
