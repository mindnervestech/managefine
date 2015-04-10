package viewmodel;

import java.util.List;

public class ReportEvent {
	public List<Integer> y;
	public String tooltip;
	public String x;
	public String color;
	public String label;
	public List<Integer> getY() {
		return y;
	}
	public void setY(List<Integer> y) {
		this.y = y;
	}
	public String getTooltip() {
		return tooltip;
	}
	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
}
