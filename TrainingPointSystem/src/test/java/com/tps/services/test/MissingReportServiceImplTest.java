package com.tps.services.test;

import com.tps.pojo.MissingReport;
import com.tps.pojo.Mission;
import com.tps.pojo.User;
import com.tps.repositories.MissingReportRepository;
import com.tps.services.MissionService;
import com.tps.services.UserService;
import com.tps.services.impl.MissingReportServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MissingReportServiceImplTest {

    @Mock
    private MissingReportRepository missingReportRepository;

    @Mock
    private MissionService missionService;

    @Mock
    private UserService userService;

    @InjectMocks
    private MissingReportServiceImpl missingReportService;

    @Test
    void getMissingById() {
        int missingReportId = 1;
        MissingReport expectedMissingReport  = new MissingReport();

        when(missingReportRepository.getMissingById(missingReportId)).thenReturn(expectedMissingReport);

        MissingReport actualMissingReport = missingReportService.getMissingById(missingReportId);

        assertEquals(expectedMissingReport, actualMissingReport);
        verify(missingReportRepository).getMissingById(missingReportId);
    }

    @Test
    void getMissingByStudentMission() {
        int studentId = 1;
        int missionId = 1;
        MissingReport expectedMissingReport = new MissingReport(); // Create expected missing report

        when(missingReportRepository.getMissingByStudentMission(studentId, missionId)).thenReturn(expectedMissingReport);

        MissingReport actualMissingReport = missingReportService.getMissingByStudentMission(studentId, missionId);

        assertEquals(expectedMissingReport, actualMissingReport);
        verify(missingReportRepository).getMissingByStudentMission(studentId, missionId);

    }

    @Test
    void updateMissingReport() {
        MissingReport missingReportToUpdate = new MissingReport(); // Prepare missing report to update

        missingReportService.updateMissingReport(missingReportToUpdate);

        verify(missingReportRepository).updateMissingReport(missingReportToUpdate);

    }

    @Test
    void getMissionReportByFaculty() {
    }

    @Test
    void addMissingReport() {
        int studentId = 1;
        int missionId = 1;
        Map<String, String> params = Collections.singletonMap("description", "Test description");
        MissingReport expectedMissingReport = new MissingReport(); // Expected added missing report

        when(missionService.getMissionById(missionId)).thenReturn(new Mission());
        when(userService.getUserById(studentId)).thenReturn(new User());
        when(missingReportRepository.addMissingReport(any(MissingReport.class))).thenReturn(expectedMissingReport);

        MissingReport actualMissingReport = missingReportService.addMissingReport(studentId, missionId, params);

        assertEquals(expectedMissingReport, actualMissingReport);
        verify(missionService).getMissionById(missionId);
        verify(userService).getUserById(studentId);
        verify(missingReportRepository).addMissingReport(any(MissingReport.class));
    }

    @Test
    void uploadMissingImages() {
        List<MultipartFile> files = Collections.singletonList(mock(MultipartFile.class));
        int missingId = 1;

        missingReportService.uploadMissingImages(files, missingId);

        verify(missingReportRepository).uploadMissingImages(files, missingId);
    }

    @Test
    void getMissingReportByStudentId() {
        int studentId = 1;
        int periodId = 1;
        List<Object[]> expectedReports = Collections.singletonList(new Object[0]); // Create expected reports

        when(missingReportRepository.getMissingReportByStudentId(studentId, periodId)).thenReturn(expectedReports);

        List<Object[]> actualReports = missingReportService.getMissingReportByStudentId(studentId, periodId);

        assertEquals(expectedReports, actualReports);
        verify(missingReportRepository).getMissingReportByStudentId(studentId, periodId);

    }
}