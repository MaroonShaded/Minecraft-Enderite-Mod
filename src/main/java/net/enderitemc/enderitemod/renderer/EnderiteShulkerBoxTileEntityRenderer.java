package net.enderitemc.enderitemod.renderer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.enderitemc.enderitemod.tileEntity.EnderiteShulkerBoxTileEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.ShulkerModel;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EnderiteShulkerBoxTileEntityRenderer extends TileEntityRenderer<EnderiteShulkerBoxTileEntity> {
    private final ShulkerModel<?> model;

    private static final ResourceLocation TEXTURE = new ResourceLocation(
            "textures/entity/shulker/enderite_shulker.png");
    private static final RenderType ENDERITE_SHULKER;

    public EnderiteShulkerBoxTileEntityRenderer(TileEntityRendererDispatcher p_i226013_2_) {
        super(p_i226013_2_);
        this.model = new ShulkerModel<>();
    }

    public void render(EnderiteShulkerBoxTileEntity tileEntityIn, float partialTicks, MatrixStack matrixStackIn,
            IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Direction direction = Direction.UP;
        if (tileEntityIn.hasLevel()) {
            BlockState blockstate = tileEntityIn.getLevel().getBlockState(tileEntityIn.getBlockPos());
            if (blockstate.getBlock() instanceof ShulkerBoxBlock) {
                direction = blockstate.getValue(ShulkerBoxBlock.FACING);
            }
        }

        matrixStackIn.pushPose();
        matrixStackIn.translate(0.5D, 0.5D, 0.5D);
        float f = 0.9995F;
        matrixStackIn.scale(0.9995F, 0.9995F, 0.9995F);
        matrixStackIn.mulPose(direction.getRotation());
        matrixStackIn.scale(1.0F, -1.0F, -1.0F);
        matrixStackIn.translate(0.0D, -1.0D, 0.0D);
        IVertexBuilder ivertexbuilder = bufferIn.getBuffer(ENDERITE_SHULKER);
        this.model.getBase().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn);
        matrixStackIn.translate(0.0D, (double) (-tileEntityIn.getProgress(partialTicks) * 0.5F), 0.0D);
        matrixStackIn.mulPose(Vector3f.YP.rotationDegrees(270.0F * tileEntityIn.getProgress(partialTicks)));
        this.model.getLid().render(matrixStackIn, ivertexbuilder, combinedLightIn, combinedOverlayIn);
        matrixStackIn.popPose();
    }

    static {
        ENDERITE_SHULKER = RenderType.entityCutoutNoCull(TEXTURE);
    }

}