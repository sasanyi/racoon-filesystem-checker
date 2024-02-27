package hu.racoonsoftware.filesystemchecker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point of WebService
 */
@SpringBootApplication
public class FilesystemCheckerApplication {

    public static void main(String[] args) {
        SpringApplication.run(FilesystemCheckerApplication.class, args);
    }

}
