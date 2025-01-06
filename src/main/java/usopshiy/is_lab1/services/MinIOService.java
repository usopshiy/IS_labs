package usopshiy.is_lab1.services;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Stateless;
import org.primefaces.model.file.UploadedFile;

import javax.naming.ServiceUnavailableException;
import java.io.InputStream;
import java.net.ConnectException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Stateless
public class MinIOService {

    private MinioClient minioClient;

    private final String bucket = "multiadd";

    @PostConstruct
    public void init() throws Exception {
        try {
            minioClient =  MinioClient.builder()
                    .endpoint("http://127.0.0.1:9000")
                    .credentials("P5jreIeybXp8LNYDUDyR", "jaAEuv1cCm9l5YrL0AAY0XROpVldN959V2Mw6Hcu")
                    .build();
            bucket();
        } catch (ConnectException e) {
            throw new ServiceUnavailableException("MinIO unavailable: try again later");
        } catch (Exception e) {
            throw e;
        }
    }

    public void bucket() throws Exception {
        try {
            boolean isExist = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket).build());
            if (!isExist) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket).build());
            }
        } catch (ConnectException e) {
            throw new ServiceUnavailableException("MinIO unavailable: try again later");
        } catch (Exception e) {
            throw e;
        }
    }

    public void uploadFile(UploadedFile file, String username) throws Exception{
        try {
            init();
            InputStream inputStream = file.getInputStream();

            DateTimeFormatter formater = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
            String key = username + "-" + LocalDateTime.now().format(formater) + "-" + file.getFileName();
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucket)
                    .object(key)
                    .stream(inputStream, file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());
        } catch (Exception e) {
            System.out.println(e);
            throw e;
        }
    }
}
