package com.jeramtough.jtandroid.java;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author 11718
 *         on 2017  November 21 Tuesday 16:02.
 */

public class ExtractedZip implements ExtractedFile
{
	private File file;
	
	public ExtractedZip(File file)
	{
		this.file = file;
	}
	
	
	
	@Override
	public void extract(String whereAraExtractedPath)
	{
		try
		{
			ZipFile zipFile = new ZipFile(file);
			Enumeration e = zipFile.entries();
			while (e.hasMoreElements())
			{
				ZipEntry zipEntry = (ZipEntry) e.nextElement();
				if (zipEntry.isDirectory())
				{
					String name = zipEntry.getName();
					Directory directory =
							new Directory(whereAraExtractedPath + File.separator + name);
					directory.mkdirs();
				}
				else
				{
					String name = zipEntry.getName();
					
					File f = new File(
							whereAraExtractedPath + File.separator + zipEntry.getName());
					f.createNewFile();
					InputStream is = zipFile.getInputStream(zipEntry);
					FileOutputStream fos = new FileOutputStream(f);
					int length = 0;
					byte[] b = new byte[1024];
					while ((length = is.read(b, 0, 1024)) != -1)
					{
						fos.write(b, 0, length);
					}
					is.close();
					fos.close();
				}
			}
			zipFile.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
