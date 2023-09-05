delete from enrolment;
delete from student;
delete from course;
delete from teacher;
delete from subject;
delete from school;

INSERT INTO school (id, category, name, address, city, lat, lng) VALUES(1001, 'SC_PRIMARY', 'Scuola 1', NULL, NULL, NULL, NULL);

INSERT INTO subject (id, code, description) VALUES(1001, 'MATH', 'Math');
INSERT INTO subject (id, code, description) VALUES(1002, 'CS', 'Computer Science');
INSERT INTO subject (id, code, description) VALUES(1004, 'BIO', 'Biology');

INSERT INTO teacher (id, birth_date, category, email, first_name, last_name, school_id, subject_id) VALUES(1002, '1997-11-22', 'TC_SENIOR', 't2@test.com', 'Giorgio', 'Bianchi', 1001, 1004);
INSERT INTO teacher(id, birth_date, category, email, first_name, last_name, school_id, subject_id) VALUES(1001, '1987-01-02', 'TC_JUNIOR', 't1@test.com', 'Mario', 'Rossi', 1001, 1002);