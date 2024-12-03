public class VignereCipher {
    public static String encrypt(String plaintext, String key) {
        StringBuilder ciphertext = new StringBuilder();
        int keyIndex = 0;  // To track the position of the key

        // Iterate over each character in the plaintext
        for (int i = 0; i < plaintext.length(); i++) {
            char currentChar = plaintext.charAt(i);

            // Check if the current character is a letter
            if (Character.isLetter(currentChar)) {
                // Determine the shift based on the key
                char keyChar = key.charAt(keyIndex % key.length());
                int shift = Character.toUpperCase(keyChar) - 'A';

                // Encrypt uppercase characters
                if (Character.isUpperCase(currentChar)) {
                    char encryptedChar = (char) ((currentChar - 'A' + shift) % 26 + 'A');
                    ciphertext.append(encryptedChar);
                }
                // Encrypt lowercase characters
                else if (Character.isLowerCase(currentChar)) {
                    char encryptedChar = (char) ((currentChar - 'a' + shift) % 26 + 'a');
                    ciphertext.append(encryptedChar);
                }

                // Move to the next letter in the key
                keyIndex++;
            } else {
                // If it's not a letter, just add it as is (e.g., space, punctuation)
                ciphertext.append(currentChar);
            }
        }
        return ciphertext.toString();
    }
}
