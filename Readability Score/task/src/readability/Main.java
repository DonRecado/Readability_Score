package readability;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;


public class Main {
    final static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            Text text = new Text(loadFile(args));
            System.out.println(text);
            System.out.println("Enter the score you want to calculate (ARI, FK, SMOG, CL, all): ");
            String input = scanner.next();

            if (input.toUpperCase().equals("ALL")) {
                System.out.println(text.getScore());
            } else {
                System.out.println(text.getScore(input));
            }

        } catch (IOException e) {
            System.out.println("No File Found!");
        }

        scanner.close();
    }


    private static String loadFile(String[] args) throws IOException {

        String fileName = null;

        for (String s : args) {
            if (s.substring(s.length() - 4).matches(".txt")) {
                fileName = s;
            }
        }

        if (fileName != null) {


            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            return sb.toString();
        }
        return null;
    }
}
