package com.opticalcobra.storybear.db;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Blob {
	public static byte[] create(Object o){
		try {
			ByteArrayOutputStream bytearrayos = new ByteArrayOutputStream();
			ObjectOutputStream os =new ObjectOutputStream(bytearrayos);
			//ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName));
			os.writeObject(o);
			byte[] ba = bytearrayos.toByteArray();
			os.close();
			return ba;
	
		} catch (FileNotFoundException e) {
			System.err.println("File not Found");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("IO Exception");
			e.printStackTrace();
		}
		return null;
	}
	
	public static Object read(byte[] ba){
		Object result = null;
		try {
			ObjectInputStream is = new ObjectInputStream(new ByteArrayInputStream(ba));
			result = is.readObject();
			is.close();
		} catch (FileNotFoundException e) {
			System.err.println("File not Found");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("IO Exception");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.err.println("Class not found");
			e.printStackTrace();
		}
		return result;
	}
	
	public static void writeFile(String fileName, Object o){
		try {
			ObjectOutputStream os =new ObjectOutputStream(new FileOutputStream(fileName));
			//ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName));
			os.writeObject(o);
			os.close();
	
		} catch (FileNotFoundException e) {
			System.err.println("File not Found");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("IO Exception");
			e.printStackTrace();
		}
	}

	public static Object readFile(String fileName){
		Object result = null;
		try {
			ObjectInputStream is = new ObjectInputStream(new FileInputStream(fileName));
			result = is.readObject();
			is.close();
		} catch (FileNotFoundException e) {
			System.err.println("File not Found");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("IO Exception");
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			System.err.println("Class not found");
			e.printStackTrace();
		}
		return result;
	}
}
