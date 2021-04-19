/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm2_phase2_group1;


public class MinHeap {
    int capacity; //number of node in graph
    int currentSize;
    HeapNode[] mH;
    int[] indexes; //will be used to decrease the wieght

    public MinHeap(int capacity) {
        this.capacity = capacity;
        //intialize mH array & start from index 1 so increase length by 1
        mH = new HeapNode[capacity + 1];
        //to know actual index in minheap (start from 0 t0 n-1)
        indexes = new int[capacity];
        //create obj of HeapNode for node0 (has #node & #weight) 
        mH[0] = new HeapNode();
        //intialize weight of node 0 by -infinity 
        mH[0].wieght = Integer.MIN_VALUE;
        //& value be -1
        mH[0].vertex = -1;
        //tell the size of minHeap
        currentSize = 0;
    }

    public void display() {
        for (int i = 0; i <= currentSize; i++) {
            System.out.println(" " + mH[i].vertex + "   key   " + mH[i].wieght);
        }
        System.out.println("________________________");
    }

    //insert node into minHeap
    public void insert(HeapNode x) {
        //update the size of minHeap
        currentSize++;
        int I = currentSize;
        //minHeap will have node x in element [I]
        mH[I] = x;
      //upadate indexes element 
        indexes[x.vertex] = I;
        bubbleUp(I);
    }

    /* minHeap:-(start from index 1)
                          #<----------#
    mH_ind |  0* |  1  |  2  |  3  |  4  |  5  |  //ind=0* not use it
    ============================================
    vertex | -1* |  0  |  1  |  2  |  3  |  4  |        
    --------------------------------------------
    weight |-inf*|  0  | inf | inf | inf | inf |                
    */
    public void bubbleUp(int pos_I) {
        //to know position of parent node
        int parent_I = pos_I / 2;//ex: 0/2 =0 (table) ,ex:4/2=2 parent
        int current_I = pos_I; //ex : 4
        
        // 4>0 (OK) && mH[2]weight=inf > mH[4]weight=inf (notOK) ==>out
        while (current_I > 0 && mH[parent_I].wieght > mH[current_I].wieght) {
            HeapNode currentNode = mH[current_I];
            HeapNode parentNode = mH[parent_I];

            //swap the positions
            indexes[currentNode.vertex] = parent_I;
            indexes[parentNode.vertex] = current_I;
            swap(current_I, parent_I);
            current_I = parent_I;
            parent_I = parent_I / 2;
        }
    }
    
    /* minHeap:-(start from index 1)
    mH_ind |  0* |  1  |  2  |  3  |  4  |  5  |  //ind=0* not use it
    ============================================
    vertex | -1* |  4  |  1  |  2  |  3  |null |  //expand vertex 0    
    --------------------------------------------
    weight |-inf*| inf | inf | inf | inf |null |  
    */
    
    //To extract minimum weight 
    public HeapNode extractMin() {
        //variable to store what in first item in mH(root is the min)
        HeapNode min = mH[1];
        //variable to store what in last item in mH(max child)
        HeapNode lastNode = mH[currentSize];
        //update the indexes[] and move the last node to the top
        indexes[lastNode.vertex] = 1;
        //and move the first node to the down(تبديل الاماكن بينهم)
        mH[1] = lastNode;
        mH[currentSize] = null;
        sinkDown(1);//position 1 has min weight
        currentSize--;
        return min;
    }

    //Do Heapify if root wasn't minimum
    public void sinkDown(int k) {
        int smallest = k;
        int ind_leftChild = 2 * k; //2*1=2 Left children
        int ind_rightChild = 2 * k + 1;//2*1 +1 =3 right children
        
        //if there's another child (not onlt L child) 
        //& weight of root is bigger than his L child ==> change root or smallest 
        if (ind_leftChild < Current_Size_Heap() && mH[smallest].wieght > mH[ind_leftChild].wieght) {
            smallest = ind_leftChild;
        }
        //if there's another child (not onlt R child) 
        //& weight of root is bigger than his R child ==> change root or smallest 
        if (ind_rightChild < Current_Size_Heap() && mH[smallest].wieght > mH[ind_rightChild].wieght) {
            smallest = ind_rightChild;
        }
        if (smallest != k) {
            
            HeapNode smallestNode = mH[smallest];
            HeapNode kNode = mH[k];

            //swap the positions
            indexes[smallestNode.vertex] = k;
            indexes[kNode.vertex] = smallest;
            swap(k, smallest);
            sinkDown(smallest);
        }
    }

    public void swap(int a, int b) {
        HeapNode temp = mH[a];
        mH[a] = mH[b];
        mH[b] = temp;
    }

    public boolean isEmpty() {
        return currentSize == 0;
    }

    public int Current_Size_Heap() {
        return currentSize;
    }
}


