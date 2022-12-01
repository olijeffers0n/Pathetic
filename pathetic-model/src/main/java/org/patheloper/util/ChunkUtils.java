package org.patheloper.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.bukkit.ChunkSnapshot;
import org.bukkit.Material;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@UtilityClass
public class ChunkUtils {

    private static Method materialMethod;

    private static Method blockTypeMethod;

    static {
        if (BukkitVersionUtil.isUnder(13)) {
            try {
                materialMethod = Material.class.getDeclaredMethod("getMaterial", int.class);
                blockTypeMethod = ChunkSnapshot.class.getDeclaredMethod("getBlockTypeId", int.class, int.class, int.class);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    public long getChunkKey(final int x, final int z) {
        return x & 0xFFFFFFFFL | (z & 0xFFFFFFFFL) << 32;
    }

    /**
     * Get the block type from a chunk snapshot at the given coordinates
     */
    @SneakyThrows
    public Material getMaterial(ChunkSnapshot snapshot, int x, int y, int z) {
        if (BukkitVersionUtil.isUnder(13)) {
            if (materialMethod == null || blockTypeMethod == null) throw new IllegalStateException("Reflection Failed");
            try {
                return (Material) materialMethod.invoke(null, blockTypeMethod.invoke(snapshot, x, y, z));
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
                return null;
            }
        } else {
            return snapshot.getBlockType(x, y, z);
        }
    }

}
