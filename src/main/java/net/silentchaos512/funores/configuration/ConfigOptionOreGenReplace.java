/*
 * Fun Ores -- ConfigOptionOreGenReplace
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

package net.silentchaos512.funores.configuration;

import net.minecraftforge.common.config.Configuration;
import net.silentchaos512.funores.lib.IHasOre;

public class ConfigOptionOreGenReplace extends ConfigOptionOreGen {

    public ConfigOptionOreGenReplace(IHasOre ore) {
        super(ore);
    }

    public boolean replaceExisting = false;

    @Override
    public ConfigOption loadValue(Configuration c, String category, String comment) {
        if (isExample) {
            return loadExample(c);
        }

        super.loadValue(c, category, comment);
        replaceExisting = c.get(category, "ReplaceExisting", replaceExisting).getBoolean();

        return this.validate();
    }
}
