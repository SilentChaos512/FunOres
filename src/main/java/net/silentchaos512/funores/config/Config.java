/*
 * Fun Ores -- Config
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

package net.silentchaos512.funores.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.silentchaos512.funores.FunOres;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Config {
    private static Config INSTANCE = new Config();
    //@formatter:off
    private static ForgeConfigSpec spec = new ForgeConfigSpec.Builder()
        .comment("General settings TODO")
        .push("general")
            .define("testValue", 42)
        .pop()
        .build();
    //@formatter:on

    private CommentedFileConfig configData;

    private void loadFrom(final Path configRoot) {
        Path configFile = configRoot.resolve(FunOres.MOD_ID + ".toml");
        configData = CommentedFileConfig.builder(configFile).sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();
        configData.load();

        if (!spec.isCorrect(configData)) {
            FunOres.LOGGER.warn("Configuration file {} is not correct. Correcting", configRoot);
            spec.correct(configData, (action, path, incorrectValue, correctedValue) ->
                    FunOres.LOGGER.warn("Incorrect key {} was corrected from {} to {}", path, incorrectValue, correctedValue));
            configData.save();
        }

        FunOres.LOGGER.debug("Loaded config from {}", configFile);
    }

    public static void load() {
        INSTANCE.loadFrom(Paths.get("config"));
    }

    public static void init(File file) {
    }

    public static void save() {
    }
}
