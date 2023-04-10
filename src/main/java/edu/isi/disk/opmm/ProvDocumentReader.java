package edu.isi.disk.opmm;

import java.util.List;

import org.openprovenance.prov.model.Bundle;
import org.openprovenance.prov.model.Document;
import org.openprovenance.prov.model.Entity;
import org.openprovenance.prov.model.ProvUtilities;
import org.openprovenance.prov.model.Statement;
import org.openprovenance.prov.model.StatementOrBundle;
import org.openprovenance.prov.model.Type;

public class ProvDocumentReader {
  ProvUtilities u = new ProvUtilities();
  List<Bundle> bundles;

  public ProvDocumentReader(Document doc) {
    bundles = u.getNamedBundle(doc);
  }

  public Bundle getBundle(String bundleId) {
    for (Bundle bundle : bundles) {
      if (bundle.getId().getLocalPart().equals(bundleId)) {
        return bundle;
      }
    }
    return null;
  }

  /**
   * Get the entity with the given local name from the given bundle.
   *
   * @param bundle    A bundle from the document
   * @param localname The local name of the entity
   * @return The entity with the given local name
   * @throws RuntimeException if the entity is not found
   */
  public Entity getEntityByLocalName(Bundle bundle, String localname) throws RuntimeException {
    for (Entity entity : u.getEntity(bundle)) {
      String entityLocalName = entity.getId().getLocalPart();
      if (entityLocalName.equals(localname)) {
        return entity;
      }
    }
    throw new RuntimeException("Record not found: " + localname);
  }

  /**
   * Get the entity with the give type from the given bundle.
   *
   * @param bundle A bundle from the document
   * @param type   The type of the entity
   * @return The entity with the given type
   * @throws RuntimeException if the entity is not found
   *
   */
  public Entity getEntityByType(Bundle bundle, String type) throws RuntimeException {
    for (Entity entity : u.getEntity(bundle)) {
      List<Type> types = entity.getType();
      for (Type t : types) {
        if (t.getValue().equals(type)) {
          return entity;
        }
      }
    }
    throw new RuntimeException("Record not found: " + type);
  }
}