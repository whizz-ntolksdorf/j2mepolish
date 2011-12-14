//#condition polish.usePolishGui
// generated by de.enough.doc2java.Doc2Java (www.enough.de) on Sat Dec 06 15:06:43 CET 2003
/*
 * Copyright (c) 2004-2005 Robert Virkus / Enough Software
 *
 * This file is part of J2ME Polish.
 *
 * J2ME Polish is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * J2ME Polish is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with J2ME Polish; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * 
 * Commercial licenses are also available, please
 * refer to the accompanying LICENSE.txt or visit
 * http://www.j2mepolish.org for details.
 */
package de.enough.polish.ui;

import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

import de.enough.polish.util.RgbImage;
import de.enough.polish.util.TextUtil;
import de.enough.polish.util.WrappedText;

/**
 * An item that can contain a string.
 * 
 * A <code>StringItem</code> is display-only; the user
 * cannot edit the contents. Both the label and the textual content of a
 * <code>StringItem</code> may be modified by the application. The
 * visual representation
 * of the label may differ from that of the textual contents.
 * 
 * @author Robert Virkus, robert@enough.de
 */
public class StringItem extends Item
{
	private static final int DIRECTION_BACK_AND_FORTH = 0;
	private static final int DIRECTION_LEFT = 1;
	private static final int DIRECTION_RIGHT = 2;
	protected String text;
	protected final WrappedText textLines;
	protected int textColor;
	protected Font font;
	//#ifdef polish.css.text-wrap
		protected boolean useSingleLine;
		protected boolean clipText;
		protected int xOffset;
		protected int textWidth;
		protected boolean isHorizontalAnimationDirectionRight;
		protected boolean animateTextWrap = true;
		protected int availableTextWidth;
		//#ifdef polish.css.text-wrap-animation-direction
			protected int textWrapDirection = DIRECTION_BACK_AND_FORTH;
		//#endif
		//#ifdef polish.css.text-wrap-animation-speed
			protected int textWrapSpeed = 1;
		//#endif
	//#endif
	//#ifdef polish.css.text-horizontal-adjustment
		protected int textHorizontalAdjustment;
	//#endif
	//#ifdef polish.css.text-vertical-adjustment
		protected int textVerticalAdjustment;
	//#endif
	//#if polish.css.text-effect || polish.css.font-bitmap
		//#define tmp.useTextEffect
		protected TextEffect textEffect;
	//#endif
	//#ifdef polish.css.max-lines
		protected int maxLines = TextUtil.MAXLINES_UNLIMITED;
		protected String maxLinesAppendix = TextUtil.MAXLINES_APPENDIX;
		protected int maxLinesAppendixPosition = TextUtil.MAXLINES_APPENDIX_POSITION_AFTER;
	//#endif
	//#if polish.css.text-layout
		protected int textLayout;
	//#endif
	//#if polish.css.text-visible
		private boolean isTextVisible = true;
	//#endif
	protected boolean isTextInitializationRequired;
	private int lastAvailableContentWidth;
	private int lastContentWidth;
	private int lastContentHeight;
	//#if polish.css.text-filter && polish.midp2
		//#define tmp.useTextFilter
		private RgbFilter[] textFilters;
		private boolean isTextFiltersActive;
		private RgbImage textFilterRgbImage;
		private RgbImage textFilterProcessedRgbImage;
		private RgbFilter[] originalTextFilters;
		private int textFilterLayout;
	//#endif
	//#if polish.css.ignore-leading
	private boolean ignoreLeading;
	//#endif
	
	/**
	 * Creates a new <code>StringItem</code> object.  Calling this
	 * constructor is equivalent to calling
	 * 
	 * <pre><code>
	 * StringItem(label, text, Item.PLAIN, null);     
	 * </code></pre>
	 * 
	 * @param label the Item label
	 * @param text the text contents
	 * @see #StringItem(String, String, int, Style)
	 */
	public StringItem( String label, String text)
	{
		this( label, text, PLAIN );
	}

	/**
	 * Creates a new <code>StringItem</code> object.  Calling this
	 * constructor is equivalent to calling
	 * 
	 * <pre><code>
	 * StringItem(label, text, Item.PLAIN, style);     
	 * </code></pre>
	 * 
	 * @param label the Item label
	 * @param text the text contents
	 * @param style the style
	 * @see #StringItem(String, String, int, Style)
	 */
	public StringItem( String label, String text, Style style )
	{
		this( label, text, PLAIN, style );
	}
	
	
	/**
	 * Creates a new <code>StringItem</code> object with the given label,
	 * textual content, and appearance mode.
	 * Either label or text may be present or <code>null</code>.
	 * 
	 * <p>The <code>appearanceMode</code> parameter
	 * (see <a href="Item.html#appearance">Appearance Modes</a>)
	 * is a hint to the platform of the application's intended use
	 * for this <code>StringItem</code>.  To provide hyperlink- or
	 * button-like behavior,
	 * the application should associate a default <code>Command</code> with this
	 * <code>StringItem</code> and add an
	 * <code>ItemCommandListener</code> to this
	 * <code>StringItem</code>.
	 * 
	 * <p>Here is an example showing the use of a
	 * <code>StringItem</code> as a button: </p>
	 * <pre><code>
	 * StringItem strItem = new StringItem("Default: ", "Set", Item.BUTTON);
	 * strItem.setDefaultCommand(
	 * new Command("Set", Command.ITEM, 1);
	 * // icl is ItemCommandListener
	 * strItem.setItemCommandListener(icl);     
	 * </code></pre>
	 * 
	 * @param label the StringItem's label, or null if no label
	 * @param text the StringItem's text contents, or null if the contents are initially empty
	 * @param appearanceMode the appearance mode of the StringItem, one of Item.PLAIN, Item.HYPERLINK, or Item.BUTTON
	 * @throws IllegalArgumentException if appearanceMode invalid
	 * @since  MIDP 2.0
	 */
	public StringItem( String label, String text, int appearanceMode)
	{
		this( label, text, appearanceMode, null );
	}

