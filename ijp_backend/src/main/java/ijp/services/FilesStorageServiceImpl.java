package ijp.services;

import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

// Implementation class for FileStorageService interface
@Service
public class FilesStorageServiceImpl implements FilesStorageService {

	@Override
	public void save(MultipartFile file) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Resource load(String filename) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Stream<Path> loadAll() {
		// TODO Auto-generated method stub
		return null;
	}
	
//	private final String UPLOAD_DIR = new ClassPathResource("static/images/").getFile().getAbsolutePath();
//
//	public FilesStorageServiceImpl() throws IOException {
//	}
//
////	@Override
////	public void init() {
////		try {
////			Files.createDirectory(root);
////		} catch (IOException e) {
////			throw new RuntimeException("Could not initialize folder for upload!");
////		}
////	}
//
//	@Override
//	public void save(MultipartFile file) {
//		try {
//			Files.copy(file.getInputStream(), Paths.get(UPLOAD_DIR+File.separator+file.getOriginalFilename()), StandardCopyOption.REPLACE_EXISTING);
//		} catch (Exception e) {
//			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
//		}
//	}
//
//	@Override
//	public Resource load(String filename) {
//		try {
//			Path file = Paths.get(UPLOAD_DIR).resolve(filename);
//			Resource resource = new UrlResource(file.toUri());
//
//			if (resource.exists() || resource.isReadable()) {
//				return resource;
//			} else {
//				throw new RuntimeException("Could not read the file!");
//			}
//		} catch (MalformedURLException e) {
//			throw new RuntimeException("Error: " + e.getMessage());
//		}
//	}
//
////	@Override
////	public void deleteAll() {
////		FileSystemUtils.deleteRecursively(Paths.get(UPLOAD_DIR).toFile());
////	}
//
//	@Override
//	public Stream<Path> loadAll() {
//		try {
//			return Files.walk(Paths.get(UPLOAD_DIR), 1).filter(path -> !path.equals(Paths.get(UPLOAD_DIR))).map(Paths.get(UPLOAD_DIR)::relativize);
//		} catch (IOException e) {
//			throw new RuntimeException("Could not load the files!");
//		}
//	}
//	
//	/* File Upload to Database */
	
	
}