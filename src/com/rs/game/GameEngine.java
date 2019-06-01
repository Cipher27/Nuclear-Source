package com.rs.game;

import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.rs.cores.DecoderThreadFactory;
import com.rs.cores.SlowThreadFactory;
import com.rs.game.task.Task;
import com.rs.game.task.TaskHandler;
import com.rs.game.task.TaskManager;
import com.rs.utils.Logger;

public final class GameEngine {

	public static GameEngine get() {
		if (instance == null)
			instance = new GameEngine();
		return instance;
	}

	public static volatile boolean shutdown;

	private static GameEngine instance;
	private ExecutorService BOSS_EXECUTER;

	private ExecutorService WORKER_EXECUTER;
	private Timer fastExecutor;
	private ScheduledExecutorService slowExecutor;

	//public static MinigameThread minigameThread;

	private TaskManager tasker;

	public GameEngine() {
		//minigameThread = new MinigameThread();
		bossExecutor(Executors
				.newSingleThreadExecutor(new DecoderThreadFactory()));
		setWorker(Executors
				.newSingleThreadExecutor(new DecoderThreadFactory()));
		setFastExecutor(new Timer());
		setSlowExecuter(Executors
				.newSingleThreadScheduledExecutor(new SlowThreadFactory()));
		setTaskManager(new TaskManager());
		//minigameThread.start();
		Logger.log("Game Engine", "Started Engine and Executors.");
	}

	public GameEngine addTask(final Task task) {

		if (task == null)
			return this;

		getTaskManager().schedule(task);
		return this;
	}

	public GameEngine addTasks(Task[] tasks) {
		for (int i = 0; i < tasks.length; i++)
			addTask(tasks[i]);
		return this;
	}

	public ExecutorService getBossExecutor() {
		return BOSS_EXECUTER;
	}

	public GameEngine bossExecutor(ExecutorService executor) {
		this.BOSS_EXECUTER = executor;
		return this;
	}

	public Timer getFastExecutor() {
		return fastExecutor;
	}

	public GameEngine setFastExecutor(Timer fastExecutor) {
		this.fastExecutor = fastExecutor;
		return this;
	}

	public GameEngine init() {
		try {
			addTasks(TaskHandler.get().tasks());
			Logger.log("Game Engine", "Started "
					+ TaskHandler.get().tasks().length + " Tasks.");
		} catch (final Exception e) {
			Logger.log("Game Engine", "Error.");
		}
		return this;
	}

	public GameEngine shutdown() {
		getBossExecutor().shutdown();
		getWorker().shutdown();
		getFastExecutor().cancel();
		getSlowExecutor().shutdown();
		getTaskManager().shutdown();
		Logger.log("Game Engine", "Game Engine has been shutdown.");
		shutdown = true;
		return this;
	}

	public GameEngine setSlowExecuter(ScheduledExecutorService slowExecuter) {
		this.slowExecutor = slowExecuter;
		return this;
	}

	public ScheduledExecutorService getSlowExecutor() {
		return slowExecutor;
	}

	public TaskManager getTaskManager() {
		return tasker;
	}

	public GameEngine setTaskManager(TaskManager tasker) {
		this.tasker = tasker;
		return this;
	}

	public ExecutorService getWorker() {
		return WORKER_EXECUTER;
	}

	public GameEngine setWorker(ExecutorService executor) {
		this.WORKER_EXECUTER = executor;
		return this;
	}

}

