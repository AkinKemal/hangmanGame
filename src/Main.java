public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        HangmanGameGUI hangmanGameGUI = new HangmanGameGUI("words.txt", "randomWords.txt");
        hangmanGameGUI.setVisible(true);
    }

}