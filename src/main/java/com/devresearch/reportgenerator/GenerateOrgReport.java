package com.devresearch.reportgenerator;

import com.devresearch.common.ErrorUtils;
import com.devresearch.model.FinalReport;
import com.devresearch.model.InputDataObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import static com.devresearch.common.Constants.*;

public final class GenerateOrgReport {
    /**
     * This method will be used to generate org report based on the provided condition on requirement document.
     * Cond1: Board wants to make sure that every manager earns at least 20% more than the average salary of its direct subordinates, but no more than 50%
     * Cond2: Company wants to avoid too long reporting lines, therefore we would like to identify all employees which have more than 4 managers between them and the CEO.
     */
    public static FinalReport generateAndPrintReport(String filePath) throws Exception {

        //Step1: To read input file and convert the data present in file reusable object
        List<InputDataObject> inputDataObjects = generateInputDataObject(filePath);

        //Step2:  Create ManagerToEmployeeMap using  inputDataObjects
        Map<Integer, ArrayList<Integer>> managerToEmpMap = createManagerToEmpMap(inputDataObjects);

        //Step 3.1 and 3.2: Find All Managers Earning LessOrMore
        Map<InputDataObject, Double> managersEarningLess = new HashMap<>();
        Map<InputDataObject, Double> managersEarningMore = new HashMap<>();
        Map<InputDataObject, Double> managersEarningJustRight = new HashMap<>();
        findAllManagersEarningLessOrMore(managerToEmpMap, inputDataObjects, managersEarningLess, managersEarningMore, managersEarningJustRight);
        //Any Code can be written here to store managersEarningLess, managersEarningMore, managersEarningJustRight in DB later if needed

        //Step 4 : Find All Employees who have a reporting line which is too Integer
        Map<InputDataObject, Integer> employeesHavingTooManyReportingLines =  new HashMap<>();
        findAllEmpHavingToManyReportingLines(inputDataObjects, employeesHavingTooManyReportingLines);
        //Any Code can be written here to store employeesHavingTooManyReportingLines in DB later if needed

        FinalReport finalReport = new FinalReport(managersEarningLess, managersEarningMore, managersEarningJustRight, employeesHavingTooManyReportingLines);
        return finalReport;
    }


