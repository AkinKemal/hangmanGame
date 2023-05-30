import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Operations {

    TrieTree trieTree = new TrieTree();
    LinkedList guessedLetters = new LinkedList();
    Scanner scanner = new Scanner(System.in);

    protected static final String ANSI_RESET = "\u001B[0m";
    protected static final String ANSI_RED = "\u001B[31m";
    protected static final String ANSI_GREEN = "\u001B[32m";
    protected static final String ANSI_YELLOW = "\u001B[33m";
    protected static final String SMILING_FACE = "\uD83D\uDE00";
    protected static final String SAD_FACE = "\uD83D\uDE22";
    protected static final String POUTING_FACE = "\uD83D\uDE21";
    protected static final String FACE_WITH_PARTY_HORN_AND_PARTY_HAT = "\uD83E\uDD73";
    protected static final String WAVING_HAND = "\uD83D\uDC4B";
    protected static final String WHITE_RIGHT_POINTING_BACKHAND = "\uD83D\uDC49";
    protected static final String TURKISH_FLAG = "\uD83C\uDDF9\uD83C\uDDF7";
    protected static final String DIAMOND_SHAPE = "\uD83D\uDD38";
    protected static final String DIAMOND = "\uD83D\uDC8E";

    public String fileName;
    public String randomFileName;
    protected ArrayList<String> words;
    protected ArrayList<String> randomWords;
    protected ArrayList<Character> correctLetters;
    protected ArrayList<Character> wrongLetters;
    protected ArrayList<Character> gameTable;
    protected ArrayList<Boolean> booleanArrayList = new ArrayList<>();

    protected char suggestNextLetter;
    protected ArrayList<String> suggestNextNormalWords;
    protected ArrayList<String> suggestNextWords;
    protected ArrayList<String> suggestNextBestWords;

    protected int amountNeededToWin = 0;
    protected int numberOfRounds;
    protected int gameLifeCount;
    protected String word;
    protected int wordLength;
    protected int maxGuesses;
    protected char letter;
    protected boolean isFound;
    public int counter;

    public Operations(String fileName, String randomFileName) {
        this.fileName = fileName;
        this.randomFileName = randomFileName;
        correctLetters = new ArrayList<>();
        wrongLetters = new ArrayList<>();
        gameTable = new ArrayList<>();
    }

    public String hint01ForGuessedLetterAndWords() {
        if (trieTree.search(word)) {
            suggestNextLetter = trieTree.hintForGuessedLetter(gameTable, correctLetters, wrongLetters);
            System.out.println(DIAMOND_SHAPE + ANSI_GREEN + " 1st level hint: " + ANSI_RESET);
            System.out.println(suggestNextLetter);
            printGameTable(booleanArrayList);
        } else {
            System.out.println(ANSI_RED + "Warning! Word not found " + ANSI_RESET + POUTING_FACE);
        }
        return String.valueOf(suggestNextLetter);
    }

    public ArrayList<String> hint02ForGuessedLetterAndWords() {
        if (trieTree.search(word)) {
            suggestNextNormalWords = trieTree.theNormalHintForGuessedWord(wordLength, correctLetters);
            System.out.println(DIAMOND_SHAPE + ANSI_GREEN + " 2nd level hint: " + ANSI_RESET);
            for (String str : suggestNextNormalWords) {
                System.out.print(str + " ");
            }
            System.out.println();
            printGameTable(booleanArrayList);
        } else {
            System.out.println(ANSI_RED + "Warning! Word not found " + ANSI_RESET + POUTING_FACE);
        }
        return suggestNextNormalWords;
    }

    public ArrayList<String> hint03ForGuessedLetterAndWords() {
        if (trieTree.search(word)) {
            suggestNextWords = trieTree.hintForGuessedWord(gameTable);
            System.out.println(DIAMOND_SHAPE + ANSI_GREEN + " 3rd level hint: " + ANSI_RESET);
            for (String str : suggestNextWords) {
                System.out.print(str + " ");
            }
            System.out.println();
            printGameTable(booleanArrayList);
        } else {
            System.out.println(ANSI_RED + "Warning! Word not found " + ANSI_RESET + POUTING_FACE);
        }
        return suggestNextWords;
    }

    public ArrayList<String> hint04ForGuessedLetterAndWords() {
        if (trieTree.search(word)) {
            suggestNextBestWords = trieTree.theBestHintForGuessedWord(gameTable, wrongLetters);
            System.out.println(DIAMOND_SHAPE + ANSI_GREEN + " 4th level hint: " + ANSI_RESET);
            for (String str : suggestNextBestWords) {
                System.out.print(str + " ");
            }
            System.out.println();
            printGameTable(booleanArrayList);
        } else {
            System.out.println(ANSI_RED + "Warning! Word not found " + ANSI_RESET + POUTING_FACE);
        }
        return suggestNextBestWords;
    }

    public void printResultsToScreen() {
        trieTree.writeGetAllWords();
        trieTree.writeFrequencies();
    }

    public String printGameTable(ArrayList<Boolean> booleanArrayList) {
        design();
        int index = 0;
        StringBuilder temporary = new StringBuilder();
        System.out.println(DIAMOND + " Your remaining guesses are: " + maxGuesses);
        System.out.println(DIAMOND + " Your remaining game life: " + gameLifeCount);
        for (boolean b : booleanArrayList) {
            if (!b) {
                temporary.append(" __ ");
                System.out.print(ANSI_RED + " __ " + ANSI_RESET);
            } else {
                temporary.append(" ").append(word.charAt(index)).append(" ");
                System.out.print(ANSI_GREEN + " " + word.charAt(index) + " " + ANSI_RESET);
            }
            index++;
        }
        System.out.println();
        design();
        return String.valueOf(temporary);
    }

    public void createArraylist() {
        trieTree.add(fileName, randomFileName);
        words = trieTree.words;
        randomWords = trieTree.randomWords;
    }

    public void determineRounds() {
        numberOfRounds = randomWords.size();
    }

    public void determineGameLifeCount() {
        gameLifeCount = numberOfRounds;
    }

    public void determineWord() {
        word = randomWords.get(randomWords.size() - numberOfRounds);
        wordLength = word.length();
    }

    public void determineMaxGuesses() {
        maxGuesses = word.length();
    }

    public void createGameTable() {
        for (int i = 0; i < wordLength; i++) {
            gameTable.add('\0');
        }
    }

    public void create() {
        createArraylist();
        determineRounds();
        determineGameLifeCount();
    }

    public void GUIControl01() {
        determineWord();
        determineMaxGuesses();
        createGameTable();
        booleanArrayList = preparingTheArrayList(booleanArrayList);
        isFound = false;
    }

    public void GUIControl02() {
        trieTree.lettersUsedByTheUser(letter);
        booleanArrayList = letterControl(booleanArrayList);
        isFound = checkTheWinInWord(booleanArrayList);
        counter++;
    }

    public void GUIControl03() {
        // printGuessedResulted(maxGuesses, booleanArrayList);
        addArrayListToLinkedList(booleanArrayList);
        booleanArrayList.clear();
        trieTree.guessedLetters.clear();
        correctLetters.clear();
        wrongLetters.clear();
        gameTable.clear();
        numberOfRounds--;
        counter = 0;
    }

    public void updateLetter(char letter) {
        this.letter = letter;
    }

    public void terminalControl() {
        design();
        create();
        System.out.println(TURKISH_FLAG);
        System.out.println(WAVING_HAND + " Welcome to Hangman Game! " + WAVING_HAND);
        design();
        while (numberOfRounds > 0 && gameLifeCount > 0) {
            determineWord();
            determineMaxGuesses();
            createGameTable();
            booleanArrayList = preparingTheArrayList(booleanArrayList);
            isFound = false;
            printGameTable(booleanArrayList);
            while (maxGuesses > 0 && !isFound && gameLifeCount > 0) {
                letter = input();
                trieTree.lettersUsedByTheUser(letter);
                booleanArrayList = letterControl(booleanArrayList);
                isFound = checkTheWinInWord(booleanArrayList);
                hint01ForGuessedLetterAndWords();
                hint02ForGuessedLetterAndWords();
                hint03ForGuessedLetterAndWords();
                hint04ForGuessedLetterAndWords();
            }
            printGuessedResulted(maxGuesses, booleanArrayList);
            addArrayListToLinkedList(booleanArrayList);
            booleanArrayList.clear();
            trieTree.guessedLetters.clear();
            correctLetters.clear();
            wrongLetters.clear();
            gameTable.clear();
            numberOfRounds--;
        }
        if (!checkTheWin()) {
            System.out.println(SMILING_FACE + FACE_WITH_PARTY_HORN_AND_PARTY_HAT + ANSI_GREEN + " Congratulations, you guessed all the words correctly! " + ANSI_RESET + SMILING_FACE + FACE_WITH_PARTY_HORN_AND_PARTY_HAT);
        } else {
            System.out.println(SAD_FACE + " " + POUTING_FACE + ANSI_RED + " You Lost! " + ANSI_RESET + SAD_FACE + " " + POUTING_FACE);
        }
        printResultsToScreen();
    }

    public char input() {
        char letter = '\0';
        boolean sameLetter = true;
        while (sameLetter) {
            // Girdinin yalnızca harf içermesini sağlayan filtre tanımlama
            scanner.useDelimiter(Pattern.compile("[^a-zA-Z]+"));
            System.out.print(WHITE_RIGHT_POINTING_BACKHAND + " Please enter a letter: ");
            letter = scanner.next().charAt(0);
            letter = Character.toLowerCase(letter);
            sameLetter = sameLetterControl(letter);
        }
        return letter;
    }

    public boolean sameLetterControl(char letter) {
        boolean sameLetter = false;
        for (char c : correctLetters) {
            if (letter == c) {
                sameLetter = true;
                System.out.println(ANSI_RED + "Warning! " + POUTING_FACE + " You entered a letter you used before!" + ANSI_RESET);
                break;
            }
        }
        for (char c : wrongLetters) {
            if (letter == c) {
                sameLetter = true;
                System.out.println(ANSI_RED + "Warning! " + POUTING_FACE + " You entered a letter you used before!" + ANSI_RESET);
                break;
            }
        }

        if (letter == '\0') {
            sameLetter = true;
        }
        return sameLetter;
    }

    public void printGuessedResulted(int maxGuesses, ArrayList<Boolean> booleanArrayList) {
        int index = 0;
        if (maxGuesses > 0 && gameLifeCount > 0) {
            System.out.println(FACE_WITH_PARTY_HORN_AND_PARTY_HAT + ANSI_GREEN + " Congratulations, you guessed the word correctly! " + ANSI_RESET + SMILING_FACE);
            for (char c : word.toCharArray()) {
                System.out.print(ANSI_GREEN + c + " " + ANSI_RESET);
            }
        } else {
            System.out.println(POUTING_FACE + ANSI_RED + " Unfortunately, you couldn't guess the word! " + ANSI_RESET + SAD_FACE);
            for (char c : word.toCharArray()) {
                if (booleanArrayList.get(index)) {
                    System.out.print(ANSI_GREEN + c + " " + ANSI_RESET);
                } else {
                    System.out.print(ANSI_RED + c + " " + ANSI_RESET);
                }
                index++;
            }
        }
        System.out.println();
    }

    public ArrayList<Boolean> letterControl(ArrayList<Boolean> booleanArrayList) {
        int index = 0;
        boolean guessedCorrectly = false;
        char[] letterArray = word.toCharArray();
        for (char temporary : letterArray) {
            if (letter == temporary) {
                booleanArrayList.set(index, true);
                gameTable.set(index, letter);
                guessedCorrectly = true;
            }
            index++;
        }
        if (!guessedCorrectly) {
            gameLifeCount--;
            maxGuesses--;
            wrongLetters.add(letter);
        } else {
            correctLetters.add(letter);
        }
        return booleanArrayList;
    }

    public boolean checkTheWinInWord(ArrayList<Boolean> booleanArrayList) {
        int amountNeededToWinInWord = word.length();
        int currentAmount = 0;
        for (boolean temporary : booleanArrayList) {
            if (temporary) {
                currentAmount++;
            }
        }
        if (amountNeededToWinInWord == currentAmount) {
            amountNeededToWin++;
            return true;
        } else {
            return false;
        }
    }

    public boolean checkTheWin() {
        return gameLifeCount <= 0;
    }

    public ArrayList<Boolean> preparingTheArrayList(ArrayList<Boolean> booleanArrayList) {
        for (int index = word.length(); index > 0; index--) {
            booleanArrayList.add(false);
        }
        return booleanArrayList;
    }

    public void addArrayListToLinkedList(ArrayList<Boolean> booleanArrayList) {
        guessedLetters.add(booleanArrayList);
    }

    public void design() {
        System.out.println(ANSI_YELLOW + "------------------------------------------------------------------------------------------" + ANSI_RESET);
    }

}