package goodgenerator.main;

import net.minecraft.creativetab.CreativeTabs;

import com.github.bartimaeusnek.bartworks.API.WerkstoffAdderRegistry;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLLoadCompleteEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import goodgenerator.common.CommonProxy;
import goodgenerator.crossmod.thaumcraft.Research;
import goodgenerator.items.MyMaterial;
import goodgenerator.loader.Loaders;
import goodgenerator.loader.NaquadahReworkRecipeLoader;
import goodgenerator.tabs.MyTabs;

@SuppressWarnings("ALL")
@Mod(
        modid = GoodGenerator.MOD_ID,
        version = GoodGenerator.VERSION,
        dependencies = "required-after:IC2; " + "required-after:gregtech; "
                + "required-after:bartworks; "
                + "required-after:tectech; "
                + "required-after:structurelib; "
                + "before:miscutils; "
                + "after:dreamcraft;")
public final class GoodGenerator {

    public static final String MOD_ID = "GRADLETOKEN_MODID";
    public static final String MOD_NAME = "GRADLETOKEN_MODNAME";
    public static final String VERSION = "GRADLETOKEN_VERSION";

    public static final CreativeTabs GG = new MyTabs("Good Generator");

    @SidedProxy(clientSide = "goodgenerator.client.ClientProxy", serverSide = "goodgenerator.common.CommonProxy")
    public static CommonProxy proxy;

    public static SimpleNetworkWrapper CHANNEL = NetworkRegistry.INSTANCE.newSimpleChannel(MOD_ID);

    static {}

    @Mod.Instance(GoodGenerator.MOD_ID)
    public static GoodGenerator instance;

    @Mod.EventHandler
    public static void preInit(FMLPreInitializationEvent event) {
        GG_Config_Loader.run();
        WerkstoffAdderRegistry.addWerkstoffAdder(new MyMaterial());
        // WerkstoffAdderRegistry.addWerkstoffAdder(new IsotopeMaterialLoader());
        Loaders.preInitLoad();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public static void init(FMLInitializationEvent event) {
        Loaders.initLoad();
        proxy.init(event);
    }

    @Mod.EventHandler
    public static void postInit(FMLPostInitializationEvent event) {
        Loaders.postInitLoad();
        crossMod();
        proxy.postInit(event);
    }

    @Mod.EventHandler
    public void onLoadComplete(FMLLoadCompleteEvent event) {
        // NaquadahReworkRecipeLoader.SmallRecipeChange();
        NaquadahReworkRecipeLoader.Remover();
        Loaders.completeLoad();
    }

    public static void crossMod() {
        if (Loader.isModLoaded("Thaumcraft")) {
            Research.addResearch();
        }
    }
}
