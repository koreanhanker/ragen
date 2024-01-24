package ragen.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class FileContentReader {
    public StringBuilder reader(String fileUrl){

        StringBuilder builder = new StringBuilder();

        try {
            URL url = new URL(fileUrl);
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line).append("\n");
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder;
    }
}
