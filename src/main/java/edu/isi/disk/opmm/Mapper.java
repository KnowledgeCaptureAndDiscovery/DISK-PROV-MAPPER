package edu.isi.disk.opmm;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.diskproject.shared.classes.hypothesis.Hypothesis;
import org.diskproject.shared.classes.loi.LineOfInquiry;
import org.diskproject.shared.classes.loi.TriggeredLOI;
import org.diskproject.shared.classes.loi.WorkflowBindings;
import org.diskproject.shared.classes.question.Question;
import org.diskproject.shared.classes.question.QuestionVariable;
import org.diskproject.shared.classes.workflow.VariableBinding;
import org.openprovenance.prov.interop.InteropFramework;
import org.openprovenance.prov.model.ActedOnBehalfOf;
import org.openprovenance.prov.model.Activity;
import org.openprovenance.prov.model.Agent;
import org.openprovenance.prov.model.Bundle;
import org.openprovenance.prov.model.Entity;
import org.openprovenance.prov.model.HadMember;
import org.openprovenance.prov.model.Namespace;
// import prov
import org.openprovenance.prov.model.ProvFactory;
import org.openprovenance.prov.model.QualifiedName;
import org.openprovenance.prov.model.Used;
import org.openprovenance.prov.model.WasDerivedFrom;
import org.openprovenance.prov.model.WasGeneratedBy;
import org.openprovenance.prov.model.WasInformedBy;
import org.semanticweb.owlapi.model.IRI;

/**
 * @author Maximiliano Osorio
 *
 */
public class Mapper {
        /**
         * Most of these will be reused from the old code, because it works.
         * The mapper initializes the catalog and calls to the template exporter.
         */
        public static final String RDFS_COMMENT = "http://www.w3.org/2000/01/rdf-schema#comment";
        public static final String SKOS_DEFINITION = "http://www.w3.org/2004/02/skos/core#definition";
        public static final String PROV_DEFINITION = "http://www.w3.org/ns/prov#definition";
        public static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss yyyy-MM-dd");
        public static final List<String> DESCRIPTION_PROPERTIES = new ArrayList<>(Arrays.asList(
                        RDFS_COMMENT,
                        SKOS_DEFINITION,
                        PROV_DEFINITION));
        public DocumentProv prov = new DocumentProv(InteropFramework.getDefaultFactory());
        public ProvFactory pFactory = prov.factory;

        Bundle questionBundle = pFactory.newNamedBundle(prov.qn("questionBundle"), null);
        Bundle hypothesisBundle = pFactory.newNamedBundle(prov.qn("hypothesisBundle"), null);
        Bundle loisBundle = pFactory.newNamedBundle(prov.qn("loisBundle"), null);
        Bundle triggerBundle;

        DocumentProv doc;
        Integer level = 2;

        public Mapper(Hypothesis hypothesis, LineOfInquiry lineOfInquiry, List<TriggeredLOI> triggeredLOIList,
                        List<Question> questions)
                        throws ParseException {
                triggerBundle = pFactory.newNamedBundle(prov.qn("triggerBundle"),
                                null);
                doc = transformFromHypotehsis(hypothesis, lineOfInquiry, triggeredLOIList, questions);
        }

        public DocumentProv transformFromHypotehsis(Hypothesis hypothesis, LineOfInquiry lineOfInquiry,
                        List<TriggeredLOI> triggeredLOIList, List<Question> questions) throws ParseException {
                map(hypothesis, lineOfInquiry, triggeredLOIList, questions);
                Namespace triggerDefaultNamespace = new Namespace();
                DocumentProv.register(triggerDefaultNamespace, DocumentProv.PROV_NEUROSCIENCE_TRIGGER_NS);
                Namespace loisDefaultNamespace = new Namespace();
                DocumentProv.register(loisDefaultNamespace, DocumentProv.PROV_NEUROSCIENCE_LINE_NS);
                Namespace hypothesisDefaultNamespace = new Namespace();
                DocumentProv.register(hypothesisDefaultNamespace, DocumentProv.PROV_NEUROSCIENCE_HYPOTHESIS_NS);
                Namespace questionDefaultNamespace = new Namespace();
                DocumentProv.register(questionDefaultNamespace, DocumentProv.PROV_NEUROSCIENCE_QUESTION_NS);

                prov.document.setNamespace(prov.ns);
                questionBundle.setNamespace(questionDefaultNamespace);
                prov.document.getStatementOrBundle().add(questionBundle);
                hypothesisBundle.setNamespace(hypothesisDefaultNamespace);
                prov.document.getStatementOrBundle().add(hypothesisBundle);
                loisBundle.setNamespace(loisDefaultNamespace);
                prov.document.getStatementOrBundle().add(loisBundle);
                triggerBundle.setNamespace(triggerDefaultNamespace);
                prov.document.getStatementOrBundle().add(triggerBundle);
                return prov;
        }

