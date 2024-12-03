### Felcycle (Incognitro) Cryptic Plaque Permutation Program

This is a fairly simple Java application that will perform the following in order:

1. Left and right adjustments to the Felcycle cryptic plaque code (JYPFFQVY) based on a statically-defined list of offsets.
2. Encrypt each adjusted text using either Playfair or Vignere ciphers, with the key of 'play'.
3. Attempt to find words within the encrypted values based on a dictionary.

The WordFinder algorithm is fairly inefficient, possibly taking a few minutes to run.

Also, the `output.txt` file will be placed in the current working directory of where you define it in your runner of your IDE or where you run Felcycle.main().  And the `dictionary.txt` file is assumed to be in your defined current working directory as well.

I've taken this as far as I probably will.  If anyone wishes to take this further, please do!