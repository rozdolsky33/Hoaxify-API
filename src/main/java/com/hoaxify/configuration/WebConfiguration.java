package com.hoaxify.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;
/*
This will be checked by spring for web related configurations like setting static resources pads or setting
interceptors that will intercepting incoming HTTP requests other than WebMVC configuration methods. Implementing web configuration
web related beans.
 */

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Value("${uploadpath}")
     String uploadPath;
    // code checking upload folder when test. will create if it doesn't exist
    @Bean
    CommandLineRunner createUploadFolder(){
        return (args) -> {
            File uploadFolder = new File(uploadPath);
            boolean uploadFolderExist = uploadFolder.exists() && uploadFolder.isDirectory();
            if (!uploadFolderExist){
                uploadFolder.mkdir();
            }
        };
    }

}
