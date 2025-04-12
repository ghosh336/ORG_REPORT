package com.devresearch.executor;

import com.devresearch.common.Constants;
import com.devresearch.model.FinalReport;
import com.devresearch.reportgenerator.GenerateOrgReport;

public class Application {

    public static void main(String[] args) throws Exception{
        FinalReport finalReport = GenerateOrgReport.generateAndPrintReport(Constants.INPUT_FILE_PATH);
        finalReport.printReportForManagersEarningLess();
        finalReport.printReportForManagersEarningMore();
        finalReport.printReportForEmpWithTooManyReportingLines();
    }
}
