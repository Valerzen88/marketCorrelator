package com.symbols.correlations.parser;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Scanner;

public class SymbolsCorrelatorApp {

    public static void main(final String[] args) throws Exception {
        final Scanner positionsTableScanner = new Scanner(getPositionsFile());
        final Scanner correlationTableScanner = new Scanner(getCorrelatorTableFile());
        boolean useMonthPeriod=true;
        String lastXMonth = "6";
        final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Calendar cal = Calendar.getInstance();
        String endDate = dateFormat.format(cal.getTime());
        cal.add(Calendar.MONTH, -Integer.valueOf(lastXMonth));
        String startDate = dateFormat.format(cal.getTime());

        if(args[0]!=null && !args[0].equals("") && !args[0].equals("lastXMonth=")) {
            lastXMonth = args[0].replace("lastXMonth=", "");
        } else {
            if(args[1]!=null && !args[1].equals("")) startDate = args[1].replace("startDate=", "");
            if(args[2]!=null && !args[2].equals("")) endDate = args[2].replace("endDate=", "");
            useMonthPeriod=false;
        }

        new SymbolService(positionsTableScanner, correlationTableScanner, lastXMonth, startDate, endDate, useMonthPeriod);
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
