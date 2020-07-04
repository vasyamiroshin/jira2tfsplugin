package com.vasyateam.model.tfs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Describes test step com.vasyateam.model in TFS.
 *
 * @author Vasiliy_Miroshin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TfsTestStep {

    private String stepAction;

    private String stepExpected;

}
