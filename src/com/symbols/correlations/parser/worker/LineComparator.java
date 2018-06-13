package com.symbols.correlations.parser.worker;

import com.symbols.correlations.parser.data.SymbolCorrelatorTableStorage;
import com.symbols.correlations.parser.data.SymbolDataLineBean;
import com.symbols.correlations.parser.data.SymbolDataStorage;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public interface LineComparator {

    static void compareLines(final SymbolDataStorage dataStorage, final SymbolCorrelatorTableStorage symbolCorrelatorTableStorage,
                             final String lastXMonth, final String startDate, final String endDate, final boolean useMonthPeriod) throws ParseException {
        final List<SymbolDataLineBean> symbolDataStorage = dataStorage.getSymbolDataStorage();
        final List<String> tableCorrelationFirstLine = getTableCorrelationFirstLine(symbolCorrelatorTableStorage);
        final List<List<String>> tableCorrelationOtherLines = getTableCorrelationOtherLines(symbolCorrelatorTableStorage);

        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -Integer.valueOf(lastXMonth));

        final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

        if (useMonthPeriod) {
            System.out.println("***** Working period: " + lastXMonth + " month.");
            System.out.println("***** Start date: " + dateFormat.format(cal.getTime()) + ".");
        } else {
            System.out.println("***** Start date: " + startDate);
            System.out.println("***** End date: " + endDate);
        }

        for (int i = 1; i < symbolDataStorage.size(); i++) {
            final SymbolDataLineBean previousPosition = symbolDataStorage.get(i - 1);
            final SymbolDataLineBean currentPosition = symbolDataStorage.get(i);
            if ((useMonthPeriod && (previousPosition.getEntryDay().after(cal.getTime()) || previousPosition.getEntryDay().compareTo(cal.getTime()) == 0))
                || (previousPosition.getEntryDay().after(dateFormat.parse(startDate)) || previousPosition.getEntryDay().compareTo(dateFormat.parse(startDate)) == 0)) {

                if (previousPosition.getEntryDay().after(currentPosition.getEntryDay())
                        && previousPosition.getExitDay().before(currentPosition.getExitDay())) {
                    System.out.println("Position with index=" + i + " for symbol=" + currentPosition.getSymbolName()
                            + " and direction=" + currentPosition.getPositionDirection() + " from "
                            + dateFormat.format(currentPosition.getEntryDay()) + " can be opened.");
                } else {
                    final String correlationSymbolNameFirst = previousPosition.getSymbolName();
                    final String correlationSymbolNameSecond = currentPosition.getSymbolName();
                    final int correlationFactorSymbolArrayIDFromFirstLine =
                            getCorrelationFactorSymbolArrayIDFromFirstLine(tableCorrelationFirstLine, correlationSymbolNameFirst);

                    final String correlationFactor = getCorrelationFactorSymbolArrayIDFromOtherLines(
                            tableCorrelationOtherLines, correlationSymbolNameSecond, correlationFactorSymbolArrayIDFromFirstLine);
                    if (!correlationFactor.equals("")) {
                        System.out.println("Position with index=" + i + " for symbol=" + correlationSymbolNameSecond
                                + " should be proofed for correlation. Correlation factor is " + correlationFactor);
                    }
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

    static String getCorrelationFactorSymbolArrayIDFromOtherLines(final List<List<String>> tableCorrelationLine, final String symbolName, final int indexFromFirstLine) {
        if (indexFromFirstLine > -1) {
            for (List<String> aTableCorrelationLine : tableCorrelationLine) {
                if (aTableCorrelationLine.get(0).equals(symbolName)) {
                    return aTableCorrelationLine.get(indexFromFirstLine);
                }
            }
        }
        return "";
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
