/*
 * Project: xenophobia
 * Class: com.leontg77.xenophobia.Main
 *
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 Leon Vaktskjold <leontg77@gmail.com>.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package fr.jnath.xenophobia;


import fr.jnath.UHCAPI.scenario.Scenario;
import fr.jnath.xenophobia.listeners.Listeners;
import me.libraryaddict.disguise.DisguiseAPI;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;
import me.libraryaddict.disguise.disguisetypes.MobDisguise;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

/**
 * The main class of the plugin class.
 *
 * @author LeonTG77
 *
 * Modify by Jnath for Jnath's UHCApi
 */
public class Main extends JavaPlugin {
    public static final String PREFIX = "§aXenophobia §8» §7";

    @Override
    public void onEnable() {
        new Scenario("xenophobia_scenario","§cXénophobia",PREFIX, Material.EMERALD, scenario -> {
            MobDisguise mobDisguise = new MobDisguise(DisguiseType.VILLAGER, true, true);

            mobDisguise.setKeepDisguiseOnPlayerDeath(false);
            mobDisguise.setViewSelfDisguise(false);
            mobDisguise.setHearSelfDisguise(false);

            Listeners listener = new Listeners(mobDisguise);

            Bukkit.getPluginManager().registerEvents(listener, this);
            Bukkit.getWorlds()
                    .forEach(world -> Arrays.stream(world.getLoadedChunks())
                            .forEach(chunk -> Arrays.stream(chunk.getEntities())
                                    .filter(entity -> entity instanceof LivingEntity)
                                    .filter(living -> !DisguiseAPI.isDisguised(living) || DisguiseAPI.getDisguise(living).getType() != mobDisguise.getType())
                                    .forEach(living -> DisguiseAPI.disguiseToAll(living, mobDisguise))));
        }, new String[] {"§5Change all the entity on png","","§aOriginal plugin made by LeonTG77","§aModify by jnath for jnath's UHCApi"});
    }
}