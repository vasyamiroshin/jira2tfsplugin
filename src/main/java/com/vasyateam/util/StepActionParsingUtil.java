package com.vasyateam.util;

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

    private static final String FIRST_STEP_INDEX = "1. ";

    public static Map<Integer, String> parseJiraDescription(String jiraDescriptionField) {

        HashMap<Integer, String> steps = new HashMap<>();
        int currentStepIndex = 1;
        int stepDescriptionStartingPosition = 0;
        int stepDescriptionFinishingPosition = 0;

        if (jiraDescriptionField.startsWith(FIRST_STEP_INDEX)) {
            currentStepIndex = 2;
            stepDescriptionStartingPosition = 3;
        }

        while (jiraDescriptionField.contains(" " + currentStepIndex + ".")) {
            stepDescriptionFinishingPosition = jiraDescriptionField.indexOf(" " + currentStepIndex + ".");
            steps.put(currentStepIndex - 1, jiraDescriptionField
                    .substring(stepDescriptionStartingPosition, stepDescriptionFinishingPosition));
            stepDescriptionStartingPosition = stepDescriptionFinishingPosition + 3;
            currentStepIndex++;
        }
        stepDescriptionFinishingPosition = jiraDescriptionField.length() - 1;
        steps.put(currentStepIndex - 1, jiraDescriptionField
                .substring(stepDescriptionStartingPosition, stepDescriptionFinishingPosition));
        return steps;
    }

}

