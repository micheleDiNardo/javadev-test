package com.onetag.javadev;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

public class CSVExtractor implements Extractor {

    @Override
    public List<String> extract() {
        List<String> vincitori = new ArrayList<>();

        try {
            FileReader fileReaderFemale = new FileReader("src/main/resources/oscar_age_female.csv");
            CSVReader csvReaderFemale = new CSVReader(fileReaderFemale);
            List<String[]> valuesFemale = csvReaderFemale.readAll();
            valuesFemale.remove(0);

            FileReader fileReaderMale = new FileReader("src/main/resources/oscar_age_male.csv");
            CSVReader csvReaderMale = new CSVReader(fileReaderMale);
            List<String[]> valuesMale = csvReaderMale.readAll();
            valuesMale.remove(0);

            List<String[]> valuesAll = new ArrayList<>();
            valuesAll.addAll(valuesFemale);
            valuesAll.addAll(valuesMale);

            HashMap<String, Integer> mapAttori = new HashMap<>();

            for (String[] va : valuesAll) {
                String nome = va[3];
                mapAttori.put(nome, mapAttori.getOrDefault(nome, 0) + 1);
            }

            List<Map.Entry<String, Integer>> sortedEntries = new ArrayList<>(mapAttori.entrySet());

            Collections.sort(sortedEntries, new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> entry1, Map.Entry<String, Integer> entry2) {
                    int comparison = entry2.getValue().compareTo(entry1.getValue());

                    if (comparison == 0 && entry1.getValue() == 1) {
                        int age1 = Integer.parseInt(ottieniEtaAttore(valuesAll, entry1.getKey()));
                        int age2 = Integer.parseInt(ottieniEtaAttore(valuesAll, entry2.getKey()));
                        return Integer.compare(age1, age2);
                    }

                    return comparison;
                }

                private String ottieniEtaAttore(List<String[]> valuesAll, String actorName) {
                    for (String[] row : valuesAll) {
                        if (row[3].equals(actorName)) {
                            return row[2];
                        }
                    }
                    return "";
                }
            });

            for (Map.Entry<String, Integer> entry : sortedEntries) {
                vincitori.add(entry.getKey());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            e.printStackTrace();
        }

        return vincitori;
    }
}
