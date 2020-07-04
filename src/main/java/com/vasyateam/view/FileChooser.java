package com.vasyateam.view;

import com.vasyateam.controller.FileChoosingActionListener;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;

/**
 * UI component for file choosing.
 *
 * @author Vasiliy_Miroshin
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Component
public class FileChooser extends JPanel {

    public static final String NEW_LINE = "\n";

    private JButton openButton, saveButton;

    private JTextArea log;

    private JFileChooser fc;

    private FileChoosingActionListener actionListener;

    public FileChooser(FileChoosingActionListener actionListener) {
        super(new BorderLayout());
        super.setPreferredSize(new Dimension(500, 300));
        this.actionListener = actionListener;
        this.actionListener.setFileChooser(this);
        log = new JTextArea(5,20);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);

        JScrollPane logScrollPane = new JScrollPane(log);
        fc = new JFileChooser();
        FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Excel Documents", "xls", "xlsx");
        fc.setFileFilter(fileFilter);

        openButton = new JButton("Open a File...");
        openButton.addActionListener(actionListener);

        saveButton = new JButton("Save a File...");
        saveButton.addActionListener(actionListener);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(openButton);
        buttonPanel.add(saveButton);

        add(buttonPanel, BorderLayout.PAGE_START);
        add(logScrollPane, BorderLayout.CENTER);
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Jira to TFS Converter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.add(new FileChooser(this.actionListener));

        frame.pack();
        frame.setVisible(true);
    }

}
