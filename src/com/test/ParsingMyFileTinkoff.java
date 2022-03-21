package com.test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ParsingMyFileTinkoff {
    private static final String FIND_WITH_REGEX = "(.*[\\+\\-.* ₽])([\\+\\-]) (.*)";
    private static final String REPLACE_TO = "$2$3";
    private static final String PATH_TO_FILE = "tinkoff.txt";

    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(PATH_TO_FILE));
        double rashody = 0;
        HashMap<String, Double> map = new HashMap<>();

        while (reader.ready()) {
            String line = reader.readLine();
            if (line.contains("+"))
                continue;

            String replaceAll = line.replaceAll(FIND_WITH_REGEX, REPLACE_TO);   //-500,00 ₽ Оплата в AGZS 2 KAZAN RUS
            String[] myArrays = replaceAll.split("₽");                   //[-500,00 ₽, Оплата в AGZS 2 KAZAN RUS]
            double parseDouble = Math.abs(Double.parseDouble(myArrays[0].replaceAll(",", ".").replaceAll(" ", ""))); // 8500.0

            rashody += parseDouble;
            map.merge(myArrays[1].trim(), parseDouble, Double::sum);


        }
        reader.close();
        System.out.println("Сумма расходов: " + java.text.NumberFormat.getInstance().format(rashody) + " руб.\n");
        System.out.println("Суммы расходов по организациям:");

        Map<String, Double> sortedMap =
                map.entrySet().stream()
                        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                        .collect(Collectors.toMap(
                                Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

        for (Map.Entry<String, Double> entry : sortedMap.entrySet()) {
            System.out.print(entry.getKey() + " - ");
            System.out.println(java.text.NumberFormat.getInstance().format(Double.valueOf(entry.getValue().toString())) + " руб.");

        }
    }
}
/*


OUTPUT:
Сумма расходов: 159 421,33 руб.

Суммы расходов по организациям:
Пополнение брокерского счета - 40 000 руб.
Оплата услуг mBank.ZHKH - 14 624,64 руб.
Перевод по реквизитам карты другого банка - 12 072 руб.
*/


