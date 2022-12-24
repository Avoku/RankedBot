package com.kasp.rbw3.perms;

import com.kasp.rbw3.Main;
import org.apache.commons.io.IOUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Perms {

    public static HashMap<String, String> permsData = new HashMap<>();

    public static void loadPerms() throws FileNotFoundException {
        String filename = "permissions.yml";
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
        Map<String, Object> data = yaml.load(new FileInputStream("RBW/permissions.yml"));
        for (String s : data.keySet()) {
            permsData.put(s, data.get(s).toString());
        }

        System.out.println("Successfully loaded the permissions file into memory");
    }

    public static String getPerm(String permission) {
        return permsData.get(permission);
    }
}
