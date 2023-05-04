package io.github.knowledgecaptureanddiscovery.diskprovmapper.Generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.crypto.Data;

import org.openprovenance.prov.model.Value;

import com.hubspot.jinjava.Jinjava;

import io.github.knowledgecaptureanddiscovery.diskprovmapper.ExtractorStep.DataTypes.DataNarrativeVariableSchema;
import io.github.knowledgecaptureanddiscovery.diskprovmapper.ExtractorStep.DataTypes.EntityGroup;
import io.github.knowledgecaptureanddiscovery.diskprovmapper.ExtractorStep.DataTypes.EntityItem;

public class DataNarrativeGenerator {
  String narrative;

  public String getNarrative() {
    return narrative;
  }

  public void setNarrative(String narrative) {
    this.narrative = narrative;
  }

  Jinjava jinjava = new Jinjava();

  public DataNarrativeGenerator(DataNarrativeVariableSchema schema, String dataNarrativeTemplate) {
    List<EntityGroup> entityGroups = schema.getGroups();

    Map<String, Object> context = new HashMap<String, Object>();
    for (EntityGroup entityGroup : entityGroups) {
      List<EntityItem> entityItems = entityGroup.getEntities();
      String key = entityGroup.getName();
      if (entityItems.size() > 1) {
        List<EntityItem> values = new ArrayList<EntityItem>();
        for (EntityItem item : entityItems) {
          context.put(key, item);
          values.add(item);
        }
        context.put(key, values);
      } else if (entityItems.size() == 1) {
        EntityItem item = entityItems.get(0);
        context.put(key, item);
      }
    }

    narrative = jinjava.render(dataNarrativeTemplate, context);
  }
}
