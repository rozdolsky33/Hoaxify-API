package com.hoaxify.configuration;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "hoaxify") // used for tying the external properties to this object - with define prefix for properties
public class AppConfiguration {

    String uploadPath;
    String profileImagesPath = "profile";
    String attachmentsFolder = "attachments";

    public String getFullProfileImagesPath(){
        return this.uploadPath + "/" + this.profileImagesPath;
    }

    public String getFullAttachmentsPath() {
        return this.uploadPath + "/" + this.attachmentsFolder;
    }
}
