package com.hoaxify;


import com.hoaxify.configuration.AppConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class StaticResourcesTest {

    @Autowired
    AppConfiguration appConfiguration;

    @Test
    public void checkStaticFolder_whenAppIsInitialized_uploadFolderMustExist(){
        File uploadFolder = new File(appConfiguration.getUploadPath());
        boolean uploadFolderExist = uploadFolder.exists() && uploadFolder.isDirectory();
        assertThat(uploadFolderExist).isTrue();
    }
    @Test
    public void checkStaticFolder_whenAppIsInitialized_profileImageSubFolderMustExist(){
        String profileImageFolderPath = appConfiguration.getFullProfileImagesPath();
        File profileImageFolderExist = new File(profileImageFolderPath);
        boolean uploadFolderExist = profileImageFolderExist.exists() && profileImageFolderExist.isDirectory();
        assertThat(uploadFolderExist).isTrue();
    }
    @Test
    public void checkStaticFolder_whenAppIsInitialized_attachmentsSubFolderMustExist(){
        String attachmentsFolderPath = appConfiguration.getFullAttachmentsPath();
        File profileImageFolderExist = new File(attachmentsFolderPath);
        boolean uploadFolderExist = profileImageFolderExist.exists() && profileImageFolderExist.isDirectory();
        assertThat(uploadFolderExist).isTrue();
    }
}
