package algorithm2_phase2_group1;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import javafx.util.Pair;




public class Phase2_main {

    static int cases = 10, n, m;
    static long startTime_Kruskal , endTime_Kruskal ;
    
    public static void main(String[] args) throws FileNotFoundException {
        Scanner sc = new Scanner(System.in);
        PrintWriter output = new PrintWriter("output.txt");
        
        long startTime_Prim = 0 , startTime_minHeap = 0 ;
        long endTime_Prim = 0 , endTime_minHeap = 0;
        
        System.out.print("Enter the number of the case you want to run (1-10) : ");
        int i = sc.nextInt();
        
        //for (int i = 1; i <= cases; i++) {

            System.out.println("\n\n  "
                    + "        >>>---- Case" + i + " ----<<<"
                    + "\n");
//            output.println("\n\n  "
//                    + "                  >>>---- Case" + i + " ----<<<"
//                    + "\n");

            //enter your node(n) & egde(m) in graph 
            System.out.println("-------Variables--------\n");
            switch (i) {
                case 1:
                  
                    n = 1000;
                    m = 10000;
                    System.out.println("Number of nodes: "+ n+"\nNumber of edges: "+m);
                    break;
                case 2:
                    n = 1000;
                    m = 15000;
                    System.out.println("Number of nodes: "+ n+"\nNumber of edges: "+m);
                    break;
                case 3:
                    n = 1000;
                    m = 25000;
                    System.out.println("Number of nodes: "+ n+"\nNumber of edges: "+m);
                    break;
                case 4:
                    n = 5000;
                    m = 15000;
                    System.out.println("Number of nodes: "+ n+"\nNumber of edges: "+m);
                    break;
                case 5:
                    n = 5000;
                    m = 25000;
                    System.out.println("Number of nodes: "+ n+"\nNumber of edges: "+m);
                    break;
                case 6:
                    n = 10000;
                    m = 15000;
                    System.out.println("Number of nodes: "+ n+"\nNumber of edges: "+m);
                    break;
                case 7:
                    n = 10000;
                    m = 25000;
                    System.out.println("Number of nodes: "+ n+"\nNumber of edges: "+m);
                    break;
                case 8:
                    n = 20000;
                    m = 200000;
                    System.out.println("Number of nodes: "+ n+"\nNumber of edges: "+m);
                    break;
                case 9:
                    n = 20000;
                    m = 300000;
                    System.out.println("Number of nodes: "+ n+"\nNumber of edges: "+m);
                    break;
                case 10:
                    n = 50000;
                    m = 1000000;
                    System.out.println("Number of nodes: "+ n+"\nNumber of edges: "+m);
                    break;
                default:
                    System.out.println("Sorry This Case Doesn't Here ,Try again !!!!");
            }            
//            System.out.println("m= " + m + " < V(V-1) = " + (n * (n - 1)) + ", n = " + n);
        
            //check maximum number of edges in Direct graph = (n * (n - 1))
            //Printing costs for spanning trees
             System.out.println("\n----Total Costs(ST)-----\n");
            //if (m < (n * (n - 1))) {
                //create object of Type Graph to make graph
                Graph myGraph = make_graph(n, m);
                GraphKruskal graph = make_graph2(n,m);
                
                //ALGORITHMS:
                //1.find case using pq prim algorithm & calculate Runtime
                startTime_Prim = System.nanoTime();
                myGraph.primMST();
                endTime_Prim = System.nanoTime();
                
                //2.find case using minHeap prim algorithm & calculate Runtime
                startTime_minHeap = System.nanoTime();
                myGraph.primMST_minHeap();
                endTime_minHeap = System.nanoTime();
                
                //3.find case using Kruskal algorithm & calculate Runtime 
                startTime_Kruskal = System.nanoTime();
                graph.KruskalMST();
                endTime_Kruskal = System.nanoTime();
                
                //print outputs in "Output File" using method PrintAllToFile in Graph class
                //myGraph.PrintAllToFile(output);
            //} else {
              //  System.out.println("There's an error ,\n Please Another number of vertex");
            
//      }
        
         
        //calculate Time takes for each Algorithm 
        //1. time takes by Prim:
        long estmateTime_Prim = endTime_Prim - startTime_Prim;
        long estmateTime_minHeap = endTime_minHeap - startTime_minHeap;
        long estmateTime_Kruskal = endTime_Kruskal - startTime_Kruskal;
        //System.out.println("estmateTime (millisecond)= " + ((double) estmateTime_Prim) / 1_000_000.0d);
                         //   "\n==============================================="
        System.out.println("\n\n^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"
                         + "\n----------//// RunTime (milliSec) \\\\\\\\----------"
                         + "\n>>_1_ PQ Prim = " + TimeUnit.NANOSECONDS.toMillis(estmateTime_Prim)+" ms"
                         + "\n>>_2_ MinHeap Prim = " +TimeUnit.NANOSECONDS.toMillis(estmateTime_minHeap)+" ms"
                         + "\n>>_3_ Kruskal = " +TimeUnit.NANOSECONDS.toMillis(estmateTime_Kruskal)+" ms"
                         + "\n================================================");
    }
    

