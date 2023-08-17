package poly.edu.asmjava5.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public interface StorageService {
    //tạo file lưu trữ dựa vào id được truyền vào
    String getStorageFilename(MultipartFile file, String id);

    //để lưu file
    void store(MultipartFile file, String storedFilename);

    // để đọc(nạp) file
    Resource loadAsResource(String fileName);

    Path load(String fileName);

    //phương thức để xóa file
    void delete(String storedFilename) throws IOException;

    //khởi tạo các thư mục
    void init();
}
