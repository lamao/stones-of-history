/** 
 * SHProfilesScreenController.java 20.11.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.ui.controllers;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import lamao.soh.core.SHBreakoutGameContext;
import lamao.soh.core.model.entity.SHUser;
import lamao.soh.core.service.SHSessionService;
import lamao.soh.core.service.SHUserService;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
import de.lessvoid.nifty.controls.Button;
import de.lessvoid.nifty.controls.ListBox;
import de.lessvoid.nifty.controls.ListBoxSelectionChangedEvent;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.screen.Screen;

/**
 * Controller for profiles panel
 * @author lamao
 *
 */
public class SHProfilesScreenController extends SHBasicScreenController
{
	private static final String PROFILE_EXISTS_TITLE = "Profile exists";
	
	private static final String PROFILE_EXISTS_TEXT = "Profile <%s> already exists";
	
	private static final String SAVE_PROFILE_ERROR_TITLE = "Profile can't be saved";
	
	private static final String SAVE_PROFILE_ERROR_TEXT = "Some errors were during saving profile";
	
	private static final String DELETE_YOURSELF_TITLE = "Can't delete your profile";
	
	private static final String DELETE_YOURSELF_TEXT = "You are trying to delete currently selected profile";
	
	private static final String DELETE_PROFILE_ERROR_TITLE = "Profile can't be deleted";
	
	private static final String DELETE_PROFILE_ERROR_TEXT = "Some errrors were during deleting profile";
	
	private static Logger LOGGER = Logger.getLogger(SHProfilesScreenController.class.getCanonicalName());
	
	private SHUserService userService;
	
	private SHBreakoutGameContext gameContext;
	
	private SHSessionService sessionService;
	
	private Element addProfilesWindow;
	
	private SHUser selectedUser = null;
	
	public SHProfilesScreenController(SHUserService userService,
			SHBreakoutGameContext gameContext,
			SHSessionService sessionService)
	{
		this.userService = userService;
		this.gameContext = gameContext;
		this.sessionService = sessionService;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void bind(Nifty nifty, Screen screen)
	{
		super.bind(nifty, screen);
		
		addProfilesWindow = screen.findElementByName("addProfiles");
		
		fillListProfiles();
	}
	
	@SuppressWarnings("unchecked")
	private void fillListProfiles()
	{
		ListBox<SHUser> listBox = (ListBox<SHUser>)getScreen().findNiftyControl("listProfiles", ListBox.class);
		
		List<SHUser> users = userService.getAll();
		listBox.addAllItems(users);
	}
	
	public void hideAddProfilesWindow()
	{
		addProfilesWindow.setVisible(false);
	}
	
	public void showAddProfilesWindow()
	{
		addProfilesWindow.setVisible(true);
	}
	
	/**
	 * Create new profile with name from text input and save it.
	 */
	@SuppressWarnings("unchecked")
	public void addProfile()
	{
		try
		{
    		TextField profileNameField = getScreen().findNiftyControl("profileName", 
    				TextField.class);
    		
    		String profileName = profileNameField.getText();
    		
    		if (profileName.isEmpty())
    		{
    			return;
    		} 
    		else if (userService.isExists(profileName))
    		{
    			showInfoWindow(PROFILE_EXISTS_TITLE, 
    					String.format(PROFILE_EXISTS_TEXT, profileName));
    		}
    		else
    		{
        		SHUser newUser = new SHUser();
        		newUser.setName(profileName);
        		
        		userService.save(newUser);
        		
        		ListBox<SHUser> listBox = (ListBox<SHUser>)getScreen().findNiftyControl("listProfiles", ListBox.class);
        		listBox.addItem(newUser);
        		
        		hideAddProfilesWindow();
        		
        		actualSelectProfile(newUser);
    		}
		}
		catch (IOException e)
		{
			showInfoWindow(SAVE_PROFILE_ERROR_TITLE, SAVE_PROFILE_ERROR_TEXT);
			LOGGER.log(Level.WARNING, e.getMessage(), e);
		}
		
	}
	
	@NiftyEventSubscriber(id="listProfiles")
	public void onMyListBoxSelectionChanged(final String id, 
			final ListBoxSelectionChangedEvent<SHUser> event) {
	    List<SHUser> selection = event.getSelection();
	    
	    if (selection.isEmpty())
	    {
	    	unselectProfile();
	    }
	    else
	    {
	    	selectProfile(selection.get(0));
	    }
	}
	
	@SuppressWarnings("unchecked")
	public void deleteProfile()
	{
		if (selectedUser == gameContext.getPlayer())
		{
			showInfoWindow(DELETE_YOURSELF_TITLE, DELETE_YOURSELF_TEXT);
		}
		else
		{
			boolean wasDeleted = userService.delete(selectedUser.getName());
			if (!wasDeleted)
			{
				showInfoWindow(DELETE_PROFILE_ERROR_TITLE, DELETE_PROFILE_ERROR_TEXT);
			}
			else
			{
				ListBox<SHUser> listBox = (ListBox<SHUser>)getScreen().findNiftyControl("listProfiles", ListBox.class);
				listBox.removeItem(selectedUser);
				unselectProfile();
			}
		}
	}
	
	/**
	 * Applying selection to game context and return to main screen
	 */
	public void selectProfile()
	{
		if (selectedUser != null)
		{
			actualSelectProfile(selectedUser);
		}
	}
	
	/**
	 * Do 'select profile' logic. 
	 * @param profile profile to select. Must be not null
	 */
	private void actualSelectProfile(SHUser profile)
	{
		gameContext.setPlayer(profile);
		sessionService.getSessionInfo().setLastSelectedUsername(profile.getName());
		sessionService.saveSessionInfo();
		gotoScreen("start");
	}
	
	/**
	 * Sets currently selected player to null (both in this window and globally
	 * for game context) and disables corresponding controls.
	 */
	private void unselectProfile()
	{
		selectedUser = null;
		gameContext.setPlayer(null);
		
		Button selectButton = getScreen().findNiftyControl("btnSelect", Button.class);
		selectButton.disable();
		
		Button deleteButton = getScreen().findNiftyControl("btnDelete", Button.class);
		deleteButton.disable();
	}
	
	/**
	 * Saves selected profiles for this window and enables corresponding controls. 
	 * @param profile profile was selected
	 */
	private void selectProfile(SHUser profile)
	{
		selectedUser = profile;
		
		Button selectButton = getScreen().findNiftyControl("btnSelect", Button.class);
		selectButton.enable();
		
		Button deleteButton = getScreen().findNiftyControl("btnDelete", Button.class);
		deleteButton.enable();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onStartScreen()
	{
		if (selectedUser == null)
		{
			unselectProfile();
		}
	}
	
}
