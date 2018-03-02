package framework.utils;

import framework.reporter.ReportException;
import org.apache.commons.io.FilenameUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileUtils {

    /**
     * Reads file into a string. <b><font color='red'>pay attention - you need JDK 1.8 to compile this code</font><b/>
     *
     * @param filePath path to file
     * @return content of file as string
     * @throws IOException in case of file interactions problem
     */
    public static String readFileContent(String filePath) throws IOException {
        StringBuilder sb = new StringBuilder();
        Files.lines(Paths.get(filePath)).forEach(sb::append);
        return sb.toString();
    }

    public static void writeStringToFile(String string, String filePath, boolean append) {
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        PrintWriter fw = null;
        try {
            fw = new PrintWriter(new BufferedWriter(new FileWriter(file, append)));
            fw.print(string);
        } catch (IOException e) {
            throw new ReportException("Unable to work with file " + filePath, e);
        } finally {
            assert fw != null;
            fw.close();
        }
    }

    public static void writeStringsToFile(List<String> strings, String filePath) {
        writeStringsToFile(strings, filePath, false);
    }

    public static void writeStringsToFile(List<String> strings, String filePath, boolean append) {
        File file = new File(filePath);
        file.getParentFile().mkdirs();
        PrintWriter fw = null;
        try {
            fw = new PrintWriter(new BufferedWriter(new FileWriter(file, append)));
            for (String fileLine : strings)
                fw.println(fileLine);
        } catch (IOException e) {
            throw new ReportException("Unable to work with file " + filePath, e);
        } finally {
            assert fw != null;
            fw.close();
        }
    }

    public static BufferedImage readImageFromFile(String filePath) throws IOException {
        return ImageIO.read(new File(filePath));
    }

    public static void writeImageToFile(BufferedImage src, String filePath) throws IOException {
        File outputFile = new File(filePath);
        ImageIO.write(src, FilenameUtils.getExtension(filePath), outputFile);
    }
}
