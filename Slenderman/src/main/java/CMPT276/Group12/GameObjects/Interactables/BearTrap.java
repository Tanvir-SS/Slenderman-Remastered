package CMPT276.Group12.GameObjects.Interactables;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import CMPT276.Group12.GameObjects.Actors.Player;
import CMPT276.Group12.Mechanics.Game;

public class BearTrap extends Interactable {
    private static String[] trap32pxPath = {
            "/sprites/mono_set/beartrap.png"
    };

    private Game map;

    public BearTrap(int spriteSize, CMPT276.Group12.Mechanics.Game map) {
        super(spriteSize, trap32pxPath);
        this.name = "bearTrap";
        this.map = map;
    }

    /**
     * Override interact function from Interactable class
     * Traps player for 3 seconds by changing player state from ACTIVE->TRAPPED
     * before being released
     */
    @Override
    public void interact(Player player) {
        player.trap();

        if (player.getPages() > 0) {
            player.removePage();
            map.respawnPage();
        }

        // Start moving enemies automatically while player is trapped
        //map.startEnemyMovement();

        // traps player for 3 seconds
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.schedule(() -> {
            player.untrap();
            //map.stopEnemyMovement(); // Stop automatic enemy movement
            System.out.println("Player is now free!");
        }, 3, TimeUnit.SECONDS);

        scheduler.shutdown();
    }
}
