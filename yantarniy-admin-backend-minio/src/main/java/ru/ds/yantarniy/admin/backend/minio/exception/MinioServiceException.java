package ru.ds.yantarniy.admin.backend.minio.exception;

public class MinioServiceException extends RuntimeException {

    public MinioServiceException() {
    }

    public MinioServiceException(String message) {
        super(message);
    }

    public MinioServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
