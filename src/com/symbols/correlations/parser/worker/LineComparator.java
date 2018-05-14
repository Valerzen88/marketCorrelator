package com.symbols.correlations.parser.worker;

import com.symbols.correlations.parser.data.SymbolDataLineBean;
import com.symbols.correlations.parser.data.SymbolDataStorage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

public interface LineComparator {

    static void compareLines(final SymbolDataStorage dataStorage, final String lastXMonth) {
        final List<SymbolDataLineBean> symbolDataStorage = dataStorage.getSymbolDataStorage();
        final Map<String, List<SymbolDataLineBean>> dataLinesGroupedBySymbol = buildSymbolToPositionsMap(symbolDataStorage);
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -Integer.valueOf(lastXMonth));

        final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        System.out.println("***** Working period: " + lastXMonth + " month.");
        System.out.println("***** Start date: " + dateFormat.format(cal.getTime()) + ".");

        dataLinesGroupedBySymbol.forEach((symbol, positions) -> {
            for (int i = 0; i < positions.size(); i++) {
                if (positions.size() > 1 && i > 0) {
                    final SymbolDataLineBean previousPosition = positions.get(i - 1);
                    final SymbolDataLineBean currentPosition = positions.get(i);
                    if (previousPosition.getEntryDay().after(cal.getTime())) {
                        if (previousPosition.getEntryDay().before(currentPosition.getEntryDay())
                                && previousPosition.getExitDay().before(currentPosition.getExitDay())) {
                            System.out.println("Position with index=" + i + " for symbol=" + currentPosition.getSymbolName()
                                    + " and direction=" + currentPosition.getPositionDirection() + " from " + dateFormat.format(currentPosition.getEntryDay()) + " can be opened.");
                        } else {
                            System.out.println("Position with index=" + i + " for symbol=" + currentPosition.getSymbolName() + " should be proofed for correlation");
                        }
                    }
                }
            }

        });
    }

    static Map<String, List<SymbolDataLineBean>> buildSymbolToPositionsMap(final List<SymbolDataLineBean> symbolDataStorage) {
        return symbolDataStorage.stream()
                .collect(groupingBy(SymbolDataLineBean::getSymbolName));
    }

}
