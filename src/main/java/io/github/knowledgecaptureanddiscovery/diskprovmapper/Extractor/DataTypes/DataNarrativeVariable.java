
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
 * A representation of a person, company, organization, or place
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "variables"
})
public class DataNarrativeVariable {

    @JsonProperty("variables")
    private List<VariableItem> variables;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new LinkedHashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     *
     */
    public DataNarrativeVariable() {
    }

    /**
     *
     * @param variables
     */
    public DataNarrativeVariable(List<VariableItem> variables) {
        super();
        this.variables = variables;
    }

    @JsonProperty("variables")
    public List<VariableItem> getVariables() {
        return variables;
    }

    @JsonProperty("variables")
    public void setVariables(List<VariableItem> variables) {
        this.variables = variables;
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
