package com.symbols.correlations.parser.worker;

import com.symbols.correlations.parser.data.SymbolDataLineBean;
import com.symbols.correlations.parser.data.SymbolDataStorage;

import java.util.List;
import java.util.Optional;

import static com.symbols.correlations.parser.worker.LineComparator.getLineWithTheSameSymbol;
import static java.util.stream.Collectors.toList;

public class SymbolCorrelator {

    public SymbolCorrelator(final SymbolDataStorage symbolDataStorage) {
        final List<SymbolDataLineBean> symbolList = symbolDataStorage.getSymbolDataStorage();

        List<SymbolDataLineBean> alreadyExistingPositionForTheLineWithTheSameSymbol = symbolList
                .stream()
                .filter(line -> getLineWithTheSameSymbol(symbolDataStorage, line).isPresent())
                .collect(toList());
        alreadyExistingPositionForTheLineWithTheSameSymbol.stream().peek(line -> System.out.println(line.toString()));
    }
}