	/**
	 * Creates a new <code>StringItem</code> object with the given label,
	 * textual content, and appearance mode.
	 * Either label or text may be present or <code>null</code>.
	 * 
	 * <p>The <code>appearanceMode</code> parameter
	 * (see <a href="Item.html#appearance">Appearance Modes</a>)
	 * is a hint to the platform of the application's intended use
	 * for this <code>StringItem</code>.  To provide hyperlink- or
	 * button-like behavior,
	 * the application should associate a default <code>Command</code> with this
	 * <code>StringItem</code> and add an
	 * <code>ItemCommandListener</code> to this
	 * <code>StringItem</code>.
	 * 
	 * <p>Here is an example showing the use of a
	 * <code>StringItem</code> as a button: </p>
	 * <pre><code>
	 * StringItem strItem = new StringItem("Default: ", "Set", Item.BUTTON);
	 * strItem.setDefaultCommand(
	 * new Command("Set", Command.ITEM, 1);
	 * // icl is ItemCommandListener
	 * strItem.setItemCommandListener(icl);     
	 * </code></pre>
	 * 
	 * @param label the StringItem's label, or null if no label
	 * @param text the StringItem's text contents, or null if the contents are initially empty
	 * @param appearanceMode the appearance mode of the StringItem, one of Item.PLAIN, Item.HYPERLINK, or Item.BUTTON
	 * @param style the style for this item
	 * @throws IllegalArgumentException if appearanceMode invalid
	 * @since  MIDP 2.0
	 */
	public StringItem( String label, String text, int appearanceMode, Style style )
	{
		super( label, LAYOUT_DEFAULT, appearanceMode, style );
		this.text = text;
		this.textLines = new WrappedText();
	}
	
	
	
	//#if tmp.useTextEffect || polish.css.text-wrap
	/* (non-Javadoc)
	 * @see de.enough.polish.ui.Item#animate(long, de.enough.polish.ui.ClippingRegion)
	 */
	public void animate(long currentTime, ClippingRegion repaintRegion)
	{
		super.animate(currentTime, repaintRegion);
		//#if polish.css.text-wrap
			if (this.animateTextWrap) {
				if (this.useSingleLine && this.clipText) {
					int speed = 1;
					//#ifdef polish.css.text-wrap-animation-speed
						speed = this.textWrapSpeed;
					//#endif
					//#ifdef polish.css.text-wrap-animation-direction
						if (this.textWrapDirection == 0) {
					//#endif
							if (this.isHorizontalAnimationDirectionRight) {
								this.xOffset += speed;
								if (this.xOffset >= 0) {
									this.isHorizontalAnimationDirectionRight = false;
								}
							} else {
								this.xOffset -= speed;
								if (this.xOffset + this.textWidth < this.availableTextWidth) {
									this.isHorizontalAnimationDirectionRight = true;
								}
							}
					//#ifdef polish.css.text-wrap-animation-direction
						} else if (this.textWrapDirection == DIRECTION_LEFT) {
							int offset = this.xOffset - speed;
							if (offset + this.textWidth < 0) {
								offset = this.availableTextWidth;
							}
							this.xOffset = offset;
						} else if (this.textWrapDirection == DIRECTION_RIGHT) { // direction is right:
							int offset = this.xOffset + speed;
							if (offset > this.availableTextWidth) {
								offset = -this.textWidth;
							}
							this.xOffset = offset;							
						} 
					//#endif

					addRelativeToContentRegion(repaintRegion, 0, 0, this.contentWidth, this.contentHeight );
				}
			}
		//#endif
		//#if tmp.useTextEffect
			if (this.textEffect != null) {
				this.textEffect.animate( this, currentTime, repaintRegion );
			}
		//#endif
	}
	//#endif

