package snownee.loquat.compat.kubejs;

import dev.latvian.mods.kubejs.level.LevelEventJS;
import lombok.Getter;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import snownee.loquat.core.AreaManager;
import snownee.loquat.spawner.SpawnMobAreaKubeEvent;

public class AreaSpawnMobWaveTickEventJS extends LevelEventJS {

	@Getter
	public final SpawnMobAreaKubeEvent context;
	public final Level level;


	public AreaSpawnMobWaveTickEventJS(Level level, SpawnMobAreaKubeEvent context) {
		this.level = level;
		this.context = context;
	}

	public AreaManager getAreaManager() {
		if (getLevel() instanceof ServerLevel serverLevel) {
			return AreaManager.of(serverLevel);
		}
		return null;
	}

	public boolean removeArea() {
		return getAreaManager().remove(context.getArea().getUuid());
	}

	@Override
	public Level getLevel() {
		return level;
	}
}
