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

package de.danielbechler.util.api;

import de.danielbechler.util.assertion.*;
import de.danielbechler.util.codec.*;
import de.danielbechler.util.url.*;

import java.nio.charset.*;

/** @author Daniel Bechler */
public final class GravatarUrlBuilder
{
	public enum Rating
	{
		/** suitable for display on all websites with any audience type. */
		G,

		/**
		 * may contain rude gestures, provocatively dressed individuals, the lesser swear words, or mild
		 * violence.
		 */
		PG,

		/** may contain such things as harsh profanity, intense violence, nudity, or hard drug use. */
		R,

		/** may contain hardcore sexual imagery or extremely disturbing violence. */
		X
	}

	public static class DefaultImage
	{
		public static final DefaultImage NONE = new DefaultImage("404");
		public static final DefaultImage MYSTERY_MAN = new DefaultImage("mm");

		private final String value;

		private DefaultImage(final String value)
		{
			this.value = value;
		}

		public static DefaultImage custom(final String defaultImage)
		{
			return new DefaultImage(defaultImage);
		}

		public String getValue()
		{
			return value;
		}
	}

	private final String hash;

	private int size = 200;
	private boolean secure;
	private DefaultImage defaultImage = DefaultImage.NONE;
	private Rating rating = Rating.G;
	private boolean forceDefault;

	private GravatarUrlBuilder(final String hash)
	{
		Assert.hasText(hash, "Argument [hash] must not be empty");
		this.hash = hash;
	}

	public GravatarUrlBuilder withSize(final int size)
	{
		this.size = size;
		return this;
	}

	public GravatarUrlBuilder secure(final boolean secure)
	{
		this.secure = secure;
		return this;
	}

	public GravatarUrlBuilder withDefaultImage(final DefaultImage defaultImage)
	{
		this.defaultImage = defaultImage;
		return this;
	}

	public GravatarUrlBuilder withForceDefault(final boolean forceDefault)
	{
		this.forceDefault = forceDefault;
		return this;
	}

	public GravatarUrlBuilder withRating(final Rating rating)
	{
		this.rating = rating;
		return this;
	}

	public String toRequestUrl()
	{
		final StringBuilder sb = new StringBuilder();
		if (secure)
		{
			sb.append("https://secure.gravatar.com");
		}
		else
		{
			sb.append("http://www.gravatar.com");
		}
		sb.append("/avatar/").append(hash);
		final UrlParameters params = new UrlParameters();
		if (size > 0)
		{
			params.set("s", Integer.toString(size));
		}
		if (defaultImage != null)
		{
			params.set("d", defaultImage.getValue());
		}
		if (rating != null)
		{
			params.set("r", rating.name().toLowerCase());
		}
		if (forceDefault)
		{
			params.set("f", "y");
		}
		if (!params.isEmpty())
		{
			sb.append('?').append(params.toQueryString(Charset.forName("UTF-8")));
		}
		return sb.toString();
	}

	public static String emailToHash(final String emailAddress)
	{
		Assert.hasText(emailAddress, "Argument [emailAddress] must not be empty");
		return MD5.forString(emailAddress.toLowerCase().trim());
	}

	public static GravatarUrlBuilder forEmailAddress(final String emailAddress)
	{
		return forHash(emailToHash(emailAddress));
	}

	public static GravatarUrlBuilder forHash(final String hash)
	{
		return new GravatarUrlBuilder(hash);
	}

	public static GravatarUrlBuilder forDummyHash()
	{
		return forHash("00000000000000000000000000000000");
	}

	@Override
	public String toString()
	{
		return toRequestUrl();
	}
}
