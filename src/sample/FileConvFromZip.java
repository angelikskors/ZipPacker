package sample;

import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;

import java.io.*;
import java.util.Enumeration;
import java.util.Vector;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileConvFromZip {
    static final ProgressBar pb = new ProgressBar(0);
    static final ProgressIndicator pi = new ProgressIndicator(0);
    private static final Logger l = Logger.getLogger(FileConvFromZip.class.getSimpleName());
    int sum = 0;
    private int i;
    private File file;
    FileConvFromZip() throws IOException {
        this.file = new FileConvToZip("").openFile("ZIP");
        new NameScreen().createWindowProgress(pb, pi);

    }

    static String slash2sep(String src) {
        int i;
        char[] chDst = new char[src.length()];
        String dst;

        for (i = 0; i < src.length(); i++) {
            if (src.charAt(i) == '/')
                chDst[i] = File.separatorChar;
            else
                chDst[i] = src.charAt(i);
        }
        dst = new String(chDst);
        return dst;
    }

    public void unpackDir(String fileTo, TextArea textArea) {
        fileTo = "D://" + fileTo;

        final String finalFileTo = fileTo;
        new Thread(new Runnable() {
            @Override
            public void run() {


                ZipFile zf;
                Vector zipEntries = new Vector();

                try {
                    zf = new ZipFile(file.getAbsoluteFile());
                    Enumeration en = zf.entries();

                    while (en.hasMoreElements()) {
                        zipEntries.addElement((ZipEntry) en.nextElement());
                    }
                    for (i = 0; i < zipEntries.size(); i++) {
                        sum++;
                    }
                    System.out.println(sum);
                    for (i = 0; i < zipEntries.size(); i++) {
                        ZipEntry ze = (ZipEntry) zipEntries.elementAt(i);


                        extractFromZip(file.getAbsoluteFile().toString(), finalFileTo, textArea, ze.getName(), zf,
                                ze);
                        Thread.currentThread().sleep(100);
                        new Thread(new Runnable() {

                            @Override
                            public void run() {
                                double k = 1;
                                k = (double) (i) / sum;
                                System.out.println("k" + k);
                                pb.setProgress(k);
                                pi.setProgress(k);
                            }
                        }).start();
                    }

                    zf.close();

                } catch (Exception ex) {
                    System.out.println(ex.toString());
                }
            }
        }).start();
    }

    void extractFromZip(String zipFilePath, String fileTo, TextArea textArea,
                        String szName, ZipFile zf, ZipEntry ze) {
        if (ze.isDirectory())
            return;

        String nameSeparNorm = slash2sep(szName);

        String nameClean;

        if (nameSeparNorm.lastIndexOf(File.separator) != -1) {
            nameClean = nameSeparNorm.substring(0,
                    nameSeparNorm.lastIndexOf(File.separator));
        } else
            nameClean = "";


        textArea.appendText(nameSeparNorm + "\n");

        try {
            File newDir = new File(fileTo + File.separator + nameClean);
            l.info(newDir.getAbsolutePath());


            newDir.mkdirs();

            FileOutputStream fos = new FileOutputStream(fileTo
                    + File.separator + nameSeparNorm);

            InputStream is = zf.getInputStream(ze);
            byte[] buf = new byte[1024];

            int nLength;

            while (true) {
                try {
                    nLength = is.read(buf);
                } catch (EOFException ex) {
                    break;
                }

                if (nLength < 0)
                    break;
                fos.write(buf, 0, nLength);
            }

            is.close();
            fos.close();
        } catch (Exception ex) {
            System.out.println(ex.toString());
            System.exit(0);
        }
    }


}


