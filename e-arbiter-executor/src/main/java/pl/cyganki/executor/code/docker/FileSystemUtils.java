package pl.cyganki.executor.code.docker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
@Slf4j
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
            log.error(ioe.getMessage());
        } finally {
            if (fos != null) {
                try { fos.close(); } catch (IOException ignored) {
                    // do nothing
                }
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
