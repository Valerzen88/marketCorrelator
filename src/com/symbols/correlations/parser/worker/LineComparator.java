package com.symbols.correlations.parser.worker;

import com.symbols.correlations.parser.data.SymbolCorrelatorTableStorage;
import com.symbols.correlations.parser.data.SymbolDataLineBean;
import com.symbols.correlations.parser.data.SymbolDataStorage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public interface LineComparator {

    static void compareLines(final SymbolDataStorage dataStorage, final String lastXMonth,
                             final SymbolCorrelatorTableStorage symbolCorrelatorTableStorage) {
        final List<SymbolDataLineBean> symbolDataStorage = dataStorage.getSymbolDataStorage();
        final List<String> tableCorrelationFirstLine = getTableCorrelationFirstLine(symbolCorrelatorTableStorage);
        final List<List<String>> tableCorrelationOtherLines = getTableCorrelationOtherLines(symbolCorrelatorTableStorage);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -Integer.valueOf(lastXMonth));

        final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        System.out.println("***** Working period: " + lastXMonth + " month.");
        System.out.println("***** Start date: " + dateFormat.format(cal.getTime()) + ".");

        for (int i = 1; i < symbolDataStorage.size(); i++) {
            final SymbolDataLineBean previousPosition = symbolDataStorage.get(i - 1);
            final SymbolDataLineBean currentPosition = symbolDataStorage.get(i);
            if (previousPosition.getEntryDay().before(cal.getTime()) || previousPosition.getEntryDay().compareTo(cal.getTime()) == 0) {
                if (previousPosition.getEntryDay().after(currentPosition.getEntryDay())
                        && previousPosition.getExitDay().before(currentPosition.getExitDay())) {
                    System.out.println("Position with index=" + i + " for symbol=" + currentPosition.getSymbolName()
                            + " and direction=" + currentPosition.getPositionDirection() + " from "
                            + dateFormat.format(currentPosition.getEntryDay()) + " can be opened.");
                } else {
                    final String correlationSymbolNameFirst = previousPosition.getSymbolName();
                    final String correlationSymbolNameSecond = currentPosition.getSymbolName();

                    //final String correlationFactor = getCorrelationFactorSymbolArrayIDFromFirstLine(tableCorrelationFirstLine, currentPosition.getSymbolName());
                    System.out.println("Position with index=" + i + " for symbol=" + currentPosition.getSymbolName()
                            + " should be proofed for correlation. Correlation factor is " + "null");
                    //correlationFactor);
                }
            }
        }
    }

    static List<List<String>> getTableCorrelationOtherLines(final SymbolCorrelatorTableStorage symbolCorrelatorTableStorage) {
        final List<List<String>> lines = new ArrayList<>();
        symbolCorrelatorTableStorage.getCorrelatorTableStorage()
                .forEach(line -> {
                    if (line.size() > 1) {
                        lines.add(line);
                    }
                });
        return lines;
    }

    static int getCorrelationFactorSymbolArrayIDFromFirstLine(final List<String> tableCorrelationFirstLine, final String symbolName) {
        for (int i = 0; i < tableCorrelationFirstLine.size(); i++) {
            if (tableCorrelationFirstLine.get(i).equals(symbolName)) {
                return i;
            }
        }
        return -1;
    }

    static int getCorrelationFactorSymbolArrayIDFromOtherLines(final List<List<String>> tableCorrelationLine, final String symbolName) {
        for (int i = 0; i < tableCorrelationLine.size(); i++) {
            if (tableCorrelationLine.get(0).equals(symbolName)) {
                return i;
            }
        }
        return -1;
    }

    static List<String> getTableCorrelationFirstLine(final SymbolCorrelatorTableStorage symbolCorrelatorTableStorage) {
        final List<String> lines = new ArrayList<>();
        symbolCorrelatorTableStorage.getCorrelatorTableStorage()
                .forEach(line -> {
                    if (line.size() == 1) {
                        lines.add(line.get(0));
                    }
                });
        return lines;
    }

}
