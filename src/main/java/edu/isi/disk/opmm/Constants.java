package edu.isi.disk.opmm;

/**
 *
 * @author dgarijo
 */

// TO DO: Cleanup of the deprecated properties is needed

public class Constants {
        // OPMW
        public static final String OPMW_WORKFLOW_EXECUTION_ARTIFACT_LOCALNAME = "WorkflowExecutionArtifact";
        public static final String OPMW_WORKFLOW_EXECUTION_ARTIFACT_URL = "http://www.opmw.org/ontology/WorkflowExecutionArtifact";
        public static final String OPMW_WORKFLOW_EXECUTION_PARAMETER_VARIABLE_LOCAL_NAME = "ParameterVariable";
        public static final String OPMW_WORKFLOW_EXECUTION_PARAMETER_VARIABLE_URL = "http://www.opmw.org/ontology/ParameterVariable";
        public static final String OPMW_WORKFLOW_EXECUTION_DATA_VARIABLE_LOCAL_NAME = "DataVariable";
        public static final String OPMW_WORKFLOW_EXECUTION_DATA_VARIABLE_URL = "http://www.opmw.org/ontology/DataVariable";
        // Entities types
        public static String DISK_ONTOLOGY_TRIGGER_LINE_OF_INQUIRY_LOCALNAME = "TriggeredLineOfInquiry";
        public static final String DCAT_DATASET_LOCALNAME = "Dataset";
        public static final String DCAT_CATALOG_LOCALNAME = "Catalog";
        public static final String DCAT_RESOURCE_LOCALNAME = "Resource";
        public static final String DCAT_DATASET_URL = "http://www.w3.org/ns/dcat#Dataset";
        public static final String DCAT_CATALOG_URL = "http://www.w3.org/ns/dcat#Catalog";
        public static final String DCAT_RESOURCE_URL = "http://www.w3.org/ns/dcat#Resource";

        // Entity local names

        // BUNDLE NAMES
        public static String QUESTION_BUNDLE_NAME = "questionBundle";
        public static String HYPOTHESIS_BUNDLE_NAME = "hypothesisBundle";
        public static String LOIS_BUNDLE_NAME = "loisBundle";
        public static String TLOIS_BUNDLE_NAME = "triggerBundle";
        // Variables binding names
        public static final String QUESTION_VARIABLES_BINDING = "questionVariablesBinding";
        public static final String HYPOTHESIS_VARIABLES_BINDING = "hypothesisVariablesBinding";
        public static final String META_WORKFLOW_VARIABLES_BINDING = "metaWorkflowVariablesBinding";
        public static final String META_WORKFLOW_INPUTS_BINDING = "metaWorkflowInputsFileBinding";
        public static final String META_WORKFLOW_OUTPUTS_BINDING = "metaWorkflowOutputsFileBinding";
        public static final String META_WORKFLOW_VARIABLE_BINDING_LOCAL_NAME = "metaWorkflowVariableBinding";
        public static final String META_WORKFLOW_VARIABLE_BINDING_LABEL = "metaWorkflowVariableBinding";
        public static final String WORKFLOW_VARIABLE_BINDING_LOCAL_NAME = "workflowVariableBinding";
        public static final String WORKFLOW_VARIABLE_BINDING_LABEL = "workflowVariableBinding";

        // Confidence values
        public static final String DISK_ONTOLOGY_CONFIDENCE_REPORT_LOCALNAME = "ConfidenceReport";

        public static enum WORKFLOW_TYPE {
                META_WORKFLOW, WORKFLOW
        }

        // Activity constants: local names, labels, etc

        // Select Question
        public static final String ACTIVITY_SELECT_QUESTION_LABEL = "Select the question";
        public static final String ACTIVITY_SELECT_QUESTION_LOCALNAME = "select_question";
        // Select Hypothesis
        public static final String ACTIVITY_SELECT_HYPOTHESIS_LABEL = "Select the hypothesis";
        public static final String ACTIVITY_SELECT_HYPOTHESIS_LOCALNAME = "select_hypothesis";
        // Select Workflow
        public static final String ACTIVITY_SELECT_WORKFLOW_LABEL = "Select the workflow";
        public static final String ACTIVITY_SELECT_WORKFLOW_VARIABLE_LABEL = "Select the variable workflow";
        // Select Workflow Variable
        public static final String ACTIVITY_SELECT_WORKFLOW_VARIABLE_LOCALNAME = "select_variable_workflow";
        public static final String ACTIVITY_SELECT_WORKFLOW_LOCALNAME = "select_workflow";

        // Create Question
        public static final String ACTIVITY_CREATE_QUESTION_LABEL = "Create the question";
        public static final String ACTIVITY_CREATE_QUESTION_LOCALNAME = "createQuestion";
        public static final String ACTIVITY_CREATE_QUESTION_DESCRIPTION = "The agent creates a Question and each of the variables of them. The agent then creates a hypothesis.";

        // Create Hypothesis
        public static final String ACTIVITY_CREATE_HYPOTHESIS_LABEL = "Create the hypothesis";
        public static final String ACTIVITY_CREATE_HYPOTHESIS_LOCALNAME = "createHypothesis";
        public static final String ACTIVITY_CREATE_HYPOTHESIS_DESCRIPTION = "The agent creates a Hypothesis and each of the variables of them. The agent then creates a question.";

}