        public void map(Hypothesis hypothesis, LineOfInquiry lineOfInquiry,
                        List<TriggeredLOI> triggeredLOIList, List<Question> questions) throws ParseException {
                Agent hvargas = createDummyAgent("hvargas", "Hernan Vargas");
                Agent neda = createDummyAgent("neda", "Neda Jahanshad");
                prov.document.getStatementOrBundle().add(hvargas);
                prov.document.getStatementOrBundle().add(neda);

                // find the question by the hypothesis.questionId
                Question question = null;
                for (Question q : questions) {
                        if (q.getId().equals(hypothesis.getQuestionId())) {
                                question = q;
                                break;
                        }
                }
                if (question == null) {
                        throw new RuntimeException("Question not found for hypothesis: " + hypothesis.getId());
                }

                Entity questionEntity = createQuestionEntity(questions.get(0));
                Entity hypothesisEntity = createHypothesisEntity(hypothesis);
                Entity lineOfInquiryEntity = createLineOfInquiryEntity(lineOfInquiry);

                String commentText = "The agent selects a Question and the values for each of the variables of the selected question. The agent then creates a hypothesis.";
                Activity createQuestionActivity = linkActivityEntities(hvargas, neda, questionEntity, questionBundle,
                                "createQuestion", "Create Question",
                                "The agent creates a Question and each of the variables of them. The agent then creates a hypothesis.");
                Activity createHypothesisActivity = linkActivityEntities(hvargas, neda, hypothesisEntity,
                                hypothesisBundle,
                                "createHypothesis", "Create Hypothesis", commentText);

                Entity questionVariablesBinding = level2QuestionAddVariables(question,
                                questionEntity, null,
                                createQuestionActivity);
                Entity hypothesisVariablesCollection = level2HypothesisAddVariables(hypothesis,
                                hypothesisEntity, questionVariablesBinding,
                                createHypothesisActivity);

                for (TriggeredLOI trigger : triggeredLOIList) {
                        Entity triggerEntity = createTiggerEntity(trigger);
                        level1WasDerived(questionEntity, hypothesisEntity, lineOfInquiryEntity,
                                        triggerEntity);
                        level2LineAdd(trigger, lineOfInquiry,
                                        hypothesisEntity, questionEntity,
                                        lineOfInquiryEntity,
                                        triggerEntity, hypothesisVariablesCollection);
                }
                System.out.println("Done");
        }

        private void addRunVariableBinding(Entity collection, String name, String value) {
                String variableLocalName = collection.getId().getLocalPart() + '/'
                                + name;
                Entity variableBindingEntity = pFactory.newEntity(
                                prov.qn(variableLocalName, DocumentProv.PROV_NEUROSCIENCE_TRIGGER_PREFIX),
                                name);
                if (value == null)
                        value = "null";

                variableBindingEntity.setValue(pFactory.newValue(value));

                HadMember hm2 = pFactory.newHadMember(
                                collection.getId(),
                                variableBindingEntity.getId());

                triggerBundle.getStatement().add(variableBindingEntity);
                triggerBundle.getStatement().add(hm2);
        }

        private void addVariableBindingWorkflow(Entity variablesBindingMetaWorkflowEntity,
                        Activity transformDataQuery) {
                Used dataQueryUsedVariable = pFactory.newUsed(null, variablesBindingMetaWorkflowEntity.getId(),
                                transformDataQuery.getId(), null,
                                null);
                triggerBundle.getStatement().add(dataQueryUsedVariable);
        }

        private Activity linkActivityEntities(Agent delegate, Agent responsible, Entity generated, Bundle bundle,
                        String activityName, String activityLabel, String commentText) {
                Activity activity = pFactory.newActivity(prov.qn(activityName), activityLabel);
                WasGeneratedBy wasGeneratedBy = pFactory.newWasGeneratedBy(null, generated.getId(), activity.getId(),
                                null, null);
                ActedOnBehalfOf actedOnBehalfOf = pFactory.newActedOnBehalfOf(null, delegate.getId(),
                                responsible.getId(),
                                activity.getId(), null);
                activity.getOther().add(pFactory.newOther(DocumentProv.RDFS_NS, "comment",
                                DocumentProv.RDFS_PREFIX, commentText,
                                pFactory.getName().XSD_NAME));
                prov.document.getStatementOrBundle().add(actedOnBehalfOf);
                bundle.getStatement().add(activity);
                bundle.getStatement().add(wasGeneratedBy);
                return activity;
        }

        public Agent createDummyAgent(String localName, String label) {
                Agent agent = pFactory.newAgent(prov.qn(localName), label);
                return agent;
        }

        private Activity createActivity(String localName, String name, String dateCreated, Bundle bundle) {
                Activity activity = pFactory.newActivity(
                                prov.qn(localName, DocumentProv.DISK_PREFIX),
                                name);
                // add date created to the activity
                activity.getOther().add(pFactory.newOther(DocumentProv.RDFS_NS, "comment",
                                DocumentProv.RDFS_PREFIX, dateCreated,
                                pFactory.getName().XSD_NAME));
                loisBundle.getStatement().add(activity);
                return activity;
        }

