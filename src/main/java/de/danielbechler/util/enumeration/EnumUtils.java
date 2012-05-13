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
package de.danielbechler.util.enumeration;

import de.danielbechler.util.assertion.*;

import java.lang.Class;
import java.lang.Enum;
import java.lang.String;

/** @author Daniel Bechler */
public final class EnumUtils
{
	private EnumUtils()
	{
	}

	public static <T extends java.lang.Enum<T>> String getResourceKey(final T object)
	{
		return object.getDeclaringClass().getSimpleName() + "." + object.name();
	}

	public static <T extends Enum<T>> String getCanonicalResourceKey(final T object)
	{
		return object.getDeclaringClass().getName() + "." + object.name();
	}

	/**
	 * Returns the enum constant matching the given name, or null, if it didn't match any of the elements. This method will
	 * never throw any exceptions, as long as valid arguments are passed to it.
	 *
	 * @param enumType	  The enum type with the constants to scan.
	 * @param name		  The name of the enum constant to find.
	 * @param caseSensitive Flag to turn case sensitivity on or off.
	 * @param <T>           The type of the given Enum.
	 *
	 * @return An enum constant matching the given name, or <code>null</code>.
	 */
	public static <T extends Enum<T>> T valueOf(final Class<T> enumType, final String name, final boolean caseSensitive)
	{
		Assert.notNull(enumType, "enumType");
		Assert.hasText(name, "name");
		final T[] enumConstants = enumType.getEnumConstants();
		if (enumConstants == null)
		{
			return null;
		}
		for (final T enumConstant : enumConstants)
		{
			if (caseSensitive)
			{
				if (enumConstant.name().equals(name))
				{
					return enumConstant;
				}
			}
			else
			{
				if (enumConstant.name().equalsIgnoreCase(name))
				{
					return enumConstant;
				}
			}
		}
		return null;
	}

	/**
	 * Convenience method that defaults to case insensitive matching.
	 *
	 * @see #valueOf(Class, String, boolean)
	 */
	public static <T extends Enum<T>> T valueOf(final Class<T> enumType, final String name)
	{
		return valueOf(enumType, name, false);
	}

	/**
	 * Returns a lower-cased and hyphenated presentation of the given enum constant.
	 *
	 * @param enumConstant
	 * @param <T>
	 *
	 * @return
	 */
	public static <T extends Enum<T>> String cssClass(final T enumConstant)
	{
		return enumConstant.name().toLowerCase().replace('_', '-');
	}
}
