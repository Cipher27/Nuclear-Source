package com.rs.game.player.dialogues;


import java.util.HashMap;

import com.rs.game.minigames.warbands.WarbandsRewards;
import com.rs.game.minigames.zombies.ZombieMonk;
import com.rs.game.player.content.instances.InstanceD;
import com.rs.game.player.content.thieving.pyramidplunder.LeavePyramidD;
import com.rs.game.player.dialogues.Canifis.Taxidermist;
import com.rs.game.player.dialogues.GertrudesCat.Fluffs;
import com.rs.game.player.dialogues.GertrudesCat.Gertrude;
import com.rs.game.player.dialogues.GertrudesCat.WiloughAndShilop;
import com.rs.game.player.dialogues.TeleportInterface.AgilityD;
import com.rs.game.player.dialogues.TeleportInterface.CraftingD;
import com.rs.game.player.dialogues.TeleportInterface.FarmingD;
import com.rs.game.player.dialogues.TeleportInterface.FishingD;
import com.rs.game.player.dialogues.TeleportInterface.HunterD;
import com.rs.game.player.dialogues.TeleportInterface.MiningD;
import com.rs.game.player.dialogues.TeleportInterface.RunecraftingD;
import com.rs.game.player.dialogues.TeleportInterface.SmithingD;
import com.rs.game.player.dialogues.TeleportInterface.ThievingD;
import com.rs.game.player.dialogues.TeleportInterface.WoodcuttingD;
import com.rs.game.player.dialogues.ZariaQuest.AncientRock;
import com.rs.game.player.dialogues.ZariaQuest.EndPortal;
import com.rs.game.player.dialogues.ZariaQuest.EndQuest;
import com.rs.game.player.dialogues.ZariaQuest.PortalFound;
import com.rs.game.player.dialogues.ZariaQuest.StartQuest;
import com.rs.game.player.dialogues.ZariaQuest.ZariaIsland;
import com.rs.game.player.dialogues.ZariaQuest.ZariaPlace;
import com.rs.game.player.dialogues.achievements.Achievements;
import com.rs.game.player.dialogues.alkharid.AliMorrisane;
import com.rs.game.player.dialogues.alkharid.Avan;
import com.rs.game.player.dialogues.alkharid.Camel;
import com.rs.game.player.dialogues.alkharid.Faruq;
import com.rs.game.player.dialogues.alkharid.FatherReen;
import com.rs.game.player.dialogues.alkharid.FremennikWarrior;
import com.rs.game.player.dialogues.alkharid.GemTrader;
import com.rs.game.player.dialogues.alkharid.Leaflet;
import com.rs.game.player.dialogues.alkharid.LouieLegs;
import com.rs.game.player.dialogues.alkharid.Osman;
import com.rs.game.player.dialogues.alkharid.SilkTrader;
import com.rs.game.player.dialogues.auto.DungLeaving;
import com.rs.game.player.dialogues.auto.EnteringDung;
import com.rs.game.player.dialogues.auto.TriviaRewards;
import com.rs.game.player.dialogues.christmas.Imp1;
import com.rs.game.player.dialogues.christmas.Imp2;
import com.rs.game.player.dialogues.christmas.Imp3;
import com.rs.game.player.dialogues.christmas.Imp4;
import com.rs.game.player.dialogues.christmas.Imp5;
import com.rs.game.player.dialogues.christmas.JackFrost;
import com.rs.game.player.dialogues.christmas.Santa1;
import com.rs.game.player.dialogues.construction.PortalChamberD;
import com.rs.game.player.dialogues.draynor.Aggie;
import com.rs.game.player.dialogues.draynor.Diango;
import com.rs.game.player.dialogues.draynor.Fisherman;
import com.rs.game.player.dialogues.draynor.Fortunato;
import com.rs.game.player.dialogues.draynor.Morgan;
import com.rs.game.player.dialogues.draynor.Ned1;
import com.rs.game.player.dialogues.draynor.Olivia;
import com.rs.game.player.dialogues.draynor.PressureGauge;
import com.rs.game.player.dialogues.draynor.ProfessorOddenstein;
import com.rs.game.player.dialogues.draynor.Schism;
import com.rs.game.player.dialogues.draynor.Sedridor;
import com.rs.game.player.dialogues.draynor.Traiborn;
import com.rs.game.player.dialogues.draynor.Veronica;
import com.rs.game.player.dialogues.draynor.Wizard;
import com.rs.game.player.dialogues.draynor.WizardGrayzig;
import com.rs.game.player.dialogues.draynor.WizardMizgog;
import com.rs.game.player.dialogues.edgeville.Oracle;
import com.rs.game.player.dialogues.edgeville.Oziach;
import com.rs.game.player.dialogues.home.HomeTour;
import com.rs.game.player.dialogues.home.RulesD;
import com.rs.game.player.dialogues.home.ServerIntro;
import com.rs.game.player.dialogues.home.ServerIntro2;
import com.rs.game.player.dialogues.home.ServerIntro3;
import com.rs.game.player.dialogues.home.SkillerMaster;
import com.rs.game.player.dialogues.home.ZariaD;
import com.rs.game.player.dialogues.impl.Acanatha;
import com.rs.game.player.dialogues.impl.CollectGEItems;
import com.rs.game.player.dialogues.impl.GETutor;
import com.rs.game.player.dialogues.impl.GrandExchangeClerk;
import com.rs.game.player.dialogues.impl.Graves;
import com.rs.game.player.dialogues.impl.InstancedDungeonDialogue;
import com.rs.game.player.dialogues.impl.Player_Shop_Manager;
import com.rs.game.player.dialogues.impl.SolomonD;
import com.rs.game.player.dialogues.impl.ToggleGraves;
import com.rs.game.player.dialogues.interfaces.ModPanel;
import com.rs.game.player.dialogues.interfaces.Quests;
import com.rs.game.player.dialogues.interfaces.panel.AdminPanel;
import com.rs.game.player.dialogues.interfaces.panel.DonatorTab;
import com.rs.game.player.dialogues.interfaces.panel.HomeTeleportD;
import com.rs.game.player.dialogues.interfaces.panel.InfoPanel;
import com.rs.game.player.dialogues.interfaces.panel.MemberShipD;
import com.rs.game.player.dialogues.karamja.Jungler;
import com.rs.game.player.dialogues.karamja.Saniboch;
import com.rs.game.player.dialogues.lostcity.MonkOfEntrana;
import com.rs.game.player.dialogues.lostcity.Shamus;
import com.rs.game.player.dialogues.lostcity.Warrior;
import com.rs.game.player.dialogues.lumbridge.ApprenticeSmith;
import com.rs.game.player.dialogues.lumbridge.BarfyBill;
import com.rs.game.player.dialogues.lumbridge.BeefyBill;
import com.rs.game.player.dialogues.lumbridge.Bob;
import com.rs.game.player.dialogues.lumbridge.BorderGuard;
import com.rs.game.player.dialogues.lumbridge.Donie;
import com.rs.game.player.dialogues.lumbridge.Doomsayer;
import com.rs.game.player.dialogues.lumbridge.DukeHoracio;
import com.rs.game.player.dialogues.lumbridge.ExplorerJack;
import com.rs.game.player.dialogues.lumbridge.FatherAereck;
import com.rs.game.player.dialogues.lumbridge.FatherAereck1;
import com.rs.game.player.dialogues.lumbridge.FatherAereck2;
import com.rs.game.player.dialogues.lumbridge.FatherUrhney;
import com.rs.game.player.dialogues.lumbridge.Ghost;
import com.rs.game.player.dialogues.lumbridge.GhostFind;
import com.rs.game.player.dialogues.lumbridge.GhostWo;
import com.rs.game.player.dialogues.lumbridge.Guardsman1;
import com.rs.game.player.dialogues.lumbridge.Guardsman2;
import com.rs.game.player.dialogues.lumbridge.Ham;
import com.rs.game.player.dialogues.lumbridge.Hans;
import com.rs.game.player.dialogues.lumbridge.Iain;
import com.rs.game.player.dialogues.lumbridge.JimmyChisel;
import com.rs.game.player.dialogues.lumbridge.Julian;
import com.rs.game.player.dialogues.lumbridge.Lachtopher;
import com.rs.game.player.dialogues.lumbridge.LumbridgeCook;
import com.rs.game.player.dialogues.lumbridge.LumbridgeSage;
import com.rs.game.player.dialogues.lumbridge.MagicInstructor;
import com.rs.game.player.dialogues.lumbridge.MillieMiller;
import com.rs.game.player.dialogues.lumbridge.Musicians;
import com.rs.game.player.dialogues.lumbridge.Nastroth;
import com.rs.game.player.dialogues.lumbridge.PriestYauchomi;
import com.rs.game.player.dialogues.lumbridge.RangedInstructor;
import com.rs.game.player.dialogues.lumbridge.Victoria;
import com.rs.game.player.dialogues.lumbridge.Xenia;
import com.rs.game.player.dialogues.pets.ChickarraD;
import com.rs.game.player.dialogues.pets.CorporealPuppyD;
import com.rs.game.player.dialogues.pets.EllieD;
import com.rs.game.player.dialogues.pets.GeneralAwwdorD;
import com.rs.game.player.dialogues.pets.KingBlackDragonlingD;
import com.rs.game.player.dialogues.pets.KrilTinyrothD;
import com.rs.game.player.dialogues.pets.NexterminatorD;
import com.rs.game.player.dialogues.portsarim.Betty;
import com.rs.game.player.dialogues.portsarim.Brian;
import com.rs.game.player.dialogues.portsarim.Gerrant;
import com.rs.game.player.dialogues.portsarim.Grum;
import com.rs.game.player.dialogues.portsarim.JackSeagull;
import com.rs.game.player.dialogues.portsarim.Klarense;
import com.rs.game.player.dialogues.portsarim.Ned2;
import com.rs.game.player.dialogues.portsarim.SirRebrum;
import com.rs.game.player.dialogues.portsarim.StankyMorgan;
import com.rs.game.player.dialogues.portsarim.Wormbrain;
import com.rs.game.player.dialogues.portsarim.Wydin;
import com.rs.game.player.dialogues.quests.TheLostPirate.PirateDoor;
import com.rs.game.player.dialogues.quests.TheLostPirate.PirateHomeD;
import com.rs.game.player.dialogues.quests.TheLostPirate.PirateIntro;
import com.rs.game.player.dialogues.quests.TheLostPirate.PirateQuest;
import com.rs.game.player.dialogues.quests.pvmQuest.BossCavesD;
import com.rs.game.player.dialogues.quests.pvmQuest.PvmMasterQuest;
import com.rs.game.player.dialogues.quests.pvmQuest.PvmMasterQuest2;
import com.rs.game.player.dialogues.quests.pvmQuest.PvmMasterQuest3;
import com.rs.game.player.dialogues.recipe.SuperSaradominD;
import com.rs.game.player.dialogues.relekka.DwarvenBoatman;
import com.rs.game.player.dialogues.relekka.Islands;
import com.rs.game.player.dialogues.slayer.EliteDungeonD;
import com.rs.game.player.dialogues.slayer.ExpLamps;
import com.rs.game.player.dialogues.slayer.HeartTokens;
import com.rs.game.player.dialogues.slayer.SlayerDropsD;
import com.rs.game.player.dialogues.slayer.SlayerExp;
import com.rs.game.player.dialogues.slayer.SlayerHelmCraftD;
import com.rs.game.player.dialogues.slayer.SlayerRing;
import com.rs.game.player.dialogues.slayer.SlayerShopD;
import com.rs.game.player.dialogues.slayer.SlayerTabsD;
import com.rs.game.player.dialogues.slayer.SlayerToolbeltOptiosD;
import com.rs.game.player.dialogues.slayer.SlayerhelmUpgradeD;
import com.rs.game.player.dialogues.swordofwiseman.Ozan;
import com.rs.game.player.dialogues.swordofwiseman.SowQuestStart;
import com.rs.game.player.dialogues.varrock.Aubury;
import com.rs.game.player.dialogues.varrock.DrHarlow;
import com.rs.game.player.dialogues.varrock.GuildMaster;
import com.rs.game.player.dialogues.varrock.SawMillOperator;
import com.rs.game.player.dialogues.worldwide.LegendaryPetLevelUp;
import com.rs.game.player.dialogues.worldwide.LevelUp;
import com.rs.utils.Logger;
import com.rs.utils.Utils;

