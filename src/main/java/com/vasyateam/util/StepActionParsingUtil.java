package com.vasyateam.util;

import static org.apache.commons.lang3.StringUtils.trim;

import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility for parsing step actions from whole text.
 *
 * @author Vasiliy_Miroshin
 */
@UtilityClass
public class StepActionParsingUtil {

    private static final String FIRST_STEP_INDEX = "1.";

    public static Map<Integer, String> parseJiraDescription(String jiraDescriptionField) {

        jiraDescriptionField = trim(jiraDescriptionField);

        HashMap<Integer, String> steps = new HashMap<>();
        int currentStepIndex = 0;
        int stepDescriptionStartingPosition = 0;
        int stepDescriptionFinishingPosition;

        if (jiraDescriptionField.startsWith(FIRST_STEP_INDEX)) {
            stepDescriptionStartingPosition = 2;
        }

        for (int i = 1; i <= 10; i++) {
            String pointPattern = i + ".";
            int nextPointPosition = jiraDescriptionField.indexOf(i + 1 + ".");
            if (jiraDescriptionField.contains(pointPattern)) {
                stepDescriptionFinishingPosition = jiraDescriptionField.indexOf(pointPattern);
                if ((stepDescriptionFinishingPosition > stepDescriptionStartingPosition)
                        && ((stepDescriptionFinishingPosition <= nextPointPosition) || (currentStepIndex >0 && nextPointPosition <0))) {
                    steps.put(currentStepIndex, jiraDescriptionField
                            .substring(stepDescriptionStartingPosition, stepDescriptionFinishingPosition).trim());
                    stepDescriptionStartingPosition = stepDescriptionFinishingPosition + 2;
                }
            }
            currentStepIndex++;

            for (int j = 1; j <= 5; j++) {
                String subpointPattern = i + "." + j;
                if (jiraDescriptionField.contains(subpointPattern)) {
                    stepDescriptionFinishingPosition = jiraDescriptionField.indexOf(subpointPattern);
                    if ((stepDescriptionFinishingPosition > stepDescriptionStartingPosition)
                            && ((stepDescriptionFinishingPosition <= nextPointPosition) || (currentStepIndex >0 && nextPointPosition <0))) {
                        steps.put(currentStepIndex, jiraDescriptionField
                                .substring(stepDescriptionStartingPosition, stepDescriptionFinishingPosition).trim());
                        stepDescriptionStartingPosition = stepDescriptionFinishingPosition + 3;
                    }
                }
                currentStepIndex++;
            }
        }

        int fieldLength = jiraDescriptionField.length();
        stepDescriptionFinishingPosition = (fieldLength > 0) ? fieldLength  - 1 : 0;
        steps.put(currentStepIndex, jiraDescriptionField
                .substring(stepDescriptionStartingPosition, stepDescriptionFinishingPosition));
        return steps;
    }

}

