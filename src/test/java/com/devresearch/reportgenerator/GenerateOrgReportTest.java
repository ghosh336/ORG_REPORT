package com.devresearch.reportgenerator;

import static org.junit.jupiter.api.Assertions.*;

import com.devresearch.common.ErrorUtils;
import org.junit.jupiter.api.Test;


public class GenerateOrgReportTest {

    @Test
    public void checkERROR_1001_ReceivedWhenFileIsNotFound() {
        String anyDummyPath = "\\randomDirectory\\randomFile.csv";
        Throwable thrown = assertThrows(RuntimeException.class, ()-> {GenerateOrgReport.generateAndPrintReport(anyDummyPath);});
        assertEquals(ErrorUtils.getMessage(ErrorUtils.ERROR_1001), thrown.getMessage());
    }

    @Test
    public void checkERROR_1002_ReceivedWhenFileHasInvalidValues() {
        String fileWithInvalidData = "src\\test\\java\\resources\\fileWithInvalidData.csv";
        Throwable thrown = assertThrows(RuntimeException.class, ()-> {GenerateOrgReport.generateAndPrintReport(fileWithInvalidData);});
        assertEquals(ErrorUtils.getMessage(ErrorUtils.ERROR_1002), thrown.getMessage());
    }

    @Test
    public void checkERROR_1004_ReceivedWhenFileHasTooLessValues() {
        String fileWithLessValues = "src\\test\\java\\resources\\tooLessValuesSeparatedByComma.csv";
        Throwable thrown = assertThrows(RuntimeException.class, ()-> {GenerateOrgReport.generateAndPrintReport(fileWithLessValues);});
        assertEquals(ErrorUtils.getMessage(ErrorUtils.ERROR_1004), thrown.getMessage());
    }

    @Test
    public void checkERROR_1004_ReceivedWhenFileHasTooManyValues() {
        String filWithManyValues = "src\\test\\java\\resources\\tooManyValuesSeparatedByComma.csv";
        Throwable thrown = assertThrows(RuntimeException.class, ()-> {GenerateOrgReport.generateAndPrintReport(filWithManyValues);});
        assertEquals(ErrorUtils.getMessage(ErrorUtils.ERROR_1004), thrown.getMessage());
    }

    @Test
    public void checkERROR_1005_ReceivedWhenFileIsEmpty() {
        String emptyFile = "src\\test\\java\\resources\\emptyInputFile.csv";
        Throwable thrown = assertThrows(RuntimeException.class, ()-> {GenerateOrgReport.generateAndPrintReport(emptyFile);});
        assertEquals(ErrorUtils.getMessage(ErrorUtils.ERROR_1005), thrown.getMessage());
    }
}
