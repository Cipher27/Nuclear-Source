package com.runescape.worldmap;

// Decompiled by DJ v3.10.10.93 Copyright 2007 Atanas Neshkov  Date: 8/7/2008 2:02:56 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   FileOperations.java

import java.io.*;

public class FileData {

	private FileData() {
	}

	public static final byte[] ReadFile(String path) {
		try {
			File file = new File(path);
			int len = (int) file.length();
			byte data[] = new byte[len];
			DataInputStream datainputstream = new DataInputStream(new BufferedInputStream(new FileInputStream(path)));
			datainputstream.readFully(data, 0, len);
			datainputstream.close();
			readCounter++;
			return data;
		} catch (Exception exception) {
			System.out.println("Read Error: " + path);
		}
		return null;
	}

	public static final void WriteFile(String path, byte data[]) {
		try {
			writeCounter++;
			(new File((new File(path)).getParent())).mkdirs();
			FileOutputStream fileoutputstream = new FileOutputStream(path);
			fileoutputstream.write(data, 0, data.length);
			fileoutputstream.close();
			completeWriteCounter++;
		} catch (Throwable throwable) {
			System.out.println("Write Error: " + path);
		}
	}

	public static boolean FileExists(String file) {
		File f = new File(file);
		return f.exists();
	}

	public static int readCounter = 0;
	public static int writeCounter = 0;
	public static int completeWriteCounter = 0;
}