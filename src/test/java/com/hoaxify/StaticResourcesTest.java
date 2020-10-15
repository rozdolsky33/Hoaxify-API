package com.hoaxify;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.FilteredClassLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class StaticResourcesTest {

    @Value("${uploadpath}")
    String uploadPath;

    @Test
    public void checkStaticFolder_whenAppIsInitialized_uploadFolderMustExist(){
        File uploadFolder = new File("uploads-test");
        boolean uploadFolderExist = uploadFolder.exists() && uploadFolder.isDirectory();
        assertThat(uploadFolderExist).isTrue();

    }

}
