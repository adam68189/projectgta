package com.jpacourse.dto;

import com.jpacourse.persistance.entity.VisitEntity;
import com.jpacourse.persistance.enums.TreatmentType;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

public class VisitTO implements Serializable {
    private Long id;
    private LocalDateTime time;
    private String doctorLastName;
    private String doctorFirstName;
    private List<TreatmentType> types;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getDoctorLastName() {
        return doctorLastName;
    }

    public void setDoctorLastName(String doctorLastName) {
        this.doctorLastName = doctorLastName;
    }

    public String getDoctorFirstName() {
        return doctorFirstName;
    }

    public void setDoctorFirstName(String doctorFirstName) {
        this.doctorFirstName = doctorFirstName;
    }

    public List<TreatmentType> getTypes() {
        return types;
    }

    public void setTypes(List<TreatmentType> types) {
        this.types = types;
    }

    public void setTreatments(List<MedicalTreatmentTO> treatments) {
        this.types = treatments.stream()
                .map(MedicalTreatmentTO::getType)
                .toList();
    }

    public void setDoctor(VisitEntity visitEntity) {
        this.doctorFirstName = visitEntity.getDoctor().getFirstName();
        this.doctorLastName = visitEntity.getDoctor().getLastName();
    }
}