        private void createWorkflowEntity(WorkflowBindings workflowBinding,
                        Activity activitySavedLineOfInquiry, Activity activityWriteDataQueryTemplate,
                        Constants.WORKFLOW_TYPE type, Entity workflowSystem, Entity workflow,
                        Entity variableBindingWorkflow) {
                String workflowLabel = workflowBinding.getDescription();
                String workflowLocalName = "unknown";
                String workflowSystemName = workflowBinding.getSource();
                workflowSystem = pFactory.newEntity(
                                prov.qn(workflowSystemName, DocumentProv.WINGS_ONTOLOGY_PREFIX),
                                workflowSystemName);
                workflow = pFactory.newEntity(prov.qn(workflowLocalName, DocumentProv.WINGS_ONTOLOGY_PREFIX),
                                workflowLabel);
                String variableBindingLocalName = "workflowVariableBinding";
                String variableBindingLabel = "Workflow Variable Binding";
                if (type == Constants.WORKFLOW_TYPE.META_WORKFLOW) {
                        variableBindingLocalName = "metaWorkflowVariableBinding";
                        variableBindingLabel = "Meta Workflow Variable Binding";
                }
                variableBindingWorkflow = pFactory.newEntity(
                                prov.qn(variableBindingLocalName,
                                                DocumentProv.PROV_NEUROSCIENCE_LINE_PREFIX),
                                variableBindingLabel);
                handleWorkflowActivity(activityWriteDataQueryTemplate, activitySavedLineOfInquiry,
                                workflowSystem,
                                workflow,
                                variableBindingWorkflow,
                                Constants.ACTIVITY_SELECT_WORKFLOW_LABEL,
                                Constants.ACTIVITY_SELECT_WORKFLOW_VARIABLE_LABEL,
                                Constants.ACTIVITY_SELECT_WORKFLOW_VARIABLE_LOCALNAME,
                                Constants.ACTIVITY_SELECT_WORKFLOW_LOCALNAME,
                                workflowBinding);
        }

        public void level2LineAdd(TriggeredLOI triggerOfLineInquiry, LineOfInquiry lineOfInquiry,
                        Entity hypothesis, Entity questionEntity,
                        Entity lineOfInquiryEntity, Entity triggerEntity, Entity hypothesisVariablesCollection)
                        throws ParseException {

                String dateCreated = triggerOfLineInquiry.getDateCreated();
                String dataQuery = triggerOfLineInquiry.getDataQuery();
                String dataQueryDescription = triggerOfLineInquiry.getDataQueryExplanation();
                String dataSourceText = triggerOfLineInquiry.getDataSource();
                List<WorkflowBindings> metaworkflows = triggerOfLineInquiry.getMetaWorkflows();
                List<WorkflowBindings> workflows = triggerOfLineInquiry.getWorkflows();

                // create the activity for writing the data query

                Entity dataQueryTemplateEntity = pFactory.newEntity(
                                prov.qn("data_query", DocumentProv.PROV_NEUROSCIENCE_LINE_PREFIX),
                                dataQuery);
                Entity dataSource = pFactory.newEntity(
                                prov.qn("data_source", DocumentProv.PROV_NEUROSCIENCE_LINE_PREFIX), dataSourceText);
                loisBundle.getStatement().add(dataQueryTemplateEntity);
                loisBundle.getStatement().add(dataSource);

                Activity activitySelectQuestion = createActivity("select_question", "Select the question", dateCreated,
                                loisBundle);
                Activity activitySelectDataSource = createActivity("select_data_source", "Select the data source",
                                dateCreated, loisBundle);
                Activity activityWriteDataQueryTemplate = createActivity("write_data_query_template",
                                "Write data query template", dateCreated, loisBundle);
                Activity activitySavedLineOfInquiry = createActivity("saved_line_of_inquiry", "Saved line of inquiry",
                                dateCreated, loisBundle);

                // Activity relations
                WasInformedBy wib = pFactory.newWasInformedBy(null, activitySelectDataSource.getId(),
                                activitySelectQuestion.getId(), null);
                WasInformedBy wib1 = pFactory.newWasInformedBy(null, activityWriteDataQueryTemplate.getId(),
                                activitySelectDataSource.getId(), null);

                // 1. Select the question
                Used usedSelectQuestion = pFactory.newUsed(null, activitySelectQuestion.getId(), questionEntity.getId(),
                                null,
                                null);
                // TODO: generate the variables ???
                // 2. Select the data source
                Used usedSelectDataSource = pFactory.newUsed(null, activitySelectDataSource.getId(), dataSource.getId(),
                                null,
                                null);
                // 3. Write data query template
                Used usedWriteDataQueryTemplate = pFactory.newUsed(null, activityWriteDataQueryTemplate.getId(),
                                questionEntity.getId(), null, null);
                WasGeneratedBy wgbWriteDataQueryTemplate = pFactory.newWasGeneratedBy(null,
                                dataQueryTemplateEntity.getId(), activityWriteDataQueryTemplate.getId(), null, null);
                // 8. Save the line of inquiry
                WasGeneratedBy wgbSavedLineOfInquiry = pFactory.newWasGeneratedBy(null, lineOfInquiryEntity.getId(),
                                activitySavedLineOfInquiry.getId(), null, null);

                Entity workflowSystem = null;
                Entity workflow = null;
                Entity variableBindingWorkflow = null;
                Entity metaWorkflow = null;
                Entity variableBindingMetaWorkflow = null;

                for (WorkflowBindings workflowBinding : metaworkflows) {
                        createWorkflowEntity(workflowBinding, activityWriteDataQueryTemplate,
                                        activitySavedLineOfInquiry, Constants.WORKFLOW_TYPE.META_WORKFLOW,
                                        workflowSystem, metaWorkflow, variableBindingMetaWorkflow);

                }

                for (WorkflowBindings workflowBinding : workflows) {
                        createWorkflowEntity(workflowBinding, activityWriteDataQueryTemplate,
                                        activitySavedLineOfInquiry, Constants.WORKFLOW_TYPE.WORKFLOW, workflowSystem,
                                        workflow, variableBindingWorkflow);
                }
                loisBundle.getStatement().add(usedSelectQuestion);
                prov.document.getStatementOrBundle().add(usedSelectDataSource);
                loisBundle.getStatement().add(usedWriteDataQueryTemplate);
                loisBundle.getStatement().add(wgbWriteDataQueryTemplate);
                loisBundle.getStatement().add(wgbSavedLineOfInquiry);

                level2AddTrigger(triggerOfLineInquiry, triggerEntity,
                                lineOfInquiryEntity, dataSource,
                                dataQueryTemplateEntity,
                                workflowSystem,
                                workflow, metaWorkflow, variableBindingWorkflow, variableBindingMetaWorkflow,
                                hypothesisVariablesCollection);
        }

