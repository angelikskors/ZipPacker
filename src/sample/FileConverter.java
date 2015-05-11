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

public class FileConverter {
    private static final Logger l = Logger.getLogger(FileConverter.class.getSimpleName());
    final private String DIR = "DIR";
    final private String ZIP = "ZIP";
    private String fileFrom;
    private String fileTo;
    private File file;
    private TextArea textArea;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getFileFrom() {
        return fileFrom;
    }

    public void setFileFrom(String fileFrom) {
        this.fileFrom = fileFrom;
    }

    public String getFileTo() {
        return fileTo;
    }

    public void setFileTo(String fileTo) {
        this.fileTo = fileTo;
    }

    FileConverter(String fileFrom, String fileTo) {
        setFileFrom(fileFrom);
        setFileTo(fileTo);

    }

    FileConverter() {

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
                    if(zos != null){
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

    private void packFile(ZipOutputStream zos, File file, TextArea textArea){
        if(file.isFile()){
            addFileToZip(zos, file, textArea);
        }else if(file.isDirectory()){
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

    private String correctFilePathInZip(File input){
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
            while ((readLength = fis.read(buffer))!=-1){
                outputStream.write(buffer, 0, readLength);
            }
            outputStream.closeEntry();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private File openFile(String files) {
        Stage stage = new Stage();
        DirectoryChooser directoryChooser = new DirectoryChooser();

        directoryChooser.setTitle("Open Resource File");
        String path;

        if (files.equals(DIR)) {
            file = directoryChooser.showDialog(stage);
        } else {
        }


        if (file != null) {
            return file;
        }
        return null;

    }
}
