package com.forum.forum.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Bucket;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * @author ：Zack
 * @date ：Created in 2020/2/28 16:22
 */
@Service
public class UploadService {
    public String uploadObject(MultipartFile uploadFile) throws IOException {
        FileInputStream serviceAccount =
                new FileInputStream("service-account-file.json");
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setStorageBucket("spring-forum-1cec4.appspot.com")
                .setConnectTimeout(60)
                .build();
        FirebaseApp app = FirebaseApp.initializeApp(options);
        StorageClient storageClient = StorageClient.getInstance(app);
        String blobString = "images/" + uploadFile.getOriginalFilename();
        URL url= storageClient.bucket().create(blobString, uploadFile.getBytes(), String.valueOf(Bucket.BlobWriteOption.userProject("spring-forum-1cec4")))
                .signUrl(365, TimeUnit.DAYS);
        serviceAccount.close();
        app.delete();
        return url.toString();
    }
}
