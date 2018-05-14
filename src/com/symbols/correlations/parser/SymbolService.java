package com.symbols.correlations.parser;

import com.symbols.correlations.parser.data.SymbolDataLineBean;
import com.symbols.correlations.parser.data.SymbolDataStorage;
import com.symbols.correlations.parser.worker.SymbolCorrelator;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.symbols.correlations.parser.worker.FileParser.parseLine;
import static com.symbols.correlations.parser.worker.LineParser.checkIfCellIsAvailable;
import static com.symbols.correlations.parser.worker.LineParser.parseDateFromLine;

class SymbolService {

    SymbolService(final Scanner scanner, final String lastXMonth) {

        final SymbolDataStorage symbolDataStorage = new SymbolDataStorage();
        final List<SymbolDataLineBean> symbolDataLineBeanList = new ArrayList<>();

        scanner.nextLine();
        while (scanner.hasNext()) {
            final List<String> line = parseLine(scanner.nextLine());

            if (line != null) {
                final SymbolDataLineBean symbolDataLineBean = new SymbolDataLineBean();

                symbolDataLineBean.setSymbolName(checkIfCellIsAvailable(line.get(3)));
                symbolDataLineBean.setPositionDirection(checkIfCellIsAvailable(line.get(5)));
                symbolDataLineBean.setEntryDay(parseDateFromLine(checkIfCellIsAvailable(line.get(6))));
                symbolDataLineBean.setExitDay(parseDateFromLine(checkIfCellIsAvailable(line.get(15))));

                symbolDataLineBeanList.add(symbolDataLineBean);
            }

        }

        symbolDataStorage.setSymbolDataStorage(symbolDataLineBeanList);

        new SymbolCorrelator(symbolDataStorage, lastXMonth);

    }

}
