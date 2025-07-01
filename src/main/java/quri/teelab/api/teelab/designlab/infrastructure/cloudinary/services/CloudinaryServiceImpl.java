package quri.teelab.api.teelab.designlab.infrastructure.cloudinary.services;


import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Service;
import quri.teelab.api.teelab.designlab.infrastructure.cloudinary.CloudinaryService;

import java.util.Map;
import java.util.Optional;

@Service
public class CloudinaryServiceImpl implements CloudinaryService {

    private Cloudinary cloudinary;

    public CloudinaryServiceImpl() {
        Dotenv dotenv = Dotenv.load();
        Cloudinary cloudinary = new Cloudinary(dotenv.get("CLOUDINARY_URL"));
        System.out.println(cloudinary.config.cloudName);
    }


    @Override
    public Optional<String> uploadImage(String fileName) {
        // Upload the image

        try {
            Map params1 = ObjectUtils.asMap(
                    "use_filename", true,
                    "unique_filename", false,
                    "overwrite", true
            );

            cloudinary.uploader().upload("https://cloudinary-devs.github.io/cld-docs-assets/assets/images/coffee_cup.jpg", params1);

            return Optional.empty();
        } catch (Exception e) {
            System.err.println("Error uploading image to Cloudinary: " + e.getMessage());
            return Optional.empty();
        }
    }
}
