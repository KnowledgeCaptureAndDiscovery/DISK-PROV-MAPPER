
package io.github.knowledgecaptureanddiscovery.diskprovmapper.extractor;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "name",
        "label",
        "value"
})
public class VariableValue {

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
     * The label Schema
     * <p>
     *
     *
     */
    @JsonProperty("label")
    private String label = "";
    /**
     * The value Schema
     * <p>
     *
     *
     */
    @JsonProperty("value")
    private List<Object> value;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public VariableValue() {
    }

    /**
     *
     * @param name
     * @param id
     * @param label
     * @param value
     */
    public VariableValue(String id, String name, String label, List<Object> value) {
        super();
        this.id = id;
        this.name = name;
        this.label = label;
        this.value = value;
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
     * The label Schema
     * <p>
     *
     *
     */
    @JsonProperty("label")
    public String getLabel() {
        return label;
    }

    /**
     * The label Schema
     * <p>
     *
     *
     */
    @JsonProperty("label")
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * The value Schema
     * <p>
     *
     *
     */
    @JsonProperty("value")
    public List<Object> getValue() {
        return value;
    }

    /**
     * The value Schema
     * <p>
     *
     *
     */
    @JsonProperty("value")
    public void setValue(List<Object> value) {
        this.value = value;
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
