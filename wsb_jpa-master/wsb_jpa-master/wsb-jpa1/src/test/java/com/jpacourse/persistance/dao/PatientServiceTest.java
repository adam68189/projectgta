package com.jpacourse.persistance.dao;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.persistance.entity.*;
import com.jpacourse.persistance.enums.Specialization;
import com.jpacourse.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PatientServiceTest {

    @Autowired
    private PatientService patientService;

    @Autowired
    private PatientDao patientDao;

    @Autowired
    private DoctorDao doctorDao;

    @Autowired
    private VisitDao visitDao;

    @Autowired
    private AddressDao addressDao;

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

    @Transactional
    @Test
    public void testShouldRemovePatientButNotDoctors() {
        //adres
        AddressEntity address = new AddressEntity();
        address.setCity("Wrocław");
        address.setPostalCode("00-000");
        address.setAddressLine1("Grochowska");
        address.setAddressLine2("22");
        final AddressEntity savedAddress = addressDao.save(address);

        //doctor
        DoctorEntity doctor = new DoctorEntity();
        doctor.setFirstName("Jan");
        doctor.setLastName("Nowak");
        doctor.setDoctorNumber("DOC-01");
        doctor.setTelephoneNumber("123456789");
        doctor.setSpecialization(com.jpacourse.persistance.enums.Specialization.DERMATOLOGIST);
        doctor.setAddress(savedAddress);
        doctor = doctorDao.save(doctor);

        //pacjent
        PatientEntity patient = new PatientEntity();
        patient.setFirstName("Karol");
        patient.setLastName("Kowalski");
        patient.setPatientNumber("PAT-01");
        patient.setTelephoneNumber("987654321");
        patient.setDateOfBirth(LocalDate.of(1990, 1, 1));
        patient.setAddress(savedAddress);

        //wizyta
        VisitEntity visit1 = new VisitEntity();
        visit1.setDescription("Pierwsza wizyta");
        visit1.setTime(LocalDateTime.now());
        visit1.setDoctor(doctor);
        visit1.setPatient(patient);

        VisitEntity visit2 = new VisitEntity();
        visit2.setDescription("Druga wizyta");
        visit2.setTime(LocalDateTime.now().plusDays(1));
        visit2.setDoctor(doctor);
        visit2.setPatient(patient);

        patient.setVisits(List.of(visit1, visit2));
        patient = patientDao.save(patient);

        Long patientId = patient.getId();
        Long doctorId = doctor.getId();

        assertThat(visitDao.findAll()).isNotEmpty();
        assertThat(doctorDao.findOne(doctorId)).isNotNull();

        patientService.deletePatient(patientId);

        assertThat(patientDao.findOne(patientId)).isNull();
        assertThat(visitDao.findAll()
                .stream()
                .filter(v -> v.getDoctor().getId().equals(doctorId)))
                .isEmpty();

        assertThat(doctorDao.findOne(doctorId)).isNotNull();
    }

    @Transactional
    @Test
    public void testShouldReturnPatientTOW() {
        AddressEntity address = new AddressEntity();
        address.setCity("Wrocław");
        address.setPostalCode("00-001");
        address.setAddressLine1("Konwaliowa");
        address.setAddressLine2("19");
        addressDao.save(address);

        PatientEntity patient = new PatientEntity();
        patient.setPesel(12345678901L);
        patient.setFirstName("Anna");
        patient.setLastName("Nowak");
        patient.setTelephoneNumber("987654321");
        patient.setEmail("anna.nowak@example.com");
        patient.setPatientNumber("PAT-456");
        patient.setDateOfBirth(LocalDate.of(1985, 5, 15));
        patient.setAddress(address);

        Long patientId = patientDao.save(patient).getId();

        PatientTO patientTO = patientService.findById(patientId);

        assertThat(patientTO).isNotNull();

        assertThat(patientTO.getId()).isEqualTo(patientId);
        assertThat(patientTO.getPesel()).isEqualTo(12345678901L);
        assertThat(patientTO.getFirstName()).isEqualTo("Anna");
        assertThat(patientTO.getLastName()).isEqualTo("Nowak");
        assertThat(patientTO.getTelephoneNumber()).isEqualTo("987654321");

        assertThat(patientTO.getEmail()).isEqualTo("anna.nowak@example.com");
        assertThat(patientTO.getPatientNumber()).isEqualTo("PAT-456");
        assertThat(patientTO.getDateOfBirth()).isEqualTo(LocalDate.of(1985, 5, 15));

        assertThat(patientTO.getCompletedVisits()).isEmpty();
    }
}