	//#if tmp.useTextFilter
	/* (non-Javadoc)
	 * @see de.enough.polish.ui.Item#addRepaintArea(de.enough.polish.ui.ClippingRegion)
	 */
	public void addRepaintArea(ClippingRegion repaintRegion) {
		super.addRepaintArea(repaintRegion);
		RgbImage img = this.textFilterProcessedRgbImage;
		if (img != null && (img.getHeight() > this.lastContentHeight || img.getWidth() > this.lastContentWidth)) {
			int lo;
			//#if polish.css.text-filter-layout
				lo = this.textFilterLayout;
			//#elif polish.css.text-layout
				lo = this.textLayout;
			//#else
				lo = this.layout;
			//#endif
			int w = img.getWidth();
			int h = img.getHeight();
			int horDiff = w - this.lastContentWidth;
			int verDiff = h - this.lastContentHeight;
			w += this.contentWidth - this.lastContentWidth;
			h += this.contentHeight - this.lastContentHeight;
			int absX = getAbsoluteX();
			int absY = getAbsoluteY();
			if ((lo & LAYOUT_CENTER) == LAYOUT_CENTER) {
				absX -= horDiff / 2;
			} else if ((lo & LAYOUT_CENTER) == LAYOUT_RIGHT) {
				absX -= horDiff;
			}
			if ((lo & LAYOUT_VCENTER) == LAYOUT_VCENTER) {
				absY -= verDiff / 2; 
			} else if ((lo & LAYOUT_VCENTER) == LAYOUT_TOP) {
				absY -= verDiff; 
			}
			repaintRegion.addRegion( absX, absY, w, h );
		}
	}
	//#endif
	
	//#if polish.css.text-wrap
	/* (non-Javadoc)
	 * @see de.enough.polish.ui.Item#defocus(de.enough.polish.ui.Style)
	 */
	protected void defocus(Style originalStyle) {
		super.defocus(originalStyle);
		if (this.clipText) {
			this.xOffset = 0;
		}
	}	
	//#endif


	//#ifdef tmp.useTextEffect
	/* (non-Javadoc)
	 * @see de.enough.polish.ui.Item#hideNotify()
	 */
	protected void hideNotify() {
		if (this.textEffect != null) {
			this.textEffect.onDetach(this);
			this.textEffect.hideNotify();
		}
		super.hideNotify();
	}
	//#endif

	//#ifdef tmp.useTextEffect
	/* (non-Javadoc)
	 * @see de.enough.polish.ui.Item#showNotify()
	 */
	protected void showNotify() {
		if (this.textEffect != null) {
			this.textEffect.onAttach(this);
			this.textEffect.showNotify();
		}
		super.showNotify();
	}
	//#endif

	
	/**
	 * Gets the text contents of the <code>StringItem</code>, or
	 * <code>null</code> if the <code>StringItem</code> is
	 * empty.
	 * 
	 * @return a string with the content of the item
	 * @see #setText(java.lang.String)
	 */
	public String getText()
	{
		return this.text;
	}

	/**
	 * Sets the text contents of the <code>StringItem</code>. 
	 * If text
	 * is <code>null</code>,
	 * the <code>StringItem</code>
	 * is set to be empty.
	 * 
	 * @param text the new content
	 * @see #getText()
	 */
	public void setText( String text)
	{
		setText( text, null );
	}
	
	/**
	 * Sets the text contents of the <code>StringItem</code> along with a style. 
	 * If text is <code>null</code>,
	 * the <code>StringItem</code>
	 * is set to be empty.
	 * 
	 * @param text the new content
	 * @param style the new style, is ignored when null
	 * @see #getText()
	 */
	public void setText( String text, Style style)
	{
		//#debug
		System.out.println("StringItem: setText( \"" + text + "\" )");
		if ( style != null ) {
			setStyle( style );
		}
		if (text != this.text) {
			this.text = text;
			if (text == null) {
				this.textLines.clear();
			}
			this.isTextInitializationRequired = true;
			requestInit();
		}
		notifyValueChanged(text);
	}

	//#if tmp.useTextEffect
	/**
	 * Sets the effect for this StringItem
	 * Note that this method is only available when either using
	 * font-bitmap or the text-effect CSS attributes:
	 * <pre>
	 * 	//#if polish.css.font-bitmap || polish.css.text-effect
	 * </pre>
	 * 
	 * @param effect the new text effect, use null for removing the current text effect
	 */
	public void setTextEffect(TextEffect effect)
	{
		if (effect != this.textEffect) {
			this.textEffect = effect;
			this.isTextInitializationRequired = true;
		}
	}
	//#endif
	
	/**
	 * Sets the text color for contents of the <code>StringItem</code>.
	 *
	 * @param color the new color for the content
	 */
	public void setTextColor( int color)
	{
		this.textColor = color;
	}

	/**
	 * Sets the application's preferred font for
	 * rendering this <code>StringItem</code>.
	 * The font is a hint, and the implementation may disregard
	 * the application's preferred font.
	 * 
	 * <p> The <code>font</code> parameter must be a valid <code>Font</code>
	 * object or <code>null</code>. If the <code>font</code> parameter is
	 * <code>null</code>, the implementation must use its default font
	 * to render the <code>StringItem</code>.</p>
	 * 
	 * @param font - the preferred font to use to render this StringItem
	 * @see #getFont()
	 * @since  MIDP 2.0
	 */
	public void setFont( Font font)
	{
		if (font == null || !font.equals(this.font)) {
			this.font = font;
			setInitialized(false);
		}
	}

	/**
	 * Gets the application's preferred font for
	 * rendering this <code>StringItem</code>. The
	 * value returned is the font that had been set by the application,
	 * even if that value had been disregarded by the implementation.
	 * If no font had been set by the application, or if the application
	 * explicitly set the font to <code>null</code>, the value is the default
	 * font chosen by the implementation.
	 * 
	 * @return the preferred font to use to render this StringItem
	 * @see #setFont(javax.microedition.lcdui.Font)
	 * @since  MIDP 2.0
	 */
	public Font getFont()
	{
		if (this.font == null) {
			if (this.style != null) {
				this.font = this.style.getFont();
			}
			if (this.font == null) {
				this.font = Font.getDefaultFont();
			}
		}
		return this.font;
	}

