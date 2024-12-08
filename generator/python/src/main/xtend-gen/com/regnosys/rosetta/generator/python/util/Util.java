package com.regnosys.rosetta.generator.python.util;

import com.google.common.collect.Iterables;
import com.regnosys.rosetta.rosetta.RosettaModel;
import com.regnosys.rosetta.rosetta.RosettaType;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;
import org.eclipse.xtend2.lib.StringConcatenation;
import org.eclipse.xtext.xbase.lib.CollectionLiterals;
import org.eclipse.xtext.xbase.lib.IterableExtensions;

@SuppressWarnings("all")
public class Util {
  private static class DistinctByIterator<T extends Object, U extends Object> implements Iterable<T> {
    private final Iterable<T> iterable;

    private final Function<T, U> extractFunction;

    public DistinctByIterator(final Iterable<T> iterable, final Function<T, U> extractFunction) {
      this.iterable = iterable;
      this.extractFunction = extractFunction;
    }

    @Override
    public Iterator<T> iterator() {
      abstract class __DistinctByIterator_1 implements Iterator<T> {
        HashSet<Object> read;

        T readNext;
      }

      final Iterator<T> parentIterator = this.iterable.iterator();
      return new __DistinctByIterator_1() {
        {
          read = CollectionLiterals.<Object>newHashSet();
        }
        @Override
        public boolean hasNext() {
          if ((this.readNext != null)) {
            return true;
          }
          while (true) {
            {
              boolean _hasNext = parentIterator.hasNext();
              boolean _not = (!_hasNext);
              if (_not) {
                return false;
              }
              this.readNext = parentIterator.next();
              final U compareVal = DistinctByIterator.this.extractFunction.apply(this.readNext);
              boolean _contains = this.read.contains(compareVal);
              boolean _not_1 = (!_contains);
              if (_not_1) {
                this.read.add(compareVal);
                return true;
              }
            }
          }
        }

        @Override
        public T next() {
          boolean _hasNext = this.hasNext();
          if (_hasNext) {
            final T result = this.readNext;
            this.readNext = null;
            return result;
          } else {
            throw new NoSuchElementException("read past end of iterator");
          }
        }
      };
    }
  }

  public static <T extends Object> Iterable<T> distinct(final Iterable<T> parentIterable) {
    final Function<T, T> _function = (T it) -> {
      return it;
    };
    return new Util.DistinctByIterator<T, T>(parentIterable, _function);
  }

  public static <T extends Object, U extends Object> Iterable<T> distinctBy(final Iterable<T> parentIterable, final Function<T, U> extractFunction) {
    return new Util.DistinctByIterator<T, U>(parentIterable, extractFunction);
  }

  public static <T extends Object> boolean exists(final Iterable<? super T> iter, final Class<T> clazz) {
    boolean _isEmpty = IterableExtensions.isEmpty(Iterables.<T>filter(iter, clazz));
    return (!_isEmpty);
  }

  public static String getNamespace(final RosettaModel rm) {
    return rm.getName().split("\\.")[0];
  }

  public static String fullname(final RosettaType clazz) {
    StringConcatenation _builder = new StringConcatenation();
    String _name = clazz.getModel().getName();
    _builder.append(_name);
    _builder.append(".");
    String _name_1 = clazz.getName();
    _builder.append(_name_1);
    return _builder.toString();
  }

  public static String packageName(final RosettaType clazz) {
    return clazz.getModel().getName();
  }
}
