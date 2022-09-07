package com.github.niefy.modules.oss.cloud;

import com.github.niefy.common.exception.RRException;
import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import io.minio.errors.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * 阿里云存储
 * @author Mark sunlightcs@gmail.com
 */
public class MinioAbstractCloudStorageService extends AbstractCloudStorageService {
    private MinioClient minioClient;

    public MinioAbstractCloudStorageService(CloudStorageConfig config) {
        this.config = config;

        //初始化
        init();
    }

    private void init() {
        try {
            minioClient =  new MinioClient(config.getMinioEndPoint(), config.getMinioAccessKeyId(), config.getMinioAccessKeySecret());
        } catch (InvalidEndpointException e) {
            e.printStackTrace();
        } catch (InvalidPortException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String upload(byte[] data, String path) {
        return upload(new ByteArrayInputStream(data), path);
    }

    @Override
    public String upload(InputStream inputStream, String path) {
        try {
            PutObjectOptions putObjectOptions = new PutObjectOptions(inputStream.available(), 0);
            minioClient.putObject(config.getMinioBucketName(), path, inputStream, putObjectOptions);
            return config.getMinioDomain() + "/" + path;
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (InvalidBucketNameException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String uploadSuffix(byte[] data, String suffix) {
        return upload(data, getPath(config.getAliyunPrefix(), suffix));
    }

    @Override
    public String uploadSuffix(InputStream inputStream, String suffix) {
        return upload(inputStream, getPath(config.getAliyunPrefix(), suffix));
    }
}
