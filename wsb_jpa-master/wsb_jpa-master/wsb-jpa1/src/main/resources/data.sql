--Address
INSERT INTO address (id, address_line1, address_line2, city, postal_code)
VALUES (4842, 'Ogrodowa', '83', 'Tczew', '50-940');

INSERT INTO address (id, address_line1, address_line2, city, postal_code)
VALUES (2492, 'Jesienna', '62', 'Opole', '75-720');

INSERT INTO address (id, address_line1, address_line2, city, postal_code)
VALUES (7675, 'Jesienna', '27', 'Tczew', '91-802');

INSERT INTO address (id, address_line1, address_line2, city, postal_code)
VALUES (8377, 'Leśna', NULL, 'Kraków', '30-050');

INSERT INTO address (id, address_line1, address_line2, city, postal_code)
VALUES (5773, 'Polna', '93', 'Lublin', '50-277');

INSERT INTO address (id, address_line1, address_line2, city, postal_code)
VALUES (4705, 'Ogrodowa', '22', 'Zamość', '25-238');

--Doctor
INSERT INTO doctor (id, address_id, doctor_number, email, first_name, last_name, telephone_number, specialization)
VALUES (3013, 7651, 'DOC163', 'dr.tomasz.nowicki@example.com', 'Jakub', 'Skonieczka', '039104567', 'SURGEON');

INSERT INTO doctor (id, address_id, doctor_number, email, first_name, last_name, telephone_number, specialization)
VALUES (4995, 4640, 'DOC993', 'dr.maria.kamińska@example.com', 'Julita', 'Batóg', '292335894', 'DERMATOLOGIST');

INSERT INTO doctor (id, address_id, doctor_number, email, first_name, last_name, telephone_number, specialization)
VALUES (3751, 4372, 'DOC996', 'dr.robert.mazur@example.com', 'Kornelia', 'Barcz', '117999729', 'GP');

INSERT INTO doctor (id, address_id, doctor_number, email, first_name, last_name, telephone_number, specialization)
VALUES (5910, 4202, 'DOC893', 'dr.elżbieta.dąbrowska@example.com', 'Alan', 'Wasiewicz', '928441495', 'OCULIST');

--Patient
INSERT INTO patient (id, pesel, first_name, last_name, telephone_number, email, patient_number, date_of_birth, address_id)
VALUES (5533, 500123123 , 'Marek', 'Kamińska', '500222333', 'marek.nowak@email.com', 'PAT001', '1985-05-15', 1186);

INSERT INTO patient (id, pesel, first_name, last_name, telephone_number, email, patient_number, date_of_birth, address_id)
VALUES (2068, 0012345890, 'Agnieszka', 'Kowalska', '500555666', 'agnieszka.kowalska@email.com', 'PAT002', '1990-08-22', 1186);

INSERT INTO patient (id, pesel, first_name, last_name, telephone_number, email, patient_number, date_of_birth, address_id)
VALUES (9389, 9001486003, 'Tomasz', 'Mazur', '500888999', 'tomasz.wisniewski@email.com', 'PAT003', '1978-11-03', 1002);

INSERT INTO patient (id, pesel, first_name, last_name, telephone_number, email, patient_number, date_of_birth, address_id)
VALUES (7372, 9001437182 , 'Karolina', 'Dąbrowska', '500333444', 'karolina.wojcik@email.com', 'PAT004', '1995-02-28', 1143);

--Visit
INSERT INTO visit (doctor_id, patient_id, id, time, description)
VALUES (2233, 3, 1, '2025-02-28 12:35:00', 'test');

INSERT INTO visit (doctor_id, patient_id, id, time, description)
VALUES (9063, 4, 3, '2025-03-01 09:00:00', 'Lorem ipsum');

INSERT INTO visit (doctor_id, patient_id, id, time, description)
VALUES (9061, 2, 6, '2025-03-05 11:30:00', 'Badanie okresowe');

INSERT INTO visit (doctor_id, patient_id, id, time, description)
VALUES (7585, 1, 9, '2025-03-10 14:15:00', 'Szczepienie');

INSERT INTO visit (doctor_id, patient_id, id, time, description)
VALUES (9863, 1, 15, '2025-03-15 16:45:00', 'Wizyta diagnostyczna');

INSERT INTO visit (doctor_id, patient_id, id, time, description)
VALUES (8472, 3, 16, '2025-03-20 10:00:00', 'Konsultacja wyników badań');

--Medical treatment
INSERT INTO medical_treatment (id, visit_id, description, type)
VALUES (4982, 1, 'Test descritpiona', 'EKG');

INSERT INTO medical_treatment (id, visit_id, description, type)
VALUES (9631, 3, 'Test descritpiona', 'USG');

INSERT INTO medical_treatment (id, visit_id, description, type)
VALUES (5662, 3, 'Test descritpiona', 'RTG');
