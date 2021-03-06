package xzeroair.trinkets.client.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.RenderTickEvent;
import xzeroair.trinkets.capabilities.Capabilities;
import xzeroair.trinkets.capabilities.race.EntityProperties;
import xzeroair.trinkets.client.playerCamera.PlayerRaceCameraRenderer;
import xzeroair.trinkets.init.EntityRaces;
import xzeroair.trinkets.util.TrinketsConfig;

public class ChangeViewRenderEvent {

	private final Minecraft mc = Minecraft.getMinecraft();
	private EntityRenderer renderer, prevRenderer;

	@SubscribeEvent
	public void RenderTick(RenderTickEvent event) {
		if (TrinketsConfig.CLIENT.entityRenderer) {
			EntityPlayer player = mc.player;
			if (player != null) {
				EntityProperties prop = Capabilities.getEntityRace(player);
				boolean flag = (prop != null) && !prop.getCurrentRace().equals(EntityRaces.none) && !prop.isNormalHeight();
				if (flag) {
					if (renderer == null) {
						renderer = new PlayerRaceCameraRenderer(mc, mc.getResourceManager());
						//						renderer = new PlayerRaceCameraRendererOptifine(mc, mc.getResourceManager());
					}
					if (mc.entityRenderer != renderer) {
						// be sure to store the previous renderer
						prevRenderer = mc.entityRenderer;
						mc.entityRenderer = renderer;
					}
				} else if ((prevRenderer != null) && (mc.entityRenderer != prevRenderer)) {
					// reset the renderer
					mc.entityRenderer = prevRenderer;
					renderer = null;
					prevRenderer = null;
				}
			}
		}
	}

}
