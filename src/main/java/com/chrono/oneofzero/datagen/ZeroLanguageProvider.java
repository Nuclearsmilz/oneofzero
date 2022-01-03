package com.chrono.oneofzero.datagen;

import com.chrono.oneofzero.OneOfZero;
import com.chrono.oneofzero.setup.Registration;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

import static com.chrono.oneofzero.setup.ModSetup.TAB_NAME;

public class ZeroLanguageProvider extends LanguageProvider {
	
	public ZeroLanguageProvider(DataGenerator generator,  String locale) { super(generator, OneOfZero.MODID, locale); }
	
	@Override
	protected void addTranslations () {
		add("itemGroup." + TAB_NAME , "OneOfZero");
		
		add(Registration.ZERO_ORE_OW.get(), "Zero Ore");
		add(Registration.ZERO_ORE_NETHER.get(), "End Zero Ore");
		add(Registration.ZERO_ORE_END.get(), "Nether Zero Ore");
		add(Registration.ZERO_ORE_DS.get(), "Deepslate Zero Ore");
	}
}
