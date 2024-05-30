package com.tps.components;

import com.tps.dto.FacultyDTO;
import com.tps.pojo.Faculty;
import org.springframework.stereotype.Component;

@Component
public class FacultyConverter {
    public FacultyDTO toDTO(Faculty faculty) {
        FacultyDTO facultyDTO = new FacultyDTO();
        facultyDTO.setId(faculty.getId());
        facultyDTO.setName(faculty.getName());
        return facultyDTO;
    }
}
