package quri.teelab.api.teelab.orderfulfillment.application.internal.commandservices;

import org.springframework.stereotype.Service;
import quri.teelab.api.teelab.orderfulfillment.domain.model.aggregates.Manufacturer;
import quri.teelab.api.teelab.orderfulfillment.domain.model.commands.CreateManufacturerCommand;
import quri.teelab.api.teelab.orderfulfillment.domain.model.valueobjects.UserId;
import quri.teelab.api.teelab.orderfulfillment.domain.services.ManufacturerCommandService;
import quri.teelab.api.teelab.orderfulfillment.infrastructure.persistence.jpa.repositories.ManufacturerRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class ManufacturerCommandServiceImpl implements ManufacturerCommandService {
    
    private final ManufacturerRepository manufacturerRepository;
    
    public ManufacturerCommandServiceImpl(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }
    
    @Override
    public Optional<Manufacturer> handle(CreateManufacturerCommand command) {
        // Check if manufacturer already exists for this user
        UserId userId = new UserId(UUID.fromString(command.userId()));
        if (manufacturerRepository.existsByUserId(userId)) {
            return Optional.empty(); // Manufacturer already exists for this user
        }
        
        var manufacturer = new Manufacturer(
                command.userId(),
                command.name(),
                command.address(),
                command.city(),
                command.country(),
                command.state(),
                command.zip()
        );
        
        try {
            manufacturerRepository.save(manufacturer);
            return Optional.of(manufacturer);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
