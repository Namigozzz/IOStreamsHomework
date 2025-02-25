package jc.netology.task2and3;

import java.io.*;
import java.util.zip.*;

public class Main {
    public static void main(String[] args) {
        GameProgress gameProgress1 = new GameProgress(30, 5, 50, 100);
        GameProgress gameProgress2 = new GameProgress(45, 6, 55, 200);
        GameProgress gameProgress3 = new GameProgress(60, 7, 60, 300);

        saveGame(gameProgress1, "/Users/namigabdullaev/Games/savegames/save1.dat");
        saveGame(gameProgress2, "/Users/namigabdullaev/Games/savegames/save2.dat");
        saveGame(gameProgress3, "/Users/namigabdullaev/Games/savegames/save3.dat");

        zipFiles(
                "/Users/namigabdullaev/Games/savegames/save.zip",
                new String[]{
                        "/Users/namigabdullaev/Games/savegames/save1.dat",
                        "/Users/namigabdullaev/Games/savegames/save2.dat",
                        "/Users/namigabdullaev/Games/savegames/save3.dat"
                }
        );

        File[] files = new File("/Users/namigabdullaev/Games/savegames").listFiles();
        for (File file : files) {
            if (file.isFile() && !file.getName().endsWith(".zip")) {
                file.delete();
            }
        }

        openZip(
                "/Users/namigabdullaev/Games/savegames/save.zip",
                "/Users/namigabdullaev/Games/savegames"
        );

        openProgress("/Users/namigabdullaev/Games/savegames/save1.dat");
    }

    static void saveGame(GameProgress gameProgress, String filePath) {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void openProgress(String filePath) {
        try (FileInputStream fis = new FileInputStream(filePath);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            GameProgress gameProgress = (GameProgress) ois.readObject();
            System.out.println(gameProgress);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void zipFiles(String zipPath, String[] filePaths) {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipPath))) {
            for (String filePath : filePaths) {
                FileInputStream fis = new FileInputStream(filePath);
                ZipEntry entry = new ZipEntry(new File(filePath).getName());
                zos.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zos.write(buffer);
                zos.closeEntry();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void openZip(String zipPath, String extractPath) {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(zipPath))) {
            ZipEntry entry;

            while ((entry = zis.getNextEntry()) != null) {
                String name = entry.getName();
                String fileName = extractPath + File.separator + name;

                try (FileOutputStream fos = new FileOutputStream(fileName)){
                    byte[] buffer = new byte[zis.available()];
                    int length;
                    while ((length = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, length);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}