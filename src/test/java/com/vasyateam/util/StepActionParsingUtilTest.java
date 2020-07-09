package com.vasyateam.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static com.vasyateam.util.StepActionParsingUtil.parseJiraDescription;

import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * Test for utility for parsing step actions from whole text.
 *
 * @author Vasiliy_Miroshin
 */
class StepActionParsingUtilTest {

    @Test
    void parseJiraDescriptionWithPrecondition() {
        final String jiraDescription = "*Предусловие:* есть Ромашка и связанный с ним ЭД \"Пион\". Скан-копии " +
                "документов Ромашка предоставленных клиентом на бумажных носителях, хранятся в системе Солнышко." +
                "  1. Перейти в скроллер \"Пион\" на стороне Клиента. 2. Открыть ЭД \"Пион\" в режиме " +
                "просмотра. 3. Перейти на вкладку \"Другие цветы\". 4. Нажать на пиктограмму около запис" +
                "и _Ромашка (Передано на бумажном носителе)_. 5. Проверить ф" +
                "ормат выгруженного документа.";
        Map<Integer, String> steps = parseJiraDescription(jiraDescription);
        assertTrue(steps.size() > 0);
        assertEquals(6, steps.size());
    }

    @Test
    void parseJiraDescriptionWithoutPrecondition() {
        final String jiraDescription = "1. Зайти в скроллер пионов садовником. 2. Перейти на ЭФ одно" +
                "го из пионов в статусе \"На цветнике\". Перейти на вкладку \"Другие цветы\".  3. Прове" +
                "рить в БД по полю длина корней дату последнего обновления метаданных.  4. Если" +
                " текущая дата-время больше даты-времени в поле длина корней меньше, чем на 15 " +
                "минут, проверить, что запрос в Солнышко не отправляется. 5. Когда текущая дата-время будет больше " +
                "даты-времени в поле длина корней на 15 минут и больше, проверить по логам, что" +
                " ушел запрос в Солнышко. 6. Проверить, что запрос в Солнышко корректный. 7. После получения данных из " +
                "Солнышка проверить, что обновилась дата-время в поле длина корней. 8. Поменять в к" +
                "онфиг мапах время, через которое должно происходить обновление метаданных. Проверить, что запр" +
                "ос в Солнышко уходит через указанные промежутки времени.";
        Map<Integer, String> steps = parseJiraDescription(jiraDescription);
        assertTrue(steps.size() > 0);
        assertEquals(8, steps.size());
    }

    @Test
    void parseJiraExpectedActionsWithoutPrecondition() {
        final String jiraDescription = "1. Открыт в скроллер \"Пионы\" на стороне Садовника. 2. Открыт ЭД \"Пионы\"" +
                " в режиме просмотра. 3. Открыта вкладка \"Другие садовники\". 4. Началась выгрузка скан-документа Р" +
                "омашка. 5. Формат выгруженных документов соответствует формату хранения из данной системы в Садик 2" +
                ".1.";
        Map<Integer, String> steps = parseJiraDescription(jiraDescription);
        assertTrue(steps.size() > 0);
        assertEquals(5, steps.size());
    }

    @Test
    void parseJiraExpectedActionsWithSubPoints() {
        final String jiraDescription = "1. Войти как Клиент > Импорт\n2. Импортировать XML файл \"Жучка\", в кото" +
                "ром:\n2.1 Вид собаки (песеля) = 1, 2, 7, 8, 9:\n* ABBA = 1, 7, 8\n* AC/DC " +
                "= 2, 9, 0\n\n2.2 Вид собаки (песеля) = 5, 6:\n* ABBA = 1, 9\n* AC/DC = 2, 8";
        Map<Integer, String> steps = parseJiraDescription(jiraDescription);
        assertTrue(steps.size() > 0);
        assertEquals(4, steps.size());
    }

    @Test
    void parseJiraExpectedActionsAndResultsWithSubPoints() {
        final String jiraActionDescription = "1. ABBA 2.DEMO 3.  KASTA  3.1 SCORPIONS 4. LESOPOVAL";
        final String jiraResultDescription = "1. ABBA 4. LESOPOVAL";
        Map<Integer, String> steps = parseJiraDescription(jiraActionDescription);
        Map<Integer, String> results = parseJiraDescription(jiraResultDescription);
        assertTrue(steps.size() > 0);
        assertEquals(5, steps.size());
        assertTrue(results.size() > 0);
        assertEquals(2, results.size());
        int actionStep = steps.entrySet().stream()
                .filter(integerStringEntry -> integerStringEntry.getValue().equals("LESOPOVAL"))
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(-1);
        int resultStep = results.entrySet().stream()
                .filter(integerStringEntry -> integerStringEntry.getValue().equals("LESOPOVAL"))
                .findFirst()
                .map(Map.Entry::getKey)
                .orElse(-1);
        assertEquals(actionStep, resultStep);
    }


}