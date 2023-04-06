package edu.isi.disk.opmm;

import java.util.HashMap;
import java.util.List;

import org.openprovenance.prov.model.Bundle;
import org.openprovenance.prov.model.Document;
import org.openprovenance.prov.model.Entity;
import org.openprovenance.prov.model.Statement;
import org.openprovenance.prov.model.StatementOrBundle.Kind;

public class DataNarrative {
  Document document;
  Bundle loiBundle;
  Bundle tloiBundle;
  Bundle hypothesisBundle;
  Bundle questionBundle;

  String HYPOTHESIS_VARIABLES_BINDING = "hypothesisVariablesBinding";
  String QUESTION_VARIABLES_BINDING = "questionVariablesBinding";
  String META_WORKFLOW_VARIABLES_BINDING = "metaWorkflowVariablesBinding";

  String LOIS_BUNDLE_IDENTIFIER = "loisBundle";
  String TLOIS_BUNDLE_IDENTIFIER = "triggerBundle";
  String HYPOTHESIS_BUNDLE_IDENTIFIER = "hypothesisBundle";
  String QUESTION_BUNDLE_IDENTIFIER = "questionBundle";

  // Hashmap key: LOIS_BUNDLE_IDENTIFIER, TLOIS_BUNDLE_IDENTIFIER,
  // HYPOTHESIS_BUNDLE_IDENTIFIER, QUESTION_BUNDLE_IDENTIFIER
  // Hashmap value: Bundle
  HashMap<String, Bundle> bundleMap = new HashMap<String, Bundle>();

  public DataNarrative(Document document) {
    this.document = document;
    System.out.println("DataNarrative: " + document);

    document.getStatementOrBundle().forEach(statement -> {
      if (statement instanceof Bundle) {
        Bundle bundle = (Bundle) statement;
        Kind kind = bundle.getKind();
        if (kind.equals(Kind.PROV_BUNDLE)) {
          System.out.println("Bundle: " + bundle.getId());
          String bundleIdentifer = bundle.getId().getLocalPart();
          bundleMap.put(bundleIdentifer, bundle);
          if (bundleIdentifer.equals(LOIS_BUNDLE_IDENTIFIER)) {
            loiBundle = bundle;
          } else if (bundleIdentifer.equals(TLOIS_BUNDLE_IDENTIFIER)) {
            tloiBundle = bundle;
          } else if (bundleIdentifer.equals(HYPOTHESIS_BUNDLE_IDENTIFIER)) {
            hypothesisBundle = bundle;
          } else if (bundleIdentifer.equals(QUESTION_BUNDLE_IDENTIFIER)) {
            questionBundle = bundle;
          }
        }
      }
    });

    getRecordById(hypothesisBundle, HYPOTHESIS_BUNDLE_IDENTIFIER);

  }

  public static Statement getRecordById(Bundle bundle, String statementId) {
    /**
     * Get a record from a bundle by its id
     * 
     * @param bundle
     * @param statementId
     *
     */
    List<Statement> statements = bundle.getStatement();
    for (Statement statement : statements) {
      if (statement.getClass() == Entity.class) {
        Entity entity = (Entity) statement;
        if (entity.getId().getLocalPart().equals(statementId)) {
          System.out.println("Found entity: " + entity.getId().getLocalPart());
        }
      }
    }
    return null;
  }

}
