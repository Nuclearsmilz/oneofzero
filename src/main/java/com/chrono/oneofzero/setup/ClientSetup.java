package com.chrono.oneofzero.setup;

import com.chrono.oneofzero.client.PowergenScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class ClientSetup {
	public static void init (FMLClientSetupEvent event) {
		event.enqueueWork(() -> {
			MenuScreens.register(Registration.POWERGEN_CONTAINER.get(), PowergenScreen::new);
		});
	}
}
