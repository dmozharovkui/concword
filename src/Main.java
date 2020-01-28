import sun.reflect.generics.tree.Tree;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static void main(String[] args) throws IOException {
        Set<String> words = getWordsFromFile("C:\\Users\\Димон\\Desktop\\words.txt");

        //for testing
        /*Set<String> testWords = new TreeSet<String>((first,second) -> {
            if (second.length() > first.length())
                return 1;
            else if (second.length() < first.length())
                return -1;
            else
                return second.compareTo(first);
        });
        testWords.addAll(Arrays.asList("catcats", "cat", "cats", "catsdogcats", "dog", "dogcatsdog", "hippopotamuses", "rat", "ratcatdogcatcats"));*/

        List<String> concatWords = findAllConcatenatedWords(words);

        try{
            System.out.println("First largest concatenated word: " + concatWords.get(0)); // get first element
            System.out.println("Second largest concatenated word: " + concatWords.get(1)); // get second element
            System.out.println("Total concatenated words count: " + concatWords.size());
        } catch(IndexOutOfBoundsException error) {
            if (concatWords.size() < 1){
                System.out.println("First concatenated word doesnt exit!");
            }

            if (concatWords.size() < 2){
                System.out.println("Second concatenated word doesnt exit!");
            }
        }
    }

    public static List<String> findAllConcatenatedWords(Set<String> words) {

        List<String> sortedWords = new ArrayList<String>();

        // Not using recursion because for large files it may cause stack overflow!
        for (String originalWord : words) {
            String lookUpWord = String.valueOf(originalWord); // copy so original string is not modified
            while (!lookUpWord.isEmpty()) {
                String finalLookUpWord = lookUpWord;
                Optional<String> word = words.stream().filter(wrd -> finalLookUpWord.contains((String)wrd))
                                                      .filter(wrd -> !((String)wrd).equals(originalWord))
                                                      .max(Comparator.comparingInt(String::length));

                if (word.isPresent()) {
                    lookUpWord = lookUpWord.replace(word.get(), "");
                }else{
                    break;
                }
            }

            if (lookUpWord.isEmpty()) {
                // Look up word consists only from contatenated words
                sortedWords.add(originalWord);
            }
            // else - the word is not concatenated
        }

        return sortedWords;
    }

    public static Set<String> getWordsFromFile(String filename) throws IOException {
        File file = new File(filename);
        Scanner scanner = new Scanner(file);



        // Set is used to remove duplicated words, TreeSet - to sort in ASCENDING order using a lambda.
        Set<String> words = new TreeSet<String>((first,second)->{
            if (second.length() > first.length()) // Sort by ascending order if second is larger than the first
                return 1;
            else if (second.length() < first.length())
                return -1;
            else
                return second.compareTo(first); // If lengths are equal - make lexical comparison and remove duplicates
        });


        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (!line.isEmpty()) {

                words.add(line);
            }
        }



        System.out.println("Number of lines: " + words.size());

        scanner.close();

        return words;
    }
}


