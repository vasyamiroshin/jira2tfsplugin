package com.vasyateam.mapping;

import static org.junit.jupiter.api.Assertions.*;

import com.vasyateam.model.jira.JiraTestCase;
import com.vasyateam.model.tfs.TfsTestCase;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;

/**
 * Test for custom mapper from jira to tfs testcases.
 *
 * @author Vasiliy_Miroshin
 */
class TestCaseMapperTest {

    @Test
    void mapJiraTestCaseToTfs() {

        final String jiraDescription = "*Предусловие:* есть Ромашка и связанный с ним ЭД \"Пион\". Скан-копии " +
                "документов Ромашка предоставленных клиентом на бумажных носителях, хранятся в системе Солнышко." +
                "  1. Перейти в скроллер \"Пион\" на стороне Клиента. 2. Открыть ЭД \"Пион\" в режиме " +
                "просмотра. 3. Перейти на вкладку \"Другие цветы\". 4. Нажать на пиктограмму около запис" +
                "и _Ромашка (Передано на бумажном носителе)_. 5. Проверить ф" +
                "ормат выгруженного документа.";
        final String expectedResult = "1. Открыт в скроллер \"Пионы\" на стороне Садовника. 2. Открыт ЭД \"Пионы\"" +
                " в режиме просмотра. 3. Открыта вкладка \"Другие садовники\". 4. Началась выгрузка скан-документа Р" +
                "омашка. 5. Формат выгруженных документов соответствует формату хранения из данной системы в Садик 2" +
                ".1.";
        JiraTestCase jiraTestCase = new JiraTestCase();
        jiraTestCase.setSummary("testcase");
        jiraTestCase.setTestSteps(jiraDescription);
        jiraTestCase.setExpectedResult(expectedResult);

        TfsTestCase tfsTestCase = TestCaseMapper.INSTANCE.mapJiraTestCaseToTfs(jiraTestCase);
        assertNotNull(tfsTestCase);
        assertTrue(tfsTestCase.getSteps().size() == 6);
        assertEquals(tfsTestCase.getSteps().get(0).getStepExpected(), StringUtils.EMPTY);
    }
}