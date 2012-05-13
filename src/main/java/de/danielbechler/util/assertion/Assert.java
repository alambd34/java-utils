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

package de.danielbechler.util.assertion;

import de.danielbechler.util.collection.*;
import de.danielbechler.util.object.*;
import de.danielbechler.util.text.*;

import java.util.*;

/** @author Daniel Bechler */
public class Assert
{
	private Assert()
	{
	}

	public static void equalTypesOrNull(final Object... objects)
	{
		final Collection<Class<?>> types = ClassUtils.typesOf(objects);
		Class<?> previousType = null;
		for (final Class<?> type : types)
		{
			if (previousType != null && !type.equals(previousType))
			{
				throw new IllegalArgumentException(
						"The given objects should be either null or of the same type ('" + previousType + "') = " + types);
			}
			previousType = type;
		}
	}

	public static void notEmpty(final Collection<?> collection, final String name)
	{
		notEmpty(name, "name");
		if (CollectionUtils.isEmpty(collection))
		{
			throw new IllegalArgumentException("'" + name + "' must not be null or empty");
		}
	}

	/**
	 * Same as {@link #hasText(String, String)}.
	 *
	 * @see #hasText(String, String)
	 */
	public static void notEmpty(final String text, final String name) throws IllegalArgumentException
	{
		hasText(text, name);
	}

	/**
	 * Ensures that the given <code>value</code> contains characters.
	 *
	 * @param value The value to check.
	 * @param name  The name of the variable (used for the exception message).
	 *
	 * @throws IllegalArgumentException If the given value is <code>null</code> or doesn't contain any non-whitespace
	 *                                  characters.
	 */
	public static void hasText(final String value, final String name) throws IllegalArgumentException
	{
		if (StringUtils.isEmpty(name)) // Yo dawg, I heard you like assertions, so I put an assertion in your assertion
		{
			throw new IllegalArgumentException("'name' must not be null or empty");
		}
		if (StringUtils.isEmpty(value))
		{
			throw new IllegalArgumentException("'" + name + "' must not be null or empty");
		}
	}

	public static <T> T notNull(final T object, final String name)
	{
		if (object == null)
		{
			throw escalate("Argument [%s] must not be null", name);
		}
		return object;
	}

	public static <T extends CharSequence> T notEmpty(final T s, final String name)
	{
		if (s == null || s.toString().trim().length() == 0)
		{
			throw escalate("Argument [%s] must not be empty", name);
		}
		return s;
	}

	public static int greater(final int boundary, final int value, final String name)
	{
		if (value <= boundary)
		{
			throw escalate("Argument [%s] must be greater than %d (was %d)", name, boundary, value);
		}
		return value;
	}

	public static int greaterOrEqual(final int boundary, final int value, final String name)
	{
		if (value < boundary)
		{
			throw escalate("Argument [%s] must be greater or equal to %d (was %d)", name, boundary, value);
		}
		return value;
	}

	public static long greaterOrEqual(final long boundary, final long value, final String name)
	{
		if (value < boundary)
		{
			throw escalate("Argument [%s] must be greater or equal to %d (was %d)", name, boundary, value);
		}
		return value;
	}

	public static int less(final int value, final int boundary, final String name)
	{
		if (value >= boundary)
		{
			throw escalate("Argument [%s] must be less than %d (was %d)", name, boundary, value);
		}
		return value;
	}

	public static int lessOrEqual(final int value, final int boundary, final String name)
	{
		if (value > boundary)
		{
			throw escalate("Argument [%s] must be less than %d (was %d)", name, boundary, value);
		}
		return value;
	}

	public static int between(final int value, final int min, final int max, final boolean inclusive, final String name)
	{
		if (inclusive)
		{
			if (value < min || value > max)
			{
				throw escalate("Argument [%s] must be between %d and %d (%s) - was: %d", name, min, max, "inclusive", value);
			}
		}
		else
		{
			if (value <= min || value >= max)
			{
				throw escalate("Argument [%s] must be between %d and %d (%s) - was: %d", name, min, max, "exclusive", value);
			}
		}
		return value;
	}

	private static IllegalArgumentException escalate(final String message, final Object... args)
	{
		throw new IllegalArgumentException(String.format(message, args));
	}
}
