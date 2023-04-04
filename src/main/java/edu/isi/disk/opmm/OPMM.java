package edu.isi.disk.opmm;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.apache.jena.sparql.core.DatasetGraph;

public class OPMM {
    static Logger logger = null;

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        // Ontologies
        // Get the resources directory of the project relatived to the current directory
        String outputDirectory = "/Users/mosorio/repos/disk-project/DISK-OPMW-Mapper/examples/";
        String resourcesDirectory = "/Users/mosorio/repos/disk-project/DISK-OPMW-Mapper/src/main/resources/";
        String ontologiesDirectory = resourcesDirectory + "ontologies/";
        String diskTriples = resourcesDirectory + "sample_data/neuro-disk.nq";
        String opmwTriples = resourcesDirectory + "sample_data/neuro-opmw.nq";
        List<String> localOntologies = new ArrayList<>();
        localOntologies.add(ontologiesDirectory + "p-plan.owl");
        localOntologies.add(ontologiesDirectory + "opmv.ttl");

        String tLoisGraphId = "http://localhost:8080/disk-server/admin/tlois";
        String hypothesisGraphId = "http://localhost:8080/disk-server/admin/hypotheses";
        String loisGraphId = "http://localhost:8080/disk-server/admin/lois";
        String questionGraphId = "https://raw.githubusercontent.com/KnowledgeCaptureAndDiscovery/QuestionOntology/main/development/EnigmaQuestions.xml";
        String tloiId = "http://localhost:8080/disk-server/admin/tlois/TriggeredLOI-cUmY0H6u20gu";

        // Create a new database
        DatasetGraph diskDataset = ModelUtils.loadDatasetGraph(diskTriples);

        // Create the mapper
        Mapper mapper = new Mapper(diskDataset, tloiId, tLoisGraphId, hypothesisGraphId, loisGraphId,
                questionGraphId,
                localOntologies);
        DocumentProv documentProv = mapper.doc;
        // create directory if it doesn't exist
        File directory = new File(outputDirectory);
        if (!directory.exists()) {
            directory.mkdir();
        }
        String documentProvFilePath = directory.getAbsolutePath() + '/' + "document";
        documentProv.doConversions(documentProv.document, documentProvFilePath);

        // File documentProvFile = new File(documentProvFilePath);
        // DataNarrative narrative = new DataNarrative(documentProv.document);

        // mapper.opmwModel.write(new FileOutputStream(opmwTriples),
        // Lang.TURTLE.getName());
    }
}