	/* (non-Javadoc)
	 * @see de.enough.polish.ui.Item#paintContent(int, int, javax.microedition.lcdui.Graphics)
	 */
	public void paintContent(int x, int y, int leftBorder, int rightBorder, Graphics g) {
		//#debug
		System.out.println("painting at left=" + leftBorder + ", right=" + rightBorder + ", x=" + x + ", y=" + y + ", text=" + this.text);
		//#if polish.css.text-visible
			if (!this.isTextVisible) {
				return;
			}
		//#endif
		//#if tmp.useTextFilter
			if (this.isTextFiltersActive && this.textFilters != null) {
				RgbImage rgbImage = this.textFilterRgbImage;
				if ( rgbImage == null) {
					int w = this.lastContentWidth;
					int h = this.lastContentHeight;
					Image image = Image.createImage( w, h );
					int transparentColor = 0x12345678;
					Graphics imgG = image.getGraphics();
					imgG.setColor(transparentColor);
					imgG.fillRect(0, 0, w+1, h+1 );
					int[] transparentColorRgb = new int[1];
					image.getRGB(transparentColorRgb, 0, 1, 0, 0, 1, 1 );
					transparentColor = transparentColorRgb[0];
					paintText( 0, 0, 0, w, imgG );
					int[] textRgbData = new int[ w*h ];
					image.getRGB(textRgbData, 0, w, 0, 0, w, h );
					// ensure transparent parts are indeed transparent
					for (int i = 0; i < textRgbData.length; i++) {
						if( textRgbData[i] == transparentColor ) {
							textRgbData[i] = 0;
						}
					}
					rgbImage = new RgbImage(textRgbData, w);
					this.textFilterRgbImage = rgbImage;
				} 
				int lo;
				//#if polish.css.text-filter-layout
					lo = this.textFilterLayout;
				//#elif polish.css.text-layout
					lo = this.textLayout;
				//#else
					lo = this.layout;
				//#endif
				this.textFilterProcessedRgbImage = paintFilter( x, y, this.textFilters, rgbImage, lo, g );
			} else {
				paintText(x, y, leftBorder, rightBorder, g);
			}
		//#endif		
	//#if tmp.useTextFilter
	}
	private void paintText( int x, int y, int leftBorder, int rightBorder, Graphics g) {
	//#endif
		WrappedText lines = this.textLines;
		if (lines.size() == 0) {
			return;
		}
		
		//#if polish.css.text-wrap
			int clipX = 0;
			int clipY = 0;
			int clipWidth = 0;
			int clipHeight = 0;
			if (this.useSingleLine && this.clipText ) {
				clipX = g.getClipX();
				clipY = g.getClipY();
				clipWidth = g.getClipWidth();
				clipHeight = g.getClipHeight();
				g.clipRect( x, y, this.availableTextWidth, this.contentHeight );
			}
		//#endif
		//#ifdef polish.css.text-vertical-adjustment
			y += this.textVerticalAdjustment;
		//#endif
			
		//#if polish.Bugs.needsBottomOrientiationForStringDrawing
			y += getFontHeight();
		//#endif

		g.setFont( this.font );
		g.setColor( this.textColor );
		
		int lineHeight = getLineHeight();
		//#ifdef polish.css.text-horizontal-adjustment
			x += this.textHorizontalAdjustment;
			leftBorder += this.textHorizontalAdjustment;
			rightBorder += this.textHorizontalAdjustment;
		//#endif

		//#if tmp.useTextEffect
			if (this.textEffect != null) {
				//#if polish.css.text-wrap
					if (this.useSingleLine && this.clipText ) {
						x += this.xOffset;
					}
				//#endif
				int lo;
				//#if polish.css.text-layout
					lo = this.textLayout & ~Item.LAYOUT_VCENTER;
				//#else
					lo = this.layout & ~Item.LAYOUT_VCENTER;
				//#endif
				//#if polish.Bugs.needsBottomOrientiationForStringDrawing
					lo |= Item.LAYOUT_BOTTOM;
				//#else
					lo |= Item.LAYOUT_TOP;
				//#endif
				this.textEffect.drawStrings( this, lines, this.textColor, x, y, leftBorder, rightBorder, lineHeight, this.contentWidth, lo, g );
			} else {
		//#endif
				int centerX = 0;
				boolean isCenter;
				boolean isRight;
				boolean isExpand;
				//#if polish.css.text-layout
					int lo = this.textLayout;
					isCenter = (lo & LAYOUT_CENTER) == LAYOUT_CENTER;
					isRight = (lo & LAYOUT_CENTER) == LAYOUT_RIGHT;
					isExpand = (lo & LAYOUT_EXPAND) == LAYOUT_EXPAND;
				//#else
					isCenter = this.isLayoutCenter;
					isRight = this.isLayoutRight;
				//#endif
				
				if (isCenter) {
					//#if polish.css.text-layout && polish.text-layout.parentCenter
					if(isExpand && this.parent != null) {
						int parentLeft = this.parent.getAbsoluteX() + this.parent.getContentX(); 
						int parentRight = parentLeft + this.parent.getContentWidth();
						centerX = parentLeft + (parentRight - parentLeft) / 2;
						//#ifdef polish.css.text-horizontal-adjustment
						centerX += this.textHorizontalAdjustment;
						//#endif
					}
					else
					//#endif
					{
						centerX = leftBorder + (rightBorder - leftBorder) / 2;
						//#ifdef polish.css.text-horizontal-adjustment
						centerX += this.textHorizontalAdjustment;
						//#endif
					}
				}
				int lineX = x;
				int lineY = y;
				
				//#if polish.css.ignore-leading && polish.blackberry
				//# if(this.ignoreLeading) {
				//# 	lineY -= this.font.getLeading();
				//# }
				//#endif
				
				int orientation = Graphics.LEFT;
				// adjust the painting according to the layout:
//				if (isRight) {
//					lineX = rightBorder; //x + this.backgroundWidth - this.paddingLeft - this.paddingRight; // rightBorder can be influenced by sub classes such as IconItem, the rest not.
//					orientation = Graphics.RIGHT;
//				} else if (isCenter) {
//					lineX = centerX;
//					orientation = Graphics.HCENTER;
//				} else {
//					orientation = Graphics.LEFT;
//				}
				
				//#if polish.css.text-wrap
					if (this.clipText) {
						// when clipping (and therefore a scrolling animation) is needed,
						// center and right layouts don't really make sense - this would
						// start and stop the scrolling at wrong places outside of the clipping area: 
						orientation = Graphics.LEFT;
						lineX = x + this.xOffset;
						isCenter = false;
						isRight = false;
					}
				//#endif
				//#if polish.Bugs.needsBottomOrientiationForStringDrawing
					orientation = Graphics.BOTTOM | orientation;
				//#else
					orientation = Graphics.TOP | orientation;
				//#endif
				int startIndex = 0;
				int endIndex = lines.size();
				if (endIndex > 2) {
					int clippingY = g.getClipY();
					int clippingHeight = g.getClipHeight();
					if (lineY + endIndex * lineHeight > clippingY + clippingHeight) {
						endIndex -= ((lineY + endIndex * lineHeight) - (clippingY + clippingHeight)) / lineHeight;
					}
					if (lineY < clippingY) {
						startIndex = (clippingY - lineY) / lineHeight;
						lineY += startIndex * lineHeight;
						if (startIndex > 0) {
							lineX = leftBorder;
							if ( this.useSingleRow && this.label != null) {
								lineX -= this.label.itemWidth;
							}
						}
					}
					//#if polish.Bugs.needsBottomOrientiationForStringDrawing
						endIndex++;
					//#endif
					if (endIndex > lines.size()) {
						endIndex = lines.size();
					}
				}
				//int usedContentWidth = rightBorder - leftBorder;
				int lineWidth;
				Object[] lineObjects = lines.getLinesInternalArray();
				for (int i = startIndex; i < endIndex; i++) {
					String line = (String) lineObjects[i];
					if (isCenter || isRight) {
						lineWidth = lines.getLineWidth(i);
						if (isCenter) {
							lineX = centerX - (lineWidth/2);
						} else {
							lineX = rightBorder - lineWidth; // lineX + usedContentWidth - lineWidth; (the outcommented calculation results in misbehaving right aligment)
						}
					}
					drawString( line, lineX, lineY, orientation, g );
					lineY += lineHeight;
					if (i == 0) {
						if (x > leftBorder) {
							lineX = leftBorder;
						}
						//System.out.println("changing lineX from " + lineX + " to " + leftBorder);
						if ( this.useSingleRow && this.label != null) {
							lineX -= this.label.itemWidth;
						}
					}
				}
		//#if tmp.useTextEffect
			}
		//#endif
		//#if polish.css.text-wrap
			if (this.useSingleLine && this.clipText ) {
				g.setClip( clipX, clipY, clipWidth, clipHeight );
			}
		//#endif
	}
	
