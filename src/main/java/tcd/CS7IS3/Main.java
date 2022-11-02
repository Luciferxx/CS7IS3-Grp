package tcd.CS7IS3;

import tcd.CS7IS3.models.TopicModel;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        // TODO: Make separate function probably
        List<String> fileData = Files.readAllLines(Paths.get("topics"), StandardCharsets.UTF_8);
        ArrayList<TopicModel> topics = new ArrayList<TopicModel>();
        String fieldType = "";
        TopicModel topic = new TopicModel();
        StringBuilder readString = new StringBuilder();
        for(String line : fileData) {
            if(line.length() > 0) {
                if(line.charAt(0) == '<') {
                    String newFieldType = line.substring(1, line.indexOf('>'));
                    switch (newFieldType) {
                        case "top": topic = new TopicModel(); break;
                        case "/top": topics.add(topic); break;
                        case "num": topic.setNumber(Integer.parseInt(line.substring(14).trim())); break;
                        case "title": topic.setTitle(line.substring(8).trim());
                    }
                    if (!newFieldType.equals(fieldType)) {
                        switch (fieldType) {
                            case "desc": topic.setDescription(readString.toString().trim()); break;
                            case "narr": topic.setNarrative(readString.toString().trim()); break;
                        }
                        readString = new StringBuilder();
                    }
                    fieldType = newFieldType;
                } else {
                    readString.append(line.trim()).append(" ");
                }
            }
        }
        System.out.println(topics.get(0));
    }
}