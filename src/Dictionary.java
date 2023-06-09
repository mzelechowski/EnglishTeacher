import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Dictionary {
    private final static String path = "C:\\Mike\\EnglishTeacher\\src\\storage\\";
    private final static String fileName = "dictionary.txt";
    private int correct;
    private int incorrect;
    private Map<String, List<String>> dictionaryAsMap;
    private List<String> keyList;

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
                if (dictionaryAsMap.get(keyWord).stream().anyMatch(d -> d.toLowerCase().equals(finalAnswer.toLowerCase()))) {
                    System.out.println("\t\u2696\t\u261D\u261D Barwo odgadłeś szukane słowwo :D. " +
                            "Wszystkie tłumaczenia dla słowa " + ConsoleColors.GREEN_BOLD + keyWord + ConsoleColors.RESET + " to:");
                    printAllAnswers(keyWord);
                    correct++;
                } else {
                    incorrect++;
                    System.out.println(ConsoleColors.RED+"\t\u2622\u26A0\u26A1\u26B0 Niestety odpowiedź jest niepoprawna. :(");
                    System.out.println(ConsoleColors.RESET +"Poprawne odpowiedzi to:");
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

    private List<String> getKeyListFromMap(Map<String, List<String>> dictionary) {
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
            System.out.println("Dictionary file does not exist. The program will be interrupted.!!!");
            System.out.println("Check that the given path to file and fileName of the dictionary file are correct.");
            System.exit(0);
        }
        BufferedReader br;
        String line;
        dictionary = new TreeMap<>();
        try {
            br = new BufferedReader(new FileReader(file));
            //br= new BufferedReader(new InputStreamReader(new FileInputStream(file), c));
            while ((line = br.readLine()) != null) {
                String[] tab = line.split("[-,]");
                LinkedList wordTranslete = new LinkedList();
                for (int i = 1; i < tab.length; i++) {
                    wordTranslete.add(tab[i].trim());

                }
                dictionary.put(tab[0].replaceAll("[^\\x00-\\x7F]", "").trim(), wordTranslete);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sortBubbleByMe(dictionary);
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
    public Map<String, List<String>> sortMapByKeys(Map<String, List<String>> map) {
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }
    public Map<String, List<String>>  sortByKeysV2(Map<String, List<String>> map) {
        Map<String, List<String>> result2 = new LinkedHashMap<>();
        map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEachOrdered(x -> result2.put(x.getKey(), x.getValue()));
        return result2;
    }

    public Map<String, List<String>> sortBubbleByMe(Map<String, List<String>> map) {
        List<Map.Entry<String, List<String>>> listOfMap = new LinkedList<>(map.entrySet()) ;
        Map.Entry<String, List<String>> temp1;
        Map<String, List<String>> orderMap = new LinkedHashMap<>();
        for (int j = 0; j < listOfMap.size(); j++) {
            for (int i = j + 1; i < listOfMap.size(); i++) {
                if (listOfMap.get(i).getKey().compareTo(listOfMap.get(j).getKey()) < 0) {
                    temp1 = listOfMap.get(j);
                    listOfMap.set(j, listOfMap.get(i));
                    listOfMap.set(i, temp1);
                }
            }
        }
        for (Map.Entry<String, List<String>> e : listOfMap) {
            orderMap.put(e.getKey(), e.getValue());
        }
        return orderMap;
    }
}