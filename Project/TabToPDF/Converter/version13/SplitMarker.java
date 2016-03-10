package version13;

import tabparts.TabStaff;

public class SplitMarker {
	
	/* ATTRIBUTES */
	
	protected TabStaff staff;
	protected int maxchars;
	
	public SplitMarker(TabStaff staff, int maxchars) {
		this.staff = new TabStaff();
		this.staff.copyStaff(staff);
		this.maxchars = maxchars;
	}
}
