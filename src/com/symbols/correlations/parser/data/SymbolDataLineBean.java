package com.symbols.correlations.parser.data;

import java.util.Date;
import java.util.Objects;

public class SymbolDataLineBean {

    private String symbolName;
    private String positionDirection;
    private Date entryDay;
    private Date exitDay;

    public String getSymbolName() {
        return symbolName;
    }

    public void setSymbolName(String symbolName) {
        this.symbolName = symbolName;
    }

    public String getPositionDirection() {
        return positionDirection;
    }

    public void setPositionDirection(String positionDirection) {
        this.positionDirection = positionDirection;
    }

    public Date getEntryDay() {
        return entryDay;
    }

    public void setEntryDay(Date entryDay) {
        this.entryDay = entryDay;
    }

    public Date getExitDay() {
        return exitDay;
    }

    public void setExitDay(Date exitDay) {
        this.exitDay = exitDay;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SymbolDataLineBean that = (SymbolDataLineBean) o;
        return Objects.equals(symbolName, that.symbolName) &&
                Objects.equals(positionDirection, that.positionDirection) &&
                Objects.equals(entryDay, that.entryDay) &&
                Objects.equals(exitDay, that.exitDay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbolName, positionDirection, entryDay, exitDay);
    }

    @Override
    public String toString() {
        return "SymbolDataLineBean{" +
                "symbolName='" + symbolName + '\'' +
                ", positionDirection='" + positionDirection + '\'' +
                ", entryDay=" + entryDay +
                ", exitDay=" + exitDay +
                '}';
    }
}
