/**
 * SHEpochsScreenController.java 22.11.2012
 *
 * Copyright 2012 Stones of History
 * All rights reserved.
 */
package lamao.soh.ui.controllers;

import java.util.List;
import java.util.Set;

import com.jme3.app.state.AppStateManager;
import lamao.soh.core.SHBreakoutGameContext;
import lamao.soh.core.model.SHEpochLevelItem;
import lamao.soh.core.model.entity.SHEpoch;
import lamao.soh.core.model.entity.SHLevel;
import lamao.soh.core.service.SHEpochService;
import lamao.soh.core.service.StateService;
import lamao.soh.states.LoadLevelState;
import lamao.soh.states.SHNiftyState;
import lamao.soh.ui.model.ListBoxDisablebleEntry;

import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.Button;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.controls.ListBox;
import de.lessvoid.nifty.controls.ListBoxSelectionChangedEvent;

/**
 * Controller for screen with epochs/levels.
 * @author lamao
 *
 */
public class SHEpochsScreenController extends SHBasicScreenController
{
	private StateService stateService;

	private SHBreakoutGameContext gameContext;

	private SHEpochService epochService;

	private SHEpoch selectedEpoch;

	private SHLevel selectedLevel;

	/**
	 *
	 */
	public SHEpochsScreenController(
            StateService stateService,
			SHBreakoutGameContext gameContext,
			SHEpochService epochService)
	{
		this.stateService = stateService;
		this.gameContext = gameContext;
		this.epochService = epochService;
	}

	public SHEpoch getSelectedEpoch()
	{
		return selectedEpoch;
	}

	public SHLevel getSelectedLevel()
	{
		return selectedLevel;
	}

	/**
	 * Start playing selected level
	 */
	public void startGame()
	{
        LoadLevelState loadLevelState = stateService.get(LoadLevelState.class);
        loadLevelState.setEpoch(selectedEpoch);
        loadLevelState.setLevel(selectedLevel);

        stateService.detach(SHNiftyState.class);
        stateService.attach(LoadLevelState.class);
	}

	@SuppressWarnings("unchecked")
	private void fillEpochLists()
	{
		ListBox<ListBoxDisablebleEntry<SHEpoch>> epochList = (ListBox<ListBoxDisablebleEntry<SHEpoch>>)
				getScreen().findNiftyControl("epochsList", ListBox.class);
		epochList.clear();
		List<SHEpoch> allEpochs = epochService.getAll();

		SHEpochLevelItem firstUncompleted = epochService.getFirstUncompletedEpoch(
				allEpochs, gameContext.getPlayer().getCompletedLevels());

		boolean enabled = true;
		for (SHEpoch epoch : allEpochs)
		{
			epochList.addItem(new ListBoxDisablebleEntry<SHEpoch>(epoch, enabled));
			if (firstUncompleted != null
				&& epoch == firstUncompleted.getEpoch())
			{
				enabled = false;
			}
		}
		if (firstUncompleted == null)
		{
			selectEpoch(allEpochs.get(allEpochs.size() - 1));
		}
		else
		{
			selectEpoch(firstUncompleted.getEpoch());
		}
	}

	@SuppressWarnings("unchecked")
	private void fillLevelLists(SHEpoch epoch)
	{
		ListBox<ListBoxDisablebleEntry<SHLevel>> levelsList = (ListBox<ListBoxDisablebleEntry<SHLevel>>)
				getScreen().findNiftyControl("levelsList", ListBox.class);
		levelsList.clear();

		List<SHLevel> levels = epoch.getLevels();
		Set<String> completedLevels = gameContext.getPlayer()
				.getCompletedLevels().get(epoch.getId());

		SHLevel firstUncompleted = epochService.getFirstUncompletedLevel(
				levels, completedLevels);

		boolean enabled = true;
		for (SHLevel level : levels)
		{
			levelsList.addItem(new ListBoxDisablebleEntry<SHLevel>(level, enabled));
			if (firstUncompleted != null && level == firstUncompleted)
			{
				enabled = false;
			}
		}

		if (firstUncompleted == null)
		{
			firstUncompleted = levels.get(levels.size() - 1);
		}
		selectLevel(firstUncompleted);
	}

	@NiftyEventSubscriber(id="epochsList")
	public void onEpochsListBoxSelectionChanged(final String id,
			final ListBoxSelectionChangedEvent<ListBoxDisablebleEntry<SHEpoch>> event) {
	    List<ListBoxDisablebleEntry<SHEpoch>> selection = event.getSelection();

	    ListBoxDisablebleEntry<SHEpoch> selectedItem = selection.isEmpty() ? null : selection.get(0);

	    if (selectedItem != null && !selectedItem.isEnabled())
	    {
	    	return;
	    }
	    else
	    {
        	SHEpoch selectedEpoch = selectedItem == null ? null : selectedItem.getContent();
        	selectEpoch(selectedEpoch);
	    }
	}

	@NiftyEventSubscriber(id="levelsList")
	public void onLevelsListBoxSelectionChanged(final String id,
			final ListBoxSelectionChangedEvent<ListBoxDisablebleEntry<SHLevel>> event) {
	    List<ListBoxDisablebleEntry<SHLevel>> selection = event.getSelection();

	    ListBoxDisablebleEntry<SHLevel> selectedItem = selection.isEmpty() ? null : selection.get(0);

	    if (selectedItem != null && !selectedItem.isEnabled())
	    {
	    	return;
	    }
	    else
	    {
	    	SHLevel selectedLevel = selectedItem == null ? null : selectedItem.getContent();
	    	selectLevel(selectedLevel);
	    }
	}

	/**
	 * Called internally to process onEpochSelected actions. Updates play button
	 * state, changes content of list with levels and selects first uncompleted
	 * level.
	 * @param epoch epoch being selected
	 */
	private void selectEpoch(SHEpoch epoch)
	{
		selectedEpoch = epoch;
    	updatePlayButtonState();

	    if (selectedEpoch == null)
	    {
	    	selectLevel(null);
	    }
	    else
	    {
	    	fillLevelLists(epoch);
	    }
	}

	/**
	 * Called internally to process onLevelSelected action. Updates play button
	 * state and changes level details in corresponding window
	 * @param level level being selected
	 */
	private void selectLevel(SHLevel level)
	{
		selectedLevel = level;
	    updatePlayButtonState();

	    Label detailsTitleLabel = getScreen().findNiftyControl(
    			"detailsTitleLabel", Label.class);
    	Label detailsContentLabel = getScreen().findNiftyControl(
    			"detailsContentLabel", Label.class);

	    if (selectedLevel == null)
	    {
	    	detailsTitleLabel.setText("");
	    	detailsContentLabel.setText("");
	    }
	    else
	    {
	    	detailsTitleLabel.setText(selectedLevel.getName());
	    	detailsContentLabel.setText(selectedLevel.getIntro());
	    }
	}

	private void updatePlayButtonState()
	{
		Button playButton = getScreen().findNiftyControl("btnPlay", Button.class);

		playButton.setEnabled(selectedEpoch != null && selectedLevel != null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onStartScreen()
	{
		fillEpochLists();
		updatePlayButtonState();
	}

}
