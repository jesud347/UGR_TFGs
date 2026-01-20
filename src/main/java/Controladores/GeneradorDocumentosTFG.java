package Controladores;

import org.apache.poi.xwpf.usermodel.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GeneradorDocumentosTFG {

    public static void generarDocumentoTFG(
            String tutor, String curso, String convocatoria, String grado,
            String estudiante, String tituloTFG, String notaFinal,
            String numTutores, String dia, String mes, String anio) {

        URL url = GeneradorDocumentosTFG.class.getClassLoader().getResource("Recursos/plantillaCertificadoTFG.docx");
        System.out.println("Ruta encontrada: " + url);

        try (InputStream fis = GeneradorDocumentosTFG.class
                .getClassLoader()
                .getResourceAsStream("Recursos/plantillaCertificadoTFG.docx")) {

            if (fis == null) {
                throw new FileNotFoundException("No se encontró la plantilla en Recursos/plantillaCertificadoTFG.docx");
            }

            XWPFDocument document = new XWPFDocument(fis);

            // Mapa de variables a reemplazar
            Map<String, String> variables = new HashMap<>();
            variables.put("{{tutor}}", tutor);
            variables.put("{{curso}}", curso);
            variables.put("{{convocatoria}}", convocatoria);
            variables.put("{{grado}}", grado);
            variables.put("{{estudiante}}", estudiante);
            variables.put("{{tituloTFG}}", tituloTFG);
            variables.put("{{notaFinal}}", notaFinal);
            variables.put("{{numTutores}}", numTutores);
            variables.put("{{dia}}", dia);
            variables.put("{{mes}}", mes);
            variables.put("{{anio}}", anio);

            // Reemplazar en todos los párrafos del documento
            for (XWPFParagraph paragraph : document.getParagraphs()) {
                reemplazarVariablesEnParrafo(paragraph, variables);
            }

            // Reemplazar en todas las celdas de las tablas
            for (XWPFTable table : document.getTables()) {
                for (XWPFTableRow row : table.getRows()) {
                    for (XWPFTableCell cell : row.getTableCells()) {
                        for (XWPFParagraph paragraph : cell.getParagraphs()) {
                            reemplazarVariablesEnParrafo(paragraph, variables);
                        }
                    }
                }
            }

            // Selección de ruta de guardado con JFileChooser
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar certificado de TFG");
            fileChooser.setSelectedFile(new File("TFG_" + estudiante.replaceAll(" ", "_") + ".docx"));
            fileChooser.setFileFilter(new FileNameExtensionFilter("Documentos Word (*.docx)", "docx"));

            int userSelection = fileChooser.showSaveDialog(null);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File archivoDestino = fileChooser.getSelectedFile();

                // Añadir extensión si no se especifica
                if (!archivoDestino.getName().toLowerCase().endsWith(".docx")) {
                    archivoDestino = new File(archivoDestino.getAbsolutePath() + ".docx");
                }

                try (FileOutputStream out = new FileOutputStream(archivoDestino)) {
                    document.write(out);
                    System.out.println("Documento guardado en: " + archivoDestino.getAbsolutePath());
                }
            } else {
                System.out.println("El usuario canceló el guardado del documento.");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void reemplazarVariablesEnParrafo(XWPFParagraph paragraph, Map<String, String> variables) {
        StringBuilder textoParrafo = new StringBuilder();
        List<XWPFRun> runs = paragraph.getRuns();

        if (runs == null || runs.isEmpty()) {
            return;
        }

        // Reconstruir el texto completo del párrafo
        for (XWPFRun run : runs) {
            String text = run.getText(0);
            if (text != null) {
                textoParrafo.append(text);
            }
        }

        String textoFinal = textoParrafo.toString();

        // Reemplazar todas las variables
        for (Map.Entry<String, String> entry : variables.entrySet()) {
            textoFinal = textoFinal.replace(entry.getKey(), entry.getValue());
        }

        // Borrar todos los runs existentes
        int numRuns = runs.size();
        for (int i = numRuns - 1; i >= 0; i--) {
            paragraph.removeRun(i);
        }

        // Escribir el texto modificado en un nuevo run
        XWPFRun nuevoRun = paragraph.createRun();
        nuevoRun.setText(textoFinal);
    }
}