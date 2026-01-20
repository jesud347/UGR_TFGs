-- 1. Primero los profesores
CREATE TABLE profesores (
    codProfesor VARCHAR(10) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    departamento VARCHAR(150) NOT NULL
);

-- 2. Luego los usuarios que hacen referencia a profesores
CREATE TABLE usuarios (
    usuario VARCHAR(100) PRIMARY KEY,
    passwd VARCHAR(100),
    codProfesor VARCHAR(10),
    permisos BOOLEAN NOT NULL DEFAULT FALSE,
    FOREIGN KEY (codProfesor) REFERENCES profesores(codProfesor)
);

-- 3. Tribunales
CREATE TABLE tribunales (
    codTribunal VARCHAR(10) PRIMARY KEY,
    curso VARCHAR(5) NOT NULL,
    extraordinaria BOOLEAN NOT NULL DEFAULT FALSE
);

-- 4. Relación muchos a muchos
CREATE TABLE profesores_tribunales (
    codTribunal VARCHAR(10),
    codProfesor VARCHAR(10),
    rol VARCHAR(12) NOT NULL DEFAULT 'Vocal',	-- Presidente/a, Vocal, Secretario/a, Suplente
    PRIMARY KEY (codTribunal, codProfesor),
    FOREIGN KEY (codTribunal) REFERENCES tribunales(codTribunal),
    FOREIGN KEY (codProfesor) REFERENCES profesores(codProfesor)
);

-- 5. TFGs
CREATE TABLE tfgs (
	codTFG VARCHAR(10) PRIMARY KEY,
    codProfesor VARCHAR(10),
    titulo VARCHAR(150),
    profesor2 VARCHAR(150), -- POR SI EL TFG TIENE OTRO RESPONSABLE
    codTribunal VARCHAR(10),
    especial boolean, -- SI ES CONVOCATORIA ESPECIAL (DICIEMBRE) o es NORMAL
    FOREIGN KEY (codProfesor) REFERENCES profesores(codProfesor),
    FOREIGN KEY (codTribunal) REFERENCES tribunales(codTribunal)
);

-- 6. Estudiantes
CREATE TABLE estudiantes (
    codEstudiante INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    apellidos VARCHAR(100),
    email VARCHAR(100),
    codTFG VARCHAR(10),
    seminario BOOLEAN, -- APTO o NO APTO
    calificacion_tutor DECIMAL(4,2),
    calificacion_tribunal DECIMAL(4,2),
    FOREIGN KEY (codTFG) REFERENCES tfgs(codTFG)
);
