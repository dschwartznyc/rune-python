package com.regnosys.rosetta.generator.python.object;

import com.google.inject.Inject;
import com.regnosys.rosetta.generator.java.enums.EnumHelper;
import com.regnosys.rosetta.generator.python.PythonCodeGenerator;
import com.regnosys.rosetta.rosetta.RosettaModel;
import com.regnosys.rosetta.tests.RosettaInjectorProvider;
import com.regnosys.rosetta.tests.util.ModelHelper;
import java.util.Collections;
import java.util.HashMap;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.testing.InjectWith;
import org.eclipse.xtext.testing.extensions.InjectionExtension;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.Extension;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(InjectionExtension.class)
@InjectWith(RosettaInjectorProvider.class)
@SuppressWarnings("all")
public class EnumGeneratorTest {
  @Inject
  @Extension
  private ModelHelper _modelHelper;

  @Inject
  private PythonCodeGenerator generator;

  @Test
  public void shouldGenerateEnums() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("enum TestEnum: <\"Test enum description.\">");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("TestEnumValue1 <\"Test enum value 1\">");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("TestEnumValue2 <\"Test enum value 2\">");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("TestEnumValue3 <\"Test enum value 3\">");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("_1 displayName \"1\" <\"Rolls on the 1st day of the month.\">");
    _builder.newLine();
    final HashMap<String, CharSequence> python = this.generatePython(_builder);
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("class TestEnum(Enum):");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("Test enum description.");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("TEST_ENUM_VALUE_1 = \"TestEnumValue1\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("Test enum value 1");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("TEST_ENUM_VALUE_2 = \"TestEnumValue2\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("Test enum value 2");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("TEST_ENUM_VALUE_3 = \"TestEnumValue3\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("Test enum value 3");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("_1 = \"1\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("Rolls on the 1st day of the month.");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    final String expected = _builder_1.toString();
    Assertions.assertTrue(python.toString().contains(expected));
  }

  @Test
  @Disabled
  public void shouldGenerateAnnotationForEnumSynonyms() {
  }

  @Test
  public void shouldGenerateEnums2() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("enum TestEnum: <\"Test enum description.\">");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("TestEnumValue1 <\"Test enum value 1\">");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("TestEnumValue2 <\"Test enum value 2\">");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("TestEnumValue3 <\"Test enum value 3\">");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("_1 displayName \"1\" <\"Rolls on the 1st day of the month.\">");
    _builder.newLine();
    final HashMap<String, CharSequence> python = this.generatePython(_builder);
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("class TestEnum(Enum):");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("Test enum description.");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("TEST_ENUM_VALUE_1 = \"TestEnumValue1\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("Test enum value 1");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("TEST_ENUM_VALUE_2 = \"TestEnumValue2\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("Test enum value 2");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("TEST_ENUM_VALUE_3 = \"TestEnumValue3\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("Test enum value 3");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("_1 = \"1\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("Rolls on the 1st day of the month.");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    final String expected = _builder_1.toString();
    Assertions.assertTrue(python.toString().contains(expected));
  }

