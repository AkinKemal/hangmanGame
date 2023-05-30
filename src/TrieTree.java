import java.io.IOException;
import java.util.*;

public class TrieTree {
    public TrieNode root;
    public ArrayList<String> words;
    public ArrayList<String> randomWords;
    public ArrayList<Character> guessedLetters;
    int[] indexesOfKnownLetters;

    public TrieTree() {
        // kök düğüm boş karakter olarak ata
        this.root = new TrieNode('\0');
        guessedLetters = new ArrayList<>();
    }

    /*
    '\0'
    Trie veri yapısında kök düğüm oluşturulurken, kök düğümünün değerinin null olması gerekiyor.
    Fakat char değeri olarak null atanamaz.
    Bu nedenle '\0' değeri kullanıyoruz.
    '\0' karakteri, null karakteri olarak bilinir ve ASCII karakter setinde değeri sıfırdır.
    Bu karakter, null kelimesinden farklı olarak karakterler arasında yer alır ve bu nedenle karakter dizilerinde kullanılabilir.
     */

    public void add(String fileName, String randomFileName) {

        // okuma
        ReadFile readFile = new ReadFile(fileName, randomFileName);
        try {
            words = readFile.readFile();
            randomWords = readFile.randomReadFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            System.out.println("TrieTree.java  --->  add Method");
        }

        // add
        for (String itemWord : words) {
            insert(itemWord);
        }
    }

    public void insert(String word) {
        TrieNode current = root;
        for (char c : word.toCharArray()) {

            /*
            toCharArray
            toCharArray() metodu, bir String'i char dizisine dönüştürür.
            Bu metod, karakterlerin her birine ayrı ayrı erişmenizi sağlar.
            Bu nedenle for döngüsü, her karakteri tek tek ele almak için kullanılır.
             */

            int index = c - 'a'; // karakterin ingilizce alfabe sırasındaki index'sini hesapla

            if (current.getChildren()[index] == null) {

                /*
                Eğer current düğümünün çocuklarından, bu indeksteki alt düğüm henüz oluşturulmamışsa,
                o zaman yeni bir alt düğüm oluşturuyoruz ve karakter değerini atıyoruz.
                 */

                current.getChildren()[index] = new TrieNode(c);
                current.getChildren()[index].setParent(current);
                // parent referansı ayarlandı
                current.getChildren()[index].setParent(current);
            }

            // current'ı current'ın bu indeksteki alt düğümüne atama, böylece sonraki karaktere geçebilme
            current = current.getChildren()[index];
        }

        // kelimenin en son harfini işaretle
        current.setEndOfWord(true);

    }

    public boolean search(String word) {
        TrieNode currentSearch = root;
        for (char c : word.toCharArray()) {
            int index = c - 'a';
            if (currentSearch.getChildren()[index] == null) {
                return false;
            }
            currentSearch = currentSearch.getChildren()[index];
        }
        return currentSearch.isEndOfWord();
    }

    public char hintForGuessedLetter(ArrayList<Character> gameTable, ArrayList<Character> correctLetters, ArrayList<Character> wrongLetters) {
        char result;
        TrieNode node = root;
        turnGameBoardIntoArray(gameTable);
        result = setLetters(node, gameTable, indexesOfKnownLetters, correctLetters, wrongLetters);
        return result;
    }

    public ArrayList<String> theNormalHintForGuessedWord(int wordLength, ArrayList<Character> correctLetters) {
        return possibleWords(wordLength, correctLetters);
    }

    public ArrayList<String> hintForGuessedWord(ArrayList<Character> gameTable) {
        ArrayList<String> result;
        TrieNode node = root;
        turnGameBoardIntoArray(gameTable);
        result = findLettersOfPossibleWords(node, gameTable, indexesOfKnownLetters);
        return result;
    }

    public ArrayList<String> theBestHintForGuessedWord(ArrayList<Character> gameTable, ArrayList<Character> wrongLetters) {
        ArrayList<String> result;
        TrieNode node = root;
        turnGameBoardIntoArray(gameTable);
        result = improveTheResult(node, gameTable, indexesOfKnownLetters, wrongLetters);
        return result;
    }

