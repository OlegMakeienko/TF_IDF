import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class ListOfDocumentsWithNeededWord {

    private static List<String> listOfDocumentsWithNeededWord(List<File> files, String neededWord) throws IOException {
        List <String> documents = new ArrayList<>();

        for (int i = 0; i < files.size(); i++) {
            List<String> lines = Files.readAllLines(Paths.get(String.valueOf(files.get(i))));
            for (String s : lines) {
                if (s.contains(neededWord)) {
                    documents.add(files.get(i).getName().
                            substring(0, files.get(i).getName().lastIndexOf(".")));
                    break;
                }
            }
        }
        return documents;
    }

    public static List<String> listOfDocumentsWithNeededWordSortedByTFIDF(List<File> files,
                                                                             String neededWord) throws IOException {
        Map<String, Float> mapOfDocsWithWord = new TreeMap<>();
        List<String> documentsWithNeededWord =
                ListOfDocumentsWithNeededWord.listOfDocumentsWithNeededWord(files, neededWord);
        for (int i = 0; i < documentsWithNeededWord.size(); i++) {
            for (File file : files) {
                if(file.getName().contains(documentsWithNeededWord.get(i))) {
                    float tf = Counter.counter_tf(file, neededWord);
                    float idf = Counter.counter_idf(files, neededWord);
                    mapOfDocsWithWord.put(documentsWithNeededWord.get(i), tf * idf);
                }
            }
        }

        mapOfDocsWithWord = mapOfDocsWithWord.entrySet().stream()
                .sorted(Comparator.comparingDouble(e -> -e.getValue()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (a, b) -> { throw new AssertionError(); },
                        LinkedHashMap::new
                ));
        System.out.println(mapOfDocsWithWord);
        List<String> listOfDocsWithWordSortedByTFIDF = new ArrayList<>(mapOfDocsWithWord.keySet());
        return listOfDocsWithWordSortedByTFIDF;
    }
}
