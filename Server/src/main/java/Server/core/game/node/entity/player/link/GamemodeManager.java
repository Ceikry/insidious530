package core.game.node.entity.player.link;

import core.game.node.entity.player.Player;
import core.game.node.entity.player.info.login.SavingModule;
import core.game.system.SystemLogger;
import plugin.interaction.inter.custom.gamemodes.Modes;

import java.nio.ByteBuffer;

/**
 * Manages the game mode of an account.
 * @author Sagacity
 *
 */
public class GamemodeManager implements SavingModule {

    /**
     * The player instance.
     */
    private final Player player;

    /**
     * The game mode.
     */
    private Modes mode = Modes.Easier;

    /**
     * Constructs a new {@code GamemodeManager} {@code Object}
     * @param player the player.
     */
    public GamemodeManager(Player player) {
        this.player = player;
    }

    @Override
    public void save(ByteBuffer buffer) {
            buffer.put((byte) 1);
            buffer.put((byte) mode.ordinal());
        buffer.put((byte) 0);
    }

    @Override
    public void parse(ByteBuffer buffer) {
        int opcode;
        while ((opcode = buffer.get()) != 0) {
            switch (opcode) {
                case 1:
                    mode = Modes.values()[buffer.get()];
                    break;
                case 2: //Indicates permanent.
                    break;
            }
        }
    }

    /**
     * Gets the player.
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the mode.
     * @return the mode
     */
    public Modes getMode() {
        SystemLogger.log(mode.name().replace("_", " "));
        return mode;
    }

    /**
     * Gets the gamemode name.
     * @return the gamemode name
     */
    public String getModeName() {
        return mode.name().replace("_", " ");
    }

    /**
     * Sets the mode.
     * @param mode the mode to set.
     */
    public void setMode(Modes mode) {
        this.mode = mode;
    }
}
