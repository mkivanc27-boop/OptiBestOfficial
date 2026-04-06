package com.optibest;

import com.optibest.config.OptiBestConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.option.CloudRenderMode;
import net.minecraft.client.option.GraphicsMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OptiBestClient implements ClientModInitializer {

    public static final Logger LOGGER = LoggerFactory.getLogger("optibest");

    @Override
    public void onInitializeClient() {
        LOGGER.info("[OptiBest] Yuklendi!");

        ClientTickEvents.START_CLIENT_TICK.register(client -> {
            if (client.options == null) return;

            if (OptiBestConfig.unlimitedFps)
                client.options.getMaxFps().setValue(260);

            client.options.getViewDistance().setValue(OptiBestConfig.targetViewDistance);

            if (OptiBestConfig.cloudRenderOff)
                client.options.getCloudRenderMode().setValue(CloudRenderMode.OFF);

            if (OptiBestConfig.entityShadowsDisabled)
                client.options.getEntityShadows().setValue(false);

            if (OptiBestConfig.fastGraphics)
                client.options.getGraphicsMode().setValue(GraphicsMode.FAST);

            if (OptiBestConfig.smoothLightingOff)
                client.options.getAo().setValue(false);

            client.options.getEntityDistanceScaling().setValue(0.5);
            client.options.getMipmapLevels().setValue(0);
        });
    }
}
