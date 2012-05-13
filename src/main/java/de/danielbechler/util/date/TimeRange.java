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

import de.danielbechler.util.assertion.*;

import java.io.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;

/**
 * Represents a time range. This class is immutable.
 *
 * @author Daniel Bechler
 */
public final class TimeRange implements Serializable
{
	private static final long serialVersionUID = -871772595525048733L;

	/**
	 * Creates a new time range from the oldest and the newest date in the given collection.
	 *
	 * @param dates The dates to create a time range from.
	 *
	 * @return A new time range representing the given dates.
	 */
	public static TimeRange from(final Iterable<Date> dates)
	{
		Date min = null;
		Date max = null;
		for (final Date date : dates)
		{
			if (min == null || min.after(date))
			{
				min = date;
			}
			if (max == null || max.before(date))
			{
				max = date;
			}
		}
		return new TimeRange(min, max);
	}

	private final Date start;
	private final Date end;

	public TimeRange(final Date start, final Date end)
	{
		Assert.notNull(start, "start");
		Assert.notNull(end, "end");
		if (start.compareTo(end) > 0)
		{
			throw new IllegalArgumentException("The start time must be before or the same as the end time");
		}
		this.start = new Date(start.getTime());
		this.end = new Date(end.getTime());
	}

	public Date getStart()
	{
		return new Date(start.getTime());
	}

	public Date getEnd()
	{
		return new Date(end.getTime());
	}

	/**
	 * @param unit The target time unit.
	 *
	 * @return The time difference in the given {@link java.util.concurrent.TimeUnit}.
	 */
	public long getDelta(final TimeUnit unit)
	{
		Assert.notNull(unit, "unit");
		return unit.convert(getDelta(), TimeUnit.MILLISECONDS);
	}

	/**
	 * Checks whether the given date lies in between the start and end time of this time range.
	 *
	 * @param date The date to check.
	 *
	 * @return <code>true</code> if the given date lies in between start and end. Otherwise <code>false</code>.
	 */
	public boolean includes(final Date date)
	{
		if (date == null)
		{
			return false;
		}
		return start.compareTo(date) >= 0 && end.compareTo(date) <= 0;
	}

	/** @return The time difference in milliseconds. */
	public long getDelta()
	{
		return end.getTime() - start.getTime();
	}

	@Override
	public boolean equals(final Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (!(o instanceof TimeRange))
		{
			return false;
		}

		final TimeRange timeRange = (TimeRange) o;

		if (!end.equals(timeRange.end))
		{
			return false;
		}
		if (!start.equals(timeRange.start))
		{
			return false;
		}

		return true;
	}

	@Override
	public int hashCode()
	{
		int result = start.hashCode();
		result = 31 * result + end.hashCode();
		return result;
	}

	@Override
	public String toString()
	{
		final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS", Locale.ENGLISH);
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		final StringBuilder builder = new StringBuilder();
		builder.append(dateFormat.format(start));
		builder.append(" -- ");
		builder.append(dateFormat.format(end));
		return builder.toString();
	}
}
