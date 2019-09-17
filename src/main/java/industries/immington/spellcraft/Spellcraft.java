package industries.immington.spellcraft;

import industries.immington.spellcraft.entity.Entities;
import industries.immington.spellcraft.entity.FlungBlockEntity;
import industries.immington.spellcraft.item.ScepterItem;
import industries.immington.spellcraft.spell.Spell;
import industries.immington.spellcraft.spell.SpellDomain;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegistryBuilder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Spellcraft.MOD_ID)
public class Spellcraft
{
    /** Spellcraft mod ID. */
    public static final String MOD_ID = "spellcraft";

    public static class Registries {
    }

    // public static CreativeTabs SPELLCRAFT_CREATIVE_TAB = null; // new SpellcraftCreativeTab();

    /** Mod logger instance */
    static final Logger LOGGER = LogManager.getLogger(Spellcraft.MOD_ID);

    public Spellcraft() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
        LOGGER.info("HELLO FROM PREINIT SPELLCRAFT!!!");
        LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
        LOGGER.info("Stone axe: {}",
                ((SwordItem)Items.STONE_SWORD).getAttributeModifiers(EquipmentSlotType.MAINHAND));
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
    }

    private void enqueueIMC(final InterModEnqueueEvent event) {

    }

    private void processIMC(final InterModProcessEvent event) {
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }

    @SubscribeEvent
    public void onServerStarting(final FMLServerStartingEvent event) {
    }

    /**
     * Contains event handlers for Forge registries.
     */
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistryEvents {
        @SubscribeEvent
        public static void registerItems(final RegistryEvent.Register<Item> event) {
            event.getRegistry().registerAll(
                new ScepterItem(ItemTier.WOOD, 2, -2.2F, SpellDomain.LIFE, new Item.Properties().group(ItemGroup.TOOLS))
                    .setRegistryName("spellcraft:wooden_scepter"),
                new ScepterItem(ItemTier.WOOD, 3, -2F, SpellDomain.UNDEAD, new Item.Properties().group(ItemGroup.TOOLS))
                    .setRegistryName("spellcraft:undead_scepter"),
                new ScepterItem(ItemTier.WOOD, 3, -2.1F, SpellDomain.OCEAN, new Item.Properties().group(ItemGroup.TOOLS))
                    .setRegistryName("spellcraft:ocean_scepter"),
                new ScepterItem(ItemTier.GOLD, 3, -2F, SpellDomain.ILLAGER, new Item.Properties().group(ItemGroup.TOOLS))
                    .setRegistryName("spellcraft:emerald_scepter"),
                /* Redstone */
                new ScepterItem(ItemTier.IRON, 4, -2F, SpellDomain.NETHER, new Item.Properties().group(ItemGroup.TOOLS))
                    .setRegistryName("spellcraft:nether_scepter"),
                new ScepterItem(ItemTier.GOLD, 3, -2.1F, SpellDomain.ENDER, new Item.Properties().group(ItemGroup.TOOLS))
                    .setRegistryName("spellcraft:ender_scepter")
            );
        }

        @SubscribeEvent
        public static void registerEntities(final RegistryEvent.Register<EntityType<?>> event) {
            Entities.FLUNG_BLOCK_ENTITY = EntityType.Builder.create(FlungBlockEntity::new, EntityClassification.MISC)
                    .size(1F, 1F)
                    .setShouldReceiveVelocityUpdates(true)
                    .build("spellcraft:flung_block");
            Entities.FLUNG_BLOCK_ENTITY.setRegistryName("spellcraft:flung_block");
            event.getRegistry().register(Entities.FLUNG_BLOCK_ENTITY);
        }

        @SubscribeEvent
        public static void registerRegistries(final RegistryEvent.NewRegistry event) {
            Spells.registry = new RegistryBuilder<Spell>()
                    .allowModification()
                    .setDefaultKey(new ResourceLocation("spellcraft:empty"))
                    .setType(Spell.class)
                    .setName(new ResourceLocation("spellcraft:spells"))
                    .create();
            SpellDomain.registry = new RegistryBuilder<SpellDomain>()
                    .allowModification()
                    .setDefaultKey(new ResourceLocation("spellcraft:empty"))
                    .setType(SpellDomain.class)
                    .setName(new ResourceLocation("spellcraft:domains"))
                    .create();
        }

        @SubscribeEvent
        public static void registerSpellDomains(final RegistryEvent.Register<SpellDomain> event) {
            event.getRegistry().registerAll(
                    SpellDomain.LIFE,
                    SpellDomain.UNDEAD,
                    SpellDomain.ILLAGER,
                    SpellDomain.REDSTONE,
                    SpellDomain.OCEAN,
                    SpellDomain.NETHER,
                    SpellDomain.ENDER
            );
        }

        @SubscribeEvent
        public static void registerSpells(final RegistryEvent.Register<Spell> event) {
            event.getRegistry().registerAll(
                    Spells.FLING
            );
        }
    }
}
