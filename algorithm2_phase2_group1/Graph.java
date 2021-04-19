/*
 * Link refrence of MST minHeap Prim:-
 * https://algorithms.tutorialhorizon.com/prims-minimum-spanning-tree-mst-using-adjacency-list-and-min-heap/
 * Link refrence of MST PQ Prim:- 
 * https://algorithms.tutorialhorizon.com/prims-minimum-spanning-tree-mst-using-adjacency-list-and-priority-queue-without-decrease-key-in-oelogv/
 */
package algorithm2_phase2_group1;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
//---------prim----------
import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import javafx.util.Pair;

public class Graph {

    int numberOfNodes;
    //create aray list to store edges into it 
    ArrayList<Edge>[] Nodes;

    //generate object
    //this parameter has #nodes entered in the begining
    public Graph(int number_Nodes) {
        this.numberOfNodes = number_Nodes;
        //generate arraylist of node with #nodes
        this.Nodes = new ArrayList[number_Nodes];

        // initialize list for all nodes
        for (int i = 0; i < Nodes.length; i++) {
            //initilize node with size 0 first (no edge until now)
            Nodes[i] = new ArrayList<Edge>();
        }
    }

    public void addEdge(int i, int j, int weight) {
        Edge newEdge = new Edge(i, j, weight);
        //if not exist before add this edge to src_v vertex in Nodes
        if (!exists(i, j)) {
            Nodes[i].add(newEdge);
        }
    }

    // method check if edge bw src_v ,dest_u already happened or not using contains method
    public boolean exists(int i, int j) {
        Edge newEdge = new Edge(i, j, 0);
        return Nodes[i].contains(newEdge);
    }

    //print all nodes(vertcies)with its edges
    public void PrintAllToFile(PrintWriter output) throws FileNotFoundException {
        //PrintWriter output = new PrintWriter(fileName);
        //visit all node in graph
        for (int node = 0; node < Nodes.length; node++) {
            //System.out.println("=========================== Node(" + node + ") ===========================");
            //output.println("=========================== Node(" + node + ") ===========================");
            if (Nodes[node] == null || Nodes[node].size() == 0) {
                System.out.println("This node dose not have any edges yet");
                //output.println("This node dose not have any edges yet");
            } else {
                //& print all edges in this specific node 
                for (int i = 0; i < Nodes[node].size(); i++) {
                    //System.out.println(Nodes[node].get(i));
                    //output.println(Nodes[node].get(i)); 
                }
            }
        }
        output.flush();
        output.close();

    }
//------------------------------------------------------------------------------    
//----------------------------<<<<< Prim PQ >>>>>-------------------------------         ***
//------------------------------------------------------------------------------ 

