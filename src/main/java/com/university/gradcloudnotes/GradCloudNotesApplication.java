package com.university.gradcloudnotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.university.gradcloudnotes.repository",
        "com.university.gradcloudnotes.repository"})
public class GradCloudNotesApplication {

    public static void main(String[] args) {
        SpringApplication.run(GradCloudNotesApplication.class, args);
    }

}
