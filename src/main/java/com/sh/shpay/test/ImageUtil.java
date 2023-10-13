package com.sh.shpay.test;


import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Slf4j
public class ImageUtil {
    static String uploadPath = "src/main/resources/static/ImageFile/";
    private Thumbnailator thumbnailator;


/*    public enum Uses {
        SHOP_IMAGE(800),
        SHOP_THUMBNAIL(320),
        ;

        private final int width;


        Uses(int width) {
            this.width = width;
        }

        public int getWidth() {
            return width;
        }
    }*/



    public static void uploadImageCompressed(File file) throws IOException {
        // get fileName at originalFileName
        String originalFileName = file.getName().substring(0, file.getName().lastIndexOf("."));
        String fileName = "thumb_" + originalFileName + ".jpg";

        log.info("fileName : {}", fileName);

        // thumbnailator를 이용한 이미지 압축
        // 최대 width는 800px

        // 파일의 원본 이미지를 thumbnailator를 이용하여 800픽셀 width로 압축

        Thumbnails.of(file)
                .size(1000, 1000)
                .outputFormat("jpg")
                .outputQuality(0.7)
                .toFile(uploadPath + "/" + fileName);
    }

    public static File upload(MultipartFile file) {
        try {
            String uuid = UUID.randomUUID().toString();
            // get 확장자 with dot
            String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
            String fileName = uuid + extension;
            Path imgPath = Paths.get(uploadPath + "/" + fileName);
            File dest = new File(imgPath.toUri());
            file.transferTo(dest);

            return dest;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResponseEntity<Resource> display(String filename) {
        Resource resource = new FileSystemResource(uploadPath  + filename);
        if(!resource.exists())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        HttpHeaders header = new HttpHeaders();
        Path filePath = null;
        try{
            filePath = Paths.get(uploadPath + filename);
            header.add("Content-type", Files.probeContentType(filePath));
        }catch(IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(resource, header, HttpStatus.OK);
    }

    public static void removeImage(String originalFileName) {
        Path imgPath = Paths.get(uploadPath + "/" + originalFileName);
        File dest = new File(imgPath.toUri());
        dest.delete();
    }

    public static ResponseEntity<Resource> displayThumb(String fileName) {
        // get rid of extension
        String originalFileName = fileName.substring(0, fileName.lastIndexOf("."));
        String thumbFileName = "thumb_" + originalFileName + ".jpg";
        Resource resource = new FileSystemResource(uploadPath  + thumbFileName);
        HttpHeaders header = new HttpHeaders();
        Path filePath = null;
        try{
            filePath = Paths.get(uploadPath + thumbFileName);
            header.add("Content-type", Files.probeContentType(filePath));
        }catch(IOException e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(resource, header, HttpStatus.OK);
    }
}