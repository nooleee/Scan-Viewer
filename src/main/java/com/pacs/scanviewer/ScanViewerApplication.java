package com.pacs.scanviewer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ScanViewerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScanViewerApplication.class, args);
    }

}
