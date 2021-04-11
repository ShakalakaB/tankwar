package com.aldora.tankwar;

import static org.junit.Assert.assertTrue;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Unit test for simple App.
 */
public class AppTest
{
    @org.junit.jupiter.api.Test
    void stash() throws IOException {
        String filePath = "tmp/snapshot";

        App.getInstance().stash(filePath);

        byte[] bytes = Files.readAllBytes(Paths.get(filePath));
        String fileContent = new String(bytes);
        Snapshot snapshotObject = JSON.parseObject(fileContent, Snapshot.class);
//        assertTrue(snapshotObject.getIsGameOnGoing());
        assertTrue(snapshotObject.isGameOnGoing());
    }
}
