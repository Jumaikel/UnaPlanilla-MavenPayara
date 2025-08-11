/*
Created: 19/7/2017
Modified: 10/5/2021
Model: UnaPlanilla
Database: Oracle 11g Release 2
*/




-- Create sequences section -------------------------------------------------

CREATE SEQUENCE plam_tipoplanillas_seq01
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 MINVALUE 0
 NOCACHE
;

CREATE SEQUENCE plam_empleados_seq01
 INCREMENT BY 1
 START WITH 1
 NOMAXVALUE
 MINVALUE 0
 NOCACHE
;

-- Create tables section -------------------------------------------------

-- Table plam_empleados

CREATE TABLE plam_empleados(
  emp_id Number NOT NULL,
  emp_nombre Varchar2(30 ) NOT NULL,
  emp_papellido Varchar2(15 ) NOT NULL,
  emp_sapellido Varchar2(15 ) NOT NULL,
  emp_cedula Varchar2(40 ) NOT NULL,
  emp_genero Varchar2(1 ) DEFAULT 'M' NOT NULL
        CONSTRAINT plam_empleados_ck03 CHECK (emp_genero in ('M','F')),
  emp_correo Varchar2(80 ),
  emp_administrador Varchar2(1 ) DEFAULT 'N' NOT NULL
        CONSTRAINT plam_empleados_ck02 CHECK (emp_administrador in ('S','N')),
  emp_usuario Varchar2(15 ),
  emp_clave Varchar2(8 ),
  emp_fingreso Date NOT NULL,
  emp_fsalida Date,
  emp_estado Varchar2(1 ) DEFAULT 'A' NOT NULL
        CONSTRAINT plam_empleados_ck01 CHECK (emp_estado in ('A','I')),
  emp_version Number DEFAULT 1 NOT NULL
)
;

-- Create indexes for table plam_empleados

CREATE UNIQUE INDEX plam_empleados_ind01 ON plam_empleados (emp_usuario)
;

-- Add keys for table plam_empleados

ALTER TABLE plam_empleados ADD CONSTRAINT plam_empleados_pk PRIMARY KEY (emp_id)
;

-- Table and Columns comments section

COMMENT ON TABLE plam_empleados IS 'Tabla de empleados'
;
COMMENT ON COLUMN plam_empleados.emp_id IS 'Id del empleado'
;
COMMENT ON COLUMN plam_empleados.emp_nombre IS 'Nombre del empleado'
;
COMMENT ON COLUMN plam_empleados.emp_papellido IS 'Primer apellido del empleado'
;
COMMENT ON COLUMN plam_empleados.emp_sapellido IS 'Segundo apellido del empleado'
;
COMMENT ON COLUMN plam_empleados.emp_cedula IS 'Cedula del empleado'
;
COMMENT ON COLUMN plam_empleados.emp_genero IS 'Genero del empleado (M:Masculino, F:Femenino)'
;
COMMENT ON COLUMN plam_empleados.emp_correo IS 'Correo electronico del empleado'
;
COMMENT ON COLUMN plam_empleados.emp_administrador IS 'Empleado administador del sistema (S:Si, N:No)'
;
COMMENT ON COLUMN plam_empleados.emp_usuario IS 'Usuario del sistema para el empleado'
;
COMMENT ON COLUMN plam_empleados.emp_clave IS 'Clave del sistema para el empleado'
;
COMMENT ON COLUMN plam_empleados.emp_fingreso IS 'Fecha de ingreso del empleado'
;
COMMENT ON COLUMN plam_empleados.emp_fsalida IS 'Fecha de salida del empleado'
;
COMMENT ON COLUMN plam_empleados.emp_estado IS 'Estado del empleado (A:Activo, I:Inactivo)'
;
COMMENT ON COLUMN plam_empleados.emp_version IS 'Version del registro'
;

-- Table plam_tipoplanillas

CREATE TABLE plam_tipoplanillas(
  tpla_id Number NOT NULL,
  tpla_codigo Varchar2(4 ) NOT NULL,
  tpla_descripcion Varchar2(40 ) NOT NULL,
  tpla_plaxmes Number NOT NULL,
  tpla_anoultpla Number,
  tpla_mesultpla Number,
  tpla_numultpla Number,
  tpla_estado Varchar2(1 ) DEFAULT 'A' NOT NULL
        CONSTRAINT plam_tipoplanillas_ck01 CHECK (tpla_estado in ('A','I')),
  tpla_version Number DEFAULT 1 NOT NULL
)
;

