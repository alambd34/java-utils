/*
 * Copyright 2012 Daniel Bechler
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.danielbechler.util.collection;

import java.util.*;

/**
 * An iterator that is capable of converting the elements of the input iterator into another type.
 *
 * @author Daniel Bechler
 */
public abstract class AdapterIterator<I, O> implements Iterator<O>
{
    private final Iterator<? extends I> delegate;

    public AdapterIterator(final Iterator<? extends I> delegate)
    {
        this.delegate = (delegate == null) ? CollectionUtils.<I>emptyIterator() : delegate;
    }

    @Override
    public boolean hasNext()
    {
        return delegate.hasNext();
    }

    @Override
    public O next()
    {
        return adapt(delegate.next());
    }

    protected abstract O adapt(final I item);

    @Override
    public void remove()
    {
        delegate.remove();
    }
}
