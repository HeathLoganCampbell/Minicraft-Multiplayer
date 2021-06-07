package com.mojang.ld22.entity;

import com.mojang.ld22.gfx.Color;

public class Lantern extends Furniture {
	public Lantern() {
		super("Lantern");
		col = Color.get(-1, 000, 111, 555);
		sprite = 5;
		width = 3;
		height = 2;
	}

	public int getLightRadius() {
		return 8;
	}
}