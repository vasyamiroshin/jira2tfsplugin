package com.vasyateam.service.impl;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import com.vasyateam.model.jira.JiraTestCase;
import com.vasyateam.service.ReadFromExcelService;
import lombok.NoArgsConstructor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service for reading jira testcases from excel file.
 *
 * @author Vasiliy_Miroshin
 */
@Service
@NoArgsConstructor
public class ReadFromExcelServiceImpl implements ReadFromExcelService {

    @Override
    public List<JiraTestCase> readJiraTestCasesFromFile(String filePath) throws IOException, InvalidFormatException {
        List<JiraTestCase> jiraTestCases = new ArrayList<>();
        Workbook workbook = WorkbookFactory.create(new File(filePath));
        Sheet sheet = workbook.getSheetAt(0);
        sheet.forEach(row -> {
            if (row.getRowNum() > 0) {
                JiraTestCase jiraTestCase = new JiraTestCase();
                jiraTestCase.setSummary(Optional.ofNullable(row.getCell(0)).map(Cell::getStringCellValue).orElse(EMPTY));
                jiraTestCase.setTestSteps(Optional.ofNullable(row.getCell(1)).map(Cell::getStringCellValue).orElse(EMPTY));
                jiraTestCase.setExpectedResult(Optional.ofNullable(row.getCell(2)).map(Cell::getStringCellValue).orElse(EMPTY));
                jiraTestCase.setPriority(Optional.ofNullable(row.getCell(3)).map(Cell::getStringCellValue).orElse(EMPTY));


                jiraTestCases.add(jiraTestCase);
            }
        });
        workbook.close();
        return jiraTestCases;
    }
}
