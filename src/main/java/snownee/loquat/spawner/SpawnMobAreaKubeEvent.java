package snownee.loquat.spawner;

import lombok.Setter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import snownee.loquat.AreaEventTypes;
import snownee.loquat.compat.kubejs.AreaSpawnMobWaveTickEventJS;
import snownee.loquat.compat.kubejs.LoquatKubeJSEvents;
import snownee.loquat.core.AreaEvent;
import snownee.loquat.core.area.Area;

import java.util.HashMap;
import java.util.Map;

public class SpawnMobAreaKubeEvent extends AreaEvent {

	public String spawnerId;
	@Setter
	public int waveEndTicks;
	@Setter
	public int waveId;
	@Setter
	public Map<String, Object> customDataMap = new HashMap<>();
	public SpawnMobAreaKubeEvent(Area area, String spawnerId, int waveEndTicks, int waveId) {
		super(area);
		this.spawnerId = spawnerId;
		this.waveEndTicks = waveEndTicks;
		this.waveId = waveId;
	}

	@Override
	public void tick(ServerLevel world) {
			LoquatKubeJSEvents.AREA_SPAWN_MOB_WAVE_TICK.post(new AreaSpawnMobWaveTickEventJS(world, this));
	}

	public int setCurrentWaveTime(int curWaveTicks) {
		this.waveEndTicks = this.ticksExisted + curWaveTicks;
		return this.waveEndTicks;
	}
	public void setFinished() {
		this.isFinished = true;
	}

	@Override
	public AreaEvent.Type<?> getType() {
		return AreaEventTypes.SPAWN_MOBS_KUBE;
	}

	public static class Type extends AreaEvent.Type<SpawnMobAreaKubeEvent> {
		@Override
		public SpawnMobAreaKubeEvent deserialize(Area area, CompoundTag data) {
			String spawnerId = data.getString("SpawnerId");
			int waveEndTicks = data.getInt("WaveEndTicks");
			int waveId = data.getInt("WaveId");
            return new SpawnMobAreaKubeEvent(
					area,
					spawnerId,
					waveEndTicks,
					waveId);
		}

		@Override
		public CompoundTag serialize(CompoundTag data, SpawnMobAreaKubeEvent event) {
			data.putString("SpawnerId", event.spawnerId);
			data.putInt("WaveEndTicks", event.waveEndTicks);
			data.putInt("WaveId", event.waveId);
			return data;
		}
	}
}
