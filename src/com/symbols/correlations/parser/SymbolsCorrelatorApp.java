package com.symbols.correlations.parser;

import java.io.File;
import java.util.Objects;
import java.util.Scanner;

public class SymbolsCorrelatorApp {

    public static void main(final String[] args) throws Exception {
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        final File csvFile = new File(Objects.requireNonNull(classLoader.getResource("positionsTable.csv")).getFile());
        final Scanner scanner = new Scanner(csvFile);
        new SymbolService(scanner, args[0].replace("lastXMonth=",""));
        scanner.close();
    }

}