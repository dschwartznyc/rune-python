package com.regnosys.rosetta.generator.python.object;

import com.google.inject.Inject;
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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(InjectionExtension.class)
@InjectWith(RosettaInjectorProvider.class)
@SuppressWarnings("all")
public class PythonEnumGeneratorTest {
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
    final String enums = python.toString();
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
    Assertions.assertTrue(enums.contains(expected));
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