	/**
	 * Paints a single line of text that is within the visible bounds
	 * @param line the text 
	 * @param x the left horizontal start position
	 * @param y the top or bottom vertical position
	 * @param anchor either Graphics.LEFT | Graphics.TOP or Graphics.LEFT | Graphics.BOTTOM
	 */
	public void drawString(String line, int x, int y, int anchor, Graphics g) {
		//#if polish.blackberry
			//# g.drawString(line, x, y ); // assuming top | left for all text
		//#else
			g.drawString( line, x, y, anchor );
		//#endif
	}
	
	/**
	 * Returns the height of one line
	 * @return the height of one line
	 */
	public int getLineHeight() {
		return getFontHeight() + this.paddingVertical;
	}
	
	/**
	 * Calculates the width of the given text.
	 * When a bitmap font is used, the calculation is forwarded to it.
	 * When a texteffect is used, the calculation is forwarded to it.
	 * In other cases font.stringWidth(text) is returned.
	 * 
	 * @param str the text of which the width should be determined
	 * @return the width of the text
	 */
	public int stringWidth( String str ) {
		//#if tmp.useTextEffect
			if (this.textEffect != null) {
				return this.textEffect.stringWidth( str );
			} else {
		//#endif
				return getFont().stringWidth(str);
		//#if tmp.useTextEffect
			}
		//#endif
	}
	