    /**
     * This is private method to convert the data read from file input to java Object.
     * Few validations are put currently. More validations can be added while parsing input data.
     * @return
     */
    private static List<InputDataObject> generateInputDataObject(String filePath) throws Exception {

        List<InputDataObject> inputDataObjects = new ArrayList<>();

        try {
            File inpFile = new File(filePath);
           // System.out.println("InpFile : " + inpFile.getAbsolutePath());

            Scanner sc = new Scanner(inpFile);
            if (sc.hasNextLine()) {
                //Skipping Headers
                String line = sc.nextLine();
            }
            if (inpFile.length() == 0) {
                throw new RuntimeException(ErrorUtils.getMessage(ErrorUtils.ERROR_1005));
            }
            while(sc.hasNextLine()) {
              String line = sc.nextLine();
              String[] splittedLine = line.split(",");
              if (splittedLine.length < 4 || splittedLine.length > 5) {
                  //Assumption : For missing data length of array after split will be less than 4 or more than 5
                  throw new RuntimeException(ErrorUtils.getMessage(ErrorUtils.ERROR_1004));
              }
               Integer mangerId = null;
               if (splittedLine.length == 5) {
                   //Assumption : managerId for CEO is null
                   mangerId = Integer.parseInt(splittedLine[4]);
               }
                InputDataObject inpDataObj = new InputDataObject(Integer.parseInt(splittedLine[0]),
                        splittedLine[1], splittedLine[2], Double.parseDouble(splittedLine[3]),
                        mangerId);

                inputDataObjects.add(inpDataObj);
            }
        } catch (FileNotFoundException foe) {
            foe.printStackTrace();
            throw new RuntimeException(ErrorUtils.getMessage(ErrorUtils.ERROR_1001));
        } catch (NumberFormatException noe) {
            noe.printStackTrace();
            throw new RuntimeException(ErrorUtils.getMessage(ErrorUtils.ERROR_1002));
        } catch (RuntimeException e) {
            e.printStackTrace();
            handleRunTimeException(e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(ErrorUtils.getMessage(ErrorUtils.ERROR_1003));
        }

        return inputDataObjects;
    }

    /**
     * This method is to handle and differentiate various runtime exception to generate distinct and user friendly error messages.
     * @param e
     */
    private static void handleRunTimeException(RuntimeException e) {
        if (e.getMessage().contains(ErrorUtils.getMessage(ErrorUtils.ERROR_1004))) {
            throw new RuntimeException(ErrorUtils.getMessage(ErrorUtils.ERROR_1004));
        } else if (e.getMessage().contains(ErrorUtils.getMessage(ErrorUtils.ERROR_1005))) {
            throw new RuntimeException(ErrorUtils.getMessage(ErrorUtils.ERROR_1005));
        }

    }

    /**
     * This is private method to create ManagerToEmployee Map to be used later.
     * @param inputDataObjects
     * @return
     */
    private static Map<Integer, ArrayList<Integer>> createManagerToEmpMap(List<InputDataObject> inputDataObjects) {

        Map<Integer, ArrayList<Integer>> managerToEmpMap =  new HashMap<>();

        for (InputDataObject inpData : inputDataObjects) {
            if (managerToEmpMap.containsKey(inpData.getManagerId())) {
                List<Integer> existingEmps = managerToEmpMap.get(inpData.getManagerId());
                existingEmps.add(inpData.getEmpId());
            } else {
                ArrayList<Integer> empList = new ArrayList<>();
                empList.add(inpData.getEmpId());
                managerToEmpMap.put(inpData.getManagerId(), empList);
            }
        }

       // System.out.println("managerToEmpMap : " + managerToEmpMap);
        return managerToEmpMap;
    }

    /**
     * This is private method to generate report and find out which managers are earning less than expected and who are earning more.
     * @param managerToEmpMap
     * @param inputDataObjects
     * @param managersEarningLess
     * @param managersEarningMore
     * @param managersEarningJustRight
     */
    private static void findAllManagersEarningLessOrMore(Map<Integer, ArrayList<Integer>> managerToEmpMap,
                                                  List<InputDataObject> inputDataObjects,
                                                  Map<InputDataObject, Double> managersEarningLess,
                                                  Map<InputDataObject, Double> managersEarningMore,
                                                  Map<InputDataObject, Double> managersEarningJustRight) {

        for (Integer managerId : managerToEmpMap.keySet()) {
            List<Integer> subordinateIds = managerToEmpMap.get(managerId);
            double salOfSubOrdinates = 0;

            for (Integer subOrdinateId : subordinateIds) {
                InputDataObject currentSubOrdinate = inputDataObjects.stream().filter(emp -> emp.getEmpId().equals(subOrdinateId)).findFirst().get();
                salOfSubOrdinates += currentSubOrdinate.getSalary();
            }
            double avgSalOfSubordinates = salOfSubOrdinates / subordinateIds.size();

            if (managerId == null) {
                continue;
            }

            InputDataObject currentManager = inputDataObjects.stream().filter(emp -> emp.getEmpId().equals(managerId)).findFirst().get();
            double currentManagerSal = currentManager.getSalary();

            if (currentManagerSal <  avgSalOfSubordinates + (avgSalOfSubordinates * PERCENTAGE_MIN)) {
                managersEarningLess.put(currentManager, ((avgSalOfSubordinates + (avgSalOfSubordinates * PERCENTAGE_MIN)) - currentManagerSal));
            } else if (currentManagerSal >  avgSalOfSubordinates + (avgSalOfSubordinates * PERCENTAGE_MAX)) {
                managersEarningMore.put(currentManager, (currentManagerSal- (avgSalOfSubordinates + (avgSalOfSubordinates * PERCENTAGE_MAX))));
            } else {
                managersEarningJustRight.put(currentManager, (currentManagerSal));
            }
        }
    }

    /**
     * This is private method to find all employees who have too many managers between them and CEO.
     * @param inputDataObjects
     * @param employeesHavingTooManyReportingLines
     */
    private static void findAllEmpHavingToManyReportingLines(List<InputDataObject> inputDataObjects, Map<InputDataObject, Integer> employeesHavingTooManyReportingLines) {

        //Assumption : Manager Id of CEO is null
        InputDataObject empCEO =  inputDataObjects.stream().filter(e -> e.getManagerId() == null).findFirst().get();
        Integer ceoEmpId = empCEO.getEmpId();

        for (InputDataObject currentEmp : inputDataObjects) {

            int depth = -1;
            InputDataObject emp = inputDataObjects.stream().filter(e -> e.getEmpId().equals(currentEmp.getEmpId())).findFirst().get();

            while (emp.getManagerId() != null) {
                Integer managerId = emp.getManagerId();
                emp = inputDataObjects.stream().filter(e -> e.getEmpId().equals(managerId)).findFirst().get();
                depth++;
            }

            //Checking if there are more than 4 managers between employee and CEO
            if (depth > ALLOWED_NUMBER_OF_MANAGERS_IN_BETWEEN) {
                employeesHavingTooManyReportingLines.put(currentEmp, depth);
            }
        }
    }
}
