import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Counter {

    public static List<File> printCountOfAllAndNeededWordsFromAllFilesAndReturnFileList(File rootFile, String term) throws IOException {
        List<File> filesInDirectory = new ArrayList<>();
        int sumOfCount = 0;
        int countOfTerm = 0;
        if (rootFile.isDirectory()) {
            System.out.println("searching: " + rootFile.getAbsolutePath());
            File[] directoryFiles = rootFile.listFiles();
            if (directoryFiles != null) {
                for (File file : directoryFiles) {
                    if (file.isDirectory()) {
                        printCountOfAllAndNeededWordsFromAllFilesAndReturnFileList(file, term);
                    } else {
                        filesInDirectory.add(file);
                        if (file.getName().toLowerCase().endsWith(".txt")) {
                            countOfTerm += countOfNeededWordsInFile(file, term);
                            sumOfCount += countOfAllWordsInFile(file);
                        }
                    }
                }
            }
        }
        System.out.println(sumOfCount);
        System.out.println(countOfTerm);

        return filesInDirectory;
    }

    private static int countOfNeededWordsInFile(File file, String neededWord) throws IOException {
        int count = 0;
        try (FileReader reader = new FileReader(file);
             BufferedReader fileReader = new BufferedReader(reader)) {
            while (fileReader.ready()) {
                String[] s = fileReader.readLine().split("[., !?;:-]");
                for (String elem : s) {
                    if (neededWord.equalsIgnoreCase(elem))
                        count ++;
                }
            }
        }
        return count;
    }

    private static int countOfAllWordsInFile(File file) throws IOException {
        int count = 0;
        try (FileReader reader = new FileReader(file);
             BufferedReader fileReader = new BufferedReader(reader)) {
            while (fileReader.ready()) {
                String[] s = fileReader.readLine().split("[., !?;:-]");
                for (String elem : s) {
                    if (elem != null)
                        count++;
                }
            }
        }
        return count;
    }

    private static int countOfNeededWordsInAllFiles(List<File> files, String neededWord) throws IOException {
        int count = 0;
        for (int i = 0; i < files.size(); i++) {
            count += countOfNeededWordsInFile(files.get(i), neededWord);
        }
        return count;
    }

    private static int countOfAllWordsInAllFiles(List<File> files) throws IOException {
        int count = 0;
        for (File file : files) {
            count += countOfAllWordsInFile(file);
        }
        return count;
    }

    private static int counterOfDocumentsWithNeededWord(List<File> files, String neededWord) throws IOException {
        int counter = 0;
        for (File file : files) {
            List<String> lines = Files.readAllLines(Paths.get(String.valueOf(file)));
            for (String s : lines) {
                if (s.contains(neededWord)) {
                    counter++;
                    break;
                }
            }
        }
        return counter;
    }

    private static int counterOfAllDocuments(List<File> files) {
        return files.size();
    }

    public static float counter_tf(File file, String neededWord) throws IOException {
        float result = 0;
        if (countOfAllWordsInFile(file) != 0) {
            result = (float) countOfNeededWordsInFile(file, neededWord) / (float) countOfAllWordsInFile(file);
        }
        return result;
    }

    public static float counter_idf(List<File> files, String neededWord) throws IOException {
        float result = 0;
        if (counterOfDocumentsWithNeededWord(files, neededWord) != 0) {
            result = (float) counterOfAllDocuments(files) / (float) counterOfDocumentsWithNeededWord(files, neededWord);
            result = (float) Math.log10(result);
        }
        return result;
    }
}
