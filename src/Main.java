import java.io.*;
import java.util.List;


public class Main {
    public static void main(String[] args) throws IOException {

        String term = "brown";
        List<File> filesFromDataBase =
                Counter.printCountOfAllAndNeededWordsFromAllFilesAndReturnFileList(
                        new File("/Users/oleg/IdeaProjects/TF-IDF.simlpe_search_engine/DataBase"), term);

        System.out.println(ListOfDocumentsWithNeededWord.
                listOfDocumentsWithNeededWordSortedByTFIDF(filesFromDataBase, term));
    }
}
