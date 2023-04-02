import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Dictionary {
    private final static String path = "C:\\Mike\\EnglishTeacher\\src\\storage\\";
    private final static String fileName = "dictionary.txt";
    private int correct;
    private int incorrect;
    private Map<String, List<String>> dictionaryAsMap;
    private LinkedList<String> keyList;

    public Dictionary() {
        this.correct = 0;
        this.incorrect = 0;
        this.dictionaryAsMap = getMapFromFile(path + fileName);
        this.keyList = getKeyListFromMap(dictionaryAsMap);
    }

    public void play() {
        String answer = "start";
        while (!answer.equals("quit")) {
            System.out.println("\nPodaj tłumaczenie dla słowa (lub wpisz \"quit\" aby zakonczyc): ");
            String keyWord = keyList.get((int) Math.floor(Math.random() * keyList.size()));
            System.out.print(ConsoleColors.GREEN_BOLD + keyWord + ConsoleColors.RESET + " -> ");
            Scanner scanner = new Scanner(System.in);
            answer = scanner.nextLine();
            if (answer.equals("quit")) {
                System.out.println("Zakonczyles program");
            } else {
                String finalAnswer = answer;
                if (dictionaryAsMap.get(keyWord).stream().anyMatch(d -> d.toLowerCase().contains(finalAnswer.toLowerCase()))) {
                    //answer.contains(dictionary.get(keyWord).get(0))
                    System.out.println("\t\t\t\tBarwo odgadłeś szukane słowwo. " +
                            "Wszystkie tłumaczenia dla słowa " + ConsoleColors.GREEN_BOLD + keyWord + ConsoleColors.RESET + " to:");
                    printAllAnswers(keyWord);
                    correct++;
                } else {
                    incorrect++;
                    System.out.println("\t\t\t\tNiestety odpowiedź jest niepoprawna.");
                    System.out.println("Poprawne odpowiedzi to:");
                    //+ dictionary.get(keyWord) + ConsoleColors.RESET);
                    printAllAnswers(keyWord);
                }
            }
        }
        printScores();
    }

    private void printScores() {
        System.out.println("Podałeś prawidłowych odpowiedzi: " + correct);
        System.out.println("Podałeś nieprawidłowych odpowiedzi: " + incorrect);
    }

    private LinkedList<String> getKeyListFromMap(Map<String, List<String>> dictionary) {
        LinkedList<String> keyList = new LinkedList<>();
        for (Map.Entry<String, List<String>> entry : dictionary.entrySet()) {
            keyList.add(entry.getKey());
        }
        return keyList;
    }

    private Map<String, List<String>> getMapFromFile(String pathFileName) {
        Map<String, List<String>> dictionary = null;
        File file = new File(pathFileName);
        //Charset ch = Charset.forName("UTF-8");
        if (!file.exists()) {
            System.out.println("Dictionary file does not exist. The program will be interrupted.");
            System.out.println("Check that the given path to file and fileName of the dictionary file are correct.");
            System.exit(0);
        }
        BufferedReader br;
        String line;
        dictionary = new LinkedHashMap<>();
        try {
            br = new BufferedReader(new FileReader(file));
            //br= new BufferedReader(new InputStreamReader(new FileInputStream(file), c));
            while ((line = br.readLine()) != null) {
                String[] tab = line.split("[-,]");
                LinkedList wordTranslete = new LinkedList();
                for (int i = 1; i < tab.length; i++) {
                    wordTranslete.add(tab[i].trim());
                }
                dictionary.put(tab[0], wordTranslete);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dictionary;
    }

    private void printAllAnswers(String keyWord) {
        System.out.print(ConsoleColors.RED);
        for (String q : dictionaryAsMap.get(keyWord)) {
            System.out.print(q + ", ");
        }
        System.out.print(ConsoleColors.RESET);
    }

    public void printMap() {
        for (Map.Entry<String, List<String>> entry : dictionaryAsMap.entrySet()) {
            System.out.println(entry.getKey() + ":" + entry.getValue());
        }
    }
}
