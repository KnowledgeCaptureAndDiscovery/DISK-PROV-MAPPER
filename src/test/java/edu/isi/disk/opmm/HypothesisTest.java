package edu.isi.disk.opmm;

import java.io.IOException;
import java.util.List;

import org.diskproject.shared.classes.hypothesis.Hypothesis;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import org.diskproject.shared.classes.workflow.VariableBinding;

public class HypothesisTest {

        @Test
        public void loadHypothesis() throws StreamReadException, DatabindException, IOException {
                Hypothesis hypothesis = UtilsTest
                                .loadHypothesis("src/test/resources/Hypothesis-4CGdVLyttD07/hypothesis.json");
                Assert.assertEquals("Hypothesis-4CGdVLyttD07", hypothesis.getId());
                Assert.assertEquals(
                                "Is the effect size of rs1080066 on Surface Area of Precentral associated with Percentage of Females for cohorts of European ancestry",
                                hypothesis.getName());
                Assert.assertEquals("Runs meta regression ", hypothesis.getDescription());
                Assert.assertEquals("17:48:24 2022-09-28", hypothesis.getDateCreated());
                Assert.assertEquals("07:19:00 2023-03-30", hypothesis.getDateModified());
                Assert.assertEquals("enigma-disk@isi.edu", hypothesis.getAuthor());
                Assert.assertEquals("", hypothesis.getNotes());
                // TODO: #3 QuestionId is not a property of Hypothesis class
                Assert.assertEquals("https://w3id.org/sqo/resource/EnigmaQuestion5", hypothesis.getQuestionId());

                List<VariableBinding> questionBindings = hypothesis.getQuestionBindings();
                Assert.assertEquals(5, questionBindings.size());

                VariableBinding genotypeBinding = questionBindings.get(0);
                Assert.assertEquals("http://disk-project.org/resources/enigma/variable/genotype",
                                genotypeBinding.getVariable());
                Assert.assertEquals("rs1080066", genotypeBinding.getBinding());
                // TODO: #4 Type is not a property of VariableBinding class
                // Assert.assertNull(genotypeBinding.getType());
                Assert.assertFalse(genotypeBinding.isCollection());
                Assert.assertArrayEquals(new String[] { "rs1080066" },
                                genotypeBinding.getBindingAsArray());

                VariableBinding brainImagingTraitBinding = questionBindings.get(1);
                Assert.assertEquals("http://disk-project.org/resources/enigma/variable/brainImagingTrait",
                                brainImagingTraitBinding.getVariable());
                Assert.assertEquals("SA", brainImagingTraitBinding.getBinding());
                Assert.assertFalse(brainImagingTraitBinding.isCollection());
                Assert.assertArrayEquals(new String[] { "SA" },
                                brainImagingTraitBinding.getBindingAsArray());

                VariableBinding brainRegionBinding = questionBindings.get(2);
                Assert.assertEquals("http://disk-project.org/resources/enigma/variable/brainRegion",
                                brainRegionBinding.getVariable());
                Assert.assertEquals("Precentral", brainRegionBinding.getBinding());
                Assert.assertFalse(brainRegionBinding.isCollection());
                Assert.assertArrayEquals(new String[] { "Precentral" },
                                brainRegionBinding.getBindingAsArray());

                VariableBinding demographicAttributeBinding = questionBindings.get(3);
                Assert.assertEquals("http://disk-project.org/resources/enigma/variable/demographicAttribute",
                                demographicAttributeBinding.getVariable());
                Assert.assertEquals(
                                "http://localhost:8080/enigma_dev/Special:URIResolver/Property-3AHasNumberOfFemaleSex_-28E-29",
                                demographicAttributeBinding.getBinding());
                Assert.assertFalse(demographicAttributeBinding.isCollection());
                Assert.assertArrayEquals(
                                new String[] {
                                                "http://localhost:8080/enigma_dev/Special:URIResolver/Property-3AHasNumberOfFemaleSex_-28E-29"
                                },
                                demographicAttributeBinding.getBindingAsArray());

                VariableBinding ethnicGroupBinding = questionBindings.get(4);
                Assert.assertEquals("http://disk-project.org/resources/enigma/variable/ethnicGroup",
                                ethnicGroupBinding.getVariable());
                Assert.assertEquals("European", ethnicGroupBinding.getBinding());
                Assert.assertFalse(ethnicGroupBinding.isCollection());
                Assert.assertArrayEquals(new String[] { "European" },
                                ethnicGroupBinding.getBindingAsArray());

        }
}
