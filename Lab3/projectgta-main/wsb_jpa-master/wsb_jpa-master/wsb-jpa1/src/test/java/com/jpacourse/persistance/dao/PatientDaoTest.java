package com.jpacourse.persistance.dao;
import com.jpacourse.persistance.entity.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.jpacourse.persistance.enums.Specialization.SURGEON;

@SpringBootTest
@Transactional
public class PatientDaoTest
{

    @Autowired
    private PatientDao patientDao;

    @Autowired
    private DoctorDao doctorDao;

    @Autowired
    private AddressDao addressDao;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private VisitDao visitDao;

    @Autowired
    private MedicalTreatmentDao medicalTreatmentDao;

    @BeforeEach
    public void cleanup() {
        medicalTreatmentDao.deleteAll();
        visitDao.deleteAll();
        patientDao.deleteAll();
        doctorDao.deleteAll();
        addressDao.deleteAll();
    }

    @Test
    public void shouldAddVisitToPatient() {
        // given
        AddressEntity address = new AddressEntity();
        address.setCity("Wrocław");
        address.setAddressLine1("Testowa");
        address.setAddressLine2("123");
        address.setPostalCode("00-111");
        address = addressDao.save(address);

        DoctorEntity doctor = new DoctorEntity();
        doctor.setFirstName("Jan");
        doctor.setLastName("Kowalski");
        doctor.setEmail("jankowalski@doctor.pl");
        doctor.setTelephoneNumber("123456789");
        doctor.setDoctorNumber("DOC-01");
        doctor.setSpecialization(SURGEON);
        doctor.setAddress(address);
        doctor = doctorDao.save(doctor);

        PatientEntity patient = new PatientEntity();
        patient.setPesel(12345678901L);
        patient.setFirstName("Anna");
        patient.setLastName("Nowak");
        patient.setTelephoneNumber("987654321");
        patient.setEmail("anna.nowak@example.com");
        patient.setPatientNumber("PAT-456");
        patient.setDateOfBirth(LocalDate.of(1985, 5, 15));
        patient.setAddress(address);

        patient = patientDao.save(patient);

        LocalDateTime visitTime = LocalDateTime.now().withNano(0);
        String description = "Badanie";

        // when
        patientDao.addVisitToPatient(patient.getId(), doctor.getId(), visitTime, description);
        entityManager.flush();
        entityManager.clear();

        // then
        PatientEntity updated = patientDao.findOne(patient.getId());
        List<VisitEntity> visits = updated.getVisits();

        Assertions.assertEquals(1, visits.size());

        VisitEntity visit = visits.get(0);
        Assertions.assertEquals(description, visit.getDescription());
        Assertions.assertEquals(visitTime, visit.getTime().withNano(0));
        Assertions.assertEquals(doctor.getId(), visit.getDoctor().getId());
        Assertions.assertEquals(patient.getId(), visit.getPatient().getId());
    }
}
