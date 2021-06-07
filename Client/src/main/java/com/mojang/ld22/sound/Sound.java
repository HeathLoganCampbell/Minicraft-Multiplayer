package com.mojang.ld22.sound;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.applet.Applet;
import java.applet.AudioClip;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Sound {
	public static final Sound playerHurt = new Sound("/playerhurt.wav");
	public static final Sound playerDeath = new Sound("/death.wav");
	public static final Sound monsterHurt = new Sound("/monsterhurt.wav");
	public static final Sound test = new Sound("/test.wav");
	public static final Sound pickup = new Sound("/pickup.wav");
	public static final Sound bossdeath = new Sound("/bossdeath.wav");
	public static final Sound craft = new Sound("/craft.wav");

	public ExecutorService taskService = Executors.newCachedThreadPool(new ThreadFactoryBuilder().setDaemon(true)
                .setNameFormat("Minicraft Sound Task - #%d").build());

	private AudioClip clip;

	private Sound(String name) {
		try {
			clip = Applet.newAudioClip(Sound.class.getResource(name));
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	public void play() {
		taskService.execute(() -> clip.play());
	}
}