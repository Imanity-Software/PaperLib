package io.papermc.lib.environments;

import io.papermc.lib.features.asyncchunks.AsyncChunkImanity;
import io.papermc.lib.features.asyncteleport.AsyncTeleportPaper;
import io.papermc.lib.features.bedspawnlocation.BedSpawnLocationPaper;

public class ImanityEnvironment extends PaperEnvironment {

    public ImanityEnvironment() {
        super();
        this.asyncChunksHandler = new AsyncChunkImanity();
        this.asyncTeleportHandler = new AsyncTeleportPaper();
        this.bedSpawnLocationHandler = new BedSpawnLocationPaper();
    }

    @Override
    public boolean isImanity() {
        return true;
    }
}
