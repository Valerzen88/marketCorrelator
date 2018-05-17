package com.symbols.correlations.parser;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Scanner;

public class SymbolsCorrelatorApp {

    public static void main(final String[] args) throws Exception {
        final Scanner positionsTableScanner = new Scanner(getPositionsFile());
        final Scanner correlationTableScanner = new Scanner(getCorrelatorTableFile());
        new SymbolService(positionsTableScanner, correlationTableScanner, args[0].replace("lastXMonth=", ""));
        positionsTableScanner.close();
    }

    private static File getPositionsFile() {
        return getCSVFile("positionsTable.csv");
    }

    private static File getCorrelatorTableFile() {
        return getCSVFile("correlation_table_full.csv");
    }

    private static File getCSVFile(final String fileName) {
        final Path path = Paths.get(fileName);
        File correlatorTableFile = new File(fileName);
        if (!Files.exists(path)) {
            final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            correlatorTableFile = new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());
        }
        return correlatorTableFile;
    }

}