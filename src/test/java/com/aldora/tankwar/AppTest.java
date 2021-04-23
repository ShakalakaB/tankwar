package com.aldora.tankwar;

import com.alibaba.fastjson.JSON;
import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    @org.junit.jupiter.api.Test
    void save() throws IOException {
        String filePath = "tmp/snapshot";

        App.getInstance().save(filePath);

        byte[] bytes = Files.readAllBytes(Paths.get(filePath));
        String fileContent = new String(bytes);
        Snapshot snapshotObject = JSON.parseObject(fileContent, Snapshot.class);
//        assertTrue(snapshotObject.getIsGameOnGoing());
        assertTrue(snapshotObject.isGameOnGoing());
    }
}
