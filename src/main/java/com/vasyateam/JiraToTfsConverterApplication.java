package com.vasyateam;

import com.vasyateam.controller.FileChoosingActionListener;
import com.vasyateam.view.FileChooser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Main application, endpoint to start.
 */
@SpringBootApplication
public class JiraToTfsConverterApplication implements CommandLineRunner {

    @Autowired
    FileChoosingActionListener fileChoosingActionListener;

    @Autowired
    FileChooser fileChooser;

    public static void main(String[] args) {
        new SpringApplicationBuilder(JiraToTfsConverterApplication.class).headless(false).run(args);
    }

    @Override
    public void run(String... args) throws Exception {
        fileChooser.createAndShowGUI();
    }
}
