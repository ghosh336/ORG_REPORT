package com.devresearch.model;

import java.util.Map;

public class FinalReport {

    private Map<InputDataObject, Double> managersEarningLess;
    private Map<InputDataObject, Double> managersEarningMore;
    private Map<InputDataObject, Double> managersEarningJustRight;
    private Map<InputDataObject, Integer> employeesHavingTooManyReportingLines;

    public FinalReport(Map<InputDataObject, Double> managersEarningLess, Map<InputDataObject, Double> managersEarningMore, Map<InputDataObject, Double> managersEarningJustRight, Map<InputDataObject, Integer> employeesHavingTooManyReportingLines) {
        this.managersEarningLess = managersEarningLess;
        this.managersEarningMore = managersEarningMore;
        this.managersEarningJustRight = managersEarningJustRight;
        this.employeesHavingTooManyReportingLines = employeesHavingTooManyReportingLines;
    }

    public Map<InputDataObject, Double> printReportForManagersEarningLess() {
        System.out.println("******** Following Managers Earning Less Than They Should ********");
        System.out.println("-------------------------------------------------------------------");
        for (InputDataObject manager : managersEarningLess.keySet()) {
            System.out.println("EmpId : " + manager.getEmpId() + " EmpName " + manager.getFirstName() + " " + manager.getLastName() + ", Earning Less By Amount : " + managersEarningLess.get(manager));
        }
        System.out.println("-------------------------------------------------------------------\n\n");
        return managersEarningLess;
    }

    public Map<InputDataObject, Double> printReportForManagersEarningMore() {
        System.out.println("********  Following Managers Earning More Than They Should ********");
        System.out.println("-------------------------------------------------------------------");
        for (InputDataObject manager : managersEarningMore.keySet()) {
            System.out.println("EmpId : " + manager.getEmpId() + " EmpName " + manager.getFirstName() + " " + manager.getLastName() + ", Earning More By Amount : " + managersEarningMore.get(manager));
        }
        System.out.println("-------------------------------------------------------------------\n\n");
        return managersEarningMore;
    }

    public Map<InputDataObject, Double> printReportForManagersEarningJustRight() {
        System.out.println("******** Following Managers Earning What They Should ********");
        System.out.println("-------------------------------------------------------------------");
        for (InputDataObject manager : managersEarningJustRight.keySet()) {
            System.out.println("EmpId : " + manager.getEmpId() + " EmpName " + manager.getFirstName() + " " + manager.getLastName() + ", Earning Just Right Amount : " + managersEarningJustRight.get(manager));
        }
        System.out.println("-------------------------------------------------------------------\n\n");
        return managersEarningJustRight;
    }

    public Map<InputDataObject, Integer> printReportForEmpWithTooManyReportingLines() {
        System.out.println("******** Following Employees has Too Many Managers Between Them adn CEO ********");
        System.out.println("-------------------------------------------------------------------");
        for (InputDataObject emp : employeesHavingTooManyReportingLines.keySet()) {
            System.out.println("EmpId : " + emp.getEmpId() + " EmpName " + emp.getFirstName() + " " + emp.getLastName() + ", is having too many managers between them and CEO, Count Is :" + employeesHavingTooManyReportingLines.get(emp));
        }
        System.out.println("-------------------------------------------------------------------\n\n");
        return employeesHavingTooManyReportingLines;
    }

}
