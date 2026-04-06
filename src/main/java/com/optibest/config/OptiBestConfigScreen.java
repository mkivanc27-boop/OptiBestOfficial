package com.optibest.config;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import java.util.function.Consumer;

public class OptiBestConfigScreen extends Screen {

    private final Screen parent;
    private static final int ITEM_HEIGHT = 24;

    public OptiBestConfigScreen(Screen parent) {
        super(Text.literal("OptiBest - Ayarlar"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        int btnWidth = 300;
        int centerX = this.width / 2 - btnWidth / 2;
        int y = 40;

        y = addToggle(centerX, y, btnWidth, "Entity Culling", OptiBestConfig.entityCulling, v -> OptiBestConfig.entityCulling = v);
        y = addToggle(centerX, y, btnWidth, "Fog Kapali", OptiBestConfig.fogDisabled, v -> OptiBestConfig.fogDisabled = v);
        y = addToggle(centerX, y, btnWidth, "Entity Golgeleri Kapali", OptiBestConfig.entityShadowsDisabled, v -> OptiBestConfig.entityShadowsDisabled = v);
        y = addToggle(centerX, y, btnWidth, "Bulutlar Kapali", OptiBestConfig.cloudRenderOff, v -> OptiBestConfig.cloudRenderOff = v);
        y = addToggle(centerX, y, btnWidth, "Gokyuzu Kapali", OptiBestConfig.skyRenderOff, v -> OptiBestConfig.skyRenderOff = v);
        y = addToggle(centerX, y, btnWidth, "Hava Durumu Kapali", OptiBestConfig.weatherRenderOff, v -> OptiBestConfig.weatherRenderOff = v);
        y = addToggle(centerX, y, btnWidth, "Partikul Limiti", OptiBestConfig.particleLimit, v -> OptiBestConfig.particleLimit = v);
        y = addToggle(centerX, y, btnWidth, "Chunk Sinirla", OptiBestConfig.chunkThrottling, v -> OptiBestConfig.chunkThrottling = v);
        y = addToggle(centerX, y, btnWidth, "Hizli Grafik", OptiBestConfig.fastGraphics, v -> OptiBestConfig.fastGraphics = v);
        y = addToggle(centerX, y, btnWidth, "Smooth Lighting Kapali", OptiBestConfig.smoothLightingOff, v -> OptiBestConfig.smoothLightingOff = v);
        y = addToggle(centerX, y, btnWidth, "Entity Tick Opt", OptiBestConfig.entityTickOptimization, v -> OptiBestConfig.entityTickOptimization = v);
        y = addToggle(centerX, y, btnWidth, "Block Entity Culling", OptiBestConfig.blockEntityCulling, v -> OptiBestConfig.blockEntityCulling = v);
        y = addToggle(centerX, y, btnWidth, "Biome Renk Kapali", OptiBestConfig.biomeBlendsOff, v -> OptiBestConfig.biomeBlendsOff = v);
        y = addToggle(centerX, y, btnWidth, "Bellek Opt", OptiBestConfig.memoryOptimization, v -> OptiBestConfig.memoryOptimization = v);
        y = addToggle(centerX, y, btnWidth, "Ses Sinirla", OptiBestConfig.soundThrottle, v -> OptiBestConfig.soundThrottle = v);
        y = addToggle(centerX, y, btnWidth, "HUD Opt", OptiBestConfig.hudOptimization, v -> OptiBestConfig.hudOptimization = v);
        y = addToggle(centerX, y, btnWidth, "Mob AI Throttle", OptiBestConfig.mobAiThrottle, v -> OptiBestConfig.mobAiThrottle = v);
        y = addToggle(centerX, y, btnWidth, "Golge Bypass", OptiBestConfig.shadowBypass, v -> OptiBestConfig.shadowBypass = v);
        y = addToggle(centerX, y, btnWidth, "Player List Opt", OptiBestConfig.playerListOptimization, v -> OptiBestConfig.playerListOptimization = v);
        y = addToggle(centerX, y, btnWidth, "Paket Throttle", OptiBestConfig.packetThrottle, v -> OptiBestConfig.packetThrottle = v);

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Geri"), btn -> {
            assert this.client != null;
            this.client.setScreen(parent);
        }).dimensions(this.width / 2 - 100, this.height - 28, 200, 20).build());
    }

    private int addToggle(int x, int y, int width, String label, boolean current, Consumer<Boolean> setter) {
        final boolean[] state = {current};
        this.addDrawableChild(ButtonWidget.builder(
            Text.literal((state[0] ? "§a[ACIK] " : "§c[KAPALI] ") + label),
            b -> {
                state[0] = !state[0];
                setter.accept(state[0]);
                b.setMessage(Text.literal((state[0] ? "§a[ACIK] " : "§c[KAPALI] ") + label));
            }
        ).dimensions(x, y, width, 20).build());
        return y + ITEM_HEIGHT;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer,
                Text.literal("§6§lOptiBest §r§7FPS Ayarlari"),
                this.width / 2, 15, 0xFFFFFF);
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        assert this.client != null;
        this.client.setScreen(parent);
    }
                                           }