        private void level2AddTrigger(TriggeredLOI triggeredLOI, Entity triggerEntity,
                        Entity lineOfInquiryEntity, Entity dataSourceLoi,
                        Entity dataQueryTemplate, Entity workflowSystem, Entity workflowLoi, Entity metaWorkflowLoi,
                        Entity variablesBindingWorkflowEntity, Entity variablesBindingMetaWorkflowEntity,
                        Entity hypothesisVariableCollection) {

                // Create entities: dataQuery
                String dataQueryConcreteText = triggeredLOI.getDataQuery();
                Entity dataQueryConcreteEntity = pFactory.newEntity(
                                prov.qn("data_query", DocumentProv.PROV_NEUROSCIENCE_TRIGGER_PREFIX),
                                dataQueryConcreteText);
                // String dataQueryFilled = getLiteralByProperty(triggerResource,
                // tLoisGraphModel, "hasDataQuery");
                // Entity dataQuery = pFactory.newEntity(
                // prov.qn("dataQuery", DocumentProv.PROV_NEUROSCIENCE_TRIGGER_PREFIX),
                // dataQueryFilled);
                triggerBundle.getStatement().add(dataQueryConcreteEntity);

                String dataSource = triggeredLOI.getDataSource();
                // String dataSource = getLiteralByProperty(triggerResource, tLoisGraphModel,
                // "hasDataSource");
                Entity dataSourceEntity = pFactory.newEntity(
                                prov.qn("dataSource", DocumentProv.PROV_NEUROSCIENCE_TRIGGER_PREFIX), dataSource);
                triggerBundle.getStatement().add(dataSourceEntity);
                WasDerivedFrom dataSourceWasDerivedFrom = pFactory.newWasDerivedFrom(null, dataSourceEntity.getId(),
                                dataSourceLoi.getId());
                triggerBundle.getStatement().add(dataSourceWasDerivedFrom);

                // Create activities: transformDataQuery, executeDataQuery
                Activity transformDataQuery = pFactory.newActivity(prov.qn("transformDataQueryTemplate"),
                                "Replace the variables in the data query template with the variables of the hypothesis");
                Used transformDataQueryActivityUsed = pFactory.newUsed(null, dataQueryTemplate.getId(),
                                transformDataQuery.getId(), null,
                                null);
                WasGeneratedBy dataQueryWasGeneratedBy = pFactory.newWasGeneratedBy(null,
                                dataQueryConcreteEntity.getId(),
                                transformDataQuery.getId(), null, null);
                Activity executeDataQuery = pFactory.newActivity(prov.qn("executeDataQuery"), "Execute the data query");
                WasGeneratedBy toiWasGeneratedBy = pFactory.newWasGeneratedBy(null, triggerEntity.getId(),
                                transformDataQuery.getId(), null, null);
                triggerBundle.getStatement().add(toiWasGeneratedBy);
                triggerBundle.getStatement().add(executeDataQuery);
                triggerBundle.getStatement().add(transformDataQuery);
                triggerBundle.getStatement().add(transformDataQueryActivityUsed);
                triggerBundle.getStatement().add(dataQueryWasGeneratedBy);

                if (variablesBindingMetaWorkflowEntity != null) {
                        addVariableBindingWorkflow(variablesBindingMetaWorkflowEntity,
                                        transformDataQuery);
                }
                if (variablesBindingWorkflowEntity != null) {
                        addVariableBindingWorkflow(variablesBindingWorkflowEntity,
                                        transformDataQuery);
                }

                Activity createRunActivity = pFactory.newActivity(
                                prov.qn("createRun", DocumentProv.PROV_NEUROSCIENCE_TRIGGER_PREFIX),
                                "Create run");

                List<WorkflowBindings> metaworkflows = triggeredLOI.getMetaWorkflows();
                List<WorkflowBindings> workflows = triggeredLOI.getWorkflows();

                for (WorkflowBindings metaWorkflow : metaworkflows) {
                        String workflowLink = metaWorkflow.getWorkflowLink();
                        String workflowName = metaWorkflow.getWorkflow();
                        String workflowLocalName = workflowName;
                        List<VariableBinding> metaWorkflowBinding = metaWorkflow.getBindings();
                        if (workflowLocalName == null) {
                                workflowLocalName = "Unknown";
                        }
                        String workflowSystemName = metaWorkflow.getSource();
                        Entity metaWorkflowRun = pFactory.newEntity(
                                        prov.qn(workflowLocalName, DocumentProv.PROV_NEUROSCIENCE_TRIGGER_PREFIX),
                                        workflowName);
                        WasGeneratedBy metaWorkflowWasGeneratedBy = pFactory.newWasGeneratedBy(null,
                                        metaWorkflowRun.getId(), createRunActivity.getId(), null, null);
                        workflowSystem = pFactory.newEntity(
                                        prov.qn(workflowSystemName, DocumentProv.WINGS_ONTOLOGY_PREFIX),
                                        workflowSystemName);
                        triggerBundle.getStatement().add(metaWorkflowRun);
                        triggerBundle.getStatement().add(workflowSystem);
                        triggerBundle.getStatement().add(metaWorkflowWasGeneratedBy);
                        /**
                         * Handle the inputs of the Meta Workflow
                         */

                        // Step 1: Create the collection
                        String inputCollectionLocalName = triggerEntity.getId().getLocalPart() + '/'
                                        + Constants.META_WORKFLOW_INPUTS_BINDING;
                        Entity runInputBindingMetaWorkflow = pFactory.newEntity(
                                        prov.qn(inputCollectionLocalName,
                                                        DocumentProv.PROV_NEUROSCIENCE_TRIGGER_PREFIX),
                                        "Stores the run inputs for the Meta Workflow");
                        triggerBundle.getStatement().add(runInputBindingMetaWorkflow);
                        WasGeneratedBy runInputBindingMetaWorkflowWasGeneratedBy = pFactory.newWasGeneratedBy(null,
                                        runInputBindingMetaWorkflow.getId(), executeDataQuery.getId(), null,
                                        null);
                        triggerBundle.getStatement().add(runInputBindingMetaWorkflowWasGeneratedBy);

                        // TODO: Handle the files
                        // metaWorkflow.getRun().getFiles().forEach(file -> {
                        // addRunVariableBinding(runInputBindingMetaWorkflow, s, tLoisGraphModel);
                        // });

                        /**
                         * Handle the variables of the Meta Workflow
                         */
                        String variableCollectionLocalName = triggerEntity.getId().getLocalPart() + '/'
                                        + Constants.META_WORKFLOW_VARIABLES_BINDING;
                        Entity runVariableBindingMetaWorkflow = pFactory.newEntity(
                                        prov.qn(variableCollectionLocalName,
                                                        DocumentProv.PROV_NEUROSCIENCE_TRIGGER_PREFIX),
                                        "Stores the run parameters for the Meta Workflow");
                        metaWorkflowBinding.forEach(variableBinding -> {
                                addRunVariableBinding(hypothesisVariableCollection, variableBinding.getVariable(),
                                                variableBinding.getBinding());
                        });
                        triggerBundle.getStatement().add(runVariableBindingMetaWorkflow);
                        // Execute Data Query Activity generates the Variable Binding
                        WasGeneratedBy runVariableBindingMetaWorkflowWasGeneratedBy = pFactory.newWasGeneratedBy(null,
                                        runVariableBindingMetaWorkflow.getId(), executeDataQuery.getId(), null,
                                        null);
                        triggerBundle.getStatement().add(runVariableBindingMetaWorkflowWasGeneratedBy);

                        // Activity `createRunOnWorkflow` uses the Trigger Variable Binding
                        Used createRunUsedVariablesHypothesis = pFactory.newUsed(null,
                                        runVariableBindingMetaWorkflow.getId(),
                                        createRunActivity.getId(), null, null);
                        triggerBundle.getStatement().add(createRunUsedVariablesHypothesis);

                        // TODO: metaworkflowLOI is null. We lost the relationship between the LOI
                        // Workflow and the Triggered LOI
                        // Activity `createRunOnWorkflow` uses the Meta Workflow
                        // Used createRunUsedMetaWorkflow = pFactory.newUsed(null,
                        // metaWorkflowLoi.getId(),
                        // createRunActivity.getId(), null, null);
                        // triggerBundle.getStatement().add(createRunUsedMetaWorkflow);

                        /*
                         * New Activity `AnalyzeWorkflowRun` analyzes the WorkflowRun
                         * `AnalysisWorkflowRun` uses the WorkflowRun
                         * `AnalysisWorkflowRun` generates the Analysis
                         */
                        Activity analyzeWorkflowRun = pFactory.newActivity(prov.qn("analyzeWorkflowRun"),
                                        "Analyze the Workflow Run");
                        Used analyzeWorkflowRunUsedWorkflowRun = pFactory.newUsed(null,
                                        metaWorkflowRun.getId(),
                                        analyzeWorkflowRun.getId(), null, null);
                        Entity analysis = pFactory.newEntity(prov.qn("analysis"), "Analysis");
                        WasGeneratedBy analysisWasGeneratedBy = pFactory.newWasGeneratedBy(null,
                                        analysis.getId(),
                                        analyzeWorkflowRun.getId(), null, null);
                        triggerBundle.getStatement().add(analyzeWorkflowRun);
                        triggerBundle.getStatement().add(analysis);
                        triggerBundle.getStatement().add(analyzeWorkflowRunUsedWorkflowRun);
                        triggerBundle.getStatement().add(analysisWasGeneratedBy);

                        // TODO: Handle the outputs of the Meta Workflow
                        /*
                         * Entity `metaWorkflowRun` generates a Collection of Entity
                         * `outputCollection`
                         * Entity `outputItem` is a member of Entity `outputCollection`
                         */
                        // Entity runOutputCollection =
                        // pFactory.newEntity(prov.qn("runOutputCollection"),
                        // "Collection of the outputs of the Workflow Run");
                        // WasGeneratedBy runOutputCollectionWasGeneratedBy =
                        // pFactory.newWasGeneratedBy(null,
                        // runOutputCollection.getId(), metaWorkflowRun.getId(), null, null);
                        // triggerBundle.getStatement().add(runOutputCollection);
                        // triggerBundle.getStatement().add(runOutputCollectionWasGeneratedBy);

                }

                // Confidence report
                String confidenceReportLocalName = triggerEntity.getId().getLocalPart() + '/'
                                + Constants.DISK_ONTOLOGY_CONFIDENCE_REPORT_LOCALNAME;
                Entity confidenceReport = pFactory.newEntity(
                                prov.qn(confidenceReportLocalName, DocumentProv.PROV_NEUROSCIENCE_TRIGGER_PREFIX),
                                "Confidence Report");

                // Add other type
                QualifiedName confidenceReportType = prov.qn(Constants.DISK_ONTOLOGY_CONFIDENCE_REPORT_LOCALNAME,
                                DocumentProv.DISK_ONTOLOGY_PREFIX);
                confidenceReport.getType()
                                .add(pFactory.newType(confidenceReportType.getUri(), pFactory.getName().XSD_ANY_URI));
                String confidenceReportValue = null;
                if (confidenceReportValue == null) {
                        confidenceReportValue = "0.0";
                }
                confidenceReport.setValue(pFactory.newValue(confidenceReportValue));
                triggerBundle.getStatement().add(confidenceReport);
                triggerBundle.getStatement().add(createRunActivity);

        }

