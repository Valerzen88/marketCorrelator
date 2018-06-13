package com.symbols.correlations.parser;

import com.symbols.correlations.parser.data.SymbolCorrelatorTableStorage;
import com.symbols.correlations.parser.data.SymbolDataLineBean;
import com.symbols.correlations.parser.data.SymbolDataStorage;
import com.symbols.correlations.parser.worker.SymbolCorrelator;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static com.symbols.correlations.parser.worker.FileParser.parseLine;
import static com.symbols.correlations.parser.worker.LineParser.checkIfCellIsAvailable;
import static com.symbols.correlations.parser.worker.LineParser.parseDateFromLine;

class SymbolService {

    SymbolService(final Scanner positionsTableScanner, Scanner correlationTableScanner,
                  final String lastXMonth, final String startDate, final String endDate, final boolean useMonthPeriod) throws ParseException {

        final SymbolDataStorage symbolDataStorage = fillSymbolDataStorage(positionsTableScanner);

        final SymbolCorrelatorTableStorage symbolCorrelatorTableStorage = fillSymbolCorrelationTableStorage(correlationTableScanner);

        new SymbolCorrelator(symbolDataStorage, symbolCorrelatorTableStorage, lastXMonth, startDate, endDate, useMonthPeriod);

    }

    private SymbolCorrelatorTableStorage fillSymbolCorrelationTableStorage(final Scanner correlationTableScanner) {
        final SymbolCorrelatorTableStorage symbolCorrelatorTableStorage = new SymbolCorrelatorTableStorage();
        final List<List<String>> lines = new ArrayList<>();
        while (correlationTableScanner.hasNext()) {
            String line = correlationTableScanner.next();
            String[] values = line.split(";");
            lines.add(Arrays.asList(values));
        }

        symbolCorrelatorTableStorage.setCorrelatorTableStorage(lines);
        return symbolCorrelatorTableStorage;
    }

    private SymbolDataStorage fillSymbolDataStorage(Scanner positionsTableScanner) {
        final SymbolDataStorage symbolDataStorage = new SymbolDataStorage();
        final List<SymbolDataLineBean> symbolDataLineBeanList = new ArrayList<>();

        positionsTableScanner.nextLine();
        while (positionsTableScanner.hasNext()) {
            final List<String> line = parseLine(positionsTableScanner.nextLine());

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
        return symbolDataStorage;
    }

}
