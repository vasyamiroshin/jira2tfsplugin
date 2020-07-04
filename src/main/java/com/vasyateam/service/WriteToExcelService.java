package com.vasyateam.service;

import com.vasyateam.model.tfs.TfsTestCase;

import java.io.IOException;
import java.util.List;

/**
 * Interface for writing and saving excel files.
 *
 * @author Vasiliy_Miroshin
 */
public interface WriteToExcelService {

    void writeTfsTestCasesIntoFile(List<TfsTestCase> tfsTestCases, String filePath) throws IOException;
}
