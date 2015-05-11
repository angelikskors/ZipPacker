package sample;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.Deflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileConvToZip {
    private static final Logger l = Logger.getLogger(FileConvToZip.class.getSimpleName());
    final private String DIR = "DIR";
    final private String ZIP = "ZIP";

    private File file;


    FileConvToZip(String forFile) {
    }

    FileConvToZip() {

        this.file = openFile("DIR");

    }

    public void packingDir(String name, TextArea textArea) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ZipOutputStream zos =
                        createZipOutputStream("D://" + name + ".zip");
                try {
                    packFile(zos, file, textArea);
                } finally {
                    if (zos != null) {
                        try {
                            zos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        }).start();
    }

    private void packFile(ZipOutputStream zos, File file, TextArea textArea) {
        if (file.isFile()) {
            addFileToZip(zos, file, textArea);
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                packFile(zos, f, textArea);
            }
        }
    }

    private static ZipOutputStream createZipOutputStream(String string) {
        ZipOutputStream zos = null;
        File tempfile = new File(string);
        try {
            zos = new ZipOutputStream(
                    new FileOutputStream(tempfile));
            zos.setLevel(
                    Deflater.DEFAULT_COMPRESSION);
        } catch (FileNotFoundException e) {
            l.log(Level.SEVERE, "", e);
        }
        return zos;
    }

    private String correctFilePathInZip(File input) {
        String absolutePath = input.getAbsolutePath();
        String rootPath = file.getAbsolutePath();
        String filePath = absolutePath.substring(rootPath.length());
        return filePath;
    }

    void addFileToZip(ZipOutputStream outputStream, File input, TextArea textArea) {
        l.info(input.getAbsolutePath());
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                textArea.appendText(input.getAbsolutePath() + "\n");
            }
        });

        ZipEntry ze = new ZipEntry(correctFilePathInZip(input));
        FileInputStream fis = null;
        try {
            outputStream.putNextEntry(ze);
            fis = new FileInputStream(input);
            byte[] buffer = new byte[1024 * 8];
            int readLength = -1;
            while ((readLength = fis.read(buffer)) != -1) {
                outputStream.write(buffer, 0, readLength);
            }
            outputStream.closeEntry();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public File openFile(String files) {
        Stage stage = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();
        FileChooser fileChooser = new FileChooser();

        directoryChooser.setTitle("Open Resource File");


        if (files.equals(DIR)) {
            file = directoryChooser.showDialog(stage);
        } else if (files.equals(ZIP)) {
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("ZIP", "*.zip")

            );
            file = fileChooser.showOpenDialog(stage);

        }

        if (file != null) {
            return file;
        }
        return null;

    }
}
