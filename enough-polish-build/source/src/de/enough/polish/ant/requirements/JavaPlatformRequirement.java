/*
 * Created on 16-Feb-2004 at 13:33:54.
 *
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
package de.enough.polish.ant.requirements;

import de.enough.polish.Device;
import de.enough.polish.util.StringUtil;

import de.enough.polish.BuildException;

/**
 * <p>Selects a device by the supported platform. A platform is for example "MIDP/1.0"</p>
 *
 * <p>Copyright Enough Software 2004 - 2011</p>

 * <pre>
 * history
 *        16-Feb-2004 - rob creation
 * </pre>
 * @author Robert Virkus, robert@enough.de
 */
public class JavaPlatformRequirement extends Requirement {
	
	private String platformName;
	private VersionMatcher platformVersion;
	private String neededPlatform;

	/**
	 * Creates a new requirement for the java platform of a device.
	 * 
	 * @param value The value of the platform
	 */
	public JavaPlatformRequirement(String value ) {
		super(value, "JavaPlatform" );
		this.neededPlatform = value;
		int splitPos = value.indexOf('/');
		if (splitPos == -1) {
			throw new BuildException("The JavaPlatform requirement needs to specify the " +
					"name and the version of the platform in the following form: " +
					"\"[name]/[version]\" - e.g. \"MIDP/1.0+\".");
		}
		this.platformName = value.substring(0, splitPos ).trim();
		this.platformVersion = new VersionMatcher( value.substring(splitPos + 1).trim() );
	}
	
	/* (non-Javadoc)
	 * @see de.enough.polish.ant.requirements.Requirement#isMet(de.enough.polish.Device, java.lang.String)
	 */
	protected boolean isMet(Device device, String property) {
		if (property.equals( this.neededPlatform)) {
			return true;
		}
		String[] platforms = StringUtil.splitAndTrim( property, ',');
		for (int i = 0; i < platforms.length; i++) {
			String platform = platforms[i];
			int splitPos = platform.indexOf('/');
			if (splitPos == -1) {
				return false;
			}
			String devPlatformName = platform.substring(0, splitPos ).trim();
			String devPlatformVersion = platform.substring(splitPos +1 ).trim();
			if ( this.platformName.equals(devPlatformName)
				&& this.platformVersion.matches(devPlatformVersion) ) {
				return true;
			}
		}
		return false;
	}
}
