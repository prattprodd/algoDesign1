import java.util.Iterator;

public class myLinkedList {

    private VertexEdgeNode rend;
    private VertexEdgeNode lstart;
    private int size;

    public VertexEdgeNode getFirstNode(){
        return this.lstart;
    }

    public VertexEdgeNode getLastNode(){
        return this.rend;
    }

    public void addNode(int eidx){
        VertexEdgeNode node = new VertexEdgeNode(eidx, null);
        this.rend.next = node;
        this.rend = node;
    }

    public void removeCurrentNode(VertexEdgeNode node){







    }



    public class VertexEdgeNode{

        private int edgeIndex;
        private VertexEdgeNode next;

        public VertexEdgeNode(int edgeIndex, VertexEdgeNode next){
            this.edgeIndex = edgeIndex;
            this.next = next;
        }

        public void setEdgeIndex(int edgeIndex){
            this.edgeIndex = edgeIndex;
        }

        public int getEdgeIndex(){
            return this.edgeIndex;
        }

        public void setNext(VertexEdgeNode next){
            this.next = next;
        }

        public VertexEdgeNode getNext(){
            return this.next;
        }

    }


}
