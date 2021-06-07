package com.mojang.ld22.entity;

import com.mojang.ld22.gfx.Screen;
import com.mojang.ld22.item.Item;
import com.mojang.ld22.level.Level;
import com.mojang.ld22.level.tile.Tile;

import java.util.List;
import java.util.Random;

public class Entity {
	protected static final Random RANDOM = new Random();
	public int x, y;
	public int width = 6;
	public int height = 6;
	public boolean removed = false;
	public Level level;

	public void render(Screen screen)
	{

	}

	public void tick()
	{

	}

	public void remove() {
		removed = true;
	}

	public final void init(Level level) {
		this.level = level;
	}

	public boolean intersects(int x0, int y0, int x1, int y1) {
		return !(x + width < x0 || y + height < y0 || x - width > x1 || y - height > y1);
	}

	public boolean blocks(Entity e) {
		return false;
	}

	public void hurt(Mob mob, int dmg, int attackDir)
	{
	}

	public void hurt(Tile tile, int x, int y, int dmg)
	{
	}

	public boolean move(int xa, int ya)
	{
		if (xa != 0 || ya != 0)
		{
			boolean stopped = true;
			if (xa != 0 && move2(xa, 0)) stopped = false;
			if (ya != 0 && move2(0, ya)) stopped = false;
			if (!stopped)
			{
				int xt = x >> 4;
				int yt = y >> 4;
				level.getTile(xt, yt).steppedOn(level, xt, yt, this);
			}
			return !stopped;
		}
		return true;
	}

	protected boolean move2(int xa, int ya)
	{
		if (xa != 0 && ya != 0) throw new IllegalArgumentException("Move2 can only move along one axis at a time!");

		int xto0 = ((x) - width) >> 4;
		int yto0 = ((y) - height) >> 4;
		int xto1 = ((x) + width) >> 4;
		int yto1 = ((y) + height) >> 4;

		int xt0 = ((x + xa) - width) >> 4;
		int yt0 = ((y + ya) - height) >> 4;
		int xt1 = ((x + xa) + width) >> 4;
		int yt1 = ((y + ya) + height) >> 4;
		boolean blocked = false;
		for (int yt = yt0; yt <= yt1; yt++)
		{
			for (int xt = xt0; xt <= xt1; xt++)
			{
				if (xt >= xto0 && xt <= xto1 && yt >= yto0 && yt <= yto1) continue;
				Tile tile = level.getTile(xt, yt);

				tile.bumpedInto(level, xt, yt, this);
				if (!tile.mayPass(level, xt, yt, this))
				{
					blocked = true;
					return false;
				}
			}
		}

		if (blocked) return false;

		List<Entity> wasInside = level.getEntities(x - width, y - height, x + width, y + height);
		List<Entity> isInside = level.getEntities(x + xa - width, y + ya - height, x + xa + width, y + ya + height);
		for (int i = 0; i < isInside.size(); i++)
		{
			Entity e = isInside.get(i);
			if (e == this) continue;

			e.touchedBy(this);
		}

		isInside.removeAll(wasInside);

		for (int i = 0; i < isInside.size(); i++)
		{
			Entity e = isInside.get(i);
			if (e == this) continue;

			if (e.blocks(this))
			{
				return false;
			}
		}

		x += xa;
		y += ya;
		return true;
	}

	protected void touchedBy(Entity entity)
	{
	}

	public boolean isBlockableBy(Mob mob)
	{
		return true;
	}

	public void touchItem(ItemEntity itemEntity)
	{
	}

	public boolean canSwim()
	{
		return false;
	}

	public boolean interact(Player player, Item item, int attackDir)
	{
		return item.interact(player, this, attackDir);
	}

	public boolean use(Player player, int attackDir)
	{
		return false;
	}

	public int getLightRadius()
	{
		return 0;
	}
}