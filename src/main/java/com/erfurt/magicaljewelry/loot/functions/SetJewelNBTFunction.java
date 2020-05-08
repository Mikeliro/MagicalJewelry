package com.erfurt.magicaljewelry.loot.functions;

import com.erfurt.magicaljewelry.MagicalJewelry;
import com.erfurt.magicaljewelry.objects.items.JewelItem;
import com.erfurt.magicaljewelry.util.interfaces.IJewelEffects;
import com.erfurt.magicaljewelry.util.interfaces.IJewelRarity;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootFunction;
import net.minecraft.world.storage.loot.conditions.ILootCondition;

import java.util.Random;

public class SetJewelNBTFunction extends LootFunction implements IJewelEffects, IJewelRarity
{
	private static int rarityID;
	
	private static int gemColor;
	
	private SetJewelNBTFunction(ILootCondition[] conditionIn)
	{
		super(conditionIn);
	}

	@Override
	public ItemStack doApply(ItemStack stack, LootContext context)
	{
		rarityID = getRarity();
		JewelItem.setJewelRarity(stack, rarityID);
		
		JewelItem.setJewelEffects(stack, getEffects(rarityID, JewelItem.jewelEffects));

		int colorID = new Random().nextInt(DyeColor.values().length);
		gemColor = DyeColor.byId(colorID).getColorValue();
		JewelItem.setGemColor(stack, gemColor);
		return stack;
	}
	
	public static class Serializer extends LootFunction.Serializer<SetJewelNBTFunction>
	{
        public static final Serializer INSTANCE = new Serializer(new ResourceLocation(MagicalJewelry.MOD_ID, "set_jewel_nbt"), SetJewelNBTFunction.class);

        Serializer(ResourceLocation location, Class<SetJewelNBTFunction> clazz)
        {
            super(location, clazz);
        }

        @Override
        public void serialize(JsonObject object, SetJewelNBTFunction functionClazz, JsonSerializationContext serializationContext)
        {
            object.addProperty("Rarity", rarityID);
            object.addProperty("GemColor", gemColor);
        }

        @Override
        public SetJewelNBTFunction deserialize(JsonObject object, JsonDeserializationContext deserializationContext, ILootCondition[] conditionsIn)
        {
            return new SetJewelNBTFunction(conditionsIn);
        }
    }
}