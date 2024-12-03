import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

public class PlayfairCipher {
    // Function to create the 5x5 matrix from the keyword
    private static char[][] createPlayfairMatrix(String keyword) {
        keyword = keyword.toUpperCase().replaceAll("[^A-Z]", "").replace("J", "I");
        Set<Character> uniqueChars = new LinkedHashSet<>();

        // Add characters from the keyword to the set
        for (char c : keyword.toCharArray()) {
            uniqueChars.add(c);
        }

        // Add the remaining letters of the alphabet to the set
        for (char c = 'A'; c <= 'Z'; c++) {
            if (c != 'J' && !uniqueChars.contains(c)) {
                uniqueChars.add(c);
            }
        }

        // Convert the set to an array and fill the matrix
        char[][] matrix = new char[5][5];
        Iterator<Character> iterator = uniqueChars.iterator();
        int i = 0;
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                matrix[row][col] = iterator.next();
            }
        }

        return matrix;
    }

    // Function to get the coordinates of a character in the 5x5 matrix
    private static int[] getCharCoordinates(char c, char[][] matrix) {
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                if (matrix[row][col] == c) {
                    return new int[]{row, col};
                }
            }
        }
        return null;
    }

    // Function to prepare the plaintext for encryption by handling duplicate letters
    private static String preparePlaintext(String plaintext) {
        plaintext = plaintext.toUpperCase().replaceAll("[^A-Z]", "").replace("J", "I");

        // Handle duplicate letters by inserting 'X'
        StringBuilder preparedText = new StringBuilder();
        for (int i = 0; i < plaintext.length(); i++) {
            if (i + 1 < plaintext.length() && plaintext.charAt(i) == plaintext.charAt(i + 1)) {
                preparedText.append(plaintext.charAt(i)).append('X');
            } else {
                preparedText.append(plaintext.charAt(i));
            }
        }

        // If the length of the text is odd, append 'X' at the end
        if (preparedText.length() % 2 != 0) {
            preparedText.append('X');
        }

        return preparedText.toString();
    }

    // Function to encrypt the plaintext using the Playfair cipher
    public static String encrypt(String plaintext, String keyword) {
        char[][] matrix = createPlayfairMatrix(keyword);
        String preparedText = preparePlaintext(plaintext);
        StringBuilder ciphertext = new StringBuilder();

        for (int i = 0; i < preparedText.length(); i += 2) {
            char firstChar = preparedText.charAt(i);
            char secondChar = preparedText.charAt(i + 1);

            int[] firstCoordinates = getCharCoordinates(firstChar, matrix);
            int[] secondCoordinates = getCharCoordinates(secondChar, matrix);

            // Same row: Replace with the letter to the right (wrap around if necessary)
            if (firstCoordinates[0] == secondCoordinates[0]) {
                ciphertext.append(matrix[firstCoordinates[0]][(firstCoordinates[1] + 1) % 5]);
                ciphertext.append(matrix[secondCoordinates[0]][(secondCoordinates[1] + 1) % 5]);
            }
            // Same column: Replace with the letter below (wrap around if necessary)
            else if (firstCoordinates[1] == secondCoordinates[1]) {
                ciphertext.append(matrix[(firstCoordinates[0] + 1) % 5][firstCoordinates[1]]);
                ciphertext.append(matrix[(secondCoordinates[0] + 1) % 5][secondCoordinates[1]]);
            }
            // Rectangle rule: Swap columns
            else {
                ciphertext.append(matrix[firstCoordinates[0]][secondCoordinates[1]]);
                ciphertext.append(matrix[secondCoordinates[0]][firstCoordinates[1]]);
            }
        }

        return ciphertext.toString();
    }
}
