package quri.teelab.api.teelab.designlab.application.internal.queryservices;

import org.springframework.stereotype.Service;
import quri.teelab.api.teelab.designlab.domain.model.aggregates.Project;
import quri.teelab.api.teelab.designlab.domain.model.queries.GetAllProjectsByUserIdQuery;
import quri.teelab.api.teelab.designlab.domain.model.queries.GetProjectByIdQuery;
import quri.teelab.api.teelab.designlab.domain.model.queries.GetProjectDetailsForProductQuery;
import quri.teelab.api.teelab.designlab.domain.model.valueobjects.UserId;
import quri.teelab.api.teelab.designlab.domain.services.ProjectQueryService;
import quri.teelab.api.teelab.designlab.infrastructure.persistence.jpa.repositories.ProjectRepository;

import java.util.List;

@Service
public class ProjectQueryServiceImpl implements ProjectQueryService {
    private final ProjectRepository projectRepository;

    public ProjectQueryServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    public List<Project> handle(GetAllProjectsByUserIdQuery query) {
        // Validate if there is a Project With the given User ID
        if (!projectRepository.existsByUserId(query.userId())) {
            System.out.println("No projects found for user ID: " + query.userId());
            return List.of(); // Return an empty list if no projects are found
        }

        // Fetch all projects for the given User ID
        var projects = projectRepository.findAllByUserId(query.userId());
        if (projects.isEmpty()) {
            return List.of(); // Return an empty list if no projects are found
        }

        // Return the list of projects
        return projects;
    }

    public Project handle(GetProjectByIdQuery query) {
        // Validate if there is a Project With the given ID
        if (!projectRepository.existsById(query.projectId())) {
            System.out.println("Project with ID " + query.projectId() + " does not exist.");
            throw new IllegalArgumentException("Project with ID " + query.projectId() + " does not exist.");
        }

        // Fetch the project by ID
        var project = projectRepository.findById(query.projectId())
                .orElseThrow(() -> new IllegalArgumentException("Project with ID " + query.projectId() + " does not exist."));

        // Return the project
        return project;
    }

    public Project handle(GetProjectDetailsForProductQuery query) {
        // Validate if there is a Project With the given ID
        if (!projectRepository.existsById(query.projectId())) {
            System.out.println("Project with ID " + query.projectId() + " does not exist.");
            throw new IllegalArgumentException("Project with ID " + query.projectId() + " does not exist.");
        }

        // Fetch the project by ID (reusing the same logic as GetProjectByIdQuery)
        var project = projectRepository.findById(query.projectId())
                .orElseThrow(() -> new IllegalArgumentException("Project with ID " + query.projectId() + " does not exist."));

        // Return the project
        return project;
    }

}
