package com.backend.group.controller;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class FileUtils {
    protected String bookFile;
    protected String outputFile;
    protected String reportFile;
    protected String instructionFile;

    public FileUtils(String fileName) {
        String projectDir = System.getProperty("user.dir");
        this.bookFile = projectDir + "/librarymanagementsystem/src/main/java/com/backend/group/output/" + bookFile;
        this.reportFile = projectDir + "/librarymanagementsystem/src/main/java/com/backend/group/output/report.txt";
        this.outputFile = projectDir + "/librarymanagementsystem/src/main/java/com/backend/group/output/output.txt";
    }

    public void ensureFileAndDirectoryExists(String filePath, String fileName) throws IOException {
        File file = new File(filePath);
        File parentDir = file.getParentFile();

        // Ensure parent directory exists
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
            System.out.println("Output directory created: " + parentDir.getAbsolutePath());
        }

        // Ensure file exists, prompting for a new name if not
        if (!file.exists()) {
            file = createNewFile(parentDir, fileName);
        }
    }

    private File createNewFile(File parentDir, String fileName) throws IOException {
        System.out
                .print(fileName + " File not found. Please enter a name for the file to create (without extension): ");
        Scanner scanner = new Scanner(System.in);
        String newFileName = scanner.nextLine().trim();

        if (newFileName.isEmpty()) {
            newFileName = fileName;
        }

        File newFile = new File(parentDir, newFileName + ".txt");
        newFile.createNewFile();
        System.out.println("File created: " + newFile.getAbsolutePath());
        scanner.close();
        return newFile;
    }

}
