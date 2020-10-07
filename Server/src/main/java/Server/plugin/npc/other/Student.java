package plugin.npc.other;

import core.cache.def.impl.NPCDefinition;
import core.game.interaction.OptionHandler;
import core.game.node.Node;
import core.game.node.entity.player.Player;
import core.plugin.InitializablePlugin;
import core.plugin.Plugin;

/**
 * Handles interactions with exam center students, to address #106
 * @author ceik
 */
@InitializablePlugin
public class Student extends OptionHandler {
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        NPCDefinition.forId(7153).getConfigurations().put("option:talk-to",this);
        NPCDefinition.forId(7154).getConfigurations().put("option:talk-to",this);
        NPCDefinition.forId(7155).getConfigurations().put("option:talk-to",this);
        NPCDefinition.forId(7156).getConfigurations().put("option:talk-to",this);
        NPCDefinition.forId(7157).getConfigurations().put("option:talk-to",this);
        return this;
    }
    public final boolean handle(Player player, Node node, String options){
        if(options.equals("talk-to")){
            player.getPacketDispatch().sendMessage("This student is trying to focus on their work.");
        }
        return true;
    }
}
