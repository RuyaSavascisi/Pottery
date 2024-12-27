package com.supermartijn642.pottery.content;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.client.color.item.ItemTintSources;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.item.BlockModelWrapper;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created 27/12/2024 by SuperMartijn642
 */
public class PotItemModel implements ItemModel.Unbaked {

    public static final MapCodec<PotItemModel> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
        ResourceLocation.CODEC.fieldOf("model").forGetter(model -> model.model)
    ).apply(instance, PotItemModel::new));

    private final ResourceLocation model;

    public PotItemModel(ResourceLocation model){
        this.model = model;
    }

    @Override
    public ItemModel bake(ItemModel.BakingContext context){
        PotBakedModel model = new PotBakedModel(context.bake(this.model));
        return (state, stack, modelResolver, displayContext, level, entity, someRandomId) -> {
            model.stack = stack;
            state.newLayer().setupBlockModel(model, ItemBlockRenderTypes.getRenderType(stack));
        };
    }

    @Override
    public void resolveDependencies(Resolver resolver){
        resolver.resolve(this.model);
    }

    @Override
    public MapCodec<? extends ItemModel.Unbaked> type(){
        return CODEC;
    }
}
