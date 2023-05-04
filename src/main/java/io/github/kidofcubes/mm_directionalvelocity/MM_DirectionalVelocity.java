package io.github.kidofcubes.mm_directionalvelocity;

import io.lumine.mythic.bukkit.events.MythicMechanicLoadEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class MM_DirectionalVelocity extends JavaPlugin implements Listener {


    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }


    /**
     * why is this how you register mechanics wtf
     * @param event
     */
    @EventHandler
    public void onMythicMechanicLoad(MythicMechanicLoadEvent event)	{
        if(event.getMechanicName().equalsIgnoreCase("dirvelocity"))	{
            event.register(new DirectionalVelocityMechanic(event.getConfig()));
        }
    }
}
