/** 
 * SHProfilesScreenController.java 20.11.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.ui.controllers;

import java.io.IOException;
import java.util.List;

import lamao.soh.core.SHBreakoutGameContext;
import lamao.soh.core.model.entity.SHUser;
import lamao.soh.core.service.SHUserService;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyEventSubscriber;
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
	private SHUserService userService;
	
	private SHBreakoutGameContext gameContext;
	
	private Element addProfilesWindow;
	
	private SHUser selectedUser = null;
	
	public SHProfilesScreenController(SHUserService userService,
			SHBreakoutGameContext gameContext)
	{
		this.userService = userService;
		this.gameContext = gameContext;
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
	
	@SuppressWarnings("unchecked")
	public void addProfile() throws IOException
	{
		TextField profileNameField = getScreen().findNiftyControl("profileName", 
				TextField.class);
		
		String profileName = profileNameField.getText();
		
		if (!profileName.isEmpty() && !userService.isExists(profileName))
		{
    		SHUser newUser = new SHUser();
    		newUser.setName(profileName);
    		
    		userService.save(newUser);
    		
    		ListBox<SHUser> listBox = (ListBox<SHUser>)getScreen().findNiftyControl("listProfiles", ListBox.class);
    		listBox.addItem(newUser);
    		
    		hideAddProfilesWindow();
		}
		
	}
	
	@NiftyEventSubscriber(id="listProfiles")
	public void onMyListBoxSelectionChanged(final String id, 
			final ListBoxSelectionChangedEvent<SHUser> event) {
	    List<SHUser> selection = event.getSelection();
	    
	    if (selection.isEmpty())
	    {
	    	selectedUser = null;
	    }
	    else
	    {
	    	selectedUser = selection.get(0);
	    }
	}
	
	@SuppressWarnings("unchecked")
	public void deleteProfile()
	{
		if (selectedUser != null && selectedUser != gameContext.getPlayer())
		{
			userService.delete(selectedUser.getName());
			ListBox<SHUser> listBox = (ListBox<SHUser>)getScreen().findNiftyControl("listProfiles", ListBox.class);
			listBox.removeItem(selectedUser);
			selectedUser = null;
		}
	}
	
	public void selectProfile()
	{
		if (selectedUser != null)
		{
			gameContext.setPlayer(selectedUser);
			gotoScreen("start");
		}
	}
	
}
