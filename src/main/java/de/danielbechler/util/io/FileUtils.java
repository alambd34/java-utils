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
package de.danielbechler.util.io;

import java.io.*;

/** @author Daniel Bechler */
public final class FileUtils
{
	private FileUtils()
	{
	}

	public static File createFileFromByteArray(final byte[] data, final File target) throws IOException
	{
		final File parent = target.getParentFile();
		if (parent != null && !parent.exists())
		{
			if (!parent.mkdirs())
			{
				throw new IOException("Unable to create directory '" + parent.getPath());
			}
		}
		final OutputStream fos = new FileOutputStream(target);
		try
		{
			fos.write(data);
		}
		finally
		{
			fos.close();
		}
		return target;
	}

	public static byte[] readAsBytes(final File file) throws IOException
	{
		final FileInputStream fis = new FileInputStream(file);
		try
		{
			return inputStreamToByteArray(fis, 8192);
		}
		finally
		{
			fis.close();
		}
	}

	public static byte[] inputStreamToByteArray(final InputStream is, final int bufferSize) throws IOException
	{
		final byte[] buffer = new byte[bufferSize];
		final ByteArrayOutputStream os = new ByteArrayOutputStream();
		int length = is.read(buffer);
		while (length > 0)
		{
			os.write(buffer, 0, length);
			length = is.read(buffer);
		}
		return os.toByteArray();
	}
}