	/**
	 * Retrieves the width of the given char
	 * @param c the char
	 * @return the width of that char
	 */
	public int charWidth( char c) {
		return this.font.charWidth(c);
	}
	
	/**
	 * Retrieves the height necessary for displaying a row of text without the padding-vertical.
	 * 
	 * @return the font height (either from the bitmap, the text-effect or the font used)
	 */
	public int getFontHeight() {
		//#if tmp.useTextEffect
			if (this.textEffect != null) {
				return this.textEffect.getFontHeight();
			} else {
		//#endif
				return getFont().getHeight();
		//#if tmp.useTextEffect
			}
		//#endif
	}
	

	/* (non-Javadoc)
	 * @see de.enough.polish.ui.Item#initItem()
	 */
	protected void initContent(int firstLineWidth, int availWidth, int availHeight){
		//#debug
		System.out.println("initContent for " + this.text + " with firstLineWidth=" + firstLineWidth + ", availWidth=" + availWidth + ", availHeight=" + this.availableHeight);
		String body = this.text;
		if (body != null && this.font == null) {
			this.font = Font.getDefaultFont();
		}
		if ((body == null)
			//#if polish.css.text-visible
				|| !this.isTextVisible
			//#endif
		) {
			this.contentWidth = 0;
			this.contentHeight = 0;
			this.textLines.clear();
			return;
		}
		if (!this.isTextInitializationRequired && availWidth == this.lastAvailableContentWidth) {
			this.contentWidth = this.lastContentWidth; 
			this.contentHeight = this.lastContentHeight; 
			return;
		}
		this.isTextInitializationRequired = false;
		this.lastAvailableContentWidth = availWidth;
		this.textLines.clear();
		//#if polish.css.text-wrap
			if ( this.useSingleLine ) {
				this.availableTextWidth = availWidth;
				int myTextWidth = stringWidth(body);
				this.textLines.addLine(body, myTextWidth);
				if (myTextWidth > availWidth) {
					this.clipText = true;
					this.textWidth = myTextWidth;
					this.isHorizontalAnimationDirectionRight = false;
					this.contentWidth = availWidth;
				} else {
					this.clipText = false;
					this.contentWidth = myTextWidth;
				}
				this.contentHeight = getFontHeight();
			} else {
		//#endif
				wrap(body, firstLineWidth, availWidth);
				WrappedText lines = this.textLines;
				this.contentHeight = calculateLinesHeight(lines, getLineHeight());
				int maxWidth = lines.getMaxLineWidth();
				//#if polish.i18n.rightToLeft
					Object[] lineObjects = lines.getLinesInternalArray();
					for (int i = 0; i < lines.size(); i++) {
						String line = (String) lineObjects[i];
						line = TextUtil.reverseForRtlLanguage( line );
						//todo: possibly re-calculate the line width?
						lines.setLine(i, line );  
					}
				//#endif
				this.contentWidth = maxWidth;
		//#if polish.css.text-wrap
			}
		//#endif
//		if (this.textEffect != null)
//			System.out.println("init: padding-vertical=" + this.paddingVertical + ", paddingTop=" + this.paddingTop + " for " + this.text);
		this.lastContentWidth = this.contentWidth;
		this.lastContentHeight = this.contentHeight;
	}
	
	/**
	 * Resets if the text needs reinitialization.
	 * Normally this method does not need to be called, as any changes that require text-reinitialization are picked up
	 * automatically.
	 * @param isInitializationRequired true when the text needs to be initialized again.
	 * @see #isTextInitializationRequired()
	 */
	public void setTextInitializationRequired( boolean isInitializationRequired) {
		this.isTextInitializationRequired = isInitializationRequired;
	}
	
	/**
	 * Checks if the text needs to be initialized again at this point in time.
	 * @return true when a reinitialization is reuqired.
	 * @see #setTextInitializationRequired(boolean)
	 */
	public boolean isTextInitializationRequired() {
		return this.isTextInitializationRequired;
	}
	
	/**
	 * Wraps the specified text and adds the result to the internal field 'wrappedText'.
	 * @param body the text
	 * @param firstLineWidth the available width for the first line
	 * @param availWidth the available width for subsequent lines
	 */
	protected void wrap(String body, int firstLineWidth, int availWidth)
	{
		int maxNumberOfLines = TextUtil.MAXLINES_UNLIMITED;
		String maxAppendix = null;
		int maxPosition = TextUtil.MAXLINES_APPENDIX_POSITION_AFTER;
		//#ifdef polish.css.max-lines
			maxNumberOfLines = this.maxLines;
			maxAppendix = this.maxLinesAppendix;
			maxPosition = this.maxLinesAppendixPosition;
		//#endif
		//#ifdef tmp.useTextEffect
			if (this.textEffect != null) {
				this.textEffect.wrap(this, body, this.textColor, this.font, firstLineWidth, availWidth, maxNumberOfLines, maxAppendix, maxPosition, this.textLines );
			} else {
		//#endif
				TextUtil.wrap(body, this.font, firstLineWidth, availWidth, maxNumberOfLines, maxAppendix, maxPosition, this.textLines);
		//#ifdef tmp.useTextEffect
			}
		//#endif
	}
	
