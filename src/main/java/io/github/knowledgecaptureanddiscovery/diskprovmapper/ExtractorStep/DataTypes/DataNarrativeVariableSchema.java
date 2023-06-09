
package io.github.knowledgecaptureanddiscovery.diskprovmapper.ExtractorStep.DataTypes;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * A representation of the Entity extracted from a PROV document. Each entity has a set of values.
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "views",
    "groupNames",
    "groups"
})
public class DataNarrativeVariableSchema {

    @JsonProperty("views")
    private List<String> views = new ArrayList<String>();
    /**
     * A list contains the Entities name which were grouped by the type of entity they represent
     * <p>
     * 
     * 
     */
    @JsonProperty("groupNames")
    private List<String> groupNames = new ArrayList<String>();
    @JsonProperty("groups")
    private List<EntityGroup> groups = new ArrayList<EntityGroup>();

    @JsonProperty("views")
    public List<String> getViews() {
        return views;
    }

    @JsonProperty("views")
    public void setViews(List<String> views) {
        this.views = views;
    }

    /**
     * A list contains the Entities name which were grouped by the type of entity they represent
     * <p>
     * 
     * 
     */
    @JsonProperty("groupNames")
    public List<String> getGroupNames() {
        return groupNames;
    }

    /**
     * A list contains the Entities name which were grouped by the type of entity they represent
     * <p>
     * 
     * 
     */
    @JsonProperty("groupNames")
    public void setGroupNames(List<String> groupNames) {
        this.groupNames = groupNames;
    }

    @JsonProperty("groups")
    public List<EntityGroup> getGroups() {
        return groups;
    }

    @JsonProperty("groups")
    public void setGroups(List<EntityGroup> groups) {
        this.groups = groups;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(DataNarrativeVariableSchema.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("views");
        sb.append('=');
        sb.append(((this.views == null)?"<null>":this.views));
        sb.append(',');
        sb.append("groupNames");
        sb.append('=');
        sb.append(((this.groupNames == null)?"<null>":this.groupNames));
        sb.append(',');
        sb.append("groups");
        sb.append('=');
        sb.append(((this.groups == null)?"<null>":this.groups));
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
        result = ((result* 31)+((this.groupNames == null)? 0 :this.groupNames.hashCode()));
        result = ((result* 31)+((this.groups == null)? 0 :this.groups.hashCode()));
        result = ((result* 31)+((this.views == null)? 0 :this.views.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof DataNarrativeVariableSchema) == false) {
            return false;
        }
        DataNarrativeVariableSchema rhs = ((DataNarrativeVariableSchema) other);
        return ((((this.groupNames == rhs.groupNames)||((this.groupNames!= null)&&this.groupNames.equals(rhs.groupNames)))&&((this.groups == rhs.groups)||((this.groups!= null)&&this.groups.equals(rhs.groups))))&&((this.views == rhs.views)||((this.views!= null)&&this.views.equals(rhs.views))));
    }

}
