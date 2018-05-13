package com.symbols.correlations.parser.worker;

import com.symbols.correlations.parser.data.SymbolDataLineBean;
import com.symbols.correlations.parser.data.SymbolDataStorage;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;

public interface LineComparator {

    static Optional<SymbolDataLineBean> getLineWithTheSameSymbol(final SymbolDataStorage dataStorage, final SymbolDataLineBean dataLineBean) {
        Optional<SymbolDataLineBean> lineWithTheSameSymbolOrEmpty = findLineWithTheSameSymbolOrEmpty(dataStorage, dataLineBean);
        return lineWithTheSameSymbolOrEmpty != null && lineWithTheSameSymbolOrEmpty.isPresent()
                ? lineWithTheSameSymbolOrEmpty : Optional.of(new SymbolDataLineBean());
    }

    static Optional<SymbolDataLineBean> findLineWithTheSameSymbolOrEmpty(final SymbolDataStorage dataStorage,
                                                                         final SymbolDataLineBean currentLine) {
        final Date startDate = currentLine.getEntryDay();
        final Date exitDate = currentLine.getExitDay();
        final String symbolName = currentLine.getSymbolName();
        final List<SymbolDataLineBean> symbolDataStorage = dataStorage.getSymbolDataStorage();
        final Optional<SymbolDataLineBean> alreadyExistingLine = symbolDataStorage.stream()
                .filter(line -> line.getSymbolName().equals(symbolName))
                .filter(line -> line.getEntryDay().before(startDate))
                .filter(line -> line.getExitDay() != null && line.getExitDay().after(exitDate))
                .findFirst();
        return alreadyExistingLine != null && alreadyExistingLine.isPresent() ? alreadyExistingLine : empty();
    }
}
