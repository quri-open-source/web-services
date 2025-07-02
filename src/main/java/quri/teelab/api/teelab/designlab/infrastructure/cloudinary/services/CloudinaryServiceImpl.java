package quri.teelab.api.teelab.designlab.infrastructure.cloudinary.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import quri.teelab.api.teelab.designlab.infrastructure.cloudinary.CloudinaryService;

import java.util.Map;
import java.util.Optional;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    private final Cloudinary cloudinary;

    public CloudinaryServiceImpl(@Value("${cloudinary.url}") String cloudinaryUrl) {
        this.cloudinary = new Cloudinary(cloudinaryUrl);
        System.out.println("Cloudinary initialized with cloud name: " + this.cloudinary.config.cloudName);
    }

    @Override
    public Optional<String> uploadImage(String fileName) {
        try {
            @SuppressWarnings("unchecked")
            Map<String, Object> params = ObjectUtils.asMap(
                    "use_filename", true,
                    "unique_filename", false,
                    "overwrite", true
            );

            @SuppressWarnings("unchecked")
            Map<String, Object> uploadResult = this.cloudinary.uploader().upload(
                    "https://cloudinary-devs.github.io/cld-docs-assets/assets/images/coffee_cup.jpg", 
                    params
            );

            String imageUrl = (String) uploadResult.get("secure_url");
            System.out.println("Image uploaded successfully: " + imageUrl);
            return Optional.ofNullable(imageUrl);

        } catch (Exception e) {
            System.err.println("Error uploading image to Cloudinary: " + e.getMessage());
            e.printStackTrace();
            return Optional.empty();
        }
    }
}
