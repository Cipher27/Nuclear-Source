package com.rs.game.minigames.warbands;

import com.rs.game.Animation;
import com.rs.game.Graphics;
/**
 * @makers of hyperion II hc47
 * @author paolo
 *
 */
public class WarbandsConstants {
	/*
	 * array of the minions
	 */
/**
 * saraomdin
 * armadyl
 * zamorak
 * bandos
 */
	public static final int[][] MINIONS = {
		{17066,17067,17068},//saradomin
		{17074,17075,17076},//arma
		{17070,17071,17072},//zamorak
		{17078,17079,17080},//bandos
	},
	//not used atm.
	RARE_REWARDS = {
		{}
	};
	/*
	 * chiefs
	 */
	public static final int[] CHIEFTAINS = {
			17069,//sara
			17077, //arma
			17073,//zamorak
			17081 //bandos
		};
	public static final Animation looting = new Animation(881);
	public static final Animation discharging = new Animation(16596);
	public static final Graphics discharge = new Graphics(3060);
	public static final Graphics explosion = new Graphics(3057);
	/**
	 * To be deleted below
	 */
}