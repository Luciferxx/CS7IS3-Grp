package tcd.CS7IS3;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        List<String> fileData = Files.readAllLines(Paths.get("topics"), StandardCharsets.UTF_8);
        for(String line : fileData) {

        }
        System.out.println("Hello world!");
    }
}