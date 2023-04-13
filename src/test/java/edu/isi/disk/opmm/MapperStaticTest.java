package edu.isi.disk.opmm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.diskproject.shared.classes.workflow.VariableBinding;
import org.junit.Test;

import junit.framework.Assert;

public class MapperStaticTest {

  @Test
  public void filterFilesTest() {
    // Create a new Map<String, String> with the files that we want to filter
    Map<String, String> files = new HashMap<String, String>();
    files.put("SHA4945c5_BIG_Significant_GWAS.csv",
        "http://localhost:8080/wings-portal/export/users/admin/Enigma/data/library.owl#SHA4945c5_BIG_Significant_GWAS.csv");
    files.put("SHA47a5c5_ASRB_Significant_GWAS.csv",
        "http://localhost:8080/wings-portal/export/users/admin/Enigma/data/library.owl#SHA47a5c5_ASRB_Significant_GWAS.csv");
    List<VariableBinding> bindings = new ArrayList<VariableBinding>();
    VariableBinding binding = new VariableBinding();
    binding.setVariable("cohortData");
    binding.setBinding(
        "[SHA4fe5c4_1000BRAINS_Significant_GWAS.csv, SHA4815c5_ADNI1_Significant_GWAS.csv, SHA4a85c5_ADNI2GO_Significant_GWAS.csv, SHA4bf5c6_ALSPACa_Significant_GWAS.csv, SHA47a5c5_ASRB_Significant_GWAS.csv, SHA4aa5c5_BETULA_Significant_GWAS.csv, SHA4945c5_BIG_Significant_GWAS.csv, SHA5305c4_BIG-PsychChip_Significant_GWAS.csv, SHA4c45c5_BONN_Significant_GWAS.csv, SHA4ad5c5_BrainScale_Significant_GWAS.csv]");
    bindings.add(binding);
    List<VariableBinding> filesVariableBindings = Mapper.filterInputs(bindings, files);
    System.out.println(filesVariableBindings);
  }

  @Test
  public void splitArrayStringTest() {
    String string = "[a,b,c]";
    String[] array = Mapper.splitArrayString(string);
    Assert.assertEquals(3, array.length);
    Assert.assertEquals("a", array[0]);
    Assert.assertEquals("b", array[1]);
    Assert.assertEquals("c", array[2]);

    String string2 = "[a, b, c]";
    String[] array2 = Mapper.splitArrayString(string2);
    Assert.assertEquals(3, array2.length);
    Assert.assertEquals("a", array2[0]);
    Assert.assertEquals("b", array2[1]);
    Assert.assertEquals("c", array2[2]);

  }
}
