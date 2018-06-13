package com.symbols.correlations.parser.worker;

import com.symbols.correlations.parser.data.SymbolCorrelatorTableStorage;
import com.symbols.correlations.parser.data.SymbolDataStorage;

import java.util.ArrayList;
import java.util.List;

import static com.symbols.correlations.parser.worker.LineComparator.compareLines;

public class SymbolCorrelator {

    public SymbolCorrelator(final SymbolDataStorage symbolDataStorage, final SymbolCorrelatorTableStorage symbolCorrelatorTableStorage, final String lastXMonth) {
        compareLines(symbolDataStorage, lastXMonth, symbolCorrelatorTableStorage);

        /*final List<String> firstLine = new ArrayList<>();
        final List<String> otherLines = new ArrayList<>();
        firstLine.add("FirstLine: ");
        symbolCorrelatorTableStorage.getCorrelatorTableStorage()
                .forEach(line -> {
                    if (line.size()==1) {
                        firstLine.add(line.get(0));
                    }
                    if (line.size()>1) {
                        otherLines.add(line.toString());
                    }
                });
        System.out.println(firstLine.toString());
        otherLines.forEach(System.out::println);*/
    }
}
