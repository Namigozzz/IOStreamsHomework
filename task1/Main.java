package jc.netology.task1;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        File dir = new File("/Users/admin/Games/res/icons");
        File file = new File("/Users/admin/Games/src/main/Utils.java");
        File logFile = new File("/Users/admin/Games/temp/temp.txt");
        StringBuilder sb = new StringBuilder();
        String log;

        if (!dir.exists()) {
            if (dir.mkdir()) {
                sb.append("[INFO] [" + LocalDateTime.now() + "Directory " + dir.getName() + " created\n");
            } else {
                sb.append("[ERROR] [" + LocalDateTime.now() + "] Error during directory " + dir.getName() + " creation\n");
            }
        } else {
            sb.append("[WARN] [" + LocalDateTime.now() + "] Directory " + dir.getName() + " already exists\n");
        }

        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    sb.append("[INFO] [" + LocalDateTime.now() + "] File " + file.getName() + " created\n");
                } else {
                    sb.append("[ERROR] [" + LocalDateTime.now() + "] Error during file " + file.getName() + " creation\n");
                }
            } catch (Exception e) {
                sb.append("[ERROR] [" + LocalDateTime.now() + "] " + e.getMessage() + "\n");
            }
        } else {
            sb.append("[WARN] [" + LocalDateTime.now() + "] File " + file.getName() + " already exists\n");
        }

        log = sb.toString();

        try {
            FileWriter fw = new FileWriter(logFile, true);
            fw.write(log);
            fw.close();
        } catch (Exception e) {
            e.getMessage();
        }
    }
}