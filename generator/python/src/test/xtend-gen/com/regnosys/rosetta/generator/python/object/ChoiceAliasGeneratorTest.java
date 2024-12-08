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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(InjectionExtension.class)
@InjectWith(RosettaInjectorProvider.class)
@SuppressWarnings("all")
public class ChoiceAliasGeneratorTest {
  @Inject
  @Extension
  private ModelHelper _modelHelper;

  @Inject
  private PythonCodeGenerator generator;

  @Test
  public void testChoiceAliasGenerator() {
    StringConcatenation _builder = new StringConcatenation();
    _builder.append("type Bar1:");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("deep1 Deep1 (1..1)");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("b1 int (1..1)");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("a int (1..1)");
    _builder.newLine();
    _builder.newLine();
    _builder.append("type Bar2:");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("deep1 Deep1 (1..1)");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("b1 int (1..1)");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("c int (1..1)");
    _builder.newLine();
    _builder.newLine();
    _builder.append("type Bar4:");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("deep1 Deep1 (1..1)");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("b1 int (1..1)");
    _builder.newLine();
    _builder.newLine();
    _builder.append("type Bar3:");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("bar2 Bar2(0..1)");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("bar4 Bar4(0..1)");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("condition Choice: one-of ");
    _builder.newLine();
    _builder.newLine();
    _builder.append("type Foo:");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("bar1 Bar1 (0..1)");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("bar3 Bar3 (0..1)");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("condition Choice: one-of");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("condition Test:");
    _builder.newLine();
    _builder.append("       ");
    _builder.append("if bar1 exists then");
    _builder.newLine();
    _builder.append("           ");
    _builder.append("bar1->deep1->attr = 3");
    _builder.newLine();
    _builder.append("       ");
    _builder.append("else if bar3 exists then");
    _builder.newLine();
    _builder.append("           ");
    _builder.append("bar3->>deep1->attr =3");
    _builder.newLine();
    _builder.append("       ");
    _builder.append("else False ");
    _builder.newLine();
    _builder.newLine();
    _builder.append("type FooBar:");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("foo Foo (1..1)");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("condition Test: ");
    _builder.newLine();
    _builder.append("       ");
    _builder.append("foo->>deep1->attr = 3");
    _builder.newLine();
    _builder.newLine();
    _builder.append("type Deep1:");
    _builder.newLine();
    _builder.append("    ");
    _builder.append("attr int (1..1)");
    _builder.newLine();
    final HashMap<String, CharSequence> pythonCode = this.generatePython(_builder);
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