	/**
	 * Calculates the content height with the lines, the lineheight and the vertical padding
	 * @param lines the lines
	 * @param lineHeight the lineheight
	 * @return the height in pixels, normally (lines.size() * lineHeight) - this.paddingVertical;
	 * @see TextEffect#calculateLinesHeight(WrappedText, int, int)
	 */
	protected int calculateLinesHeight(WrappedText lines, int lineHeight) {
		//#ifdef tmp.useTextEffect
		if (this.textEffect != null) {
			return this.textEffect.calculateLinesHeight(lines,lineHeight,this.paddingVertical);
		} else
		{
		//#endif
			int linesHeight = (lines.size() * lineHeight) - this.paddingVertical;
			//#if polish.css.ignore-leading && polish.blackberry
			//# if(this.ignoreLeading) {
			//# 	linesHeight -= this.font.getLeading();
			//# }
			//#endif
			return linesHeight;
		//#ifdef tmp.useTextEffect
		}
		//#endif
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see de.enough.polish.ui.Item#setContentWidth(int)
	 */
	protected void setContentWidth(int width) {
		if (width < this.contentWidth) {
			initContent( width, width, this.availContentHeight );
		}
		super.setContentWidth(width);
		
	}

	/* (non-Javadoc)
	 * @see de.enough.polish.ui.Item#setStyle(de.enough.polish.ui.Style)
	 */
	public void setStyle(Style style)
	{
		//#ifdef tmp.useTextEffect
			TextEffect effect = (TextEffect) style.getObjectProperty( "text-effect" );
			if (effect != null)  {
				if (this.textEffect == null || this.textEffect.getClass() != effect.getClass()) {
					if (effect.isTextSensitive) {
						try
						{
							effect = (TextEffect) effect.getClass().newInstance();
						} catch (Exception e)
						{
							//#debug error
							System.out.println("Unable to copy effect " + effect + e);
						}
					}
					this.textEffect = effect;
					this.textEffect.onAttach(this);
				} else {
					effect = this.textEffect;
				}
				effect.setStyle(style);
				this.isTextInitializationRequired = true;
			} else if (this.textEffect != null) {
				this.textEffect.onDetach(this);
				this.textEffect = null;
				this.isTextInitializationRequired = true;
			}
		//#endif
		//#if polish.css.text-layout
			Integer layoutInt = style.getIntProperty("text-layout");
			if (layoutInt != null) {
				this.textLayout = layoutInt.intValue();
			} else {
				this.textLayout = style.layout;
			}
		//#endif 
		//#if tmp.useTextFilter
			RgbFilter[] filterObjects = (RgbFilter[]) style.getObjectProperty("text-filter");
			if (filterObjects != null) {
				if (filterObjects != this.originalTextFilters) {
					this.textFilters = new RgbFilter[ filterObjects.length ];
					for (int i = 0; i < filterObjects.length; i++)
					{
						RgbFilter rgbFilter = filterObjects[i];
						try
						{
							this.textFilters[i] = (RgbFilter) rgbFilter.getClass().newInstance();
						} catch (Exception e)
						{
							//#debug warn
							System.out.println("Unable to initialize filter class " + rgbFilter.getClass().getName() + e );
						}
					}
					this.originalTextFilters = filterObjects;
				}
			} else if (this.textFilterRgbImage != null) {
				this.originalTextFilters = null;
				this.textFilters = null;
				this.textFilterRgbImage = null;
			}
			//#if polish.css.text-filter-layout
				Integer textFilterLayoutInt = style.getIntProperty("text-filter-layout");
				if (textFilterLayoutInt != null) {
					this.textFilterLayout = textFilterLayoutInt.intValue();
				}
			//#endif
		//#endif

		//#if polish.css.ignore-leading
			Boolean ignoreLeadingBoolean = style.getBooleanProperty("ignore-leading");
			if (ignoreLeadingBoolean != null) {
				this.ignoreLeading = ignoreLeadingBoolean.booleanValue();
			}	
		//#endif
				
		super.setStyle(style);
	}

	/* (non-Javadoc)
	 * @see de.enough.polish.ui.Item#setStyle(de.enough.polish.ui.Style, boolean)
	 */
	public void setStyle(Style style, boolean resetStyle) {
		
		super.setStyle(style, resetStyle);
		
		if (resetStyle) {
			this.textColor = style.getFontColor();
			//System.out.println("reset style: color=" + Integer.toHexString(this.textColor) + " for " + this);
			Font prevFont = this.font;
			this.font = style.getFont();
			this.isTextInitializationRequired = this.isTextInitializationRequired 
				|| (prevFont == null && this.font != null) 
				|| (prevFont != null && !prevFont.equals(this.font));
		}

		//#ifdef polish.css.font-color
			Color textColorObj = style.getColorProperty("font-color");
			if ( textColorObj != null ) {
				this.textColor = textColorObj.getColor();
			}
		//#endif
		//#ifdef polish.css.text-horizontal-adjustment
			Integer textHorizontalAdjustmentInt = style.getIntProperty( "text-horizontal-adjustment" );
			if ( textHorizontalAdjustmentInt != null ) {
				this.textHorizontalAdjustment = textHorizontalAdjustmentInt.intValue();		
			}
		//#endif
		//#ifdef polish.css.text-vertical-adjustment
			Integer textVerticalAdjustmentInt = style.getIntProperty( "text-vertical-adjustment" );
			if ( textVerticalAdjustmentInt != null ) {
				this.textVerticalAdjustment = textVerticalAdjustmentInt.intValue();		
			}
		//#endif
		// reset useSingleLine:
		//#if polish.css.text-wrap
			Boolean textWrapBool = style.getBooleanProperty("text-wrap");
			if (textWrapBool != null) {
				if (textWrapBool.booleanValue() == this.useSingleLine) {
					this.useSingleLine = !textWrapBool.booleanValue();
					this.isTextInitializationRequired = true;
				}
			} else if (resetStyle && this.useSingleLine) {
				this.useSingleLine = false;
				this.isTextInitializationRequired = true;
			}
			//#if polish.css.text-wrap-animate
				Boolean animateTextWrapBool = style.getBooleanProperty("text-wrap-animate");
				if (animateTextWrapBool != null) {
					this.animateTextWrap = animateTextWrapBool.booleanValue();
				}
			//#endif
			//#ifdef polish.css.text-wrap-animation-direction
				Integer directionInt = style.getIntProperty("text-wrap-animation-direction");
				if (directionInt != null) {
					this.textWrapDirection = directionInt.intValue();
				}
			//#endif
			//#ifdef polish.css.text-wrap-animation-speed
				Integer animationSpeedInt = style.getIntProperty("text-wrap-animation-speed");
				if (animationSpeedInt != null) {
					this.textWrapSpeed = animationSpeedInt.intValue();
				}
			//#endif
		//#endif
		//#ifdef tmp.useTextEffect
			if (!resetStyle && this.textEffect != null) {
				this.textEffect.setStyle( style, false );
			}
		//#endif
		//#ifdef polish.css.max-lines
			Integer maxLinesInt = style.getIntProperty("max-lines");
			if (maxLinesInt != null) {
				if (maxLinesInt.intValue() != this.maxLines) {
					this.maxLines = maxLinesInt.intValue();
					if (this.maxLines <= 0) {
						this.maxLines = TextUtil.MAXLINES_UNLIMITED;
					}
					this.isTextInitializationRequired = true;
				}
			} 
			//#ifdef polish.css.max-lines-appendix
				String appendix = style.getProperty("max-lines-appendix");
				if (resetStyle || appendix != null) {
					this.maxLinesAppendix = appendix; 
				}
			//#endif
			//#ifdef polish.css.max-lines-appendix-position
				Integer positionInt = style.getIntProperty("max-lines-appendix-position");
				if (positionInt != null) {
					this.maxLinesAppendixPosition = positionInt.intValue(); 
				}
			//#endif
		//#endif
		//#if polish.css.text-visible
			Boolean textVisibleBool = style.getBooleanProperty("text-visible");
			if (textVisibleBool != null) {
				this.isTextVisible = textVisibleBool.booleanValue();
			} else if (resetStyle){
				this.isTextVisible = true;
			}
		//#endif
		//#if tmp.useTextFilter
			if (this.textFilters != null) {
				boolean isActive = false;
				for (int i=0; i<this.textFilters.length; i++) {
					RgbFilter filter = this.textFilters[i];
					filter.setStyle(style, resetStyle);
					isActive |= filter.isActive();
				}
				this.isTextFiltersActive = isActive;
				this.textFilterRgbImage = null;
			}
		//#endif
	}

	//#ifdef polish.useDynamicStyles
	/* (non-Javadoc)
	 * @see de.enough.polish.ui.Item#getCssSelector()
	 */
	protected String createCssSelector() {
		if ( this.appearanceMode == BUTTON ) {
			return "button";
		} else if (this.appearanceMode == HYPERLINK ) {
			return "a";
		} else {
			return "p";
		}
	}
	//#endif
	
	//#if polish.debug.error || polish.keepToString
	public String toString() {
		return  "StringItem " + super.toString() + ": \"" + this.getText() + "\""; 
	}
	//#endif

	/* (non-Javadoc)
	 * @see de.enough.polish.ui.Item#releaseResources()
	 */
	public void releaseResources() {
		super.releaseResources();
		//#ifdef tmp.useTextEffect
			 if ( this.textEffect != null ) {
				 this.textEffect.releaseResources();
			 }
		//#endif
	}

	/**
	 * Allows access to the wrapped text within this StringItem.
	 * Note that the text is only wrapped AFTER init()/initContent() has been called on this StringItem.
	 * @return the wrapped text, may be null when called before init()/initContent()
	 * @see #init(int, int, int)
	 * @see #initContent(int, int, int)
	 */
	public WrappedText getWrappedText() {
		return this.textLines;
	}
	
	/**
	 * Retrieves the number of lines.
	 * Note that the text is only wrapped AFTER init()/initContent() has been called on this StringItem.
	 * @return the number of lines or -1 when the text has not been initialized yet.
	 * @see #init(int, int, int)
	 * @see #initContent(int, int, int)
	 */
	public int getNumberOfLines() {
		if (this.textLines == null) {
			return -1;
		}
		return this.textLines.size();
	}

}
