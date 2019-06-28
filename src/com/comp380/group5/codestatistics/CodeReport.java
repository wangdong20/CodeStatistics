package com.comp380.group5.codestatistics;

public class CodeReport {
    private String fileName;
    private int lineOfCode;
    private int lineOfComments;
    private int totalLines;

    public CodeReport(String fileName, int lineOfCode, int lineOfComments, int totalLines) {
        this.fileName = fileName;
        this.lineOfCode = lineOfCode;
        this.lineOfComments = lineOfComments;
        this.totalLines = totalLines;
    }

    public void setLineOfComments(int lineOfComments) {
        this.lineOfComments = lineOfComments;
    }

    public int getLineOfCode() {
        return lineOfCode;
    }

    public void setLineOfCode(int lineOfCode) {
        this.lineOfCode = lineOfCode;
    }

    public int getLineOfComments() {
        return lineOfComments;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getTotalLines() {
        return totalLines;
    }

    public void setTotalLines(int totalLines) {
        this.totalLines = totalLines;
    }

    @Override
    public String toString() {
        return "FileName: '" + fileName + '\'' +
                "\nLineOfCode: " + lineOfCode +
                "\nLineOfComments: " + lineOfComments +
                "\nTotalLines: " + totalLines +
                "\n\n";
    }
}
