public class TrieNode {

    private TrieNode parent;

    // Bu düğümün harfi tutar
    private char value;

    // Bu düğüm, bir kelimenin sonu mu yoksa henüz kelimenin ortasında mı olduğumuzu belirtmek için kullanılır
    private boolean isEndOfWord;

    // Bu düğümün alt düğümlerini, her harfin bir alt düğümle eşleştiği bir Trie veri yapısı olarak saklar.
    private TrieNode[] children;

    public TrieNode(char value) {
        this.setParent(null);
        this.setValue(value);
        this.setEndOfWord(false);
        this.setChildren(new TrieNode[26]);
    }

    public char getValue() {
        return value;
    }

    public void setValue(char value) {
        this.value = value;
    }

    public boolean isEndOfWord() {
        return isEndOfWord;
    }

    public void setEndOfWord(boolean endOfWord) {
        isEndOfWord = endOfWord;
    }

    public TrieNode[] getChildren() {
        return children;
    }

    public void setChildren(TrieNode[] children) {
        this.children = children;
    }

    public TrieNode getParent() {
        return parent;
    }

    public void setParent(TrieNode parent) {
        this.parent = parent;
    }

}