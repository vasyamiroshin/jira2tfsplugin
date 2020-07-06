package com.vasyateam.model.jira;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  Describes test case com.vasyateam.model in Jira.
 *
 * @author Vasiliy_Miroshin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JiraTestCase {

    private String summary;

    private String testSteps;

    private String expectedResult;

    private Integer priority;
}
