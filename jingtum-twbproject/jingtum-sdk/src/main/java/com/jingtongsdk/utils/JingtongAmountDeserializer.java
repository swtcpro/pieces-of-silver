package com.jingtongsdk.utils;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.jingtongsdk.bean.Jingtong.reqrsp.TransactionAmount;

public class JingtongAmountDeserializer implements JsonDeserializer<TransactionAmount>
{

	@Override
	public TransactionAmount deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException
	{
		TransactionAmount ta = new TransactionAmount();
		if (json.isJsonObject())
		{
			JsonObject jsonObject = json.getAsJsonObject();
			String value = jsonObject.get("value").getAsString();
			String currency = jsonObject.get("currency").getAsString();
			String issuer = jsonObject.get("issuer").getAsString();
			ta.setCurrency(currency);
			ta.setIssuer(issuer);
			ta.setValue(value);
		}
		else
		{
			ta.setValue(json.getAsString());
		}

		return ta;
	}
}
