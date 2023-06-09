package io.github.knowledgecaptureanddiscovery.diskprovmapper;

import java.io.OutputStream;

import org.openprovenance.prov.interop.InteropFramework;
import org.openprovenance.prov.interop.Formats.ProvFormat;
import org.openprovenance.prov.model.Document;
import org.openprovenance.prov.model.Namespace;
import org.openprovenance.prov.model.QualifiedName;
import org.openprovenance.prov.model.ProvFactory;

/**
 *
 */
public class DocumentProv {

    public static final String PROV_PREFIX = "prov";
    public static final String PROV_NS = "http://www.w3.org/ns/prov#";

    public static final String PROV_NEUROSCIENCE_NS = "http://provenance.isi.edu/disk/neuro/";
    public static final String PROV_NEUROSCIENCE_PREFIX = "provNeuroScience";

    public static final String PROV_NEUROSCIENCE_QUESTION_NS = "http://provenance.isi.edu/disk/neuro/question/";
    public static final String PROV_NEUROSCIENCE_QUESTION_PREFIX = "provNeuroScienceQuestion";

    public static final String PROV_NEUROSCIENCE_HYPOTHESIS_NS = "http://provenance.isi.edu/disk/neuro/hypothesis/";
    public static final String PROV_NEUROSCIENCE_HYPOTHESIS_PREFIX = "provNeuroScienceHypothesis";

    public static final String PROV_NEUROSCIENCE_TRIGGER_NS = "http://provenance.isi.edu/disk/neuro/tloi/";
    public static final String PROV_NEUROSCIENCE_TRIGGER_PREFIX = "provNeuroScienceTLOI";

    public static final String PROV_NEUROSCIENCE_LINE_NS = "http://provenance.isi.edu/disk/neuro/loi/";
    public static final String PROV_NEUROSCIENCE_LINE_PREFIX = "provNeuroScienceLOI";

    public static final String DISK_PREFIX = "neuroScienceDisk";
    public static final String DISK_NS = "http://localhost:8080/disk-server/admin/";

    public static final String DISK_ONTOLOGY_PREFIX = "disk";
    public static final String DISK_ONTOLOGY_NS = "http://disk-project.org/ontology/disk#";

    public static final String QUESTION_ONTOLOGY_PREFIX = "sqo";
    public static final String QUESTION_ONTOLOGY_NS = "https://w3id.org/sqo#";

    public static final String WINGS_ONTOLOGY_PREFIX = "wings";
    public static final String WINGS_ONTOLOGY_NS = "http://www.wings-workflows.org/ontology/";

    public static final String RDF_PREFIX = "rdf";
    public static final String RDF_NS = "http://www.w3.org/1999/02/22-rdf-syntax-ns#";

    public static final String RDFS_PREFIX = "rdfs";
    public static final String RDFS_NS = "http://www.w3.org/2000/01/rdf-schema#";

    // dcat
    public static final String DCAT_PREFIX = "dcat";
    public static final String DCAT_NS = "http://www.w3.org/ns/dcat#";

    public static final String DCTERMS_NS = "http://purl.org/dc/terms/";
    public static final String DCTERMS_PREFIX = "dcterms";

    public static final String OPMW_NS = "http://www.opmw.org/ontology/";
    public static final String OPMW_PREFIX = "opmw";

    public static final String SQO_PREFIX = "sqo";
    public static final String SQO_NS = "https://w3id.org/sqo";

    public static final String SQO_RESOURCE_PREFIX = "sqo-resource";
    public static final String SQO_RESOURCE_NS = "https://w3id.org/sqo/resource/";

    public ProvFactory factory;
    public Namespace ns;
    public Document document;

    public DocumentProv(ProvFactory pFactory) {
        this.factory = pFactory;
        this.document = pFactory.newDocument();
        ns = new Namespace();
        register(ns, PROV_NEUROSCIENCE_NS);
    }

    public static void register(Namespace localNs, String defaultNameSpace) {
        localNs.addKnownNamespaces();
        localNs.register(PROV_NEUROSCIENCE_QUESTION_PREFIX, PROV_NEUROSCIENCE_QUESTION_NS);
        localNs.register(PROV_NEUROSCIENCE_HYPOTHESIS_PREFIX, PROV_NEUROSCIENCE_HYPOTHESIS_NS);
        localNs.register(PROV_NEUROSCIENCE_TRIGGER_PREFIX, PROV_NEUROSCIENCE_TRIGGER_NS);
        localNs.register(PROV_NEUROSCIENCE_LINE_PREFIX, PROV_NEUROSCIENCE_LINE_NS);
        localNs.register(PROV_NEUROSCIENCE_PREFIX, PROV_NEUROSCIENCE_NS);
        localNs.register(DISK_PREFIX, DISK_NS);
        localNs.register(DISK_ONTOLOGY_PREFIX, DISK_ONTOLOGY_NS);
        localNs.register(QUESTION_ONTOLOGY_PREFIX, QUESTION_ONTOLOGY_NS);
        localNs.register(WINGS_ONTOLOGY_PREFIX, WINGS_ONTOLOGY_NS);
        localNs.register(RDFS_PREFIX, RDFS_NS);
        localNs.register(RDF_PREFIX, RDF_NS);
        localNs.register(DCTERMS_PREFIX, DCTERMS_NS);
        localNs.register(DCAT_PREFIX, DCAT_NS);
        localNs.register(OPMW_PREFIX, OPMW_NS);
        localNs.register(SQO_PREFIX, SQO_NS);
        localNs.register(SQO_RESOURCE_PREFIX, SQO_RESOURCE_NS);
        localNs.setDefaultNamespace(defaultNameSpace);
        System.out.println("Default namespace: " + localNs.getDefaultNamespace());
    }

    public void registerNamespace(String prefix, String iri) {
        ns.register(prefix, iri);
    }

    public QualifiedName qn(String n) {
        return ns.qualifiedName(PROV_NEUROSCIENCE_PREFIX, n, factory);
    }

    public QualifiedName qn(String n, String prefix) throws RuntimeException {
        try {
            return ns.qualifiedName(prefix, n, factory);
        } catch (Exception e) {
            throw new RuntimeException("Error in qn: " + n + " " + prefix);
        }
    }

    public QualifiedName qn(String n, String prefix_iri, String none) {
        String prefix = ns.getNamespaces().get(prefix_iri);
        return ns.qualifiedName(prefix, n, factory);
    }

    public void write(String file) {
        String provFile = file + ".provn";
        String pngFile = file + ".png";
        String ttlFile = file + ".ttl";
        String jsonFile = file + ".json";
        InteropFramework intF = new InteropFramework();
        intF.writeDocument(provFile, document);
        intF.writeDocument(pngFile, document);
        intF.writeDocument(ttlFile, document);
        intF.writeDocument(jsonFile, document);
    }

    public Document read(String file) {
        InteropFramework intF = new InteropFramework();
        return intF.readDocument(file);
    }

    public void write(OutputStream outputStream, String format) {
        InteropFramework intF = new InteropFramework();
        ProvFormat provFormat = ProvFormat.valueOf(format.toUpperCase());
        intF.writeDocument(outputStream, provFormat, document);
    }

    public void doFigure(Document document, String file) {
        InteropFramework intF = new InteropFramework();
        intF.writeDocument(file, document);
    }

    public void closingBanner() {
        System.out.println("*************************");
    }

    public void openingBanner() {
        System.out.println("*************************");
        System.out.println("* Converting document  ");
        System.out.println("*************************");
    }

    public static void main(String[] args) {
        if (args.length != 1)
            throw new UnsupportedOperationException("main to be called with filename");

    }

}
