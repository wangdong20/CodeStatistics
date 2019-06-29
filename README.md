# CodeStatistics
This is CodeStatistics project from group 5 based on javafx. It can generate code statistics report from source code files. It supports source code files end with .java, .c, .h, .cpp, .m or .swift currently. Basically the code statistics report can tell you how many lines of code(except break lines) in your source code file, how many lines of comments in your source code file, the name of source code file, the total lines of code in your source code file. It does not only support generate report from single file but also support generate reports from folder like the root directory of you project.

How to use it
------
Run this project,
When you want to generate code statistics report from your single source code file. You can either drag the source code file or click choose button to select the source code file to generate the report and the report will be displayed on the UI.
When you want to generate code statistics reports from your project which may contain many sub-directory and other format files and the source code files. This program will search support source code files from project root directory, then generate the code statistics reports from this project you select. You can only drag the project folder to the drag file area, then the code statistics report will be displayed on the UI.

Running effect
------
### Generate code statistics report from single file
![](https://github.com/wangdong20/CodeStatistics/blob/master/screenshots/CodeStatisticsSingleFileSample.gif)
### Generate code statistics report from project directory
![](https://github.com/wangdong20/CodeStatistics/blob/master/screenshots/CodeStatisticsFolderFilesSample.gif)
