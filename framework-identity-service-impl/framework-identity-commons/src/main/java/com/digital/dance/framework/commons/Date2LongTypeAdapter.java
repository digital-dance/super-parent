package com.digital.dance.framework.commons;

import java.util.regex.Pattern;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.util.regex.Matcher;
import com.google.gson.JsonPrimitive;

import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonParseException;
import com.google.gson.JsonDeserializationContext;

/**
 * 
 * @author liuxiny
 *
 */
public class Date2LongTypeAdapter implements JsonDeserializer<Date>, JsonSerializer<Date> {

	private static Log log = new Log(Date2LongTypeAdapter.class);

	public Date deserialize(JsonElement dateJson, Type typeOfSource, JsonDeserializationContext jsonSerializationContext)
			throws JsonParseException {
		
		Pattern datePattern = Pattern.compile("[0-9]{4}-[0-9]{2}-[0-9]{2}");
		String dateFormat = "yyyy-MM-dd";
		Matcher matcher = datePattern.matcher(dateJson.getAsJsonPrimitive().getAsString());
		boolean isDate = matcher.matches();

		SimpleDateFormat dateFormatter = new SimpleDateFormat(dateFormat);

		if (dateJson.getAsJsonPrimitive().getAsString() != null) {
			if (isDate) {
				try {
					return dateFormatter.parse(dateJson.getAsJsonPrimitive().getAsString());
				} catch (ParseException e) {
					log.error(e.getMessage(), e);
				}
			} else {
				return new java.util.Date(dateJson.getAsJsonPrimitive().getAsLong());
			}
		} else {
			return null;
		}
		return null;
	}

	public JsonElement serialize(Date date, Type typeOfSource, JsonSerializationContext jsonSerializationContext) {
		return new JsonPrimitive(date.getTime());
	}

}
