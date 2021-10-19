package readability;

import java.util.List;

public class Word {
    private final String text;
    private final int syllables;
    private final int characters;

    public Word(String text) {
        this.text = text;
        this.syllables = setSyllables(text);
        this.characters = setCharacters(text);
    }

    public int getCharacters() {
        return characters;
    }

    private int setSyllables(String text) {
        List<Character> allowedSyllables = List.of('a', 'e', 'i', 'o', 'u', 'y', 'A', 'E', 'I', 'O', 'U', 'Y');
        int counter = 0;
        for (int i = 0; i < text.length(); i++) {
            if (allowedSyllables.contains(text.charAt(i))) {
                counter++;

                if (i != 0) {
                    if (allowedSyllables.contains(text.charAt(i - 1))) {
                        counter--;
                    }
                }

                if (i == text.length() - 1 && text.charAt(i) == 'e') {
                    counter--;
                }
            }
        }
        return counter <= 0 ? 1 : counter;
    }

    private int setCharacters(String text) {
        return text.length();
    }

    public int getSyllables() {
        return syllables;
    }
}
