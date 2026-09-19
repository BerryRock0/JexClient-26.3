package bedrockminer.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.world.item.Items;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;

//import java.util.ArrayList;

//import static net.minecraft.block.Block.sideCoversSmallSquare;

public class BlockBreaker {
    public static void breakBlock(ClientWorld world, BlockPos pos) {
        InventoryManager.switchToItem(Items.DIAMOND_PICKAXE);
        MinecraftClient.getInstance().interactionManager.attackBlock(pos, Direction.UP);
    }


}