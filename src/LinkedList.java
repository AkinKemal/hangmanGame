import java.util.ArrayList;

public class LinkedList {

    private LinkedNode head;
    private int size;

    public LinkedList() {
        this.head = null;
        this.size = 0;
    }

    public void add(ArrayList<Boolean> booleanWord) {
        LinkedNode node = new LinkedNode();
        node.setBooleanWord(booleanWord);
        if (isEmpty()) {
            this.head = node;
        } else {
            LinkedNode current = this.head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(node);
        }
        size++;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public ArrayList<Boolean> getBooleanWord(int index) {
        if (index < 0 || index >= size) {
            System.out.println("LinkedList.java  --->  getBooleanWord Method");
            throw new IndexOutOfBoundsException("Index is out of bounds!");
        }

        LinkedNode current = this.head;
        while (current.getNext() != null || index <= 0) {
            current = current.getNext();
            index--;
        }
        return current.getBooleanWord();
    }
}