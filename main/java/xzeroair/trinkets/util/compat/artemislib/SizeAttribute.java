package xzeroair.trinkets.util.compat.artemislib;

import java.util.UUID;

import com.artemis.artemislib.util.attributes.ArtemisLibAttributes;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.Loader;
import xzeroair.trinkets.util.Reference;
import xzeroair.trinkets.util.TrinketsConfig;

public class SizeAttribute {
	private final String id = Reference.MODID + ".";
	private String name;
	protected final UUID uuid = UUID.fromString("02a31a03-4d5b-46ed-947b-322c4f004a75");
	protected final UUID uuidW = UUID.fromString("4ff77810-5a91-44ce-8e93-fc05a8d00def");
	private int operation;
	private EntityLivingBase entity;
	private double height;
	private double width;

	public SizeAttribute(EntityLivingBase entity, double height, double width, int operation) {
		this.entity = entity;
		name = id + "artemislib.size.attribute";
		this.height = height;
		this.width = width;
		this.operation = operation;

	}

	public void addHeightModifier() {
		if (!Loader.isModLoaded("artemislib")) {
			return;
		}
		if (!TrinketsConfig.compat.artemislib) {
			this.removeHeightModifier();
			return;
		}
		final IAttributeInstance AttributeInstance = entity.getAttributeMap().getAttributeInstance(ArtemisLibAttributes.ENTITY_HEIGHT);
		if ((AttributeInstance == null)) {
			return;
		}
		double clampH = MathHelper.clamp(height, -0.75, 1024.0D);
		if (AttributeInstance.getModifier(uuid) != null) {
			if (AttributeInstance.getModifier(uuid).getAmount() != clampH) {
				this.removeHeightModifier();
			}
		}
		if (AttributeInstance.getModifier(uuid) == null) {
			AttributeModifier modifier = new AttributeModifier(uuid, name + ".height", clampH, operation);
			AttributeInstance.applyModifier(modifier);
		}

	}

	public void addWidthModifier() {
		if (!Loader.isModLoaded("artemislib")) {
			return;
		}
		if (!TrinketsConfig.compat.artemislib) {
			this.removeWidthModifier();
			return;
		}
		final IAttributeInstance AttributeInstance = entity.getAttributeMap().getAttributeInstance(ArtemisLibAttributes.ENTITY_WIDTH);
		if ((AttributeInstance == null)) {
			return;
		}
		double clampW = MathHelper.clamp(width, -0.75, 1024.0D);
		if (AttributeInstance.getModifier(uuidW) != null) {
			if (AttributeInstance.getModifier(uuidW).getAmount() != clampW) {
				this.removeWidthModifier();
			}
		}
		if (AttributeInstance.getModifier(uuidW) == null) {
			AttributeModifier modifier = new AttributeModifier(uuidW, name + ".width", clampW, operation);
			AttributeInstance.applyModifier(modifier);
		}

	}

	public void addModifiers() {
		if (height != 0) {
			this.addHeightModifier();
		}
		if (width != 0) {
			this.addWidthModifier();
		}
	}

	public void removeModifiers() {
		this.removeHeightModifier();
		this.removeWidthModifier();
	}

	public void removeHeightModifier() {
		if (!Loader.isModLoaded("artemislib")) {
			return;
		}
		final IAttributeInstance AttributeInstance = entity.getAttributeMap().getAttributeInstance(ArtemisLibAttributes.ENTITY_HEIGHT);
		if ((AttributeInstance == null)) {
			return;
		}
		if (AttributeInstance.getModifier(uuid) != null) {
			AttributeInstance.removeModifier(uuid);
		}
	}

	public void removeWidthModifier() {
		if (!Loader.isModLoaded("artemislib")) {
			return;
		}
		final IAttributeInstance AttributeInstanceW = entity.getAttributeMap().getAttributeInstance(ArtemisLibAttributes.ENTITY_WIDTH);
		if ((AttributeInstanceW == null)) {
			return;
		}
		if (AttributeInstanceW.getModifier(uuidW) != null) {
			AttributeInstanceW.removeModifier(uuidW);
		}
	}
}
