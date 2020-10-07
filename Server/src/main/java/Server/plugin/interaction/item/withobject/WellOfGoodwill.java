package plugin.interaction.item.withobject;

import core.cache.def.impl.ObjectDefinition;
import core.game.interaction.NodeUsageEvent;
import core.game.interaction.OptionHandler;
import core.game.interaction.UseWithHandler;
import core.game.node.Node;
import core.game.node.entity.player.Player;
import core.game.node.item.Item;
import core.plugin.InitializablePlugin;
import core.plugin.Plugin;
import core.plugin.PluginManager;
import plugin.interaction.inter.custom.goodwill.goodwill.WOGInterface;

/**
 * Default header
 *
 * @author Sagacity - http://rune-server.org/members/Sagacity
 * @created 05/10/2020 - 14:24
 * @project 530-rsps
 */
@InitializablePlugin
public class WellOfGoodwill extends OptionHandler {

    @Override
    public Plugin<Object> newInstance(Object arg) throws Throwable {
        PluginManager.definePlugin(new WellOfGoodwillHandler());
        ObjectDefinition.forId(26945).getConfigurations().put("option:check", this);
        return this;
    }

    @Override
    public boolean handle(Player player, Node node, String option) {
        if ("option:check".equals(option)) {
            WOGInterface.open(player);
            return true;
        }
        return true;
    }

    public class WellOfGoodwillHandler extends UseWithHandler {

        /**
         * Constructs a new {@code WellOfGoodwill} {@code Object}
         */
        public WellOfGoodwillHandler() {
            super(995);
        }

        @Override
        public Plugin<Object> newInstance(Object arg) throws Throwable {
            addHandler(26945, OBJECT_TYPE, this);
            ObjectDefinition.forId(26945).getConfigurations().put("option:check", this);
            return this;
        }

        @Override
        public boolean handle(NodeUsageEvent event) {
            final Player player = event.getPlayer();
            final Item coins = event.getUsedItem();

            player.lock(2);
            plugin.interaction.inter.custom.goodwill.goodwill.WellOfGoodwill.give(player);

            return true;
        }
    }
}
