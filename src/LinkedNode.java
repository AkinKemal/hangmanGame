import java.util.ArrayList;

public class LinkedNode {

    private ArrayList<Boolean> booleanWord;
    private LinkedNode next;

    public LinkedNode() {
        this.setBooleanWord(new ArrayList<>());
        this.setNext(null);
    }

    public ArrayList<Boolean> getBooleanWord() {
        return booleanWord;
    }

    public void setBooleanWord(ArrayList<Boolean> booleanWord) {
        this.booleanWord = booleanWord;
    }

    public LinkedNode getNext() {
        return next;
    }

    public void setNext(LinkedNode next) {
        this.next = next;
    }
}