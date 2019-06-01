package com.rs.game.player.content.ports.npcs.meg;


public class Answer {
  

	enum RATING {TERRIBLE,BAD,NEUTRAL,GOOD,EXCELLENT}; 

	private String answer;
	private RATING rate;
	public Answer(String answer,RATING rating ){
		this.setAnswer(answer);
		this.setRate(rating);
	}
	/**
	 * @return the answer
	 */
	public String getAnswer() {
		return answer;
	}
	/**
	 * @param answer the answer to set
	 */
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	/**
	 * @return the rate
	 */
	public RATING getRate() {
		return rate;
	}
	/**
	 * @param rate the rate to set
	 */
	public void setRate(RATING rate) {
		this.rate = rate;
	}
}
