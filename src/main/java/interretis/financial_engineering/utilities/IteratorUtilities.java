package interretis.financial_engineering.utilities;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Iterator;

public final class IteratorUtilities {

    public static <T> Iterator<T> constant(final T constant)
    {
        return new ConstantIterator<T>(constant);
    }

    public static <T> Iterator<T> take(final Iterator<T> iterator, final int limit)
    {
        return new LimitedIterator<>(iterator, limit);
    }

    @AllArgsConstructor
    private static class ConstantIterator<T> implements Iterator<T> {

        private final T constant;

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public T next() {
            return constant;
        }
    }

    @RequiredArgsConstructor
    @AllArgsConstructor
    private static class LimitedIterator<T> implements Iterator<T> {

        private final Iterator<T> iterator;
        private final int limit;
        private int counter = 0;

        @Override
        public boolean hasNext() {
            return iterator.hasNext() && counter < limit;
        }

        @Override
        public T next() {
            counter++;
            return iterator.next();
        }
    }
}
