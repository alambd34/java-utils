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

import de.danielbechler.util.assertion.*;

import java.util.*;

/** @author Daniel Bechler */
public final class SubIterator<T> implements Iterator<T>
{
	private final Iterator<? extends T> delegate;
	private final int first;
	private final int count;

	private int index;
	private T next;
	private boolean awaitingCallToNext;

	public SubIterator(final Iterator<? extends T> delegate, final int first, final int count)
	{
		Assert.notNull(delegate, "delegate");
		Assert.greaterOrEqual(0, first, "first");
		Assert.greaterOrEqual(0, count, "count");
		this.delegate = delegate;
		this.first = first;
		this.count = count;
	}

	@Override
	public boolean hasNext()
	{
		// check if the item from the previous call to hasNext() has already been retrieved
		if (awaitingCallToNext)
		{
			return true;
		}

		// skip ahead until first requested item is reached
		while (index < first)
		{
			if (delegate.hasNext())
			{
				delegate.next();
				index++;
			}
			else
			{
				return false;
			}
		}

		if (getReturnedCount() < count && delegate.hasNext())
		{
			next = delegate.next();
			awaitingCallToNext = true;
			index++;
			return true;
		}

		awaitingCallToNext = false;
		return false;
	}

	private int getReturnedCount()
	{
		return index - first;
	}

	@Override
	public T next()
	{
		if (awaitingCallToNext || hasNext())
		{
			awaitingCallToNext = false;
			return next;
		}
		throw new NoSuchElementException();
	}

	@Override
	public void remove()
	{
		delegate.remove();
	}
}
