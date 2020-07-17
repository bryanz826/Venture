package com.example.utils;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.refs.ReferenceResource;

public class DataHandler
{
	public static void load(Object obj, String fileName)
	{
		String filePath = ReferenceResource.DATA_LOC + fileName;
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {

			List<Field> fields = new ArrayList<Field>();
			for (Class<?> c = obj.getClass(); c != null; c = c.getSuperclass()) {
				fields.addAll(Arrays.asList(c.getDeclaredFields()));
			}

			String line;
			while ((line = br.readLine()) != null) {
				line = line.replaceAll("\\s", "");

				String fieldName = "";
				String fieldValue = "";

				if (line.indexOf('=') > 0) {
					fieldName = line.substring(0, line.indexOf('='));
					fieldValue = line.substring(line.indexOf('=') + 1);
				}

				for (Field field : fields) {
					field.setAccessible(true);

					if (field.getName().equals(fieldName)) {
						Type fieldType = field.getType();

						if (fieldType.equals(int.class)) {
							field.set(obj, Integer.parseInt(fieldValue));
						}
					}
				}
			}
		}
		catch (NumberFormatException e) {
			System.err.println("Field value has a conflicting data type!");
			e.printStackTrace();
		}
		catch (IOException | IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	public static void save(Object obj, String fileName, String... excludedFieldNames)
	{
		String filePath = ReferenceResource.DATA_LOC + fileName;

		List<Field> fields = new ArrayList<Field>();
		for (Class<?> c = obj.getClass(); c != null; c = c.getSuperclass()) {
			fields.addAll(Arrays.asList(c.getDeclaredFields()));
		}

		StringBuffer inputBuffer = new StringBuffer();
		for (Field field : fields) {
			field.setAccessible(true);

			appendFieldData: {
				for (String excluded : excludedFieldNames)
					if (field.getName().equals(excluded)) break appendFieldData;

				try {
					inputBuffer.append(field.getName() + "=" + field.get(obj));
					inputBuffer.append('\n');
				}
				catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}

		FileOutputStream fos;
		try {
			fos = new FileOutputStream(filePath);
			fos.write(inputBuffer.toString().getBytes());
			fos.close();
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
