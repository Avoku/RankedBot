package com.kasp.rbw3.config;

import com.kasp.rbw3.Main;
import org.apache.commons.io.IOUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Config {

    public static HashMap<String, String> configData = new HashMap<>();

    public static void loadConfig() throws FileNotFoundException {
        String filename = "config.yml";
        ClassLoader classLoader = Main.class.getClassLoader();

        try (InputStream inputStream = classLoader.getResourceAsStream(filename)) {
            String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);

            File file = new File("RBW/" + filename);
            if (!file.exists()) {
                file.createNewFile();
                BufferedWriter bw = new BufferedWriter(new FileWriter("RBW/" + filename));
                bw.write(result);
                bw.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Yaml yaml = new Yaml();
        Map<String, Object> data = yaml.load(new FileInputStream("RBW/config.yml"));
        for (String s : data.keySet()) {
            configData.put(s, data.get(s).toString());
        }

        System.out.println("Successfully loaded the config file into memory");
    }

    public static String getValue(String key) {
        return configData.get(key);
    }
}
