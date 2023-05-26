package calendar.drawing.color;

public enum Theme {
    Latte {
        public Color rosewater() { return new Color(220, 138, 120); }
        public Color flamingo()  { return new Color(221, 120, 120); }
        public Color pink()      { return new Color(234, 118, 203); }
        public Color mauve()     { return new Color(136, 57, 239); }
        public Color red()       { return new Color(210, 15, 57); }
        public Color maroon()    { return new Color(230, 69, 83); }
        public Color peach()     { return new Color(254, 100, 11); }
        public Color yellow()    { return new Color(223, 142, 29); }
        public Color green()     { return new Color(64, 160, 43); }
        public Color teal()      { return new Color(23, 146, 153); }
        public Color sky()       { return new Color(4, 165, 229); }
        public Color sapphire()  { return new Color(32, 159, 181); }
        public Color blue()      { return new Color(30, 102, 245); }
        public Color lavender()  { return new Color(114, 135, 253); }
        public Color text()      { return new Color(76, 79, 105); }
        public Color subtext1()  { return new Color(92, 95, 119); }
        public Color subtext0()  { return new Color(108, 111, 133); }
        public Color Overlay2()  { return new Color(124, 127, 147); }
        public Color overlay1()  { return new Color(140, 143, 161); }
        public Color overlay0()  { return new Color(156, 160, 176); }
        public Color surface2()  { return new Color(172, 176, 190); }
        public Color surface1()  { return new Color(188, 192, 204); }
        public Color surface0()  { return new Color(204, 208, 218); }
        public Color base()      { return new Color(239, 241, 245); }
        public Color mantle()    { return new Color(230, 233, 239); }
        public Color crust()     { return new Color(220, 224, 232); }

        public Color background() { return base(); }

        public Color[] highlights() {
            return new Color[]{
                rosewater(), flamingo(), pink(), mauve(), red(), maroon(), peach(), 
                yellow(), green(), teal(), sky(), sapphire(), blue(), lavender()
            };
        }

        public Color highlightText() { return new Color(255, 255, 255); }

        public Color offDayBack() { return crust(); }
        public Color offDayNum() { return subtext0(); }

        public Color selectedDayBack() { return mantle(); }
        public Color selectedDayFore() { return teal(); }

        public Color overflowText() { return text(); }
        public Color overflowHighlight() { return surface0(); }

        public Color infoLine() { return crust(); }
        public Color helpText() { return subtext0(); }
    },
    Frappe {
        public Color rosewater() { return new Color(242, 213, 207); }
        public Color flamingo()  { return new Color(238, 190, 190); }
        public Color pink()      { return new Color(244, 184, 228); }
        public Color mauve()     { return new Color(202, 158, 230); }
        public Color red()       { return new Color(231, 130, 132); }
        public Color maroon()    { return new Color(234, 153, 156); }
        public Color peach()     { return new Color(239, 159, 118); }
        public Color yellow()    { return new Color(229, 200, 144); }
        public Color green()     { return new Color(166, 209, 137); }
        public Color teal()      { return new Color(129, 200, 190); }
        public Color sky()       { return new Color(153, 209, 219); }
        public Color sapphire()  { return new Color(133, 193, 220); }
        public Color blue()      { return new Color(140, 170, 238); }
        public Color lavender()  { return new Color(186, 187, 241); }
        public Color text()      { return new Color(198, 208, 245); }
        public Color subtext1()  { return new Color(181, 191, 226); }
        public Color subtext0()  { return new Color(165, 173, 206); }
        public Color overlay2()  { return new Color(148, 156, 187); }
        public Color overlay1()  { return new Color(131, 139, 167); }
        public Color overlay0()  { return new Color(115, 121, 148); }
        public Color surface2()  { return new Color(98, 104, 128); }
        public Color surface1()  { return new Color(81, 87, 109); }
        public Color surface0()  { return new Color(65, 69, 89); }
        public Color base()      { return new Color(48, 52, 70); }
        public Color mantle()    { return new Color(41, 44, 60); }
        public Color crust()     { return new Color(35, 38, 52); }

        public Color background() { return base(); }

        public Color[] highlights() {
            return new Color[]{
                rosewater(), flamingo(), pink(), mauve(), red(), maroon(), peach(), 
                yellow(), green(), teal(), sky(), sapphire(), blue(), lavender()
            };
        }

        public Color highlightText() { return base(); }

        public Color offDayBack() { return mantle(); } // crust is wayyy to dark
        public Color offDayNum() { return subtext0(); }

        public Color selectedDayBack() { return mantle(); }
        public Color selectedDayFore() { return teal(); }

        public Color overflowText() { return text(); }
        public Color overflowHighlight() { return surface0(); }

        public Color infoLine() { return mantle(); }
        public Color helpText() { return subtext0(); }
    },
    Macchiato {
        public Color rosewater() { return new Color(244, 219, 214); }
        public Color flamingo()  { return new Color(240, 198, 198); }
        public Color pink()      { return new Color(245, 189, 230); }
        public Color mauve()     { return new Color(198, 160, 246); }
        public Color red()       { return new Color(237, 135, 150); }
        public Color maroon()    { return new Color(238, 153, 160); }
        public Color peach()     { return new Color(245, 169, 127); }
        public Color yellow()    { return new Color(238, 212, 159); }
        public Color green()     { return new Color(166, 218, 149); }
        public Color teal()      { return new Color(139, 213, 202); }
        public Color sky()       { return new Color(145, 215, 227); }
        public Color sapphire()  { return new Color(125, 196, 228); }
        public Color blue()      { return new Color(138, 173, 244); }
        public Color lavender()  { return new Color(183, 189, 248); }
        public Color text()      { return new Color(202, 211, 245); }
        public Color subtext1()  { return new Color(184, 192, 224); }
        public Color subtext0()  { return new Color(165, 173, 203); }
        public Color overlay2()  { return new Color(147, 154, 183); }
        public Color overlay1()  { return new Color(128, 135, 162); }
        public Color overlay0()  { return new Color(110, 115, 141); }
        public Color surface2()  { return new Color(91, 96, 120); }
        public Color surface1()  { return new Color(73, 77, 100); }
        public Color surface0()  { return new Color(54, 58, 79); }
        public Color base()      { return new Color(36, 39, 58); }
        public Color mantle()    { return new Color(30, 32, 48); }
        public Color crust()     { return new Color(24, 25, 38); }

        public Color background() { return base(); }

        public Color[] highlights() {
            return new Color[]{
                rosewater(), flamingo(), pink(), mauve(), red(), maroon(), peach(), 
                yellow(), green(), teal(), sky(), sapphire(), blue(), lavender()
            };
        }

        public Color highlightText() { return base(); }

        public Color offDayBack() { return mantle(); } // crust is wayyy to dark
        public Color offDayNum() { return subtext0(); }

        public Color selectedDayBack() { return mantle(); }
        public Color selectedDayFore() { return teal(); }

        public Color overflowText() { return text(); }
        public Color overflowHighlight() { return surface0(); }

        public Color infoLine() { return mantle(); }
        public Color helpText() { return subtext0(); }
    },
    Mocha {
 	    public Color rosewater() { return new Color(245, 224, 220); }
	    public Color flamingo()  { return new Color(242, 205, 205); }
	    public Color pink()      { return new Color(245, 194, 231); }
	    public Color mauve()     { return new Color(203, 166, 247); }
	    public Color red()       { return new Color(243, 139, 168); }
	    public Color maroon()    { return new Color(235, 160, 172); }
	    public Color peach()     { return new Color(250, 179, 135); }
	    public Color yellow()    { return new Color(249, 226, 175); }
	    public Color green()     { return new Color(166, 227, 161); }
	    public Color teal()      { return new Color(148, 226, 213); }
	    public Color sky()       { return new Color(137, 220, 235); }
	    public Color sapphire()  { return new Color(116, 199, 236); }
	    public Color blue()      { return new Color(137, 180, 250); }
	    public Color lavender()  { return new Color(180, 190, 254); }
	    public Color text()      { return new Color(205, 214, 244); }
	    public Color subtext1()  { return new Color(186, 194, 222); }
	    public Color subtext0()  { return new Color(166, 173, 200); }
	    public Color overlay2()  { return new Color(147, 153, 178); }
	    public Color overlay1()  { return new Color(127, 132, 156); }
	    public Color overlay0()  { return new Color(108, 112, 134); }
	    public Color surface2()  { return new Color(88, 91, 112); }
	    public Color surface1()  { return new Color(69, 71, 90); }
	    public Color surface0()  { return new Color(49, 50, 68); }
	    public Color base()      { return new Color(30, 30, 46); }
	    public Color mantle()    { return new Color(24, 24, 37); }
	    public Color crust()     { return new Color(17, 17, 27); }

        public Color background() { return base(); }

        public Color[] highlights() {
            return new Color[]{
                rosewater(), flamingo(), pink(), mauve(), red(), maroon(), peach(), 
                yellow(), green(), teal(), sky(), sapphire(), blue(), lavender()
            };
        }

        public Color highlightText() { return base(); }

        public Color offDayBack() { return mantle(); } // crust is wayyy to dark
        public Color offDayNum() { return subtext0(); }

        public Color selectedDayBack() { return mantle(); }
        public Color selectedDayFore() { return teal(); }

        public Color overflowText() { return text(); }
        public Color overflowHighlight() { return surface0(); }

        public Color infoLine() { return mantle(); }
        public Color helpText() { return subtext0(); }
    },
    Transparent {
        public Color text() { return Frappe.text(); }
        public Color background() { return null; }

        public Color[] highlights() {
            return Frappe.highlights();
        }

        public Color highlightText() { return Frappe.highlightText(); }

        public Color offDayBack() { return Frappe.offDayBack(); } // crust is wayyy to dark
        public Color offDayNum() { return Frappe.offDayNum(); }

        public Color selectedDayBack() { return Frappe.selectedDayBack(); }
        public Color selectedDayFore() { return Frappe.selectedDayFore(); }

        public Color overflowText() { return text(); }
        public Color overflowHighlight() { return Frappe.overflowHighlight(); }

        public Color infoLine() { return Frappe.infoLine(); }
        public Color helpText() { return Frappe.helpText(); }
    };

    public abstract Color text();
    public abstract Color background();

    public abstract Color[] highlights();
    public abstract Color highlightText();

    public abstract Color offDayBack();
    public abstract Color offDayNum();

    public abstract Color selectedDayBack();
    public abstract Color selectedDayFore();

    public abstract Color overflowText();
    public abstract Color overflowHighlight();

    public abstract Color infoLine();
    public abstract Color helpText();
}
