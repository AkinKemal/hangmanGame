import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ReadFile {

    private String fileName;

    private String randomFileName;

    public ArrayList<String> words = new ArrayList<>();
    public ArrayList<String> randomWords = new ArrayList<>();

    public ReadFile(String fileName, String randomFileName) {
        this.setFileName(fileName);
        this.setRandomFileName(randomFileName);
    }

    public ArrayList<String> readFile() throws IOException {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(getFileName()))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                words.add(line.trim().toLowerCase());
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            System.out.println("Bir hata oluştu: " + e.getMessage());
        } finally {
            System.out.println("ReadFile.java  --->  readFile Method");
        }
        return words;
    }

    public ArrayList<String> randomReadFile() throws IOException {

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(getRandomFileName()))) {
            String line = bufferedReader.readLine();
            while (line != null) {
                randomWords.add(line.trim().toLowerCase());
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            System.out.println("Bir hata oluştu: " + e.getMessage());
        } finally {
            System.out.println("ReadFile.java  --->  randomReadFile Method");
        }
        return randomWords;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getRandomFileName() {
        return randomFileName;
    }

    public void setRandomFileName(String randomFileName) {
        this.randomFileName = randomFileName;
    }
}