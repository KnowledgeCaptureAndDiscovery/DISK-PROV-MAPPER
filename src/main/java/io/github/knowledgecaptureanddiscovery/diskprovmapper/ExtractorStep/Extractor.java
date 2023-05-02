package io.github.knowledgecaptureanddiscovery.diskprovmapper.ExtractorStep;

import java.util.ArrayList;
import java.util.List;

import org.openprovenance.prov.model.Document;
import org.openprovenance.prov.model.Entity;
import org.openprovenance.prov.model.Type;

import io.github.knowledgecaptureanddiscovery.diskprovmapper.ExtractorStep.DataTypes.DataNarrativeVariable;
import io.github.knowledgecaptureanddiscovery.diskprovmapper.ExtractorStep.DataTypes.VariableItem;
import io.github.knowledgecaptureanddiscovery.diskprovmapper.ExtractorStep.DataTypes.VariableValue;

public class Extractor {
  Document document;

  public Extractor(Document document) {
    this.document = document;
    ProvDocumentReader reader = new ProvDocumentReader(document);
    DataNarrativeVariable dataNarrativeVariable = new DataNarrativeVariable();
    reader.bundles.forEach(bundle -> {
      System.out.println(bundle.getId().getLocalPart());
      List<Type> types = reader.getTypesOfBundle(bundle);
      for (Type type : types) {
        VariableItem variableItem = new VariableItem();
        variableItem.setId(type.getValue().toString());
        List<Entity> entities = reader.getEntitiesByType(bundle, type);
        List<VariableValue> variableValues = new ArrayList<VariableValue>();
        for (Entity entity : entities) {
          String id = entity.getId().toString();
          String name = entity.getLabel().toString();
          String label = entity.getLabel().toString();
          String value = entity.getValue().toString();
          VariableValue variableValue = new VariableValue(id, name, label, value);
        }
        dataNarrativeVariable.getVariables().add(variableItem);
      }
      ;
    });
  }

}
