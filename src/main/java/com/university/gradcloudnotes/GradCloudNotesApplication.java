package com.university.gradcloudnotes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.university.gradcloudnotes.repository",
        "com.university.gradcloudnotes.repository"})
public class GradCloudNotesApplication {

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        /**允许上传的文件最大值*/
        factory.setMaxFileSize(DataSize.parse("50MB"));
        /**设置总上传数据总大小*/
        factory.setMaxRequestSize(DataSize.parse("50MB"));
        return factory.createMultipartConfig();
    }

    public static void main(String[] args) {
        SpringApplication.run(GradCloudNotesApplication.class, args);
    }



}
