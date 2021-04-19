/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algorithm2_phase2_group1;

public class Edge {

    int weight;
    int src_v;//number of vertex src_v(source) 
    int dest_u;//number of vertex dest_u(destination)

    //determined info of Edge : source vertex src_v, destenation vertex dest_u , weight bw them 
    public Edge(int i, int j, int weight) {
        this.weight = weight;
        this.src_v = i;
        this.dest_u = j;
    }

    
    //check main obj ==> source src_v , destination dest_u  
    public boolean equals(Object o) {
        //related to contains (in exist method)
        //System.out.println("**"+((Edge) o).src_v+ " & "+ ((Edge) o).dest_u);
        return (this.src_v == ((Edge) o).src_v && this.dest_u == ((Edge) o).dest_u);
        //(1,2)=5 (1,2)=3
        // this.src_v = 1 ,this.dest_u = 2 
        //((Edge) o).src_v = my graph ==> check all src_v in myGraph 
        //((Edge) o).dest_u) = my graph ==> check all src_v in myGraph 
        // 1 is exist before && 1 is exist before
    }

    @Override
    public String toString() {
        return "(" + src_v + "," + dest_u + ")->" + weight;
    }

}
