package dev.pvpsmooth;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

public final class PvPSmoothClient implements ClientModInitializer {
    private static final String MOD_ID = "pvpsmooth";
    private static KeyBinding toggleCameraKey;
    private static KeyBinding toggleHudKey;
    private static boolean hudEnabled = true;

    @Override
    public void onInitializeClient() {
        toggleCameraKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.pvpsmooth.toggle_camera",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_C,
                "category.pvpsmooth"
        ));
        toggleHudKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.pvpsmooth.toggle_hud",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_H,
                "category.pvpsmooth"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleCameraKey.wasPressed()) {
                client.options.smoothCameraEnabled = !client.options.smoothCameraEnabled;
                client.options.write();
                if (client.player != null) {
                    client.player.sendMessage(
                            Text.literal("Smooth camera: " + (client.options.smoothCameraEnabled ? "ON" : "OFF")),
                            true
                    );
                }
            }

            while (toggleHudKey.wasPressed()) {
                hudEnabled = !hudEnabled;
                if (client.player != null) {
                    client.player.sendMessage(Text.literal("Armor HUD: " + (hudEnabled ? "ON" : "OFF")), true);
                }
            }
        });

        HudElementRegistry.addLast(Identifier.of(MOD_ID, "armor_durability"), PvPSmoothClient::renderArmorHud);
    }

    private static void renderArmorHud(DrawContext context, net.minecraft.client.render.RenderTickCounter tickCounter) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (!hudEnabled || client.player == null || client.options.hudHidden) {
            return;
        }

        int x = 8;
        int y = 8;
        int lineHeight = 12;
        String[] labels = {"HELMET", "CHEST", "LEGS", "BOOTS"};
        EquipmentSlot[] slots = {EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

        // Compact translucent-style backing panel. All values are read locally from the player's equipment.
        context.fill(x - 4, y - 4, x + 112, y + 4 + lineHeight * 4, 0x90000000);

        for (int i = 0; i < slots.length; i++) {
            ItemStack stack = client.player.getEquippedStack(slots[i]);
            String value;
            int color = 0xFFAAAAAA;

            if (stack.isEmpty()) {
                value = labels[i] + "  --";
            } else if (!stack.isDamageable()) {
                value = labels[i] + "  N/A";
                color = 0xFFE0E0E0;
            } else {
                int max = stack.getMaxDamage();
                int remaining = Math.max(0, max - stack.getDamage());
                int percent = max <= 0 ? 100 : (remaining * 100 / max);
                value = labels[i] + "  " + percent + "% (" + remaining + ")";
                color = percent <= 20 ? 0xFFFF5555 : (percent <= 50 ? 0xFFFFCC55 : 0xFF66FF88);
            }

            context.drawText(client.textRenderer, value, x, y + i * lineHeight, color, true);
        }
    }
}