    public void primMST() {

        //store True=node visited or False=node unvisited 
        boolean[] MST = new boolean[numberOfNodes];

        //store the parent & weight(connected with parent)of that child node  
        Results[] resultSet = new Results[numberOfNodes];

        //store the weight of each verttex , also use in update  
        int[] Weights = new int[numberOfNodes];

        for (int i = 0; i < numberOfNodes; i++) {
            //Initialize all item in Weights by maximum number(with is infinity)
            Weights[i] = Integer.MAX_VALUE;

            //initialize results by weight=0 & parent=0 
            resultSet[i] = new Results();
        }

        //Initialize priority queue
        PriorityQueue<Pair<Integer, Integer>> pq = new PriorityQueue<>(numberOfNodes, new Comparator<Pair<Integer, Integer>>() {
            //override the comparator to do the sorting according to keys that we have here 
            @Override
            public int compare(Pair<Integer, Integer> p1, Pair<Integer, Integer> p2) {
                //sort using weight values
                int key1 = p1.getKey();
                int key2 = p2.getKey();
                return key1 - key2; //v=3,dest_u=1 , 3-1 = 2 ==> src_v >? dest_u-src_v (X wrong)??????????
                //reaturn 1 if src_v > dest_u , return -1 if src_v < dest_u????????????
            }
        }
        );

        //update weight of node 0 so become: infinity ==> *0*
        Weights[0] = 0;
        //to add node & weight into priority Queue as pair 
        Pair<Integer, Integer> p0 = new Pair<>(Weights[0], 0); //v=0,weight=0
        //add it to pq
        pq.offer(p0);

        resultSet[0] = new Results();//??????????????
        //First(start)node doesn't have parent 
        resultSet[0].parent = -1;

        //while priority queue is not empty
        while (!pq.isEmpty()) {
            //extract the smallest ,example: remove first node from pq so become 0
            Pair<Integer, Integer> extractedPair = pq.poll();

            //this var take the value(#node)that removed from pq
            int extractedVertex = extractedPair.getValue();
            //remove from pq means , this node is visited 
            MST[extractedVertex] = true; //mst[0]=false ==> MST[0]=true

            //put the edges from the specific node in list
            //ex: first node 0 ,that has edges first has(src_v=0,dest_u=4,w=7) , sec has(src_v=0,dest_u=3,w=9) 
            ArrayList<Edge> list = Nodes[extractedVertex];

            //iterate through all the adjacent numberOfNodes and update the keys
            for (int i = 0; i < list.size(); i++) {
                //get info of egde: src_v , dest_u ,weight
                Edge edge = list.get(i);
                //if edge dest_u is not visited in MST 
                if (MST[edge.dest_u] == false) {
                    int x = edge.dest_u; //ex: dest_u = edge.3 ==> dest_u=3
                    //use it to update weight
                    int new_W = edge.weight;

                    //check if current weight > new weight, if yes==> update it
                    if (Weights[x] > new_W) {  //inf> 1
                        //add node&weight to the priority queue as pair 
                        Pair<Integer, Integer> p = new Pair<>(new_W, x); //add new weight for this vertex dest_u 
                        //add pair to PQ 
                        pq.offer(p);
                        //update the info of results for dest_u 
                        resultSet[x].parent = extractedVertex;
                        resultSet[x].weight = new_W;
                        //update the weight(weight) of the dest_u dest_u 
                        Weights[x] = new_W;
                    }
                }
            }
        }
        //print MST
        printMST(resultSet);
    }

    public void printMST(Results[] resultSet) {
        int Min_Spanning_Tree = 0;
//        System.out.println("Minimum Spanning Tree: ");
        for (int i = 1; i < numberOfNodes; i++) {
//            System.out.println("Edge from ( " + results[i].parent + " ) to ( " + i
//                    + " ), weight = " + results[i].weight);
            Min_Spanning_Tree += resultSet[i].weight;
        }
        System.out.print(">>_Minimum Spanning Tree(priority queue) = " + Min_Spanning_Tree + "\n");
       //for (int i = 1; i < numberOfNodes; i++) {
           //System.out.print(resultSet[i].weight);
           // if(i == (numberOfNodes-1)){    break;    }
           //System.out.print(" + ");
       //} //System.out.print(" = " + Min_Spanning_Tree+"\n\n");
    }

