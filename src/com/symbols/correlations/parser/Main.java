package com.symbols.correlations.parser;

import java.io.File;
import java.util.Scanner;

public class Main {

    public static void main(final String[] args) throws Exception {

        final String csvFile = "/corr.csv";

        final Scanner scanner = new Scanner(new File(csvFile));

        new SymbolService(scanner);

        scanner.close();

    }

}