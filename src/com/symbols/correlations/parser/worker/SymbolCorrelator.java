package com.symbols.correlations.parser.worker;

import com.symbols.correlations.parser.data.SymbolCorrelatorTableStorage;
import com.symbols.correlations.parser.data.SymbolDataStorage;

import java.text.ParseException;

import static com.symbols.correlations.parser.worker.LineComparator.compareLines;

public class SymbolCorrelator {

    public SymbolCorrelator(final SymbolDataStorage symbolDataStorage, final SymbolCorrelatorTableStorage symbolCorrelatorTableStorage,
                            final String lastXMonth, final String startDate, final String endDate, final boolean useMonthPeriod) throws ParseException {

        compareLines(symbolDataStorage, symbolCorrelatorTableStorage, lastXMonth, startDate, endDate, useMonthPeriod);

    }
}
