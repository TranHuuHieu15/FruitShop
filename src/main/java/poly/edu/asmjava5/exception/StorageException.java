package poly.edu.asmjava5.exception;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

public class StorageException extends RuntimeException{
    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Exception e) {
        super(message, e);
    }
}
