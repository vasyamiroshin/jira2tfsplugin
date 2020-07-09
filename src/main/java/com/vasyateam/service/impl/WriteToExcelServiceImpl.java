package com.vasyateam.service.impl;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import com.vasyateam.model.tfs.TfsTestCase;
import com.vasyateam.model.tfs.TfsTestStep;
import com.vasyateam.service.WriteToExcelService;
import lombok.NoArgsConstructor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Service for writing and saving excel files.
 *
 * @author Vasiliy_Miroshin
 */
@Service
@NoArgsConstructor
public class WriteToExcelServiceImpl implements WriteToExcelService {

    @Override
    public void writeTfsTestCasesIntoFile(List<TfsTestCase> tfsTestCases, String filePath) throws IOException {

        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet("TFS Export");
        createHeaderRow(sheet);
        for (TfsTestCase tfsTestCase : tfsTestCases) {
            createRowFromTfsTestCase(tfsTestCase, sheet);
        }
        FileOutputStream out = new FileOutputStream(filePath);
        book.write(out);
        book.close();
        out.close();
    }

    private void createHeaderRow(Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        Cell summary = headerRow.createCell(0);
        summary.setCellValue("summary");
        Cell testSteps = headerRow.createCell(1);
        testSteps.setCellValue("test steps");
        Cell expectedResult = headerRow.createCell(2);
        expectedResult.setCellValue("expected result");
        Cell tags = headerRow.createCell(3);
        tags.setCellValue("tags");
        Cell priority = headerRow.createCell(4);
        priority.setCellValue("priority");
        Cell assignedTo = headerRow.createCell(5);
        assignedTo.setCellValue("assigned to");
    }

    private void createRowFromTfsTestCase(TfsTestCase tfsTestCase, Sheet sheet) {
        Integer minKey = tfsTestCase.getSteps().keySet().stream().min(Integer::compareTo).orElse(0);
        List<Integer> sortedStepKeys = tfsTestCase.getSteps().keySet().stream().sorted().collect(Collectors.toList());
        for (Integer key :  sortedStepKeys){
            TfsTestStep tfsTestStep = tfsTestCase.getSteps().get(key);
            Row headerRow = sheet.createRow(sheet.getLastRowNum() + 1);
            Cell summary = headerRow.createCell(0);
            summary.setCellValue(key.equals(minKey) ? tfsTestCase.getTitle() : EMPTY);
            Cell testSteps = headerRow.createCell(1);
            testSteps.setCellValue(tfsTestStep.getStepAction());
            Cell expectedResult = headerRow.createCell(2);
            expectedResult.setCellValue(tfsTestStep.getStepExpected());
            Cell tags = headerRow.createCell(3);
            tags.setCellValue(EMPTY);
            Cell priority = headerRow.createCell(4);
            Integer priorityValue = tfsTestCase.getPriority();
            if (key.equals(minKey) && priorityValue != null) {
                priority.setCellValue(priorityValue);
            }
            Cell assignedTo = headerRow.createCell(5);
            assignedTo.setCellValue(EMPTY);
        }
    }
}
