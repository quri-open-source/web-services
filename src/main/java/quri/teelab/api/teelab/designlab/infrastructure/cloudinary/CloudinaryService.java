package quri.teelab.api.teelab.designlab.infrastructure.cloudinary;

import java.util.Optional;

public interface CloudinaryService {

    Optional<String> uploadImage(String fileName);


}
