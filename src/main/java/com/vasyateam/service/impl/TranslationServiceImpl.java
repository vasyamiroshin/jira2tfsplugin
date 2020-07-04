package com.vasyateam.service.impl;

import lombok.NoArgsConstructor;
import com.vasyateam.mapping.TestCaseMapper;
import com.vasyateam.model.jira.JiraTestCase;
import com.vasyateam.model.tfs.TfsTestCase;
import org.springframework.stereotype.Service;
import com.vasyateam.service.TranslationService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service for conversion jira testcases to tfs testcases.
 *
 * @author Vasiliy_Miroshin
 */
@Service
@NoArgsConstructor
public class TranslationServiceImpl implements TranslationService{

    @Override
    public List<TfsTestCase> convertJiraTestCasesToTfsTestCases(List<JiraTestCase> jiraTestCases) {
        return jiraTestCases.stream()
                .map(TestCaseMapper.INSTANCE::mapJiraTestCaseToTfs)
                .collect(Collectors.toList());
    }
}
