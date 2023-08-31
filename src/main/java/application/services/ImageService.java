package application.services;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import application.payloads.ImagePayload;

@Service
public class ImageService {

	private final Cloudinary cloudinary;

	@Autowired
	public ImageService(Cloudinary cloudinary) {
		this.cloudinary = cloudinary;
	}

	public ImagePayload uploadImage(MultipartFile image) throws IOException {

		Map<String, Object> params = ObjectUtils.asMap("folder", "your_folder", "overwrite", true, "resource_type",
				"auto");

		Map risp = cloudinary.uploader().upload(image.getBytes(), params);
		return new ImagePayload(risp.get("secure_url").toString(), risp.get("public_id").toString());

	}

	public void deleteImage(String publicId) {
		try {
			cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
