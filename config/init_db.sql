CREATE TABLE resume
(
  uuid      VARCHAR(36) PRIMARY KEY NOT NULL,
  full_name VARCHAR                 NOT NULL
);

CREATE TABLE contact
(
  id          SERIAL      NOT NULL,
  resume_uuid VARCHAR(36) NOT NULL  CONSTRAINT contact_resume_uuid_fk REFERENCES resume ON DELETE CASCADE,
  type        VARCHAR     NOT NULL,
  value       VARCHAR     NOT NULL
);

CREATE UNIQUE INDEX contact_uuid_type_index
  ON contact (resume_uuid, type);

CREATE TABLE SECTION
(
  id          SERIAL      NOT NULL
    CONSTRAINT section_pkey
    PRIMARY KEY,
  resume_uuid VARCHAR(36) NOT NULL  CONSTRAINT section_resume_uuid_fk REFERENCES resume ON DELETE CASCADE,
  type        VARCHAR     NOT NULL,
  value       VARCHAR     NOT NULL
);

CREATE INDEX section_uuid_type_index
  ON section (resume_uuid, type);

