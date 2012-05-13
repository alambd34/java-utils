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

package de.danielbechler.util.date;

import java.text.*;
import java.util.*;

/** @author Daniel Bechler */
public final class DateUtils
{
	private static final TimeZone UTC = TimeZone.getTimeZone("UTC");

	private DateUtils()
	{
	}

	/**
	 * Creates an ISO 8601 compliant {@link java.text.DateFormat} using the UTC time zone.
	 * <p/>
	 * Example: 2012-05-13T14:56:15Z
	 *
	 * @return
	 */
	public static DateFormat getDateTimeUTCFormat()
	{
		@SuppressWarnings({"SimpleDateFormatWithoutLocale"})
		final DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
		format.setTimeZone(UTC);
		return format;
	}

	/**
	 * Creates an (almost) ISO 8601 compliant {@link java.text.DateFormat} using the UTC time zone. It takes the liberty to add
	 * the milliseconds to the formatted string.
	 * <p/>
	 * Example: 2012-05-13T14:56:15.123Z
	 *
	 * @return
	 */
	public static DateFormat getDateTimeMillisecondsUTCFormat()
	{
		@SuppressWarnings({"SimpleDateFormatWithoutLocale"})
		final DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		return format;
	}

	public static Date getDateFromDateTimeUTCFormat(final String dateString)
	{
		try
		{
			return getDateTimeUTCFormat().parse(dateString);
		}
		catch (ParseException e)
		{
			throw new RuntimeException(e);
		}
	}

	public static Date getDateFromDateTimeMillisecondsUTCFormat(final String dateString)
	{
		try
		{
			return getDateTimeMillisecondsUTCFormat().parse(dateString);
		}
		catch (ParseException e)
		{
			throw new RuntimeException(e);
		}
	}
}