public final class DialogueHandler {

	private static final HashMap<Object, Class<Dialogue>> handledDialogues = new HashMap<Object, Class<Dialogue>>();
	@SuppressWarnings("unchecked")
	public static final void init() {
		try {
			
			Class<Dialogue>[] impl = Utils.
					getClasses("com.rs.game.player.dialogues.impl");
               for (Class<Dialogue> c : impl) {
				if (c.isAnonymousClass())
					continue;
				handledDialogues.put(c.getSimpleName(), c);
			}         
			Class<Dialogue> value1 = (Class<Dialogue>) Class
					.forName(LevelUp.class.getCanonicalName());
			handledDialogues.put("LevelUp", value1);
			Class<Dialogue> value2 = (Class<Dialogue>) Class
					.forName(ZarosAltar.class.getCanonicalName());
			handledDialogues.put("ZarosAltar", value2);
			Class<Dialogue> value3 = (Class<Dialogue>) Class
					.forName(ClimbNoEmoteStairs.class.getCanonicalName());
            			handledDialogues.put("ClimbNoEmoteStairs", value3);
			Class<Dialogue> value4 = (Class<Dialogue>) Class
					.forName(Banker.class.getCanonicalName());
			handledDialogues.put("Banker", value4);
			Class<Dialogue> value5 = (Class<Dialogue>) Class
					.forName(DestroyItemOption.class.getCanonicalName());
			handledDialogues.put("DestroyItemOption", value5);
			Class<Dialogue> value6 = (Class<Dialogue>) Class
					.forName(FremennikShipmaster.class.getCanonicalName());
			handledDialogues.put("FremennikShipmaster", value6);
			Class<Dialogue> value8 = (Class<Dialogue>) Class
					.forName(NexEntrance.class.getCanonicalName());
			handledDialogues.put("NexEntrance", value8);
			Class<Dialogue> value9 = (Class<Dialogue>) Class
					.forName(MagicPortal.class.getCanonicalName());
			handledDialogues.put("MagicPortal", value9);
			Class<Dialogue> value10 = (Class<Dialogue>) Class
					.forName(LunarAltar.class.getCanonicalName());
			handledDialogues.put("LunarAltar", value10);
			Class<Dialogue> value11 = (Class<Dialogue>) Class
					.forName(AncientAltar.class.getCanonicalName());
			handledDialogues.put("AncientAltar", value11);
			// TODO 12 and 13
			
			Class<Dialogue> value12 = (Class<Dialogue>) Class
					.forName(FletchingD.class.getCanonicalName());
			handledDialogues.put("FletchingD", value12);
			Class<Dialogue> value14 = (Class<Dialogue>) Class
					.forName(RuneScapeGuide.class.getCanonicalName());
			handledDialogues.put("RuneScapeGuide", value14);
			Class<Dialogue> value15 = (Class<Dialogue>) Class
					.forName(SurvivalExpert.class.getCanonicalName());
			handledDialogues.put("SurvivalExpert", value15);
			Class<Dialogue> value16 = (Class<Dialogue>) Class
					.forName(SimpleMessage.class.getCanonicalName());
			handledDialogues.put("SimpleMessage", value16);
			Class<Dialogue> value17 = (Class<Dialogue>) Class
					.forName(ItemMessage.class.getCanonicalName());
			handledDialogues.put("ItemMessage", value17);
			Class<Dialogue> value18 = (Class<Dialogue>) Class
					.forName(ClimbEmoteStairs.class.getCanonicalName());
			handledDialogues.put("ClimbEmoteStairs", value18);
			Class<Dialogue> value19 = (Class<Dialogue>) Class
					.forName(QuestGuide.class.getCanonicalName());
			handledDialogues.put("QuestGuide", value19);
			Class<Dialogue> value20 = (Class<Dialogue>) Class
					.forName(GemCuttingD.class.getCanonicalName());
			handledDialogues.put("GemCuttingD", value20);
			
			Class<Dialogue> value21 = (Class<Dialogue>) Class
					.forName(CookingD.class.getCanonicalName());
			handledDialogues.put("CookingD", value21);
			Class<Dialogue> value22 = (Class<Dialogue>) Class
					.forName(HerbloreD.class.getCanonicalName());
			handledDialogues.put("HerbloreD", value22);
			Class<Dialogue> value23 = (Class<Dialogue>) Class
					.forName(BarrowsD.class.getCanonicalName());
			handledDialogues.put("BarrowsD", value23);
			
			Class<Dialogue> value24 = (Class<Dialogue>) Class
					.forName(SmeltingD.class.getCanonicalName());
			handledDialogues.put("SmeltingD", value24);
			Class<Dialogue> value25 = (Class<Dialogue>) Class
					.forName(LeatherCraftingD.class.getCanonicalName());
			handledDialogues.put("LeatherCraftingD", value25);
			Class<Dialogue> value26 = (Class<Dialogue>) Class
					.forName(EnchantedGemDialouge.class.getCanonicalName());
			handledDialogues.put("EnchantedGemDialouge", value26);
			Class<Dialogue> value27 = (Class<Dialogue>) Class
					.forName(ForfeitDialouge.class.getCanonicalName());
			handledDialogues.put("ForfeitDialouge", value27);
			Class<Dialogue> value28 = (Class<Dialogue>) Class
					.forName(Transportation.class.getCanonicalName());
			handledDialogues.put("Transportation", value28);
			Class<Dialogue> value29 = (Class<Dialogue>) Class
					.forName(WildernessDitch.class.getCanonicalName());
			handledDialogues.put("WildernessDitch", value29);
			Class<Dialogue> value30 = (Class<Dialogue>) Class
					.forName(SimpleNPCMessage.class.getCanonicalName());
			handledDialogues.put("SimpleNPCMessage", value30);
			Class<Dialogue> value31 = (Class<Dialogue>) Class
					.forName(Transportation.class.getCanonicalName());
			handledDialogues.put("Transportation", value31);
			Class<Dialogue> value32 = (Class<Dialogue>) Class
					.forName(DTSpectateReq.class.getCanonicalName());
			handledDialogues.put("DTSpectateReq", value32);
			Class<Dialogue> value33 = (Class<Dialogue>) Class
					.forName(StrangeFace.class.getCanonicalName());
			handledDialogues.put("StrangeFace", value33);
			Class<Dialogue> value34 = (Class<Dialogue>) Class
					.forName(AncientEffigiesD.class.getCanonicalName());
			handledDialogues.put("AncientEffigiesD", value34);
			Class<Dialogue> value35 = (Class<Dialogue>) Class
					.forName(DTClaimRewards.class.getCanonicalName());
			handledDialogues.put("DTClaimRewards", value35);
			Class<Dialogue> value36 = (Class<Dialogue>) Class
					.forName(SetSkills.class.getCanonicalName());
			handledDialogues.put("SetSkills", value36);
			Class<Dialogue> value37 = (Class<Dialogue>) Class
					.forName(DismissD.class.getCanonicalName());
			handledDialogues.put("DismissD", value37);
			Class<Dialogue> value38 = (Class<Dialogue>) Class
					.forName(MrEx.class.getCanonicalName());
			handledDialogues.put("MrEx", value38);
			Class<Dialogue> value45 = (Class<Dialogue>) Class
					.forName(RuneSpanStore.class.getCanonicalName());
			handledDialogues.put("RuneSpanStore", value45);
			Class<Dialogue> value39 = (Class<Dialogue>) Class
					.forName(MakeOverMage.class.getCanonicalName());
			handledDialogues.put("MakeOverMage", value39);
			Class<Dialogue> value40 = (Class<Dialogue>) Class
					.forName(KaramjaTrip.class.getCanonicalName());
			handledDialogues.put("KaramjaTrip", value40);
			Class<Dialogue> value44 = (Class<Dialogue>) Class
					.forName(LumbridgeCook.class.getCanonicalName());
			handledDialogues.put("LumbridgeCook", value44);
			Class<Dialogue> value46 = (Class<Dialogue>) Class
					.forName(Musicians.class.getCanonicalName());
			handledDialogues.put("Musicians", value46);
			Class<Dialogue> value47 = (Class<Dialogue>) Class
					.forName(BorderGuard.class.getCanonicalName());
			handledDialogues.put("BorderGuard", value47);
			Class<Dialogue> value49 = (Class<Dialogue>) Class
					.forName(ExplorerJack.class.getCanonicalName());
			handledDialogues.put("ExplorerJack", value49);
			Class<Dialogue> value52 = (Class<Dialogue>) Class
					.forName(LumbridgeSage.class.getCanonicalName());
			handledDialogues.put("LumbridgeSage", value52);
			Class<Dialogue> value53 = (Class<Dialogue>) Class
					.forName(Hans.class.getCanonicalName());
			handledDialogues.put("Hans", value53);
			Class<Dialogue> value54 = (Class<Dialogue>) Class
					.forName(Doomsayer.class.getCanonicalName());
			handledDialogues.put("Doomsayer", value54);
			Class<Dialogue> value56 = (Class<Dialogue>) Class
					.forName(FatherAereck.class.getCanonicalName());
			handledDialogues.put("FatherAereck", value56);
			Class<Dialogue> value42 = (Class<Dialogue>) Class
					.forName(DagonHai.class.getCanonicalName());
			handledDialogues.put("DagonHai", value42);
			Class<Dialogue> value43 = (Class<Dialogue>) Class
					.forName(TeleportCrystal.class.getCanonicalName());
			handledDialogues.put("TeleportCrystal", value43);
			Class<Dialogue> value98 = (Class<Dialogue>) Class.forName(ToggleGraves.class.getCanonicalName());
			handledDialogues.put("ToggleGraves", value98);
			Class<Dialogue> value99 = (Class<Dialogue>) Class.forName(Graves.class.getCanonicalName());
			handledDialogues.put("Graves", value99);
			Class<Dialogue> value74 = (Class<Dialogue>) Class
					.forName(WizardMizgog.class.getCanonicalName());
			handledDialogues.put("WizardMizgog", value74);
			Class<Dialogue> value59 = (Class<Dialogue>) Class
					.forName(LendReturn.class.getCanonicalName());
			handledDialogues.put("LendReturn", value59);
			Class<Dialogue> value61 = (Class<Dialogue>) Class
					.forName(DiscardLend.class.getCanonicalName());
			handledDialogues.put("DiscardLend", value61);
			Class<Dialogue> value66 = (Class<Dialogue>) Class
					
					.forName(StarSprite.class.getCanonicalName());
			handledDialogues.put("StarSprite", value66);
			Class<Dialogue> value104 = (Class<Dialogue>) Class.forName(GrandExchangeClerk.class.getCanonicalName());
			handledDialogues.put("GrandExchangeClerk", value104);
			Class<Dialogue> value106 = (Class<Dialogue>) Class.forName(GETutor.class.getCanonicalName());
			handledDialogues.put("GETutor", value106);
			Class<Dialogue> value101 = (Class<Dialogue>) Class.forName(CollectGEItems.class.getCanonicalName());
			handledDialogues.put("CollectGEItems", value101);
			handledDialogues.put("ForumPoint", (Class<Dialogue>) Class
			.forName(ForumPoint.class.getCanonicalName()));
		//	handledDialogues.put("DungRewardConfirm", (Class<Dialogue>) Class.forName(DungRewardConfirm.class.getCanonicalName()));
			handledDialogues.put("Guthix", (Class<Dialogue>) Class
					.forName(Guthix.class.getCanonicalName()));
			handledDialogues.put("WouldYou", (Class<Dialogue>) Class
					.forName(WouldYou.class.getCanonicalName()));
			handledDialogues.put("Oziach", (Class<Dialogue>) Class
					.forName(Oziach.class.getCanonicalName()));
			handledDialogues.put("Oracle", (Class<Dialogue>) Class
					.forName(Oracle.class.getCanonicalName()));
			handledDialogues.put("Wormbrain", (Class<Dialogue>) Class
					.forName(Wormbrain.class.getCanonicalName()));
			handledDialogues.put("Farmer", (Class<Dialogue>) Class
					.forName(Farmer.class.getCanonicalName()));
			handledDialogues.put("PetsStore", (Class<Dialogue>) Class
				     .forName(PetsStore.class.getCanonicalName()));
			handledDialogues.put("Klarense", (Class<Dialogue>) Class
					.forName(Klarense.class.getCanonicalName()));
			handledDialogues.put("Betty", (Class<Dialogue>) Class
					.forName(Betty.class.getCanonicalName()));
			handledDialogues.put("OsmanDialogue", (Class<Dialogue>) Class
					.forName(OsmanDialogue.class.getCanonicalName()));
			handledDialogues.put("Churning", (Class<Dialogue>) Class
					.forName(Churning.class.getCanonicalName()));
			handledDialogues.put("Loom", (Class<Dialogue>) Class
					.forName(Loom.class.getCanonicalName()));
			handledDialogues.put("Ned1", (Class<Dialogue>) Class
					.forName(Ned1.class.getCanonicalName()));
			handledDialogues.put("Ned2", (Class<Dialogue>) Class
					.forName(Ned2.class.getCanonicalName()));
			handledDialogues.put("KillStreak", (Class<Dialogue>) Class
					.forName(KillStreak.class.getCanonicalName()));
			handledDialogues.put("Saniboch", (Class<Dialogue>) Class
					.forName(Saniboch.class.getCanonicalName()));
			handledDialogues.put("Olivia", (Class<Dialogue>) Class
					.forName(Olivia.class.getCanonicalName()));
			handledDialogues.put("SirRebrum", (Class<Dialogue>) Class
					.forName(SirRebrum.class.getCanonicalName()));
			handledDialogues.put("Islands", (Class<Dialogue>) Class
					.forName(Islands.class.getCanonicalName()));
			handledDialogues.put("GraveStones", (Class<Dialogue>) Class
					.forName(GraveStones.class.getCanonicalName()));
			handledDialogues.put("GrandExchange", (Class<Dialogue>) Class
					.forName(GrandExchange.class.getCanonicalName()));
			handledDialogues.put("Lumdo", (Class<Dialogue>) Class
					.forName(Islands.class.getCanonicalName()));
			handledDialogues.put("Charges", (Class<Dialogue>) Class
					.forName(Charges.class.getCanonicalName()));
			handledDialogues.put("Orb", (Class<Dialogue>) Class
					.forName(Orb.class.getCanonicalName()));
			handledDialogues.put("GamfriedShield", (Class<Dialogue>) Class
					.forName(GamfriedShield.class.getCanonicalName()));
			handledDialogues.put("HousePortal", (Class<Dialogue>) Class
					.forName(HousePortal.class.getCanonicalName()));
			handledDialogues.put("EstateAgent", (Class<Dialogue>) Class
					.forName(EstateAgent.class.getCanonicalName()));
			handledDialogues.put("Jungler", (Class<Dialogue>) Class
					.forName(Jungler.class.getCanonicalName()));
			handledDialogues.put("Aggie", (Class<Dialogue>) Class
					.forName(Aggie.class.getCanonicalName()));
			handledDialogues.put("Schism", (Class<Dialogue>) Class
					.forName(Schism.class.getCanonicalName()));
			handledDialogues.put("Morgan", (Class<Dialogue>) Class
					.forName(Morgan.class.getCanonicalName()));
			handledDialogues.put("DrHarlow", (Class<Dialogue>) Class
					.forName(DrHarlow.class.getCanonicalName()));
			handledDialogues.put("WizardGrayzig", (Class<Dialogue>) Class
					.forName(WizardGrayzig.class.getCanonicalName()));
			handledDialogues.put("Traiborn", (Class<Dialogue>) Class
					.forName(Traiborn.class.getCanonicalName()));
			handledDialogues.put("Wizard", (Class<Dialogue>) Class
					.forName(Wizard.class.getCanonicalName()));
			handledDialogues.put("JackSeagull", (Class<Dialogue>) Class
					.forName(JackSeagull.class.getCanonicalName()));
			handledDialogues.put("StankyMorgan", (Class<Dialogue>) Class
					.forName(StankyMorgan.class.getCanonicalName()));
			handledDialogues.put("Wizard", (Class<Dialogue>) Class
					.forName(Wizard.class.getCanonicalName()));
			handledDialogues.put("Aubury", (Class<Dialogue>) Class
					.forName(Aubury.class.getCanonicalName()));
			handledDialogues.put("ChangeElement", (Class<Dialogue>) Class
					.forName(ChangeElement.class.getCanonicalName()));
			handledDialogues.put("Sedridor", (Class<Dialogue>) Class
					.forName(Sedridor.class.getCanonicalName()));
			handledDialogues.put("KamfreendaDefender", (Class<Dialogue>) Class
					.forName(KamfreendaDefender.class.getCanonicalName()));
			handledDialogues.put("ClaimClanItem", (Class<Dialogue>) Class
					.forName(ClaimClanItem.class.getCanonicalName()));
			handledDialogues.put("ClanCreateD", (Class<Dialogue>) Class
					.forName(ClanCreateD.class.getCanonicalName()));
			handledDialogues.put("ClanInvite", (Class<Dialogue>) Class
					.forName(ClanInvite.class.getCanonicalName()));
			handledDialogues.put("ClanMotto", (Class<Dialogue>) Class
					.forName(ClanMotto.class.getCanonicalName()));
			handledDialogues.put("LeaveClan", (Class<Dialogue>) Class
					.forName(LeaveClan.class.getCanonicalName()));
			handledDialogues.put("Sailing", (Class<Dialogue>) Class
					.forName(Sailing.class.getCanonicalName()));
			handledDialogues.put("SingleSmithingD", (Class<Dialogue>) Class
					.forName(SingleSmithingD.class.getCanonicalName()));
			handledDialogues.put("DwarvenBoatman", (Class<Dialogue>) Class
					.forName(DwarvenBoatman.class.getCanonicalName()));
			handledDialogues.put("Conductor", (Class<Dialogue>) Class
					.forName(Conductor.class.getCanonicalName()));
			handledDialogues.put("Fortunato", (Class<Dialogue>) Class
					.forName(Fortunato.class.getCanonicalName()));
			handledDialogues.put("Shelves", (Class<Dialogue>) Class
					.forName(Shelves.class.getCanonicalName()));
			handledDialogues.put("WeaponsRack", (Class<Dialogue>) Class
					.forName(WeaponsRack.class.getCanonicalName()));
			handledDialogues.put("Larders", (Class<Dialogue>) Class
					.forName(Larders.class.getCanonicalName()));
			handledDialogues.put("LegendaryPetLevelUp", (Class<Dialogue>) Class
					.forName(LegendaryPetLevelUp.class.getCanonicalName()));
			handledDialogues.put("Heraldry", (Class<Dialogue>) Class
					.forName(Heraldry.class.getCanonicalName()));
			handledDialogues.put("CraftingBench", (Class<Dialogue>) Class
					.forName(CraftingBench.class.getCanonicalName()));
			handledDialogues.put("ToolStore", (Class<Dialogue>) Class
					.forName(ToolStore.class.getCanonicalName()));
			handledDialogues.put("PkScores", (Class<Dialogue>) Class
					.forName(PkScores.class.getCanonicalName()));
			handledDialogues.put("Fisherman", (Class<Dialogue>) Class
					.forName(Fisherman.class.getCanonicalName()));
			handledDialogues.put("LanderSquire", (Class<Dialogue>) Class
					.forName(LanderSquire.class.getCanonicalName()));
			handledDialogues.put("VoidKnightExchange", (Class<Dialogue>) Class
					.forName(VoidKnightExchange.class.getCanonicalName()));
			handledDialogues.put("OttoGodblessed", (Class<Dialogue>) Class
					.forName(OttoGodblessed.class.getCanonicalName()));
			handledDialogues.put("DonatorShops", (Class<Dialogue>) Class
					.forName(DonatorShops.class.getCanonicalName()));
			handledDialogues.put("Taxidermist", (Class<Dialogue>) Class
					.forName(Taxidermist.class.getCanonicalName()));
			handledDialogues.put("RugMerchant", (Class<Dialogue>) Class
					.forName(RugMerchant.class.getCanonicalName()));
			handledDialogues.put("Gertrude", (Class<Dialogue>) Class
					.forName(Gertrude.class.getCanonicalName()));
			handledDialogues.put("TanningD", (Class<Dialogue>) Class
					.forName(TanningD.class.getCanonicalName()));
			handledDialogues.put("WiloughAndShilop", (Class<Dialogue>) Class
					.forName(WiloughAndShilop.class.getCanonicalName()));
			handledDialogues.put("Fluffs", (Class<Dialogue>) Class
					.forName(Fluffs.class.getCanonicalName()));
			handledDialogues.put("DrakansMedallion", (Class<Dialogue>) Class
					.forName(DrakansMedallion.class.getCanonicalName()));
			handledDialogues.put("SmokeDungeonEntrance", (Class<Dialogue>) Class
					.forName(SmokeDungeonEntrance.class.getCanonicalName()));
			handledDialogues.put("MTLowLevelTraining", (Class<Dialogue>) Class
					.forName(MTLowLevelTraining.class.getCanonicalName()));
			handledDialogues.put("MTMediumLevelTraining", (Class<Dialogue>) Class
					.forName(MTMediumLevelTraining.class.getCanonicalName()));
			handledDialogues.put("MTLowLevelDungeons", (Class<Dialogue>) Class
					.forName(MTLowLevelDungeons.class.getCanonicalName()));
			handledDialogues.put("MTMediumLevelDungeons", (Class<Dialogue>) Class
					.forName(MTMediumLevelDungeons.class.getCanonicalName()));
			handledDialogues.put("MTHighLevelDungeons", (Class<Dialogue>) Class
					.forName(MTHighLevelDungeons.class.getCanonicalName()));
			handledDialogues.put("MTSlayerDungeons", (Class<Dialogue>) Class
					.forName(MTSlayerDungeons.class.getCanonicalName()));
			handledDialogues.put("MTMediumLevelBosses", (Class<Dialogue>) Class
					.forName(MTMediumLevelBosses.class.getCanonicalName()));
			handledDialogues.put("MTHighLevelBosses", (Class<Dialogue>) Class
					.forName(MTHighLevelBosses.class.getCanonicalName()));
			handledDialogues.put("BoltTipMaking", (Class<Dialogue>) Class
					.forName(BoltTipMaking.class.getCanonicalName()));
			handledDialogues.put("SwapSpellBook", (Class<Dialogue>) Class
					.forName(SwapSpellBook.class.getCanonicalName()));
			handledDialogues.put("PenguinRewards", (Class<Dialogue>) Class
					.forName(PenguinRewards.class.getCanonicalName()));
			handledDialogues.put("SpinningD", (Class<Dialogue>) Class
					.forName(SpinningD.class.getCanonicalName()));
			handledDialogues.put("ClimbHouseStairD", (Class<Dialogue>) Class
					.forName(ClimbHouseStairD.class.getCanonicalName()));
			handledDialogues.put("CreateRoomD", (Class<Dialogue>) Class
					.forName(CreateRoomD.class.getCanonicalName()));
			handledDialogues.put("CreateRoomStairsD", (Class<Dialogue>) Class
					.forName(CreateRoomStairsD.class.getCanonicalName()));
			handledDialogues.put("RemoveBuildD", (Class<Dialogue>) Class
					.forName(RemoveBuildD.class.getCanonicalName()));
			handledDialogues.put("RemoveRoomD", (Class<Dialogue>) Class
					.forName(RemoveRoomD.class.getCanonicalName()));
			handledDialogues.put("FremennikWarrior", (Class<Dialogue>) Class
					.forName(FremennikWarrior.class.getCanonicalName()));
			handledDialogues.put("LanderD", (Class<Dialogue>) Class
					.forName(LanderD.class.getCanonicalName()));
			handledDialogues.put("Bob", (Class<Dialogue>) Class
					.forName(Bob.class.getCanonicalName()));
			handledDialogues.put("Lumber", (Class<Dialogue>) Class
					.forName(Lumber.class.getCanonicalName()));
			handledDialogues.put("ImplingManager", (Class<Dialogue>) Class
					.forName(ImplingManager.class.getCanonicalName()));
			handledDialogues.put("LouieLegs", (Class<Dialogue>) Class
					.forName(LouieLegs.class.getCanonicalName()));
			handledDialogues.put("FatherReen", (Class<Dialogue>) Class
					.forName(FatherReen.class.getCanonicalName()));
			handledDialogues.put("DeathTaskMaster", (Class<Dialogue>) Class
					.forName(DeathTaskMaster.class.getCanonicalName()));
			handledDialogues.put("GemTrader", (Class<Dialogue>) Class
					.forName(GemTrader.class.getCanonicalName()));
			handledDialogues.put("Donie", (Class<Dialogue>) Class
					.forName(Donie.class.getCanonicalName()));
			handledDialogues.put("Victoria", (Class<Dialogue>) Class
					.forName(Victoria.class.getCanonicalName()));
			handledDialogues.put("Iain", (Class<Dialogue>) Class
					.forName(Iain.class.getCanonicalName()));
			handledDialogues.put("Cassie", (Class<Dialogue>) Class
					.forName(Cassie.class.getCanonicalName()));
			handledDialogues.put("Dreadnaut", (Class<Dialogue>) Class
					.forName(Dreadnaut.class.getCanonicalName()));
			handledDialogues.put("Diango", (Class<Dialogue>) Class
					.forName(Diango.class.getCanonicalName()));
			handledDialogues.put("ApprenticeSmith", (Class<Dialogue>) Class
					.forName(ApprenticeSmith.class.getCanonicalName()));
			handledDialogues.put("PriestYauchomi", (Class<Dialogue>) Class
					.forName(PriestYauchomi.class.getCanonicalName()));
			handledDialogues.put("Julian", (Class<Dialogue>) Class
					.forName(Julian.class.getCanonicalName()));
			handledDialogues.put("ShantayGuard", (Class<Dialogue>) Class
				     .forName(ShantayGuard.class.getCanonicalName()));
			handledDialogues.put("BeefyBill", (Class<Dialogue>) Class
				     .forName(BeefyBill.class.getCanonicalName()));
			handledDialogues.put("Xenia", (Class<Dialogue>) Class
				     .forName(Xenia.class.getCanonicalName()));
			handledDialogues.put("FarmingShop", (Class<Dialogue>) Class
				     .forName(FarmingShop.class.getCanonicalName()));
			handledDialogues.put("GnomeGlider", (Class<Dialogue>) Class
				     .forName(GnomeGlider.class.getCanonicalName()));
			handledDialogues.put("Lachtopher", (Class<Dialogue>) Class
					.forName(Lachtopher.class.getCanonicalName()));
			handledDialogues.put("Imp1", (Class<Dialogue>) Class
					.forName(Imp1.class.getCanonicalName()));
			handledDialogues.put("Imp2", (Class<Dialogue>) Class
					.forName(Imp2.class.getCanonicalName()));
			handledDialogues.put("Imp3", (Class<Dialogue>) Class
					.forName(Imp3.class.getCanonicalName()));
			handledDialogues.put("Imp4", (Class<Dialogue>) Class
					.forName(Imp4.class.getCanonicalName()));
			handledDialogues.put("Imp5", (Class<Dialogue>) Class
					.forName(Imp5.class.getCanonicalName()));
			handledDialogues.put("Santa1", (Class<Dialogue>) Class
					.forName(Santa1.class.getCanonicalName()));
			handledDialogues.put("GhostDisciple", (Class<Dialogue>) Class
					.forName(GhostDisciple.class.getCanonicalName()));
			handledDialogues.put("SawMillOperator", (Class<Dialogue>) Class
					.forName(SawMillOperator.class.getCanonicalName()));
			handledDialogues.put("JackFrost", (Class<Dialogue>) Class
					.forName(JackFrost.class.getCanonicalName()));
			handledDialogues.put("ShantayWarning", (Class<Dialogue>) Class
				     .forName(ShantayWarning.class.getCanonicalName()));
			handledDialogues.put("RangedInstructor", (Class<Dialogue>) Class
					.forName(RangedInstructor.class.getCanonicalName()));
			handledDialogues.put("MagicInstructor", (Class<Dialogue>) Class
					.forName(MagicInstructor.class.getCanonicalName()));
			handledDialogues.put("MeleeInstructor", (Class<Dialogue>) Class
					.forName(MeleeInstructor.class.getCanonicalName()));
			handledDialogues.put("BarfyBill", (Class<Dialogue>) Class
					.forName(BarfyBill.class.getCanonicalName()));
			handledDialogues.put("SilkTrader", (Class<Dialogue>) Class
					.forName(SilkTrader.class.getCanonicalName()));
			handledDialogues.put("Osman", (Class<Dialogue>) Class
					.forName(Osman.class.getCanonicalName()));
			handledDialogues.put("Avan", (Class<Dialogue>) Class
					.forName(Avan.class.getCanonicalName()));
			handledDialogues.put("Leaflet", (Class<Dialogue>) Class
					.forName(Leaflet.class.getCanonicalName()));
			handledDialogues.put("Warrior", (Class<Dialogue>) Class
					.forName(Warrior.class.getCanonicalName()));
			handledDialogues.put("Shamus", (Class<Dialogue>) Class
					.forName(Shamus.class.getCanonicalName()));
			handledDialogues.put("PressureGauge", (Class<Dialogue>) Class
					.forName(PressureGauge.class.getCanonicalName()));
			handledDialogues.put("Veronica", (Class<Dialogue>) Class
					.forName(Veronica.class.getCanonicalName()));
			handledDialogues.put("ProfessorOddenstein", (Class<Dialogue>) Class
					.forName(ProfessorOddenstein.class.getCanonicalName()));
			handledDialogues.put("MonkOfEntrana", (Class<Dialogue>) Class
					.forName(MonkOfEntrana.class.getCanonicalName()));
			handledDialogues.put("AliMorrisane", (Class<Dialogue>) Class
					.forName(AliMorrisane.class.getCanonicalName()));
			handledDialogues.put("Faruq", (Class<Dialogue>) Class
					.forName(Faruq.class.getCanonicalName()));
			handledDialogues.put("Shanomi", (Class<Dialogue>) Class
					.forName(Shanomi.class.getCanonicalName()));
			handledDialogues.put("Lidio", (Class<Dialogue>) Class
					.forName(Lidio.class.getCanonicalName()));
			handledDialogues.put("Lilly", (Class<Dialogue>) Class
					.forName(Lilly.class.getCanonicalName()));
			handledDialogues.put("ShotputD", (Class<Dialogue>) Class
					.forName(ShotputD.class.getCanonicalName()));
			handledDialogues.put("HarlaakMenarous", (Class<Dialogue>) Class
					.forName(HarlaakMenarous.class.getCanonicalName()));
			handledDialogues.put("Ghommel", (Class<Dialogue>) Class
					.forName(Ghommel.class.getCanonicalName()));
			handledDialogues.put("Anton", (Class<Dialogue>) Class
					.forName(Anton.class.getCanonicalName()));
			handledDialogues.put("FatherAereck1", (Class<Dialogue>) Class
					.forName(FatherAereck1.class.getCanonicalName()));
			handledDialogues.put("FatherAereck2", (Class<Dialogue>) Class
					.forName(FatherAereck2.class.getCanonicalName()));
			handledDialogues.put("FatherUrhney", (Class<Dialogue>) Class
					.forName(FatherUrhney.class.getCanonicalName()));
			handledDialogues.put("Ghost", (Class<Dialogue>) Class
					.forName(Ghost.class.getCanonicalName()));
			handledDialogues.put("GhostFind", (Class<Dialogue>) Class
					.forName(GhostFind.class.getCanonicalName()));
			handledDialogues.put("GhostWo", (Class<Dialogue>) Class
					.forName(GhostWo.class.getCanonicalName()));
			handledDialogues.put("PolyPoncho", (Class<Dialogue>) Class
					.forName(PolyPoncho.class.getCanonicalName()));
			handledDialogues.put("PolyLeggings", (Class<Dialogue>) Class
					.forName(PolyLeggings.class.getCanonicalName()));
			handledDialogues.put("PolyVisor", (Class<Dialogue>) Class
					.forName(PolyVisor.class.getCanonicalName()));
			handledDialogues.put("BlueMoonInn", (Class<Dialogue>) Class
					.forName(BlueMoonInn.class.getCanonicalName()));
			handledDialogues.put("RisingSun2", (Class<Dialogue>) Class
					.forName(RisingSun2.class.getCanonicalName()));
			handledDialogues.put("BlurberrysBar", (Class<Dialogue>) Class
					.forName(BlurberrysBar.class.getCanonicalName()));
			handledDialogues.put("DeadMansChest", (Class<Dialogue>) Class
					.forName(DeadMansChest.class.getCanonicalName()));
			handledDialogues.put("QuickTaskD", (Class<Dialogue>) Class
					.forName(QuickTaskD.class.getCanonicalName()));
			handledDialogues.put("DragonInn", (Class<Dialogue>) Class
					.forName(DragonInn.class.getCanonicalName()));
			handledDialogues.put("FlyingHorseInn", (Class<Dialogue>) Class
					.forName(FlyingHorseInn.class.getCanonicalName()));
			handledDialogues.put("ForestersArms", (Class<Dialogue>) Class
					.forName(ForestersArms.class.getCanonicalName()));
			handledDialogues.put("JollyBoarInn", (Class<Dialogue>) Class
					.forName(JollyBoarInn.class.getCanonicalName()));
			handledDialogues.put("KaramjaSpiritsBar", (Class<Dialogue>) Class
					.forName(KaramjaSpiritsBar.class.getCanonicalName()));
			handledDialogues.put("SpiritTreeDialogue", (Class<Dialogue>) Class
					.forName(SpiritTreeDialogue.class.getCanonicalName()));
			handledDialogues.put("MainSpiritTreeDialogue", (Class<Dialogue>) Class
					.forName(MainSpiritTreeDialogue.class.getCanonicalName()));
			handledDialogues.put("RisingSunInn", (Class<Dialogue>) Class
					.forName(RisingSunInn.class.getCanonicalName()));
			handledDialogues.put("RustyAnchorBar", (Class<Dialogue>) Class
					.forName(RustyAnchorBar.class.getCanonicalName()));
			handledDialogues.put("EnteringDung", (Class<Dialogue>) Class
			.forName(EnteringDung.class.getCanonicalName()));
			handledDialogues.put("DungLeaving", (Class<Dialogue>) Class
					.forName(DungLeaving.class.getCanonicalName()));
			handledDialogues.put("TriviaRewards", (Class<Dialogue>) Class
					.forName(TriviaRewards.class.getCanonicalName()));
			handledDialogues.put("Player_Shop_Manager", (Class<Dialogue>) Class
					.forName(Player_Shop_Manager.class.getCanonicalName()));
			handledDialogues.put("Acanatha", (Class<Dialogue>) Class
					.forName(Acanatha.class.getCanonicalName()));
			handledDialogues.put("PotteryWheel", (Class<Dialogue>) Class
					.forName(PotteryWheel.class.getCanonicalName()));
			handledDialogues.put("PotteryFurnace", (Class<Dialogue>) Class
					.forName(PotteryFurnace.class.getCanonicalName()));
			handledDialogues.put("PrestigeOne", (Class<Dialogue>) Class
					.forName(PrestigeOne.class.getCanonicalName()));
			handledDialogues.put("Thok", (Class<Dialogue>) Class
					.forName(Thok.class.getCanonicalName()));
			handledDialogues.put("Nastroth", (Class<Dialogue>) Class
					.forName(Nastroth.class.getCanonicalName()));
			handledDialogues.put("Guardsman1", (Class<Dialogue>) Class
					.forName(Guardsman1.class.getCanonicalName()));
			handledDialogues.put("Guardsman2", (Class<Dialogue>) Class
					.forName(Guardsman2.class.getCanonicalName()));
			handledDialogues.put("Camel", (Class<Dialogue>) Class
					.forName(Camel.class.getCanonicalName()));
			handledDialogues.put("Stryke", (Class<Dialogue>) Class
					.forName(Stryke.class.getCanonicalName()));
			handledDialogues.put("DukeHoracio", (Class<Dialogue>) Class
					.forName(DukeHoracio.class.getCanonicalName()));
			handledDialogues.put("Ozan", (Class<Dialogue>) Class
					.forName(Ozan.class.getCanonicalName()));
			handledDialogues.put("StankyMorgan", (Class<Dialogue>) Class
					.forName(Ozan.class.getCanonicalName()));
			handledDialogues.put("MillieMiller", (Class<Dialogue>) Class
					.forName(MillieMiller.class.getCanonicalName()));
			handledDialogues.put("JimmyChisel", (Class<Dialogue>) Class
					.forName(JimmyChisel.class.getCanonicalName()));
			handledDialogues.put("Shantay", (Class<Dialogue>) Class
				     .forName(Shantay.class.getCanonicalName()));
			handledDialogues.put("Ham", (Class<Dialogue>) Class
				     .forName(Ham.class.getCanonicalName()));
			handledDialogues.put("Brian", (Class<Dialogue>) Class
				     .forName(Brian.class.getCanonicalName()));
			handledDialogues.put("Grum", (Class<Dialogue>) Class
				     .forName(Grum.class.getCanonicalName()));
			
			handledDialogues.put("Gerrant", (Class<Dialogue>) Class
				     .forName(Gerrant.class.getCanonicalName()));
			handledDialogues.put("Wydin", (Class<Dialogue>) Class
				     .forName(Wydin.class.getCanonicalName()));
			handledDialogues.put("Squire", (Class<Dialogue>) Class
					.forName(Squire.class.getCanonicalName()));
			handledDialogues.put("Capes", (Class<Dialogue>) Class
					.forName(Capes.class.getCanonicalName()));
			handledDialogues.put("StarterClass", (Class<Dialogue>) Class
					.forName(StarterClass.class.getCanonicalName()));
			handledDialogues.put("SkillsNeck", (Class<Dialogue>) Class
					.forName(SkillsNeck.class.getCanonicalName()));
			handledDialogues.put("Santa", (Class<Dialogue>) Class.forName(Santa1.class.getCanonicalName()));           
            Class<Dialogue> value55 = (Class<Dialogue>) Class.forName(FreakyForester.class.getCanonicalName());
            handledDialogues.put("FreakyForester", value55);
            handledDialogues.put("QuizMaster", value59);
			Class<Dialogue> value83 = (Class<Dialogue>) Class
					.forName(PumpkinPete.class.getCanonicalName());
			handledDialogues.put("PumpkinPete", value83);
			
			Class<Dialogue> value84 = (Class<Dialogue>) Class
					.forName(PumpkinPete2.class.getCanonicalName());
			handledDialogues.put("PumpkinPete2", value84);
			
			Class<Dialogue> value85 = (Class<Dialogue>) Class
					.forName(Zabeth.class.getCanonicalName());
			handledDialogues.put("Zabeth", value85);
			
			Class<Dialogue> value86 = (Class<Dialogue>) Class
					.forName(Zabeth2.class.getCanonicalName());
			handledDialogues.put("Zabeth2", value86);
			
			Class<Dialogue> value87 = (Class<Dialogue>) Class
					.forName(GrimReaper.class.getCanonicalName());
			handledDialogues.put("GrimReaper", value87);
			
			Class<Dialogue> value88 = (Class<Dialogue>) Class
					.forName(GrimReaper2.class.getCanonicalName());
			handledDialogues.put("GrimReaper2", value88);
			
			Class<Dialogue> value89 = (Class<Dialogue>) Class
					.forName(GrimReaper3.class.getCanonicalName());
			handledDialogues.put("GrimReaper3", value89);
			
			Class<Dialogue> value90 = (Class<Dialogue>) Class
					.forName(Zabeth3.class.getCanonicalName());
			handledDialogues.put("Zabeth3", value90);
			Class<Dialogue> value91 = (Class<Dialogue>) Class
					.forName(Lawgof.class.getCanonicalName());
			handledDialogues.put("Lawgof", value91);
			Class<Dialogue> value92 = (Class<Dialogue>) Class
					.forName(Nulodion.class.getCanonicalName());
			handledDialogues.put("Nulodion", value92);
			Class<Dialogue> value93 = (Class<Dialogue>) Class
					.forName(PickOne.class.getCanonicalName());
			handledDialogues.put("PickOne", value93);
			Class<Dialogue> value94 = (Class<Dialogue>) Class
					.forName(Quests.class.getCanonicalName());
			handledDialogues.put("Quests", value94);
			handledDialogues.put("RulesD", (Class<Dialogue>) Class.forName(RulesD.class.getCanonicalName()));
			handledDialogues.put("PrayerD", (Class<Dialogue>) Class.forName(PrayerD.class.getCanonicalName()));
			handledDialogues.put("clan_wars_view", (Class<Dialogue>) Class.forName(ClanWarsViewing.class.getCanonicalName()));
			handledDialogues.put("SummoningShop", (Class<Dialogue>) Class.forName(SummoningShop.class.getCanonicalName()));
			handledDialogues.put("HouseTeleport", (Class<Dialogue>) Class.forName(HouseTeleport.class.getCanonicalName()));
			handledDialogues.put("News", (Class<Dialogue>) Class.forName(News.class.getCanonicalName()));
			handledDialogues.put("ModPanel", (Class<Dialogue>) Class.forName(ModPanel.class.getCanonicalName()));
			handledDialogues.put("Animations", (Class<Dialogue>) Class.forName(Animations.class.getCanonicalName()));
			handledDialogues.put("DiceBag", (Class<Dialogue>) Class.forName(DiceBag.class.getCanonicalName()));
			handledDialogues.put("PartyPete", (Class<Dialogue>) Class.forName(PartyPete.class.getCanonicalName()));
			handledDialogues.put("PartyRoomLever", (Class<Dialogue>) Class.forName(PartyRoomLever.class.getCanonicalName()));
			handledDialogues.put("DrogoDwarf", (Class<Dialogue>) Class.forName(DrogoDwarf.class.getCanonicalName()));
			handledDialogues.put("GeneralStore", (Class<Dialogue>) Class.forName(GeneralStore.class.getCanonicalName()));
			handledDialogues.put("Nurmof", (Class<Dialogue>) Class.forName(Nurmof.class.getCanonicalName()));
			handledDialogues.put("BootDwarf", (Class<Dialogue>) Class.forName(BootDwarf.class.getCanonicalName()));
			handledDialogues.put("MiningGuildDwarf", (Class<Dialogue>) Class.forName(MiningGuildDwarf.class.getCanonicalName()));
			handledDialogues.put("Man", (Class<Dialogue>) Class.forName(Man.class.getCanonicalName()));
			handledDialogues.put("GuildMaster", (Class<Dialogue>) Class.forName(GuildMaster.class.getCanonicalName()));
			handledDialogues.put("Scavvo", (Class<Dialogue>) Class.forName(Scavvo.class.getCanonicalName()));	
			handledDialogues.put("Valaine", (Class<Dialogue>) Class.forName(Valaine.class.getCanonicalName()));	
			handledDialogues.put("Hura", (Class<Dialogue>) Class.forName(Hura.class.getCanonicalName()));
			handledDialogues.put("TzHaarMejJal", (Class<Dialogue>) Class.forName(TzHaarMejJal.class.getCanonicalName()));
			handledDialogues.put("TzHaarMejKah", (Class<Dialogue>) Class.forName(TzHaarMejKah.class.getCanonicalName()));
			handledDialogues.put("MasterOfFear", (Class<Dialogue>) Class.forName(MasterOfFear.class.getCanonicalName()));
			handledDialogues.put("TokHaarHok", (Class<Dialogue>) Class.forName(TokHaarHok.class.getCanonicalName()));
			handledDialogues.put("NomadThrone", (Class<Dialogue>) Class.forName(NomadThrone.class.getCanonicalName()));
			handledDialogues.put("SimplePlayerMessage", (Class<Dialogue>) Class.forName(SimplePlayerMessage.class.getCanonicalName()));
			handledDialogues.put("BonfireD", (Class<Dialogue>) Class.forName(BonfireD.class.getCanonicalName()));
			handledDialogues.put("FightKilnDialogue", (Class<Dialogue>) Class.forName(FightKilnDialogue.class.getCanonicalName()));
			handledDialogues.put("RewardChest", (Class<Dialogue>) Class.forName(RewardChest.class.getCanonicalName()));
			handledDialogues.put("WizardFinix", (Class<Dialogue>) Class.forName(WizardFinix.class.getCanonicalName()));	
			handledDialogues.put("RunespanPortalD", (Class<Dialogue>) Class.forName(RunespanPortalD.class.getCanonicalName()));
			handledDialogues.put("RuneSpanLeaving", (Class<Dialogue>) Class.forName(RuneSpanLeaving.class.getCanonicalName()));
			handledDialogues.put("SorceressGardenNPCs", (Class<Dialogue>) Class.forName(SorceressGardenNPCs.class.getCanonicalName()));
			handledDialogues.put("Marv", (Class<Dialogue>) Class.forName(Marv.class.getCanonicalName()));
			handledDialogues.put("FlamingSkull", (Class<Dialogue>) Class.forName(FlamingSkull.class.getCanonicalName()));
			handledDialogues.put("Hairdresser", (Class<Dialogue>) Class.forName(Hairdresser.class.getCanonicalName()));
			handledDialogues.put("Thessalia", (Class<Dialogue>) Class.forName(Thessalia.class.getCanonicalName()));
			handledDialogues.put("GrilleGoatsD", (Class<Dialogue>) Class.forName(GrilleGoatsDialogue.class.getCanonicalName()));
			handledDialogues.put("ZombieMonk", (Class<Dialogue>) Class.forName(ZombieMonk.class.getCanonicalName()));
			handledDialogues.put("ProteanBarD", (Class<Dialogue>) Class.forName(ProteanBarD.class.getCanonicalName()));
			handledDialogues.put("ProteanLogD", (Class<Dialogue>) Class.forName(ProteanLogD.class.getCanonicalName()));
			handledDialogues.put("CorruptedSmithingD", (Class<Dialogue>) Class.forName(CorruptedSmithingD.class.getCanonicalName()));
			handledDialogues.put("teleportWarningD", (Class<Dialogue>) Class.forName(teleportWarningD.class.getCanonicalName()));
			handledDialogues.put("Smuggler", (Class<Dialogue>) Class.forName(Smuggler.class.getCanonicalName()));
			/**Skillcape stores**/
			handledDialogues.put("Ajjat", (Class<Dialogue>) Class.forName(Ajjat.class.getCanonicalName()));
			handledDialogues.put("Sloane", (Class<Dialogue>) Class.forName(Sloane.class.getCanonicalName()));
			handledDialogues.put("MeleeInstructor", (Class<Dialogue>) Class.forName(MeleeInstructor.class.getCanonicalName()));
			handledDialogues.put("ArmourSalesman", (Class<Dialogue>) Class.forName(ArmourSalesman.class.getCanonicalName()));
			handledDialogues.put("BrotherJered", (Class<Dialogue>) Class.forName(BrotherJered.class.getCanonicalName()));
			handledDialogues.put("RobeStoreOwner", (Class<Dialogue>) Class.forName(RobeStoreOwner.class.getCanonicalName()));
			handledDialogues.put("Larriar", (Class<Dialogue>) Class.forName(Larriar.class.getCanonicalName()));
			handledDialogues.put("SurgeonGeneralTafani", (Class<Dialogue>) Class.forName(SurgeonGeneralTafani.class.getCanonicalName()));
			handledDialogues.put("CapnIzzyNoBeard", (Class<Dialogue>) Class.forName(CapnIzzyNoBeard.class.getCanonicalName()));
			handledDialogues.put("Kaqemeex", (Class<Dialogue>) Class.forName(Kaqemeex.class.getCanonicalName()));
			handledDialogues.put("MartinThwait", (Class<Dialogue>) Class.forName(MartinThwait.class.getCanonicalName()));
			handledDialogues.put("MasterCrafter", (Class<Dialogue>) Class.forName(MasterCrafter.class.getCanonicalName()));
			handledDialogues.put("Hickton", (Class<Dialogue>) Class.forName(Hickton.class.getCanonicalName()));
			handledDialogues.put("HuntingExpert", (Class<Dialogue>) Class.forName(HuntingExpert.class.getCanonicalName()));
			handledDialogues.put("Dwarf", (Class<Dialogue>) Class.forName(Dwarf.class.getCanonicalName()));
			handledDialogues.put("Thurgo", (Class<Dialogue>) Class.forName(Thurgo.class.getCanonicalName()));
			handledDialogues.put("MasterFisher", (Class<Dialogue>) Class.forName(MasterFisher.class.getCanonicalName()));
			handledDialogues.put("HeadChef", (Class<Dialogue>) Class.forName(HeadChef.class.getCanonicalName()));
			handledDialogues.put("IgnatiusVulcan", (Class<Dialogue>) Class.forName(IgnatiusVulcan.class.getCanonicalName()));
			handledDialogues.put("Wilfred", (Class<Dialogue>) Class.forName(Wilfred.class.getCanonicalName()));
			handledDialogues.put("MartintheMasterGardener", (Class<Dialogue>) Class.forName(MartintheMasterGardener.class.getCanonicalName()));
			handledDialogues.put("Pikkupstix", (Class<Dialogue>) Class.forName(Pikkupstix.class.getCanonicalName()));
			handledDialogues.put("Thok", (Class<Dialogue>) Class.forName(Thok.class.getCanonicalName()));
			handledDialogues.put("NewPlayerTutorial", (Class<Dialogue>) Class.forName(NewPlayerTutorial.class.getCanonicalName()));
			handledDialogues.put("HerbSeeds", (Class<Dialogue>) Class.forName(HerbSeeds.class.getCanonicalName()));
			handledDialogues.put("SowQuestStart", (Class<Dialogue>) Class.forName(SowQuestStart.class.getCanonicalName()));
			handledDialogues.put("Ozan", (Class<Dialogue>) Class.forName(Ozan.class.getCanonicalName()));
			handledDialogues.put("GrotDungeonAgility", (Class<Dialogue>) Class.forName(GrotDungeonAgility.class.getCanonicalName()));
			handledDialogues.put("Repair", (Class<Dialogue>) Class.forName(Repair.class.getCanonicalName()));
			handledDialogues.put("Looks", (Class<Dialogue>) Class.forName(Looks.class.getCanonicalName()));
              handledDialogues.put("Kuradel", (Class<Dialogue>) Class.forName(Kuradel.class.getCanonicalName()));
              handledDialogues.put("SummonSlayerportal", (Class<Dialogue>) Class.forName(SummonSlayerportal.class.getCanonicalName()));
              handledDialogues.put("KingBlackDragonDia", (Class<Dialogue>) Class.forName(KingBlackDragonDia.class.getCanonicalName()));
              handledDialogues.put("InstancedDungeonDialogue", (Class<Dialogue>) Class.forName(InstancedDungeonDialogue.class.getCanonicalName()));
			  	handledDialogues.put("DonatorTab", (Class<Dialogue>) Class.forName(DonatorTab.class.getCanonicalName()));
			handledDialogues.put("InfoPanel", (Class<Dialogue>) Class.forName(InfoPanel.class.getCanonicalName()));
				handledDialogues.put("AdminPanel", (Class<Dialogue>) Class.forName(AdminPanel.class.getCanonicalName()));
			handledDialogues.put("LootBeam", (Class<Dialogue>) Class.forName(LootBeam.class.getCanonicalName()));
			handledDialogues.put("WildyManager", (Class<Dialogue>) Class.forName(WildyManager.class.getCanonicalName()));
			handledDialogues.put("Achievements", (Class<Dialogue>) Class.forName(Achievements.class.getCanonicalName()));
			handledDialogues.put("PriceChecker", (Class<Dialogue>) Class.forName(PriceChecker.class.getCanonicalName()));
			handledDialogues.put("PlayerManager", (Class<Dialogue>) Class.forName(PlayerManager.class.getCanonicalName()));
			handledDialogues.put("NormalMode", (Class<Dialogue>) Class.forName(NormalMode.class.getCanonicalName()));
			handledDialogues.put("SkillerMode", (Class<Dialogue>) Class.forName(SkillerMode.class.getCanonicalName()));
			handledDialogues.put("IronMan", (Class<Dialogue>) Class.forName(IronMan.class.getCanonicalName()));
			handledDialogues.put("TicketHelp", (Class<Dialogue>) Class.forName(TicketHelp.class.getCanonicalName()));
			handledDialogues.put("KeepSakeBoxD", (Class<Dialogue>) Class.forName(KeepSakeBoxD.class.getCanonicalName()));
			handledDialogues.put("ExpLamps", (Class<Dialogue>) Class.forName(ExpLamps.class.getCanonicalName()));
			handledDialogues.put("HeartTokens", (Class<Dialogue>) Class.forName(HeartTokens.class.getCanonicalName()));
			handledDialogues.put("SlayerExp", (Class<Dialogue>) Class.forName(SlayerExp.class.getCanonicalName()));
			handledDialogues.put("SlayerShopD", (Class<Dialogue>) Class.forName(SlayerShopD.class.getCanonicalName()));
			handledDialogues.put("SlayerDropsD", (Class<Dialogue>) Class.forName(SlayerDropsD.class.getCanonicalName()));
			handledDialogues.put("SlayerTabsD", (Class<Dialogue>) Class.forName(SlayerTabsD.class.getCanonicalName()));
			handledDialogues.put("SlayerHelmCraftD", (Class<Dialogue>) Class.forName(SlayerHelmCraftD.class.getCanonicalName()));
			handledDialogues.put("SlayerRing", (Class<Dialogue>) Class.forName(SlayerRing.class.getCanonicalName()));
			handledDialogues.put("SlayerhelmUpgradeD", (Class<Dialogue>) Class.forName(SlayerhelmUpgradeD.class.getCanonicalName()));
			handledDialogues.put("SlayerToolbeltOptiosD", (Class<Dialogue>) Class.forName(SlayerToolbeltOptiosD.class.getCanonicalName()));
			handledDialogues.put("VoidSet", (Class<Dialogue>) Class.forName(VoidSet.class.getCanonicalName()));
			handledDialogues.put("VoteStore", (Class<Dialogue>) Class.forName(VoteStore.class.getCanonicalName()));
			handledDialogues.put("EventHost", (Class<Dialogue>) Class.forName(EventHost.class.getCanonicalName()));
			handledDialogues.put("ServerManager", (Class<Dialogue>) Class.forName(ServerManager.class.getCanonicalName()));
			handledDialogues.put("CompCape", (Class<Dialogue>) Class.forName(CompCape.class.getCanonicalName()));
			handledDialogues.put("SkillerCape", (Class<Dialogue>) Class.forName(SkillerCape.class.getCanonicalName()));
			handledDialogues.put("HerbStore", (Class<Dialogue>) Class.forName(HerbStore.class.getCanonicalName()));
			handledDialogues.put("SkillingStore", (Class<Dialogue>) Class.forName(SkillingStore.class.getCanonicalName()));
			handledDialogues.put("PowerCrystal", (Class<Dialogue>) Class.forName(PowerCrystal.class.getCanonicalName()));
			handledDialogues.put("TheMaxer", (Class<Dialogue>) Class.forName(TheMaxer.class.getCanonicalName()));
			handledDialogues.put("TrainingTeleports", (Class<Dialogue>) Class.forName(TrainingTeleports.class.getCanonicalName()));
			handledDialogues.put("PkingTeleports", (Class<Dialogue>) Class.forName(PkingTeleports.class.getCanonicalName()));
			handledDialogues.put("BossTeleports", (Class<Dialogue>) Class.forName(BossTeleports.class.getCanonicalName()));
			handledDialogues.put("SkillingTeleports", (Class<Dialogue>) Class.forName(SkillingTeleports.class.getCanonicalName()));
			handledDialogues.put("MinigameTeleports", (Class<Dialogue>) Class.forName(MinigameTeleports.class.getCanonicalName()));
			handledDialogues.put("HomeTour", (Class<Dialogue>) Class.forName(HomeTour.class.getCanonicalName()));
			handledDialogues.put("SkillerMaster", (Class<Dialogue>) Class.forName(SkillerMaster.class.getCanonicalName())); 
            handledDialogues.put("homeJunglPortal", (Class<Dialogue>) Class.forName(homeJunglPortal.class.getCanonicalName()));
			handledDialogues.put("PrestigePortal", (Class<Dialogue>) Class.forName(PrestigePortal.class.getCanonicalName()));
			handledDialogues.put("TournamentsD", (Class<Dialogue>) Class.forName(TournamentsD.class.getCanonicalName()));
			handledDialogues.put("SearchItem", (Class<Dialogue>) Class.forName(SearchItem.class.getCanonicalName()));
			handledDialogues.put("PvmMasterQuest", (Class<Dialogue>) Class.forName(PvmMasterQuest.class.getCanonicalName()));
			handledDialogues.put("PvmMasterQuest2", (Class<Dialogue>) Class.forName(PvmMasterQuest2.class.getCanonicalName()));
		    handledDialogues.put("PvmMasterQuest3", (Class<Dialogue>) Class.forName(PvmMasterQuest3.class.getCanonicalName()));
			handledDialogues.put("PerkManagerD", (Class<Dialogue>) Class.forName(PerkManagerD.class.getCanonicalName()));
			handledDialogues.put("PerkOptions", (Class<Dialogue>) Class.forName(PerkOptions.class.getCanonicalName()));
			handledDialogues.put("pvmInformation", (Class<Dialogue>) Class.forName(pvmInformation.class.getCanonicalName()));
			handledDialogues.put("BossCavesD", (Class<Dialogue>) Class.forName(BossCavesD.class.getCanonicalName()));
			handledDialogues.put("PirateIntro", (Class<Dialogue>) Class.forName(PirateIntro.class.getCanonicalName()));
			handledDialogues.put("PirateDoor", (Class<Dialogue>) Class.forName(PirateDoor.class.getCanonicalName()));
			handledDialogues.put("PirateQuest", (Class<Dialogue>) Class.forName(PirateQuest.class.getCanonicalName()));
			handledDialogues.put("DropValue", (Class<Dialogue>) Class.forName(DropValue.class.getCanonicalName()));
			handledDialogues.put("PreHestoricalDoor", (Class<Dialogue>) Class.forName(PreHestoricalDoor.class.getCanonicalName()));
			handledDialogues.put("EmptyConfirm", (Class<Dialogue>) Class.forName(EmptyConfirm.class.getCanonicalName()));
			handledDialogues.put("PetInfo", (Class<Dialogue>) Class.forName(PetInfo.class.getCanonicalName()));
			handledDialogues.put("GlassblowingD", (Class<Dialogue>) Class.forName(GlassBlowingD.class.getCanonicalName())); 
			handledDialogues.put("DragonBoneUpgradeKiteInfoD", (Class<Dialogue>) Class.forName(DragonBoneUpgradeKiteInfoD.class.getCanonicalName()));
			handledDialogues.put("PortalChamberD", (Class<Dialogue>) Class.forName(PortalChamberD.class.getCanonicalName()));
			handledDialogues.put("BotManagerD", (Class<Dialogue>) Class.forName(BotManagerD.class.getCanonicalName()));
			handledDialogues.put("InstanceD", (Class<Dialogue>) Class.forName(InstanceD.class.getCanonicalName()));
			handledDialogues.put("RobustGlassMachineD", (Class<Dialogue>) Class.forName(RobustGlassMachineD.class.getCanonicalName()));
			handledDialogues.put("CrystalRobustGlassMachineD", (Class<Dialogue>) Class.forName(CrystalRobustGlassMachineD.class.getCanonicalName()));
			handledDialogues.put("SpectatePanel", (Class<Dialogue>) Class.forName(SpectatePanel.class.getCanonicalName()));
			handledDialogues.put("Particles", (Class<Dialogue>) Class.forName(Particles.class.getCanonicalName()));
			handledDialogues.put("ProteanItem", (Class<Dialogue>) Class.forName(ProteanItem.class.getCanonicalName()));
			handledDialogues.put("CharmingImp", (Class<Dialogue>) Class.forName(CharmingImp.class.getCanonicalName()));
			handledDialogues.put("AgilityD", (Class<Dialogue>) Class.forName(AgilityD.class.getCanonicalName()));
			handledDialogues.put("FishingD", (Class<Dialogue>) Class.forName(FishingD.class.getCanonicalName()));
			handledDialogues.put("CraftingD", (Class<Dialogue>) Class.forName(CraftingD.class.getCanonicalName()));
			handledDialogues.put("ThievingD", (Class<Dialogue>) Class.forName(ThievingD.class.getCanonicalName()));
			handledDialogues.put("RunecraftingD", (Class<Dialogue>) Class.forName(RunecraftingD.class.getCanonicalName()));
			handledDialogues.put("HunterD", (Class<Dialogue>) Class.forName(HunterD.class.getCanonicalName()));
			handledDialogues.put("FarmingD", (Class<Dialogue>) Class.forName(FarmingD.class.getCanonicalName()));
			handledDialogues.put("SmithingD", (Class<Dialogue>) Class.forName(SmithingD.class.getCanonicalName()));
			handledDialogues.put("MiningD", (Class<Dialogue>) Class.forName(MiningD.class.getCanonicalName()));
			handledDialogues.put("WoodcuttingD", (Class<Dialogue>) Class.forName(WoodcuttingD.class.getCanonicalName()));
			handledDialogues.put("SeediciderD", (Class<Dialogue>) Class.forName(SeediciderD.class.getCanonicalName()));
			handledDialogues.put("HiddenTrapdoorD", (Class<Dialogue>) Class.forName(HiddenTrapdoorD.class.getCanonicalName()));
			handledDialogues.put("HiddenTrapdoorD2", (Class<Dialogue>) Class.forName(HiddenTrapdoorD2.class.getCanonicalName()));
			handledDialogues.put("CremationD", (Class<Dialogue>) Class.forName(CremationD.class.getCanonicalName()));
			handledDialogues.put("LoyaltyShopD", (Class<Dialogue>) Class.forName(LoyaltyShopD.class.getCanonicalName()));
			handledDialogues.put("CommonQuestions", (Class<Dialogue>) Class.forName(CommonQuestions.class.getCanonicalName()));
			handledDialogues.put("HomeTeleportD", (Class<Dialogue>) Class.forName(HomeTeleportD.class.getCanonicalName()));
			handledDialogues.put("MemberShipD", (Class<Dialogue>) Class.forName(MemberShipD.class.getCanonicalName()));
			//boss pets
			handledDialogues.put("GeneralAwwdorD", (Class<Dialogue>) Class.forName(GeneralAwwdorD.class.getCanonicalName()));
			handledDialogues.put("EllieD", (Class<Dialogue>) Class.forName(EllieD.class.getCanonicalName()));
			handledDialogues.put("NexterminatorD", (Class<Dialogue>) Class.forName(NexterminatorD.class.getCanonicalName()));
			handledDialogues.put("KingBlackDragonlingD", (Class<Dialogue>) Class.forName(KingBlackDragonlingD.class.getCanonicalName()));
			handledDialogues.put("KrilTinyrothD", (Class<Dialogue>) Class.forName(KrilTinyrothD.class.getCanonicalName()));
			handledDialogues.put("ChickarraD", (Class<Dialogue>) Class.forName(ChickarraD.class.getCanonicalName()));
			handledDialogues.put("CorporealPuppyD", (Class<Dialogue>) Class.forName(CorporealPuppyD.class.getCanonicalName()));
			//dung
			handledDialogues.put("dungentrance", (Class<Dialogue>) Class.forName(dungentrance.class.getCanonicalName()));
			handledDialogues.put("DungRunecraftingD", (Class<Dialogue>) Class.forName(DungRunecraftingD.class.getCanonicalName()));
			//urns
			handledDialogues.put("PotteryWheelOption", (Class<Dialogue>) Class.forName(PotteryWheelOption.class.getCanonicalName()));
			handledDialogues.put("PotteryWheelFishing", (Class<Dialogue>) Class.forName(PotteryWheelFishing.class.getCanonicalName()));
		   //xd
		   handledDialogues.put("BobBarterD", (Class<Dialogue>) Class.forName(BobBarterD.class.getCanonicalName()));
		   handledDialogues.put("SmithingHelper", (Class<Dialogue>) Class.forName(SmithingHelper.class.getCanonicalName()));
		   //pp 
		   handledDialogues.put("LeavePyramidD", (Class<Dialogue>) Class.forName(LeavePyramidD.class.getCanonicalName()));
		   //server intro
		   handledDialogues.put("ServerIntro", (Class<Dialogue>) Class.forName(ServerIntro.class.getCanonicalName()));
		   handledDialogues.put("ServerIntro2", (Class<Dialogue>) Class.forName(ServerIntro2.class.getCanonicalName()));
		   handledDialogues.put("ServerIntro3", (Class<Dialogue>) Class.forName(ServerIntro3.class.getCanonicalName()));
		   //zariax
		   handledDialogues.put("ZariaD", (Class<Dialogue>) Class.forName(ZariaD.class.getCanonicalName()));
		   //donator
		   handledDialogues.put("DonatorClueD", (Class<Dialogue>) Class.forName(DonatorClueD.class.getCanonicalName()));
		   handledDialogues.put("RuneDragonD", (Class<Dialogue>) Class.forName(RuneDragonD.class.getCanonicalName()));
		   handledDialogues.put("DonationTokenManager", (Class<Dialogue>) Class.forName(DonationTokenManager.class.getCanonicalName()));
		   //recipe
		   handledDialogues.put("SuperSaradominD", (Class<Dialogue>) Class.forName(SuperSaradominD.class.getCanonicalName()));
		   handledDialogues.put("BossLootBoxesD", (Class<Dialogue>) Class.forName(BossLootBoxesD.class.getCanonicalName()));
		   //presets
		   handledDialogues.put("PresetsName", (Class<Dialogue>) Class.forName(PresetsName.class.getCanonicalName()));
		   handledDialogues.put("PresetD", (Class<Dialogue>) Class.forName(PresetD.class.getCanonicalName()));
		   handledDialogues.put("PresetQuickLoad", (Class<Dialogue>) Class.forName(PresetQuickLoad.class.getCanonicalName()));
		   //oldschool shit xdDDD 
		   handledDialogues.put("OldSchoolD", (Class<Dialogue>) Class.forName(OldSchoolD.class.getCanonicalName()));
		   //skillcape shop
		   handledDialogues.put("SkillcapeShop", (Class<Dialogue>) Class.forName(SkillcapeShop.class.getCanonicalName()));
		    handledDialogues.put("BuyMastercapeD", (Class<Dialogue>) Class.forName(BuyMastercapeD.class.getCanonicalName()));
		   //zaria quests
		   handledDialogues.put("StartQuest", (Class<Dialogue>) Class.forName(StartQuest.class.getCanonicalName()));
		   handledDialogues.put("PortalFound", (Class<Dialogue>) Class.forName(PortalFound.class.getCanonicalName()));
		   handledDialogues.put("AncientRock", (Class<Dialogue>) Class.forName(AncientRock.class.getCanonicalName()));
		   handledDialogues.put("ZariaIsland", (Class<Dialogue>) Class.forName(ZariaIsland.class.getCanonicalName()));
		   handledDialogues.put("ZariaPlace", (Class<Dialogue>) Class.forName(ZariaPlace.class.getCanonicalName()));
		    handledDialogues.put("EndPortal", (Class<Dialogue>) Class.forName(EndPortal.class.getCanonicalName()));
			 handledDialogues.put("EndQuest", (Class<Dialogue>) Class.forName(EndQuest.class.getCanonicalName()));
			  handledDialogues.put("SolomonD", (Class<Dialogue>) Class.forName(SolomonD.class.getCanonicalName()));
		   //runite dragon shit
		    handledDialogues.put("RuniteDragonWarning", (Class<Dialogue>) Class.forName(RuniteDragonWarning.class.getCanonicalName()));
			//cosmeticsD
			 handledDialogues.put("CosmeticsD", (Class<Dialogue>) Class.forName(CosmeticsD.class.getCanonicalName()));
			  handledDialogues.put("ContractDialogue", (Class<Dialogue>) Class.forName(ContractDialogue.class.getCanonicalName()));
			 
			  handledDialogues.put("GrimGem", (Class<Dialogue>) Class.forName(GrimGem.class.getCanonicalName()));
			  //exp
			   handledDialogues.put("ExpToken", (Class<Dialogue>) Class.forName(ExpToken.class.getCanonicalName()));
			    handledDialogues.put("EliteDungeonD", (Class<Dialogue>) Class.forName(EliteDungeonD.class.getCanonicalName()));
			  //crafting
			  handledDialogues.put("TectonicEnergyCraftingD", (Class<Dialogue>) Class.forName(TectonicEnergyCraftingD.class.getCanonicalName()));
			  handledDialogues.put("SirenicScaleCraftingD", (Class<Dialogue>) Class.forName(SirenicScaleCraftingD.class.getCanonicalName()));
			  handledDialogues.put("MalevolentCraftingD", (Class<Dialogue>) Class.forName(MalevolentCraftingD.class.getCanonicalName()));
			  handledDialogues.put("CombinationPotionsD", (Class<Dialogue>) Class.forName(CombinationPotionsD.class.getCanonicalName()));
			   handledDialogues.put("PirateHomeD", (Class<Dialogue>) Class.forName(PirateHomeD.class.getCanonicalName()));
			     handledDialogues.put("RecipeOptions", (Class<Dialogue>) Class.forName(RecipeOptions.class.getCanonicalName()));
			     handledDialogues.put("DksOption", (Class<Dialogue>) Class.forName(DksOption.class.getCanonicalName()));
			     handledDialogues.put("WarbandsRewards", (Class<Dialogue>) Class.forName(WarbandsRewards.class.getCanonicalName()));
		   } catch (Throwable e) {
			Logger.handle(e);
			Logger.handle(e); 
		}
	}

	public static final void reload() {
		handledDialogues.clear();
		init();
	}

	public static final Dialogue getDialogue(Object key) {
		if (key instanceof Dialogue)
			return (Dialogue) key;
		Class<Dialogue> classD = handledDialogues.get(key);
		if (classD == null)
			return null;
		try {
			return classD.newInstance();
		} catch (Throwable e) {
			Logger.handle(e);
		}
		return null;
	}

	private DialogueHandler() {

	}
}
