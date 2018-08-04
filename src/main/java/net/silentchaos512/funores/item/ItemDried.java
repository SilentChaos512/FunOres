/*
 * Fun Ores -- ItemDried
 * Copyright (C) 2018 SilentChaos512
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation version 3
 * of the License.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.silentchaos512.funores.item;

import com.google.common.collect.Lists;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.lib.EnumDriedItem;
import net.silentchaos512.funores.lib.IDisableable;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.lib.item.ItemFoodSL;
import net.silentchaos512.lib.registry.RecipeMaker;
import net.silentchaos512.lib.util.ItemHelper;
import org.lwjgl.input.Keyboard;

import java.util.List;
import java.util.Map;

public class ItemDried extends ItemFoodSL implements IDisableable {

    public ItemDried() {
        super(EnumDriedItem.values().length, FunOres.MOD_ID, Names.DRIED_ITEM, 1, 0.1f, true);
        setMaxStackSize(64);
        setHasSubtypes(true);
        setMaxDamage(0);
        setTranslationKey(Names.DRIED_ITEM);
    }

    @Override
    public void clAddInformation(ItemStack stack, World world, List list, boolean advanced) {
        boolean shifted = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT);

        // Display flavor text when shift is held.
        if (shifted)
            for (String line : FunOres.localizationHelper.getItemDescriptionLines(getEnum(stack).name))
                list.add(TextFormatting.ITALIC + line);
    }

    @Override
    public void addRecipes(RecipeMaker recipes) {
        ItemStack driedFlesh = getStack(EnumDriedItem.DRIED_FLESH);
        if (!FunOres.registry.isItemDisabled(driedFlesh))
            recipes.addShapeless("dried_flesh_leather", new ItemStack(Items.LEATHER), driedFlesh, driedFlesh);
    }

    @Override
    public int getHealAmount(ItemStack stack) {
        EnumDriedItem e = getEnum(stack);
        if (e != null)
            return e.foodValue;
        return super.getHealAmount(stack);
    }

    @Override
    public float getSaturationModifier(ItemStack stack) {
        EnumDriedItem e = getEnum(stack);
        if (e != null)
            return e.saturationValue;
        return super.getSaturationModifier(stack);
    }

    @Override
    protected void clGetSubItems(Item item, CreativeTabs tab, List<ItemStack> list) {
        if (!ItemHelper.isInCreativeTab(item, tab))
            return;

        // Add only non-disabled items for display!
        for (ItemStack stack : getSubItems(item))
            if (!FunOres.registry.isItemDisabled(stack))
                list.add(stack);
    }

    @Override
    public List<ItemStack> getSubItems(Item item) {
        // Make a list of all possible items, including disabled ones.
        List<ItemStack> ret = Lists.newArrayList();
        for (EnumDriedItem e : EnumDriedItem.values())
            ret.add(new ItemStack(item, 1, e.meta));
        return ret;
    }

    public ItemStack getStack(EnumDriedItem e) {
        return new ItemStack(this, 1, e.meta);
    }

    public EnumDriedItem getEnum(ItemStack stack) {
        for (EnumDriedItem e : EnumDriedItem.values())
            if (e.meta == stack.getItemDamage())
                return e;

        return null;
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        EnumDriedItem e = getEnum(stack);
        return "item.funores:" + (e == null ? Names.DRIED_ITEM : e.name);
    }

    @Override
    public void getModels(Map<Integer, ModelResourceLocation> models) {
        for (EnumDriedItem item : EnumDriedItem.values()) {
            if (!FunOres.registry.isItemDisabled(item.getItem())) // Don't load disabled item models.
                models.put(item.meta, new ModelResourceLocation((modId + ":" + item.textureName).toLowerCase(), "inventory"));
        }
    }
}
