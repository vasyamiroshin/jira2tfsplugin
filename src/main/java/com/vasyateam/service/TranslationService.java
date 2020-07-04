package com.vasyateam.service;

import com.vasyateam.model.jira.JiraTestCase;
import com.vasyateam.model.tfs.TfsTestCase;

import java.util.List;

/**
 * Interface for conversion jira testcases to tfs testcases.
 *
 * @author Vasiliy_Miroshin
 */
public interface TranslationService {

    List<TfsTestCase> convertJiraTestCasesToTfsTestCases(List<JiraTestCase> jiraTestCases);
}
