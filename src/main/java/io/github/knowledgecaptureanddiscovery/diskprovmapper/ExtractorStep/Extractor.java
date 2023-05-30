package io.github.knowledgecaptureanddiscovery.diskprovmapper.ExtractorStep;

import java.util.ArrayList;
import java.util.List;

import org.openprovenance.prov.model.Document;
import org.openprovenance.prov.model.Entity;
import org.openprovenance.prov.model.Type;
import org.openprovenance.prov.model.QualifiedName;

import io.github.knowledgecaptureanddiscovery.diskprovmapper.DocumentProv;
import io.github.knowledgecaptureanddiscovery.diskprovmapper.ExtractorStep.DataTypes.DataNarrativeVariableSchema;
import io.github.knowledgecaptureanddiscovery.diskprovmapper.ExtractorStep.DataTypes.EntityGroup;
import io.github.knowledgecaptureanddiscovery.diskprovmapper.ExtractorStep.DataTypes.EntityItem;

public class Extractor {
  Document document;
  List<EntityGroup> entityGroups = new ArrayList<EntityGroup>();
  DataNarrativeVariableSchema dataNarrativeVariable = new DataNarrativeVariableSchema();

  public Extractor(DocumentProv documentProv) {
    this.document = documentProv.document;
    ProvDocumentReader reader = new ProvDocumentReader(document);
    extracted(reader);
  }

  public Extractor(Document document) {
    this.document = document;
    ProvDocumentReader reader = new ProvDocumentReader(document);
    extracted(reader);
  }

  private void extracted(ProvDocumentReader reader) {
    List<String> views = new ArrayList<String>();
    List<String> groups = new ArrayList<String>();
    reader.bundles.forEach(bundle -> {
      views.add(bundle.getId().getLocalPart());
      System.out.println(bundle.getId().getLocalPart());
      List<Type> types = reader.getTypesOfBundle(bundle);
      for (Type type : types) {
        // Get all entities of the type
        List<Entity> entities = reader.getEntitiesByType(bundle, type);
        QualifiedName typeQn = (QualifiedName) type.getValue();
        // Create a group of entities for the type
        EntityGroup entityGroup = new EntityGroup();
        String entityGroupId = typeQn.getUri();
        String entityGroupName = typeQn.getLocalPart();
        String entityGroupCmment = typeQn.getUri();
        groups.add(entityGroupName);
        entityGroup.setId(entityGroupId);
        entityGroup.setName(entityGroupName);
        entityGroup.setComment(entityGroupCmment);
        entityGroup.setView(bundle.getId().getLocalPart());

        // Create a temporary list of entity items
        List<EntityItem> entityItems = new ArrayList<EntityItem>();
        for (Entity entity : entities) {
          EntityItem entityItem = new EntityItem();
          QualifiedName entityQn = entity.getId();
          String id = entityQn.getUri();
          String name = entity.getId().getLocalPart();
          String label = entity.getLabel().get(0).getValue();
          String comment = entity.getLabel().get(0).getValue();
          List<Object> value = null;
          if (entity.getValue() != null) {
            value = new ArrayList<Object>();
            value.add(0, entity.getValue().getConvertedValue());
          }
          entityItem.setId(id);
          entityItem.setName(name);
          entityItem.setLabel(label);
          entityItem.setComment(comment);
          entityItem.setValue(value);
          entityItems.add(entityItem);
        }
        // Set the list of entity items to the group
        entityGroup.setEntities(entityItems);
        entityGroups.add(entityGroup);
      }
      ;
    });
    dataNarrativeVariable.setViews(views);
    dataNarrativeVariable.setGroups(entityGroups);
    dataNarrativeVariable.setGroupNames(groups);
  }

  public DataNarrativeVariableSchema getDataNarrativeVariable() {
    return dataNarrativeVariable;
  }

  public void setDataNarrativeVariable(DataNarrativeVariableSchema dataNarrativeVariable) {
    this.dataNarrativeVariable = dataNarrativeVariable;
  }

  public List<EntityGroup> getEntityGroups() {
    return entityGroups;
  }

  public void setEntityGroups(List<EntityGroup> entityGroups) {
    this.entityGroups = entityGroups;
  }
}