    public char setLetters(TrieNode node, ArrayList<Character> gameTable, int[] indexesOfKnownLetters, ArrayList<Character> correctLetters, ArrayList<Character> wrongLetters) {
        ArrayList<String> result = findLettersOfPossibleWords(node, gameTable, indexesOfKnownLetters);
        char characterResult = '\0';

        // Tüm kelimelerin harflerini birleştir
        StringBuilder stringBuilder = new StringBuilder();
        for (String word : result) {
            stringBuilder.append(word);
        }

        // Karakterleri ayır ve sayımları yap
        HashMap<Character, Integer> charFrequency = new HashMap<>();
        for (char c : stringBuilder.toString().toCharArray()) {
            if (charFrequency.containsKey(c)) {
                charFrequency.put(c, charFrequency.get(c) + 1);
            } else {
                charFrequency.put(c, 1);
            }
        }
        for (char c : correctLetters) {
            if (c != '\n') {
                charFrequency.remove(c);
            }
        }
        for (char c : wrongLetters) {
            if (c != '\n') {
                charFrequency.remove(c);
            }
        }
        int maxFrequency = Integer.MIN_VALUE;
        for (Map.Entry<Character, Integer> entry : charFrequency.entrySet()) {
            int currentFrequency = entry.getValue();
            if (currentFrequency > maxFrequency) {
                maxFrequency = currentFrequency;
                characterResult = entry.getKey();
            }
        }
        return characterResult;
    }

    private ArrayList<String> improveTheResult(TrieNode node, ArrayList<Character> gameTable, int[] indexesOfKnownLetters, ArrayList<Character> wrongLetters) {
        ArrayList<String> result = findLettersOfPossibleWords(node, gameTable, indexesOfKnownLetters);
        ArrayList<String> bestResult = new ArrayList<>();

        boolean control = true;
        for (String s : result) {
            for (char c : wrongLetters) {
                if (s.contains(Character.toString(c))) {
                    control = false;
                    break;
                }
            }
            if (control) {
                bestResult.add(s);
            }
            control = true;
        }
        return bestResult;
    }

    private void turnGameBoardIntoArray(ArrayList<Character> gameTable) {
        indexesOfKnownLetters = new int[gameTable.size()];
        int temporary = 0;
        for (char c : gameTable) {
            if (c == '\0') {
                indexesOfKnownLetters[temporary] = -1;
            } else {
                indexesOfKnownLetters[temporary] = 0;
            }
            temporary++;
        }
    }

    private ArrayList<String> findLettersOfPossibleWords(TrieNode node, ArrayList<Character> gameTable, int[] indexesOfKnownLetters) {
        int currentIndex = 0;
        ArrayList<String> possibleWords = new ArrayList<>();
        findLettersOfPossibleWordsHelper(possibleWords, node, gameTable, indexesOfKnownLetters, currentIndex);
        return possibleWords;
    }

    private void findLettersOfPossibleWordsHelper(ArrayList<String> possibleWords, TrieNode node, ArrayList<Character> gameTable, int[] indexesOfKnownLetters, int currentIndex) {

        // Bilinen harfler bittiğinde, kelimeyi ekle
        if (currentIndex == indexesOfKnownLetters.length) {
            if (node.isEndOfWord()) {

                // mevcut düğüm kelimenin sonu ise, kelimeyi possibleWords listesine ekler
                possibleWords.add(getWord(node));
            }
            return;
        }

        // Bilinen harfi kontrol et
        char currentKnowLetter = gameTable.get(currentIndex);
        int currentKnownIndex = indexesOfKnownLetters[currentIndex];
        if (currentKnownIndex >= 0 && currentKnownIndex < node.getChildren().length) {
            int index = currentKnowLetter - 'a';
            TrieNode child = node.getChildren()[index];
            if (child != null) {
                findLettersOfPossibleWordsHelper(possibleWords, child, gameTable, indexesOfKnownLetters, currentIndex + 1);
            }
        } else if (currentKnowLetter == '\0') {

            // Eğer '\0' ise, tüm harfleri dene
            for (int i = 0; i < node.getChildren().length; i++) {
                TrieNode child = node.getChildren()[i];
                if (child != null) {
                    findLettersOfPossibleWordsHelper(possibleWords, child, gameTable, indexesOfKnownLetters, currentIndex + 1);
                }
            }
        } else {

            // Eğer harf biliniyorsa, sadece o harfi dene
            int index = currentKnowLetter - 'a';
            TrieNode child = node.getChildren()[index];
            if (child != null) {
                findLettersOfPossibleWordsHelper(possibleWords, child, gameTable, indexesOfKnownLetters, currentIndex + 1);
            }
        }
    }

    private String getWord(TrieNode node) {
        String result;
        StringBuilder stringBuilder = new StringBuilder();
        while (node.getParent() != null) {
            stringBuilder.append(node.getValue());
            node = node.getParent();
        }

        // reverse() metodu, oluşturulan karakter dizisindeki karakterlerin sıralamasını tersine çevirir
        // toString() ise, StringBuilder nesnesinin içindeki karakter dizisini String olarak dönüştürür
        result = stringBuilder.reverse().toString();
        return result;
    }

