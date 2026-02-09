Para el uso / prueba del funcionamiento de la aplicación, recomiendo leer hasta el final.

Recomiendo ver también este video de demostración de la aplicación: https://www.youtube.com/watch?v=MoRFO7ZEQfM

------------------------------------------------------------------------------------------

Tecnologías empleadas:

• Java (openjdk version "21.0.7" 2025-04-15 LTS)
• NetBeans 25
• MySQL

------------------------------------------------------------------------------------------

Introducción

Hemos desarrollado una aplicación con el objetivo de facilitar la organización, gestión y evaluación de los Tribunales de Trabajo Fin de Grado (TFG) en los grados de Psicología y Logopedia.

A través de una interfaz intuitiva y estructurada, permite a los usuarios de gestión / coordinación llevar a cabo todas las tareas relacionadas con la creación de tribunales, asignación de TFGs, seguimiento de tutores y evaluación de estudiantes.

⸻

Acceso a la aplicación

Al iniciar la aplicación, el usuario deberá seleccionar el grado con el que desea trabajar:
	•	Psicología
	•	Logopedia

Para acceder, es necesario iniciar sesión con:
	•	📧 Cuenta de correo institucional (@ugr.es)
	•	🔐 Contraseña

Si es la primera vez que se accede:
	•	La contraseña por defecto será 1234
	•	La aplicación solicitará inmediatamente el cambio por una contraseña personalizada

⸻

Pantalla principal

Una vez iniciada sesión correctamente, el usuario accederá a la pantalla principal, donde podrá elegir entre dos grandes bloques de funcionalidades:
	•	Gestión / Coordinación
	•	Evaluación de alumnos

El acceso a la sección de Gestión / Coordinación dependerá de los permisos del usuario.

⸻

Gestión / Coordinación

1. Gestión de Tribunales

Desde esta sección, el usuario podrá:
	•	Visualizar todos los docentes disponibles en la base de datos
	•	Generar tribunales para un curso determinado mediante:
	•	Generación completamente aleatoria
	•	Generación aleatoria con restricción, donde cada tribunal tendrá 2 profesores del mismo departamento
	•	Generación automática de un tribunal suplente
	•	Filtrar los tribunales por curso académico

Menú superior
	•	Ver Departamentos
Visualiza los tribunales por departamento en el curso seleccionado y permite exportarlos.
	•	Ver Suplentes
Muestra los tribunales suplentes generados.
	•	Editar / Organizar Tribunales
Permite:
	•	Ver integrantes y roles (Presidente, Vocal, Secretario o Suplente)
	•	Eliminar o intercambiar docentes entre tribunales (máximo 3 por tribunal)
	•	Marcar tribunales como Ordinaria o Extraordinaria
	•	Eliminar tribunales y liberar a los docentes
	•	Exportador a Excel
Exporta tribunales completos o filtrados por convocatoria.
	•	Eliminar Tribunales
Elimina:
	•	Todos los tribunales de un curso concreto
	•	Todos los tribunales del sistema

⸻

2. Gestión de Docentes
	•	Listado completo de docentes
	•	Búsqueda por:
	  •	Código
	  •	Nombre
	  •	Departamento

Al seleccionar un docente se muestra:
	•	Información detallada
	•	TFGs tutorizados
	•	Tribunales a los que pertenece

Funciones del menú superior
	•	Crear docente
	•	Editar docente (datos y permisos de gestión)
	•	Eliminar docente

⸻

3. Gestión de TFGs
	•	Listado completo de TFGs registrados
	•	Asignación de un TFG a un tribunal
	•	Eliminación del tribunal asociado a un TFG para modificar la asignación

⸻

4. Gestión de Estudiantes
	•	Listado de todos los estudiantes registrados
	•	Asignación de un TFG a un estudiante
	•	Eliminación del TFG asignado para poder reasignarlo

⸻

Evaluación

Esta sección está disponible para todos los usuarios, independientemente de sus permisos de gestión.

⸻

1. Evaluación como Tutor
	•	Listado de estudiantes tutorizados por el usuario
	•	Selección de un estudiante y acceso a la rúbrica de evaluación
	•	Evaluación basada en criterios definidos
	•	Resultado:
	•	Apto
	•	No Apto
	•	La nota queda registrada en el sistema

⸻

2. Evaluación como Tribunal
	•	Listado de estudiantes asignados al tribunal del usuario
	•	Consulta de:
	•	Información del estudiante
	•	Nota del tutor
	•	Si el usuario tiene rol de Presidente, puede:
	•	Acceder a la rúbrica de evaluación
	•	Registrar la nota final si se cumplen los requisitos
	•	Botón “Certificado TFG”:
	•	Genera automáticamente un certificado con los datos del estudiante


Eso sería todo el funcionamiento que tiene la aplicación, a continuación, verás la estructura de la base de datos:

- profesores

codProfesor VARCHAR(10) PRIMARY KEY,
nombre VARCHAR(100) NOT NULL,
departamento VARCHAR(150) NOT NULL

- usuarios

usuario VARCHAR(100) PRIMARY KEY,
passwd VARCHAR(100),
codProfesor VARCHAR(10),
permisos BOOLEAN NOT NULL DEFAULT FALSE,
FOREIGN KEY (codProfesor) REFERENCES profesores(codProfesor)

- tribunales

codTribunal VARCHAR(10) PRIMARY KEY,
curso VARCHAR(5) NOT NULL,
extraordinaria BOOLEAN NOT NULL DEFAULT FALSE

- profesores_tribunales

codTribunal VARCHAR(10),
codProfesor VARCHAR(10),
rol VARCHAR(12) NOT NULL DEFAULT 'Vocal',
PRIMARY KEY (codTribunal, codProfesor),
FOREIGN KEY (codTribunal) REFERENCES tribunales(codTribunal),
FOREIGN KEY (codProfesor) REFERENCES profesores(codProfesor)

- tfgs

codTFG VARCHAR(10) PRIMARY KEY,
codProfesor VARCHAR(10),
titulo VARCHAR(150),
profesor2 VARCHAR(150),
codTribunal VARCHAR(10),
especial BOOLEAN,
FOREIGN KEY (codProfesor) REFERENCES profesores(codProfesor),
FOREIGN KEY (codTribunal) REFERENCES tribunales(codTribunal)

- estudiantes

codEstudiante INT AUTO_INCREMENT PRIMARY KEY,
nombre VARCHAR(100),
apellidos VARCHAR(100),
email VARCHAR(100),
codTFG VARCHAR(10),
seminario BOOLEAN,
calificacion_tutor DECIMAL(4,2),
calificacion_tribunal DECIMAL(4,2),
FOREIGN KEY (codTFG) REFERENCES tfgs(codTFG)

