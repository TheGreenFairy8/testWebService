package webservice;

import java.io.*;
import java.util.List;
import java.awt.*;

public class HtmlFunctions {

    void createHtmlPage(List<Test> tests) {

        try {
            StringBuilder htmlStringBuilder = new StringBuilder();
            htmlStringBuilder.append("<html><head><title>Test table</title></head>");
            htmlStringBuilder.append("<body>");

            createTable(htmlStringBuilder, tests);

            htmlStringBuilder.append("</body></html>");

            //write html string content to a file
            String html = "test_table.html";

            writeToFile(htmlStringBuilder.toString(), html);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createTable(StringBuilder htmlStringBuilder, List<Test> tests) {
        htmlStringBuilder.append(" <table border=\"1\" width=\"100%\" cellpadding=\"5\">");
        htmlStringBuilder.append("<tr>");
        htmlStringBuilder.append("<th>Start time</th> <th>Test</th><th>End time</th><th>Progress</th>");
        htmlStringBuilder.append("</tr>");
        for (int i = 0; i < tests.size(); i++) {
            htmlStringBuilder.append("<tr>");
            try {
                htmlStringBuilder.append("<td>" + tests.get(i).getBeginTime().getTime().toString() + "</td>");
                htmlStringBuilder.append("<td>" + tests.get(i).getName() + "</td>");
                htmlStringBuilder.append("<td>" + tests.get(i).getEndTime().getTime().toString() + "</td>");

            } catch (NullPointerException e) {
                htmlStringBuilder.append("<td></td>");
            }
            htmlStringBuilder.append("<td>");
            int progressValue = calculateProgress();
            htmlStringBuilder.append("<progress max=\"100\" value=\"" + progressValue + "\">\n</progress>");
            htmlStringBuilder.append("</td");
            htmlStringBuilder.append("</tr>");
        }
    }

    private int calculateProgress() {
        // TO DO something else
        return 0;
    }

    private void writeToFile(String fileContent, String fileName) throws IOException {

        String projectPath = System.getProperty("user.dir");

        String tempFile = projectPath + File.separator + fileName;

        System.out.println(tempFile);
        File file = new File(tempFile);
        // if file does exists, then delete and create a new file
        if (file.exists()) {
            try {
                File newFileName = new File(projectPath + File.separator + "backup_" + fileName);
                file.renameTo(newFileName);
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //write to file with OutputStreamWriter
        OutputStream outputStream = new FileOutputStream(file.getAbsoluteFile());
        Writer writer = new OutputStreamWriter(outputStream, "UTF-16");
        writer.write(fileContent);
        writer.close();
        openPage(tempFile);
    }

    private void openPage(String filePath) {
        Desktop desktop = Desktop.getDesktop();
        File file = new File(filePath);

        try {
            desktop.browse(file.toURI());
        } catch (IOException e) {
             e.printStackTrace();
        }
    }

}
