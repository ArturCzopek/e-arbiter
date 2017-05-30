package pl.cyganki.executor.code.docker;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
class FileSystemUtils {

    void saveFile(byte[] data, String name, String path) {
        String fileDest = path + "/" + name;

        File file = new File(fileDest);
        FileOutputStream fos = null;

        try {
            file.getParentFile().mkdirs();
            file.createNewFile();

            fos = new FileOutputStream(file);
            fos.write(data);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            if (fos != null) {
                try { fos.close(); } catch (IOException ignored) {}
            }
        }
    }

    void deleteDir(String path) {
        File dir = new File(path);
        String[] files = dir.list();

        if (files != null) {
            for (String file: files) {
                File currentFile = new File(dir.getPath(), file);
                currentFile.delete();
            }
        }

        dir.delete();
    }
}
