# ScrabblerService-CodeLogic-Java


#### Code Logic for Scrabbler Service, with rank/score ordering of the output words.

Takes a string of letters as input and returns a rank/score ordered list of valid words that can be generated from the letters. 

Use a valid dictionary for the purpose.

The score is calculated as : 

    Points | Letters
    -------+-----------------------------
       1   | A, E, I, L, N, O, R, S, T, U
       2   | D, G
       3   | B, C, M, P
       4   | F, H, V, W, Y
       5   | K
       8   | J, X
      10   | Q, Z
