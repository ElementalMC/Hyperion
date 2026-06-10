/*
 *   Copyright 2016, 2017, 2020 Moros <https://github.com/PrimordialMoros>
 *
 * 	This file is part of Hyperion.
 *
 *    Hyperion is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU Affero General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    Hyperion is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU Affero General Public License for more details.
 *
 *    You should have received a copy of the GNU Affero General Public License
 *    along with Hyperion.  If not, see <https://www.gnu.org/licenses/>.
 */

package me.moros.hyperion.util;

import org.bukkit.Location;
import org.bukkit.Particle;

/**
 * Lightweight, behaviour-preserving replacement for ProjectKorra's
 * {@code com.projectkorra.projectkorra.util.ParticleEffect}, which was removed
 * in ProjectKorra 1.13.0 in favour of Bukkit's native {@link Particle}.
 *
 * <p>Each constant maps to the {@link Particle} that renders the same underlying
 * {@code minecraft:} particle as the original ParticleEffect constant did. The
 * {@code display(...)} overloads mirror the originals exactly, including the
 * data-type guard, so existing particle calls keep their previous behaviour and
 * still pass data ({@code BlockData}, {@code ItemStack}, {@code Color}, ...) only
 * to particles that accept it.
 */
public enum ParticleEffect {
	BLOCK_CRACK(Particle.BLOCK),
	BLOCK_DUST(Particle.BLOCK),
	CLOUD(Particle.CLOUD),
	CRIT(Particle.CRIT),
	EXPLOSION_HUGE(Particle.EXPLOSION_EMITTER),
	EXPLOSION_NORMAL(Particle.POOF),
	FIREWORKS_SPARK(Particle.FIREWORK),
	FLAME(Particle.FLAME),
	LAVA(Particle.LAVA),
	SMOKE_LARGE(Particle.LARGE_SMOKE),
	SMOKE_NORMAL(Particle.SMOKE),
	SNOW_SHOVEL(Particle.ITEM_SNOWBALL),
	SPELL_MOB(Particle.ENTITY_EFFECT);

	private final Particle particle;
	private final Class<?> dataClass;

	ParticleEffect(Particle particle) {
		this.particle = particle;
		this.dataClass = particle.getDataType();
	}

	public Particle getParticle() {
		return particle;
	}

	public void display(Location loc, int amount) {
		display(loc, amount, 0, 0, 0);
	}

	public void display(Location loc, int amount, double offsetX, double offsetY, double offsetZ) {
		display(loc, amount, offsetX, offsetY, offsetZ, 0);
	}

	public void display(Location loc, int amount, double offsetX, double offsetY, double offsetZ, double extra) {
		loc.getWorld().spawnParticle(particle, loc, amount, offsetX, offsetY, offsetZ, extra, null, true);
	}

	public void display(Location loc, int amount, double offsetX, double offsetY, double offsetZ, Object data) {
		display(loc, amount, offsetX, offsetY, offsetZ, 0, data);
	}

	public void display(Location loc, int amount, double offsetX, double offsetY, double offsetZ, double extra, Object data) {
		if (dataClass.isAssignableFrom(Void.class) || data == null || !dataClass.isAssignableFrom(data.getClass())) {
			display(loc, amount, offsetX, offsetY, offsetZ, extra);
		} else {
			loc.getWorld().spawnParticle(particle, loc, amount, offsetX, offsetY, offsetZ, extra, data, true);
		}
	}
}
