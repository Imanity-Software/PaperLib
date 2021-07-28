package io.papermc.lib.features.asyncchunks;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.imanity.imanityspigot.chunk.AsyncPriority;

import java.util.concurrent.CompletableFuture;

public class AsyncChunkImanity implements AsyncChunks {
    @Override
    public CompletableFuture<Chunk> getChunkAtAsync(World world, int x, int z, boolean gen, boolean isUrgent) {
        return world.imanity().getChunkAtAsynchronously(x, z, isUrgent ? AsyncPriority.HIGHER : AsyncPriority.NORMAL);
    }
}
