package com.comp380.group5.codestatistics;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Controller {
    public static final String JAVA_CODE_FILE_SUFFIX = ".java";
    public static final String C_CODE_FILE_SUFFIX = ".c";
    public static final String CPP_CODE_FILE_SUFFIX = ".cpp";
    public static final String H_CODE_FILE_SUFFIX = ".h";
    public static final String OC_CODE_FILE_SUFFIX = ".m";
    public static final String SWIFT_CODE_FILE_SUFFIX = ".swift";

    @FXML
    private TextArea code_statistics_report_area;

    @FXML
    private Label drag_file_area;

    @FXML
    private Button choose_btn;

    private boolean hasGenerateReport;

    @FXML
    private void initialize() {
        hasGenerateReport = false;
        code_statistics_report_area.setText("Welcome to Code Statistics Project.\nYou can drag or choose .java, .c, .h, .cpp, .m or .swift or folder contain those files to generate report.\n");
    }

    @FXML
    void setOnDragDropped(DragEvent event) {
        choose_btn.getScene().setCursor(Cursor.DEFAULT);
        Dragboard dragboard = event.getDragboard();
        boolean success = false;
        if(dragboard.hasFiles()) {
            File file = dragboard.getFiles().get(0);
            if(file.exists()) {
                if (isFileViald(file.getName())) {
                    String oldPath = file.getAbsolutePath();
                    try {
                        generateReportFromSingleFile(oldPath, file.getName());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if(file.isDirectory()) {
                    String oldPath = file.getAbsolutePath();
                    try {
                        generateReportsFromFolder(oldPath, file.getName());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            success = true;
        }
        event.setDropCompleted(success);

        event.consume();
    }

    @FXML
    void setOnDragEntered(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        File file = dragboard.getFiles().get(0);
        String fileName = file.getName();
        if(dragboard.hasFiles() && (isFileViald(fileName) || file.isDirectory())) {
            Image image = new Image("drag_in_cursor.png");

            Scene scene = choose_btn.getScene();
            scene.setCursor(new ImageCursor(image,
                    image.getWidth() / 2,
                    image.getHeight() /2));
        }
        event.consume();
    }

    @FXML
    void setOnDragExited(DragEvent event) {
        choose_btn.getScene().setCursor(Cursor.DEFAULT);
        event.consume();
    }

    @FXML
    void setOnDragOver(DragEvent event) {
        Dragboard dragboard = event.getDragboard();
        if(dragboard.hasFiles()) {
            event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        event.consume();
    }

    @FXML
    void choose(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose the file end with .java, .c, .h, .cpp, .m or .swift");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(".java, .c, .h, .cpp, .m, .swift", "*.java", ".c", ".h", ".cpp", ".m", ".swift")
        );
        File file = fileChooser.showOpenDialog(choose_btn.getScene().getWindow());
        if (file != null && file.exists() && isFileViald(file.getName())) {
            String oldPath = file.getAbsolutePath();
            try {
                generateReportFromSingleFile(oldPath, file.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void clear(ActionEvent event) {
        code_statistics_report_area.clear();
//        hasGenerateReport = false;
//        code_statistics_report_area.setText("Welcome to Code Statistics Project.\nYou can drag or choose .java or .c or folder contain those files to generate report.\n");
    }

    private void generateReportFromSingleFile(String path, String fileName) throws IOException {
        if(!hasGenerateReport) {
            hasGenerateReport = true;
            code_statistics_report_area.clear();
        }
        CodeReport report = CodeAnalyzer.generateReport(path, fileName);
        code_statistics_report_area.appendText(report.toString());
    }

    private void generateReportsFromFolder(String path, String folderName) throws IOException {
        if(!hasGenerateReport) {
            hasGenerateReport = true;
            code_statistics_report_area.clear();
        }
        List<CodeReport> reports = CodeAnalyzer.generateReport(path);
        code_statistics_report_area.appendText("Folder: " + folderName + "\n");
        code_statistics_report_area.appendText("************************" + "\n");
        int totalLineOfCode = 0;
        int totalLineOfComment = 0;
        if(reports.size() > 0) {
            for (CodeReport report : reports) {
                code_statistics_report_area.appendText(report.toString());
                totalLineOfCode += report.getLineOfCode();
                totalLineOfComment += report.getLineOfComments();
            }
        } else {
            code_statistics_report_area.appendText("There are not any source code files end with .java, .c, .h, .cpp, .m or .swift in this folder.\n");
        }
        code_statistics_report_area.appendText("************************" + "\n\n");
        code_statistics_report_area.appendText("Total lines of code: " + totalLineOfCode + "\n");
        code_statistics_report_area.appendText("Total lines of comments: " + totalLineOfComment + "\n");
        code_statistics_report_area.appendText("Total lines: " + (totalLineOfCode + totalLineOfComment) + "\n");
    }

    public static final boolean isFileViald(String fileName) {
        return fileName.endsWith(C_CODE_FILE_SUFFIX) ||
                fileName.endsWith(JAVA_CODE_FILE_SUFFIX) ||
                fileName.endsWith(CPP_CODE_FILE_SUFFIX) ||
                fileName.endsWith(H_CODE_FILE_SUFFIX) ||
                fileName.endsWith(OC_CODE_FILE_SUFFIX) ||
                fileName.endsWith(SWIFT_CODE_FILE_SUFFIX);
    }


}
