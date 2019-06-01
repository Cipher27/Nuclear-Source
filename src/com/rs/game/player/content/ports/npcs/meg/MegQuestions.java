package com.rs.game.player.content.ports.npcs.meg;

import com.rs.game.player.content.ports.npcs.meg.Answer.RATING;
/**
 * handles the daily questions of meg
 * @author paolo
 *
 */
public class MegQuestions {
  
	public enum questions{
		
			HUNTER("A friend of mine told me about this rare breed of shark that can walk on land. I’ve decided to go and hunt one down – It’d look great mounted on my wall at home. Erm..do you have any idea how I would go about doing that?"
					, new Answer[] {
							new Answer("You'll need a strong harpoon.",RATING.BAD), 
							new Answer("Use kebbits as bait", RATING.GOOD), 
							new Answer("Dance on the ground to attract them.",RATING.EXCELLENT), 
							new Answer("Use fire to trap them in one place.", RATING.TERRIBLE), 
							new Answer("Dig a moat to trap them",RATING.NEUTRAL)
					});
		
		String questions;
		Answer Answers[];
		
		private questions(String question, Answer answer[]){
			this.questions=question;
			this.Answers = answer;
		}
	
	}
}
