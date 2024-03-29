CREATE TABLE school (
	id int4 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	category varchar(20) NULL,
	name varchar(255) NULL,
	address varchar(255) NULL,
	city varchar(255) NULL,
	lat float8 NULL,
	lng float8 NULL,
	CONSTRAINT school_pkey PRIMARY KEY (id)
);

CREATE TABLE subject (
	id int4 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	code varchar(20) NULL,
	description varchar(255) NULL,
	CONSTRAINT subject_pkey PRIMARY KEY (id)
);

CREATE TABLE teacher (
	id int4 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	birth_date date NULL,
	category varchar(20) NULL,
	first_name varchar(255) NULL,
	last_name varchar(255) NULL,
	school_id int4 NULL references school(id),
	subject_id int4 NULL references subject(id),
	CONSTRAINT teacher_pkey PRIMARY KEY (id)
);

CREATE TABLE course (
	id int4 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	description varchar(255) NULL,
	end_date timestamp NULL,
	start_date timestamp NULL,
	title varchar(255) NULL,
	teacher_id int4 NULL references teacher(id),
	CONSTRAINT course_pkey PRIMARY KEY (id)
);

CREATE TABLE student (
	id int4 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	birth_date timestamp NULL,
	email varchar(255) NULL,
	first_name varchar(255) NULL,
	last_name varchar(255) NULL,
	school_id int4 NULL references school(id),
	CONSTRAINT student_pkey PRIMARY KEY (id)
);

CREATE TABLE enrolment (
	id int4 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	enrolment_date timestamp NULL,
	course_id int4 NULL references course(id),
	student_id int4 NULL references student(id),
	CONSTRAINT enrolment_pkey PRIMARY KEY (id)
);

CREATE TABLE app_user (
	id int4 NOT NULL GENERATED BY DEFAULT AS IDENTITY,
	first_name varchar(255) NULL,
	last_login timestamp NULL,
	last_name varchar(255) NULL,
	passwd varchar(255) NULL,
	username varchar(255) NULL,
	CONSTRAINT app_user_pkey PRIMARY KEY (id)
);