    static class Results {
        //declare 2 variable 
        int parent;
        int weight;
    }

//------------------------------------------------------------------------------    
//--------------------------<<<<< MinHeap Prim >>>>>----------------------------
//------------------------------------------------------------------------------    
    public void primMST_minHeap() {

        //store if node visited(True) ot not(False)
        //also keep track vertices in minHeap
        boolean[] inHeap = new boolean[numberOfNodes];
        //store the parent & weight of that child node  
        Results[] results = new Results[numberOfNodes];
        //store wight to know weight & if wieght of node update(number) or not(infinity)
        int[] Wieghts = new int[numberOfNodes];
        //store the vertex & it's weight  
        HeapNode[] heapNodes = new HeapNode[numberOfNodes];
        
        //initialize all items in each array & give it value 
        for (int i = 0; i < numberOfNodes; i++) {
            //create obj for each item of heapNodes
            heapNodes[i] = new HeapNode();
            //initialize vertex by #node 
            heapNodes[i].vertex = i;
            //initialize all vertex weight by infinity expect first by 0 (down)
            heapNodes[i].wieght = Integer.MAX_VALUE;
            results[i] = new Results();
            //initialize vertex which doesn't have parent 
            results[i].parent = -1;
            //if vertex in Heap =true , if it's removed= false
            inHeap[i] = true;
            //Initialize all item in Weights by maximum number(with is infinity)
            Wieghts[i] = Integer.MAX_VALUE;
        }

        //update weight of node 0 so become: infinity ==> *0*
        heapNodes[0].wieght = 0;

        //initialize minHeap with lenght
        MinHeap minHeap = new MinHeap(numberOfNodes);
        //add all the vertices to minHeap
        for (int i = 0; i < numberOfNodes; i++) {
            //insert nide i in minHeap
            minHeap.insert(heapNodes[i]);
        }

        //while minHeap is not empty
        while (!minHeap.isEmpty()) {
            //extract the min(root)
            HeapNode extractedNode = minHeap.extractMin();

            //extract min vertex
            int extractedVertex = extractedNode.vertex;
            //vertex is not in Heap any more
            inHeap[extractedVertex] = false;

            //put the edges from the specific node(extractedVertex)in list
            //ex: first node 0 ,that has edges first has(src_v=0,dest_u=4,w=7) , sec has(src_v=0,dest_u=3,w=9) 
            ArrayList<Edge> list = Nodes[extractedVertex];
            for (int i = 0; i < list.size(); i++) {
                Edge edge = list.get(i);
                //only if edge dest_u is present in heap
                if (inHeap[edge.dest_u]) {
                    int x = edge.dest_u; //x is # of destination node 
                    //use it to update weight
                    int new_W = edge.weight;
                    //check if weight(in Weights array) > new weight, if yes==> update it
                    if (Wieghts[x] > new_W){
                        
                        decreaseKey(minHeap, new_W, x);
                        //update the parent node for dest_u & weight 
                        results[x].parent = extractedVertex;
                        results[x].weight = new_W;
                        Wieghts[x] = new_W;
                    }
                }
            }
        }
        //print mst
        printMST_minHeap(results);
    }

    //to update weight & remove the min 
    public void decreaseKey(MinHeap minHeap, int new_W, int vertex){
            //get the index which Wieghts's needs a decrease;
            int index = minHeap.indexes[vertex];
            //get the node and update its value
            HeapNode node = minHeap.mH[index];
            //update weight of vertex index in minHeap
            node.wieght= new_W;
            minHeap.bubbleUp(index);
        }
    
    public void printMST_minHeap(Results[] resultSet){
            int total_min_weight = 0;
//            System.out.println("Minimum Spanning Tree: ");
            for (int i = 1; i <numberOfNodes ; i++) {
//                System.out.println("Edge: " + i + " - " + results[i].parent +
//                        " weight: " + results[i].weight);
                total_min_weight += resultSet[i].weight;
            }
            System.out.println(">>_Minimum Spanning Tree(minHeap) = " + total_min_weight);
        }
    
//    public void Trace_pq(Results[] results){
//        
//    }
    
    

}







//    public void printNodeEdges(int node) {
//        if (Nodes[node] == null || Nodes[node].size() == 0) {
//            System.out.println("This node dose not have any edges yet");
//        } else {
//            for (int src_v = 0; src_v < Nodes[node].size(); src_v++) {
//                System.out.println(Nodes[node].get(src_v));
//            }
//        }
//    }
//  
//    public void PrintAll() {
//        for (int src_v = 0; src_v < Nodes.length; src_v++) {
//            System.out.println("=========================== Node(" + src_v + ") ===========================");
//            printNodeEdges(src_v);
//        }
//    }
// not important 
//	public boolean removeEdge(int src_v, int dest_u) {
//		if (exists(src_v, dest_u)) {
//			Edge removedEdge = new Edge(src_v, dest_u, 0);
//			Nodes[src_v].remove(removedEdge);
//			return true;
//		} else {
//			return false;
//		}
//
//	}
/*  tree vertex Vt(results)                           Remaining V-Vt
 --------------                                      --------------
 a(-,-)                                     fringe:  c(b ,1) ...  
 b(a=parent, 3=weight)                      unseen:  d(- ,inf)      
    
 */
