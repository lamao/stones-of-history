/** 
 * SHBasicScreenController.java 21.11.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.ui.controllers;

import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.controls.label.builder.LabelBuilder;
import de.lessvoid.nifty.controls.window.WindowControl;
import de.lessvoid.nifty.controls.window.builder.WindowBuilder;
import de.lessvoid.nifty.screen.Screen;
import de.lessvoid.nifty.screen.ScreenController;

/**
 * Basic screen controller for nifty controller
 * @author lamao
 *
 */
public class SHBasicScreenController implements ScreenController
{
	private int nextWindowId = 0;
	
	private Screen screen;
	
	private Nifty nifty;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void bind(Nifty nifty, Screen screen)
	{
		this.nifty = nifty;
		this.screen = screen;
	}
	
	public Screen getScreen()
	{
		return screen;
	}

	public void setScreen(Screen screen)
	{
		this.screen = screen;
	}

	public Nifty getNifty()
	{
		return nifty;
	}

	public void setNifty(Nifty nifty)
	{
		this.nifty = nifty;
	}

	public void closeInfoWindow(String windowId)
	{
		WindowControl infoWindow = screen.findNiftyControl(windowId, 
				WindowControl.class);
		infoWindow.closeWindow();
	}
	
	/**
	 * Create and show window for informational message. 
	 * @param title - title of window
	 * @param text - textual message
	 */
	public void showInfoWindow(final String title, final String text)
	{
		showInfoWindow(title, text, "closeInfoWindow");
	}
	
	/**
	 * Create and show window for informational message
	 * @param title - title of window
	 * @param text - textual message
	 * @param okHandler - handler for pressing OK button (e.g. closeInfoWindow
	 * 		for default handler - closing window). It must contain single 
	 * 		parameter - ID of window being clicked at.
	 */
	public void showInfoWindow(final String title, final String text, 
			final String okHandler) 
	{
		final String windowId = "_infoWindow" + nextWindowId++;
		
		new LayerBuilder("_infoWindowLayer"){{
			childLayoutAbsolute();
			width("100%");
			height("100%");
			control(new WindowBuilder(windowId, title) {{
				visibleToMouse(true);
				closeable(false);
				width("50%"); // windows will need a size
				height("150px");
				x("25%");
				y("35%");
				panel(new PanelBuilder("_rootPanel") {{
					height("100%");
					width("100%");
					alignCenter();
					childLayoutVertical();
					color("#00000000");
					control(new LabelBuilder("_infoText") {{
						text(text);
						style("base-font");
						color("#eeef");
						valignCenter();
						width("100%");
						height("75%");
					}});
					control(new ButtonBuilder("_btnOk") {{
						name("button");
						label("OK");
						alignCenter();
						width("50%");
						height("25%");
						interactOnClick(okHandler + "(" + windowId + ")");
					}});
				}});
			}});
		}}.build(nifty, screen, screen.getRootElement());
		
		screen.layoutLayers();
	}
	
	public void gotoScreen(String screenId)
	{
		nifty.gotoScreen(screenId);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onStartScreen()
	{
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onEndScreen()
	{
	}

}
