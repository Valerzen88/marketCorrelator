package com.symbols.correlations.parser.worker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public interface LineParser {

    static Date parseDateFromLine(final String dateString) {
        try {
            return new SimpleDateFormat("dd.MM.yyyy").parse(dateString);
        } catch (final ParseException e) {
            throw new RuntimeException(e);
        }
    }

    static String checkIfCellIsAvailable(final String cell) {
        return cell != null ? cell : "noValueAvailable";
    }

}