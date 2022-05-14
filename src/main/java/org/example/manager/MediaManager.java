package org.example.manager;

import org.example.dto.response.MediaUploadResponseDTO;
import org.springframework.stereotype.Component;


import org.apache.tika.Tika;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class MediaManager {
    private Tika tika = new Tika();
    public MediaManager() throws IOException{
        Files.createDirectories(Paths.get("media"));
    }
    public MediaUploadResponseDTO upload(byte[] data) throws IOException {
        String name = UUID.randomUUID().toString();

        String mime = tika.detect(data);
        name = withExtension(name, mime);
        Path path = path(name);
        Files.write(path, data);
        return new MediaUploadResponseDTO(name);
    }

    private String withExtension(String name, String mime) {
        if(mime.equals("image/png")){
            return name + ".png";
        }
        return name;
    }
    private Path path (String name){
        return Paths.get("media", name);
    }
}


