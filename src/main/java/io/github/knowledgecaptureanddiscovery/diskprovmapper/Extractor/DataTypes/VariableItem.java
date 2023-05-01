
package io.github.knowledgecaptureanddiscovery.diskprovmapper.Extractor.DataTypes;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * A variable item
 * <p>
 *
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name",
        "comment",
        "values"
})
public class VariableItem {

    /**
     * The id Schema
     * <p>
     *
     *
     */
    @JsonProperty("id")
    private String id = "";
    /**
     * The name Schema
     * <p>
     *
     *
     */
    @JsonProperty("name")
    private String name = "";
    /**
     * The comment Schema
     * <p>
     *
     *
     */
    @JsonProperty("comment")
    private String comment = "";
    @JsonProperty("values")
    private List<VariableValue> values;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public VariableItem() {
    }

    /**
     *
     * @param values
     * @param name
     * @param comment
     * @param id
     */
    public VariableItem(String id, String name, String comment, List<VariableValue> values) {
        super();
        this.id = id;
        this.name = name;
        this.comment = comment;
        this.values = values;
    }

    /**
     * The id Schema
     * <p>
     *
     *
     */
    @JsonProperty("id")
    public String getId() {
        return id;
    }

    /**
     * The id Schema
     * <p>
     *
     *
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    /**
     * The name Schema
     * <p>
     *
     *
     */
    @JsonProperty("name")
    public String getName() {
        return name;
    }

    /**
     * The name Schema
     * <p>
     *
     *
     */
    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The comment Schema
     * <p>
     *
     *
     */
    @JsonProperty("comment")
    public String getComment() {
        return comment;
    }

    /**
     * The comment Schema
     * <p>
     *
     *
     */
    @JsonProperty("comment")
    public void setComment(String comment) {
        this.comment = comment;
    }

    @JsonProperty("values")
    public List<VariableValue> getValues() {
        return values;
    }

    @JsonProperty("values")
    public void setValues(List<VariableValue> values) {
        this.values = values;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
