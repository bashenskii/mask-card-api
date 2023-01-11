package com.rbashenskyi.maskcardapi;

import com.rbashenskyi.maskcardapi.service.CardMaskService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Slf4j
class MaskCardApiApplicationTests {

    @Autowired
    public CardMaskService cardMaskService;

    public static String INPUT_FILE_NAME = "src/test/resources/application.log";
    public static final String OUTPUT_FILE_NAME = "src/test/resources/application-masked.log";

    //just disable this to see output content
    @AfterAll
    static void cleanTestData() {
        try {
            Files.delete(Paths.get(OUTPUT_FILE_NAME));
        } catch (IOException e) {
            log.warn("File doesn't exist");
        }
    }


    @Test
    void testMaskData() {
        String testString = "{\"commandType\":\"command\",\"id\":\"4444333322221111\",\"hostReferences\":{\"key\":\"value\"},\"decisionFilters\":";
        String testString2 = "\"hostReferences\": {\n" +
                "\t\t\"Account Number\": \"4155796342057211\"\n" +
                "\t},";
        assertEquals("{\"commandType\":\"command\",\"id\":************1111,\"hostReferences\":{\"key\":\"value\"},\"decisionFilters\":", cardMaskService.maskData(testString));
        assertEquals("\"hostReferences\": {\n" +
                "\t\t\"Account Number\": ************7211\n" +
                "\t},", cardMaskService.maskData(testString2));
    }

    @Test
    void testMaskFileContent() throws IOException {
        cardMaskService.maskFileContent(INPUT_FILE_NAME, OUTPUT_FILE_NAME);
        Path outputFilePath = Path.of(OUTPUT_FILE_NAME);
        File outputFile = outputFilePath.toFile();
        assertTrue(outputFile.exists());

        Path inputFilePath = Path.of(INPUT_FILE_NAME);
        String contentInput = Files.readString(inputFilePath);
        String contentOutput = Files.readString(outputFilePath);
        assertEquals(cardMaskService.maskData(contentInput) + "\n", contentOutput);
    }
}
