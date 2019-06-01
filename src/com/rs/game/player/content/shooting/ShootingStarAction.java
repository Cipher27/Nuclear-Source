package com.rs.game.player.content.shooting;

import java.util.ArrayList;
import java.util.List;

import com.rs.game.Animation;
import com.rs.game.Graphics;
import com.rs.game.World;
import com.rs.game.WorldObject;
import com.rs.game.WorldTile;
import com.rs.game.item.Item;
import com.rs.game.player.Player;
import com.rs.game.player.Skills;
import com.rs.game.player.actions.Action;
import com.rs.utils.Utils;
 
	/**
	 * 
	 * @author paolo
	 *
	 */
public class ShootingStarAction extends Action {

	private int ticks;
	private int skillId;
	
	public ShootingStarAction(int ticks, int skillId) {
		this.ticks = ticks;
		this.skillId = skillId;
	}

	@Override
	public boolean process(Player player) {
		
		if (player.getSkills().getLevel(World.shootingStar.getSkillId()) < World.shootingStar.getReqLevel()) {
			player.getDialogueManager().startDialogue("SimpleMessage","You need a "+World.shootingStar.getReqLevel()+" level of "+ Skills.SKILL_NAME[World.shootingStar.getSkillId()]+ " to create this.");
			return false;
		}
		return true;
	}

	@Override
	public int processWithDelay(Player player) {
		ticks--;
		player.setNextAnimation(new Animation(17310));
		player.setNextGraphics(new Graphics(3304));
		player.getInventory().addItem(new Item(13727));
		player.getSkills().addXp(World.shootingStar.getSkillId(), getExp());
		World.shootingStar.mine();
		if (ticks > 0) {
			return 3;
		}
		return -1;
		
	}
	/**
	 * todo based on worlds event.
	 * @param bar
	 * @return
	 */
	public double getExp(){
		
		return 45*World.shootingStar.getStage(); 
	}
	@Override
	public boolean start(Player player) {
		if (World.shootingStar == null) {
			return false;
		}
		if (player.getSkills().getLevel(World.shootingStar.getSkillId()) < World.shootingStar.getReqLevel()) {
			player.getDialogueManager().startDialogue("SimpleMessage","You need a "+World.shootingStar.getReqLevel()+" level of "+ Skills.SKILL_NAME[World.shootingStar.getSkillId()]+ " to create this.");
			return false;
		}
		return true;
	}

	@Override
	public void stop(Player player) {
		this.setActionDelay(player, 3);
	}

	
}
