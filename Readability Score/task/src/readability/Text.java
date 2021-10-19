package readability;

import java.util.ArrayList;
import java.util.List;

public class Text {
    private final String text;
    private final int words;
    private final int characters;
    private final int sentences;
    private final int syllables;
    private final int polySyllables;
    private List<Word> wordList;

    public Text(String text) {
        this.text = text;
        this.characters = setCharacters(text);
        this.sentences = setSentences(text);
        this.wordList = setWordList(text);
        this.words = wordList.size();
        this.syllables = setSyllables();
        this.polySyllables = setPolySyllables();
    }

    private int setSentences(String text) {
        String[] textArr = text.split("[\\.\\!\\?]");
        return textArr.length;
    }

    private List<Word> setWordList(String text) {
        List<Word> words = new ArrayList<>();
        String[] textArr = text.split("[\\.\\!\\?]");
        for (String sent : textArr) {
            String[] wordArr = sent.split(" ");
            for (String word : wordArr) {
                if (word.length() != 0) {
                    words.add(new Word(word));
                }
            }
        }
        return words;
    }

    private int setCharacters(String text) {
        int characters = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c != ' ') {
                characters++;
            }
        }
        return characters;
    }

    private int setSyllables() {
        int count = 0;
        for (Word w : wordList) {
            count += w.getSyllables();
        }
        return count;
    }

    private int setPolySyllables() {
        int count = 0;
        for (Word w : wordList) {
            if (w.getSyllables() > 2) {
                count++;
            }
        }
        return count;
    }

    private double ari() {
        return ((4.71d * ((double) characters / (double) words)) + (0.5d * (((double) words / (double) sentences)))) - 21.43;
    }

    private double fk() {
        return ((0.39 * ((double) words / (double) sentences)) + (11.8 * ((double) syllables / (double) words))) - 15.59;
    }

    private double smog() {
        return (1.043 * Math.sqrt(((double) polySyllables) * (30 / (double) sentences))) + 3.1291;
    }

    private double cl() {
        double L = ((double) characters / (double) words) * 100;
        double S = ((double) sentences / (double) words) * 100;

        return 0.0588 * L - 0.296 * S - 15.8;
    }

    public String getScore(String input) {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        switch (input.toUpperCase()) {
            case "ARI":
                sb.append("Automated Readability Index: ");
                sb.append(String.format("%.2f", ari()));
                sb.append(" (about " + getRange(ari()) + "-year-olds).");
                break;
            case "FK":
                sb.append("Flesch–Kincaid readability tests: ");
                sb.append(String.format("%.2f", fk()));
                sb.append(" (about " + getRange(fk()) + "-year-olds).");
                break;
            case "SMOG":
                sb.append("Simple Measure of Gobbledygook: ");
                sb.append(String.format("%.2f", smog()));
                sb.append(" (about " + getRange(smog()) + "-year-olds).");
                break;
            case "CL":
                sb.append("Coleman–Liau index: ");
                sb.append(String.format("%.2f", cl()));
                sb.append(" (about " + getRange(cl()) + "-year-olds).");
                break;
        }
        return sb.toString();
    }

    public String getScore() {
        String[] allScores = new String[]{"ARI", "FK", "SMOG", "CL"};
        StringBuilder stringBuilder = new StringBuilder();

        for (String score : allScores) {
            stringBuilder.append(getScore(score));
        }

        double average = (double) (getRange(ari()) + getRange(fk()) + getRange(smog()) + getRange(cl())) / 4;

        stringBuilder.append("\n\nThis text should be understood in average by ");
        stringBuilder.append(String.format("%.2f", average));
        stringBuilder.append("-year-olds.");
        return stringBuilder.toString();
    }


    private static int getRange(double score) {
        Integer range;
        int newScore = score > 13 ? 14 : (int) Math.round(score);


        switch (newScore) {
            case 1:
                range = 6;
                break;
            case 2:
                range = 7;
                break;
            case 3:
                range = 9;
                break;
            case 4:
                range = 10;
                break;
            case 5:
                range = 11;
                break;
            case 6:
                range = 12;
                break;
            case 7:
                range = 13;
                break;
            case 8:
                range = 14;
                break;
            case 9:
                range = 15;
                break;
            case 10:
                range = 16;
                break;
            case 11:
                range = 17;
                break;
            case 12:
                range = 18;
                break;
            case 13:
            case 14:
                range = 24;
                break;
            default:
                range = null;

        }

        return range;

    }


    @Override
    public String toString() {
        return "Words: " + words + "\n" +
                "Sentences: " + sentences + "\n" +
                "Characters: " + characters + "\n" +
                "Syllables: " + syllables + "\n" +
                "Polysyllables: " + polySyllables;
    }
}