-- Create indexes for table plam_tipoplanillas

CREATE UNIQUE INDEX plam_tipoplanillas_ind01 ON plam_tipoplanillas (tpla_codigo)
;

-- Add keys for table plam_tipoplanillas

ALTER TABLE plam_tipoplanillas ADD CONSTRAINT plam_tipoplanillas_pk PRIMARY KEY (tpla_id)
;

-- Table and Columns comments section

COMMENT ON COLUMN plam_tipoplanillas.tpla_id IS 'Id del tipo de planilla'
;
COMMENT ON COLUMN plam_tipoplanillas.tpla_codigo IS 'Codigo del tipo de planilla'
;
COMMENT ON COLUMN plam_tipoplanillas.tpla_descripcion IS 'Descripcion o nombre de la planilla'
;
COMMENT ON COLUMN plam_tipoplanillas.tpla_plaxmes IS 'Cantidad de planillas a generar por mes'
;
COMMENT ON COLUMN plam_tipoplanillas.tpla_anoultpla IS 'A�o de la ultima planilla generada'
;
COMMENT ON COLUMN plam_tipoplanillas.tpla_mesultpla IS 'Mes de la ultima planilla generada'
;
COMMENT ON COLUMN plam_tipoplanillas.tpla_numultpla IS 'Numero de la ultima planilla generada'
;
COMMENT ON COLUMN plam_tipoplanillas.tpla_estado IS 'Estado del tipo de planilla(A:Activa,I:Inactiva)'
;
COMMENT ON COLUMN plam_tipoplanillas.tpla_version IS 'Version del registro'
;

-- Table plam_empleadosplanilla

CREATE TABLE plam_empleadosplanilla(
  exp_idtpla Number NOT NULL,
  exp_idemp Number NOT NULL
)
;

-- Add keys for table plam_empleadosplanilla

ALTER TABLE plam_empleadosplanilla ADD CONSTRAINT plam_empleadosplanilla_pk PRIMARY KEY (exp_idtpla,exp_idemp)
;

-- Table and Columns comments section

COMMENT ON TABLE plam_empleadosplanilla IS 'Tabla ralacional de empleados por tipo de planilla'
;
COMMENT ON COLUMN plam_empleadosplanilla.exp_idtpla IS 'Id del tipo de planilla.'
;
COMMENT ON COLUMN plam_empleadosplanilla.exp_idemp IS 'Id del empleado.'
;

-- Trigger for sequence plam_empleados_seq01 for column emp_id in table plam_empleados ---------
CREATE OR REPLACE TRIGGER plam_empleados_tgr01 BEFORE INSERT
ON plam_empleados FOR EACH ROW
BEGIN
  IF :new.emp_id IS NULL THEN
     :new.emp_id := plam_empleados_seq01.nextval;
  END IF;
END;
/
CREATE OR REPLACE TRIGGER plam_empleados_tgr02 AFTER UPDATE OF emp_id
ON plam_empleados FOR EACH ROW
BEGIN
  RAISE_APPLICATION_ERROR(-20010,'Cannot update column emp_id in table plam_empleados as it uses sequence.');
END;
/

-- Trigger for sequence plam_tipoplanillas_seq01 for column tpla_id in table plam_tipoplanillas ---------
CREATE OR REPLACE TRIGGER plam_tipoplanillas_tgr01 BEFORE INSERT
ON plam_tipoplanillas FOR EACH ROW
BEGIN
  IF :new.tpla_id IS NULL THEN
     :new.tpla_id := plam_tipoplanillas_seq01.nextval;
  END IF;
END;
/
CREATE OR REPLACE TRIGGER plam_tipoplanillas_tgr02 AFTER UPDATE OF tpla_id
ON plam_tipoplanillas FOR EACH ROW
BEGIN
  RAISE_APPLICATION_ERROR(-20010,'Cannot update column tpla_id in table plam_tipoplanillas as it uses sequence.');
END;
/


-- Create foreign keys (relationships) section ------------------------------------------------- 

ALTER TABLE plam_empleadosplanilla ADD CONSTRAINT plam_empleadosplanilla_fk01 FOREIGN KEY (exp_idtpla) REFERENCES plam_tipoplanillas (tpla_id)
;



ALTER TABLE plam_empleadosplanilla ADD CONSTRAINT plam_empleadosplanilla_fk02 FOREIGN KEY (exp_idemp) REFERENCES plam_empleados (emp_id)
;
