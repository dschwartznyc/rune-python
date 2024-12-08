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
public class ImportsGeneratorTest {
  @Inject
  @Extension
  private ModelHelper _modelHelper;

  @Inject
  private PythonCodeGenerator generator;

  @Test
  public void testImportsGenerator() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("namespace rosetta_dsl.test.semantic.deep_path : <\"generate Python unit tests from Rosetta.\">");
    _builder.newLine();
    _builder.append("               ");
    _builder.append("type Deep1:");
    _builder.newLine();
    _builder.append("                   ");
    _builder.append("attr int (1..1)");
    _builder.newLine();
    _builder.append("                ");
    _builder.append("type Bar1:");
    _builder.newLine();
    _builder.append("                   ");
    _builder.append("deep1 Deep1 (1..1)");
    _builder.newLine();
    final HashMap<String, CharSequence> pythonCode = this.generatePython(_builder);
    StringConcatenation _builder_1 = new StringConcatenation();
    _builder_1.append("class Bar1(BaseDataClass):");
    _builder_1.newLine();
    _builder_1.append("    ");
    _builder_1.append("deep1: rosetta_dsl.test.semantic.deep_path.Deep1.Deep1 = Field(..., description=\"\")");
    _builder_1.newLine();
    _builder_1.newLine();
    _builder_1.append("import rosetta_dsl ");
    _builder_1.newLine();
    _builder_1.append("import rosetta_dsl.test.semantic.deep_path.Deep1");
    final String expected = _builder_1.toString();
    Assertions.assertTrue(pythonCode.toString().contains(expected));
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
