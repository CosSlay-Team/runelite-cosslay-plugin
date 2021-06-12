package com.github.adituv.cosslayplugin.util;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class EnumCaseInsensitiveTypeAdapter<T extends Enum<T>> extends TypeAdapter<T>
{

	private final Class<T> enumClass;

	@Override
	public void write(JsonWriter writer, T anEnum) throws IOException
	{
		String text = anEnum.toString().toLowerCase();
		writer.value(text);
	}

	@Override
	public T read(JsonReader reader) throws IOException
	{
		String textLower = reader.nextString();

		for (T enumConst : this.enumClass.getEnumConstants())
		{
			if (textLower.equalsIgnoreCase(enumConst.toString()))
			{
				return enumConst;
			}
		}

		return null;
	}

	public EnumCaseInsensitiveTypeAdapter(Class<T> enumClass)
	{
		this.enumClass = enumClass;
	}
}
