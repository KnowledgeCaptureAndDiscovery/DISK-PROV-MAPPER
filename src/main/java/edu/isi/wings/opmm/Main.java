package edu.isi.wings.opmm;

import java.io.File;
import java.util.*;
public class Main {
public static void main(String[] args) {
    Mapper instance = new Mapper();
   
  String lib = "src\\main\\resources\\sample_data\\new\\simple_abstract\\library.owl";
  //String execution = "/Users/Tirthmehta/Desktop/WINGS_PROVENANCE_EXPORT_ISI/executions/spacer/spacer-ex.owl";
  //String execution = "/Users/Tirthmehta/Desktop/WINGS_PROVENANCE_EXPORT_ISI/executions/parasimple/cpexe.owl";
  //String execution = "/Users/Tirthmehta/Documents/workspace/WINGS_PROVENANCE_EXPORT_SCENARIOS/ab2ex.owl";
  //String execution = "/Users/Tirthmehta/Desktop/WINGS_PROVENANCE_EXPORT_ISI/abex3.owl";
  String execution ="src\\main\\resources\\sample_data\\new\\simple_abstract\\execution.owl";
  
   //String template="/Users/Tirthmehta/Desktop/WINGS_PROVENANCE_EXPORT_ISI/executions/parasimple/CaesarCypherParallelSimple.owl";
   //String template="/Users/Tirthmehta/Desktop/WINGS_PROVENANCE_EXPORT_ISI/executions/abstractTest1/abstractTest1.owl";
   //String template="/Users/Tirthmehta/Documents/workspace/WINGS_PROVENANCE_EXPORT_SCENARIOS/mapreduce.owl";
   //String template="/Users/Tirthmehta/Documents/workspace/WINGS_PROVENANCE_EXPORT_SCENARIOS/EXAMPLE-2_INPUT_FOR_MAPPER_SCENARIO-1/HashingConceptScenario1.owl";
   String template="src\\main\\resources\\sample_data\\new\\simple_abstract\\template.owl";
    String mode = "RDF/XML";
    String mode2="Turtle";
  String outFileOPMW = "testResultOPMW";
  String outFilePROV = "testResultPROV";
  String outFile = "testTemplateabstractTest1";
  String data_catalog="TestingDomain1\\Data\\TestingDomain_DataCatalog.owl";
  String taxonomy_export="TestingDomain1\\Component\\TestingDomain_TaxonomyHierarchyModel.owl";
  String componentDirectory = "src\\main\\resources\\sample_data\\new\\simple_abstract\\components\\";
  String dataDirectory = "src\\main\\resources\\sample_data\\new\\simple_abstract\\data\\";
 // /Users/Tirthmehta/Documents/workspace/WINGS_PROVENANCE_EXPORT_SCENARIOS/LOCAL_FOLDER_COMPONENT_CATALOGS_OF_DIFFERENT_DOMAINS/Testing.owl
    
//  String ans=instance.transformWINGSElaboratedTemplateToOPMW(template, mode, outFile,taxonomy_export,mode2, componentDirectory,"TestingDomain");
  String ans2=instance.transformWINGSResultsToOPMW(execution, lib, mode, outFileOPMW, outFilePROV, null,data_catalog,mode2,dataDirectory, "TestingDomain");
    System.out.println("--------------------------");
//    System.out.println("location is :"+ans);
    
    //b28cf887da133e5130bd53ee69c995 FIRSTY1 greats
   //
 

    //http://ontosoft.isi.edu:8080/wings-portal/export/users/tirth/TestingDomain/workflows/spacer.owl
    //http://datascience4all.org/wings-portal/export/users/tirth/TestingDomain/workflows/spacer.owl
    //http://datascience4all.org/wings-portal/export/users/tirth/TestingDomain/workflows/abstract.owl
    ///Users/Tirthmehta/Documents/workspace/WINGS_PROVENANCE_EXPORT_ISI/executions/spacer/spacer-ex.owl
    //http://datascience4all.org/wings-portal/export/users/tirth/TestingDomain/executions/spacer-64c57dfd-d9ad-44f2-b114-2cfc3d0e50a1.owl
    //http://datascience4all.org/wings-portal/export/users/tirth/TestingDomain/executions/abstractTest1-83-cdd28105-a8a5-4fe8-bfb9-c8291a31ac7a.owl
    //http://datascience4all.org/wings-portal/export/users/tirth/TestingDomain/executions/library.owl
    //http://datascience4all.org/wings-portal/export/users/tirth/CaesarCypher/workflows/CaesarCypherParallelSimple.owl
    
    //expanded template for abstractTest1
    //http://datascience4all.org/wings-portal/export/users/tirth/TestingDomain/executions/abstractTest1-83-7336709c-6108-4fc8-9ecd-810f27c8d870.owl#abstractTest1-83-7336709c-6108-4fc8-9ecd-810f27c8d870
//    System.out.println("Transform an execution to OPMW and PROV ");
////        Mapper instance = new Mapper();
//        String mode = "RDF/XML";
//        String outFileOPMW = "testResult";
//        String outFilePROV = "testResult2"; 
//        System.out.println("Transforming an execution of a template with collections...");
//        String lib="src\\main\\resources\\sample_data\\new\\abs_words\\Library.owl";
//        String execution="src\\main\\resources\\sample_data\\new\\abs_words\\Execution.owl";
//        String data_catalog="TestingDomain1\\Data\\TestingDomain_DataCatalog.owl";
//        String data_URI="src\\main\\resources\\sample_data\\new\\abs_words\\data\\";
//        String template = "src\\main\\resources\\sample_data\\new\\abs_words\\Template.owl";
//        String outFile = "testTemplateabstractTest1";
//        String taxonomy_export="TestingDomain1\\Component\\TestingDomain_TaxonomyHierarchyModel.owl";
//        System.out.println(instance.transformWINGSResultsToOPMW(execution, lib, mode, outFileOPMW, outFilePROV,null, data_catalog, "TURTLE",data_URI,"CompareFiles"));
//        String ans=instance.transformWINGSElaboratedTemplateToOPMW(template, mode, outFile,taxonomy_export,"TURTLE", taxonomy_export,"TestingDomain");
//        //delete output files
//        File f = new File(outFileOPMW);
//        File f2 = new File(outFilePROV);
}
}