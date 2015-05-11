package sample;

import javafx.scene.control.TextArea;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
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
        ZipOutputStream zos =
                createZipOutputStream("D://" + name + ".zip");
        String[] sDirList = file.list();
        int i;
        try {
            for (i = 0; i < sDirList.length; i++) {
                File f1 = new File(
                        file.getAbsolutePath() + File.separator + sDirList[i]);
                l.info("found file " + f1.getAbsolutePath() );

                if (f1.isFile()) {
                    addFileToZip(zos, file.getAbsolutePath() + File.separator,
                            sDirList[i], textArea);
                }
            }
            zos.close();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }

    }

    private static ZipOutputStream createZipOutputStream(String string) {
        File tempfile;
        ZipOutputStream zos = null;
        tempfile = new File(string);
        try {
            zos = new ZipOutputStream(
                    new FileOutputStream(tempfile));
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        }
        zos.setLevel(
                Deflater.DEFAULT_COMPRESSION);

        return zos;
    }

    static void addFileToZip(ZipOutputStream zos,
                             String szPath, String szName, TextArea textArea) throws IOException {

        textArea.appendText(szPath + szName  + "\n");
        ZipEntry ze;
        ze = new ZipEntry(szName);
        zos.putNextEntry(ze);
        FileInputStream fis =
                new FileInputStream(szPath + szName);

        byte[] buf = new byte[8000];
        int nLength;
        while (true) {
            nLength = fis.read(buf);
            if (nLength < 0)
                break;
            zos.write(buf, 0, nLength);
        }
        fis.close();
        zos.closeEntry();
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
