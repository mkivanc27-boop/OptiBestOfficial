package com.optibest.config;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import java.util.function.Consumer;
import java.util.ArrayList;
import java.util.List;

public class OptiBestConfigScreen extends Screen {

    private final Screen parent;
    private static final int ITEM_HEIGHT = 24;
    private static final int VISIBLE_AREA_TOP = 35;
    private static final int VISIBLE_AREA_BOTTOM_OFFSET = 35;
    private int scrollOffset = 0;
    private int totalHeight = 0;
    private final List<ToggleEntry> entries = new ArrayList<>();

    private static class ToggleEntry {
        String label;
        boolean[] state;
        Consumer<Boolean> setter;
        ButtonWidget button;

        ToggleEntry(String label, boolean initial, Consumer<Boolean> setter) {
            this.label = label;
            this.state = new boolean[]{initial};
            this.setter = setter;
        }
    }

    public OptiBestConfigScreen(Screen parent) {
        super(Text.literal("OptiBest - Ayarlar"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        entries.clear();

        entries.add(new ToggleEntry("Entity Culling", OptiBestConfig.entityCulling, v -> OptiBestConfig.entityCulling = v));
        entries.add(new ToggleEntry("Fog Kapali", OptiBestConfig.fogDisabled, v -> OptiBestConfig.fogDisabled = v));
        entries.add(new ToggleEntry("Entity Golgeleri Kapali", OptiBestConfig.entityShadowsDisabled, v -> OptiBestConfig.entityShadowsDisabled = v));
        entries.add(new ToggleEntry("Bulutlar Kapali", OptiBestConfig.cloudRenderOff, v -> OptiBestConfig.cloudRenderOff = v));
        entries.add(new ToggleEntry("Gokyuzu Kapali", OptiBestConfig.skyRenderOff, v -> OptiBestConfig.skyRenderOff = v));
        entries.add(new ToggleEntry("Hava Durumu Kapali", OptiBestConfig.weatherRenderOff, v -> OptiBestConfig.weatherRenderOff = v));
        entries.add(new ToggleEntry("Partikul Limiti", OptiBestConfig.particleLimit, v -> OptiBestConfig.particleLimit = v));
        entries.add(new ToggleEntry("Chunk Sinirla", OptiBestConfig.chunkThrottling, v -> OptiBestConfig.chunkThrottling = v));
        entries.add(new ToggleEntry("Hizli Grafik", OptiBestConfig.fastGraphics, v -> OptiBestConfig.fastGraphics = v));
        entries.add(new ToggleEntry("Smooth Lighting Kapali", OptiBestConfig.smoothLightingOff, v -> OptiBestConfig.smoothLightingOff = v));
        entries.add(new ToggleEntry("Entity Tick Opt", OptiBestConfig.entityTickOptimization, v -> OptiBestConfig.entityTickOptimization = v));
        entries.add(new ToggleEntry("Block Entity Culling", OptiBestConfig.blockEntityCulling, v -> OptiBestConfig.blockEntityCulling = v));
        entries.add(new ToggleEntry("Biome Renk Kapali", OptiBestConfig.biomeBlendsOff, v -> OptiBestConfig.biomeBlendsOff = v));
        entries.add(new ToggleEntry("Bellek Opt", OptiBestConfig.memoryOptimization, v -> OptiBestConfig.memoryOptimization = v));
        entries.add(new ToggleEntry("Ses Sinirla", OptiBestConfig.soundThrottle, v -> OptiBestConfig.soundThrottle = v));
        entries.add(new ToggleEntry("HUD Opt", OptiBestConfig.hudOptimization, v -> OptiBestConfig.hudOptimization = v));
        entries.add(new ToggleEntry("Mob AI Throttle", OptiBestConfig.mobAiThrottle, v -> OptiBestConfig.mobAiThrottle = v));
        entries.add(new ToggleEntry("Golge Bypass", OptiBestConfig.shadowBypass, v -> OptiBestConfig.shadowBypass = v));
        entries.add(new ToggleEntry("Player List Opt", OptiBestConfig.playerListOptimization, v -> OptiBestConfig.playerListOptimization = v));
        entries.add(new ToggleEntry("Paket Throttle", OptiBestConfig.packetThrottle, v -> OptiBestConfig.packetThrottle = v));

        totalHeight = entries.size() * ITEM_HEIGHT;

        rebuildButtons();

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Geri"), btn -> {
            assert this.client != null;
            this.client.setScreen(parent);
        }).dimensions(this.width / 2 - 100, this.height - 28, 200, 20).build());
    }

    private void rebuildButtons() {
        int btnWidth = 300;
        int centerX = this.width / 2 - btnWidth / 2;
        int visibleBottom = this.height - VISIBLE_AREA_BOTTOM_OFFSET;

        for (ToggleEntry entry : entries) {
            if (entry.button != null) {
                remove(entry.button);
            }

            int y = VISIBLE_AREA_TOP + entries.indexOf(entry) * ITEM_HEIGHT - scrollOffset;

            if (y + ITEM_HEIGHT < VISIBLE_AREA_TOP || y > visibleBottom) {
                entry.button = null;
                continue;
            }

            final ToggleEntry e = entry;
            ButtonWidget btn = ButtonWidget.builder(
                Text.literal((e.state[0] ? "§a[ACIK] " : "§c[KAPALI] ") + e.label),
                b -> {
                    e.state[0] = !e.state[0];
                    e.setter.accept(e.state[0]);
                    b.setMessage(Text.literal((e.state[0] ? "§a[ACIK] " : "§c[KAPALI] ") + e.label));
                }
            ).dimensions(centerX, y, btnWidth, 20).build();

            entry.button = btn;
            this.addDrawableChild(btn);
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
        int maxScroll = Math.max(0, totalHeight - (this.height - VISIBLE_AREA_TOP - VISIBLE_AREA_BOTTOM_OFFSET));
        scrollOffset = (int) Math.max(0, Math.min(maxScroll, scrollOffset - verticalAmount * 15));
        clearChildren();
        init();
        return true;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);

        context.drawCenteredTextWithShadow(this.textRenderer,
                Text.literal("§6§lOptiBest §r§7FPS Ayarlari"),
                this.width / 2, 15, 0xFFFFFF);

        // Scrollbar çiz
        int visibleHeight = this.height - VISIBLE_AREA_TOP - VISIBLE_AREA_BOTTOM_OFFSET;
        if (totalHeight > visibleHeight) {
            int scrollbarX = this.width / 2 + 155;
            int scrollbarHeight = (int) ((float) visibleHeight / totalHeight * visibleHeight);
            int scrollbarY = VISIBLE_AREA_TOP + (int) ((float) scrollOffset / totalHeight * visibleHeight);
            context.fill(scrollbarX, VISIBLE_AREA_TOP, scrollbarX + 4, this.height - VISIBLE_AREA_BOTTOM_OFFSET, 0x44FFFFFF);
            context.fill(scrollbarX, scrollbarY, scrollbarX + 4, scrollbarY + scrollbarHeight, 0xFFAAAAAA);
        }

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        assert this.client != null;
        this.client.setScreen(parent);
    }
            }
