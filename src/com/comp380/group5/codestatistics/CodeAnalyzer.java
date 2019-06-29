package com.comp380.group5.codestatistics;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *  CodeAnalyzer written by Dong Wang
 */
public class CodeAnalyzer {

    public static final CodeReport generateReport(String path, String fileName) throws IOException {
        int lineOfCode = 0;
        int lineOfComments = 0;
        int totalLines = 0;
        boolean isMutipleLineOfComments = false;

        InputStream inputStream = new FileInputStream(path);
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(inputStream));

        String str;
        while((str = bufferedReader.readLine()) != null) {
            str = str.trim();
            if(!str.equals("")) {
                if (str.startsWith("//")) {
                    lineOfComments++;
                } else if (str.startsWith("/*")) {
                    lineOfComments++;
                    isMutipleLineOfComments = true;
                    if(str.endsWith("*/") && isMutipleLineOfComments) {
                        isMutipleLineOfComments = false;
                    }
                } else if (str.endsWith("*/") && isMutipleLineOfComments) {
                    lineOfComments++;
                    isMutipleLineOfComments = false;
                } else if (isMutipleLineOfComments) {
                    lineOfComments++;
                }  else {
                    lineOfCode++;
                }
            }
            totalLines++;
        }

        //close
        inputStream.close();
        bufferedReader.close();

        return new CodeReport(fileName, lineOfCode, lineOfComments, totalLines);

    }

    public static final List<CodeReport> generateReport(String path) throws IOException {
        int lineOfCode;
        int lineOfComments;
        int totalLines;
        boolean isMutipleLineOfComments;
        List<CodeReport> reports = new ArrayList<>();

        File file = new File(path);
        File[] files = file.listFiles();
        Arrays.sort(files);

        for (int i = 0; i < files.length; i++) {
            String fileName = files[i].getName();
            lineOfCode = 0;
            lineOfComments = 0;
            totalLines = 0;
            isMutipleLineOfComments = false;

            if (files[i].isFile() && (Controller.isFileViald(files[i].getName()))) {
                InputStreamReader inputStream = new InputStreamReader(new FileInputStream(files[i]));
                BufferedReader bufferedReader = new BufferedReader(inputStream);
                String str;
                while ((str = bufferedReader.readLine()) != null) {
                    str = str.trim();
                    if (!str.equals("")) {
                        if (str.startsWith("//")) {
                            lineOfComments++;
                        } else if (str.startsWith("/*")) {
                            lineOfComments++;
                            isMutipleLineOfComments = true;
                            if(str.endsWith("*/") && isMutipleLineOfComments) {
                                isMutipleLineOfComments = false;
                            }
                        } else if (str.endsWith("*/") && isMutipleLineOfComments) {
                            lineOfComments++;
                            isMutipleLineOfComments = false;
                        } else if (isMutipleLineOfComments) {
                            lineOfComments++;
                        } else {
                            lineOfCode++;
                        }
                    }
                    totalLines++;
                }
                //close
                inputStream.close();
                bufferedReader.close();
                reports.add(new CodeReport(fileName, lineOfCode, lineOfComments, totalLines));
            } else if(files[i].isDirectory()) {
                reports.addAll(generateReport(files[i].getAbsolutePath()));
            }
        }
        return reports;
    }
}
