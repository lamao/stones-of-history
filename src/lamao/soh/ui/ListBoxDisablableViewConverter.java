/** 
 * ListBoxDisablableViewConverter.java 26.11.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.ui;

import lamao.soh.ui.model.ListBoxDisablebleEntry;
import de.lessvoid.nifty.controls.ListBox.ListBoxViewConverter;
import de.lessvoid.nifty.elements.Element;
import de.lessvoid.nifty.elements.render.TextRenderer;

/**
 * @author lamao
 *
 */
public class ListBoxDisablableViewConverter implements ListBoxViewConverter<ListBoxDisablebleEntry<?>>
{
	
	private static final String LIST_ITEM_DISABLED_STYLE = "listitem-disabled";

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void display(Element element, ListBoxDisablebleEntry<?> model)
	{
		TextRenderer renderer = element.getRenderer(TextRenderer.class);
		if (!model.isEnabled())
		{
			element.setStyle(LIST_ITEM_DISABLED_STYLE);
		}
		renderer.setText(model.toString());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getWidth(Element element, ListBoxDisablebleEntry<?> model)
	{
		TextRenderer renderer = element.getRenderer(TextRenderer.class);
		
		int width = (renderer.getFont() == null) 
				? 0 
				: renderer.getFont().getWidth(model.toString());
		
		return width;
	}
	

}
