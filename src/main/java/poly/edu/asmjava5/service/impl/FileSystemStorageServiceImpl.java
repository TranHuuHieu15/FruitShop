package poly.edu.asmjava5.service.impl;

import org.apache.commons.io.FilenameUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import poly.edu.asmjava5.config.StorageProperties;
import poly.edu.asmjava5.exception.StorageException;
import poly.edu.asmjava5.exception.StorageFileNotFoundException;
import poly.edu.asmjava5.service.StorageService;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileSystemStorageServiceImpl implements StorageService {
    private final Path rootLocation; // xác định đường dẫn gốc để lưu thông tin file hình

    //tạo file lưu trữ dựa vào id được truyền vào
    @Override
    public String getStorageFilename(MultipartFile file, String id){
        String ext = FilenameUtils.getExtension(file.getOriginalFilename());
        return "p" + id + "." + ext;
    }

    //truyền các thông tin cấu hình cho file lưu trữ
    public FileSystemStorageServiceImpl(StorageProperties properties) {
        this.rootLocation = Paths.get(properties.getLocation());
    }

    //để lưu file
    @Override
    public void store(MultipartFile file, String storedFilename) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file");
            }
            Path destinationFile = this.rootLocation.resolve(Paths.get(storedFilename))
                    .normalize().toAbsolutePath();
            if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
                throw new StorageException("Can't store file outside current directory");
            }
            try(InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING); // copy xong thay thay thế file hiện tại
            }
        } catch (Exception e) {
            throw new StorageException("Failed to store file", e);
        }
    }

    // để đọc(nạp) file
    @Override
    public Resource loadAsResource(String fileName) {
        try {
            Path file = load(fileName);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            throw new StorageFileNotFoundException("Could not read file: " + fileName);
        } catch (Exception e) {
            throw new StorageFileNotFoundException("Could not read file: " + fileName);
        }
    }

    @Override
    public Path load(String fileName){
        return rootLocation.resolve(fileName);
    }

    //phương thức để xóa file
    @Override
    public void delete(String storedFilename) throws IOException {
        Path destinationFile = rootLocation.resolve(Paths.get(storedFilename).normalize().toAbsolutePath());
        Files.delete(destinationFile);
    }

    //khởi tạo các thư mục
    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
            System.out.println(rootLocation.toString());
        } catch (Exception e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
