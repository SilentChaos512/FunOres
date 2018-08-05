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
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.silentchaos512.funores.FunOres;
import net.silentchaos512.funores.lib.EnumDriedItem;
import net.silentchaos512.funores.lib.IDisableable;
import net.silentchaos512.funores.lib.Names;
import net.silentchaos512.lib.registry.IAddRecipes;
import net.silentchaos512.lib.registry.ICustomModel;
import net.silentchaos512.lib.registry.RecipeMaker;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class ItemDried extends ItemFood implements IDisableable, IAddRecipes, ICustomModel {
    public ItemDried() {
        super(1, 0.1f, true);
        setHasSubtypes(true);
        setMaxDamage(0);
    }

    @Override
    public void addInformation(ItemStack stack, World world, List list, ITooltipFlag flag) {
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
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list) {
        if (!isInCreativeTab(tab)) return;

        // Add only non-disabled items for display!
        for (ItemStack stack : getSubItems(this))
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
        return "item." + FunOres.MOD_ID + "." + (e == null ? Names.DRIED_ITEM : e.getName());
    }

    @Override
    public void registerModels() {
        for (EnumDriedItem item : EnumDriedItem.values()) {
            // Don't load disabled item models.
            if (!FunOres.registry.isItemDisabled(item.getItem())) {
                ModelResourceLocation model = new ModelResourceLocation(FunOres.RESOURCE_PREFIX + item.textureName, "inventory");
                ModelLoader.setCustomModelResourceLocation(this, item.ordinal(), model);
            }
        }
    }
}
