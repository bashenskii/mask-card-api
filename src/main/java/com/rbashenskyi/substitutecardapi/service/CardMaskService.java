package com.rbashenskyi.substitutecardapi.service;

import com.rbashenskyi.substitutecardapi.config.CardProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardMaskService {

    public final CardProperties cardProperties;

    public void maskFileContent(final String inputFileName, final String outputFileName) {
        try (FileReader reader = new FileReader(inputFileName);
             BufferedReader bufferedReader = new BufferedReader((reader));
             FileWriter fw = new FileWriter(outputFileName)) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String markedData = maskData(line);
                System.out.println(markedData);
                fw.write(markedData + "\n");
            }
        } catch (IOException e) {
            log.error("Exception occurred while reading/writing file", e);
            throw new RuntimeException(e);
        }
    }

    public String maskData(final String data) {
        return data.replaceAll(cardProperties.getVisaRegex(), cardProperties.getVisaMask())
                .replaceAll(cardProperties.getMastercardRegex(), cardProperties.getMastercardMask());
    }
}
