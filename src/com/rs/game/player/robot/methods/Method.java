package com.rs.game.player.robot.methods;

import com.rs.game.player.robot.NewRobotScript;
import com.rs.game.player.robot.Robot;

/**
 * 
 * @author Miles/Danny (bobismyname)
 * @date May 20, 2016
 */
public abstract class Method {

	public enum Stage {
		Looking, Fighting, Running, Waiting, Finished, 
		Protecting
	}

	public Method(Robot robot) {
		this.robot = robot;
	}

	protected Robot robot;

	protected Stage stage;

	public abstract void process();
	
	public void pause(int paused) {
		getScript().pause(paused);
	}

	public NewRobotScript getScript() {
		return robot.newScript;
	}

	public void setRobot(Robot robot) {
		this.robot = robot;
	}

	public Robot getRobot() {
		return robot;
	}

	public Stage getStage() {
		return stage;
	}
}
