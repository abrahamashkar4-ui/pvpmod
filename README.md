# PvP Smooth — Minecraft 1.21.11 / Fabric

A small, client-side mod with:
- **C**: toggles Minecraft's built-in smooth camera option.
- **H**: toggles the armor durability HUD.
- Shows helmet, chestplate, leggings, and boots durability as percentage plus remaining durability.
- Durability text is green above 50%, yellow at 21–50%, and red at 20% or below.
- The HUD is local-only; it does not send packets or change server gameplay.

## Requirements
- Minecraft Java Edition **1.21.11**
- Fabric Loader **0.18.1 or newer**
- Fabric API **0.141.6+1.21.11** (or a compatible 1.21.11 release)
- Java 21 for building

## Build the JAR
1. Install a Java 21 JDK.
2. Download a Gradle 9.x-compatible distribution or open this folder in IntelliJ IDEA and let Gradle sync.
3. From this project folder run:
   - Windows: `gradle build`
   - macOS/Linux: `gradle build`
4. The mod JAR should be created in `build/libs/`, named similar to `pvpsmooth-1.0.0.jar` (use the remapped JAR, not the `-sources.jar`).

This source archive does not include a prebuilt JAR or Gradle wrapper. Gradle needs internet access the first time so it can download Minecraft, mappings, Loom, and Fabric dependencies.

## Install with the Modrinth Launcher
1. Open Modrinth Launcher and select your **Minecraft 1.21.11 Fabric** profile.
2. Click the profile's folder/open-folder option.
3. Open or create the `mods` folder.
4. Put `pvpsmooth-1.0.0.jar` from `build/libs/` into that `mods` folder.
5. Make sure Fabric API is also installed in that same profile.
6. Launch the profile. In **Options → Controls → Key Binds**, find the **PvP Smooth** category to change the C and H bindings.

## Compatibility notes
- This is a Fabric mod for Minecraft **1.21.11** only; it is not a Forge/NeoForge mod.
- It is client-side and intended for normal Java launchers, including Modrinth Launcher.
- Smooth camera is Minecraft's built-in camera smoothing; it can feel less responsive during fast PvP movement. Press C to switch it off.
- This mod does not promise FPS gains. The HUD is lightweight, but it is not a replacement for Sodium or other performance mods.
- If a server has rules about client mods, check those rules first. The HUD only reads your own armor durability locally.

## Troubleshooting
- **Mod does not appear:** check that the JAR is in the `mods` folder for the exact 1.21.11 Fabric profile, and that Fabric API is installed.
- **Game says wrong version:** make sure the profile is Minecraft 1.21.11 and the mod is built with Java 21.
- **Build fails:** check Java with `java -version`; it should report version 21. Then retry after syncing Gradle dependencies.
