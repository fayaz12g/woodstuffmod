package one.fayaz.woodstuff;

import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

@Mod(WoodStuffMod.MODID)
public class WoodStuffMod {
    public static final String MODID = "woodstuff";
    private static final Logger LOGGER = LogUtils.getLogger();

    // Create Deferred Registers for Items and CreativeModeTabs
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    // Register the items from ItemRegistry
    public static final ItemRegistry ItemRegistry = new ItemRegistry();

    // Create CreativeModeTabs for each tool type
    public static final RegistryObject<CreativeModeTab> SWORDS_TAB = CREATIVE_MODE_TABS.register("swords_tab", () -> CreativeModeTab.builder()
            .withTabsAfter(CreativeModeTabs.SEARCH)
            .icon(() -> ItemRegistry.GENERATED_ITEMS.get("oak_sword_with_oak_stick").get().getDefaultInstance())
            .title(Component.translatable("itemGroup.woodstuff.swords"))
            .displayItems((parameters, output) -> addItemsToTab(output, "sword"))
            .build());

    public static final RegistryObject<CreativeModeTab> PICKAXES_TAB = CREATIVE_MODE_TABS.register("pickaxes_tab", () -> CreativeModeTab.builder()
            .withTabsAfter(CreativeModeTabs.SEARCH)
            .icon(() -> ItemRegistry.GENERATED_ITEMS.get("oak_pickaxe_with_oak_stick").get().getDefaultInstance())
            .title(Component.translatable("itemGroup.woodstuff.pickaxes"))
            .displayItems((parameters, output) -> addItemsToTab(output, "pickaxe"))
            .build());

    public static final RegistryObject<CreativeModeTab> AXES_TAB = CREATIVE_MODE_TABS.register("axes_tab", () -> CreativeModeTab.builder()
            .withTabsAfter(CreativeModeTabs.SEARCH)
            .icon(() -> ItemRegistry.GENERATED_ITEMS.get("oak_axe_with_oak_stick").get().getDefaultInstance())
            .title(Component.translatable("itemGroup.woodstuff.axes"))
            .displayItems((parameters, output) -> addItemsToTab(output, "axe"))
            .build());

    public static final RegistryObject<CreativeModeTab> SHOVELS_TAB = CREATIVE_MODE_TABS.register("shovels_tab", () -> CreativeModeTab.builder()
            .withTabsAfter(CreativeModeTabs.SEARCH)
            .icon(() -> ItemRegistry.GENERATED_ITEMS.get("oak_shovel_with_oak_stick").get().getDefaultInstance())
            .title(Component.translatable("itemGroup.woodstuff.shovels"))
            .displayItems((parameters, output) -> addItemsToTab(output, "shovel"))
            .build());

    public static final RegistryObject<CreativeModeTab> HOES_TAB = CREATIVE_MODE_TABS.register("hoes_tab", () -> CreativeModeTab.builder()
            .withTabsAfter(CreativeModeTabs.SEARCH)
            .icon(() -> ItemRegistry.GENERATED_ITEMS.get("oak_hoe_with_oak_stick").get().getDefaultInstance())
            .title(Component.translatable("itemGroup.woodstuff.hoes"))
            .displayItems((parameters, output) -> addItemsToTab(output, "hoe"))
            .build());

    public WoodStuffMod(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register the Deferred Register to the mod event bus
        ItemRegistry.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);

        // Register ourselves for server and other game events
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");
    }

    private static void addItemsToTab(CreativeModeTab.Output output, String toolType) {
        List<RegistryObject<Item>> sortedItems = ItemRegistry.GENERATED_ITEMS.values().stream()
                .filter(itemRegistryObject -> itemRegistryObject.getId().getPath().contains(toolType))
                .sorted((o1, o2) -> {
                    String name1 = o1.getId().getPath();
                    String name2 = o2.getId().getPath();
                    return name1.compareTo(name2); // Sort by item name
                })
                .collect(Collectors.toList());

        for (RegistryObject<Item> item : sortedItems) {
            output.accept(item.get());
        }
    }
}
