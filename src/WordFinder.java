import java.io.*;
import java.util.*;

public class WordFinder {
    public static void process(List<Tuple<String, String>> allAdjustments) throws IOException {
        // Specify the path to the dictionary file (replace with your actual dictionary path)
        String dictionaryFilePath = "dictionary.txt"; // Example path, adjust accordingly
        String outputFilePath = "output.txt";
        FileWriter writer = new FileWriter(new File(outputFilePath));

        // Load the dictionary
        Set<String> dictionary = loadDictionary(dictionaryFilePath);

        int count = 0;
        for (Tuple<String, String> adjustment : allAdjustments) {
            count++;

            System.out.println("Processing Adjustment " + count + " / " + allAdjustments.size() + ": (" + adjustment.x + ", " + adjustment.y + ")");
            // Set to store the found words
            Set<String> foundWords = new HashSet<>();
            findWords(adjustment.y, dictionary, foundWords);

            if (!foundWords.isEmpty()) {
                writer.write("For (" + adjustment.x + ", " + adjustment.y + "): Words found:\n");
                for (String word : foundWords) {
                    writer.write(word + "\n");
                }
                writer.write("\n");
            }
        }

        writer.close();
    }

    // Function to load the dictionary from a file
    private static Set<String> loadDictionary(String dictionaryFilePath) {
        Set<String> wordDict = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(dictionaryFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().contains("/")) {
                    line = line.trim().substring(0, line.indexOf('/')).toUpperCase();
                    if (line.length() >= 4) {
                        wordDict.add(line);
                    }
                } else {
                    line = line.trim().toUpperCase();
                    if (line.length() >= 4) {
                        wordDict.add(line);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading dictionary file: " + e.getMessage());
        }
        return wordDict;
    }

    // Function to find words by generating permutations more efficiently
    public static void findWords(String input, Set<String> dictionary, Set<String> foundWords) {
        // Convert the input string to a char array for easy manipulation
        char[] chars = input.toCharArray();
        // Create a map to track letter usage (to avoid using the same letter multiple times)
        Map<Character, Integer> charCountMap = new HashMap<>();
        for (char c : chars) {
            charCountMap.put(c, charCountMap.getOrDefault(c, 0) + 1);
        }

        // Perform backtracking to generate valid words from the input string
        backtrack(chars, charCountMap, "", dictionary, foundWords);
    }

    // Helper function for backtracking to generate valid words
    private static void backtrack(char[] chars, Map<Character, Integer> charCountMap, String currentWord,
                                  Set<String> dictionary, Set<String> foundWords) {
        // If the current word is a valid dictionary word, add it to the result set
        if (!currentWord.isEmpty() && dictionary.contains(currentWord)) {
            foundWords.add(currentWord);
        }

        // Iterate over all characters and try to build further words
        for (char c : charCountMap.keySet()) {
            int count = charCountMap.get(c);
            if (count > 0) {
                // Choose the character 'c' and decrease its count in the map
                charCountMap.put(c, count - 1);
                // Recur to build the word further
                backtrack(chars, charCountMap, currentWord + c, dictionary, foundWords);
                // Backtrack by restoring the character count
                charCountMap.put(c, count);
            }
        }
    }
}