        private void handleWorkflowActivity(Activity activityWriteDataQueryTemplate,
                        Activity activitySavedLineOfInquiry,
                        Entity workflowSystem, Entity workflow, Entity variableBindingWorkflow,
                        String activitySelectWorkflowLabel,
                        String activitySelectVariableWorkflowLabel, String activitySelectVariableLocalName,
                        String activitySelectWorkflowName, WorkflowBindings workflowBindingResource) {
                prov.document.getStatementOrBundle().add(workflowSystem);
                Activity activitySelectWorkflow = pFactory
                                .newActivity(prov.qn(activitySelectWorkflowName, DocumentProv.DISK_PREFIX),
                                                activitySelectWorkflowLabel);
                WasInformedBy wib2 = pFactory.newWasInformedBy(null, activitySelectWorkflow.getId(),
                                activityWriteDataQueryTemplate.getId(), null);
                Activity activitySelectVariableWorkflow = pFactory
                                .newActivity(prov.qn(activitySelectVariableLocalName, DocumentProv.DISK_PREFIX),
                                                activitySelectVariableWorkflowLabel);
                WasGeneratedBy wgbSelectVariableWorkflow = pFactory.newWasGeneratedBy(null,
                                variableBindingWorkflow.getId(),
                                activitySelectVariableWorkflow.getId(), null, null);
                Used usedSelectVariableWorkflow = pFactory.newUsed(null, activitySelectVariableWorkflow.getId(),
                                workflow.getId(), null,
                                null);
                WasInformedBy wib4 = pFactory.newWasInformedBy(null, activitySelectVariableWorkflow.getId(),
                                activitySelectWorkflow.getId(), null);
                WasInformedBy wib7 = pFactory.newWasInformedBy(null, activitySavedLineOfInquiry.getId(),
                                activitySelectVariableWorkflow.getId(), null);
                Used usedSelectWorkflow = pFactory.newUsed(null, activitySelectWorkflow.getId(), workflowSystem.getId(),
                                null,
                                null);
                WasGeneratedBy wgbSelectWorkflow = pFactory.newWasGeneratedBy(null, workflow.getId(),
                                activitySelectWorkflow.getId(), null, null);
                WasInformedBy wib6 = pFactory.newWasInformedBy(null, activitySavedLineOfInquiry.getId(),
                                activitySelectVariableWorkflow.getId(), null);
                Used usedSavedLineOfInquiry = pFactory.newUsed(null, activitySavedLineOfInquiry.getId(),
                                variableBindingWorkflow.getId(), null,
                                null);

                loisBundle.getStatement().add(variableBindingWorkflow);
                prov.document.getStatementOrBundle().add(workflow);
                HadMember hm = pFactory.newHadMember(workflow.getId(), workflowSystem.getId());
                prov.document.getStatementOrBundle().add(hm);
                loisBundle.getStatement().add(activitySelectWorkflow);
                loisBundle.getStatement().add(wib2);
                loisBundle.getStatement().add(wib7);
                loisBundle.getStatement().add(activitySelectVariableWorkflow);
                loisBundle.getStatement().add(activitySelectVariableWorkflow);
                loisBundle.getStatement().add(wib6);
                loisBundle.getStatement().add(wib4);
                prov.document.getStatementOrBundle().add(usedSelectWorkflow);
                prov.document.getStatementOrBundle().add(wgbSelectWorkflow);
                loisBundle.getStatement().add(wgbSelectVariableWorkflow);
                prov.document.getStatementOrBundle().add(usedSelectVariableWorkflow);
                loisBundle.getStatement().add(usedSavedLineOfInquiry);

                // Add the variable bindings
                workflowBindingResource.getBindings().forEach(b -> {
                        String value = b.getBinding();
                        String name = b.getVariable();

                        Entity variableBindingCollectionItem = pFactory.newEntity(
                                        prov.qn(name, DocumentProv.PROV_NEUROSCIENCE_LINE_PREFIX),
                                        name);
                        // TODO: set value
                        if (value == null) {
                                value = "";
                        }
                        variableBindingCollectionItem.setValue(pFactory.newValue(value));

                        loisBundle.getStatement().add(variableBindingCollectionItem);
                        HadMember hm2 = pFactory.newHadMember(variableBindingWorkflow.getId(),
                                        variableBindingCollectionItem.getId());
                        loisBundle.getStatement().add(hm2);
                });

        }

