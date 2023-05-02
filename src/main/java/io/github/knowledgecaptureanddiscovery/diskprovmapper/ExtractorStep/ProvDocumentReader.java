package io.github.knowledgecaptureanddiscovery.diskprovmapper.ExtractorStep;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.openprovenance.prov.model.Bundle;
import org.openprovenance.prov.model.Document;
import org.openprovenance.prov.model.Entity;
import org.openprovenance.prov.model.ProvUtilities;
import org.openprovenance.prov.model.QualifiedName;
import org.openprovenance.prov.model.Type;

/**
 * This class provides methods to read a PROV document and extract information
 * from it.
 * The
 */
public class ProvDocumentReader {
  ProvUtilities u = new ProvUtilities();
  public List<Bundle> bundles;

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
  public Entity getEntityByType(Bundle bundle, QualifiedName type) throws RuntimeException {
    for (Entity entity : u.getEntity(bundle)) {
      List<Type> types = entity.getType();
      for (Type t : types) {
        if (t.getValue().toString().equals(type.toString())) {
          return entity;
        }
      }
    }
    throw new RuntimeException("Record not found: " + type);
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
        Object entityType = t.getValue();
        if (entityType.equals(type)) {
          return entity;
        }
      }
    }
    throw new RuntimeException("Record not found: " + type);
  }

  /**
   * Get a List entity with the give type from the given bundle.
   *
   * @param bundle A bundle from the document
   * @param type   The type of the entity
   * @return The entity with the given type
   * @throws RuntimeException if the entity is not found
   *
   */
  public List<Entity> getEntitiesByType(Bundle bundle, QualifiedName type) throws RuntimeException {
    List<Entity> entities = new ArrayList<Entity>();
    for (Entity entity : u.getEntity(bundle)) {
      List<Type> types = entity.getType();
      for (Type t : types) {
        if (t.getValue().toString().equals(type.toString())) {
          entities.add(entity);
        }
      }
    }
    return entities;
  }

  /**
   * Get a List entity with the give type from the given bundle.
   *
   * @param bundle A bundle from the document
   * @param type   The type of the entity
   * @return The entity with the given type
   * @throws RuntimeException if the entity is not found
   *
   */
  public List<Entity> getEntitiesByType(Bundle bundle, String type) throws RuntimeException {
    List<Entity> entities = new ArrayList<Entity>();
    for (Entity entity : u.getEntity(bundle)) {
      List<Type> types = entity.getType();
      for (Type t : types) {
        if (t.getValue().equals(type)) {
          entities.add(entity);
        }
      }
    }
    return entities;
  }

  /**
   * Get a List entity with the give type from the given bundle.
   *
   * @param bundle A bundle from the document
   * @param type   The type of the entity
   * @return The entity with the given type
   * @throws RuntimeException if the entity is not found
   *
   */
  public List<Entity> getEntitiesByType(Bundle bundle, Type type) throws RuntimeException {
    List<Entity> entities = new ArrayList<Entity>();
    for (Entity entity : u.getEntity(bundle)) {
      List<Type> types = entity.getType();
      for (Type t : types) {
        if (t.equals(type)) {
          entities.add(entity);
        }
      }
    }
    return entities;
  }

  /**
   * Get all the types of the given bundle.
   *
   * @param bundle
   * @return
   */
  public List<Type> getTypesOfBundle(Bundle bundle) {
    List<Type> types = new ArrayList<Type>();
    for (Entity entity : u.getEntity(bundle)) {
      List<Type> type = entity.getType();
      for (Type t : type) {
        if (!types.contains(t))
          types.add(t);
      }
    }
    return types;
  }

  /**
   * Create a map of types from the given bundle.
   *
   * @return
   */
  public HashMap<String, List<Entity>> createTypeMap(Bundle bundle) {
    HashMap<String, List<Entity>> typeMap = new HashMap<String, List<Entity>>();
    List<Type> types = getTypesOfBundle(bundle);
    for (Type type : types) {
      List<Entity> entities = getEntitiesByType(bundle, type);
      typeMap.put(type.getValue().toString(), entities);
    }
    return typeMap;
  }
}