    //--------------------------------------------------------------------------
    //-----------------/////////  make_graph  \\\\\\\\--------------------------
    //--------------------------------------------------------------------------
    
    //use Function to generate Graph using 2 parameter : #nodes & #edges 
    public static Graph make_graph(int numberOfNodes, int numberOfEdges) {
        //create obj of type Graph using #nodes(n) entered before
        Graph myGraph = new Graph(numberOfNodes);
       
        int v, u, weight; //vertecies v , u & wieght
        Random ran = new Random(); //random variable 

        //add at least one edge to all nodes
        int least = 0;
        int l = 0;
        while (least < numberOfNodes) { //exit loop when least become equil #node   
            v = least;
            // choose random node (u)
            u = ran.nextInt(numberOfNodes);
            // choose random weight (1-10)
            weight = ran.nextInt(10) + 1;
            //check if the nodes are the same (selfLoop)
            if (v == u) {
                continue;
            }
            //if everything is okay(no selfLoop),add the edge to the graph
            myGraph.addEdge(v, u, weight); 
           //add edge bw v,u on Graph class
            //decrease the number of edges
           
    
             
        
            least++;
            numberOfEdges--;
           
        }

        //add rest edges randomly until #edges = 0
        while (numberOfEdges > 0) {
            // chose random node (v)
            v = ran.nextInt(numberOfNodes);
            // chose random node (u)
            u = ran.nextInt(numberOfNodes);
            // chose random node (weight)
            weight = ran.nextInt(10) + 1;
            //check if the random nodes are the same (selfLoop)
            if (v == u) {
                continue;
            }
            //check if the edges already exists 
            if (myGraph.exists(v, u)) {
                continue;
            }
            //if everything is okay(No selfLoop & exist edge),add the edge to the graph
            myGraph.addEdge(v, u, weight);
           
            //decrease the number of edges
           
            
            numberOfEdges--;
        }
                
        //return all graph 
        return myGraph;
    }
    
    
    //--------------------------------------------------------------------------
    //--------------/////////  make_graph (Kruskal)  \\\\\\\\-------------------
    //--------------------------------------------------------------------------
      public static GraphKruskal make_graph2(int numberOfNodes, int numberOfEdges) {
        //create obj of type Graph using #nodes(n) entered before
        
        GraphKruskal graph = new GraphKruskal(numberOfNodes,numberOfEdges);
        int v, u, weight; //vertecies v , u & wieght
        Random ran = new Random(); //random variable 

        //add at least one edge to all nodes
        int least = 0;
        int l = 0;
        while (least < numberOfNodes) { //exit loop when least become equil #node   
            v = least;
            // choose random node (u)
            u = ran.nextInt(numberOfNodes);
            // choose random weight (1-10)
            weight = ran.nextInt(10) + 1;
            //check if the nodes are the same (selfLoop)
            if (v == u) {
                continue;
            }
            //if everything is okay(no selfLoop),add the edge to the graph
            
            graph.edge[l].src=v;
            graph.edge[l].dest=u;
            graph.edge[l].weight= weight;
            
            //decrease the number of edges
            least++;
            numberOfEdges--;
        }

        //add rest edges randomly until #edges = 0
        while (numberOfEdges > 0) {
            // chose random node (v)
            v = ran.nextInt(numberOfNodes);
            // chose random node (u)
            u = ran.nextInt(numberOfNodes);
            // chose random node (weight)
            weight = ran.nextInt(10) + 1;
            //check if the random nodes are the same (selfLoop)
            if (v == u) {
                continue;
            }
            //check if the edges already exists 
            
            //if everything is okay(No selfLoop & exist edge),add the edge to the graph
            graph.edge[l].src=v;
            graph.edge[l].dest=u;
            graph.edge[l].weight= weight;
            //decrease the number of edges
            numberOfEdges--;
            l++;
        }
        
        
        
        //return all graph 
        return graph;
    }

//------------------------------------------------------------------------------        
}




    