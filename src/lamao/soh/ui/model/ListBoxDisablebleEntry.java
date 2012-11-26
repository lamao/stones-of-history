/** 
 * ListBoxDisablebleEntry.java 26.11.2012
 * 
 * Copyright 2012 Stones of History
 * All rights reserved. 
 */
package lamao.soh.ui.model;

/**
 * NIfty ListBox model class. Can be enabled or disabled. 
 * @author lamao
 *
 */
public class ListBoxDisablebleEntry<T>
{
	private T content;
	
	private boolean enabled;

	public ListBoxDisablebleEntry(T content, boolean enabled)
	{
		super();
		this.content = content;
		this.enabled = enabled;
	}

	public T getContent()
	{
		return content;
	}

	public void setContent(T content)
	{
		this.content = content;
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString()
	{
		return content == null ? null : content.toString(); 
	}
}
