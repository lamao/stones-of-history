/** 
 * SHEpochsScreenController.java 22.11.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.ui.controllers;

import java.util.List;

import lamao.soh.core.SHBreakoutGameContext;
import lamao.soh.core.model.entity.SHEpoch;
import lamao.soh.core.model.entity.SHLevel;
import lamao.soh.core.service.SHEpochService;
import lamao.soh.states.SHLevelState;
import lamao.soh.states.SHNiftyState;

import com.jmex.game.state.GameState;
import com.jmex.game.state.GameStateManager;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.controls.ListBox;
import de.lessvoid.nifty.controls.ListBoxSelectionChangedEvent;
import de.lessvoid.nifty.screen.Screen;

/**
 * Controller for screen with epochs/levels.
 * @author lamao
 *
 */
public class SHEpochsScreenController extends SHBasicScreenController
{
	private GameStateManager manager;
	
	private SHBreakoutGameContext gameContext;
	
	private SHEpochService epochService;
	
	private SHEpoch selectedEpoch;
	
	private SHLevel selectedLevel;
	
	/**
	 * 
	 */
	public SHEpochsScreenController(GameStateManager manager,
			SHBreakoutGameContext gameContext,
			SHEpochService epochService)
	{
		this.manager = manager;
		this.gameContext = gameContext;
		this.epochService = epochService;
	}
	
	/**
	 * Start playing selected level
	 */
	public void startGame() 
	{
		GameState niftyState = manager.getChild(SHNiftyState.NAME); 
		GameState levelState = manager.getChild(SHLevelState.NAME);
		
		niftyState.setActive(false);
		levelState.setActive(true);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void bind(Nifty nifty, Screen screen)
	{
		super.bind(nifty, screen);
		
		fillEpochLists();
	}
	
	@SuppressWarnings("unchecked")
	private void fillEpochLists()
	{
		ListBox<SHEpoch> epochList = (ListBox<SHEpoch>)getScreen()
				.findNiftyControl("epochsList", ListBox.class);
		List<SHEpoch> allEpochs = epochService.getAll();
		
		epochList.addAllItems(allEpochs);
	}
	
	@SuppressWarnings("unchecked")
	@NiftyEventSubscriber(id="epochsList")
	public void onEpochsListBoxSelectionChanged(final String id, 
			final ListBoxSelectionChangedEvent<SHEpoch> event) {
	    List<SHEpoch> selection = event.getSelection();
    	selectedEpoch = selection.isEmpty() ? null : selection.get(0);
	    
	    if (selectedEpoch != null)
	    {
	    	ListBox<SHLevel> levelList = (ListBox<SHLevel>)getScreen()
					.findNiftyControl("levelsList", ListBox.class);
	    	levelList.clear();
	    	levelList.addAllItems(selectedEpoch.getLevels());
	    }
	}
	
	@NiftyEventSubscriber(id="levelsList")
	public void onLevelsListBoxSelectionChanged(final String id, 
			final ListBoxSelectionChangedEvent<SHLevel> event) {
	    List<SHLevel> selection = event.getSelection();
	    selectedLevel = selection.isEmpty() ? null : selection.get(0);
	    
	    if (selectedLevel != null)
	    {
	    	SHLevel selectedLevel = selection.get(0);
	    	Label detailsTitleLabel = getScreen().findNiftyControl(
	    			"detailsTitleLabel", Label.class);
	    	Label detailsContentLabel = getScreen().findNiftyControl(
	    			"detailsContentLabel", Label.class);
	    	
	    	detailsTitleLabel.setText(selectedLevel.getName());
	    	detailsContentLabel.setText(selectedLevel.getIntro());
	    }
	}
	
}
