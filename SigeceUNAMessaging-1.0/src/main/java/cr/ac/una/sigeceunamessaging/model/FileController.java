
package cr.ac.una.sigeceunamessaging.model;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class FileController {
    public Byte[] convertFileToBinary(File file) throws IOException {
        byte[] fileBytes = Files.readAllBytes(file.toPath());
        Byte[] binaryData = new Byte[fileBytes.length];

        for (int i = 0; i < fileBytes.length; i++) {
            binaryData[i] = fileBytes[i];
        }

        return binaryData;
    }
    
    public String getFileName(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(0, dotIndex);
    }
    
    public String getFileExtension(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }
    
    public void writeBinaryDataToFile(File file, Byte[] binaryData) throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
            byte[] byteArray = new byte[binaryData.length];
            for (int i = 0; i < binaryData.length; i++) {
                byteArray[i] = binaryData[i];
            }
            fileOutputStream.write(byteArray);
        }
    }
}
