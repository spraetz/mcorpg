package com.gmail.spraetz.spells;

import com.gmail.spraetz.plugin.Engine;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

/**
 * Created by spraetz on 2/17/14.
 */
public class StoneWall extends Spell {

    public StoneWall(PlayerInteractEvent event, Engine plugin){
        super(event, plugin);
    }

    @Override
    public void spellEffects(PlayerEvent event) {

        PlayerInteractEvent e = (PlayerInteractEvent)event;

        //Get the block the player clicked.
        Block b = event.getPlayer().getTargetBlock(null, 20);

        Location wallMiddle = event.getPlayer().getTargetBlock(null, 20).getLocation();
        Location playerLocation = event.getPlayer().getLocation();

        // Find slope of perpendicular line
        Double slope = -(wallMiddle.getX() - playerLocation.getX()) / (wallMiddle.getZ() - playerLocation.getZ());

        //Find point 1
        Double x1 = wallMiddle.getX() + (1 / Math.sqrt(1+Math.pow(slope, 2)));
        Double z1 = wallMiddle.getZ() + (slope / Math.sqrt(1+Math.pow(slope, 2)));

        //Find point 2
        Double x2 = wallMiddle.getX() - (3 * (1 / Math.sqrt(1+Math.pow(slope, 2))));
        Double z2 = wallMiddle.getZ() - (3 * (slope / Math.sqrt(1+Math.pow(slope, 2))));

        draw((int)Math.round(x1), (int)Math.round(z1),
                (int)Math.round(x2), (int)Math.round(z2),  wallMiddle.getBlockY(), e);

    }

    private void draw(int x1, int z1, int x2, int z2, int y, PlayerInteractEvent event){
        boolean xz_swap = false;
        if (Math.abs(z2 - z1) > Math.abs(x2 - x1)) {
            xz_swap = true;
            int temp = x1;
            x1 = z1;
            z1 = temp;
            temp = x2;
            x2 = z2;
            z2 = temp;
        }

        // If line goes from right to left, swap the endpoints
        if (x2 - x1 < 0) {
            int temp = x1;
            x1 = x2;
            x2 = temp;
            temp = z1;
            z1 = z2;
            z2 = temp;
        }

        int x,                       // Current x position
                z = z1,                  // Current z position
                e = 0,                   // Current error
                m_num = z2 - z1,         // Numerator of slope
                m_denom = x2 - x1,       // Denominator of slope
                threshold  = m_denom/2;  // Threshold between E and NE increment 

        for (x = x1; x < x2; x++) {
            if (xz_swap)
                setColumn(z, y, x, event);
            else setColumn(x, y, z, event);

            e += m_num;

            // Deal separately with lines sloping upward and those
            // sloping downward
            if (m_num < 0) {
                if (e < -threshold) {
                    e += m_denom;
                    z--;
                }
            }
            else if (e > threshold) {
                e -= m_denom;
                z++;
            }
        }

        if (xz_swap)
            setColumn(z, y, x, event);
        else setColumn(x, y, z, event);

    }
    
    private void setColumn(int x, int y, int z, PlayerInteractEvent event){
        for (int i = y-2; i < y+4; i++){

            Block block = event.getPlayer().getWorld().getBlockAt(x, i, z);
            if(block.getType() == Material.AIR){
                block.setType(Material.BEDROCK);
                BedRockRemover remover = new BedRockRemover(block, plugin);
                remover.remove();
            }
        }
    }

    private class BedRockRemover extends BukkitRunnable {

        private final Engine plugin;
        private final Block block;

        BedRockRemover(Block block, Engine plugin){
            this.plugin = plugin;
            this.block = block;
        }

        public void remove(){
            //Generate a random number between 80 and 120 ticks (4 to 6 seconds).
            Random rand = new Random();
            plugin.getServer().getScheduler().runTaskLater(plugin, this, rand.nextInt(40) + 80);
        }

        @Override
        public void run() {
            if(block.getType() == Material.BEDROCK){
                block.setType(Material.AIR);
            }
        }
    }
}