        private Entity level2QuestionAddVariables(Question question,
                        Entity questionEntity, HashMap<String, Entity> questionVariablesMap,
                        Activity createQuestionActivity) {

                String variableCollectionLocalName = questionEntity.getId().getLocalPart() + '/'
                                + Constants.QUESTION_VARIABLES_BINDING;
                Entity questionVariableCollection = pFactory.newEntity(
                                prov.qn(variableCollectionLocalName,
                                                DocumentProv.PROV_NEUROSCIENCE_QUESTION_PREFIX),
                                "Collection of question variables");
                List<QuestionVariable> variables = question.getVariables();
                variables.forEach(variable -> {
                        String id = variable.getId();
                        String name = variable.getVariableName();
                        String localName = IRI.create(id).getFragment();
                        Entity questionVariableEntity = pFactory.newEntity(
                                        prov.qn(localName, DocumentProv.PROV_NEUROSCIENCE_QUESTION_PREFIX),
                                        name);
                        HadMember hadMember = pFactory.newHadMember(questionVariableCollection.getId(),
                                        questionVariableEntity.getId());
                        questionBundle.getStatement().addAll(Arrays.asList(hadMember));
                        questionBundle.getStatement().add(questionVariableEntity);

                });
                WasDerivedFrom wdf = pFactory.newWasDerivedFrom(null, questionEntity.getId(),
                                questionVariableCollection.getId());
                WasGeneratedBy wgb = pFactory.newWasGeneratedBy(null, questionVariableCollection.getId(),
                                createQuestionActivity.getId());
                questionBundle.getStatement().addAll(Arrays.asList(wdf));
                questionBundle.getStatement().add(questionVariableCollection);
                questionBundle.getStatement().add(wgb);
                return questionVariableCollection;
        }

