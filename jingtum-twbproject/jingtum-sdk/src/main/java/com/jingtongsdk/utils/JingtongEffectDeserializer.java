package com.jingtongsdk.utils;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.jingtongsdk.bean.Jingtong.reqrsp.Effect;
import com.jingtongsdk.bean.Jingtong.reqrsp.effect.OfferBought;
import com.jingtongsdk.bean.Jingtong.reqrsp.effect.OfferCancelled;
import com.jingtongsdk.bean.Jingtong.reqrsp.effect.OfferCreated;
import com.jingtongsdk.bean.Jingtong.reqrsp.effect.OfferFunded;
import com.jingtongsdk.bean.Jingtong.reqrsp.effect.OfferPartiallyFunded;

public class JingtongEffectDeserializer implements JsonDeserializer<Effect>
{

	@Override
	public Effect deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException
	{
		Effect effect = null;
		if (json.isJsonObject())
		{
			JsonObject jsonObject = json.getAsJsonObject();
			String effectType = jsonObject.get("effect").getAsString();
			if ("offer_created".equals(effectType))
			{
				effect = context.deserialize(json, OfferCreated.class);
			}
			else if("offer_bought".equals(effectType))
			{
				effect = context.deserialize(json, OfferBought.class);
			}
			else if("offer_cancelled".equals(effectType))
			{
				effect = context.deserialize(json, OfferCancelled.class);
			}
			else if("offer_funded".equals(effectType))
			{
				effect = context.deserialize(json, OfferFunded.class);
			}
			else if("offer_partially_funded".equals(effectType))
			{
				effect = context.deserialize(json, OfferPartiallyFunded.class);
			}
		}

		return effect;
	}
}
