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
        code_statistics_report_area.setText("Welcome to Code Statistics Project.\nYou can drag or choose .java or .c or folder contain those files to generate report.\n");
    }

    @FXML
    void setOnDragDropped(DragEvent event) {
        choose_btn.getScene().setCursor(Cursor.DEFAULT);
        Dragboard dragboard = event.getDragboard();
        boolean success = false;
        if(dragboard.hasFiles()) {
            File file = dragboard.getFiles().get(0);
            if(file.exists()) {
                if ((file.getName().endsWith(JAVA_CODE_FILE_SUFFIX)
                        || file.getName().endsWith(C_CODE_FILE_SUFFIX))) {
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
        String fileName = dragboard.getFiles().get(0).getName();
        if(dragboard.hasFiles() && (fileName.endsWith(JAVA_CODE_FILE_SUFFIX)
            || fileName.endsWith(C_CODE_FILE_SUFFIX))) {
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
        fileChooser.setTitle("Choose the file end with .java or .c");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter(".java or .c", "*.java", ".c")
        );
        File file = fileChooser.showOpenDialog(choose_btn.getScene().getWindow());
        if (file != null && file.exists() && (file.getName().endsWith(JAVA_CODE_FILE_SUFFIX) ||
                file.getName().endsWith(C_CODE_FILE_SUFFIX))) {
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
        if(reports.size() > 0) {
            for (CodeReport report : reports) {
                code_statistics_report_area.appendText(report.toString());
            }
        } else {
            code_statistics_report_area.appendText("There are not any source code files end with .java or .c in this folder.\n");
        }
        code_statistics_report_area.appendText("************************" + "\n\n");
    }

}
