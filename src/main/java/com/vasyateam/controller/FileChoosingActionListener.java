package com.vasyateam.controller;

import static com.vasyateam.view.FileChooser.NEW_LINE;

import com.vasyateam.model.jira.JiraTestCase;
import com.vasyateam.model.tfs.TfsTestCase;
import com.vasyateam.service.ReadFromExcelService;
import com.vasyateam.service.TranslationService;
import com.vasyateam.service.WriteToExcelService;
import com.vasyateam.view.FileChooser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Action listener for saving and opening excel files.
 *
 * @author Vasiliy_Miroshin
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Component
public class FileChoosingActionListener implements ActionListener {

    @Autowired
    private TranslationService translationService;

    @Autowired
    private WriteToExcelService writeToExcelService;

    @Autowired
    ReadFromExcelService readFromExcelService;

    private List<TfsTestCase> testCases = new ArrayList<>();

    private FileChooser fileChooser;

    @Override
    public void actionPerformed(ActionEvent e) {

        JTextArea log = fileChooser.getLog();
        if (e.getSource() == fileChooser.getOpenButton()) {
            log.setText(StringUtils.EMPTY);
            int returnVal = fileChooser.getFc().showOpenDialog(fileChooser);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getFc().getSelectedFile();
                log.append("Opening: " + file.getName() + "." + NEW_LINE);
                try {
                    List<JiraTestCase> jiraTestCases = readFromExcelService.readJiraTestCasesFromFile(file.getPath());
                    log.append("Found " + jiraTestCases.size() + " testcases." + NEW_LINE);
                    testCases = translationService.convertJiraTestCasesToTfsTestCases(jiraTestCases);
                } catch (IOException | InvalidFormatException e1) {
                    e1.printStackTrace();
                }
            } else {
                log.append("Open command cancelled by user." + NEW_LINE);
            }
            log.setCaretPosition(log.getDocument().getLength());

        } else if (e.getSource() == fileChooser.getSaveButton()) {
            int returnVal = fileChooser.getFc().showSaveDialog(fileChooser);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getFc().getSelectedFile();
                log.append("Saving: " + file.getName() + "." + NEW_LINE);
                if (testCases.size() > 0) {
                    try {
                        writeToExcelService.writeTfsTestCasesIntoFile(testCases, file.getPath());
                        fileChooser.getFc().cancelSelection();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            } else {
                log.append("Save command cancelled by user." + NEW_LINE);
            }
            log.setCaretPosition(log.getDocument().getLength());
        }
    }
}