  @Test
  public void shouldGenerateEnums3() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("enum ConfirmationStatusEnum: <\"Enumeration for the different types of confirmation status.\">");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("Confirmed");
    _builder.newLine();
    _builder.append("            ");
    _builder.append("Unconfirmed");
    _builder.newLine();
    final HashMap<String, CharSequence> python = this.generatePython(_builder);
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("class ConfirmationStatusEnum(Enum):");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("Enumeration for the different types of confirmation status.");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("CONFIRMED = \"Confirmed\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("UNCONFIRMED = \"Unconfirmed\"");
    _builder_1.newLine();
    final String expected = _builder_1.toString();
    Assertions.assertTrue(python.toString().contains(expected));
  }

  @Test
  public void shouldGenerateEnums4() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("enum TransferStatusEnum: <\"The enumeration values to specify the transfer status.\">");
    _builder.newLine();
    _builder.append("             ");
    _builder.append("Disputed <\"The transfer is disputed.\">");
    _builder.newLine();
    _builder.append("             ");
    _builder.append("Instructed <\"The transfer has been instructed.\">");
    _builder.newLine();
    _builder.append("             ");
    _builder.append("Pending <\"The transfer is pending instruction.\">");
    _builder.newLine();
    _builder.append("             ");
    _builder.append("Settled <\"The transfer has been settled.\">");
    _builder.newLine();
    _builder.append("             ");
    _builder.append("Netted <\"The transfer has been netted into a separate Transfer.\">");
    _builder.newLine();
    final HashMap<String, CharSequence> python = this.generatePython(_builder);
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("class TransferStatusEnum(Enum):");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("The enumeration values to specify the transfer status.");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("DISPUTED = \"Disputed\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("The transfer is disputed.");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("INSTRUCTED = \"Instructed\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("The transfer has been instructed.");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("NETTED = \"Netted\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("The transfer has been netted into a separate Transfer.");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("PENDING = \"Pending\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("The transfer is pending instruction.");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("SETTLED = \"Settled\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("The transfer has been settled.");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    final String expected = _builder_1.toString();
    Assertions.assertTrue(python.toString().contains(expected));
  }

  @Test
  public void shouldGenerateEnums5() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("enum FinancialUnitEnum: <\"Provides enumerated values for financial units, generally used in the context of defining quantities for securities.\">");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("Contract <\"Denotes financial contracts, such as listed futures and options.\">");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("ContractualProduct <\"Denotes a Contractual Product as defined in the CDM.  This unit type would be used when the price applies to the whole product, for example, in the case of a premium expressed as a cash amount.\">");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("IndexUnit <\"Denotes a price expressed in index points, e.g. for a stock index.\">");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("LogNormalVolatility <\"Denotes a log normal volatility, expressed in %/month, where the percentage is represented as a decimal. For example, 0.15 means a log-normal volatility of 15% per month.\">");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("Share <\"Denotes the number of units of financial stock shares.\">");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("ValuePerDay <\"Denotes a value (expressed in currency units) for a one day change in a valuation date, which is typically used for expressing sensitivity to the passage of time, also known as theta risk, or carry, or other names.\">");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("ValuePerPercent <\"Denotes a value (expressed in currency units) per percent change in the underlying rate which is typically used for expressing sensitivity to volatility changes, also known as vega risk.\">");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("Weight <\"Denotes a quantity (expressed as a decimal value) represented the weight of a component in a basket.\">");
    _builder.newLine();
    final HashMap<String, CharSequence> python = this.generatePython(_builder);
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("class FinancialUnitEnum(Enum):");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("Provides enumerated values for financial units, generally used in the context of defining quantities for securities.");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("CONTRACT = \"Contract\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("Denotes financial contracts, such as listed futures and options.");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("CONTRACTUAL_PRODUCT = \"ContractualProduct\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("Denotes a Contractual Product as defined in the CDM.  This unit type would be used when the price applies to the whole product, for example, in the case of a premium expressed as a cash amount.");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("INDEX_UNIT = \"IndexUnit\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("Denotes a price expressed in index points, e.g. for a stock index.");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("LOG_NORMAL_VOLATILITY = \"LogNormalVolatility\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("Denotes a log normal volatility, expressed in %/month, where the percentage is represented as a decimal. For example, 0.15 means a log-normal volatility of 15% per month.");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("SHARE = \"Share\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("Denotes the number of units of financial stock shares.");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("VALUE_PER_DAY = \"ValuePerDay\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("Denotes a value (expressed in currency units) for a one day change in a valuation date, which is typically used for expressing sensitivity to the passage of time, also known as theta risk, or carry, or other names.");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("VALUE_PER_PERCENT = \"ValuePerPercent\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("Denotes a value (expressed in currency units) per percent change in the underlying rate which is typically used for expressing sensitivity to volatility changes, also known as vega risk.");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("WEIGHT = \"Weight\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("Denotes a quantity (expressed as a decimal value) represented the weight of a component in a basket.");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("\"\"\"");
    _builder_1.newLine();
    final String expected = _builder_1.toString();
    Assertions.assertTrue(python.toString().contains(expected));
  }

  @Test
  @Disabled
  public void shouldGenerateAllDisplayName() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("synonym source FpML");
    _builder.newLine();
    _builder.append("enum TestEnumWithDisplay:");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("one displayName \"uno\" <\"Some description\"> [synonym FpML value \"oneSynonym\"]");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("two <\"Some other description\"> [synonym FpML value \"twoSynonym\"]");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("three displayName \"tria\" <\"Some description\"> [synonym FpML value \"threeSynonym\"]");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("four  displayName \"tessera\" <\"Some description\"> [synonym FpML value \"fourSynonym\"]");
    _builder.newLine();
    this.generatePython(_builder);
  }

  @Test
  public void shouldGenerateUppercaseUnderscoreFormattedEnumNames() {
    MatcherAssert.<String>assertThat(EnumHelper.formatEnumName("ISDA1993Commodity"), CoreMatchers.<String>is("ISDA_1993_COMMODITY"));
    MatcherAssert.<String>assertThat(EnumHelper.formatEnumName("ISDA1998FX"), CoreMatchers.<String>is("ISDA1998FX"));
    MatcherAssert.<String>assertThat(EnumHelper.formatEnumName("iTraxxEuropeDealer"), CoreMatchers.<String>is("I_TRAXX_EUROPE_DEALER"));
    MatcherAssert.<String>assertThat(EnumHelper.formatEnumName("StandardLCDS"), CoreMatchers.<String>is("STANDARD_LCDS"));
    MatcherAssert.<String>assertThat(EnumHelper.formatEnumName("_1_1"), CoreMatchers.<String>is("_1_1"));
    MatcherAssert.<String>assertThat(EnumHelper.formatEnumName("_30E_360_ISDA"), CoreMatchers.<String>is("_30E_360_ISDA"));
    MatcherAssert.<String>assertThat(EnumHelper.formatEnumName("ACT_365L"), CoreMatchers.<String>is("ACT_365L"));
    MatcherAssert.<String>assertThat(EnumHelper.formatEnumName("OSPPrice"), CoreMatchers.<String>is("OSP_PRICE"));
    MatcherAssert.<String>assertThat(EnumHelper.formatEnumName("FRAYield"), CoreMatchers.<String>is("FRA_YIELD"));
    MatcherAssert.<String>assertThat(EnumHelper.formatEnumName("AED-EBOR-Reuters"), CoreMatchers.<String>is("AED_EBOR_REUTERS"));
    MatcherAssert.<String>assertThat(EnumHelper.formatEnumName("EUR-EURIBOR-Reuters"), CoreMatchers.<String>is("EUR_EURIBOR_REUTERS"));
    MatcherAssert.<String>assertThat(EnumHelper.formatEnumName("DJ.iTraxx.Europe"), CoreMatchers.<String>is("DJ_I_TRAXX_EUROPE"));
    MatcherAssert.<String>assertThat(EnumHelper.formatEnumName("IVS1OpenMarkets"), CoreMatchers.<String>is("IVS_1_OPEN_MARKETS"));
    MatcherAssert.<String>assertThat(EnumHelper.formatEnumName("D"), CoreMatchers.<String>is("D"));
    MatcherAssert.<String>assertThat(EnumHelper.formatEnumName("_1"), CoreMatchers.<String>is("_1"));
    MatcherAssert.<String>assertThat(EnumHelper.formatEnumName("DJ.CDX.NA"), CoreMatchers.<String>is("DJ_CDX_NA"));
    MatcherAssert.<String>assertThat(EnumHelper.formatEnumName("novation"), CoreMatchers.<String>is("NOVATION"));
    MatcherAssert.<String>assertThat(EnumHelper.formatEnumName("partialNovation"), CoreMatchers.<String>is("PARTIAL_NOVATION"));
    MatcherAssert.<String>assertThat(EnumHelper.formatEnumName("ALUMINIUM_ALLOY_LME_15_MONTH"), CoreMatchers.<String>is("ALUMINIUM_ALLOY_LME_15_MONTH"));
    MatcherAssert.<String>assertThat(EnumHelper.formatEnumName("AggregateClient"), CoreMatchers.<String>is("AGGREGATE_CLIENT"));
    MatcherAssert.<String>assertThat(EnumHelper.formatEnumName("Currency1PerCurrency2"), CoreMatchers.<String>is("CURRENCY_1_PER_CURRENCY_2"));
  }

  @Test
  @Disabled
  public void shouldAllowDeprectedAnnotationForEnum() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("enum TestEnumDeprecated:");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("[deprecated]");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("one");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("two");
    _builder.newLine();
    this.generatePython(_builder);
  }

  public HashMap<String, CharSequence> generatePython(final CharSequence model) {
    HashMap<String, CharSequence> _xblockexpression = null;
    {
      final RosettaModel m = this._modelHelper.parseRosettaWithNoErrors(model);
      final ResourceSet resourceSet = m.eResource().getResourceSet();
      final String version = m.getVersion();
      final HashMap<String, CharSequence> result = CollectionLiterals.<String, CharSequence>newHashMap();
      result.putAll(this.generator.beforeAllGenerate(resourceSet, Collections.<RosettaModel>unmodifiableSet(CollectionLiterals.<RosettaModel>newHashSet(m)), version));
      result.putAll(this.generator.beforeGenerate(m.eResource(), m, version));
      result.putAll(this.generator.generate(m.eResource(), m, version));
      result.putAll(this.generator.afterGenerate(m.eResource(), m, version));
      result.putAll(this.generator.afterAllGenerate(resourceSet, Collections.<RosettaModel>unmodifiableSet(CollectionLiterals.<RosettaModel>newHashSet(m)), version));
      _xblockexpression = result;
    }
    return _xblockexpression;
  }
}