        private Entity level2HypothesisAddVariables(Hypothesis hypothesis,
                        Entity hypothesisEntity, Entity questionVariableCollection,
                        Activity createHypothesisActivity) {
                String variableCollectionLocalName = hypothesisEntity.getId().getLocalPart() + '/'
                                + Constants.HYPOTHESIS_VARIABLES_BINDING;
                Entity variableCollection = pFactory.newEntity(
                                prov.qn(variableCollectionLocalName, DocumentProv.PROV_NEUROSCIENCE_HYPOTHESIS_PREFIX),
                                "Collection of question variables");
                WasDerivedFrom derivationVariables = pFactory.newWasDerivedFrom(null,
                                variableCollection.getId(), questionVariableCollection.getId());
                hypothesisBundle.getStatement().add(variableCollection);
                hypothesisBundle.getStatement().add(derivationVariables);
                hypothesis.getQuestionBindings().forEach(variableBinding -> {
                        String variable = variableBinding.getVariable();
                        String localName = IRI.create(variable).getFragment();
                        String binding = variableBinding.getBinding();
                        String variableLocalName = variableCollectionLocalName + '/' + localName;
                        Entity variableBindingEntity = pFactory.newEntity(
                                        prov.qn(variableLocalName, DocumentProv.PROV_NEUROSCIENCE_HYPOTHESIS_PREFIX),
                                        binding);
                        HadMember hadMember = pFactory.newHadMember(variableCollection.getId(),
                                        variableBindingEntity.getId());
                        hypothesisBundle.getStatement().add(variableBindingEntity);
                        hypothesisBundle.getStatement().add(hadMember);
                });
                WasDerivedFrom wdf = pFactory.newWasDerivedFrom(null, hypothesisEntity.getId(),
                                variableCollection.getId());
                WasGeneratedBy wgb = pFactory.newWasGeneratedBy(null, variableCollection.getId(),
                                createHypothesisActivity.getId());
                hypothesisBundle.getStatement().addAll(Arrays.asList(wdf));
                hypothesisBundle.getStatement().add(createHypothesisActivity);
                hypothesisBundle.getStatement().add(wgb);
                return variableCollection;
        }