    public ArrayList<String> possibleWords(int wordLength, ArrayList<Character> correctLetters) {
        ArrayList<String> possibleWords = new ArrayList<>();
        ArrayList<String> xLetterWords = xLetterWords(wordLength);
        for (String temporaryWord : xLetterWords) {
            int amountToContain = 0;
            for (Character character : correctLetters) {
                String temporaryLetter = String.valueOf(character);
                if (temporaryWord.contains(temporaryLetter)) {
                    amountToContain++;
                }
            }
            if (amountToContain == correctLetters.size()) {
                possibleWords.add(temporaryWord);
            }
        }
        return possibleWords;
    }

    private ArrayList<String> xLetterWords(int wordLength) {
        ArrayList<String> xLetterWords = new ArrayList<>();
        ArrayList<String> getAllWords = getAllWords();
        for (String temporaryWord : getAllWords) {
            if (temporaryWord.length() == wordLength) {
                xLetterWords.add(temporaryWord);
            }
        }
        return xLetterWords;
    }

    public void writeFrequencies() {
        int[] lettersCounter = calculateFrequencies();
        for (int i = 0; i < lettersCounter.length; i++) {
            System.out.print((char) (i + 'a') + ": " + lettersCounter[i] + ", ");
        }
        System.out.println();
    }

    private int[] calculateFrequencies() {
        int[] lettersCounter = new int[26];
        for (String word : words) {
            TrieNode current = root;
            char[] wordArray = word.toCharArray();
            for (char c : wordArray) {
                int index = c - 'a';
                if (current.getChildren()[index] == null) {
                    break;
                }
                current = current.getChildren()[index];
            }
            getAllCounterLetters(current, lettersCounter);
        }
        return lettersCounter;
    }

    private void getAllCounterLetters(TrieNode node, int[] lettersCounter) {
        if (node.isEndOfWord()) {
            String value = String.valueOf(node.getValue());
            for (int i = 0; i < value.length(); i++) {
                char c = value.charAt(i);
                lettersCounter[c - 'a']++;
            }
        }
        for (TrieNode child : node.getChildren()) {
            if (child != null) {
                getAllCounterLetters(child, lettersCounter);
            }
        }
    }

    public void lettersUsedByTheUser(char letter) {
        guessedLetters.add(letter);
        System.out.println("Letters Used By The User: " + letter);
    }

    public void writeGetAllWords() {
        ArrayList<String> allWords = getAllWords();
        for (String word : allWords) {
            System.out.print(word + ", ");
        }
        System.out.println();
    }

    private ArrayList<String> getAllWords() {
        TrieNode node = root;
        ArrayList<String> getAllWords = new ArrayList<>();
        StringBuilder currentWord = new StringBuilder();
        getAllWords(node, currentWord, getAllWords);
        return getAllWords;
    }

    /*
    StringBuilder
    StringBuilder sınıfı, String sınıfının değiştirilemez olmasına karşın, değiştirilebilir bir karakter dizisi sağlar.
    Bu nedenle, bir StringBuilder nesnesi oluşturularak her karakterin Huffman kodu bu nesne içine eklenir.
    StringBuilder'ın append() yöntemi, nesne içindeki var olan karakter dizisine verilen yeni karakterleri ekleyerek StringBuilder'ı günceller.
    Bu, performansı artırır çünkü String'lerin her güncellemesi bir kopya oluşturur ve bu gereksiz bellek tüketir.
    Bu nedenle, değiştirilebilir bir karakter dizisi gerektiğinde, StringBuilder daha iyi bir seçimdir.
    */

    private void getAllWords(TrieNode node, StringBuilder currentWord, ArrayList<String> getAllWords) {
        if (node == null) {
            return;
        }
        if (node.isEndOfWord()) {

            // mevcut düğüm node kelimenin sonu mu kontrol et
            // eğer öyleyse, kelime listesine currentWord dizesinin bir kopyasını ekle
            getAllWords.add(currentWord.toString());
        }
        if (node.getChildren() == null) {
            return;
        }
        for (TrieNode child : node.getChildren()) {

            // düğümün tüm çocuklarına for döngüsü aracılığıyla eriş
            // currentWord dizesine her bir çocuğun value özelliği ekle
            // getAllWords() yöntemi, her bir çocuk düğümüne özyinelemeli olarak çağır

            if (child == null) {
                continue;
            }
            currentWord.append(child.getValue());
            getAllWords(child, currentWord, getAllWords);

            // currentWord dizesinden son karakter çıkarılır (deleteCharAt() kullanılarak)
            // böylece önceki düğüme geri dönülür ve diğer çocukları denemek için tekrar kullanılabilir hale gelir
            currentWord.deleteCharAt(currentWord.length() - 1);
        }
    }

    /*
    deleteCharAt
    deleteCharAt metodu, bir StringBuilder nesnesinin belirtilen pozisyonundaki karakteri siler.
    Sonraki karakterleri bir pozisyon geriye kaydırır.
    Bu yöntem, StringBuilder nesnesi içindeki karakter dizisini yeniden düzenlemek için kullanılır.
     */

}