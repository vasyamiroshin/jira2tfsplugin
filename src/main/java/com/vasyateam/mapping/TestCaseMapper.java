package com.vasyateam.mapping;

import static com.vasyateam.util.StepActionParsingUtil.parseJiraDescription;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import com.vasyateam.model.jira.JiraTestCase;
import com.vasyateam.model.tfs.TfsTestCase;
import com.vasyateam.model.tfs.TfsTestStep;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Custom mapper from jira to tfs testcases.
 *
 * @author Vasiliy_Miroshin
 */
@Mapper
public interface TestCaseMapper {

    TestCaseMapper INSTANCE = Mappers.getMapper(TestCaseMapper.class);

    @Mapping(target = "title", source = "summary")
    TfsTestCase mapJiraTestCaseToTfs(JiraTestCase jiraTestCase);

    @AfterMapping
    default void parseTfsSteps(JiraTestCase jiraTestCase, @MappingTarget TfsTestCase tfsTestCase) {
        Map<Integer, TfsTestStep> tfcSteps = new HashMap<Integer, TfsTestStep>();
        Map<Integer, String> stepActions = parseJiraDescription(jiraTestCase.getTestSteps());
        Map<Integer, String> stepExpectedResults = parseJiraDescription(jiraTestCase.getExpectedResult());

        stepActions.keySet().stream().forEach(
                stepIndex -> {
                    String stepAction = Optional.ofNullable(stepActions.get(stepIndex)).orElse(EMPTY);
                    String stepExpected = Optional.ofNullable(stepExpectedResults.get(stepIndex)).orElse(EMPTY);
                    TfsTestStep tfsTestStep = new TfsTestStep(stepAction, stepExpected);
                    tfcSteps.put(stepIndex, tfsTestStep);
                }
        );
        tfsTestCase.setSteps(tfcSteps);
    }
}
