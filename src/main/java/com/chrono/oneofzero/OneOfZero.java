package com.chrono.oneofzero;

import com.chrono.oneofzero.setup.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.*;
import net.minecraftforge.fml.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(OneOfZero.MODID)
public class OneOfZero {
	
	private static final Logger LOGGER = LogManager.getLogger();
	public static final String MODID = "oneofzero";
	
	public OneOfZero () {
		Registration.init();
		
		IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
		modBus.addListener(ModSetup::init);
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> modBus.addListener(ClientSetup::init));
	}
}
