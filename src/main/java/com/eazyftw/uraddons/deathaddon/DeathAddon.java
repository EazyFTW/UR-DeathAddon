package com.eazyftw.uraddons.deathaddon;

import me.TechsCode.UltraRegions.UltraRegions;
import me.TechsCode.UltraRegions.base.item.XMaterial;
import me.TechsCode.UltraRegions.flags.Flag;
import me.TechsCode.UltraRegions.storage.FlagValue;
import me.TechsCode.UltraRegions.storage.Region;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Optional;

public class DeathAddon extends Flag {

    public DeathAddon(UltraRegions plugin) {
        super(plugin, "DEATH");
    }

    @Override
    public String getName() {
        return "Death";
    }

    @Override
    public String getDescription() {
        return "If disallowed, stops players from dying.";
    }

    @Override
    public XMaterial getIcon() {
        return XMaterial.SKELETON_SKULL;
    }

    @Override
    public FlagValue getDefaultValue() {
        return FlagValue.ALLOW;
    }

    @Override
    public boolean isPlayerSpecificFlag() {
        return true;
    }

    @EventHandler
    public void onDeath(EntityDamageEvent e) {
        if(e.getEntity() instanceof Player) {
            Optional<Region> regionOpt = getTopRegion(e.getEntity().getLocation());
            regionOpt.ifPresent(region -> {
                FlagValue value = evaluate(region, (Player)e.getEntity());
                if(value == FlagValue.DISALLOW && ((Player) e.getEntity()).getHealth() - e.getFinalDamage() <= 0d) {
                    e.setCancelled(true);
                    ((Player) e.getEntity()).setHealth(1d);
                }
            });
        }
    }
}
