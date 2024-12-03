import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Felcycle {
    public static void main(String[] args) throws IOException {
        char[] crypt = {'J','Y','P','F','F','Q','V','Y'};
        int[] adjust = {0,0,0,0,1,1,2,3};
//        int[] adjust = {1,9,5,4,4,0,3,9};

        String key = "play";

        List<List<Integer>> permutations = getPermutations(adjust);
        List<Tuple<String, String>> forwardAdjustments = new ArrayList<>();
        List<Tuple<String, String>> backwardAdjustments = new ArrayList<>();
        List<Tuple<String, String>> allAdjustments = new ArrayList<>();

        for (List<Integer> permutation : permutations) {
            String adjustingForward = "";
            String adjustingBackward = "";
            for (int i = 0; i < permutation.size(); i++) {
                int adjustingVal = permutation.get(i);
                int forwardVal = ((int) crypt[i]) + adjustingVal;
                int backwardVal = ((int) crypt[i]) - adjustingVal;

                if (forwardVal > 90) {
                    forwardVal = 64 + (forwardVal - 90);
                }

                if (backwardVal < 65) {
                    backwardVal = 91 - (65 - backwardVal);
                }

                adjustingForward += (char) forwardVal;
                adjustingBackward += (char) backwardVal;
            }

            // SWAP THESE TO TEST EACH CIPHER
//            forwardAdjustments.add(new Tuple<>(adjustingForward, PlayfairCipher.encrypt(adjustingForward, key)));
//            backwardAdjustments.add(new Tuple<>(adjustingBackward, PlayfairCipher.encrypt(adjustingBackward, key)));
            forwardAdjustments.add(new Tuple<>(adjustingForward, VigenereCipher.encrypt(adjustingForward, key)));
            backwardAdjustments.add(new Tuple<>(adjustingBackward, VigenereCipher.encrypt(adjustingBackward, key)));
        }
        allAdjustments.addAll(forwardAdjustments);
        allAdjustments.addAll(backwardAdjustments);

        WordFinder.process(allAdjustments);

    }

    private static List<List<Integer>> getPermutations(int[] numbers) {
        // Sort the array to handle duplicate elements
        Arrays.sort(numbers);

        // List to hold the current permutation
        List<Integer> currentPermutation = new ArrayList<>();

        // List to hold all permutations
        List<List<Integer>> allPermutations = new ArrayList<>();

        // Boolean array to mark the visited elements
        boolean[] visited = new boolean[numbers.length];

        // Start generating permutations
        backtrack(numbers, visited, currentPermutation, allPermutations);

        return allPermutations;
    }

    private static void backtrack(int[] nums, boolean[] visited, List<Integer> currentPermutation, List<List<Integer>> allPermutations) {
        // If the current permutation is of the same length as the original list, add it to the result
        if (currentPermutation.size() == nums.length) {
            allPermutations.add(new ArrayList<>(currentPermutation));
            return;
        }

        // Iterate through the numbers
        for (int i = 0; i < nums.length; i++) {
            // Skip if the number is already used in the current permutation or if it is a duplicate and not the first occurrence in the current recursion level
            if (visited[i] || (i > 0 && nums[i] == nums[i - 1] && !visited[i - 1])) {
                continue;
            }

            // Mark the number as visited and add it to the current permutation
            visited[i] = true;
            currentPermutation.add(nums[i]);

            // Recurse to the next element
            backtrack(nums, visited, currentPermutation, allPermutations);

            // Backtrack: Unmark the number and remove it from the current permutation
            visited[i] = false;
            currentPermutation.remove(currentPermutation.size() - 1);
        }
    }

}
