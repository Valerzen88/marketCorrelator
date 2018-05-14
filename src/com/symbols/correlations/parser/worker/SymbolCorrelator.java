package com.symbols.correlations.parser.worker;

import com.symbols.correlations.parser.data.SymbolDataStorage;

import static com.symbols.correlations.parser.worker.LineComparator.compareLines;

public class SymbolCorrelator {

    public SymbolCorrelator(final SymbolDataStorage symbolDataStorage, final String lastXMonth) {
        compareLines(symbolDataStorage, lastXMonth);
    }
}
