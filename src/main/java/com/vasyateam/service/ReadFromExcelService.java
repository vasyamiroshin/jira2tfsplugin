package com.vasyateam.service;

import com.vasyateam.model.jira.JiraTestCase;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import java.io.IOException;
import java.util.List;

/**
 * Interface for reading jira testcases from excel file.
 *
 * @author Vasiliy_Miroshin
 */
public interface ReadFromExcelService {

    List<JiraTestCase> readJiraTestCasesFromFile(String filePath) throws IOException, InvalidFormatException;
}
