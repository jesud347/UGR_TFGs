package Vistas.Evaluacion;

import Controladores.Ctrl_Estudiantes;
import Controladores.EMFProvider;
import Modelos.Estudiante;
import java.awt.CardLayout;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;

public class JDialog_EvaluacionTutor extends javax.swing.JDialog {

    private EntityManagerFactory emf;
    private Ctrl_Estudiantes ctrl_estudiante;
    private Estudiante estudiante;
    private CardLayout cardLayout;
    private ArrayList<PanelEvaluacionTutor> panelesEvaluacion;
    private int indiceActual = 0;

    public JDialog_EvaluacionTutor(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        //NO SE USA
    }

    public JDialog_EvaluacionTutor(Dialog parent, boolean modal, Estudiante estudianteSeleccionado) {
        super(parent, modal);
        initComponents();
        try {
            emf = EMFProvider.getEmf();
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(this,
                    "Error de conexión: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1); 
        }



        ctrl_estudiante = new Ctrl_Estudiantes(emf);

        this.estudiante = estudianteSeleccionado;
        setTitle("Evaluación del Tutor");

        txt_nombreAlumno.setText(estudiante.toString());

        cardLayout = new CardLayout();
        panelTutor.setLayout(cardLayout);

        bt_omitir.setEnabled(false);

        panelesEvaluacion = new ArrayList<>();

        // Crear rúbricas
        panelesEvaluacion = new ArrayList<>();

        panelesEvaluacion.add(new PanelEvaluacionTutor(
                "HABILIDADES PRERREQUISITAS - Originalidad y Plagio",
                "Puntuación <1,TFG no apto. PUNTUACION (0-10)",
                "Evalúa según la siguiente rúbrica:",
                new String[]{
                    "0 - Plagio, cortapega sin citar fuentes, se detecta que el trabajo es la mera traducción de otros o el uso de inteligencia artificial en la redacción. IMPORTANTE: basta con detectar un par de casos de este tipo de plagio en el TFG para asignarle el valor de cero en esta escala. En estos casos, estaríamos ante casos de fraude y no ante errores de cita.",
                    "1 -",
                    "2 - Se citan las fuentes, pero se hace un uso excesivo de citas literales.",
                    "3 -",
                    "4 -",
                    "5 - Paráfrasis de las fuentes, sin hacer más que reproducir la información contenida en ellas. Se citan correctamente casi todas las fuentes.",
                    "6 -",
                    "7 - Reproducción de la información de las fuentes, y cierto grado de síntesis. Se citan correctamente casi todas las fuentes.",
                    "8 -",
                    "9 - Uso de la información recogida de fuentes para extraer nuevas ideas y conclusiones o apoyar argumentos propios. Se citan correctamente casi todas las fuentes.",
                    "10 - Realización de una contribución original a la disciplina perfectamente apoyada en fuentes citadas correctamente."
                }
        ));

        panelesEvaluacion.add(new PanelEvaluacionTutor(
                "HABILIDADES PRERREQUISITAS - Ortografía y Sintaxis",
                "Puntuación <2,TFG no apto. PUNTUACION (0-10)",
                "Evalúa según la siguiente rúbrica:",
                new String[]{
                    "0 - Más de 5 faltas de ortografía graves (p.ej., “b” en lugar de “v”) que podrían haber sido detectadas usando un corrector ortográfico. Uso frecuente de oraciones claramente agramaticales (con problemas graves de concordancia y puntuación) que llegan a dificultar la lectura y la comprensión del mensaje.",
                    "1 -",
                    "2 -",
                    "3 -",
                    "4 -",
                    "5 - Un máximo de 2 faltas de ortografía de cualquier tipo. Sintaxis y estilo mayormente correctos, pero con oraciones a menudo excesivamente complejas y largas, y un vocabulario o bien excesivamente coloquial o excesivamente pedante.",
                    "6 -",
                    "7 -",
                    "8 -",
                    "9 -",
                    "10 - Ortografía perfecta. Uso de oraciones muy claras, de sintaxis sencilla, perfectamente engarzadas y que permiten una lectura rápida, fácil y muy buena comprensión de las ideas."
                }
        ));

        panelesEvaluacion.add(new PanelEvaluacionTutor(
                "HABILIDADES PRERREQUISITAS - Formato general del documento",
                "Puntuación <2,TFG no apto. PUNTUACION (0-10)",
                "Evalúa según la siguiente rúbrica:",
                new String[]{
                    "0 - No se respeta la estructura general del documento indicada por el tutor/a según el tipo de TFG ni las características generales de formato indicadas en el Manual del Estudiante (incluyendo el adjuntar la declaración de originalidad). No se respetan en absoluto las características de formato de las citas y los resultados estadísticos, tablas y figuras especificadas en las normas APA y en el Manual del Estudiante.",
                    "1 -",
                    "2 - Se respeta la estructura general del documento indicada por el tutor/a según el tipo de TFG y se respetan las características de formato generales indicadas en el Manual del Estudiante, pero no se siguen las normas APA para el formateo de citas, referencias, resultados estadísticos, tablas y figuras.",
                    "3 -",
                    "4 -",
                    "5 - Se respeta la estructura general del documento indicada por el tutor/a según el tipo de TFG y se respetan las características de formato generales indicadas en el Manual del Estudiante. Además, se intenta seguir las normas APA para el formateo de citas, referencias, resultados estadísticos, tablas y figuras, pero su aplicación es inconsistente y hay muchos ejemplos en los que no se siguen correctamente.",
                    "6 -",
                    "7 -",
                    "8 -",
                    "9 -",
                    "10 - Respeto perfecto de la estructura general del documento indicada por el tutor/a según el tipo de TFG. Se respetan en detalle y de forma consistente las características de formato de las citas y los resultados estadísticos, tablas y figuras especificadas en las normas APA y en el Manual del Estudiante."
                }
        ));

        panelesEvaluacion.add(new PanelEvaluacionTutor(
                "HABILIDADES PRERREQUISITAS - Esfuerzo y Motivación",
                "Puntuación <1,TFG no apto. PUNTUACION (0-10)",
                "Evalúa según la siguiente rúbrica:",
                new String[]{
                    "0 - El trabajo se ha realizado desde la desgana más absoluta. No se ha intentado acudir a recibir feedback del/de la profesor/a, y/o no se han implementado ninguna de las sugerencias de mejora realizadas por éste.",
                    "1 -",
                    "2 -",
                    "3 -",
                    "4 -",
                    "5 -",
                    "6 -",
                    "7 -",
                    "8 -",
                    "9 -",
                    "10 - Se ha mostrado una gran motivación e interés en sacar el máximo partido formativo del trabajo, recabando y aprovechando el feedback aportado por el/la profesor/a. No se han escatimado esfuerzos en producir el mejor resultado final posible dadas las circunstancias en que el trabajo se ha llevado a cabo."
                }
        ));

        panelesEvaluacion.add(new PanelEvaluacionTutor(
                "CALIDAD DEL TRABAJO - Comprensión del tema propuesto para el TFG",
                "PUNTUACIÓN (0-10)",
                "Evalúa según la siguiente rúbrica:",
                new String[]{
                    "0 - El/la estudiante no comprende el marco teórico en el que se encuadra su TFG, no es capaz de explicar cuál es la pregunta que se intenta responder con él, ni cómo la metodología usada permite intentar responder esa pregunta.",
                    "1 -",
                    "2 -",
                    "3 -",
                    "4 -",
                    "5 - El/la estudiante tiene una comprensión básica de la temática en la que se encuadra su trabajo, de la pregunta que se intenta responder y de la metodología usada para ello, suficiente para llevar adelante el TFG propuesto, aunque le falta una comprensión clara de las implicaciones más generales de la temática.",
                    "6 -",
                    "7 -",
                    "8 -",
                    "9 -",
                    "10 - El/la estudiante comprende el marco teórico en el que se encuadra su TFG. Comprende bien los aspectos teóricos más relevantes que hacen importante la pregunta que se intenta responder, sus implicaciones y conexiones con cuestiones más generales y cómo la metodología a seguir permite intentar responder la pregunta planteada."
                }
        ));

        panelesEvaluacion.add(new PanelEvaluacionTutor(
                "CALIDAD DEL TRABAJO - Solidez del argumento",
                "PUNTUACIÓN (0-10)",
                "Evalúa según la siguiente rúbrica:",
                new String[]{
                    "0 - Uso exclusivo de fuentes de información de poca fiabilidad (páginas de internet de autores no identificados, folletos, anécdotas...) y/o el argumento tiene problemas lógicos importantes (incurre en graves falacias lógicas). Las afirmaciones no se apoyan de forma adecuada en citas.",
                    "1 -",
                    "2 -",
                    "3 - Uso de fuentes fiables y menos fiables, sin clara valoración de la distinción entre ellas, y/o el argumento tiene problemas lógicos importantes (incurre en graves falacias lógicas). Las afirmaciones casi nunca se apoyan de forma adecuada en citas.",
                    "4 -",
                    "5 - Uso predominante de fuentes fiables de información, mostrando una clara valoración de la importancia que debe darse a una fuente en función de su calidad. No considera fuentes de la literatura científica internacional (escritas en inglés). No hay graves falacias lógicas en el argumento. Las afirmaciones a veces no se apoyan de forma adecuada en citas.",
                    "6 -",
                    "7 - Uso predominante de fuentes fiables de información, mostrando una clara valoración de la importancia que debe darse a una fuente en función de su calidad. Incluye fuentes de la literatura científica internacional (escritas en inglés). No hay graves falacias lógicas en el argumento. Las afirmaciones casi siempre se apoyan de forma adecuada en citas.",
                    "8 -",
                    "9 -",
                    "10 - Uso exclusivo de fuentes de gran calidad científica (revistas internacionales con revisión por pares, libros y obras de referencia de prestigio reconocido). El idioma no es un impedimento para la selección y utilización de fuentes de información. No hay graves falacias lógicas en el argumento. Las afirmaciones siempre están bien fundamentadas en citas adecuadas."
                }
        ));

        panelesEvaluacion.add(new PanelEvaluacionTutor(
                "CALIDAD DEL TRABAJO - Claridad de la estructura del argumento",
                "PUNTUACIÓN (0-10)",
                "Evalúa según la siguiente rúbrica:",
                new String[]{
                    "0 - No se plantea claramente la pregunta que se intenta responder con el trabajo (no hay objetivos claros). El argumento para responderla es deslavazado, y el lector se pregunta muy a menudo qué tiene que ver el párrafo con el objetivo del trabajo. No hay una respuesta clara a la pregunta del trabajo (ausencia de conclusiones claras).",
                    "1 -",
                    "2 -",
                    "3 -",
                    "4 -",
                    "5 - Se plantea la pregunta que el trabajo intenta responder con claridad suficiente. El argumento está relativamente bien estructurado, pero hay partes que no se entiende por qué se han tocado ni qué aportan al conjunto. Las conclusiones no son del todo claras ni se ve claramente la relación entre ellas y el argumento usado para apoyarlas.",
                    "6 -",
                    "7 - Se plantea claramente la pregunta que el trabajo intenta responder. El argumento está relativamente bien estructurado, y el autor hace esfuerzos para guiar al lector con comentarios metanarrativos que explican el flujo del argumento, pero aún hay algunas partes que no se entiende por qué se han tocado. Las conclusiones no son del todo claras, pero más o menos se siguen del argumento.",
                    "8 -",
                    "9 -",
                    "10 - Clara especificación de la pregunta global que se intenta responder, del procedimiento a seguir para responderla y de la respuesta finalmente alcanzada. Cada párrafo cumple un papel lógico dentro de la estructura global y el autor se ha preocupado de orientar al lector en todo momento con respecto a la estructura del argumento mediante comentarios metanarrativos."
                }
        ));

        panelesEvaluacion.add(new PanelEvaluacionTutor(
                "CALIDAD DEL TRABAJO - Calidad de la revisión e integración de conocimientos",
                "PUNTUACIÓN (0-10)",
                "Evalúa según la siguiente rúbrica:",
                new String[]{
                    "0 - El/la estudiante no ha realizado una buena revisión de la literatura. No ha logrado alcanzar una visión global y objetiva de la temática y no ha sido capaz de extraer sus propias conclusiones sobre la pregunta principal del trabajo.",
                    "1 -",
                    "2 -",
                    "3 -",
                    "4 -",
                    "5 - El/la estudiante ha realizado una revisión de la literatura aceptable. Ha logrado alcanzar una visión global y objetiva de la temática, aunque no ha sido capaz de extraer sus propias conclusiones sobre la pregunta principal del trabajo. No se incluye ningún tipo de valoración, análisis o crítica de la información presentada, se limita a transcribir las ideas de los autores citados.",
                    "6 -",
                    "7 -",
                    "8 -",
                    "9 -",
                    "10 - El/la estudiante ha realizado una revisión exhaustiva de la literatura. Ha logrado alcanzar una visión global y objetiva de la temática, y ha sido capaz de extraer sus propias conclusiones sobre la pregunta principal del trabajo. El texto incluye análisis frecuentes de la información que van más allá de los planteados por los autores estudiados."
                }
        ));

        panelesEvaluacion.add(new PanelEvaluacionTutor(
                "CALIDAD DEL TRABAJO - Análisis y discusión de los datos",
                "PUNTUACIÓN (0-10) - (Sólo para TFGs que impliquen el análisis de datos (reales, simulados o esperados))",
                "Evalúa según la siguiente rúbrica:",
                new String[]{
                    "0 - El/la estudiante no ha realizado análisis estadísticos o los análisis no son apropiados. En el caso de protocolos (pre-registro de proyectos o estudios), el/la estudiante no es capaz de plantear los análisis pertinentes. El/la estudiante no comprende el análisis o no es capaz de interpretar su significado.",
                    "1 -",
                    "2 -",
                    "3 -",
                    "4 -",
                    "5 - El/la estudiante ha realizado (planteado, en el caso de protocolos) los análisis estadísticos de forma correcta, aunque no ha aprendido a realizarlo con total independencia. Comprende relativamente bien el análisis, es capaz de interpretar su significado, pero tendría dificultades para aplicarlo a su futura práctica profesional.",
                    "6 -",
                    "7 -",
                    "8 -",
                    "9 -",
                    "10 - El/la estudiante ha realizado (planteado, en el caso de protocolos) los análisis estadísticos de forma correcta y ha aprendido a realizarlos con total independencia. Comprende el análisis, es capaz de interpretar su significado y podría aplicarlo a su futura práctica profesional. En el caso de protocolos, es capaz de proyectar unos resultados posibles y entender posibles explicaciones."
                }
        ));

        panelesEvaluacion.add(new PanelEvaluacionTutor(
                "CALIDAD DEL TRABAJO - Capacidad de aplicar conocimientos a la práctica",
                "PUNTUACIÓN (0-10) - (Sólo para TFGs de aplicación de conocimientos a casos prácticos.)",
                "Evalúa según la siguiente rúbrica:",
                new String[]{
                    "0 - No es capaz de reconocer en una situación práctica sus objetivos y/o componentes básicos. No identifica los conocimientos que demanda la situación práctica. No es capaz de realizar un diagnóstico de partida y/o de acceder a información adecuada y fiable para ello. Toma decisiones, pero no las justifica. No desarrolla ninguna estrategia para el seguimiento y evaluación de los resultados alcanzados.",
                    "1 -",
                    "2 -",
                    "3 -",
                    "4 -",
                    "5 - Es capaz de reconocer en una situación práctica sus objetivos y/o componentes básicos, pero no los aplica correctamente. Realiza un diagnóstico de la situación, pero la información recabada no es adecuada ni fiable y, por tanto, no permite diseñar una buena solución al problema práctico. Justifica las decisiones tomadas, pero no de manera adecuada. Contempla una estrategia para la evaluación de resultados, pero no es del todo adecuada.",
                    "6 -",
                    "7 -",
                    "8 -",
                    "9 -",
                    "10 - Reconoce en una situación práctica sus objetivos y componentes básicos, relacionándolos entre sí. Identifica y aplica los conocimientos relevantes correctamente. Realiza un buen diagnóstico, recabando información adecuada y fiable que permite tomar las decisiones adecuadas. Justifica las decisiones de forma clara, convincente y estructurada. Pone en marcha una buena estrategia para la evaluación de resultados."
                }
        ));

        panelesEvaluacion.add(new PanelEvaluacionTutor(
                "CALIDAD DEL TRABAJO - Capacidad de desarrollar una investigación bibliográfica",
                "PUNTUACIÓN (0-10) - (Solo para TFGS de investigación bibliográfica)",
                "Evalúa según la siguiente rúbrica:",
                new String[]{
                    "0 - No es capaz de trasladar el objetivo a un esquema de búsqueda (bien palabras clave, bien una estructura tipo PICOS), a la ecuación de búsqueda y a las bases de datos. No es capaz de seleccionar los estudios que responden a la pregunta de investigación planteada. No identifica las variables relevantes para formular conclusiones que integren la evidencia recogida. Las conclusiones no responden al objetivo formulado.",
                    "1 -",
                    "2 -",
                    "3 -",
                    "4 -",
                    "5 - Es capaz de trasladar el objetivo a un esquema de búsqueda (bien palabras clave, bien una estructura tipo PICOS), a la ecuación de búsqueda y a las bases de datos, pero no es capaz de determinar si los estudios identificados responden a su pregunta de investigación. Es capaz de identificar las variables relevantes pero no es capaz de extraer la información específica de los estudios identificados. Es capaz de analizar la información de cada estudio pero no es capaz de sintetizar la evidencia de manera conjunta.",
                    "6 -",
                    "7 -",
                    "8 -",
                    "9 -",
                    "10 - Traslada adecuadamente el objetivo a un esquema de búsqueda (bien palabras clave, bien una estructura tipo PICOS), a la ecuación de búsqueda y a las bases de datos. Aplica adecuadamente los criterios establecidos para decidir si cada estudio debe ser incluido en la revisión. Identifica las variables relevantes y las extrae adecuadamente de los estudios. Identifica las aportaciones de los estudios individuales y sintetiza la evidencia adecuadamente, formulando conclusiones que integran los resultados de los estudios incluidos en la revisión."
                }
        ));

        // Agregar todos los paneles al contenedor
        for (int i = 0; i < panelesEvaluacion.size(); i++) {
            panelTutor.add(panelesEvaluacion.get(i), "panel" + i);
        }

        for (double i = 0.0; i <= 10.0; i += 0.5) {
            combobox_notas.addItem(i);
        }

        bt_omitir.addActionListener(e -> {
            // Solo se permite omitir si estamos en una rúbrica de calidad del trabajo (índice >= 4)
            if (indiceActual >= 4) {
                panelesEvaluacion.get(indiceActual).setOmitido(true);

                if (indiceActual < panelesEvaluacion.size() - 1) {
                    indiceActual++;
                    cardLayout.show(panelTutor, "panel" + indiceActual);
                    cargarNotaActual(); // recarga nota y estado del botón
                    actualizarEstadoBotones();
                } else {
                    finalizarEvaluacion();
                }
            } else {
                JOptionPane.showMessageDialog(this, "No se puede omitir una rúbrica de habilidades prerrequisitas.");
            }
        });

        bt_anterior.setEnabled(false);

        bt_anterior.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (indiceActual > 0) {
                    // Guardar la nota actual
                    double notaSeleccionada = (double) combobox_notas.getSelectedItem();
                    panelesEvaluacion.get(indiceActual).setNota(notaSeleccionada);

                    indiceActual--;
                    cardLayout.show(panelTutor, "panel" + indiceActual);

                    cargarNotaActual(); // 💡 Sustituye el código anterior
                    actualizarEstadoBotones();
                }
            }
        });

        bt_siguiente.addActionListener(e -> {
            double notaSeleccionada = (double) combobox_notas.getSelectedItem();
            panelesEvaluacion.get(indiceActual).setNota(notaSeleccionada);

            if (indiceActual < panelesEvaluacion.size() - 1) {
                indiceActual++;
                cardLayout.show(panelTutor, "panel" + indiceActual);

                cargarNotaActual(); // 💡 Sustituye el código anterior
                actualizarEstadoBotones();
            } else {
                panelesEvaluacion.get(indiceActual).setNota(notaSeleccionada);
                finalizarEvaluacion();
            }
        });

    }

    private void cargarNotaActual() {
        PanelEvaluacionTutor panelActual = panelesEvaluacion.get(indiceActual);
        double notaGuardada = panelActual.getNota();
        combobox_notas.setSelectedItem(notaGuardada >= 0 ? notaGuardada : 0.0);

        String subtitulo = panelActual.getTxt_puntuacion(); // Asegúrate de tener este getter

        // Mostrar u ocultar el botón "Omitir"
        if (subtitulo.startsWith("PUNTUACIÓN (0-10) -")) {
            bt_omitir.setEnabled(true);
        } else {
            bt_omitir.setEnabled(false);
        }
    }

    private void finalizarEvaluacion() {
        double sumaPrerrequisitos = 0;
        double sumaCalidad = 0;
        int countPrerrequisitos = 4;
        int countCalidad = 0;

        // Validar y sumar las 4 rúbricas de habilidades prerrequisitas con sus condiciones
        for (int i = 0; i < 4; i++) {
            double nota = panelesEvaluacion.get(i).getNota();
            if (nota < 0) {
                JOptionPane.showMessageDialog(this, "Falta una nota en una rúbrica de habilidades prerrequisitas.");
                return;
            }

            boolean invalido = false;
            switch (i) {
                case 0:
                    if (nota <= 1) {
                        invalido = true;
                    }
                    break;
                case 1:
                    if (nota <= 2) {
                        invalido = true;
                    }
                    break;
                case 2:
                    if (nota <= 2) {
                        invalido = true;
                    }
                    break;
                case 3:
                    if (nota <= 1) {
                        invalido = true;
                    }
                    break;
            }

            if (invalido) {
                JOptionPane.showMessageDialog(this, "El estudiante no supera el mínimo requerido en una habilidad prerrequisita.");
                ctrl_estudiante.asignarNotaTutor(estudiante.getCodEstudiante(), -1);
                dispose();
                return;
            }

            sumaPrerrequisitos += nota;
        }

        // Calcular media de calidad del trabajo (resto de rúbricas no omitidas)
        for (int i = 4; i < panelesEvaluacion.size(); i++) {
            double nota = panelesEvaluacion.get(i).getNota();
            if (nota >= 0) {
                sumaCalidad += nota;
                countCalidad++;
            }
        }

        if (countCalidad == 0) {
            JOptionPane.showMessageDialog(this, "No se ha evaluado ninguna rúbrica de calidad del trabajo.");
            return;
        }

        // Calcular medias
        double mediaPrerrequisitos = sumaPrerrequisitos / countPrerrequisitos;
        double mediaCalidad = sumaCalidad / countCalidad;

        // Nota final ponderada
        double notaFinal = (mediaPrerrequisitos * 0.3) + (mediaCalidad * 0.7);
        notaFinal = Math.round(notaFinal * 10.0) / 10.0;

        // Mostrar resultado
        ctrl_estudiante.asignarNotaTutor(estudiante.getCodEstudiante(), notaFinal);
        JOptionPane.showMessageDialog(this, "Evaluación finalizada.\nNota final del tutor: " + String.format("%.1f", notaFinal));
        dispose();
    }

    private void actualizarEstadoBotones() {
        bt_anterior.setEnabled(indiceActual > 0);
        if (indiceActual == panelesEvaluacion.size() - 1) {
            bt_siguiente.setText("FINALIZAR");
        } else {
            bt_siguiente.setText("SIGUIENTE");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txt_nombreAlumno = new javax.swing.JLabel();
        bt_siguiente = new javax.swing.JButton();
        bt_anterior = new javax.swing.JButton();
        combobox_notas = new javax.swing.JComboBox<>();
        bt_omitir = new javax.swing.JButton();
        panelTutor = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel1.setText("Estás evaluando al Alumno: ");

        txt_nombreAlumno.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        txt_nombreAlumno.setText("x");

        bt_siguiente.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        bt_siguiente.setText("SIGUIENTE");
        bt_siguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bt_siguienteActionPerformed(evt);
            }
        });

        bt_anterior.setFont(new java.awt.Font("Helvetica Neue", 1, 14)); // NOI18N
        bt_anterior.setText("ANTERIOR");

        combobox_notas.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N

        bt_omitir.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        bt_omitir.setText("NO APLICA");

        javax.swing.GroupLayout panelTutorLayout = new javax.swing.GroupLayout(panelTutor);
        panelTutor.setLayout(panelTutorLayout);
        panelTutorLayout.setHorizontalGroup(
            panelTutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1018, Short.MAX_VALUE)
        );
        panelTutorLayout.setVerticalGroup(
            panelTutorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 451, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(txt_nombreAlumno, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(combobox_notas, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(bt_anterior)
                        .addGap(56, 56, 56)
                        .addComponent(bt_siguiente)))
                .addGap(18, 18, 18)
                .addComponent(bt_omitir)
                .addGap(251, 251, 251))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelTutor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txt_nombreAlumno))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelTutor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(combobox_notas, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_omitir))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bt_anterior, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bt_siguiente, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void bt_siguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bt_siguienteActionPerformed

    }//GEN-LAST:event_bt_siguienteActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JDialog_EvaluacionTutor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JDialog_EvaluacionTutor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JDialog_EvaluacionTutor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JDialog_EvaluacionTutor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JDialog_EvaluacionTutor dialog = new JDialog_EvaluacionTutor(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bt_anterior;
    private javax.swing.JButton bt_omitir;
    private javax.swing.JButton bt_siguiente;
    private javax.swing.JComboBox<Double> combobox_notas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel panelTutor;
    private javax.swing.JLabel txt_nombreAlumno;
    // End of variables declaration//GEN-END:variables
}
