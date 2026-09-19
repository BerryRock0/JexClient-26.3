package me.dustin.jex.feature.mod.impl.misc;

import me.dustin.events.core.EventListener;
import me.dustin.events.core.annotate.EventPointer;
import me.dustin.jex.JexClient;
import me.dustin.jex.event.filters.PlayerPacketsFilter;
import me.dustin.jex.event.player.EventPlayerPackets;
import me.dustin.jex.feature.mod.core.Category;
import me.dustin.jex.feature.mod.core.Feature;
import me.dustin.jex.feature.property.Property;
import me.dustin.jex.helper.misc.StopWatch;
import me.dustin.jex.helper.misc.Wrapper;
import me.dustin.jex.helper.network.NetworkHelper;
import me.dustin.jex.helper.player.InventoryHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.network.protocol.game.*;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.network.chat.Component;
import net.minecraft.core.registries.BuiltInRegistries;
import java.util.Random;

public class CreativeDrop extends Feature {

    public final Property<Long> delayProperty = new Property.PropertyBuilder<Long>(this.getClass())
            .name("Drop Delay (MS)")
            .value(0L)
            .max(1000)
            .inc(10)
            .build();
    public final Property<Integer> speedProperty = new Property.PropertyBuilder<Integer>(this.getClass())
            .name("Speed")
            .description("The speed that you drop items at.")
            .value(1)
            .min(1)
            .max(10)
            .build();
    public final Property<Boolean> nameProperty = new Property.PropertyBuilder<Boolean>(this.getClass())
            .name("Name")
            .description("Whether or not to name the item.")
            .value(true)
            .build();
    public final Property<Boolean> enchantProperty = new Property.PropertyBuilder<Boolean>(this.getClass())
            .name("Enchant")
            .description("Whether or not to enchant the item.")
            .value(true)
            .build();

    private int slot = 1;
    private final StopWatch stopWatch = new StopWatch();

    public CreativeDrop() {
        super(Category.MISC, "Drop all items from your inventory in creative.");
    }

    @EventPointer
    private final EventListener<EventPlayerPackets> eventPlayerPacketsEventListener = new EventListener<>(event -> {
        Random random = new Random();
        String[] names = new String[]{JexClient.INSTANCE.getBaseUrl(), "Download Jex Client to do this", "Nice FPS", "Oh look a shiny item", "Copper pants", "How do I stop dropping items?", "Can you hear me?", "Please help I am stuck in this item"};
        if (stopWatch.hasPassed(delayProperty.value()) && Wrapper.INSTANCE.getLocalPlayer().isCreative()) {
            for (int i = 0; i < speedProperty.value(); i++) {
                ItemStack itemStack = new ItemStack(Item.byRawId(slot));
                if (itemStack.getItem() != null && itemStack.getItem() != Items.AIR) {
                    String name = "§" + (slot % 9) + names[(int) (random.nextFloat() * (names.length))];
                    if (this.nameProperty.value())
                        itemStack.setCustomName(Component.literal(name));
                    if (enchantProperty.value())
                        BuiltInRegistries.ENCHANTMENT.forEach(enchantment -> {
                            itemStack.addEnchantment(enchantment, 127);
                        });
                    NetworkHelper.INSTANCE.sendPacket(new CreativeInventoryActionC2SPacket(36, itemStack));
                    InventoryHelper.INSTANCE.windowClick(Wrapper.INSTANCE.getLocalPlayer().currentScreenHandler, 36, SlotActionType.THROW, 0);
                    slot += 1;
                } else
                    slot = 1;
            }
            stopWatch.reset();
        }
    }, new PlayerPacketsFilter(EventPlayerPackets.Mode.PRE));
}