        private void level1WasDerived(Entity questionEntity, Entity hypothesisEntity, Entity lineOfInquiryEntity,
                        Entity triggerEntity) {
                // Link the trigger to the line of inquiry
                WasDerivedFrom triggerFromLine = pFactory.newWasDerivedFrom(triggerEntity.getId(),
                                lineOfInquiryEntity.getId());
                WasDerivedFrom lineFromQuestion = pFactory.newWasDerivedFrom(lineOfInquiryEntity.getId(),
                                questionEntity.getId());
                WasDerivedFrom hypothesisFromQuestion = pFactory.newWasDerivedFrom(hypothesisEntity.getId(),
                                questionEntity.getId());
                triggerBundle.getStatement().add(triggerFromLine);
                loisBundle.getStatement().add(lineFromQuestion);
                questionBundle.getStatement().add(hypothesisFromQuestion);
        }

        public Entity createQuestionEntity(Question question) {
                String id = question.getId();
                String name = question.getName();
                String localName = IRI.create(id).getFragment();
                Entity questionEntity = pFactory.newEntity(
                                prov.qn(localName, DocumentProv.PROV_NEUROSCIENCE_QUESTION_PREFIX),
                                name);
                String commentLocalName = "comment";
                String commentValue = "Question class represents a scientific question. Is linked to the template, the pattern and all the variables this question uses.";
                questionEntity.getOther().add(pFactory.newOther(DocumentProv.RDFS_NS, commentLocalName,
                                DocumentProv.RDFS_PREFIX, commentValue,
                                pFactory.getName().XSD_NAME));
                questionBundle.getStatement().add(questionEntity);
                return questionEntity;
        }

        public Entity createHypothesisEntity(Hypothesis hypothesis) {
                String id = hypothesis.getId();
                String name = hypothesis.getName();
                String localName = IRI.create(id).getFragment();
                String dateCreated = hypothesis.getDateCreated();

                Entity hypothesisEntity = pFactory.newEntity(
                                prov.qn(localName, DocumentProv.PROV_NEUROSCIENCE_HYPOTHESIS_PREFIX),
                                name);
                hypothesisEntity.getOther().add(pFactory.newOther(DocumentProv.DCTERMS_NS, "created",
                                DocumentProv.DCTERMS_PREFIX, dateCreated,
                                pFactory.getName().XSD_NAME));
                hypothesisBundle.getStatement().add(hypothesisEntity);
                return hypothesisEntity;
        }

        public Entity createLineOfInquiryEntity(LineOfInquiry lineOfInquiry) {
                String id = lineOfInquiry.getId();
                String name = lineOfInquiry.getName();
                String localName = IRI.create(id).getFragment();
                String dateCreated = lineOfInquiry.getDateCreated();
                Entity loisEntity = pFactory.newEntity(
                                prov.qn(localName, DocumentProv.PROV_NEUROSCIENCE_LINE_PREFIX),
                                name);
                loisEntity.getOther().add(pFactory.newOther(DocumentProv.DCTERMS_NS, "created",
                                DocumentProv.DCTERMS_PREFIX, dateCreated,
                                pFactory.getName().XSD_NAME));
                loisBundle.getStatement().add(loisEntity);
                return loisEntity;
        }

        public Entity createTiggerEntity(TriggeredLOI triggerLineInquiry) {
                String id = triggerLineInquiry.getId();
                String name = triggerLineInquiry.getName();
                String localName = IRI.create(id).getFragment();
                String dateCreated = triggerLineInquiry.getDateCreated();
                Entity triggerEntity = pFactory.newEntity(
                                prov.qn(localName, DocumentProv.PROV_NEUROSCIENCE_TRIGGER_PREFIX),
                                name);
                triggerEntity.getOther().add(pFactory.newOther(DocumentProv.DCTERMS_NS, "created",
                                DocumentProv.DCTERMS_PREFIX, dateCreated,
                                pFactory.getName().XSD_NAME));
                triggerBundle.getStatement().add(triggerEntity);
                return triggerEntity;
        }

}
