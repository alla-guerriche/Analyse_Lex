package Analyse_Lex;

import java.io.*;

import java.util.*; 

public class Token01 {
    public static void main(String[] args) {
        try {
            char ch;
           
            String program = "";
            String oper = "=*+-!/<:>?%";

            

            // Define the list of keywords
            String[] keywords = {
                "auto", "break", "case", "char", "const", "continue", "default",
                "do", "double", "else", "enum", "extern", "float", "for", "goto",
                "if", "int", "long", "register", "return", "short", "signed",
                "sizeof", "static", "struct", "switch", "typedef", "union",
                "unsigned", "void", "volatile", "while", "printf", "{", "}","String","System.out.println","Class","scanf"
            };

            FileReader fileReader = new FileReader("Analyse_Lex\\alla.txt");
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            ArrayList<String> vecMotCles = new ArrayList<>();
            ArrayList<String> vecIdentificateur = new ArrayList<>();
            ArrayList<String> vecOperateur = new ArrayList<>();
            ArrayList<Character> vecSymbole = new ArrayList<>();
            ArrayList<String> vecChaineLaterale = new ArrayList<>();

            if (!bufferedReader.ready()) {
                System.out.println("Error while opening the file.");
                System.exit(0);
                
            }

            while ((ch = (char) bufferedReader.read()) != (char) -1) {
                program += ch;
            }

            StringTokenizer tokenizer = new StringTokenizer(program, " \"\t\n()[]{}=><?:+-!><;('')*/%,", true);
          
            boolean isInsideString = false;
            StringBuilder currentString = new StringBuilder();

            while (tokenizer.hasMoreTokens()) {
                String token = tokenizer.nextToken().trim();

                if (token.isEmpty()) {
                    continue;
                }
                //for detect the message insiede the ""
                if (token.equals("\"")) {
                    // Check for the start or end of a string
                    if (isInsideString) {
                        vecChaineLaterale.add(currentString.toString());
                        currentString.setLength(0); // Reset the current string
                    }
                    isInsideString = !isInsideString;
                } else if (isInsideString) {
                    currentString.append(token);
                } else if (Arrays.asList(keywords).contains(token)) {
                    vecMotCles.add(token);
                } else if (oper.contains(token)) {
                    String nextToken = tokenizer.nextToken().trim();
                     if ((token.equals("<") && nextToken.equals("=")) || (token.equals(">") && nextToken.equals("=")) || (token.equals("!") && nextToken.equals("="))||(token.equals("=") && nextToken.equals("="))) {
                    vecOperateur.add(token + nextToken);
                } else {
                    vecOperateur.add(token);
                }}
                        // vecOperateur.add(token); 
                 else if (",;()[]".contains(token)) {
                    
                        vecSymbole.add(token.charAt(0));
                   
                } else if (isNumeric(token)) {
                    vecChaineLaterale.add(token);
                } 
                else{
                    vecIdentificateur.add(token);
                }
            }

            bufferedReader.close();

          // Remove duplicates
          //of motcles
            Set<String> setMotCles = new HashSet<>(vecMotCles);
            vecMotCles.clear();
            vecMotCles.addAll(setMotCles);
         //of Operatuer
             Set<String> setOperateur=new HashSet<>(vecOperateur);
            vecOperateur.clear();
            vecOperateur.addAll(setOperateur);
         //of Symbole 
            Set<Character> setSymbole=new HashSet<>(vecSymbole);
            vecSymbole.clear();
            vecSymbole.addAll(setSymbole);
         //of ChaineLaterale    
            Set<String> setChaineLaterale=new HashSet<>(vecChaineLaterale);
            vecChaineLaterale.clear();
            vecChaineLaterale.addAll(setChaineLaterale);
         //of Identificatuer  
            Set<String> setIdentificateur = new HashSet<>(vecIdentificateur);
            vecIdentificateur.clear();
            vecIdentificateur.addAll(setIdentificateur);

            // Print arrays
            System.out.println("\n------------------------------------------------------------------------------");

            System.out.println("Mots Cles:");
            for (String motCle : vecMotCles) {
                System.out.print("\t" + motCle + " ");
            }
            System.out.println("\n------------------------------------------------------------------------------");
            System.out.println("\nIdentificateur:");
            for (String identificateur : vecIdentificateur) {
                System.out.print("\t" + identificateur + " ");
            }
            System.out.println("\n------------------------------------------------------------------------------");

            System.out.println("\nOperators:");
            for (String operateur : vecOperateur) {
                System.out.print("\t" + operateur + " ");
            }
            System.out.println("\n------------------------------------------------------------------------------");

            System.out.println("\nSymbole De Punctuation:");
            for (char symbole : vecSymbole) {
                System.out.print("\t" + symbole + " ");
            }
            System.out.println("\n------------------------------------------------------------------------------");

            System.out.println("\nChaine Léterale:");
            for (String chaine : vecChaineLaterale) {
                System.out.print("\t" + chaine + " ");
            }
            System.out.println("\n------------------------------------------------------------------------------");

            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Check if a string is numeric
    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
    return false;
        }
    }
}

