package apps;

import structures.*;
import java.util.ArrayList;

public class MST {



	
	/**
	 * Initializes the algorithm by building single-vertex partial trees
	 * 
	 * @param graph Graph for which the MST is to be found
	 * @return The initial partial tree list
	 */
	public static PartialTreeList initialize(Graph graph) {

	    //initialize
		PartialTreeList L = new PartialTreeList();

		//loop through the vertices
		for(Vertex v : graph.vertices){
			PartialTree T = new PartialTree(v);
            MinHeap<PartialTree.Arc> P = T.getArcs();

            //loop through the neighbors linked list add the arcs to P which get passed to T
            Vertex.Neighbor neigh = v.neighbors;
            while (neigh != null){
                PartialTree.Arc arc = new PartialTree.Arc(v, neigh.vertex, neigh.weight);
                P.insert(arc);
                neigh = neigh.next;
            }
            L.append(T);
		}
		return L;
	}




	/**
	 * Executes the algorithm on a graph, starting with the initial partial tree list
	 * 
	 * @param ptlist Initial partial tree list
	 * @return Array list of all arcs that are in the MST - sequence of arcs is irrelevant
	 */
	public static ArrayList<PartialTree.Arc> execute(PartialTreeList ptlist) {

	    //initialize
        ArrayList<PartialTree.Arc> arcs = new ArrayList<PartialTree.Arc>();

        while(ptlist.size() > 1){

            //start by picking up PTX
            PartialTree PTX = ptlist.remove();
            //get highest priority arc "a" from PQX
            PartialTree.Arc a = PTX.getArcs().deleteMin();

            //if v2 also belongs to PTX get the next arc
            while(a.v2.getRoot().equals(PTX.getRoot())){
            	a = PTX.getArcs().deleteMin();
			}


            //add a to the output list of arcs
            arcs.add(a);
			//pickup PTY by finding the tree with a.v2 and merge with PTX
            PartialTree PTY = ptlist.removeTreeContaining(a.v2);
            //merge PQX and PQY if PTY is not null
            PTX.merge(PTY);
            //append the new PTX back to the partial tree list
            ptlist.append(PTX);


            System.out.println("size: " + ptlist.size());
            for (PartialTree pt : ptlist) {
                System.out.println(pt.toString());
            }
        }
		return arcs;
	}


}
