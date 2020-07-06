package com.vasyateam.model.tfs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Describes test case com.vasyateam.model in TFS.
 *
 * @author Vasiliy_Miroshin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TfsTestCase {

    private String title;

    private Map<Integer, TfsTestStep> steps;

    private List<String> labels;

    private Integer priority;

    private String assignedTo;

}
