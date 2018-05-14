package com.symbols.correlations.parser;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Scanner;

public class SymbolsCorrelatorApp {

    public static void main(final String[] args) throws Exception {
        final Scanner scanner = new Scanner(getPositionsFile());
        new SymbolService(scanner, args[0].replace("lastXMonth=", ""));
        scanner.close();
    }

    private static File getPositionsFile() {
        final String filePathString = "positionsTable.csv";
        final Path path = Paths.get(filePathString);
        File positionsFile = new File(filePathString);
        if (!Files.exists(path)) {
            final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            positionsFile = new File(Objects.requireNonNull(classLoader.getResource(filePathString)).getFile());
        }
        return positionsFile;
    }

}