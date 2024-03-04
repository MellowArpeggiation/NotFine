package jss.notfine.mixinplugin;

import com.gtnewhorizon.gtnhmixins.ILateMixinLoader;
import com.gtnewhorizon.gtnhmixins.LateMixin;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.ModContainer;
import cpw.mods.fml.common.versioning.ArtifactVersion;
import cpw.mods.fml.common.versioning.DefaultArtifactVersion;
import cpw.mods.fml.common.versioning.InvalidVersionSpecificationException;
import cpw.mods.fml.common.versioning.VersionRange;
import jss.notfine.NotFine;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@LateMixin
public class NotFineLateMixins implements ILateMixinLoader {

    @Override
    public String getMixinConfig() {
        return "mixins.notfine.late.json";
    }

    @Override
    public List<String> getMixins(Set<String> loadedMods) {
        NotFine.logger.info("Kicking off NotFine late mixins.");

        List<String> mixins = new ArrayList<>();

        if(loadedMods.contains("Thaumcraft")) {
            mixins.add("leaves.thaumcraft.MixinBlockMagicalLeaves");
            mixins.add("faceculling.thaumcraft.MixinBlockWoodenDevice");
            mixins.add("faceculling.thaumcraft.MixinBlockStoneDevice");
            mixins.add("faceculling.thaumcraft.MixinBlockTable");
        }

        if(loadedMods.contains("TwilightForest")) {
            mixins.add("leaves.twilightforest.MixinBlockTFLeaves");
            mixins.add("leaves.twilightforest.MixinBlockTFLeaves3");

            //Non-GTNH Twilight Forest builds will break horribly with this mixin.
            boolean modernBuild = false;
            try {
                ArtifactVersion accepted = new DefaultArtifactVersion("TwilightForest", VersionRange.createFromVersionSpec("[2.3.8.18,)"));
                ModContainer mc = Loader.instance().getIndexedModList().get("TwilightForest");
                if(mc != null) modernBuild = accepted.containsVersion(mc.getProcessedVersion());
            } catch (InvalidVersionSpecificationException ignored) {
            }
            if(modernBuild) {
                mixins.add("leaves.twilightforest.MixinBlockTFMagicLeaves");
            }
        }

        if(loadedMods.contains("witchery")) {
            mixins.add("leaves.witchery.MixinBlockWitchLeaves");
        }

        return mixins;
    }

}
