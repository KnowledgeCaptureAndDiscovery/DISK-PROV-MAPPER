
package io.github.knowledgecaptureanddiscovery.diskprovmapper.ExtractorStep.DataTypes;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * A group of Entity extracted from a PROV document. They are grouped by the type of entity they represent.
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "name",
    "comment",
    "view",
    "entities"
})
public class EntityGroup {

    /**
     * Entity type IRI extracted from the PROV document
     * <p>
     * 
     * 
     */
    @JsonProperty("id")
    private String id = "";
    /**
     * A machine readable name for the entity
     * <p>
     * 
     * 
     */
    @JsonProperty("name")
    private String name = "";
    /**
     * A human readable comment for the entity. For example: the description from the ontology
     * <p>
     * 
     * 
     */
    @JsonProperty("comment")
    private String comment = "";
    /**
     * Data Narrative view where the Entity must be. For example: questionVariable is in the  view
     * <p>
     * 
     * 
     */
    @JsonProperty("view")
    private String view = "";
    /**
     * A list of entities grouped by the type of entity they represent
     * <p>
     * 
     * 
     */
    @JsonProperty("entities")
    private List<EntityItem> entities = new ArrayList<EntityItem>();

    /**
     * Entity type IRI extracted from the PROV document
     * <p>
     * 
     * 
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * Entity type IRI extracted from the PROV document
     * <p>
     * 
     * 
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    /**
     * A machine readable name for the entity
     * <p>
     * 
     * 
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * A machine readable name for the entity
     * <p>
     * 
     * 
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * A human readable comment for the entity. For example: the description from the ontology
     * <p>
     * 
     * 
     */
    @JsonProperty("comment")
    public String getComment() {
        return comment;
    }

    /**
     * A human readable comment for the entity. For example: the description from the ontology
     * <p>
     * 
     * 
     */
    @JsonProperty("comment")
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * Data Narrative view where the Entity must be. For example: questionVariable is in the  view
     * <p>
     * 
     * 
     */
    @JsonProperty("view")
    public String getView() {
        return view;
    }

    /**
     * Data Narrative view where the Entity must be. For example: questionVariable is in the  view
     * <p>
     * 
     * 
     */
    @JsonProperty("view")
    public void setView(String view) {
        this.view = view;
    }

    /**
     * A list of entities grouped by the type of entity they represent
     * <p>
     * 
     * 
     */
    @JsonProperty("entities")
    public List<EntityItem> getEntities() {
        return entities;
    }

    /**
     * A list of entities grouped by the type of entity they represent
     * <p>
     * 
     * 
     */
    @JsonProperty("entities")
    public void setEntities(List<EntityItem> entities) {
        this.entities = entities;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(EntityGroup.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null)?"<null>":this.id));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null)?"<null>":this.name));
        sb.append(',');
        sb.append("comment");
        sb.append('=');
        sb.append(((this.comment == null)?"<null>":this.comment));
        sb.append(',');
        sb.append("view");
        sb.append('=');
        sb.append(((this.view == null)?"<null>":this.view));
        sb.append(',');
        sb.append("entities");
        sb.append('=');
        sb.append(((this.entities == null)?"<null>":this.entities));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.name == null)? 0 :this.name.hashCode()));
        result = ((result* 31)+((this.comment == null)? 0 :this.comment.hashCode()));
        result = ((result* 31)+((this.view == null)? 0 :this.view.hashCode()));
        result = ((result* 31)+((this.id == null)? 0 :this.id.hashCode()));
        result = ((result* 31)+((this.entities == null)? 0 :this.entities.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof EntityGroup) == false) {
            return false;
        }
        EntityGroup rhs = ((EntityGroup) other);
        return ((((((this.name == rhs.name)||((this.name!= null)&&this.name.equals(rhs.name)))&&((this.comment == rhs.comment)||((this.comment!= null)&&this.comment.equals(rhs.comment))))&&((this.view == rhs.view)||((this.view!= null)&&this.view.equals(rhs.view))))&&((this.id == rhs.id)||((this.id!= null)&&this.id.equals(rhs.id))))&&((this.entities == rhs.entities)||((this.entities!= null)&&this.entities.equals(rhs.entities))));
    }

}
