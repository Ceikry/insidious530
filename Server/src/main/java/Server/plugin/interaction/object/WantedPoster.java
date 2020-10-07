package plugin.interaction.object;

import core.cache.def.impl.ObjectDefinition;
import core.game.interaction.OptionHandler;
import core.game.node.Node;
import core.game.node.entity.player.Player;
import core.plugin.InitializablePlugin;
import core.plugin.Plugin;

@InitializablePlugin
public class WantedPoster extends OptionHandler {

	@Override
	public Plugin<Object> newInstance(Object arg) throws Throwable {
		ObjectDefinition.forId(40992).getConfigurations().put("option:look-at", this);
		return this;
	}

	@Override
	public boolean handle(Player player, Node node, String option) {
		//Can do fun stuff related to a player being wanted or something.
		player.getDialogueInterpreter().sendPlainMessage(false, "Looks like a generic wanted poster.");
		return true;
	}

}
